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
package org.eclipse.viatra.query.patternlanguage.emf.ui.labeling;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.viatra.query.patternlanguage.emf.EMFPatternLanguageScopeHelper;
import org.eclipse.viatra.query.patternlanguage.emf.vql.ClassType;
import org.eclipse.viatra.query.patternlanguage.emf.vql.EClassifierConstraint;
import org.eclipse.viatra.query.patternlanguage.emf.vql.EnumValue;
import org.eclipse.viatra.query.patternlanguage.emf.vql.PackageImport;
import org.eclipse.viatra.query.patternlanguage.emf.vql.PatternModel;
import org.eclipse.viatra.query.patternlanguage.emf.vql.ReferenceType;
import org.eclipse.viatra.query.patternlanguage.emf.helper.PatternLanguageHelper;
import org.eclipse.viatra.query.patternlanguage.emf.vql.AggregatedValue;
import org.eclipse.viatra.query.patternlanguage.emf.vql.BoolValue;
import org.eclipse.viatra.query.patternlanguage.emf.vql.CheckConstraint;
import org.eclipse.viatra.query.patternlanguage.emf.vql.CompareConstraint;
import org.eclipse.viatra.query.patternlanguage.emf.vql.CompareFeature;
import org.eclipse.viatra.query.patternlanguage.emf.vql.FunctionEvaluationValue;
import org.eclipse.viatra.query.patternlanguage.emf.vql.ListValue;
import org.eclipse.viatra.query.patternlanguage.emf.vql.NumberValue;
import org.eclipse.viatra.query.patternlanguage.emf.vql.PathExpressionConstraint;
import org.eclipse.viatra.query.patternlanguage.emf.vql.PathExpressionHead;
import org.eclipse.viatra.query.patternlanguage.emf.vql.PathExpressionTail;
import org.eclipse.viatra.query.patternlanguage.emf.vql.Pattern;
import org.eclipse.viatra.query.patternlanguage.emf.vql.PatternBody;
import org.eclipse.viatra.query.patternlanguage.emf.vql.PatternCall;
import org.eclipse.viatra.query.patternlanguage.emf.vql.PatternCompositionConstraint;
import org.eclipse.viatra.query.patternlanguage.emf.vql.StringValue;
import org.eclipse.viatra.query.patternlanguage.emf.vql.ValueReference;
import org.eclipse.viatra.query.patternlanguage.emf.vql.VariableValue;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.XNumberLiteral;
import org.eclipse.xtext.xbase.typesystem.computation.NumberLiterals;
import org.eclipse.xtext.xbase.ui.labeling.XbaseLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class EMFPatternLanguageLabelProvider extends XbaseLabelProvider {

    @Inject 
    private NumberLiterals literals;
    
    @Inject
    public EMFPatternLanguageLabelProvider(AdapterFactoryLabelProvider delegate) {
        super(delegate);
    }

    @Override
    protected Object doGetImage(Object element) {
        if (element instanceof PackageImport) {
            super.doGetImage(((PackageImport) element).getEPackage());
        }
        return super.doGetImage(element);
    }
    
    String text(PatternModel model) {
        return "Pattern Model";
    }

    String text(PackageImport ele) {
        String name = (ele.getEPackage() != null) ? ele.getEPackage().getName() : "«package»";
        return String.format("import %s", name);
    }

    String text(Pattern pattern) {
        return String.format("pattern %s/%d", pattern.getName(), pattern.getParameters().size());
    }

    String text(PatternBody ele) {
        return String.format("body #%d", ((Pattern) ele.eContainer()).getBodies().indexOf(ele) + 1);
    }

    String text(EClassifierConstraint constraint) {
        String typename = ((ClassType) constraint.getType()).getClassname().getName();
        return String.format("%s (%s)", typename, constraint.getVar().getVar());
    }

    String text(CompareConstraint constraint) {
        CompareFeature feature = constraint.getFeature();
        String op = "<???>";
        if (feature == CompareFeature.EQUALITY) {
            op = "==";
        } else if (feature == CompareFeature.INEQUALITY) {
            op = "!=";
        }
        String left = getValueText(constraint.getLeftOperand());
        String right = getValueText(constraint.getRightOperand());
        return String.format("%s %s %s", left, op, right);
    }

    String text(PatternCompositionConstraint constraint) {
        String modifiers = (constraint.isNegative()) ? "neg " : "";
        return String.format("%s%s", modifiers, text(constraint.getCall()));
    }

    String text(PatternCall call) {
        String transitiveOp = call.isTransitive() ? "+" : "";
        final String name = call.getPatternRef() == null ? "<null>" : call.getPatternRef().getName();
        return String.format("find %s/%d%s", name, call.getParameters().size(), transitiveOp);
    }

    String text(PathExpressionConstraint constraint) {
        String typename = ((ClassType) constraint.getHead().getType()).getClassname().getName();
        String varName = (constraint.getHead().getSrc() != null) ? constraint.getHead().getSrc().getVar() : "«type»";
        return String.format("%s (%s)", typename, varName);
    }

    String text(CheckConstraint constraint) {
        return "check()";
    }
    
    String text(FunctionEvaluationValue eval) {
        return "eval()";
    }

    String text(AggregatedValue aggregate) {
        String aggregator = getAggregatorText(aggregate);
        String call = text(aggregate.getCall());
        return String.format(/* "aggregate %s %s" */"%s %s", aggregator, call);
    }

    String text(PathExpressionTail tail) {
        EStructuralFeature refname = ((ReferenceType) tail.getType()).getRefname();
        String type = (refname != null) ? refname.getName() : "«type»";
        String varName = "";
        if (tail.getTail() == null) {
            PathExpressionHead head = EMFPatternLanguageScopeHelper.getExpressionHead(tail);
            varName = String.format("(%s)", getValueText(head.getDst()));
        }
        return String.format("%s %s", type, varName);
    }

    // String text(ComputationValue computation) {
    //
    // }

    private String getAggregatorText(AggregatedValue aggregator) {
        return aggregator.getAggregator().getSimpleName();
    }

    String getValueText(ValueReference ref) {
        if (ref instanceof VariableValue) {
            return ((VariableValue) ref).getValue().getVar();
        } else if (ref instanceof NumberValue) {
            XNumberLiteral literal = ((NumberValue) ref).getValue();
            return literals.toJavaLiteral(literal);
        } else if (ref instanceof BoolValue) {
            return Boolean.toString(PatternLanguageHelper.getValue(ref, Boolean.class));
        } else if (ref instanceof ListValue) {
            EList<ValueReference> values = ((ListValue) ref).getValues();
            List<String> valueStrings = new ArrayList<>();
            for (ValueReference valueReference : values) {
                valueStrings.add(getValueText(valueReference));
            }
            return "{" + Strings.concat(", ", valueStrings) + "}";
        } else if (ref instanceof StringValue) {
            return "\"" + ((StringValue) ref).getValue() + "\"";
        } else if (ref instanceof EnumValue) {
            EnumValue enumVal = (EnumValue) ref;
            if (enumVal.getLiteral() == null || enumVal.getLiteral().getLiteral() == null) {
                return "";
            }
            String enumName;
            if (enumVal.getEnumeration() != null) {
                enumName = enumVal.getEnumeration().getName();
            } else {
                enumName = enumVal.getLiteral().getEEnum().getName();
            }
            return enumName + "::" + enumVal.getLiteral().getLiteral();
        } else if (ref instanceof AggregatedValue) {
            return text((AggregatedValue) ref);
        } else if (ref instanceof FunctionEvaluationValue) {
            return text((FunctionEvaluationValue) ref);
        }
        return null;
    }

    /*
     * //Labels and icons can be computed like this:
     * 
     * String text(MyModel ele) { return "my "+ele.getName(); }
     * 
     * String image(MyModel ele) { return "MyModel.gif"; }
     */
}
