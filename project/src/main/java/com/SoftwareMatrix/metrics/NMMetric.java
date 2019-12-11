package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class NMMetric extends Metric {
    NMMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    NMMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        return (double)target.getAllMethods().length;
    }
}
