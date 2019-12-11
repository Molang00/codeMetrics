package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public class NOCMetric extends Metric {
    NOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    NOCMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        return (double)target.getAllInnerClasses().length;
    }
}
