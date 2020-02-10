package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculate the Comment Line of Code (CLOC). The lines of code including blank
 * lines and comments lines.
 */
public class CLOCMetric extends Metric {
    private LOCMetric locMetric;
    private double locRst;

    public CLOCMetric(String name) {
        super(name);
        locMetric = new LOCMetric(name + "_loc");
    }

    /**
     * Calculate CLOC.
     * 
     * @param project the project working on.
     * @param target  the class to be calculated.
     * @return the value of CLOC.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        Set<PsiComment> set = new HashSet<>();
        locRst = locMetric.calculate(project, target);
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

    @Override
    public String generateReport() {
        double commentPercent = lastResult / locRst;
        String rst = "코드 설명의 비율이 ";
        if (commentPercent < 0.03) {
            rst += "매우 적꼬, ";
        } else if (commentPercent < 0.1) {
            rst += "적고, ";
        } else if (commentPercent < 0.2) {
            rst += "보통이고, ";
        } else if (commentPercent < 0.5) {
            rst += "많고, ";
        } else {
            rst += "매우 많고, ";
        }
        return rst;
    }
}
