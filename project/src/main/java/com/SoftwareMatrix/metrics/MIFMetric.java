package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

/**
 * Method inheritance factor (MIF): This is obtained by dividing the total number of inherited methods by the total number of methods.
 * The total number of inherited methods is obtained by summing the number of operations that a class has inherited from its super-classes.
 */
public class MIFMetric extends Metric {
    private NMIMetric nmiMetric;
    private NMMetric nmMetric;

    /**
     * Initialise MIF, minimum and maximum value based on the range of MIF
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */

    public MIFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nmiMetric = new NMIMetric(name+"_nmi");
        nmMetric = new NMMetric(name+"_nm");
    }

    public MIFMetric(String name) {
        super(name);
        nmiMetric = new NMIMetric(name+"_nmi");
        nmMetric = new NMMetric(name+"_nm");
    }

    /**
     * calculate MIF using the following formula
     * Method inheritance factor = (Sum of inherited methods) / (Total number of inherited methods)
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return MIF
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        if(target.getParent() == null) {
            lastResult = 0;
            return 0;
        }

        if(!(target.getParent() instanceof PsiClass)) {
            return -1;
        }

        double temp = nmMetric.calculate(project, target);
        if(temp==0) {
            lastResult = 0;
            return 0;
        }

        lastResult = nmiMetric.calculate(project, (PsiClass)target.getParent()) / temp;
        return lastResult;
    }
}
