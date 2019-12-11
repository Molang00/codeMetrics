package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;

public class EffortMetric extends Metric {
    DifficultyMetric difficultyMetric;
    HalsteadVolumeMetric halsteadVolumeMetric;
    EffortMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        difficultyMetric = new DifficultyMetric(name+"_dif");
        halsteadVolumeMetric = new HalsteadVolumeMetric(name+"_vol");
    }

    EffortMetric(String name) {
        super(name);
        difficultyMetric = new DifficultyMetric(name+"_dif");
        halsteadVolumeMetric = new HalsteadVolumeMetric(name+"_vol");
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        return difficultyMetric.calculate(project, target) * halsteadVolumeMetric.calculate(project, target);
    }
}
