package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculate the Distinct Operator Metric. Distinct Operator is am important
 * parameter in calculating Halstead Metric.
 */
public class DistinctOperatorMetric extends Metric {
    public DistinctOperatorMetric(String name) {
        super(name);
    }

    /**
     * Calculate Distinct Operator Metric.
     * 
     * @param project the project working on.
     * @param target  the class to be calculated.
     * @return the value of Distinct Operator Metric.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        Set<PsiElement> operators;
        Set<String> distinctOperators = new HashSet<>();

        if (target == null)
            return lastResult;

        operators = ParseAdapter.getOperators(target);
        for (PsiElement value : operators) {
            distinctOperators.add(value.toString());
        }

        lastResult = distinctOperators.size();
        return lastResult;
    }

    @Override
    public String generateReport() {
        String rst = "별개의 연산자가 ";
        if (lastResult < 5) {
            rst += "매우 적고, ";
        } else if (lastResult < 30) {
            rst += "적고, ";
        } else if (lastResult < 200) {
            rst += "보통이고, ";
        } else if (lastResult < 500) {
            rst += "많고, ";
        } else {
            rst += "매우 많고, ";
        }
        return rst;
    }
}
