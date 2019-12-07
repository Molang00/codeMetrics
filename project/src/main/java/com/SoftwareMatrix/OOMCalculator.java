package com.SoftwareMatrix;

import org.jetbrains.annotations.NotNull;
import com.intellij.psi.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  the calculator of the Object-Oriented Metrics (OOM)
 */
public class OOMCalculator {

   private List<OOMClass> classList;

    OOMCalculator(@NotNull PsiPackage psiPackage) {
        // TODO
        classList = new ArrayList<>();
        for(PsiClass p : psiPackage.getClasses()) {
            OOMClass oomClass = new OOMClass(p);
            classList.add(oomClass);
        }
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods (NM): count all the methods including public, 
     * private , protected of a class
     * @return a map: [class name: the number of all methods in this class]
     */
    public Map<String, Integer> calculateNM() {
        Map<String, Integer> classNM = new HashMap<String, Integer>();
        
        for(int i=0; i<classList.size();i++) {

            classNM.put(classList.get(i).getName(),classList.get(i).calculateNM());
        }

        return classNM;
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Public Methdos (PM): the number of public methods in a class
     * @return a map: [class name: number of public methods in this class]
     */
    public Map<String, Integer> calculatePM() {
        Map<String, Integer> classPM = new HashMap<String, Integer>();

        for(int i=0; i<classList.size();i++) {

            classPM.put(classList.get(i).getName(),classList.get(i).calculatePM());
        }

        return classPM;
    }



    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Public Variables per class (NPV): the number of public 
     * variables in a class
     * @return a map: [class name: the number of public variables in this class]
     */
    public Map<String, Integer> calculateNPV() {
        Map<String, Integer> classNPV = new HashMap<String, Integer>();

        for(int i=0; i<classList.size();i++) {

            classNPV.put(classList.get(i).getName(),classList.get(i).calculateNPV());
        }

        return classNPV;
    }


    /**
     * Lorenz Kidd metrics:
     * Number of Variables per class (NV): count all the variables which are public, 
     * private , protected of a class
     * @return a map: [class name: the number of all variables in this class]
     */
    public Map<String, Integer> calculateNV( ) {
        Map<String, Integer> classNV = new HashMap<String, Integer>();

        for(int i=0; i<classList.size();i++) {

            classNV.put(classList.get(i).getName(),classList.get(i).calculateNV());
        }

        return classNV;

    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods Inherited by a subclass (NMI): the number of 
     * methods inherited by a subclass
     * @return a map: [class name: the number of methods inherited by a subclass in this class]
     */
    public Map<String, Integer> calculateNMI() {
        Map<String, Integer> classNMI= new HashMap<String, Integer>();;
        for(int i = 0; i<classList.size(); i++) {

            classNMI.put(classList.get(i).getName(),classList.get(i).calculateNMI());
        }

        return classNMI;
    }

    /**
     * Lorenz Kidd metrics:
     * Number of Methods overridden by a subclass(NMO): counts the number of override 
     * methods of a class
     * @return a map: [class name: the number of overridden methods in this class]
     */
    public Map<String, Integer> calculateNMO( ) {
        Map<String, Integer> classNMO= new HashMap<String, Integer>();;
        for(int i = 0; i<classList.size(); i++) {

            classNMO.put(classList.get(i).getName(),classList.get(i).calculateNMO());
        }

        return classNMO;

    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods Added by a subclass (NMA)
     * @return the number of methods subclass added
     */
    public Map<String, Integer> calculateNMA(String parentClass, String childClass) {
        Map<String, Integer> classNMA= new HashMap<String, Integer>();;
        for(int i = 0; i<classList.size(); i++) {

            classNMA.put(classList.get(i).getName(),classList.get(i).calculateNMA());
        }

        return classNMA;
    }

    /**
     * Lorenz Kidd metrics:
     * Average method size of class : Ratio of the non comment, non blank source lines 
     * divided by the number of methods in the class
     * in map formate: [class name: number of lines]
     * @return a map: [class name: the average method size in this class]
     */
    public Map<String, Double> calculateAMS(){
        Map<String, Double> classAMS= new HashMap<String, Double>();;
        for(int i = 0; i<classList.size(); i++) {

            classAMS.put(classList.get(i).getName(),classList.get(i).calculateAMS());
        }

        return classAMS;
    }

    /**
     * Abreu Metrics:
     * calculate the Polymorphism Factor (PF): Ratio of the number of overriding methods in a
     * class to the total possible number of overridden methods
     * @return a map: [class name: ratio of the number of override methods in this class]
     */
    public Map<String, Double> calculatePF() {
        Map<String, Double> classPF= new HashMap<String, Double>();;
        for(int i = 0; i<classList.size(); i++) {

            classPF.put(classList.get(i).getName(),classList.get(i).calculatePF());
        }

        return classPF;
    }

    /**
     * Abreu Metrics:
     * calculate the Method Hiding Factor (MHF): Ratio of hidden (private or protected) 
     * methods to total methods
     * @return a map: [class name: ratio of hidden methods in this class]
     */
    public Map<String, Double> calculateMHF() {
        Map<String, Double> classMHF= new HashMap<String,Double>();

        for(int i = 0; i<classList.size(); i++) {

            classMHF.put(classList.get(i).getName(),classList.get(i).calculateMHF());
        }

        return classMHF;
    }

    /**
     * Abreu Metrics:
     * calculate the Method Inheritance Factor (MIF): the number of inherited methods as 
     * a ratio of total methods
     * @return a map: [class name: ratio of inherited methods in this class]
     */
    public Map<String, Double> calculateMIF() {
        Map<String, Double> classMIF = new HashMap<String, Double>();;
        for(int i = 0; i<classList.size(); i++) {

            classMIF.put(classList.get(i).getName(),classList.get(i).calculateMIF());
        }

        return classMIF;
    }

    /**
     * Abreu Metrics:
     * calculate the Attribute Hiding Factor (AHF): Ratio of hidden (private or protected) 
     * attributes to total attributes
     * @return a map: [class name: ratio of hidden attributes in this class]
     */
    public Map<String, Double> calculateAHF( ) {
        Map<String, Double> classAHF= new HashMap<String, Double>();;
        for(int i = 0; i<classList.size(); i++) {

            classAHF.put(classList.get(i).getName(),classList.get(i).calculateAHF());
        }

        return classAHF;
    }

    /**
     * Abreu Metrics:
     * calculate the Attribute Inheritance Factor (AIF): the number of inherited attributes 
     * as a ratio of total attributes
     */
    public Map<String, Double> calculateAIF() {
        Map<String, Double> classAIF= new HashMap<String, Double>();;
        for(int i = 0; i<classList.size(); i++) {

            classAIF.put(classList.get(i).getName(),classList.get(i).calculateAIF());
        }

        return classAIF;
    }


    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Depth of Inheritance Tree (DIT): the maximum level of the inheritance 
     * hierarchy of a class
     * @return the number of level of inheritance
     */
    public Map<String, Double> calculateDIT() {
        Double interitanceDepth;
        Map<String, Double> classDIT= new HashMap<String, Double>();
        for(int i = 0; i<classList.size(); i++) {
            interitanceDepth=0.0;
            OOMClass aClass= classList.get(i);
            while(aClass.getParent()!=null)
            {
                interitanceDepth++;
                aClass= aClass.getParent();
            }

            classDIT.put(classList.get(i).getName(),interitanceDepth);
        }

        return classDIT;

    }

    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Number of Children (NOC): number of subclasses belonging to a class
     * @return the number of subclasses belonging to this class
     */
    public Map<String, Double> calculateNOC() {
        Map<String, Double> classNOC= new HashMap<String, Double>();;
        for(int i = 0; i<classList.size(); i++) {

            classNOC.put(classList.get(i).getName(),classList.get(i).calculateNOC());
        }

        return classNOC;
    }

    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Weighted Methods per Class (WMC):  the number of methods in a class
     * @return a map: [class name: the number of methods in this class]
     */
    public Map<String, Integer> calculateWMC() {
        return calculateNM();
    }

    /** ------------------------------------------------------------------------------------------------
     * Chidamber and Kemerer Metrics:
     * calculate the Lack of Cohesion in Methods (LCOM): the lack of cohesion in the methods 
     * of a class
     * @return the level of cohesion in methods
     */
    public double calculateLCOM() {
        // there is no standart for measuring this, it would be necessary to discuss this
        // TODO
        return 0.0;
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
        // TODO--------------
        return couplingNumber;
    }


}