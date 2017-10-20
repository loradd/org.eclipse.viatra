/*******************************************************************************
 * Copyright (c) 2010-2017, Gabor Bergmann, IncQueryLabs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gabor Bergmann - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.matchers.tuple;

import java.util.Objects;

/**
 * Flat tuple with statically known arity of 4.
 * 
 * @author Gabor Bergmann
 * @since 1.7
 *
 */
public final class FlatTuple4 extends BaseFlatTuple {
    private final Object element0;
    private final Object element1;
    private final Object element2;
    private final Object element3;

    protected FlatTuple4(Object element0, Object element1, Object element2, Object element3) {
        this.element0 = element0;
        this.element1 = element1;
        this.element2 = element2;
        this.element3 = element3;
        calcHash();
    }
    
    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public Object get(int index) {
        switch(index) {
        case 0: return element0;
        case 1: return element1;
        case 2: return element2;
        case 3: return element3;
        default: throw raiseIndexingError(index);
        }
    }
    
    @Override
    protected boolean internalEquals(ITuple other) {
        return 4 == other.getSize() &&
                Objects.equals(element0, other.get(0)) &&
                Objects.equals(element1, other.get(1)) &&
                Objects.equals(element2, other.get(2)) &&
                Objects.equals(element3, other.get(3));
    }

}
