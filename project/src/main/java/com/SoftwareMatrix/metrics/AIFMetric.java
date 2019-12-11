package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;

public class AIFMetric extends Metric {
    NVMetric nvMetric;
    public AIFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nvMetric = new NVMetric(name+"_nv");
    }

    public AIFMetric(String name) {
        super(name);
        nvMetric = new NVMetric(name+"_nv");
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        if(target.getParent() == null)
            return 0;
        if(!(target.getParent() instanceof PsiClass)) {
            System.out.println("No parent class but not NULL!");
            return 0;
        }
        int nppv=0;     //get parent's nppv value
        for(PsiField f : ((PsiClass)target.getParent()).getAllFields()) {
            if(!f.hasModifierProperty(PsiModifier.PRIVATE))
                nppv++;
        }
        return (double)nppv / ((double)nppv + nvMetric.calculate(project, target));
    }
}
