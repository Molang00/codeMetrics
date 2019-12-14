package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

/**
 * Number of Methods Overridden by a subclass (NMO):
 * A subclass is allowed to re-define or override a method in its superclass(es) with the same name as a method in one of its superclasses.
 * According to L&K, a large number of overridden methods indicates a design problem, indicating that those methods were overridden as a design afterthought.
 * They suggest that a subclass should really be a specialization of its super- classes, resulting in new unique names for methods.
 */
public class NMOMetric extends Metric {
    /**
     * Initialize NMOMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public NMOMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public NMOMetric(String name) {
        super(name);
    }

    /**
     * calculate the number of methods overriden by a subclass of the target class
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return NMO
     */

    @Override
    public double calculate(Project project, PsiClass target) {
        int cnt=0;
        for(PsiMethod m : target.getMethods()) {
            if(m.findSuperMethods().length > 0)
                cnt++;
        }

        lastResult = cnt;
        return lastResult;
    }
}
