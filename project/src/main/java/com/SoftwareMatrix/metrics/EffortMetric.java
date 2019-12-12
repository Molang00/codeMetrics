package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public class EffortMetric extends Metric {
    private DifficultyMetric difficultyMetric;
    private HalsteadVolumeMetric halsteadVolumeMetric;

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

    @Override
    public double calculate(Project project, PsiClass target) {
        lastResult = difficultyMetric.calculate(project, target) * halsteadVolumeMetric.calculate(project, target);
        return lastResult;
    }
}
