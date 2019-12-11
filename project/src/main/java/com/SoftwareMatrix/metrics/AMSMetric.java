package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class AMSMetric extends Metric {
    NMMetric nmMetric;
    LOCMetric locMetric;
    CLOCMetric clocMetric;
    public AMSMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nmMetric = new NMMetric(name+"_nm");
        locMetric = new LOCMetric(name+"_loc");
        clocMetric = new CLOCMetric(name+"_cloc");
    }

    public AMSMetric(String name) {
        super(name);
        nmMetric = new NMMetric(name+"_nm");
        locMetric = new LOCMetric(name+"_loc");
        clocMetric = new CLOCMetric(name+"_cloc");
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        return (locMetric.calculate(project, target) - clocMetric.calculate(project, target)) / nmMetric.calculate(project, target);
    }
}
