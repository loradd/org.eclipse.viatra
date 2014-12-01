/**
 */
package org.eclipse.viatra.cep.core.metamodels.events;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.viatra.cep.core.metamodels.events.EventsFactory
 * @model kind="package"
 * @generated
 */
public interface EventsPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "events";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "cep.meta";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "org.eclipse.viatra.cep.core.metamodels";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EventsPackage eINSTANCE = org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.EventPatternImpl <em>Event Pattern</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventPatternImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getEventPattern()
     * @generated
     */
    int EVENT_PATTERN = 0;

    /**
     * The feature id for the '<em><b>Automaton</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_PATTERN__AUTOMATON = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_PATTERN__ID = 1;

    /**
     * The number of structural features of the '<em>Event Pattern</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_PATTERN_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Event Pattern</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_PATTERN_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl <em>Atomic Event Pattern</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getAtomicEventPattern()
     * @generated
     */
    int ATOMIC_EVENT_PATTERN = 1;

    /**
     * The feature id for the '<em><b>Automaton</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATOMIC_EVENT_PATTERN__AUTOMATON = EVENT_PATTERN__AUTOMATON;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATOMIC_EVENT_PATTERN__ID = EVENT_PATTERN__ID;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATOMIC_EVENT_PATTERN__TYPE = EVENT_PATTERN_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Atomic Event Pattern</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATOMIC_EVENT_PATTERN_FEATURE_COUNT = EVENT_PATTERN_FEATURE_COUNT + 1;

    /**
     * The number of operations of the '<em>Atomic Event Pattern</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATOMIC_EVENT_PATTERN_OPERATION_COUNT = EVENT_PATTERN_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.ComplexEventPatternImpl <em>Complex Event Pattern</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.ComplexEventPatternImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getComplexEventPattern()
     * @generated
     */
    int COMPLEX_EVENT_PATTERN = 2;

    /**
     * The feature id for the '<em><b>Automaton</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_PATTERN__AUTOMATON = EVENT_PATTERN__AUTOMATON;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_PATTERN__ID = EVENT_PATTERN__ID;

    /**
     * The feature id for the '<em><b>Composition Events</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_PATTERN__COMPOSITION_EVENTS = EVENT_PATTERN_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Operator</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_PATTERN__OPERATOR = EVENT_PATTERN_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Time Window</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_PATTERN__TIME_WINDOW = EVENT_PATTERN_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Complex Event Pattern</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_PATTERN_FEATURE_COUNT = EVENT_PATTERN_FEATURE_COUNT + 3;

    /**
     * The operation id for the '<em>Evaluate Parameter Bindings</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_PATTERN___EVALUATE_PARAMETER_BINDINGS__EVENT = EVENT_PATTERN_OPERATION_COUNT + 0;

    /**
     * The number of operations of the '<em>Complex Event Pattern</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_PATTERN_OPERATION_COUNT = EVENT_PATTERN_OPERATION_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.EventImpl <em>Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getEvent()
     * @generated
     */
    int EVENT = 3;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT__TYPE = 0;

    /**
     * The feature id for the '<em><b>Timestamp</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT__TIMESTAMP = 1;

    /**
     * The feature id for the '<em><b>Source</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT__SOURCE = 2;

    /**
     * The number of structural features of the '<em>Event</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Event</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.EventSourceImpl <em>Event Source</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventSourceImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getEventSource()
     * @generated
     */
    int EVENT_SOURCE = 4;

    /**
     * The number of structural features of the '<em>Event Source</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_SOURCE_FEATURE_COUNT = 0;

    /**
     * The operation id for the '<em>Get Id</em>' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_SOURCE___GET_ID = 0;

    /**
     * The number of operations of the '<em>Event Source</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EVENT_SOURCE_OPERATION_COUNT = 1;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.ComplexEventOperatorImpl <em>Complex Event Operator</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.ComplexEventOperatorImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getComplexEventOperator()
     * @generated
     */
    int COMPLEX_EVENT_OPERATOR = 5;

    /**
     * The number of structural features of the '<em>Complex Event Operator</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_OPERATOR_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Complex Event Operator</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPLEX_EVENT_OPERATOR_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.ORImpl <em>OR</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.ORImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getOR()
     * @generated
     */
    int OR = 6;

    /**
     * The number of structural features of the '<em>OR</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OR_FEATURE_COUNT = COMPLEX_EVENT_OPERATOR_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>OR</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OR_OPERATION_COUNT = COMPLEX_EVENT_OPERATOR_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.NEGImpl <em>NEG</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.NEGImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getNEG()
     * @generated
     */
    int NEG = 7;

    /**
     * The number of structural features of the '<em>NEG</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NEG_FEATURE_COUNT = COMPLEX_EVENT_OPERATOR_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>NEG</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NEG_OPERATION_COUNT = COMPLEX_EVENT_OPERATOR_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.FOLLOWSImpl <em>FOLLOWS</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.FOLLOWSImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getFOLLOWS()
     * @generated
     */
    int FOLLOWS = 8;

    /**
     * The number of structural features of the '<em>FOLLOWS</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FOLLOWS_FEATURE_COUNT = COMPLEX_EVENT_OPERATOR_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>FOLLOWS</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FOLLOWS_OPERATION_COUNT = COMPLEX_EVENT_OPERATOR_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.UNTILImpl <em>UNTIL</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.UNTILImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getUNTIL()
     * @generated
     */
    int UNTIL = 9;

    /**
     * The number of structural features of the '<em>UNTIL</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNTIL_FEATURE_COUNT = COMPLEX_EVENT_OPERATOR_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>UNTIL</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNTIL_OPERATION_COUNT = COMPLEX_EVENT_OPERATOR_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.ANDImpl <em>AND</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.ANDImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getAND()
     * @generated
     */
    int AND = 10;

    /**
     * The number of structural features of the '<em>AND</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AND_FEATURE_COUNT = COMPLEX_EVENT_OPERATOR_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>AND</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int AND_OPERATION_COUNT = COMPLEX_EVENT_OPERATOR_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.TimeWindowImpl <em>Time Window</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.TimeWindowImpl
     * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getTimeWindow()
     * @generated
     */
    int TIME_WINDOW = 11;

    /**
     * The feature id for the '<em><b>Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_WINDOW__TIME = 0;

    /**
     * The number of structural features of the '<em>Time Window</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_WINDOW_FEATURE_COUNT = 1;

    /**
     * The number of operations of the '<em>Time Window</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TIME_WINDOW_OPERATION_COUNT = 0;


    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.EventPattern <em>Event Pattern</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Event Pattern</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.EventPattern
     * @generated
     */
    EClass getEventPattern();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.viatra.cep.core.metamodels.events.EventPattern#getAutomaton <em>Automaton</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Automaton</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.EventPattern#getAutomaton()
     * @see #getEventPattern()
     * @generated
     */
    EReference getEventPattern_Automaton();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.viatra.cep.core.metamodels.events.EventPattern#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.EventPattern#getId()
     * @see #getEventPattern()
     * @generated
     */
    EAttribute getEventPattern_Id();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.AtomicEventPattern <em>Atomic Event Pattern</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Atomic Event Pattern</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.AtomicEventPattern
     * @generated
     */
    EClass getAtomicEventPattern();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.viatra.cep.core.metamodels.events.AtomicEventPattern#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.AtomicEventPattern#getType()
     * @see #getAtomicEventPattern()
     * @generated
     */
    EAttribute getAtomicEventPattern_Type();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern <em>Complex Event Pattern</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Complex Event Pattern</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern
     * @generated
     */
    EClass getComplexEventPattern();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern#getCompositionEvents <em>Composition Events</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Composition Events</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern#getCompositionEvents()
     * @see #getComplexEventPattern()
     * @generated
     */
    EReference getComplexEventPattern_CompositionEvents();

    /**
     * Returns the meta object for the containment reference '{@link org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern#getOperator <em>Operator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Operator</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern#getOperator()
     * @see #getComplexEventPattern()
     * @generated
     */
    EReference getComplexEventPattern_Operator();

    /**
     * Returns the meta object for the containment reference '{@link org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern#getTimeWindow <em>Time Window</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Time Window</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern#getTimeWindow()
     * @see #getComplexEventPattern()
     * @generated
     */
    EReference getComplexEventPattern_TimeWindow();

    /**
     * Returns the meta object for the '{@link org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern#evaluateParameterBindings(org.eclipse.viatra.cep.core.metamodels.events.Event) <em>Evaluate Parameter Bindings</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the '<em>Evaluate Parameter Bindings</em>' operation.
     * @see org.eclipse.viatra.cep.core.metamodels.events.ComplexEventPattern#evaluateParameterBindings(org.eclipse.viatra.cep.core.metamodels.events.Event)
     * @generated
     */
    EOperation getComplexEventPattern__EvaluateParameterBindings__Event();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.Event <em>Event</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Event</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.Event
     * @generated
     */
    EClass getEvent();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.viatra.cep.core.metamodels.events.Event#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.Event#getType()
     * @see #getEvent()
     * @generated
     */
    EAttribute getEvent_Type();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.viatra.cep.core.metamodels.events.Event#getTimestamp <em>Timestamp</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Timestamp</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.Event#getTimestamp()
     * @see #getEvent()
     * @generated
     */
    EAttribute getEvent_Timestamp();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.viatra.cep.core.metamodels.events.Event#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Source</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.Event#getSource()
     * @see #getEvent()
     * @generated
     */
    EReference getEvent_Source();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.EventSource <em>Event Source</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Event Source</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.EventSource
     * @generated
     */
    EClass getEventSource();

    /**
     * Returns the meta object for the '{@link org.eclipse.viatra.cep.core.metamodels.events.EventSource#getId() <em>Get Id</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the '<em>Get Id</em>' operation.
     * @see org.eclipse.viatra.cep.core.metamodels.events.EventSource#getId()
     * @generated
     */
    EOperation getEventSource__GetId();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.ComplexEventOperator <em>Complex Event Operator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Complex Event Operator</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.ComplexEventOperator
     * @generated
     */
    EClass getComplexEventOperator();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.OR <em>OR</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>OR</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.OR
     * @generated
     */
    EClass getOR();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.NEG <em>NEG</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>NEG</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.NEG
     * @generated
     */
    EClass getNEG();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.FOLLOWS <em>FOLLOWS</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>FOLLOWS</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.FOLLOWS
     * @generated
     */
    EClass getFOLLOWS();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.UNTIL <em>UNTIL</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>UNTIL</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.UNTIL
     * @generated
     */
    EClass getUNTIL();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.AND <em>AND</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>AND</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.AND
     * @generated
     */
    EClass getAND();

    /**
     * Returns the meta object for class '{@link org.eclipse.viatra.cep.core.metamodels.events.TimeWindow <em>Time Window</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Time Window</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.TimeWindow
     * @generated
     */
    EClass getTimeWindow();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.viatra.cep.core.metamodels.events.TimeWindow#getTime <em>Time</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Time</em>'.
     * @see org.eclipse.viatra.cep.core.metamodels.events.TimeWindow#getTime()
     * @see #getTimeWindow()
     * @generated
     */
    EAttribute getTimeWindow_Time();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    EventsFactory getEventsFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.EventPatternImpl <em>Event Pattern</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventPatternImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getEventPattern()
         * @generated
         */
        EClass EVENT_PATTERN = eINSTANCE.getEventPattern();

        /**
         * The meta object literal for the '<em><b>Automaton</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EVENT_PATTERN__AUTOMATON = eINSTANCE.getEventPattern_Automaton();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EVENT_PATTERN__ID = eINSTANCE.getEventPattern_Id();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl <em>Atomic Event Pattern</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.AtomicEventPatternImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getAtomicEventPattern()
         * @generated
         */
        EClass ATOMIC_EVENT_PATTERN = eINSTANCE.getAtomicEventPattern();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATOMIC_EVENT_PATTERN__TYPE = eINSTANCE.getAtomicEventPattern_Type();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.ComplexEventPatternImpl <em>Complex Event Pattern</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.ComplexEventPatternImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getComplexEventPattern()
         * @generated
         */
        EClass COMPLEX_EVENT_PATTERN = eINSTANCE.getComplexEventPattern();

        /**
         * The meta object literal for the '<em><b>Composition Events</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference COMPLEX_EVENT_PATTERN__COMPOSITION_EVENTS = eINSTANCE.getComplexEventPattern_CompositionEvents();

        /**
         * The meta object literal for the '<em><b>Operator</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference COMPLEX_EVENT_PATTERN__OPERATOR = eINSTANCE.getComplexEventPattern_Operator();

        /**
         * The meta object literal for the '<em><b>Time Window</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference COMPLEX_EVENT_PATTERN__TIME_WINDOW = eINSTANCE.getComplexEventPattern_TimeWindow();

        /**
         * The meta object literal for the '<em><b>Evaluate Parameter Bindings</b></em>' operation.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EOperation COMPLEX_EVENT_PATTERN___EVALUATE_PARAMETER_BINDINGS__EVENT = eINSTANCE.getComplexEventPattern__EvaluateParameterBindings__Event();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.EventImpl <em>Event</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getEvent()
         * @generated
         */
        EClass EVENT = eINSTANCE.getEvent();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EVENT__TYPE = eINSTANCE.getEvent_Type();

        /**
         * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EVENT__TIMESTAMP = eINSTANCE.getEvent_Timestamp();

        /**
         * The meta object literal for the '<em><b>Source</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EVENT__SOURCE = eINSTANCE.getEvent_Source();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.EventSourceImpl <em>Event Source</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventSourceImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getEventSource()
         * @generated
         */
        EClass EVENT_SOURCE = eINSTANCE.getEventSource();

        /**
         * The meta object literal for the '<em><b>Get Id</b></em>' operation.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EOperation EVENT_SOURCE___GET_ID = eINSTANCE.getEventSource__GetId();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.ComplexEventOperatorImpl <em>Complex Event Operator</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.ComplexEventOperatorImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getComplexEventOperator()
         * @generated
         */
        EClass COMPLEX_EVENT_OPERATOR = eINSTANCE.getComplexEventOperator();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.ORImpl <em>OR</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.ORImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getOR()
         * @generated
         */
        EClass OR = eINSTANCE.getOR();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.NEGImpl <em>NEG</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.NEGImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getNEG()
         * @generated
         */
        EClass NEG = eINSTANCE.getNEG();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.FOLLOWSImpl <em>FOLLOWS</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.FOLLOWSImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getFOLLOWS()
         * @generated
         */
        EClass FOLLOWS = eINSTANCE.getFOLLOWS();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.UNTILImpl <em>UNTIL</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.UNTILImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getUNTIL()
         * @generated
         */
        EClass UNTIL = eINSTANCE.getUNTIL();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.ANDImpl <em>AND</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.ANDImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getAND()
         * @generated
         */
        EClass AND = eINSTANCE.getAND();

        /**
         * The meta object literal for the '{@link org.eclipse.viatra.cep.core.metamodels.events.impl.TimeWindowImpl <em>Time Window</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.TimeWindowImpl
         * @see org.eclipse.viatra.cep.core.metamodels.events.impl.EventsPackageImpl#getTimeWindow()
         * @generated
         */
        EClass TIME_WINDOW = eINSTANCE.getTimeWindow();

        /**
         * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute TIME_WINDOW__TIME = eINSTANCE.getTimeWindow_Time();

    }

} //EventsPackage