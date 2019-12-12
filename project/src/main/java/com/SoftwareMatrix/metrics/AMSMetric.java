package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class AMSMetric extends Metric {
    private NMMetric nmMetric;
    private LOCMetric locMetric;
    private CLOCMetric clocMetric;

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
        double temp = nmMetric.calculate(project, target);
        if(temp==0) {
            lastResult = 0;
            return 0;
        }

        lastResult = (locMetric.calculate(project, target) - clocMetric.calculate(project, target)) / temp;
        return lastResult;
    }
}
