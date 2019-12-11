package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import java.util.HashSet;
import java.util.Set;

public class HalsteadVolumeMetric extends Metric{
    HalsteadVolumeMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    HalsteadVolumeMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        double volume;
        if(target==null)
        {
            System.out.println("No class for calculation!");
            return lastResult;
        }

        Set<PsiElement> operators;
        Set<PsiElement> operands;
        Set<String> distinctOperators = new HashSet<>();
        Set<String> distinctOperands = new HashSet<>();

        operators = ParseAdapter.getOperators(target);
        operands = ParseAdapter.getOperands(target);

        for (PsiElement value : operators) {
            String operator = value.toString();
            distinctOperators.add(operator);
        }
        for (PsiElement o : operands) {
            String operand = o.toString();
            distinctOperands.add(operand);
        }

        //n1, n2: count of unique operators, operands
        //N1, N2: count of all operators, operands
        int n1, n2, N1, N2;
        n1 = distinctOperators.size();
        n2 = distinctOperands.size();
        N1 = operators.size();
        N2 = operands.size();

        volume = (double)(N1+N2) * ( Math.log((double)(n1+n2)) / Math.log(2) );
        lastResult = volume;

        return volume;
    }
}
