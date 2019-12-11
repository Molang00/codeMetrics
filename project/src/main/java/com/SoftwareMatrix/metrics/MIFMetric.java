package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class MIFMetric extends Metric {
    NMIMetric nmiMetric;
    NMMetric nmMetric;
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
        if(target.getParent() == null)
            return 0;
        if(!(target.getParent() instanceof PsiClass)) {
            System.out.println("No parent class but not NULL!");
            return 0;
        }
        return nmiMetric.calculate(project, (PsiClass)target.getParent()) / nmMetric.calculate(project, target);
    }
}
