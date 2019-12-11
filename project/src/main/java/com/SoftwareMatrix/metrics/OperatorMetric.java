package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public class OperatorMetric extends Metric {
    OperatorMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    OperatorMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        if(target==null)
        {
            System.out.println("No class for calculation!");
            return lastResult;
        }

        return (double)ParseAdapter.getOperators(target).size();
    }
}
