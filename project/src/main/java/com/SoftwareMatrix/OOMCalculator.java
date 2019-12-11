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

    public OOMCalculator(@NotNull PsiPackage psiPackage) {
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

        for (OOMClass oomClass : classList) {
            classNM.put(oomClass.getName(), oomClass.calculateNM());
        }

        return classNM;
    }

    /**
     * Sum all NM scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total NM score in the PsiPackage
     */
    public Integer GetPackageNM() {
        Integer ret = 0;
        Map<String, Integer> NMmaps = calculateNM();

        for (Integer val: NMmaps.values())
            ret += val;
        return ret;
    }


    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Public Methdos (PM): the number of public methods in a class
     * @return a map: [class name: number of public methods in this class]
     */
    public Map<String, Integer> calculatePM() {
        Map<String, Integer> classPM = new HashMap<String, Integer>();

        for (OOMClass oomClass : classList) {
            classPM.put(oomClass.getName(), oomClass.calculatePM());
        }

        return classPM;
    }

    /**
     * Sum all PM scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total PM score in the PsiPackage
     */
    public Integer GetPackagePM() {
        Integer ret = 0;
        Map<String, Integer> PMmaps = calculatePM();

        for (Integer val: PMmaps.values())
            ret += val;
        return ret;
    }


    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Public Variables per class (NPV): the number of public 
     * variables in a class
     * @return a map: [class name: the number of public variables in this class]
     */
    public Map<String, Integer> calculateNPV() {
        Map<String, Integer> classNPV = new HashMap<String, Integer>();

        for (OOMClass oomClass : classList) {
            classNPV.put(oomClass.getName(), oomClass.calculateNPV());
        }

        return classNPV;
    }

    /**
     * Sum all NPV scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total NPV score in the PsiPackage
     */
    public Integer GetPackageNPV() {
        Integer ret = 0;
        Map<String, Integer> NPVmaps = calculateNPV();

        for (Integer val: NPVmaps.values())
            ret += val;
        return ret;
    }


    /**
     * Lorenz Kidd metrics:
     * Number of Variables per class (NV): count all the variables which are public, 
     * private , protected of a class
     * @return a map: [class name: the number of all variables in this class]
     */
    public Map<String, Integer> calculateNV( ) {
        Map<String, Integer> classNV = new HashMap<String, Integer>();

        for (OOMClass oomClass : classList) {
            classNV.put(oomClass.getName(), oomClass.calculateNV());
        }

        return classNV;

    }

    /**
     * Sum all NV scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total NV score in the PsiPackage
     */
    public Integer GetPackageNV() {
        Integer ret = 0;
        Map<String, Integer> NVmaps = calculateNV();

        for (Integer val: NVmaps.values())
            ret += val;
        return ret;
    }


    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods Inherited by a subclass (NMI): the number of 
     * methods inherited by a subclass
     * @return a map: [class name: the number of methods inherited by a subclass in this class]
     */
    public Map<String, Integer> calculateNMI() {
        Map<String, Integer> classNMI= new HashMap<String, Integer>();;
        for (OOMClass oomClass : classList) {
            classNMI.put(oomClass.getName(), oomClass.calculateNMI());
        }

        return classNMI;
    }

    /**
     * Sum all NMI scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total NMI score in the PsiPackage
     */
    public Integer GetPackageNMI() {
        Integer ret = 0;
        Map<String, Integer> NMImaps = calculateNMI();

        for (Integer val: NMImaps.values())
            ret += val;
        return ret;
    }


    /**
     * Lorenz Kidd metrics:
     * Number of Methods overridden by a subclass(NMO): counts the number of override 
     * methods of a class
     * @return a map: [class name: the number of overridden methods in this class]
     */
    public Map<String, Integer> calculateNMO( ) {
        Map<String, Integer> classNMO= new HashMap<String, Integer>();;
        for (OOMClass oomClass : classList) {
            classNMO.put(oomClass.getName(), oomClass.calculateNMO());
        }

        return classNMO;

    }

    /**
     * Sum all NMO scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total NMO score in the PsiPackage
     */
    public Integer GetPackageNMO() {
        Integer ret = 0;
        Map<String, Integer> NMOmaps = calculateNMO();

        for (Integer val: NMOmaps.values())
            ret += val;
        return ret;
    }


    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods Added by a subclass (NMA)
     * @return the number of methods subclass added
     */
    public Map<String, Integer> calculateNMA(String parentClass, String childClass) {
        Map<String, Integer> classNMA= new HashMap<String, Integer>();;
        for (OOMClass oomClass : classList) {
            classNMA.put(oomClass.getName(), oomClass.calculateNMA());
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
        for (OOMClass oomClass : classList) {
            classAMS.put(oomClass.getName(), oomClass.calculateAMS());
        }

        return classAMS;
    }

    /**
     * Sum all AMS scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total AMS score in the PsiPackage
     */
    public Integer GetPackageAMS() {
        Double ret = 0.0;
        Map<String, Double> AMSmaps = calculateAMS();

        for (Double val: AMSmaps.values())
            ret += val;

        Double average = ret / AMSmaps.size();
        return average.intValue();
    }


    /**
     * Abreu Metrics:
     * calculate the Polymorphism Factor (PF): Ratio of the number of overriding methods in a
     * class to the total possible number of overridden methods
     * @return a map: [class name: ratio of the number of override methods in this class]
     */
    public Map<String, Double> calculatePF() {
        Map<String, Double> classPF= new HashMap<String, Double>();;
        for (OOMClass oomClass : classList) {
            classPF.put(oomClass.getName(), oomClass.calculatePF());
        }

        return classPF;
    }

    /**
     * Sum all PF scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total PF score in the PsiPackage
     */
    public Integer GetPackagePF() {
        Double ret = 0.0;
        Map<String, Double> PFmaps = calculatePF();

        for (Double val: PFmaps.values())
            ret += val;
        return ret.intValue();
    }


    /**
     * Abreu Metrics:
     * calculate the Method Hiding Factor (MHF): Ratio of hidden (private or protected) 
     * methods to total methods
     * @return a map: [class name: ratio of hidden methods in this class]
     */
    public Map<String, Double> calculateMHF() {
        Map<String, Double> classMHF= new HashMap<String,Double>();

        for (OOMClass oomClass : classList) {
            classMHF.put(oomClass.getName(), oomClass.calculateMHF());
        }

        return classMHF;
    }

    /**
     * Sum all MHF scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total MHF score in the PsiPackage
     */
    public Integer GetPackageMHF() {
        Double ret = 0.0;
        Map<String, Double> MHFmaps = calculateMHF();

        for (Double val: MHFmaps.values())
            ret += val;
        return ret.intValue();
    }


    /**
     * Abreu Metrics:
     * calculate the Method Inheritance Factor (MIF): the number of inherited methods as 
     * a ratio of total methods
     * @return a map: [class name: ratio of inherited methods in this class]
     */
    public Map<String, Double> calculateMIF() {
        Map<String, Double> classMIF = new HashMap<String, Double>();;
        for (OOMClass oomClass : classList) {
            classMIF.put(oomClass.getName(), oomClass.calculateMIF());
        }

        return classMIF;
    }

    /**
     * Sum all MIF scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total MIF score in the PsiPackage
     */
    public Integer GetPackageMIF() {
        Double ret = 0.0;
        Map<String, Double> MIFmaps = calculateMIF();

        for (Double val: MIFmaps.values())
            ret += val;
        return ret.intValue();
    }


    /**
     * Abreu Metrics:
     * calculate the Attribute Hiding Factor (AHF): Ratio of hidden (private or protected) 
     * attributes to total attributes
     * @return a map: [class name: ratio of hidden attributes in this class]
     */
    public Map<String, Double> calculateAHF( ) {
        Map<String, Double> classAHF= new HashMap<String, Double>();;
        for (OOMClass oomClass : classList) {
            classAHF.put(oomClass.getName(), oomClass.calculateAHF());
        }

        return classAHF;
    }

    /**
     * Sum all AHF scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total AHF score in the PsiPackage
     */
    public Integer GetPackageAHF() {
        Double ret = 0.0;
        Map<String, Double> AHFmaps = calculateAHF();

        for (Double val: AHFmaps.values())
            ret += val;
        return ret.intValue();
    }


    /**
     * Abreu Metrics:
     * calculate the Attribute Inheritance Factor (AIF): the number of inherited attributes 
     * as a ratio of total attributes
     */
    public Map<String, Double> calculateAIF() {
        Map<String, Double> classAIF= new HashMap<String, Double>();;
        for (OOMClass oomClass : classList) {
            classAIF.put(oomClass.getName(), oomClass.calculateAIF());
        }

        return classAIF;
    }

    /**
     * Sum all AIF scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total AIF score in the PsiPackage
     */
    public Integer GetPackageAIF() {
        Double ret = 0.0;
        Map<String, Double> AIFmaps = calculateAIF();

        for (Double val: AIFmaps.values())
            ret += val;
        return ret.intValue();
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
        for (OOMClass oomClass : classList) {
            interitanceDepth = 0.0;
            OOMClass aClass = oomClass;
            while (aClass.getParent() != null) {
                interitanceDepth++;
                aClass = aClass.getParent();
            }

            classDIT.put(oomClass.getName(), interitanceDepth);
        }

        return classDIT;

    }

    /**
     * Sum all DIT scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total DIT score in the PsiPackage
     */
    public Integer GetPackageDIT() {
        Double ret = 0.0;
        Map<String, Double> DITmaps = calculateDIT();

        for (Double val: DITmaps.values())
            ret += val;
        return ret.intValue();
    }


    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Number of Children (NOC): number of subclasses belonging to a class
     * @return the number of subclasses belonging to this class
     */
    public Map<String, Double> calculateNOC() {
        Map<String, Double> classNOC= new HashMap<String, Double>();;
        for (OOMClass oomClass : classList) {
            classNOC.put(oomClass.getName(), oomClass.calculateNOC());
        }

        return classNOC;
    }

    /**
     * Sum all NOC scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total NOC score in the PsiPackage
     */
    public Integer GetPackageNOC() {
        Double ret = 0.0;
        Map<String, Double> NOCmaps = calculateNOC();

        for (Double val: NOCmaps.values())
            ret += val;
        return ret.intValue();
    }


    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Weighted Methods per Class (WMC):  the number of methods in a class
     * @return a map: [class name: the number of methods in this class]
     */
    public Map<String, Integer> calculateWMC() {
        return calculateNM();
    }

    /**
     * Sum all WMC scores in the PsiPackage which was a parameter of constructor.
     * @return a Interger: Total WMC score in the PsiPackage
     */
    public Integer GetPackageWMC() {
        Integer ret = 0;
        Map<String, Integer> WMCmaps = calculateWMC();

        for (Integer val: WMCmaps.values())
            ret += val;
        return ret;
    }

}