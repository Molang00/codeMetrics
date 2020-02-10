package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * Calculate the Physical Length of Code (LOC). LOC is one metric of Source
 * lines of code (SLOC), which is a software metric used to measure the size of
 * a computer program by counting the number of lines in the text of the
 * program's source code.
 */
public class LOCMetric extends Metric {
    public LOCMetric(String name) {
        super(name);
    }

    /**
     * Calculate LOC.
     * 
     * @param project the project working on.
     * @param target  the class to be calculated.
     * @return the value of LOC.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        String str = target.getText();
        if (str == null) {
            lastResult = 0;
            return 0;
        } else {
            String[] lines = str.split("\r\n|\r|\n");
            lastResult = lines.length;
            return lastResult;
        }
    }

    @Override
    public String generateReport() {
        String rst = "전체 코드의 길이가 ";
        if (lastResult < 20) {
            rst += "매우 적고, ";
        } else if (lastResult < 50) {
            rst += "적고, ";
        } else if (lastResult < 150) {
            rst += "보통이고, ";
        } else if (lastResult < 300) {
            rst += "많고, ";
        } else {
            rst += "매우 많고, ";
        }
        return rst;
    }
}
