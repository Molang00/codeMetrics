package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

import java.util.HashSet;
import java.util.Set;

public class DistinctOperandMetric extends Metric {
    public DistinctOperandMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }

    public DistinctOperandMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        Set<PsiElement> operands;
        Set<String> distinctOperands = new HashSet<>();

        if(target==null)
        {
            System.out.println("No class for calculation!");
            return lastResult;
        }

        operands = ParseAdapter.getOperands(target);
        for (PsiElement o : operands) {
            String operand = o.toString();
            distinctOperands.add(operand);
        }
        return lastResult = (double)distinctOperands.size();
    }
}
