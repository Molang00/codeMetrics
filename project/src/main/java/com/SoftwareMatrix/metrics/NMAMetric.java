package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

public class NMAMetric extends Metric {
    public NMAMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    public NMAMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        int cnt=0;
        for(PsiClass c : target.getInnerClasses()) {
            for(PsiMethod cm : c.getMethods()) {
                boolean check = true;
                for(PsiMethod pm : target.getMethods()) {
                    if(cm.getName().equals(pm.getName())) {
                        check = false;
                        break;
                    }
                }
                if (check)
                    cnt++;
            }
        }
        return lastResult = (double)cnt;
    }
}
