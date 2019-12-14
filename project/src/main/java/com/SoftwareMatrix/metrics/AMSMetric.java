package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * Calculate the Average Method Size (AMS)
 * The average method size is calculated as the number of non-comment, non-blank source lines (NCSL) in the class,
 * divided by the number of its methods.
 * AMS is clearly a size metric, and would be useful for spotting outliers, i.e., abnormally large methods.
 */
public class AMSMetric extends Metric {
    private NMMetric nmMetric;
    private LOCMetric locMetric;
    private CLOCMetric clocMetric;

    /**
     * Initialize AMSMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public AMSMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nmMetric = new NMMetric(name+"_nm");
        locMetric = new LOCMetric(name+"_loc");
        clocMetric = new CLOCMetric(name+"_cloc");
    }
    public AMSMetric(String name) {
        super(name);
        nmMetric = new NMMetric(name+"_nm");
        locMetric = new LOCMetric(name+"_loc");
        clocMetric = new CLOCMetric(name+"_cloc");
    }

    /**
     * Calculate AMS.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of AMS.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        double temp = nmMetric.calculate(project, target);
        if(temp==0) {
            lastResult = 0;
            return 0;
        }

        lastResult = (locMetric.calculate(project, target) - clocMetric.calculate(project, target)) / temp;
        return lastResult;
    }
}
