package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * Number of Methods (NM): The total number of methods in a class counts all public, private and protected methods defined.
 * L&K suggest this metric as a useful indication of the classes which may be trying to do too much work themselves; i.e.,
 * they provide too much functionality.The higher NM means the higher amount of work on this class.
 */
public class NMMetric extends Metric {
    /**
     * Initialize NMMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public NMMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    public NMMetric(String name) {
        super(name);
    }

    /**
     * Calculate number of methods
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return NM
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        lastResult = target.getMethods().length;
        return lastResult;
    }
}
