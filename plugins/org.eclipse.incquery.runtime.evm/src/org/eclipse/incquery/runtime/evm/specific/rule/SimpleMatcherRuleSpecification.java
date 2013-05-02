/*******************************************************************************
 * Copyright (c) 2010-2013, Abel Hegedus, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Abel Hegedus - initial API and implementation
 *******************************************************************************/
package org.eclipse.incquery.runtime.evm.specific.rule;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.incquery.runtime.api.IQuerySpecification;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.api.IncQueryMatcher;
import org.eclipse.incquery.runtime.evm.api.ActivationLifeCycle;
import org.eclipse.incquery.runtime.evm.api.Job;
import org.eclipse.incquery.runtime.evm.api.RuleInstance;
import org.eclipse.incquery.runtime.evm.api.RuleSpecification;
import org.eclipse.incquery.runtime.evm.api.event.Atom;
import org.eclipse.incquery.runtime.evm.api.event.EventSource;
import org.eclipse.incquery.runtime.evm.specific.event.IncQueryEventSource;
import org.eclipse.incquery.runtime.exception.IncQueryException;

import com.google.common.base.Objects;

/**
 * This class implements a rule specification that uses a single query specification to prepare instances. 
 * 
 * @author Abel Hegedus
 *
 */
public class SimpleMatcherRuleSpecification<Match extends IPatternMatch, Matcher extends IncQueryMatcher<Match>> extends RuleSpecification {
    
    private final IQuerySpecification<Matcher> querySpecification;
    
    /**
     * Creates a specification with the given query specification, life-cycle and job list.
     * 
     * @param querySpecification
     * @param lifeCycle
     * @param jobs
     */
    public SimpleMatcherRuleSpecification(final IQuerySpecification<Matcher> querySpecification, final ActivationLifeCycle lifeCycle,
            final Set<Job> jobs) {
        super(lifeCycle, jobs);
        this.querySpecification = checkNotNull(querySpecification, "Cannot create rule specification with null query specification!");
    }

    @Override
    protected RuleInstance instantiateRule(EventSource eventSource, Atom filter) {
        if(eventSource instanceof IncQueryEventSource) {
            IncQueryEngine engine = ((IncQueryEventSource) eventSource).getEngine();
            try {
                Matcher matcher = getMatcher(engine);
                SimpleMatcherRuleInstance<Match,Matcher> ruleInstance = new SimpleMatcherRuleInstance<Match,Matcher>(this, filter);
                ruleInstance.prepareInstance(matcher);
                return ruleInstance;
            } catch (IncQueryException e) {
                engine.getLogger().error(String.format("Could not initialize matcher for pattern %s in rule specification %s",querySpecification.getPatternFullyQualifiedName(),this), e);
            }
        }
        return null;
    }

    protected Matcher getMatcher(IncQueryEngine engine) throws IncQueryException {
        Matcher matcher = querySpecification.getMatcher(engine);
        return matcher;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("pattern", querySpecification.getPatternFullyQualifiedName())
                .add("lifecycle", getLifeCycle()).add("jobs", getJobs()).toString();
    }
}
