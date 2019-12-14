package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;

/**
 * Number of Public Variables per class (NPV): This metric counts the number of public variables in a class.
 * L&K consider the number of variables in a class to be one measure of its size.
 * The fact that one class has more public variables than another might imply that the class has more relationships with other objects and, as such, is more likely to be a key class,
 * i.e., a central point of coordination of objects within the system. The higher NPV means this class has more relationships with others.
 */

public class NPVMetric extends Metric {
    /**
     * Initialize NPVMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public NPVMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public NPVMetric(String name) {
        super(name);
    }

    /**
     * Calculate the number of public variables of a class
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return NPV
     */

    @Override
    public double calculate(Project project, PsiClass target) {
        int cnt = 0;
        for(PsiField f : target.getFields()) {
            if(f.hasModifierProperty(PsiModifier.PUBLIC))
                cnt++;
        }

        lastResult = cnt;
        return lastResult;
    }
}
