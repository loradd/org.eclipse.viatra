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
package org.eclipse.viatra.query.runtime.matchers.util;

/**
 * A memory containing a positive or negative number of equal() copies for some values.
 * During iterations, each distinct value is iterated only once.
 * 
 * <p> Refined by: <ul> 
 *  <li>{@link IMultiset}, which always contains values with a nonnegative multiplicity. </li>
 *  <li>{@link IDeltaBag}, which may contain values with negative multiplicity. </li>
 *  <li>{@link ISetMemory}, which is just a set (allowed multiplicities: 0 and 1). </li>
 * </ul>
 * 
 * @author Gabor Bergmann
 * @since 1.7
 */
public interface IMemory<T> extends IMemoryView<T>, Clearable {

    /**
     * Adds one value occurrence to the memory.
     * 
     * @return true if the tuple was not present before in the memory, or
     *      (in case of {@link IDeltaBag}) is no longer present in the memory 
     */
    boolean addOne(T value);

    /**
     * Adds the given number of occurrences to the memory. The count value may or may not be negative.
     * <p> Precondition if {@link IMultiset}: at least the given amount of occurrences exist, if count is negative.
     * <p> Precondition if {@link ISetMemory}: count is +1 or -1, the latter is only allowed if the set contains the value.
     * 
     * @param count
     *            the number of occurrences
     * @return true if the tuple was not present before in the memory, or is no longer present in the memory
     * @throws IllegalStateException if {@link IMultiset} or {@link ISetMemory} and the number of occurrences in the memory would underflow to negative
     */
    boolean addSigned(T value, int count);

    /**
     * Removes one occurrence of the given value from the memory.
     * <p> Precondition if {@link IMultiset} or {@link ISetMemory}: the value must have a positive amount of occurrences in the memory.
     * 
     * @return true if this was the the last occurrence of the value, or
     *      (in case of {@link IDeltaBag}) is the first negative occurrence of the value 
     * @throws IllegalStateException if {@link IMultiset} or {@link ISetMemory} and value had no occurrences in the memory
     */
    boolean removeOne(T value);

    /**
     * Removes all occurrences of the given value from the memory.
     */
    void clearAllOf(T value);

    /**
     * Empties out the memory. 
     */
    void clear();

}