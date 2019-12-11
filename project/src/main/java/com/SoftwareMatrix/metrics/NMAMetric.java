package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

public class NMAMetric extends Metric {
    NMAMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    NMAMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        int cnt=0;
        for(PsiClass c : target.getAllInnerClasses()) {
            for(PsiMethod cm : c.getAllMethods()) {
                for(PsiMethod pm : target.getAllMethods()) {
                    if(cm.getName().equals(pm.getName()))
                        continue;
                    cnt++;
                }
            }
        }
        return (double)cnt;
    }
}
