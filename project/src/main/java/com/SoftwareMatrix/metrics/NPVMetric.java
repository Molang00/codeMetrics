package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;

public class NPVMetric extends Metric{

    NPVMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    NPVMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        int cnt=0;
        for(PsiField f : target.getAllFields()) {
            if(f.hasModifierProperty(PsiModifier.PUBLIC))
                cnt++;
        }
        return (double)cnt;
    }
}
