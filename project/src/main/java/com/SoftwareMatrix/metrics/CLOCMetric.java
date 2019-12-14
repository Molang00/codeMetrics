package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculate the Comment Line of Code (CLOC).
 * The lines of code including blank lines and comments lines.
 */
public class CLOCMetric extends Metric {
    public CLOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public CLOCMetric(String name) {
        super(name);
    }

    /**
     * Calculate CLOC.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of CLOC.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        Set<PsiComment> set = new HashSet<>();
        int cloc = 0;

        // set constraints needed?
        set.addAll(PsiTreeUtil.findChildrenOfType(target, PsiComment.class));

        for (PsiComment comment : set) {
            String str = comment.getText();
            String[] lines = str.split("\r\n|\r|\n");
            cloc += lines.length;
        }

        lastResult = cloc;
        return lastResult;
    }
}
