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
package org.eclipse.viatra.query.runtime.localsearch.operations.check;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.viatra.query.runtime.localsearch.MatchingFrame;
import org.eclipse.viatra.query.runtime.localsearch.matcher.ISearchContext;

/**
 * @author Zoltan Ujhelyi
 * @noextend This class is not intended to be subclassed by clients.
 */
public class InstanceOfClassCheck extends CheckOperation {

    private int position;
    private EClass clazz;

    public InstanceOfClassCheck(int position, EClass clazz) {
        this.position = position;
        this.clazz = clazz;

    }

    @Override
    protected boolean check(MatchingFrame frame, ISearchContext context) {
        Objects.requireNonNull(frame.getValue(position), () -> String.format("Invalid plan, variable %s unbound", position));
        if (frame.getValue(position) instanceof EObject) {
            return clazz.isSuperTypeOf(((EObject) frame.getValue(position)).eClass());
        }
        return false;
    }

    @Override
    public String toString() {
        return "check     "+clazz.getName()+"(+"+ position+")";
    }
    
    @Override
    public List<Integer> getVariablePositions() {
        return Arrays.asList(position);
    }
    
}
