package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

public class NMOMetric extends Metric {
    public NMOMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    public NMOMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        int cnt=0;
        for(PsiMethod m : target.getMethods()) {
            if(m.findSuperMethods().length>0)
                cnt++;
        }
        return lastResult = (double)cnt;
    }
}
