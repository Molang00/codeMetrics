package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class MHFMetric extends Metric {
    NMMetric nmMetric;
    PMMetric pmMetric;
    MHFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nmMetric = new NMMetric(name);
        pmMetric = new PMMetric(name);
    }

    MHFMetric(String name) {
        super(name);
        nmMetric = new NMMetric(name);
        pmMetric = new PMMetric(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        return (nmMetric.calculate(project, target) - pmMetric.calculate(project, target)) / nmMetric.calculate(project, target);
    }
}
