package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;

/**
 * Calculate the Attribute Inheritance Factor (AIF).
 * A class that inherits lots of attributes from its ancestor classes contributes to a high AIF.
 * A child class that redefines its ancestors' attributes and adds new ones contributes to a lower AIF.
 * An independent class that does not inherit and has no children contributes to a lower AIF.
 */
public class AIFMetric extends Metric {
    private NVMetric nvMetric;

    /**
     * Initialize AIFMetric.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public AIFMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        nvMetric = new NVMetric(name+"_nv");
    }
    public AIFMetric(String name) {
        super(name);
        nvMetric = new NVMetric(name+"_nv");
    }

    /**
     * Calculate AIF.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of AIF.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        if(target.getParent() == null) {
            lastResult = 0;
            return 0;
        }
        if(!(target.getParent() instanceof PsiClass)) {
            lastResult = -1;
            return -1;
        }

        int nppv=0;     //get parent's nppv value
        for(PsiField f : ((PsiClass)target.getParent()).getFields()) {
            if(!f.hasModifierProperty(PsiModifier.PRIVATE))
                nppv++;
        }
        if((double)nppv + nvMetric.calculate(project, target) == 0) {
            lastResult = 0;
            return 0;
        }

        lastResult = (double)nppv / ((double)nppv + nvMetric.calculate(project, target));
        return lastResult;
    }
}
