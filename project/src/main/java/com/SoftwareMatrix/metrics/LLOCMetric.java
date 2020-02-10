package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * Calculate the Logical Length of Code (LLOC). LLOC is one metric of Source
 * lines of code (SLOC), which is a software metric used to measure the size of
 * a computer program by counting the number of lines in the text of the
 * program's source code.
 */
public class LLOCMetric extends Metric {
    private LOCMetric locMetric;
    private double locRst;

    public LLOCMetric(String name) {
        super(name);
        locMetric = new LOCMetric(name + "_loc");
    }

    /**
     * Calculate LLOC.
     * 
     * @param project the project working on.
     * @param target  the class to be calculated.
     * @return the value of LLOC.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        locRst = locMetric.calculate(project, target);
        int lloc = 0;
        for (PsiMethod m : target.getMethods()) {
            lloc += PsiTreeUtil.findChildrenOfType(m, PsiStatement.class).size();
            lloc -= PsiTreeUtil.findChildrenOfType(m, PsiBlockStatement.class).size();
        }

        lloc += target.getFields().length;

        for (PsiClass c : target.getInnerClasses()) {
            lloc = (int) Math.round(calculate(project, c));
        }

        lastResult = lloc;
        return lastResult;
    }

    @Override
    public String generateReport() {
        double logicalPercent = lastResult / locRst;
        String rst = "의미 있는 코드의 비율이 ";
        if (logicalPercent < 0.5) {
            rst += "매우 적고, ";
        } else if (logicalPercent < 0.8) {
            rst += "적고, ";
        } else if (logicalPercent < 0.9) {
            rst += "보통이고, ";
        } else if (logicalPercent < 0.95) {
            rst += "많고, ";
        } else {
            rst += "매우 많고, ";
        }
        return rst;
    }
}
