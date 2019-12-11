package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public class NOCMetric extends Metric {
    public NOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    public NOCMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        return (double)target.getAllInnerClasses().length;
    }
}
