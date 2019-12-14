package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * Polymorphism Factor (PF):
 * This metric is based on the number of overriding methods in a class as a ratio of the total possible number of overridden methods.
 * Polymorphism arises from inheritance, and Abreu claims that in some cases, overriding methods reduce complexity, so increasing understandability and maintainability.
 */

public class PFMetric extends Metric {
    private NMOMetric nmoMetric;
    private NPVMetric npvMetric;

    /**
     * Initialize PFMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public PFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nmoMetric = new NMOMetric(name+"_nmo");
        npvMetric = new NPVMetric(name+"_p_npv");
    }
    public PFMetric(String name) {
        super(name);
        nmoMetric = new NMOMetric(name+"_nmo");
        npvMetric = new NPVMetric(name+"_p_npv");
    }

    /**
     * Calculate the PF for a target class using the number of overriding methods in a class
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return PF
     */

    @Override
    public double calculate(Project project, PsiClass target) {
        PsiElement parent = target.getParent();
        if(parent == null) {
            lastResult = 0;
            return 0;
        }
        if(!(parent instanceof PsiClass))
            return -1;

        double temp = npvMetric.calculate(project, (PsiClass)parent);
        if(temp == 0) {
            lastResult = 0;
            return 0;
        }

        lastResult = nmoMetric.calculate(project, target) / temp;
        return lastResult;
    }
}
