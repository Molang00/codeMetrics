package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;

/**
 * Calculate NMI
 * Returns number of public and protected methods of class
 */

public class NMIMetric extends Metric {
    /**
     * Initialize NMIMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public NMIMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public NMIMetric(String name) {
        super(name);
    }

    /**
     * calculate number of non-private methods in class
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return NMI
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        int cnt = 0;
        for(PsiMethod m : target.getMethods()) {
            if(!m.hasModifierProperty(PsiModifier.PRIVATE))
                cnt++;
        }

        lastResult = cnt;
        return lastResult;
    }
}
