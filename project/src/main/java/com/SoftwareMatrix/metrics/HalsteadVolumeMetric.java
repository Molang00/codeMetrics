package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Calculate the Halstead Volume Metric. Halstead's volume (V) describes the
 * size of the implementation of an algorithm. The computation of V is based on
 * the number of operations performed and operands handled in the algorithm.
 * Therefore V is less sensitive to code layout than the lines-of-code measures.
 *
 * Methods:
 *
 * 1. Cal these criteria: 1. $\eta$ = the number of distinct operators &
 * operands 2. $N$ = the total number of operators & operands 2. Cal the volume:
 * $V=N\times log_2 \eta $
 */
public class HalsteadVolumeMetric extends Metric {
  private DistinctOperandMetric distinctOperandMetric;
  private DistinctOperatorMetric distinctOperatorMetric;
  private OperandMetric operandMetric;
  private OperatorMetric operatorMetric;

  public HalsteadVolumeMetric(String name) {
    super(name);
    distinctOperandMetric = new DistinctOperandMetric(name + "_dopd");
    distinctOperatorMetric = new DistinctOperatorMetric(name + "_dopr");
    operandMetric = new OperandMetric(name + "_opd");
    operatorMetric = new OperatorMetric(name + "_opr");
  }

  /**
   * Calculate Halstead Volume Metric.
   * 
   * @param project the project working on.
   * @param target  the class to be calculated.
   * @return the value of Halstead Volume Metric.
   */
  @Override
  public double calculate(Project project, PsiClass target) {
    double volume;
    if (target == null)
      return lastResult;

    // n1, n2: count of unique operators, operands
    // N1, N2: count of all operators, operands
    double n1, n2, N1, N2;
    n1 = distinctOperatorMetric.calculate(project, target);
    n2 = distinctOperandMetric.calculate(project, target);
    N1 = operatorMetric.calculate(project, target);
    N2 = operandMetric.calculate(project, target);

    volume = (N1 + N2) * (Math.log(n1 + n2) / Math.log(2));

    lastResult = volume;
    return lastResult;
  }

  @Override
  public String generateReport() {
    String rst = "";
    return rst;
  }
}