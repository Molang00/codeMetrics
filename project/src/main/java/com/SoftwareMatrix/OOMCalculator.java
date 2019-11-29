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
     * calculate the Method Hiding Factor (MHF)
     * @param calassCall
     * @param classMethod
     */
    public double calculateMHF(Map<String, int> classCall, Map<String, String[]> classMethod) {
        double callRate = 0;

        for (Map.Entry<String, String[]> entry : classMethod.entrySet()) {
            String[] methodList = entry.getValue();
            for (i = 0; i < methodList.size(); i++) {
                int callNumber = 0;
                // depend on how the map provided, if the parse team can provide the number of calling.
                // TODO
                callRate += 1 - (callNumber / (classNumber - 1));
            }
        }
        
        return callRate / double(methodNumber);
    }

    /**
     * calculate the Attribute Hiding Factor (AHF)
     * 
     */
    public double calculateAHF(Map<String, int> classCalll, Map<String, String[]> classAttribute) {
        double callRate = 0;

        for (Map.Entry<String, String[]> entry : classAttribute.entrySet()) {
            String[] attributeList = entry.getValue();
            for (i = 0; i < attributeList.size(); i++) {
                int callNumber = 0;
                // depend on how the map provided, if the parse team can provide the number of calling.
                // TODO
                callRate += 1 - (callNumber / (classNumber - 1));
            }
        }

        return callRate / double(attributeNumber);
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
    public double calculateAIF(Map<String, String[]> classInheritanceAttribute) {
        int inheritanceAttributeNumber = 0;

        for (Map.Entry<String, String[]> entry : classInheritanceAttribute.entrySet()) {
            inheritanceAttributeNumber += entry.getValue().size();
        }

        return double(inheritanceAttributeNumber) / methodNumber;
    }

    /**
     * calculate the Polymorphism Factor (PF)
     */
    public double calculatePF() {
        // calculate PF would be somewhat complex
        // TODO
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
}