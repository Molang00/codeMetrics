package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;

public class DITMetric extends Metric {
    public DITMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    public DITMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        int depth=0;
        PsiClass class_itr = target;
        PsiElement parent = class_itr.getParent();
        while (parent instanceof PsiClass) { // parent not null checked in instanceof
            depth++;
            class_itr = (PsiClass)parent;
            parent = class_itr.getParent();
        }

        lastResult = depth;
        return lastResult;
    }
}
