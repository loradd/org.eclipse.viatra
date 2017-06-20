/*******************************************************************************
 * Copyright (c) 2004-2015, Istvan David, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Istvan David - initial API and implementation
 *******************************************************************************/

package org.eclipse.viatra.cep.core.engine.compiler.builders

import com.google.common.base.Preconditions
import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.viatra.cep.core.engine.compiler.PermutationsHelper
import org.eclipse.viatra.cep.core.metamodels.automaton.Automaton
import org.eclipse.viatra.cep.core.metamodels.automaton.AutomatonFactory
import org.eclipse.viatra.cep.core.metamodels.automaton.NegativeTransition
import org.eclipse.viatra.cep.core.metamodels.automaton.Parameter
import org.eclipse.viatra.cep.core.metamodels.automaton.State
import org.eclipse.viatra.cep.core.metamodels.automaton.Transition
import org.eclipse.viatra.cep.core.metamodels.automaton.TypedTransition
import org.eclipse.viatra.cep.core.metamodels.events.AND
import org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern
import org.eclipse.viatra.cep.core.metamodels.events.EventPattern
import org.eclipse.viatra.cep.core.metamodels.events.EventPatternReference
import org.eclipse.viatra.cep.core.metamodels.events.EventsFactory
import org.eclipse.viatra.cep.core.metamodels.events.FOLLOWS
import org.eclipse.viatra.cep.core.metamodels.events.NEG
import org.eclipse.viatra.cep.core.metamodels.events.OR
import org.eclipse.viatra.cep.core.metamodels.trace.TraceFactory
import org.eclipse.viatra.cep.core.metamodels.trace.TraceModel
import org.eclipse.viatra.cep.core.engine.compiler.TransformationBasedCompiler

class ComplexMappingUtils {
    protected val extension AutomatonFactory automatonFactory = AutomatonFactory.eINSTANCE
    protected val extension TraceFactory traceFactory = TraceFactory.eINSTANCE
    private extension BuilderPrimitives builderPrimitives
    private TraceModel traceModel


    new(TraceModel traceModel) {
        this.traceModel = traceModel
        this.builderPrimitives = new BuilderPrimitives(traceModel)
    }

    private static var relabelingCounter = 0L

    def relabelWithActualParameters(ComplexEventPattern referredEventPattern, Collection<Parameter> actualParameters) {
        val relabelingUniqueID = relabelingCounter++ 
        
        val renamings = newHashMap(actualParameters.map[
            referredEventPattern.parameterNames.get(position) -> symbolicName
        ])
        
        val results = new ArrayList(referredEventPattern.containedEventPatterns.map[ symbolicPattern |
            EcoreUtil.copy(symbolicPattern) => [symbolicPattern.eResource.contents += it]
        ])
        results.forEach[
            val renamedList = new ArrayList(parameterSymbolicNames.map[
                if (it.equalsIgnoreCase(TransformationBasedCompiler.OMITTED_PARAMETER_SYMBOLIC_NAME)) {
                    it
                } else {
                    val renamed = renamings.get(it)
                    if (renamed === null)
                        '''«relabelingUniqueID»|«it»'''
                    else
                        renamed
                }
            ])
            parameterSymbolicNames.clear
            parameterSymbolicNames.addAll(renamedList)
        ]
        return results
    }

    /**
     * Builds a path of {@link Transition}s and {@link State}s for the referred {@link EventPattern} with a
     * {@link FOLLOWS} operator.
     */
    public def buildFollowsPath(Automaton automaton, ComplexEventPattern eventPattern, State preState,
        State postState) {
        buildFollowsPath(automaton, eventPattern.containedEventPatterns, preState, postState)

        initializeTimewindow(automaton, eventPattern, preState, postState)
    }

    /**
     * Builds a path of {@link Transition}s and {@link State}s for the referred {@link EventPatternReference}s combined
     * under an {@link OR} operator.
     */
    private def buildFollowsPath(Automaton automaton, List<EventPatternReference> eventPatternReferences,
        State preState, State postState) {
        var State firstCreatedState = null
        var State nextState = null

        for (eventPatternReference : eventPatternReferences) {
            if (nextState === null) {
                nextState = mapWithMultiplicity(eventPatternReference, automaton, preState)
                firstCreatedState = nextState
            } else {
                nextState = mapWithMultiplicity(eventPatternReference, automaton, nextState)
            }
        }

        createEpsilon(nextState, postState)
        firstCreatedState
    }

    public def unfoldFollowsPath(Automaton automaton, ComplexEventPattern eventPattern, TypedTransition transition) {
        val relabeledEventReferences = relabelWithActualParameters(eventPattern, transition.parameters)
        val firstCreatedState = buildFollowsPath(automaton, relabeledEventReferences, transition.preState,
            transition.postState)

        alignTimewindow(automaton, eventPattern, transition, firstCreatedState)
    }

    /**
     * Builds a path of {@link Transition}s and {@link State}s for the referred {@link EventPattern} with an {@link OR}
     * operator.
     */
    public def buildOrPath(Automaton automaton, ComplexEventPattern eventPattern, State preState, State postState) {
        buildOrPath(automaton, eventPattern.containedEventPatterns, preState, postState)
    }
    
    private def buildOrPath(Automaton automaton, Collection<EventPatternReference> eventPatterns, State preState, State postState) {
        val State lastState = createState
        automaton.states += lastState

        for (eventPatternReference : eventPatterns) {
            mapWithMultiplicity(eventPatternReference, automaton, preState, lastState)
        }

        createEpsilon(lastState, postState)
    }

    public def unfoldOrPath(Automaton automaton, ComplexEventPattern eventPattern, TypedTransition transition) {
        val relabeledEventReferences = relabelWithActualParameters(eventPattern, transition.parameters)
        buildOrPath(automaton, relabeledEventReferences, transition.preState, transition.postState)
        
        alignTimewindow(automaton, eventPattern, transition)
    }

    /**
     * Builds a path of {@link Transition}s and {@link State}s for the referred {@link EventPattern} with an
     * {@link AND} operator.
     */
    public def buildAndPath(Automaton automaton, ComplexEventPattern eventPattern, State preState, State postState) {
        buildAndPath(automaton, eventPattern.containedEventPatterns, preState, postState)
    }
    
    private def buildAndPath(Automaton automaton, List<EventPatternReference> eventPatterns, State preState, State postState) {
        for (permutation : new PermutationsHelper<EventPatternReference>().getAll(eventPatterns)) {
            automaton.buildFollowsPath(permutation, preState, postState)
        }
    }

    public def unfoldAndPath(Automaton automaton, ComplexEventPattern eventPattern, TypedTransition transition) {
        val relabeledEventReferences = relabelWithActualParameters(eventPattern, transition.parameters)
        buildAndPath(automaton, relabeledEventReferences, transition.preState, transition.postState)
        
        alignTimewindow(automaton, eventPattern, transition)
    }

    /**
     * Builds a path of {@link Transition}s and {@link State}s for the referred {@link EventPattern} with a {@link NOT}
     * operator.
     */
    public def buildNotPath(Automaton automaton, EventPattern eventPattern, State preState, State postState) {
        var transition = createNegativeTransition
        var guard = createGuard
        guard.eventType = eventPattern
        transition.guards += guard

        transition.preState = preState
        transition.postState = postState
    }

    /**
     * Unfolds a {@link Transition} guarded by a {@link ComplexEventPattern} with a {@link NEG} operator.
     */
    public def unfoldNotPath(Automaton automaton, ComplexEventPattern eventPattern, NegativeTransition transition) {
        switch (eventPattern.operator) {
            NEG: { // double negation -> positive pattern
                val newTransition = newTransition(transition.preState, transition.postState)
                newTransition.guards += transition.guards
                newTransition.parameters += transition.parameters
            }
            FOLLOWS: {
                val relabeledEventPatterns = relabelWithActualParameters(eventPattern, transition.parameters)
                val firstNegBranch = newNegTransition(transition.preState, transition.postState)
                firstNegBranch.addGuard(relabeledEventPatterns.head)
                relabeledEventPatterns.head.handleTransitionParameters(firstNegBranch)
                if (relabeledEventPatterns.size > 1) {
                    Preconditions::checkArgument(relabeledEventPatterns.size == 2) // XXX domain knowledge hard coded!
                    val secondNegBranchState = automaton.transitionToNewState(relabeledEventPatterns.head,
                        transition.preState)
                    val secondNegBranch = newNegTransition(secondNegBranchState, transition.postState)
                    secondNegBranch.addGuard(relabeledEventPatterns.get(1))
                    relabeledEventPatterns.last.handleTransitionParameters(secondNegBranch)
                }
            }
            OR: {
                val newTransition = newNegTransition(transition.preState, transition.postState)
                relabelWithActualParameters(eventPattern, transition.parameters).forEach [ ref |
                    newTransition.addGuard(ref.eventPattern)
                ]
            // TODO : parameters?
            }
            AND: {
                for (permutation : new PermutationsHelper<EventPatternReference>().getAll(
                    relabelWithActualParameters(eventPattern, transition.parameters))) 
                {
                    val surrogateFollowsPattern = EventsFactory.eINSTANCE.createComplexEventPattern
                    surrogateFollowsPattern.containedEventPatterns += permutation
                    surrogateFollowsPattern.operator = EventsFactory.eINSTANCE.createFOLLOWS
                    transitionBetween(surrogateFollowsPattern, transition.preState, transition.postState)
                }

                throw new UnsupportedOperationException
            }
            default: {
                throw new IllegalArgumentException
            }
        }
    }
}