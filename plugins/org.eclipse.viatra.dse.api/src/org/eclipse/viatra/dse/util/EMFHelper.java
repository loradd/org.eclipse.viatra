/*******************************************************************************
 * Copyright (c) 2010-2014, Miklos Foldenyi, Andras Szabolcs Nagy, Abel Hegedus, Akos Horvath, Zoltan Ujhelyi and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *   Miklos Foldenyi - initial API and implementation
 *   Andras Szabolcs Nagy - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.dse.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * This class contains static helper methods.
 * @author Andras Szabolcs Nagy
 */
public final class EMFHelper {

    private static final Logger logger = Logger.getLogger(EMFHelper.class);

    private EMFHelper() {
    }

    /**
     * Creates an {@link EditingDomain} over the given {@link EObject}.
     * @param root
     * @return
     */
    public static EditingDomain createEditingDomain(EObject root) {
        // TODO maybe there is already a ted on the eobject
        EditingDomain domain = new AdapterFactoryEditingDomain(null,new BasicCommandStack());
        registerExtensionForXmiSerializer("xmi");
        Resource createResource = domain.getResourceSet().createResource(URI.createFileURI("dummy.xmi"));
        domain.getCommandStack().execute(new AddCommand(domain, createResource.getContents(), root));
        return domain;
    }

    /**
     * Saves the EMF model into the given file. An {@link XMIResourceFactoryImpl} will be registered if not already.
     * @param root The root of model.
     * @param name The name or path of the file. 
     * @param ext The extension of the file.
     */
    public static void serializeModel(EObject root, String name, String ext) {

        registerExtensionForXmiSerializer(ext);

        ResourceSet resSet = new ResourceSetImpl();
        URI uri = URI.createFileURI(name + "." + ext);
        Resource resource = resSet.createResource(uri);

        resource.getContents().add(root);

        try {
            resource.save(Collections.EMPTY_MAP);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Registers an {@link XMIResourceFactoryImpl} for the given extension.
     * @param ext The extension as a String.
     */
    public static void registerExtensionForXmiSerializer(String ext) {
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        if (m.get(ext) == null) {
            m.put(ext, new XMIResourceFactoryImpl());
        }
    }

    /**
     * Clones the given model.
     * @param root The root container object of the model.
     * @return The cloned model.
     */
    public static EObject clone(EObject root) {
        Copier copier = new Copier();
        EObject result = copier.copy(root);

        copier.copyReferences();
        return result;

    }

    /**
     * Collects all the classes and references from the given {@link EPackage}s.
     * @param metaModelPackages
     * @return
     * 
     * @deprecated Use {@link #getAllMetaModelElements(Set)} instead.
     */
    public static List<EModelElement> getClassesAndReferences(Collection<EPackage> metaModelPackages) {
        List<EModelElement> result = new ArrayList<EModelElement>();
        for (EPackage ePackage : metaModelPackages) {
            // Add all classes and references from the package
            for (EClassifier eClassifier : ePackage.getEClassifiers()) {
                if (eClassifier instanceof EClass) {
                    EClass eClass = ((EClass) eClassifier);
                    addToListIfNotContains(result, eClass);
                    for (EClass c : eClass.getEAllSuperTypes()) {
                        addToListIfNotContains(result, c);
                    }
                    for (EReference eReference : eClass.getEAllReferences()) {
                        addToListIfNotContains(result, eReference);
                    }
                }
            }
        }
        return result;
    }

    public static class ENamedElementComparator implements Comparator<ENamedElement> {
        @Override
        public int compare(ENamedElement eClass1, ENamedElement eClass2) {
            return eClass1.getName().compareTo(eClass2.getName());
        }
    }

    /**
     * This class is used to store
     * <ul>
     * <li>{@link EClass}es,</li>
     * <li>{@link EAttribute}s,</li>
     * <li>{@link EReference}s,</li>
     * <li>EAttributes by EClasses,</li>
     * <li>EReferences by EClasses</li>
     * </ul>
     * for a given set of {@link EPackage}s.
     *
     */
    public static class MetaModelElements {
        public Set<EPackage> metaModelPackages;
        public Set<EClass> classes;
        public Set<EAttribute> attributes;
        public Set<EReference> references;
        public Map<EClass, Set<EAttribute>> attributesOfClass;
        public Map<EClass, Set<EReference>> referencesOfClass;
    }

    /**
     * Traverses the full metamodel on the given {@link EPackage}s and returns all the classes, attributes and
     * references it contains.
     * 
     * @param metaModelPackages
     *            The set of {@link EPackage}s.
     * @return A {@link MetaModelElements} instance containing the metamodel elements.
     */
    public static MetaModelElements getAllMetaModelElements(Set<EPackage> metaModelPackages) {
        return getMetaModelElements(metaModelPackages, true, true, true);
    }

    /**
     * Return a {@link MetaModelElements} instance populated with its {@link MetaModelElements#classes}.
     * 
     * @param metaModelPackages
     *            The set of {@link EPackage}s.
     * @return AA {@link MetaModelElements} instance.
     */
    public static MetaModelElements getClasses(Set<EPackage> metaModelPackages) {
        return getMetaModelElements(metaModelPackages, true, true, true);
    }

    /**
     * Return a {@link MetaModelElements} instance populated with its {@link MetaModelElements#references} and
     * {@link MetaModelElements#referencesOfClass}.
     * 
     * @param metaModelPackages
     *            The set of {@link EPackage}s.
     * @return AA {@link MetaModelElements} instance.
     */
    public static MetaModelElements getReferences(Set<EPackage> metaModelPackages) {
        return getMetaModelElements(metaModelPackages, true, true, true);
    }

    /**
     * Return a {@link MetaModelElements} instance populated with its {@link MetaModelElements#attributes} and
     * {@link MetaModelElements#attributesOfClass}.
     * 
     * @param metaModelPackages
     *            The set of {@link EPackage}s.
     * @return AA {@link MetaModelElements} instance.
     */
    public static MetaModelElements getAttrbiutes(Set<EPackage> metaModelPackages) {
        return getMetaModelElements(metaModelPackages, true, true, true);
    }

    private static MetaModelElements getMetaModelElements(Set<EPackage> metaModelPackages, boolean getClasses,
            boolean getReferences, boolean getAttrbiutes) {

        Comparator<ENamedElement> comparator = new ENamedElementComparator();

        MetaModelElements result = new MetaModelElements();
        result.metaModelPackages = metaModelPackages;
        if (getClasses) {
            result.classes = new TreeSet<EClass>(comparator);
        }
        if (getReferences) {
            result.references = new HashSet<EReference>();
            result.referencesOfClass = new HashMap<EClass, Set<EReference>>();
        }
        if (getAttrbiutes) {
            result.attributes = new HashSet<EAttribute>();
            result.attributesOfClass = new HashMap<EClass, Set<EAttribute>>();
        }
        for (EPackage ePackage : metaModelPackages) {
            for (EClassifier eClassifier : ePackage.getEClassifiers()) {
                if (eClassifier instanceof EClass) {
                    EClass eClass = ((EClass) eClassifier);
                    if (getClasses) {
                        result.classes.add(eClass);
                    }
                    if (getReferences) {
                        result.referencesOfClass.put(eClass, new TreeSet<EReference>(comparator));
                        for (EReference eReference : eClass.getEAllReferences()) {
                            result.references.add(eReference);
                            result.referencesOfClass.get(eClass).add(eReference);
                        }
                    }
                    if (getAttrbiutes) {
                        result.attributesOfClass.put(eClass, new TreeSet<EAttribute>(comparator));
                        for (EAttribute eAttribute : eClass.getEAllAttributes()) {
                            result.attributes.add(eAttribute);
                            result.attributesOfClass.get(eClass).add(eAttribute);
                        }
                    }
                }
            }
        }
        return result;
    }

    private static <T, E extends T> void addToListIfNotContains(List<T> list, E element) {
        if (!list.contains(element)) {
            list.add(element);
        }
    }
}
