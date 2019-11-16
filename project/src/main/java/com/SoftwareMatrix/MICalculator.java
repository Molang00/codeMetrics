package com.SoftwareMatrix;

import java.util.HashSet;
import java.lang.Math;

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
    public int calculateHalstead(Object[] operators, Object[] operands) {
        int n, v;
        Set<String> distinctOperators = new HashSet<Object>;
        Set<String> distinctOperands = new HashSet<Object>;

        for (i = 0; i < operators.length; i++) {
            String operator = operators[i].toString();
            if (!distinctOperators.contains(operator)) {
                distinctOperators.add(operator);
            }
        }

        for (i = 0; i < operands.length; i++) {
            String operand = operands[i].toString();
            if (!distinctOperators.contains(operand)) {
                distinctOperators.add(operand);
            }
        }

        return (operators.length + operands.length) * (Math.log(distinctOperands.size() + distinctOperators.size()) / Math.log(2));
    }

    /**
     * Calculating Cyclomatic Complexity
     * @param edge  the total number of edges in source code
     * @param node  the total number of nodes in source code
     * @return  the Cyclomatic Complexity of source code
     */
    public int calculateCC(int edge, int node) {
        return edge + node + 2;
    }

    /**
     * Calculating Maintainability Index
     * MI  = 171 - 5.2 * log2(V) - 0.23 * G - 16.2 * log2 (LOC) + 50 * sin (sqrt(2.4 * CM))
     * @param operators the array of total operators in source code
     * @param operands  the array of total operands in source code
     * @param edge  the total number of edges in source code
     * @param node  the total number of nodes in source code
     * @param lloc  the logical lines of code
     * @param loc   the physical lines of code
     * @param cloc  the comment lines of code
     * @return  the Maintainability Index of source code
     */
    public int calculateMI(Object[] operators, Object[] operands, int edge, int node, int lloc, int loc, int cloc) {
        int v = this.calculateHalstead(operators, operands); // Halstead Volume
        int g = this.calculateCC(edge, node);
        double cm = (double) (cloc / loc);

        return 171 - (5.2 * Math.log(v) / Math.log(2)) - (0.23 * g) - (16.2 * Math.log(lloc) / Math.log(2)) + (50 * Math.sin(Math.toRadians(Math.sqrt(2.4 * cm))));
    }

}