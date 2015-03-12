/*******************************************************************************
 * Copyright (c) 2010-2013, Zoltan Ujhelyi, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zoltan Ujhelyi - initial API and implementation
 *******************************************************************************/
package org.eclipse.incquery.runtime.localsearch.operations.check;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.incquery.runtime.localsearch.MatchingFrame;
import org.eclipse.incquery.runtime.localsearch.exceptions.LocalSearchException;
import org.eclipse.incquery.runtime.localsearch.matcher.ISearchContext;
import org.eclipse.incquery.runtime.localsearch.matcher.LocalSearchMatcher;
import org.eclipse.incquery.runtime.localsearch.matcher.MatcherReference;
import org.eclipse.incquery.runtime.matchers.psystem.queries.PQuery;

import com.google.common.collect.Sets;

/**
 * Calculates the count of matches for a called matcher
 * 
 * @author Zoltan Ujhelyi
 *
 */
public class CountCheck extends CheckOperation {

    private PQuery calledQuery;
    private LocalSearchMatcher matcher;
    Map<Integer, Integer> frameMapping;
    private int position;

    public LocalSearchMatcher getCalledMatcher(){
    	return matcher;
    }
    
    public CountCheck(PQuery calledQuery, Map<Integer, Integer> frameMapping, int position) {
        super();
        this.calledQuery = calledQuery;
        this.frameMapping = frameMapping;
        this.position = position;
    }

    public PQuery getCalledQuery() {
        return calledQuery;
    }

    @Override
    public void onInitialize(MatchingFrame frame, ISearchContext context) throws LocalSearchException {
        super.onInitialize(frame, context);
        Set<Integer> adornment = Sets.newHashSet();
        for (Entry<Integer, Integer> mapping : frameMapping.entrySet()) {
            Integer source = mapping.getKey();
            if (frame.get(source) != null) {
                adornment.add(mapping.getValue());
            }
        }
        matcher = context.getMatcher(new MatcherReference(calledQuery, adornment));
    }

    @Override
    protected boolean check(MatchingFrame frame) throws LocalSearchException {
        final MatchingFrame mappedFrame = matcher.editableMatchingFrame();
        for (Entry<Integer, Integer> entry : frameMapping.entrySet()) {
            mappedFrame.setValue(entry.getValue(), frame.getValue(entry.getKey()));
        }
        int count = matcher.countMatches(mappedFrame);
        return ((Integer)frame.getValue(position)) == count;
    }

}