/*******************************************************************************
 * Copyright (c) 2010-2014, Zoltan Ujhelyi, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zoltan Ujhelyi - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.patternlanguage.emf.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.viatra.query.runtime.exception.ViatraQueryException;

/**
 * Generic classloader implementation - returns the base classloader. If using an environment with multiple
 * classloaders, this provider should not be used.
 * 
 * @author Zoltan Ujhelyi
 * 
 */
public class SimpleClassLoaderProvider implements IClassLoaderProvider {

    @Override
    public ClassLoader getClassLoader(EObject ctx) {
        ClassLoader l = ctx.getClass().getClassLoader();
        if (l == null) {
            throw new ViatraQueryException(String.format("No classloader found for context object %s.", ctx), "No classloader found.");
        }
        return l;
    }
}
