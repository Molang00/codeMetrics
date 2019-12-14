package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

/**
 * Calculate the Halstead Effort Metric.
 * The effort measure translates into actual coding time using the following relation,
 * Time required to program: {\displaystyle T={E \over 18}}T={E \over 18} seconds.
 * Effort Metric is an important parameter to calculate the Halstead metric.
 */
public class EffortMetric extends Metric {
    private DifficultyMetric difficultyMetric;
    private HalsteadVolumeMetric halsteadVolumeMetric;

    /**
     * Initialize EffortMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public EffortMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        difficultyMetric = new DifficultyMetric(name+"_dif");
        halsteadVolumeMetric = new HalsteadVolumeMetric(name+"_vol");
    }
    public EffortMetric(String name) {
        super(name);
        difficultyMetric = new DifficultyMetric(name+"_dif");
        halsteadVolumeMetric = new HalsteadVolumeMetric(name+"_vol");
    }

    /**
     * Calculate Effort Metric.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of Effort Metric.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        lastResult = difficultyMetric.calculate(project, target) * halsteadVolumeMetric.calculate(project, target);
        return lastResult;
    }
}
