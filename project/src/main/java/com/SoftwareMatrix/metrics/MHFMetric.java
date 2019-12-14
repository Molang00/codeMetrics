package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * Calculate Method Hiding Factor (MHF):
 * This metric is the ratio of hidden (private or protected) methods to total methods.
 * As such, MHF is proposed as a measure of encapsulation.
 */

public class MHFMetric extends Metric {
    private NMMetric nmMetric;
    private PMMetric pmMetric;

    /**
     * Initialise parameter name MHF and maximum /minimum values of the range of MHF
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public MHFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nmMetric = new NMMetric(name);
        pmMetric = new PMMetric(name);
    }

    public MHFMetric(String name) {
        super(name);
        nmMetric = new NMMetric(name);
        pmMetric = new PMMetric(name);
    }

    /**
     * calculate the value of MHF by calculating the number of hidden methods and total methods
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return MHF
     */

    @Override
    public double calculate(Project project, PsiClass target) {
        double temp = nmMetric.calculate(project, target);
        if(temp==0) {
            lastResult = 0;
            return 0;
        }

        lastResult = (temp - pmMetric.calculate(project, target)) / temp;
        return lastResult;
    }
}
