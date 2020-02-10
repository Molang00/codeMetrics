package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculate the Distinct Operand Metric. Distinct Operand is am important
 * parameter in calculating Halstead Metric.
 */
public class DistinctOperandMetric extends Metric {
    public DistinctOperandMetric(String name) {
        super(name);
    }

    /**
     * Calculate Distinct Operand Metric.
     * 
     * @param project the project working on.
     * @param target  the class to be calculated.
     * @return the value of Distinct Operand Metric.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        Set<String> distinctOperands = new HashSet<>();

        if (target == null)
            return lastResult;

        Set<PsiElement> operands = ParseAdapter.getOperands(target);
        for (PsiElement o : operands) {
            distinctOperands.add(o.toString());
        }

        lastResult = distinctOperands.size();
        return lastResult;
    }

    @Override
    public String generateReport() {
        String rst = "별개의 피연산자가 ";
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
