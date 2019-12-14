package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

    /**
    * NOC - Number of Children
    *The greater the number of children, the greater the level of reuse, since inheritance is a form of reuse.
    *The greater the number of children, the greater the likelihood of improper abstraction of the parent class. If a class has a large number of children, it may be a case of misuse of subclassing.
    *The number of children gives an idea of the potentialinfluence a class has on the design. If a class has a large number of children, it may require more testing of the methods in that class.
    */
public class NOCMetric extends Metric {
    /**
     * Initialize NOCMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public NOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public NOCMetric(String name) {
        super(name);
    }

        /**
         * Calculate the Number of Children (NOC) as defined by C&K - the number of immediate subclasses of a class
         * @param project the project working on.
         * @param target the class to be calculated.
         * @return NOC
         */

    @Override
    public double calculate(Project project, PsiClass target) {
        lastResult = target.getInnerClasses().length;
        return lastResult;
    }
}
