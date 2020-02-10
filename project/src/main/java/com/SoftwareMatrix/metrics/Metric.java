package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

/**
 * Abstract parent class for the all the metrics proposed
 */

public abstract class Metric {
    protected String name;
    protected double lastResult;

    public Metric(String name) {
        this.name = name;
        lastResult = 0;
    }

    /**
     * Returns the calculated metric value
     * 
     * @param project the project working on.
     * @param target  the class to be calculated.
     * @return metricVal
     */
    public abstract double calculate(Project project, PsiClass target);

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public abstract String generateReport();
}
