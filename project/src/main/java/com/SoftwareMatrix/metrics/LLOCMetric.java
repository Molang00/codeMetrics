package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

public class LLOCMetric extends Metric{
    public LLOCMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public LLOCMetric(String name) {
        super(name);
    }

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
