package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * Calculate the Logical Length of Code (LLOC).
 * LLOC is one metric of Source lines of code (SLOC), which is a software metric
 * used to measure the size of a computer program by counting the number of
 * lines in the text of the program's source code.
 */
public class LLOCMetric extends Metric{
    /**
     * Initialize LLOCMetric object.
     * @param name the name of Class.
     * @param minVal the minimum number of this metrics
     * @param maxVal the maximum number of this metrics
     */
    public LLOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public LLOCMetric(String name) {
        super(name);
    }

    /**
     * Calculate LLOC.
     * @param project the project working on.
     * @param target the class to be calculated.
     * @return the value of LLOC.
     */
    @Override
    public double calculate(Project project, PsiClass target) {
        int lloc = 0;
        for(PsiMethod m : target.getMethods()) {
//            if(m.getBody()==null || m.getBody().getStatements() == null)
////                continue;
////            else {
////                lloc += m.getBody().getStatements().length;
////                for(PsiStatement s: m.getBody().getStatements()) {
////                }
////            }
            lloc += PsiTreeUtil.findChildrenOfType(m, PsiStatement.class).size();
            lloc -= PsiTreeUtil.findChildrenOfType(m, PsiBlockStatement.class).size();
        }

        lloc += target.getFields().length;

        // possible bug here (is this intended?)
        for(PsiClass c : target.getInnerClasses()) {
            lloc = (int)Math.round(calculate(project, c));
        }

        lastResult = lloc;
        return lastResult;

    }
}
