package com.SoftwareMatrix;

import com.intellij.psi.PsiConditionalExpression;
import com.intellij.psi.PsiElement;

import java.util.HashSet;
import java.lang.Math;
import java.util.Set;

/**
 *  calculator for Halstead Volume, Cyclomatic Complexity, and Mmaintainability Index.
 */
public class MICalculator {
    /**
     * Calculating Halstead Volume
     * @param operators the array of total operators in source code
     * @param operands  the array of total operands in source code
     * @return  the Halstead Volume of source code.
     */
    public static int calculateEta(Set<PsiElement> operators, Set<PsiElement> operands) {
        Set<String> distinctOperators = new HashSet<>();
        Set<String> distinctOperands = new HashSet<>();
        boolean hasConditionalExp = false;

        for (PsiElement value : operators) {
            if(value instanceof PsiConditionalExpression) {
                if (hasConditionalExp) {
                    continue;
                }
                else {
                    hasConditionalExp = true;
                }
            }
            String operator = value.toString();
            distinctOperators.add(operator);
        }

        for (PsiElement o : operands) {
            String operand = o.toString();
            distinctOperands.add(operand);
        }

        return (int) ((distinctOperands.size() + distinctOperators.size()));
    }

    /**
     * Calculating Halstead Volume
     * @param operators the array of total operators in source code
     * @param operands  the array of total operands in source code
     * @return  the Halstead Volume of source code.
     */
    public static int calculateN(Set<PsiElement> operators, Set<PsiElement> operands) {
        return (int) (operators.size() + operands.size());
    }

    /**
     * Calculating Halstead Volume
     * @param operators the array of total operators in source code
     * @param operands  the array of total operands in source code
     * @return  the Halstead Volume of source code.
     */
    public static int calculateHalstead(Set<PsiElement> operators, Set<PsiElement> operands) {

        return (int) ((calculateN(operators, operands))
                * (Math.log(calculateEta(operators, operands)) / Math.log(2)));
    }

    /**
     * Calculating Cyclomatic Complexity
     * @param branches : total branches in the class
     * @return  the Cyclomatic Complexity of source code
     */
    public static int calculateCC(Set<PsiElement> branches) {
        return branches.size() + 1;
    }

    /**
     * Calculating Maintainability Index
     * MI  = 171 - 5.2 * log2(V) - 0.23 * G - 16.2 * log2 (LOC) + 50 * sin (sqrt(2.4 * CM))
     * N.B. the LOC here means lloc.
     * @param operators the array of total operators in source code
     * @param operands  the array of total operands in source code
     * @param branches : total branches in the class
     * @param lloc  the logical lines of code
     * @param loc   the physical lines of code
     * @param cloc  the comment lines of code
     * @return  the Maintainability Index of source code
     */
    public static int calculateMI(Set<PsiElement> operators, Set<PsiElement> operands, Set<PsiElement> branches, int lloc, int loc, int cloc) {
        int v = calculateHalstead(operators, operands); // Halstead Volume
        int g = calculateCC(branches);
        double cm = (double)cloc / loc;

        if (v == 0)
            return 0;
        return (int) Math.round(171 - (5.2 * Math.log(v) / Math.log(2)) - (0.23 * g) - (16.2 * Math.log(lloc) / Math.log(2)) + (50 * Math.sin(Math.toRadians(Math.sqrt(2.4 * cm)))));
    }

}