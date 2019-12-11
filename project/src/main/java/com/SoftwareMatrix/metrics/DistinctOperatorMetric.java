package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

import java.util.HashSet;
import java.util.Set;

public class DistinctOperatorMetric extends Metric {
    DistinctOperatorMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    DistinctOperatorMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        Set<PsiElement> operators;
        Set<String> distinctOperators = new HashSet<>();
        if(target==null)
        {
            System.out.println("No class for calculation!");
            return lastResult;
        }

        operators = ParseAdapter.getOperators(target);
        for (PsiElement value : operators) {
            String operator = value.toString();
            distinctOperators.add(operator);
        }
        return (double)distinctOperators.size();
    }
}
