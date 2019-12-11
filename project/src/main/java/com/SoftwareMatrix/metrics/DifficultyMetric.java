package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public class DifficultyMetric extends Metric {
    DistinctOperandMetric distinctOperandMetric;
    DistinctOperatorMetric distinctOperatorMetric;
    OperandMetric operandMetric;
    DifficultyMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        distinctOperandMetric = new DistinctOperandMetric(name+"_dopd");
        distinctOperatorMetric = new DistinctOperatorMetric(name+"_dopr");
        operandMetric = new OperandMetric(name+"_opd");
    }

    DifficultyMetric(String name) {
        super(name);
        distinctOperandMetric = new DistinctOperandMetric(name+"_dopd");
        distinctOperatorMetric = new DistinctOperatorMetric(name+"_dopr");
        operandMetric = new OperandMetric(name+"_opd");
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        double difficulty;
        if(target==null)
        {
            System.out.println("No class for calculation!");
            return lastResult;
        }

        //n1, n2: count of unique operators, operands
        //N1, N2: count of all operators, operands
        double n1, n2, N2;
        n1 = distinctOperatorMetric.calculate(project, target);
        n2 = distinctOperandMetric.calculate(project, target);
        N2 = operandMetric.calculate(project, target);

        if(n2==0)
            return 0;
        difficulty = (n1/2) * (N2/n2);
        lastResult = difficulty;

        return difficulty;
    }
}
