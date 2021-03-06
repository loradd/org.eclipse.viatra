/*******************************************************************************
 * Copyright (c) 2004-2008 Gabor Bergmann and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Gabor Bergmann - initial API and implementation
 *******************************************************************************/

package org.eclipse.viatra.query.runtime.rete.index;

import java.util.Collection;
import java.util.List;

import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.rete.network.Receiver;
import org.eclipse.viatra.query.runtime.rete.network.ReteContainer;
import org.eclipse.viatra.query.runtime.rete.network.Supplier;

/**
 * Defines a trivial indexer that identically projects the contents of a memory-equipped node, and can therefore save
 * space. Can only exist in connection with a memory, and must be operated by another node. Do not attach parents
 * directly!
 * 
 * @noimplement Rely on the provided implementations
 * @noreference Use only via standard Node and Indexer interfaces
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @author Gabor Bergmann
 */

public class MemoryIdentityIndexer extends IdentityIndexer {
    Collection<Tuple> memory;

    /**
     * @param reteContainer
     * @param tupleWidth
     *            the width of the tuples of memoryNode
     * @param memory
     *            the memory whose contents are to be identity-indexed
     * @param parent
     *            the parent node that owns the memory
     */
    public MemoryIdentityIndexer(ReteContainer reteContainer, int tupleWidth, Collection<Tuple> memory, Supplier parent, 
            Receiver activeNode, List<ListenerSubscription> sharedSubscriptionList) {
        super(reteContainer, tupleWidth, parent, activeNode, sharedSubscriptionList);
        this.memory = memory;
    }

    /**
     * @return
     */
    @Override
    protected Collection<Tuple> getTuples() {
        return memory;
    }

}
