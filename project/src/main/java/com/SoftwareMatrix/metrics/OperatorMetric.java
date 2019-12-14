package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

/**
 * Calculates the number of operators in a class
 */

public class OperatorMetric extends Metric {
    /**
     * Initialize OperatorMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public OperatorMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public OperatorMetric(String name) {
        super(name);
    }

    /**
     * calculates the number of operators in a metric
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return OperatorMetric
     */

    @Override
    public double calculate(Project project, PsiClass target) {
        if(target==null)
            return lastResult;

        lastResult = ParseAdapter.getOperators(target).size();
        return lastResult;
    }
}
