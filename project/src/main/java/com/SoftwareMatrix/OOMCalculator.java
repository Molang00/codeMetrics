package com.SoftwareMatrix;

import java.util.HashSet;
import java.lang.Math;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;

/**
 *  the calculator of the Object-Oriented Metrics (OOM)
 */
public class OOMCalculator {

    private int methodNumber, classNumber, attributeNumber;
    /**
     * the structures of classMethod and classAttribute look like this: 
     * - e.g. className : [method1Map, method2Map, ...]
     * and the structure of method1Map looks like this:
     * - e.g. methodName : [0, 1, 0, ...]
     * the int numbers are used to descreibe: 
     * - first number: is public: 1 means public; 0 means not public; 2 means protected
     * - second number: is overrided: 1 means overided; 0 means not overided
     */
    private Map<String, Map> classMethod, classAttribute;
    private Map<String, int[]> method, attribute; 
    /**
     * classMethodNumber: 
     * - first: the number of method in classes
     * - second: the number of public number of method in classes
     * - third: the number of method ingerited
     * - fouth: the number of method overrided
     * classAttributeNumber: the number of attribute in classes
     * - first: the number of attribute in classes
     * - second: the number of public number of method in classes
     */
    private Map<String, int[]> classMethodNumber, classAttributeNumber;

    private Map<String, Map> classMethod, classAttribute;
    private Map<String, int[]> method, attribute;

    private Map<String, int[]> classMethodNumber, classAttributeNumber;

    OOMCalculator() {
        // TODO
    }

    /**
     * init OOM calculator by basic information
     * @param classMethod       a map recorded all methods in one class, and all classes in a system
     * @param classAttribute    a map recorded all defiened attribute in one class, and all classes in a system
     */
    // OOMCalculator(Map<String, String[]> classMethod, Map<String, String[]> classAttribute) {
    //     classNumber = classMethod.size();
    //     methodNumber = 0;
    //     for (Map.Entry<String, String[]> entry : classMethod.entrySet()) {
    //         methodNumber += entry.getValue().size();
    //     }
    //     attributeNumber = 0;
    //     for (Map.Entry<String, String[]> entry : classAttribute.entrySet()) {
    //         attributeNumber += entry.getValue().size();
    //     }
    // }

    /**
     * Abreu Metrics:
     * calculate the Method Hiding Factor (MHF): Ratio of hidden (private or protected) methods to total methods
<<<<<<< HEAD
     * recommendation:  A measure of encapsulation
=======
     * Recommendation:  A measure of encapsulation
>>>>>>> c19cf79098c8b17a675baa50789d6767ac5c9109
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
<<<<<<< HEAD
     * calculate the Number of Public Methdos (PM)
     */
    public void calculatePM() {
         for (Map.Entry<String, Map> entry : classMethod.entrySet()) {
             classMethodNumber.get(entry.getKey()).getValue()[1] = calculatePMSingleClass(entry.getValue());
         }
    }
    private int calculatePMSingleClass(Map methodMap) {
        int publicMethodNumber = 0;
        for (Map.Entry<String, int[]> entry : methodMap.entrySet()) {
            if (entry.getValue()[0] == 1) {
                publicMethodNumber ++;
            }
        }
        return publicMethodNumber;
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
     * calculate the Number of Public Variables per class (NPV)
     */
    public void calculateNPV() {
        for (Map.Entry<String, Map> entry : classAttribute.entrySet()) {
            classAttributeNumber.get(entry.getKey()).getValue()[1] = calculateNPVShingleClass(entry.getValue());
        }
    }
    private int calculateNPVShingleClass(Map attributeMap) {
        int publicAttributeNumber = 0;
        for (Map.Entry<String, int[]> entry : attributeMap.entrySet()) {
            if (entry.getValue()[0] == 1) {
                publicAttributeNumber ++;
            }
        }
        return publicAttributeNumber;
    }

    /**
=======
>>>>>>> c19cf79098c8b17a675baa50789d6767ac5c9109
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
<<<<<<< HEAD
    }

    /**
     * calculate the Number of Methods Inherited by a subclass (NMI)
     */
    public int calculateNMI(String parentClass, String childClass) {
        return classMethodNumber.get(parentClass)[0] - calculatePRMSingleClass(classMethod.get(parentClass)) - classMethodNumber.get(childClass)[3];
    }
    // calculate the Number of Private Method in a class (PRM)
    private int calculatePRMSingleClass(Map methodMap) {
        int privateMethodNumber = 0;
        for (Map.Entry<String, int[]> entry : methodMap.entrySet()) {
            if (entry.getValue()[0] == 0) {
                privateMethodNumber ++;
            }
        }
        return privateMethodNumber;
=======


>>>>>>> c19cf79098c8b17a675baa50789d6767ac5c9109
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
<<<<<<< HEAD
     * calculate the Number of Methods Added by a subclass (NMA)
     */
    public void calculateNMA() {
        // this is the method number - override number.
    }

    /**
=======
>>>>>>> c19cf79098c8b17a675baa50789d6767ac5c9109
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

<<<<<<< HEAD
    /**
     * calculate the Number of times a Class is Reused (NCR)
     * TODO: since we have
     */
    public void calculateNCR() {

    }
=======

>>>>>>> c19cf79098c8b17a675baa50789d6767ac5c9109

}