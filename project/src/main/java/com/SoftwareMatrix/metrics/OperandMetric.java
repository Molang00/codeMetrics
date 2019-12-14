package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

/**
 * Calculates the number of operands in a class
 */

public class OperandMetric extends Metric {
    /**
     * Initialize OperandMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public OperandMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public OperandMetric(String name) {
        super(name);
    }

    /**
     * Calculates the number of operands in a target class
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return OperandMetric
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        if(target == null)
            return lastResult;

        lastResult = ParseAdapter.getOperands(target).size();
        return lastResult;
    }
}
