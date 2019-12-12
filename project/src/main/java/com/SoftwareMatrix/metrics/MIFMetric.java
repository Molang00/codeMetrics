package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class MIFMetric extends Metric {
    private NMIMetric nmiMetric;
    private NMMetric nmMetric;

    public MIFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nmiMetric = new NMIMetric(name+"_nmi");
        nmMetric = new NMMetric(name+"_nm");
    }

    public MIFMetric(String name) {
        super(name);
        nmiMetric = new NMIMetric(name+"_nmi");
        nmMetric = new NMMetric(name+"_nm");
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        if(target.getParent() == null) {
            lastResult = 0;
            return 0;
        }

        if(!(target.getParent() instanceof PsiClass)) {
            return -1;
        }

        double temp = nmMetric.calculate(project, target);
        if(temp==0) {
            lastResult = 0;
            return 0;
        }

        lastResult = nmiMetric.calculate(project, (PsiClass)target.getParent()) / temp;
        return lastResult;
    }
}
