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
            return 0;
        if(!(target.getParent() instanceof PsiClass)) {
            System.out.println("No parent class but not NULL!");
            return 0;
        }
        return nmoMetric.calculate(project, target) / npvMetric.calculate(project, (PsiClass)target.getParent());
    }
}
