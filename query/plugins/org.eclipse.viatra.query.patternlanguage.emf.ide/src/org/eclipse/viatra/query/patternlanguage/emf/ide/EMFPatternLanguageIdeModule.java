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
package org.eclipse.viatra.query.patternlanguage.emf.ide;

import org.eclipse.viatra.query.patternlanguage.emf.ide.highlight.EMFPatternLanguageHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;

/**
 * Use this class to register ide components.
 */
public class EMFPatternLanguageIdeModule extends AbstractEMFPatternLanguageIdeModule {
    
    @Override
    public Class<? extends ISemanticHighlightingCalculator> bindSemanticHighlightingCalculator() {
        return EMFPatternLanguageHighlightingCalculator.class;
    }
}
