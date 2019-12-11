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
            return lastResult = 0;
        if(!(target.getParent() instanceof PsiClass)) {
            System.out.println("No parent class but not NULL!");
            return lastResult = -1;
        }
        int nppv=0;     //get parent's nppv value
        for(PsiField f : ((PsiClass)target.getParent()).getFields()) {
            if(!f.hasModifierProperty(PsiModifier.PRIVATE))
                nppv++;
        }
        if((double)nppv + nvMetric.calculate(project, target) == 0)
            return lastResult = 0;
        return lastResult = (double)nppv / ((double)nppv + nvMetric.calculate(project, target));
    }
}
