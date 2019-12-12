package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class MHFMetric extends Metric {
    private NMMetric nmMetric;
    private PMMetric pmMetric;

    public MHFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nmMetric = new NMMetric(name);
        pmMetric = new PMMetric(name);
    }

    public MHFMetric(String name) {
        super(name);
        nmMetric = new NMMetric(name);
        pmMetric = new PMMetric(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        double temp = nmMetric.calculate(project, target);
        if(temp==0) {
            lastResult = 0;
            return 0;
        }

        lastResult = (temp - pmMetric.calculate(project, target)) / temp;
        return lastResult;
    }
}
