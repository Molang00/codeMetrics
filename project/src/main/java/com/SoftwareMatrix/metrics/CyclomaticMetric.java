package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * Calculate the Cyclomatic Complexity Metric (CC).
 * CC is a software metric used to indicate the complexity of a program.
 * It is a quantitative measure of the number of linearly independent paths
 * through a program's source code.
 * Methods:
 *
 * 1. Graph the source code to control flow graph
 * 2. Cal these criteria:
 *    1. E = the number of edges of the graph.
 *    2. N = the number of nodes of the graph.
 *    3. P = the number of connected components.
 * 3. Cal the cyclomatic complexity: M=E-N+2P / M=E-N+P (These two formulas depend on different methods of graphing)
 *
 * N.B. Connected Components is a definition of Graph Theory.
 * P can be 2 or higher in multithreaded programming,
 * but in single thread programming, P is always 1.
 * As a result, if we just calculate the MI of single thread programming,
 * we can use the formula of calculating G: M=E-N+2.
 */
public class CyclomaticMetric extends Metric{
    public CyclomaticMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
    }
    public CyclomaticMetric(String name) {
        super(name);
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        double result = 0;

        result += ParseAdapter.getBranch(target).size() + 1;
        lastResult = result;

        return result;
    }
}
