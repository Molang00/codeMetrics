package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class PFMetric extends Metric {
    NMOMetric nmoMetric;
    NPVMetric npvMetric;
    public PFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nmoMetric = new NMOMetric(name+"_nmo");
        npvMetric = new NPVMetric(name+"_p_npv");
    }

    public PFMetric(String name) {
        super(name);
        nmoMetric = new NMOMetric(name+"_nmo");
        npvMetric = new NPVMetric(name+"_p_npv");
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        if(target.getParent() == null)
            return lastResult = 0;
        if(!(target.getParent() instanceof PsiClass)) {
            System.out.println("No parent class but not NULL!");
            return -1;
        }
        double temp = npvMetric.calculate(project, (PsiClass)target.getParent());
        if(temp == 0)
            return lastResult = 0;
        return lastResult = nmoMetric.calculate(project, target) / temp;
    }
}
