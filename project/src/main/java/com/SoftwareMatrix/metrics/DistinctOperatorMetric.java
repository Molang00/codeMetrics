package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculate the Distinct Operator Metric.
 * Distinct Operator is am important parameter in calculating Halstead Metric.
 */
public class DistinctOperatorMetric extends Metric {
    /**
     * Initialize DistinctOperatorMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public DistinctOperatorMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public DistinctOperatorMetric(String name) {
        super(name);
    }

    /**
     * Calculate Distinct Operator Metric.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of Distinct Operator Metric.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        Set<PsiElement> operators;
        Set<String> distinctOperators = new HashSet<>();

        if(target == null)
            return lastResult;

        operators = ParseAdapter.getOperators(target);
        for (PsiElement value : operators) {
            distinctOperators.add(value.toString());
        }

        lastResult = distinctOperators.size();
        return lastResult;
    }
}
