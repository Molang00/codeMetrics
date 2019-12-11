package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.HashSet;
import java.util.Set;

public class CLOCMetric extends Metric {
    CLOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    CLOCMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        Set<PsiComment> set = new HashSet<>();
        int cloc = 0;

        set.addAll(PsiTreeUtil.findChildrenOfType(target, PsiComment.class));

        for (PsiComment comment : set) {
            String str = comment.getText();
            String[] lines = str.split("\r\n|\r|\n");
            cloc += lines.length;
        }

        return (double)cloc;
    }
}
