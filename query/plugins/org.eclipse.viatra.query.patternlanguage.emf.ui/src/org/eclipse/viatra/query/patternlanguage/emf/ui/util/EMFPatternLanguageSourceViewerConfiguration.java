/*******************************************************************************
 * Copyright (c) 2010-2018, Zoltan Ujhelyi, IncQuery Labs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zoltan Ujhelyi - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.patternlanguage.emf.ui.util;

import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.viatra.query.patternlanguage.emf.ui.labeling.EMFPatternLanguageEObjectHover;
import org.eclipse.xtext.ui.editor.XtextSourceViewerConfiguration;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Zoltan Ujhelyi
 * @since 2.0
 */
public class EMFPatternLanguageSourceViewerConfiguration extends XtextSourceViewerConfiguration {

    @Inject
    private Provider<EMFPatternLanguageEObjectHover> textHoverProvider;
    
    @Override
    public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
        EMFPatternLanguageEObjectHover hover = textHoverProvider.get();
        hover.setSourceViewer(sourceViewer);
        return hover;
    }

    

}
