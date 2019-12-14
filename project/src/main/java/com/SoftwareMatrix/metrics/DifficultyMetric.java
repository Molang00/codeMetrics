package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

/**
 * Calculate the Halstead Difficulty Metric.
 * The difficulty measure is related to the difficulty of the program to write or understand,
 * e.g. when doing code review.
 */
public class DifficultyMetric extends Metric {
    private DistinctOperandMetric distinctOperandMetric;
    private DistinctOperatorMetric distinctOperatorMetric;
    private OperandMetric operandMetric;

    /**
     * Initialize DifficultyMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public DifficultyMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        distinctOperandMetric = new DistinctOperandMetric(name+"_dopd");
        distinctOperatorMetric = new DistinctOperatorMetric(name+"_dopr");
        operandMetric = new OperandMetric(name+"_opd");
    }
    public DifficultyMetric(String name) {
        super(name);
        distinctOperandMetric = new DistinctOperandMetric(name+"_dopd");
        distinctOperatorMetric = new DistinctOperatorMetric(name+"_dopr");
        operandMetric = new OperandMetric(name+"_opd");
    }

    /**
     * Calculate Difficulty Metric.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of Difficulty Metric.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        double difficulty;
        if(target==null)
        {
            return lastResult;
        }

        //n1, n2: count of unique operators, operands
        //N1, N2: count of all operators, operands
        double n1, n2, N2;
        n1 = distinctOperatorMetric.calculate(project, target);
        n2 = distinctOperandMetric.calculate(project, target);
        N2 = operandMetric.calculate(project, target);

        if(n2==0)
            return lastResult = 0;
        difficulty = (n1/2) * (N2/n2);

        return lastResult = difficulty;
    }
}
