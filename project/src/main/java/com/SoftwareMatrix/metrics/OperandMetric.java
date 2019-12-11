package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public class OperandMetric extends Metric {
    OperandMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    OperandMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        if(target==null)
        {
            System.out.println("No class for calculation!");
            return lastResult;
        }

        return (double)ParseAdapter.getOperands(target).size();
    }
}
