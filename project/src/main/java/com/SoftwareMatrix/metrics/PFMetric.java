package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

public class PFMetric extends Metric {
    private NMOMetric nmoMetric;
    private NPVMetric npvMetric;

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
        PsiElement parent = target.getParent();
        if(parent == null) {
            lastResult = 0;
            return 0;
        }
        if(!(parent instanceof PsiClass))
            return -1;

        double temp = npvMetric.calculate(project, (PsiClass)parent);
        if(temp == 0) {
            lastResult = 0;
            return 0;
        }

        lastResult = nmoMetric.calculate(project, target) / temp;
        return lastResult;
    }
}
