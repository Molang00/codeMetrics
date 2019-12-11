package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class AHFMetric extends Metric {
    NVMetric nvMetric;
    NPVMetric npvMetric;
    public AHFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nvMetric = new NVMetric(name+"_nv");
        npvMetric = new NPVMetric(name+"_npv");
    }

    public AHFMetric(String name) {
        super(name);
        nvMetric = new NVMetric(name+"_nv");
        npvMetric = new NPVMetric(name+"_npv");
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        double temp = nvMetric.calculate(project, target);
        if(temp==0)
            return 0;
        return (temp - npvMetric.calculate(project, target)) / temp;
    }
}
