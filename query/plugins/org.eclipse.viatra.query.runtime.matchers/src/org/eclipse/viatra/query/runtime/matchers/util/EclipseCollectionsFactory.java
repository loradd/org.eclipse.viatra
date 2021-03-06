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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.viatra.query.runtime.matchers.util.CollectionsFactory.BucketType;
import org.eclipse.viatra.query.runtime.matchers.util.CollectionsFactory.ICollectionsFramework;

/**
 * @author Gabor Bergmann
 * @since 1.7
 * @noreference This class is not intended to be referenced by clients.
 */
public class EclipseCollectionsFactory implements ICollectionsFramework {

    @Override
    public <K, V> Map<K, V> createMap() {
        return Maps.mutable.empty();
    }
    
    @Override
    public <K, V> Map<K, V> createMap(Map<K, V> initial) {
        MutableMap<K, V> result = Maps.mutable.ofInitialCapacity(initial.size());
        result.putAll(initial);
        return result;
    }

    @Override
    public <E> Set<E> createSet() {
        return Sets.mutable.empty();
    }

    @Override
    public <E> Set<E> createSet(Collection<E> initial) {
        return Sets.mutable.ofAll(initial);
    }
    
    @Override
    public <T> IMultiset<T> createMultiset() {
        return new EclipseCollectionsMultiset<T>();
    }
    
    @Override
    public <T> IDeltaBag<T> createDeltaBag() {
        return new EclipseCollectionsDeltaBag<T>();
    }
    
    @Override
    public <O> List<O> createObserverList() {
        return new ArrayList<O>(1); // keep concurrent modification exceptions for error detection
        // Lists.mutable.empty

    }
    
    @Override
    public <K, V> IMultiLookup<K, V> createMultiLookup(
            Class<? super K> fromKeys, 
            BucketType toBuckets,
            Class<? super V> ofValues) 
    {
        boolean longKeys = Long.class.equals(fromKeys);
        boolean objectKeys = Object.class.equals(fromKeys);
        if (! (longKeys || objectKeys)) throw new IllegalArgumentException(fromKeys.getName());
        boolean longValues = Long.class.equals(ofValues);
        boolean objectValues = Object.class.equals(ofValues);
        if (! (longValues || objectValues)) throw new IllegalArgumentException(ofValues.getName());
        
        if (longKeys) { // K == java.lang.Long
            if (longValues) { // V == java.lang.Long
                switch(toBuckets) {
                case MULTISETS:
                    return (IMultiLookup<K, V>) new EclipseCollectionsMultiLookup.FromLongs.ToMultisets.OfLongs();
                case SETS:
                    return (IMultiLookup<K, V>) new EclipseCollectionsMultiLookup.FromLongs.ToSets.OfLongs();
                default:
                    throw new IllegalArgumentException(toBuckets.toString());
                }
            } else { // objectValues
                switch(toBuckets) {
                case MULTISETS:
                    return (IMultiLookup<K, V>) new EclipseCollectionsMultiLookup.FromLongs.ToMultisets.OfObjects();
                case SETS:
                    return (IMultiLookup<K, V>) new EclipseCollectionsMultiLookup.FromLongs.ToSets.OfObjects();
                default:
                    throw new IllegalArgumentException(toBuckets.toString());
                }
            }
        } else { // objectKeys
            if (longValues) { // V == java.lang.Long
                switch(toBuckets) {
                case MULTISETS:
                    return (IMultiLookup<K, V>) new EclipseCollectionsMultiLookup.FromObjects.ToMultisets.OfLongs();
                case SETS:
                    return (IMultiLookup<K, V>) new EclipseCollectionsMultiLookup.FromObjects.ToSets.OfLongs();
                default:
                    throw new IllegalArgumentException(toBuckets.toString());
                }
            } else { // objectValues
                switch(toBuckets) {
                case MULTISETS:
                    return (IMultiLookup<K, V>) new EclipseCollectionsMultiLookup.FromObjects.ToMultisets.OfObjects();
                case SETS:
                    return (IMultiLookup<K, V>) new EclipseCollectionsMultiLookup.FromObjects.ToSets.OfObjects();
                default:
                    throw new IllegalArgumentException(toBuckets.toString());
                }
            }
        }
    }
    


}
