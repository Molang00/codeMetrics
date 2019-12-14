package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculate the Distinct Operand Metric.
 * Distinct Operand is am important parameter in calculating Halstead Metric.
 */
public class DistinctOperandMetric extends Metric {
    /**
     * Initialize DistinctOperandMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public DistinctOperandMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public DistinctOperandMetric(String name) {
        super(name);
    }

    /**
     * Calculate Distinct Operand Metric.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of Distinct Operand Metric.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        Set<String> distinctOperands = new HashSet<>();

        if(target == null)
            return lastResult;

        Set<PsiElement> operands = ParseAdapter.getOperands(target);
        for (PsiElement o : operands) {
            distinctOperands.add(o.toString());
        }

        lastResult = distinctOperands.size();
        return lastResult;
    }
}
