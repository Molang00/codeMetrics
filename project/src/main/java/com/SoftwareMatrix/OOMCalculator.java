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

    /**
     * the structures of classMethod and classAttribute look like this: 
     * - e.g. className : [method1Map, method2Map, ...]
     * and the structure of method1Map looks like this:
     * - e.g. methodName : [0, 1, 0, ...]
     * the int numbers are used to descreibe: 
     * - first number: is public: 1 means public; 0 means not public; 2 means protected
     * - second number: is overrided: 1 means overided; 0 means not overided
     */
    private Map<String, OOMMethod[]> classMethod;
    private Map<String, OOMAttribute[]> classAttribute;
    /**
     * N.B. After modifying, the classMethodNumber and classAttributeNumber would 
     * not be no longer used. 
     * classMethodNumber: 
     * - first: the number of method in classes: calculateNM()
     * - second: the number of public number of method in classes: calculatePM()
     * - third: the number of method ingerited: calculateNMI()
     * - fouth: the number of method overrided: calculateNMO()
     * classAttributeNumber: the number of attribute in classes
     * - first: the number of attribute in classes: NV
     * - second: the number of public number of method in classes: NPV
     */
    // private Map<String, int[]> classMethodNumber, classAttributeNumber;

    OOMCalculator() {
        // TODO
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods (NM): count all the methods including public, 
     * private , protected of a class
     * @return a map: [class name: the number of all methods in this class]
     */
    public Map<String, int> calculateNM() {
        Map<String, int> classNM = new HashMap<String, int>();
        
        for(Object o:classMethod.keySet()) {
            classNM.put(o, classMethod.get(o).size());
        }

        return classNM;
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Public Methdos (PM): the number of public methods in a class
     * @return a map: [class name: number of public methods in this class]
     */
    public Map<String, int> calculatePM() {
        Map<String, int> classPM = new HashMap<String, int>();

        for (Object o:classMethod.keySet()) {
            classPM.put(o, calculatePMSingleClass(classMethod.get(o)));
        }

        return classPM;
    }

    private int calculatePMSingleClass(OOMMethod[] methodList) {
        int publicMethodNumber = 0;

        for (OOMMethod method:methodList) {
            if (method.isPublic()) {
                publicMethodNumber ++;
            }
        }

        return publicMethodNumber;
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Public Variables per class (NPV): the number of public 
     * variables in a class
     * @return a map: [class name: the number of public variables in this class]
     */
    public Map<String, int> calculateNPV() {
        // here has a problem, what we count here is the number of public attributes, 
        // rather than variables the meaning of this shoule be considered in the future.
        // TODO
        Map<String, int> classNPV = new HashMap<String, int>();

        for (Object o:classAttribute.keySet()) {
            classNPV.put(o, calculateNPVShingleClass(classAttribute.get(o)));
        }

        return classNPV;
    }

    private int calculateNPVShingleClass(MMOAttribute attributeList) {
        int publicAttributeNumber = 0;

        for (MMOAttribute attribute:attributeList) {
            if (attribute.isPublic()) {
                publicAttributeNumber ++;
            }
        }

        return publicAttributeNumber;
    }

    /**
     * Lorenz Kidd metrics:
     * Number of Variables per class (NV): count all the variables which are public, 
     * private , protected of a class
     * @return a map: [class name: the number of all variables in this class]
     */
    public Map<String, int> calculateNV( ) {
        Map<String, int> classNV = new HashMap<String, int>();

        for(Object o:classAttribute.keySet()) {
            classNV.put(o, classAttribute.get(o).size());
        }

        return classNV;
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods Inherited by a subclass (NMI): the number of 
     * methods inherited by a subclass
     * @return a map: [class name: the number of methods inherited by a subclass in this class]
     */
    public int calculateNMI(String parentClass, String childClass) {
        // here we should to know how to find the parent of a class, maybe here would 
        // need a new map or tree to complete this
        // TODO
        return 0;
    }

    /**
     * Lorenz Kidd metrics:
     * Number of Methods overridden by a subclass(NMO): counts the number of override 
     * methods of a class
     * @return a map: [class name: the number of overridden methods in this class]
     */
    public Map<String, int> calculateNMO( ) {
        Map<String, int> classNMO = new HashMap<String, int>();

        for(Object o:classMethod.keySet()) {    
            OOMMethod[] methodList = classMethod.get(o);
            int j=0;
            for(Object t:methodList) {
                if(t.isOverride()) {
                    j++;
                }
            }
            classNMO.put(o, j);
        }

        return classNMO;
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods Added by a subclass (NMA)
     * @param parentClass   the super class
     * @param childClass    the subclass
     * @return the number of methods subclass added
     */
    public int calculateNMA(String parentClass, String childClass) {
        int classNMA = 0;
        OOMMethod[] parentMethodList, childMethodList;
        parentMethodList = classMethod.get(parentClass);
        childMethodList = classMethod.get(childClass);

        for (OOMMethod childMethod:childMethodList) {
            for (OOMMethod parentMethod:parentMethodList) {
                if (childMethod.getName().equal(parentMethod.getName())) {
                    continue;
                }
                classNMA++;
            }
        }

        return classNMA;
    }

    /**
     * Lorenz Kidd metrics:
     * Average method size of class : Ratio of the non comment, non blank source lines 
     * divided by the number of methods in the class
     * @param classLine the number of lines of classes without blank and comment lines 
     * in map formate: [class name: number of lines]
     * @return a map: [class name: the average method size in this class]
     */
    public Map<String, doubel> calculateAMS(Map<String, int> classLine){
        // here we need a new input, please consider this when init this calculator
        // TODO
        Map<String, double> classAMS = new HashMap<String, double>();

        for(Object o:classMethod.keySet()) {
            classAMS.put(o, ClassLinesOfCode.get(o)/classMethod.get(o).size());
        }

        return classAMS;
    }

    /**
     * Abreu Metrics:
     * calculate the Polymorphism Factor (PF): Ratio of the number of overriding methods in a
     * class to the total possible number of overridden methods
     * @return a map: [class name: ratio of the number of override methods in this class]
     */
    public Map<String, double> calculatePF() {
        Map<String, double> classPF = new HashMap<String, double>();
        Map<String, int> classNMO = this.calculateNMO();
        // TODO
        int OverriddenMethods = 1; // total possible number of OverriddenMethods here should be worked

        for(Object o:classMethod.keySet()) {
           classPF.put(o, calculateNMO.get(o)/OverriddenMethods); 
        }

        return classPF;
    }

    /**
     * Abreu Metrics:
     * calculate the Method Hiding Factor (MHF): Ratio of hidden (private or protected) 
     * methods to total methods
     * @return a map: [class name: ratio of hidden methods in this class]
     */
    public Map<String, double> calculateMHF() {
        Map<String, double> classMHF = new HashMap<String, double>();

        for(Object o:classMethod.keySet()) {
            classMHF.put(o, (calculateNM().get(o)-calculatePM().get(o))/calculateNM().get(o));
        }

        return classMHF;
    }

    /**
     * Abreu Metrics:
     * calculate the Method Inheritance Factor (MIF): the number of inherited methods as 
     * a ratio of total methods
     * @return a map: [class name: ratio of inherited methods in this class]
     */
    public Map<String, double> calculateMIF() {
        // we consider that here we need to know parent's situation
        // TODO
    }

    /**
     * Abreu Metrics:
     * calculate the Attribute Hiding Factor (AHF): Ratio of hidden (private or protected) 
     * attributes to total attributes
     * @return a map: [class name: ratio of hidden attributes in this class]
     */
    public Map<String, double> calculateAHF( ) {
        Map<String, double> classAHF = new HashMap<String, double>();

        for(Object o:classAttribute.keySet()) {
            classAHF.put(o, (calculateNV.get(o)-calculateNPV.get(o))/calculateNV.get(o));
        }

        return classAHF;
    }

    /**
     * Abreu Metrics:
     * calculate the Attribute Inheritance Factor (AIF): the number of inherited attributes 
     * as a ratio of total attributes
     */
    public Map<String, double> calculateAIF() {
        Map<String, double> classAIF = new HashMap<String, double>();

        for(Object o:classAttribute.keySet()) {
            classAIF.put(o, (calculateNV.get(o)-calculateNPV.get(o))/calculateNV.get(o));
        }

        return classAIF;
    }

    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Coupling Between Objects (CBO): the level of coupling between classes, 
     * coupling between two classes is said to occur when one class uses functions or variables 
     * of another class. 
     * @param class1
     * @param class2
     * @return the level of coupling between these two methods
     */
    public double calculateCBO(String class1, String class2) {
        int couplingNumber = 0;
        // since there is no standart measure method for this metrics
        // it is necessray to disscuss our methods to measure this
        // TODO
        return couplingNumber;
    }

    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Depth of Inheritance Tree (DIT): the maximum level of the inheritance 
     * hierarchy of a class
     * @param class the class we would liek to measure
     * @return the number of level of inheritance
     */
    public int calculateDIT(String class) {
        int interitanceDepth = 0;
        // to complete this, it would be necessart to know the tree of inheritance
        // TODO
        return interitanceDepth;
    }

    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Number of Children (NOC): number of subclasses belonging to a class
     * @param class the class we would like to measure
     * @return the number of subclasses belonging to this class
     */
    public int calculateNOC() {
        int subclassNumber = 0;
        // to complete this, it would be necessart to know the tree of inheritance
        // TODO
        return subclassNumber;
    }

    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Weighted Methods per Class (WMC):  the number of methods in a class
     * @return a map: [class name: the number of methods in this class]
     */
    public Map<String, int> calculateWMC() {
        return calculateNM();
    }

    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Lack of Cohesion in Methods (LCOM): the lack of cohesion in the methods 
     * of a class
     * @return the level of cohesion in methods
     */
    public double calculateLCOM() {
        // there is no standart for measuring this, it would be necessary to discuss this
        // TODO
    }

    // ********** ADDITIONAL PART OF METRACS **********

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

}