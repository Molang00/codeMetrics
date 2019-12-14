package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * Number of Variables per class (NV): This metric counts the total number of variables in a class.
 * The total number of variables metric includes public, private and protected variables.
 * According to L&K, the ratio of private and protected variables to total number of variables indicates the effort required by that class in providing information to other classes.
 * Private and protected variables are therefore viewed merely as data to service the methods in the class.
 */
public class NVMetric extends Metric {
    /**
     * Initialize NVMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public NVMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public NVMetric(String name) {
        super(name);
    }

    /**
     * Calculate the number of variables in a class
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return NV
     */

    @Override
    public double calculate(Project project, PsiClass target) {
        lastResult = target.getFields().length;
        return lastResult;
    }
}
