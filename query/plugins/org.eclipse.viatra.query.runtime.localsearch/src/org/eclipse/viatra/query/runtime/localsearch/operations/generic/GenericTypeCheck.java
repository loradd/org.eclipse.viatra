/*******************************************************************************
 * Copyright (c) 2010-2017, Zoltan Ujhelyi, IncQuery Labs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zoltan Ujhelyi - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.localsearch.operations.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.viatra.query.runtime.localsearch.MatchingFrame;
import org.eclipse.viatra.query.runtime.localsearch.matcher.ISearchContext;
import org.eclipse.viatra.query.runtime.localsearch.operations.IIteratingSearchOperation;
import org.eclipse.viatra.query.runtime.localsearch.operations.check.CheckOperation;
import org.eclipse.viatra.query.runtime.matchers.context.IInputKey;
import org.eclipse.viatra.query.runtime.matchers.tuple.TupleMask;
import org.eclipse.viatra.query.runtime.matchers.tuple.VolatileMaskedTuple;
import org.eclipse.viatra.query.runtime.matchers.util.Preconditions;

/**
 * @author Zoltan Ujhelyi
 * @since 1.7
 * @noextend This class is not intended to be subclassed by clients.
 */
public class GenericTypeCheck extends CheckOperation implements IIteratingSearchOperation {

    private final IInputKey type;
    private final List<Integer> positionList;
    private VolatileMaskedTuple maskedTuple;

    public GenericTypeCheck(IInputKey type, int[] positions, TupleMask callMask) {
        Preconditions.checkArgument(positions.length == type.getArity(),
                "The type %s requires %d parameters, but %d positions are provided", type.getPrettyPrintableName(),
                type.getArity(), positions.length);
        List<Integer> modifiablePositionList = new ArrayList<>();
        for (int position : positions) {
            modifiablePositionList.add(position);
        }
        this.positionList = Collections.unmodifiableList(modifiablePositionList);
        this.maskedTuple = new VolatileMaskedTuple(callMask);
        this.type = type;
    }

    @Override
    public List<Integer> getVariablePositions() {
        return positionList;
    }

    @Override
    protected boolean check(MatchingFrame frame, ISearchContext context) {
        maskedTuple.updateTuple(frame);
        return context.getRuntimeContext().containsTuple(type, maskedTuple);
    }

    @Override
    public String toString() {
        return "check     " + type.getPrettyPrintableName() + "("
                + positionList.stream().map(input -> String.format("+%d", input)).collect(Collectors.joining(", "))
                + ")";
    }

    @Override
    public IInputKey getIteratedInputKey() {
        return type;
    }
}
