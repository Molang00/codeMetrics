package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

public class LOCMetric extends Metric {

    LOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    LOCMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        String str = target.getText();
        if(str == null) {
            return 0;
        }
        else {
            String[] lines = str.split("\r\n|\r|\n");
            return lines.length;
        }
    }
}
