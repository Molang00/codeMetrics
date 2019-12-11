package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.util.PsiTreeUtil;

public class LLOCMetric extends Metric{
    LLOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    LLOCMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        int lloc;
        lloc = PsiTreeUtil.getChildrenOfTypeAsList(target, PsiStatement.class).size();
        return (double)lloc;
    }
}
