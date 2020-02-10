package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

/**
 * Calculates the number of operands in a class
 */

public class OperandMetric extends Metric {
    public OperandMetric(String name) {
        super(name);
    }

    /**
     * Calculates the number of operands in a target class
     * 
     * @param project the project working on.
     * @param target  the class to be calculated.
     * @return OperandMetric
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        if (target == null)
            return lastResult;

        lastResult = ParseAdapter.getOperands(target).size();
        return lastResult;
    }

    @Override
    public String generateReport() {
        String rst = "피연산자가 ";
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
