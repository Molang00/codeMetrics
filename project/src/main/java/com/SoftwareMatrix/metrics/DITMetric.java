package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * Calculate the Depth of Inheritance Tree (DIT).
 * Depth of inheritance of the class is the DIT metric for the class.
 * In cases involving multiple inheritance, theDIT will be the maximum
 * length from the node to the root of the tree.
 */
public class DITMetric extends Metric {
    /**
     * Initialize DITMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public DITMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    public DITMetric(String name) {
        super(name);
    }

    /**
     * Calculate the Depth of Inheritance Tree.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of the Depth of Inheritance Tree.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        int depth=0;
        PsiClass class_itr = target;
        PsiElement parent = class_itr.getParent();
        while (parent instanceof PsiClass) { // parent not null checked in instanceof
            depth++;
            class_itr = (PsiClass)parent;
            parent = class_itr.getParent();
        }

        lastResult = depth;
        return lastResult;
    }
}
