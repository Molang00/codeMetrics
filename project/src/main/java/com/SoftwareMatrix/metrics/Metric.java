package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * Abstract parent class for the all the metrics proposed
 */

public abstract class Metric {
    protected String name;
    protected double minVal;
    protected double maxVal;
    protected double lastResult;

    /**
     * Initialise the metrics with name of the metric, minimum and maximum value based on its range
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public Metric(String name, double minVal, double maxVal){
        this.name = name;
        this.minVal = minVal;
        this.maxVal = maxVal;
        lastResult = 0;
    }

    public Metric(String name){
        this.name = name;
        this.minVal = 0;
        this.maxVal = 0;
        lastResult = 0;
    }

    /**
     * Returns the calculated metric value
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return metricVal
     */
    public abstract double calculate(Project project, PsiClass target);

    public String getName(){ return name; }
    public double getRatio(){ return (lastResult-minVal)/(maxVal-minVal); }     //return 0~1
    @Override
    public String toString(){return name;}
}
