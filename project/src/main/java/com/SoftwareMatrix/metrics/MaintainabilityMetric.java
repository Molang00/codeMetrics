package com.SoftwareMatrix.metrics;

import com.SoftwareMatrix.ParseAdapter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

public class MaintainabilityMetric extends Metric {
    private CyclomaticMetric ccMetric;
    private HalsteadVolumeMetric hgMetric;
    private LOCMetric locMetric;
    private CLOCMetric clocMetric;
    private LLOCMetric llocMetric;
    MaintainabilityMetric(String name, double minVal, double maxVal) {
        super(name, minVal, maxVal);
        ccMetric = new CyclomaticMetric(name+"_cc");
        hgMetric = new HalsteadVolumeMetric(name+"_hg");
        locMetric = new LOCMetric(name+"_loc");
        clocMetric = new CLOCMetric(name+"_cloc");
        llocMetric = new LLOCMetric(name+"_lloc");
    }
    MaintainabilityMetric(String name) {
        super(name);
        ccMetric = new CyclomaticMetric(name+"_cc");
        hgMetric = new HalsteadVolumeMetric(name+"_hg");
        locMetric = new LOCMetric(name+"_loc");
        clocMetric = new CLOCMetric(name+"_cloc");
        llocMetric = new LLOCMetric(name+"_lloc");
    }

    @Override
    public double calculate(Project project, PsiClass target) {
        double result;

        //problem: avoid duplicate call of CC, Halstead, and LOC, CLOC -> making new class in MI (temporarily solved)
        double ccSum = ccMetric.calculate(project, target);
        double commentPercent = clocMetric.calculate(project, target) / locMetric.calculate(project, target);
        double volume = hgMetric.calculate(project, target);
        double lloc = llocMetric.calculate(project, target);

        if(volume == 0 || lloc == 0)
            return 0;
        result = 171 - (5.2 * Math.log(volume) / Math.log(2)) - (0.23 * ccSum) - (16.2 * Math.log(lloc) / Math.log(2)) + (50 * Math.sin(Math.toRadians(Math.sqrt(2.4 * commentPercent))));
        lastResult = result;

        return result;
    }
}
