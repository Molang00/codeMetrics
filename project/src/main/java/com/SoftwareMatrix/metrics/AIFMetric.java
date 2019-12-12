package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;

public class AIFMetric extends Metric {
    private NVMetric nvMetric;

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
        if(target.getParent() == null) {
            lastResult = 0;
            return 0;
        }
        if(!(target.getParent() instanceof PsiClass)) {
            lastResult = -1;
            return -1;
        }

        int nppv=0;     //get parent's nppv value
        for(PsiField f : ((PsiClass)target.getParent()).getFields()) {
            if(!f.hasModifierProperty(PsiModifier.PRIVATE))
                nppv++;
        }
        if((double)nppv + nvMetric.calculate(project, target) == 0) {
            lastResult = 0;
            return 0;
        }

        lastResult = (double)nppv / ((double)nppv + nvMetric.calculate(project, target));
        return lastResult;
    }
}
