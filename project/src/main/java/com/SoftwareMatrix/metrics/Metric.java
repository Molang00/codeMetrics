package com.SoftwareMatrix.metrics;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;


public abstract class Metric {
    protected String name;
    protected double minVal;
    protected double maxVal;
    protected double lastResult;

    public Metric(String name, double minVal, double maxVal){
        this.name = name;
        this.minVal = minVal;
        this.maxVal = maxVal;
        lastResult = 0;
    }

    public Metric(String name){
        this.name = name;
        this.minVal = 0;
        this.maxVal = 0;
        lastResult = 0;
    }

    public abstract double calculate(Project project, PsiClass target);

    public String getName(){ return name; }
    public double getRatio(){ return (lastResult-minVal)/(maxVal-minVal); }     //return 0~1
    @Override
    public String toString(){return name;}
}
