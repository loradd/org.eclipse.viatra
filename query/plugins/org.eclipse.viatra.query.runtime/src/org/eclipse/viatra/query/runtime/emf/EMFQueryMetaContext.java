/*******************************************************************************
 * Copyright (c) 2010-2015, Bergmann Gabor, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Bergmann Gabor - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.query.runtime.emf;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.viatra.query.runtime.emf.types.BaseEMFTypeKey;
import org.eclipse.viatra.query.runtime.emf.types.EClassTransitiveInstancesKey;
import org.eclipse.viatra.query.runtime.emf.types.EClassUnscopedTransitiveInstancesKey;
import org.eclipse.viatra.query.runtime.emf.types.EDataTypeInSlotsKey;
import org.eclipse.viatra.query.runtime.emf.types.EStructuralFeatureInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.context.AbstractQueryMetaContext;
import org.eclipse.viatra.query.runtime.matchers.context.IInputKey;
import org.eclipse.viatra.query.runtime.matchers.context.InputKeyImplication;
import org.eclipse.viatra.query.runtime.matchers.context.common.JavaTransitiveInstancesKey;

/**
 * The meta context information for EMF scopes.
 * 
 * <p> The runtime context may specialize answers with a given scope. 
 * In a static context, a conservative default version ({@link #DEFAULT}) can be used instead. 
 * 
 * <p> TODO generics? 
 * @author Bergmann Gabor
 *
 */
public final class EMFQueryMetaContext extends AbstractQueryMetaContext {
    
    /**
     * Default static instance that only makes conservative assumptions that are valid for any scope.
     * @since 1.6
     */
    public static final EMFQueryMetaContext DEFAULT = new EMFQueryMetaContext(false, true);

    
    private static final EClass EOBJECT_CLASS = 
            EcorePackage.eINSTANCE.getEObject();
    private static final EClassTransitiveInstancesKey EOBJECT_SCOPED_KEY = 
            new EClassTransitiveInstancesKey(EOBJECT_CLASS);
    private static final EClassUnscopedTransitiveInstancesKey EOBJECT_UNSCOPED_KEY = 
            new EClassUnscopedTransitiveInstancesKey(EOBJECT_CLASS);

    private boolean assumeNonDangling;
    private boolean subResourceScopeSplit;

    /**
     * Instantiates a specialized meta information that is aware of scope-specific details.
     * Note that this API is not stable and thus non-public.
     * 
     * @param assumeNonDangling assumes that all cross-references are non-dangling (do not lead out of scope), no matter what
     * @param subResourceScopeSplit the scope granularity may be finer than resource-level, i.e. proxy-non-resolving references can lead out of scope
     */
    EMFQueryMetaContext(boolean assumeNonDangling, boolean subResourceScopeSplit) {
        this.assumeNonDangling = assumeNonDangling;
        this.subResourceScopeSplit = subResourceScopeSplit;
    }
    
    /**
     * Instantiates a specialized meta information that is aware of scope-specific details.
     * Note that this API is not stable and thus non-public.
     */ 
    EMFQueryMetaContext(EMFScope scope) {
        this(scope.getOptions().isDanglingFreeAssumption(), 
                scope.getScopeRoots().size()==1 && scope.getScopeRoots().iterator().next() instanceof EObject);
    }

    
    @Override
    public boolean isEnumerable(IInputKey key) {		
        ensureValidKey(key);
        return key.isEnumerable();
//		if (key instanceof JavaTransitiveInstancesKey) 
//			return false;
//		else
//			return true;
    }
    
    @Override
    public boolean canLeadOutOfScope(IInputKey key) {
        ensureValidKey(key);
        if (key instanceof EStructuralFeatureInstancesKey) {
            EStructuralFeature feature = ((EStructuralFeatureInstancesKey) key).getEmfKey();
            if (feature instanceof EReference){
                return ((EReference) feature).isResolveProxies();
            }
        }
        return false;
    }
    
    @Override
    public boolean isStateless(IInputKey key) {
        ensureValidKey(key);
        return key instanceof JavaTransitiveInstancesKey || key instanceof EClassUnscopedTransitiveInstancesKey;
    }

    @Override
    public Map<Set<Integer>, Set<Integer>> getFunctionalDependencies(IInputKey key) {
        ensureValidKey(key);
        if (key instanceof EStructuralFeatureInstancesKey) {
            EStructuralFeature feature = ((EStructuralFeatureInstancesKey) key).getEmfKey();
            final Map<Set<Integer>, Set<Integer>> result = 
                    new HashMap<Set<Integer>, Set<Integer>>();
            if (isFeatureMultiplicityToOne(feature))
                result.put(Collections.singleton(0), Collections.singleton(1));
            if (isFeatureMultiplicityOneTo(feature))
                result.put(Collections.singleton(1), Collections.singleton(0));
            return result;
        } else {
            return Collections.emptyMap();
        }
    }
    
    @Override
    public Collection<InputKeyImplication> getImplications(IInputKey implyingKey) {
        ensureValidKey(implyingKey);
        Collection<InputKeyImplication> result = new HashSet<InputKeyImplication>();

        if (implyingKey instanceof EClassTransitiveInstancesKey) {
            EClass eClass = ((EClassTransitiveInstancesKey) implyingKey).getEmfKey();

            // direct eSuperClasses
            EList<EClass> directSuperTypes = eClass.getESuperTypes();
            if (!directSuperTypes.isEmpty()) {
                for (EClass superType : directSuperTypes) {
                    final EClassTransitiveInstancesKey implied = new EClassTransitiveInstancesKey(superType);
                    result.add(new InputKeyImplication(implyingKey, implied, Arrays.asList(0)));
                }
            } else {
                if (!EOBJECT_SCOPED_KEY.equals(implyingKey)) {
                    result.add(new InputKeyImplication(implyingKey, EOBJECT_SCOPED_KEY, Arrays.asList(0)));
                }
            }
            // implies unscoped
            result.add(new InputKeyImplication(implyingKey, new EClassUnscopedTransitiveInstancesKey(eClass),
                    Arrays.asList(0)));
        } else if (implyingKey instanceof EClassUnscopedTransitiveInstancesKey) {
            EClass eClass = ((EClassUnscopedTransitiveInstancesKey) implyingKey).getEmfKey();

            // direct eSuperClasses
            EList<EClass> directSuperTypes = eClass.getESuperTypes();
            if (!directSuperTypes.isEmpty()) {
                for (EClass superType : directSuperTypes) {
                    final EClassUnscopedTransitiveInstancesKey implied = new EClassUnscopedTransitiveInstancesKey(
                            superType);
                    result.add(new InputKeyImplication(implyingKey, implied, Arrays.asList(0)));
                }
            } else {
                if (!EOBJECT_UNSCOPED_KEY.equals(implyingKey)) {
                    result.add(new InputKeyImplication(implyingKey, EOBJECT_UNSCOPED_KEY, Arrays.asList(0)));
                }
            }

        } else if (implyingKey instanceof JavaTransitiveInstancesKey) {
            Class<?> instanceClass = ((JavaTransitiveInstancesKey) implyingKey).getInstanceClass();
            if (instanceClass != null) { // resolution successful
                // direct Java superClass
                Class<?> superclass = instanceClass.getSuperclass();
                if (superclass != null) {
                    JavaTransitiveInstancesKey impliedSuper = new JavaTransitiveInstancesKey(superclass);
                    result.add(new InputKeyImplication(implyingKey, impliedSuper, Arrays.asList(0)));
                }

                // direct Java superInterfaces
                for (Class<?> superInterface : instanceClass.getInterfaces()) {
                    if (superInterface != null) {
                        JavaTransitiveInstancesKey impliedInterface = new JavaTransitiveInstancesKey(superInterface);
                        result.add(new InputKeyImplication(implyingKey, impliedInterface, Arrays.asList(0)));
                    }
                }
            }

        } else if (implyingKey instanceof EStructuralFeatureInstancesKey) {
            EStructuralFeature feature = ((EStructuralFeatureInstancesKey) implyingKey).getEmfKey();

            // source and target type
            final EClass sourceType = featureSourceType(feature);
            final EClassTransitiveInstancesKey impliedSource = new EClassTransitiveInstancesKey(sourceType);
            final EClassifier targetType = featureTargetType(feature);
            final IInputKey impliedTarget;
            if (feature instanceof EReference) {
                EReference reference = (EReference) feature;

                // Is it possible that this edge is dangling, i.e. its target lies outside of the scope?
                // Unless non-dangling is globally assumed,
                // proxy-resolving references (incl. containment) might point to proxies and are thus considered unsafe.
                // Additionally, if the scope is sub-resource (containment subtree of object),
                // all non-containment edges are also unsafe.
                // Note that in case of cross-resource containment,
                // the scope includes the contained object even if it is in a foreign resource.
                boolean danglingPossible = (!assumeNonDangling)
                        && (reference.isResolveProxies() || (subResourceScopeSplit && !reference.isContainment()));

                if (!danglingPossible) {
                    impliedTarget = new EClassTransitiveInstancesKey((EClass) targetType);
                } else {
                    impliedTarget = new EClassUnscopedTransitiveInstancesKey((EClass) targetType);
                }
            } else { // EDatatype
                impliedTarget = new EDataTypeInSlotsKey((EDataType) targetType);
            }

            result.add(new InputKeyImplication(implyingKey, impliedSource, Arrays.asList(0)));
            result.add(new InputKeyImplication(implyingKey, impliedTarget, Arrays.asList(1)));

            // opposite
            EReference opposite = featureOpposite(feature);
            if (opposite != null) {
                EStructuralFeatureInstancesKey impliedOpposite = new EStructuralFeatureInstancesKey(opposite);
                result.add(new InputKeyImplication(implyingKey, impliedOpposite, Arrays.asList(1, 0)));
            }

            // containment
            // TODO
        } else if (implyingKey instanceof EDataTypeInSlotsKey) {
            EDataType dataType = ((EDataTypeInSlotsKey) implyingKey).getEmfKey();

            // instance class of datatype
            // TODO this can have a generation gap! (could be some dynamic EMF impl or whatever)
            Class<?> instanceClass = dataType.getInstanceClass();
            if (instanceClass != null) {
                JavaTransitiveInstancesKey implied = new JavaTransitiveInstancesKey(instanceClass);
                result.add(new InputKeyImplication(implyingKey, implied, Arrays.asList(0)));
            }
        } else {
            illegalInputKey(implyingKey);
        }

        return result;
    }
    
    @Override
    public Map<InputKeyImplication, Set<InputKeyImplication>> getConditionalImplications(IInputKey implyingKey) {
        ensureValidKey(implyingKey);
        if (implyingKey instanceof EClassUnscopedTransitiveInstancesKey) {
            EClass emfKey = ((EClassUnscopedTransitiveInstancesKey) implyingKey).getEmfKey();
            
            Map<InputKeyImplication, Set<InputKeyImplication>> result = new HashMap<>();
            result.put(
                    new InputKeyImplication(implyingKey, EOBJECT_SCOPED_KEY, Arrays.asList(0)),
                    new HashSet<>(Arrays.asList(new InputKeyImplication(implyingKey, new EClassTransitiveInstancesKey(emfKey), Arrays.asList(0))))
            );
            return result;
        } else return super.getConditionalImplications(implyingKey);
    }
    
    @Override
    public Collection<InputKeyImplication> getWeakenedAlternatives(IInputKey implyingKey) {
        ensureValidKey(implyingKey);
        if (implyingKey instanceof EClassTransitiveInstancesKey) {
            EClass emfKey = ((EClassTransitiveInstancesKey) implyingKey).getEmfKey();
            
            Collection<InputKeyImplication> result = new HashSet<InputKeyImplication>();
            result.add(
                    // in some cases, filtering by the the unscoped key may be sufficient
                    new InputKeyImplication(implyingKey, new EClassUnscopedTransitiveInstancesKey(emfKey), Arrays.asList(0))
            );
            return result;
        } else return super.getWeakenedAlternatives(implyingKey);
    }

    public void ensureValidKey(IInputKey key) {
        if (! (key instanceof BaseEMFTypeKey<?>) && ! (key instanceof JavaTransitiveInstancesKey))
            illegalInputKey(key);
    }

    public void illegalInputKey(IInputKey key) {
        throw new IllegalArgumentException("The input key " + key + " is not a valid EMF input key.");
    }
    
    public boolean isFeatureMultiplicityToOne(EStructuralFeature feature) {
        return !feature.isMany();
    }

    public boolean isFeatureMultiplicityOneTo(EStructuralFeature typeObject) {
        if (typeObject instanceof EReference) {
            final EReference feature = (EReference)typeObject;
            final EReference eOpposite = feature.getEOpposite();
            return feature.isContainment() || (eOpposite != null && !eOpposite.isMany());
        } else return false;
    }
    
    public EClass featureSourceType(EStructuralFeature feature) {
        return feature.getEContainingClass();
    }
    public EClassifier featureTargetType(EStructuralFeature typeObject) {
        if (typeObject instanceof EAttribute) {
            EAttribute attribute = (EAttribute) typeObject;
            return attribute.getEAttributeType();
        } else if (typeObject instanceof EReference) {
            EReference reference = (EReference) typeObject;
            return reference.getEReferenceType();
        } else
            throw new IllegalArgumentException("typeObject has invalid type " + typeObject.getClass().getName());
    }
    public EReference featureOpposite(EStructuralFeature typeObject) {
        if (typeObject instanceof EReference) {
            EReference reference = (EReference) typeObject;
            return reference.getEOpposite();
        } else return null;
    }
    
    @Override
    public Comparator<IInputKey> getSuggestedEliminationOrdering() {
        return SUGGESTED_ELIMINATION_ORDERING;
    }

    private static final Comparator<IInputKey> SUGGESTED_ELIMINATION_ORDERING = new Comparator<IInputKey>() {
        @Override
        public int compare(IInputKey o1, IInputKey o2) {
            if (o1 instanceof EClassTransitiveInstancesKey && o2 instanceof EClassTransitiveInstancesKey) {
                // common EClass types with many instances should be eliminated before rare types
                return getRarity((EClassTransitiveInstancesKey)o1) - getRarity((EClassTransitiveInstancesKey)o2);
            } else {
                return getKeyTypeEliminationSequence(o1) - getKeyTypeEliminationSequence(o2);
            }
        }

        // The more supertypes there are, the more specialized the type
        // the more specialized the type, the rarer instances are expected to be found
        private int getRarity(EClassTransitiveInstancesKey key) {
            return key.getEmfKey().getEAllSuperTypes().size() + (EOBJECT_SCOPED_KEY.equals(key) ? 0 : 1);
        }

        // Scoped EClass transitive instance keys are attempted to be eliminated before all else
        //  so that e.g. their unscoped version can eliminate them is variable is known to be scoped
        private int getKeyTypeEliminationSequence(IInputKey o1) {
            return (o1 instanceof EClassTransitiveInstancesKey) ? -1 : 0;
        }
    };
    
}
