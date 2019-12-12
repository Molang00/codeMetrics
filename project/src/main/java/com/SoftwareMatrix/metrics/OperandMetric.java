package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public class OperandMetric extends Metric {
    public OperandMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public OperandMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        if(target == null)
            return lastResult;

        lastResult = ParseAdapter.getOperands(target).size();
        return lastResult;
    }
}
