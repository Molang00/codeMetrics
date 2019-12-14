package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * Calculate Maintainability Index:
 * Maintainability Index (MI) is a software metric that measures how maintainable (easy to support and change) your source code is.
 * A high value means better maintainability: A rating between 20 and 100 indicates that your code has good maintainability;
 * A rating between 10 and 19 indicates that your code is moderately maintainable;
 * A rating lower than 10 indicates your code has low maintainability, it would be a good idea for you to work more on this aspect.
 */

public class MaintainabilityMetric extends Metric {
    private CyclomaticMetric ccMetric;
    private HalsteadVolumeMetric hgMetric;
    private LOCMetric locMetric;
    private CLOCMetric clocMetric;
    private LLOCMetric llocMetric;

    /**
     * Initialise parameters for maintainability visualisation such as maximum and minimum value of MI
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public MaintainabilityMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        ccMetric = new CyclomaticMetric(name+"_cc");
        hgMetric = new HalsteadVolumeMetric(name+"_hg");
        locMetric = new LOCMetric(name+"_loc");
        clocMetric = new CLOCMetric(name+"_cloc");
        llocMetric = new LLOCMetric(name+"_lloc");
    }
    public MaintainabilityMetric(String name) {
        super(name);
        ccMetric = new CyclomaticMetric(name+"_cc");
        hgMetric = new HalsteadVolumeMetric(name+"_hg");
        locMetric = new LOCMetric(name+"_loc");
        clocMetric = new CLOCMetric(name+"_cloc");
        llocMetric = new LLOCMetric(name+"_lloc");
    }

    /**
     * Calculate MI using calculated values of cyclomatic complexity and Halstead values
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return MI
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        double result;

        //problem: avoid duplicate call of CC, Halstead, and LOC, CLOC -> making new class in MI (temporarily solved)
        double ccSum = ccMetric.calculate(project, target);
        double commentPercent = clocMetric.calculate(project, target) / locMetric.calculate(project, target);
        double volume = hgMetric.calculate(project, target);
        double lloc = llocMetric.calculate(project, target);

        if(volume == 0 || lloc == 0) {
            lastResult = 0;
            return 0;
        }
        result = 171 - (5.2 * Math.log(volume) / Math.log(2)) - (0.23 * ccSum) - (16.2 * Math.log(lloc) / Math.log(2)) + (50 * Math.sin(Math.toRadians(Math.sqrt(2.4 * commentPercent))));

        lastResult = result;
        return lastResult;
    }
}
