package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

/**
 *Number of Methods Added by a subclass (NMA):
 * According to L&K, the normal expectation for a subclass is that it will further specialize (or add) methods to the superclass object.
 * A method is defined as an added method in a subclass if there is no method of the same name in any of its superclasses.
 *
 */

public class NMAMetric extends Metric {
    /**
     * Initialize NMAMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public NMAMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public NMAMetric(String name) {
        super(name);
    }

    /**
     * Counts the number of methods added in a subclass of the target class
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return NMA
     */

    @Override
    public double calculate(Project project, PsiClass target) {
        int cnt=0;
        for(PsiClass c : target.getInnerClasses()) {
            for(PsiMethod cm : c.getMethods()) {
                boolean check = true;
                for(PsiMethod pm : target.getMethods()) {
                    if(cm.getName().equals(pm.getName())) {
                        check = false;
                        break;
                    }
                }
                if (check)
                    cnt++;
            }
        }

        lastResult = cnt;
        return lastResult;
    }
}
