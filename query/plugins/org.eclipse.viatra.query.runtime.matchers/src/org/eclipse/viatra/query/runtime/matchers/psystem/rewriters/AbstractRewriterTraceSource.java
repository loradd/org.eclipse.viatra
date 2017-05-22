/*******************************************************************************
 * Copyright (c) 2010-2017, Grill Balázs, IncQueryLabs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Grill Balázs - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.matchers.psystem.rewriters;

import org.eclipse.viatra.query.runtime.matchers.psystem.PConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.PTraceable;

import com.google.common.base.Preconditions;

/**
 * @since 1.6
 *
 */
public class AbstractRewriterTraceSource {

    private IRewriterTraceCollector traceCollector = NopTraceCollector.INSTANCE;
    
    public void setTraceCollector(IRewriterTraceCollector traceCollector) {
        Preconditions.checkNotNull(traceCollector);
        this.traceCollector = traceCollector;
    }
    
    public IPTraceableTraceProvider getTraces() {
        return traceCollector;
    }
    
    protected IRewriterTraceCollector getTraceCollector() {
        return traceCollector;
    }
    
    /**
     * Mark the given derivative to be originated from the given original constraint.
     * @since 1.6
     */
    protected void addTrace(PTraceable original, PTraceable derivative){
        traceCollector.addTrace(original, derivative);
    }
    
    /**
     * Indicate that the given derivative is removed from the resulting query, thus its trace
     * information should be removed also.
     * @since 1.6
     */
    protected void derivativeRemoved(PConstraint derivative, IDerivativeModificationReason reason){
        traceCollector.derivativeRemoved(derivative, reason);
    }

}
