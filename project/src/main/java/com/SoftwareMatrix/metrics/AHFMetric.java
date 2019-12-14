package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * Calculate the Attribute Hiding Factor (AHF).
 * AHF is the ratio of hidden (private or protected) attributes to total attributes.
 * AHF is also proposed as a measure of encapsulation.
 */
public class AHFMetric extends Metric {
    private NVMetric nvMetric;
    private NPVMetric npvMetric;

    /**
     * Initialize AHFMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public AHFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nvMetric = new NVMetric(name+"_nv");
        npvMetric = new NPVMetric(name+"_npv");
    }
    public AHFMetric(String name) {
        super(name);
        nvMetric = new NVMetric(name+"_nv");
        npvMetric = new NPVMetric(name+"_npv");
    }

    /**
     * Calculate AHF.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of AHF.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        double temp = nvMetric.calculate(project, target);
        if(temp==0) {
            lastResult = 0;
            return 0;
        }

        lastResult = (temp - npvMetric.calculate(project, target)) / temp;
        return lastResult;
    }
}
