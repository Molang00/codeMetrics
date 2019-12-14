package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;

import java.lang.reflect.Modifier;

/**
 * Number of Public Methods (PM) :
 * This simply counts the number of public methods in a class.
 * According to L&K, this metric is useful as an aid to estimating the amount of work to develop a class or subsystem.
 * The higher PM means the higher amount of work on this class.
 */
public class PMMetric extends Metric{
    /**
     * Initialize PMMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public PMMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public PMMetric(String name) {
        super(name);
    }

    /**
     * Calculates the number of public methods in a class
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return PM
     */

    @Override
    public double calculate(Project project, PsiClass target) {
        int cnt=0;
        for(PsiMethod m : target.getMethods()) {
            if(m.hasModifierProperty(PsiModifier.PUBLIC))
                cnt++;
        }

        lastResult = cnt;
        return lastResult;
    }
}
