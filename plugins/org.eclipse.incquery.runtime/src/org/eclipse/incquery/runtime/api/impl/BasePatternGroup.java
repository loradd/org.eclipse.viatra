/*******************************************************************************
 * Copyright (c) 2010-2012, Mark Czotter, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Mark Czotter - initial API and implementation
 *******************************************************************************/
package org.eclipse.incquery.runtime.api.impl;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;
import org.eclipse.incquery.runtime.api.IPatternGroup;
import org.eclipse.incquery.runtime.api.IQuerySpecification;
import org.eclipse.incquery.runtime.api.IncQueryEngine;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.incquery.runtime.internal.apiimpl.IncQueryEngineImpl;
import org.eclipse.incquery.runtime.rete.construction.OperationCompilerException;

/**
 * Base implementation of {@link IPatternGroup}.
 * 
 * @author Mark Czotter
 * 
 */
public abstract class BasePatternGroup implements IPatternGroup {

    @Override
    public void prepare(Notifier emfRoot) throws IncQueryException {
        prepare(IncQueryEngine.on(emfRoot));
    }

    @Override
    public void prepare(IncQueryEngine engine) throws IncQueryException {
        try {
            final Set<Pattern> patterns = getPatterns();
            final IncQueryEngineImpl engineImpl = (IncQueryEngineImpl) engine;
			engineImpl.getSanitizer().admit(patterns);
			engineImpl.getReteEngine().buildMatchersCoalesced(patterns);
        } catch (OperationCompilerException e) {
            throw new IncQueryException(e);
        }
    }

    /**
     * Returns a set of {@link Pattern} objects, accessible from the {@link IQuerySpecification} objects.
     * 
     * @see IQuerySpecification#getPattern()
     * @param querySpecifications
     * @return
     */
    public static Set<Pattern> patterns(Set<IQuerySpecification<?>> querySpecifications) {
        Set<Pattern> result = new HashSet<Pattern>();
        for (IQuerySpecification<?> querySpecification : querySpecifications) {
            result.add(querySpecification.getPattern());
        }
        return result;
    }

}
