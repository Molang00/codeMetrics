package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import java.util.HashSet;
import java.util.Set;

public class HalsteadVolumeMetric extends Metric{
    DistinctOperandMetric distinctOperandMetric;
    DistinctOperatorMetric distinctOperatorMetric;
    OperandMetric operandMetric;
    OperatorMetric operatorMetric;
    public HalsteadVolumeMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        distinctOperandMetric = new DistinctOperandMetric(name+"_dopd");
        distinctOperatorMetric = new DistinctOperatorMetric(name+"_dopr");
        operandMetric = new OperandMetric(name+"_opd");
        operatorMetric = new OperatorMetric(name+"_opr");
    }
    public HalsteadVolumeMetric(String name) {
        super(name);
        distinctOperandMetric = new DistinctOperandMetric(name+"_dopd");
        distinctOperatorMetric = new DistinctOperatorMetric(name+"_dopr");
        operandMetric = new OperandMetric(name+"_opd");
        operatorMetric = new OperatorMetric(name+"_opr");
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        double volume;
        if(target==null)
        {
            System.out.println("No class for calculation!");
            return lastResult;
        }

        //n1, n2: count of unique operators, operands
        //N1, N2: count of all operators, operands
        double n1, n2, N1, N2;
        n1 = distinctOperatorMetric.calculate(project, target);
        n2 = distinctOperandMetric.calculate(project, target);
        N1 = operatorMetric.calculate(project, target);
        N2 = operandMetric.calculate(project, target);

        volume = (N1+N2) * ( Math.log(n1+n2) / Math.log(2) );

        return lastResult = volume;
    }
}
