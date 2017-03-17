/**
 * Copyright (c) 2010-2017, Gabor Bergmann, Abel Hegedus, Zoltan Ujhelyi, Peter Lunk, Istvan Rath, Daniel Varro, IncQuery Labs Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Gabor Bergmann, Abel Hegedus, Zoltan Ujhelyi - initial API and implementation
 *   Peter Lunk - SerializedJavaObjectSubstitution
 */
package org.eclipse.viatra.query.testing.snapshot;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Miscellaneous Substitution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.viatra.query.testing.snapshot.MiscellaneousSubstitution#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.viatra.query.testing.snapshot.SnapshotPackage#getMiscellaneousSubstitution()
 * @model
 * @generated
 */
public interface MiscellaneousSubstitution extends MatchSubstitutionRecord {
    /**
     * Returns the value of the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Value</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Value</em>' attribute.
     * @see #setValue(Object)
     * @see org.eclipse.viatra.query.testing.snapshot.SnapshotPackage#getMiscellaneousSubstitution_Value()
     * @model
     * @generated
     */
    Object getValue();

    /**
     * Sets the value of the '{@link org.eclipse.viatra.query.testing.snapshot.MiscellaneousSubstitution#getValue <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Value</em>' attribute.
     * @see #getValue()
     * @generated
     */
    void setValue(Object value);

} // MiscellaneousSubstitution
