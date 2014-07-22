/*******************************************************************************
 * Copyright (c) 2010-2012, Zoltan Ujhelyi, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zoltan Ujhelyi - initial API and implementation
 *******************************************************************************/
package org.eclipse.incquery.patternlanguage.emf.helper;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.PackageImport;
import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.PatternModel;
import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.XImportSection;
import org.eclipse.incquery.runtime.api.IPatternMatch;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Helper functions for dealing with the EMF Pattern Language models.
 * 
 * @author Zoltan Ujhelyi
 * 
 */
public class EMFPatternLanguageHelper {

    /**
     * Initializes a new list of package imports defined in a selected pattern model
     * 
     * @param model
     * @return
     */
    public static List<PackageImport> getAllPackageImports(PatternModel model) {
        return Lists.newArrayList(getPackageImportsIterable(model));
    }

    /**
     * Returns an iterable of package imports in a selected pattern model. If an import package is an unresolvable
     * proxy, it is omitted.
     * 
     * @param model
     * @return
     */
    public static Iterable<PackageImport> getPackageImportsIterable(PatternModel model) {
        XImportSection imports = model.getImportPackages();
        if (imports == null) {
        	return ImmutableList.of();
        }
		return Iterables.filter(imports.getPackageImport(),
                new Predicate<PackageImport>() {

                    @Override
                    public boolean apply(PackageImport pImport) {
                        return !pImport.eIsProxy();
                    }
                });
    }

    /**
     * Get the structural feature with the given name of the given object.
     *
     * @param o
     *            the object (must be an EObject)
     * @param featureName
     *            the name of the feature
     * @return the EStructuralFeature of the object or null if it can not be found
     */
    public static EStructuralFeature getFeature(Object o, String featureName) {
        if (o instanceof EObject) {
            EStructuralFeature feature = ((EObject) o).eClass().getEStructuralFeature(featureName);
            return feature;
        }
        return null;
    }

    /**
     * Returns the message for the given match using the given format. The format 
     * string can refer to the value of parameter A of the match with $A$ and even access 
     * features of A (if it's an EObject), e.g. $A.id$.
     * 
     * <p/> If the selected parameter does not exist, the string "[no such parameter]" is added

     * <p/> If no feature is defined, but A has a feature called "name", then its value is used.
     * 
     * <p/> If the selected feature does not exist, A.toString() is added. 
     * 
     * <p/> If the selected feature is null, the string "null" is added.
     */
    public static String getMessage(IPatternMatch match, String messageFormat) {
        String[] tokens = messageFormat.split("\\$");
        StringBuilder newText = new StringBuilder();
    
        for (int i = 0; i < tokens.length; i++) {
            if (i % 2 == 0) {
                newText.append(tokens[i]);
            } else {
                String[] objectTokens = tokens[i].split("\\.");
                if (objectTokens.length > 0) {
                    Object o = null;
                    EStructuralFeature feature = null;
    
                    if (objectTokens.length == 1) {
                        o = match.get(objectTokens[0]);
                        feature = getFeature(o, "name");
                    }
                    if (objectTokens.length == 2) {
                        o = match.get(objectTokens[0]);
                        feature = getFeature(o, objectTokens[1]);
                    }
    
                    if (o != null && feature != null) {
                        Object value = ((EObject) o).eGet(feature);
                        if (value != null) {
                            newText.append(value.toString());
                        } else {
                            newText.append("null");
                        }
                    } else if (o != null) {
                        newText.append(o.toString());
                    }
                } else {
                    newText.append("[no such parameter]");
                }
            }
        }
    
        return newText.toString();
    }
}