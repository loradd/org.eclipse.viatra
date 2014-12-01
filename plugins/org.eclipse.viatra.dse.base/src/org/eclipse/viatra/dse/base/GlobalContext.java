/*******************************************************************************
 * Copyright (c) 2010-2014, Miklos Foldenyi, Andras Szabolcs Nagy, Abel Hegedus, Akos Horvath, Zoltan Ujhelyi and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *   Miklos Foldenyi - initial API and implementation
 *   Andras Szabolcs Nagy - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.dse.base;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.incquery.runtime.api.IPatternMatch;
import org.eclipse.viatra.dse.api.DSEException;
import org.eclipse.viatra.dse.api.PatternWithCardinality;
import org.eclipse.viatra.dse.api.TransformationRule;
import org.eclipse.viatra.dse.api.strategy.ExplorerThread;
import org.eclipse.viatra.dse.api.strategy.Strategy;
import org.eclipse.viatra.dse.api.strategy.StrategyFactory;
import org.eclipse.viatra.dse.api.strategy.interfaces.IExplorerThread;
import org.eclipse.viatra.dse.api.strategy.interfaces.IStrategyFactory;
import org.eclipse.viatra.dse.designspace.api.IDesignSpace;
import org.eclipse.viatra.dse.designspace.api.TrajectoryInfo;
import org.eclipse.viatra.dse.multithreading.DSEThreadPool;
import org.eclipse.viatra.dse.solutionstore.ISolutionStore;
import org.eclipse.viatra.dse.solutionstore.SimpleSolutionStore;
import org.eclipse.viatra.dse.statecode.IStateSerializerFactory;
import org.eclipse.viatra.dse.util.EMFHelper;

/**
 * Creates new contexts for strategies. It is needed because of the multithreading.
 * 
 * @author Andras Szabolcs Nagy
 * 
 */
public class GlobalContext {

    // **** fields and methods for multi threading *****//
    // *************************************************//

    public enum ExplorationProcessState {
        NOT_STARTED,
        RUNNING,
        STOPPING,
        COMPLETED
    }

    private ConcurrentLinkedQueue<Throwable> exceptions = new ConcurrentLinkedQueue<Throwable>();

    private volatile ExplorationProcessState state = ExplorationProcessState.NOT_STARTED;
    private IStrategyFactory strategyFactory = new StrategyFactory();
    private final Set<IExplorerThread> runningThreads = new HashSet<IExplorerThread>();
    private DSEThreadPool threadPool = new DSEThreadPool();
    private int numberOfStartedThreads = 0;
    private IDesignSpace designSpace;

    /**
     * The DesignSpaceExplorer's thread.
     */
    private final Thread mainThread;

    public GlobalContext() {
        mainThread = Thread.currentThread();
    }

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * Starts a new thread to explore the design space.
     * 
     * @param strategy
     *            The {@link Strategy}.
     * @param tedToClone
     *            The model to clone. Hint: context.getTed()
     * @param cloneModel
     *            It should be true in most cases.
     * @return The newly created {@link ExplorerThread}. Null if the number of the current strategies reached their
     *         maximum.
     */
    public synchronized IExplorerThread tryStartNewThread(ThreadContext originalThreadContext, EObject root,
            boolean cloneModel, Strategy strategy) {
        if (state != ExplorationProcessState.COMPLETED && state != ExplorationProcessState.STOPPING
                && threadPool.canStartNewThread()) {

            // clone the parent's thread model. it should be done in the
            // parent's thread so the model won't be changed during cloning
            EditingDomain domain = originalThreadContext.getEditingDomain();
            EObject rootToClone = domain.getResourceSet().getResources().get(0).getContents().get(0);

            if (root != null) {
                if (cloneModel) {
                    rootToClone = root;
                } else {
                    throw new DSEException(
                            "If the newly started thread's root EObject is different then the original, it must be cloned. Change parameters.");
                }
            }

            if (cloneModel) {
                EObject clonedModel = EMFHelper.clone(rootToClone);
                domain = EMFHelper.createEditingDomain(clonedModel);
            }

            ThreadContext newThreadContext;
            if (cloneModel) {
                TrajectoryInfo trajectoryInfo = originalThreadContext.getDesignSpaceManager().getTrajectoryInfo();
                newThreadContext = new ThreadContext(this, strategy, domain, root != null ? null : trajectoryInfo,
                        originalThreadContext.getGuidance());
            } else {
                // TODO This is only appropriate if this is the first thread
                // There can be circumstances, when cloneModel is false, but this is not first thread!
                newThreadContext = originalThreadContext;
            }
            // TODO : clone undo list? slave strategy can't go further back...
            IExplorerThread explorerThread = strategyFactory.createStrategy(newThreadContext);
            newThreadContext.setExplorerThread(explorerThread);

            boolean isSuccessful = threadPool.tryStartNewStrategy(explorerThread);

            if (isSuccessful) {
                runningThreads.add(explorerThread);

                state = ExplorationProcessState.RUNNING;
                ++numberOfStartedThreads;

                if (logger.isDebugEnabled()) {
                    logger.debug("New worker started, active workers: " + runningThreads.size());
                }

                return explorerThread;
            }
        }
        return null;
    }

    /**
     * Starts a new thread to explore the design space.
     * 
     * @param strategyBase
     *            The {@link Strategy}.
     * @param tedToClone
     *            The model to clone. Hint: context.getTed()
     * @return The newly created {@link ExplorerThread}. Null if the number of the current strategies reached their
     *         maximum.
     */
    public synchronized IExplorerThread tryStartNewThread(ThreadContext originalThreadContext) {
        return tryStartNewThread(originalThreadContext, null, true, originalThreadContext.getStrategy());
    }

    public synchronized IExplorerThread tryStartNewThread(ThreadContext originalThreadContext, Strategy strategyBase) {
        return tryStartNewThread(originalThreadContext, null, true, strategyBase);
    }

    public synchronized IExplorerThread tryStartNewThread(ThreadContext originalThreadContext, boolean cloneModel) {
        return tryStartNewThread(originalThreadContext, null, cloneModel, originalThreadContext.getStrategy());
    }

    public synchronized IExplorerThread tryStartNewThread(ThreadContext originalThreadContext, EObject root) {
        return tryStartNewThread(originalThreadContext, root, true, originalThreadContext.getStrategy());
    }

    public synchronized void strategyFinished(IExplorerThread strategy) {
        runningThreads.remove(strategy);

        if (logger.isDebugEnabled()) {
            logger.debug("Worker finished, active workers: " + runningThreads.size());
        }

        // is the first part necessary?
        if (runningThreads.size() == 0) {
            state = ExplorationProcessState.COMPLETED;
            threadPool.shutdown();

            // if the main thread (which started the exploration)
            // is waiting for the solution, than wake it up
            mainThread.interrupt();

        }
    }

    public synchronized boolean isDone() {
        return state == ExplorationProcessState.COMPLETED && runningThreads.size() == 0;
    }

    public boolean canStartNewThread() {
        return (state == ExplorationProcessState.NOT_STARTED || state == ExplorationProcessState.RUNNING)
                && threadPool.canStartNewThread();
    }

    public synchronized void stopAllThreads() {
        if (state == ExplorationProcessState.RUNNING) {
            state = ExplorationProcessState.STOPPING;
            logger.debug("Stopping all threads.");
            for (IExplorerThread strategy : runningThreads) {
                strategy.stopRunning();
            }
        }
    }

    public void registerException(Throwable e) {
        exceptions.add(e);
    }

    // ******* fields and methods for exploration *******//
    // **************************************************//

    private Set<PatternWithCardinality> goalPatterns = new HashSet<PatternWithCardinality>();
    private Set<PatternWithCardinality> constraints = new HashSet<PatternWithCardinality>();
    private Set<TransformationRule<? extends IPatternMatch>> transformations = new HashSet<TransformationRule<? extends IPatternMatch>>();
    private IStateSerializerFactory stateSerializerFactory;
    private ISolutionStore solutionStore = new SimpleSolutionStore();
    private Object SharedObject;

    public void reset() {
        state = ExplorationProcessState.NOT_STARTED;
        threadPool = new DSEThreadPool();
        exceptions.clear();
    }

    // *** getters and setters

    public boolean isExceptionHappendInOtherThread() {
        return !exceptions.isEmpty();
    }

    public Collection<Throwable> getExceptions() {
        return exceptions;
    }

    public IStateSerializerFactory getStateSerializerFactory() {
        return stateSerializerFactory;
    }

    public void setStateSerializerFactory(IStateSerializerFactory stateSerializerFactory) {
        this.stateSerializerFactory = stateSerializerFactory;
    }

    public Set<PatternWithCardinality> getGoalPatterns() {
        return goalPatterns;
    }

    public Set<PatternWithCardinality> getConstraints() {
        return constraints;
    }

    public Set<TransformationRule<? extends IPatternMatch>> getTransformations() {
        return transformations;
    }

    public void setGoalPatterns(Set<PatternWithCardinality> goalPatterns) {
        this.goalPatterns = goalPatterns;
    }

    public void setConstraints(Set<PatternWithCardinality> constraints) {
        this.constraints = constraints;
    }

    public void setTransformations(Set<TransformationRule<? extends IPatternMatch>> transformations) {
        this.transformations = transformations;
    }

    public DSEThreadPool getThreadPool() {
        return threadPool;
    }

    public IStrategyFactory getStrategyFactory() {
        return strategyFactory;
    }

    public void setStrategyFactory(IStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public IDesignSpace getDesignSpace() {
        return designSpace;
    }

    public void setDesignSpace(IDesignSpace designSpace) {
        this.designSpace = designSpace;
    }

    public int getNumberOfStartedThreads() {
        return numberOfStartedThreads;
    }

    public Object getSharedObject() {
        return SharedObject;
    }

    public void setSharedObject(Object sharedObject) {
        SharedObject = sharedObject;
    }

    public ISolutionStore getSolutionStore() {
        return solutionStore;
    }

    public void setSolutionStore(ISolutionStore solutionStore) {
        this.solutionStore = solutionStore;
    }

    public ExplorationProcessState getState() {
        return state;
    }
}