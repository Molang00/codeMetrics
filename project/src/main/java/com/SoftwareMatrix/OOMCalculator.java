package com.SoftwareMatrix;

import java.util.HashSet;
import java.lang.Math;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;

/**
 *  calculate the Object-Oriented Metrics (OOM)
 */
public class OOMCalculator {

    private int methodNumber, classNumber, attributeNumber;

    private Map<String, Map> classMethod, classAttribute;
    private Map<String, int[]> method, attribute;

    private Map<String, int[]> classMethodNumber, classAttributeNumber;

    OOMCalculator() {
        methodNumber = 0;
        classNumber = 0;
        attributeNumber = 0;
    }

    /**
     * init OOM calculator by basic information
     * @param classMethod       a map recorded all methods in one class, and all classes in a system
     * @param classAttribute    a map recorded all defiened attribute in one class, and all classes in a system
     */
    OOMCalculator(Map<String, String[]> classMethod, Map<String, String[]> classAttribute) {
        classNumber = classMethod.size();
        methodNumber = 0;
        for (Map.Entry<String, String[]> entry : classMethod.entrySet()) {
            methodNumber += entry.getValue().size();
        }
        attributeNumber = 0;
        for (Map.Entry<String, String[]> entry : classAttribute.entrySet()) {
            attributeNumber += entry.getValue().size();
        }
    }

    // TODO: need a overall init function, like provide several maps.

    /**
     * Abreu Metrics:
     * calculate the Method Hiding Factor (MHF): Ratio of hidden (private or protected) methods to total methods
     * Recommendation:  A measure of encapsulation
     */
    public double[] calculateMHF() {
        double val[] = new double[0];
        int i=0;
        for(Object o:classMethod.keySet())
        {
            val[i] = (classMethodNumber.get(o)[0]-classMethodNumber.get(o)[1])/classMethodNumber.get(o)[0] ;
            i++;
        }

        return val;
    }


    /**
     * Abreu Metrics:
     * calculate the Attribute Hiding Factor (AHF): Ratio of hidden (private or protected) attributes to total attributes
     * Recommendation: A measure of encapsulation
     */
    public double[] calculateAHF( ) {
        double val[] = new double[0];
        int i=0;
        for(Object o:classAttribute.keySet())
        {
            val[i] = (classAttributeNumber.get(o)[0]-classAttributeNumber.get(o)[1])/classAttributeNumber.get(o)[0] ;
            i++;
        }

        return val;
    }

    // ********** this two methods can be merged to one method **********
    // TODO
    /**
     * calculate the Method Inheritance Factor (MIF)
     */
    public double calculateMIF(Map<String, String[]> classInheritanceMathod) {
        int inheritanceMethodNumber = 0;

        for (Map.Entry<String, String[]> entry : classInheritanceMathod.entrySet()) {
            inheritanceMethodNumber += entry.getValue().size();
        }

        return double(inheritanceMethodNumber) / methodNumber;
    }

    /**
     * calculate the Attribute Inheritance Factor (AIF)
     */
    public double[] calculateAIF() {
        double val[] = new double[0];
        int i=0;
        for(Object o:classAttribute.keySet())
        {
            val[i] = (classAttributeNumber.get(o)[0]-classAttributeNumber.get(o)[1])/classAttributeNumber.get(o)[0] ;
            i++;
        }

        return val;
    }

    /**
     * Abreu Metrics:
     * calculate the Polymorphism Factor (PF): Ratio of the number of overriding methods in a
     * class to the total possible number of overridden methods
     * Recommendation : overriding methods reduces complexity and increases understandability
     */
    public double[] calculatePF() {
        //position 3 of classMethodNumber corresponds to number of methods inherited by a class

        int OverriddenMethods[] = this.calculateNMO();
        double val[] = new double[0];
        int i=0;
        for(Object o:classMethod.keySet())
        {
           val[i] = classMethodNumber.get(o)[2]/OverriddenMethods[i];
           i++;
        }

        return val;
    }

    /**
     * calculate the CouplingFactor (CF)
     */
    public double calculateCF() {
        int couplingNumber = 0;
        // after defining the structure of classMethod by Map-Map, this part can be easily done by traversal
        // TODO
        return couplingNumber / (classNumber * classNumber - classNumber);
    }

    // here is a problem, the CBO is used to calculate one class's coupling number, not all
    // and this method will be well used in calculatePF

    /**
     * calculate the Coupling Between Objects (CBO)
     */
    public double calculateCBO() {
        int couplingNumber = 0;
        for (Map.Entry<String, String[]> entryCount : classMethod.entrySet()) {
            for (Map.Entry<String, String[]> entryRef : classMethod.entrySet()) {
                // TODO
            }
        }
        return couplingNumber;
    }

    /**
     * calculate the Depth of Inheritance Tree (DIT)
     */
    public double calculateDIT() {
        // TODO
    }

    /**
     * calculate the Number of Children (NOC)
     */
    public double calculateNOC() {
        // TODO
    }

    /**
     * calculate the Specialisation Index per Class (SIX)
     */
    public double calculateSIX() {
        // TODO
    }

    /**
     * calculate the Response For a Class (RFC)
     */
    public double calculateRFC() {
        // TODO
    }

    /**
     * calculate the Weighted Methods per Class (WMC)
     */
    public double calculateWMC() {
        // TODO
    }

    /**
     * calculate the Lack of Cohesion in Methods (LCOM)
     */
    public double calculateLCOM() {
        // TODO
    }

    /**
     * calculate the Lines of Code per method (LOC)
     */
    public double calculateLOC() {
        // TODO
    }

    /**
     * calculate the Number of Messages Send (NOM)
     */
    public double calculateNOM() {
        // TODO

    }

    /**
     * Lorenz Kidd metrics:
     * Number of Methods : count all the methods public, private , protected of a class
     */
    public int[] calculateNM( ) {

        int val[] = new int[0];
        int i=0;
        for(Object o:classMethod.keySet())
        {
            val[i]=classMethod.get(o).size();
            i++;
        }

        return val;


    }

    /**
     * Lorenz Kidd metrics:
     * Number of Variables per class : count all the variables which are public, private , protected of a class
     */
    public int[] calculateNV( ) {

        int val[] = new int[0];
        int i=0;
        for(Object o:classAttribute.keySet())
        {
            val[i]=classAttribute.get(o).size();
            i++;
        }

        return val;


    }

    /**
     * Lorenz Kidd metrics:
     * Number of Methods overridden by a subclass : counts the number of overridden methods of a class
     * recommendation : large number of overridden methods indicate a design problem
     */
    public int[] calculateNMO( ) {

        int val[] = new int[0];

        int i=0;
        for(Object o:classMethod.keySet())
        {    Map<String, int[]> ClassMap = classMethod.get(o);
             int j=0;
            for(Object t:ClassMap.keySet())
            {
                if(ClassMap.get(t)[1]==1)
                {
                    j++;
                }
            }
            val[i]=j;
            i++;
        }
        return val;
    }

    /**
     * @param: Map with key as class name and value as the number of lines of code in the class
     * Lorenz Kidd metrics:
     * Average method size of class : Ratio of the non comment , non blank source lines divided by the number of methods in the class
     * recommendation : useful for spotting abnormally large method sizes in a class
     */
    public double[] calculateAMS(Map<String, Integer> ClassLinesOfCode){
        double val[] = new double[0];
        int i=0;
        for(Object o:classMethod.keySet())
        {
            val[i]=ClassLinesOfCode.get(o)/classMethod.get(o).size();
            i++;
        }

        return val;
    }



}