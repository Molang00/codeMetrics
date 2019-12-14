package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * Calculate the Physical Length of Code (LOC).
 * LOC is one metric of Source lines of code (SLOC), which is a software metric
 * used to measure the size of a computer program by counting the number of
 * lines in the text of the program's source code.
 */
public class LOCMetric extends Metric {
    /**
     * Initialize LOCMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public LOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public LOCMetric(String name) {
        super(name);
    }

    /**
     * Calculate LOC.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of LOC.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        String str = target.getText();
        if(str == null) {
            lastResult = 0;
            return 0;
        }
        else {
            String[] lines = str.split("\r\n|\r|\n");
            lastResult = lines.length;
            return lastResult;
        }
    }
}
