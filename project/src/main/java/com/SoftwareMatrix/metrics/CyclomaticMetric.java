package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * Cyclomatic Complexity extends Metric class
 */
public class CyclomaticMetric extends Metric{
    public CyclomaticMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public CyclomaticMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        double result = 0;

        result += ParseAdapter.getBranch(target).size() + 1;
        lastResult = result;

        return result;
    }
}
