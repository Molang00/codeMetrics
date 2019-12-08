package com.SoftwareMatrix;

import com.intellij.openapi.editor.Document;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.lang.Math;
import java.util.Map.Entry;

/**
 *  class level calculating
 */
public class OOMClass {
    private String name;
    private List<OOMMethod> methodList;
    private List<OOMAttribute> attributeList;
    private Integer ClassLength;
    OOMClass parent;
    List<OOMClass> childrenList;


    OOMClass(@NotNull PsiClass psiClass) {
        // TODO
        name = psiClass.getName();

        //attributeList
        methodList = new ArrayList<>();
        for(PsiMethod p : psiClass.getMethods()) {
            OOMMethod oomMethod = new OOMMethod();
            oomMethod.setName(p.getName());
            PsiModifierList modifierList = p.getModifierList();
            oomMethod.setPrivate(modifierList.hasModifierProperty(PsiModifier.PRIVATE));
            oomMethod.setPublic(modifierList.hasModifierProperty(PsiModifier.PUBLIC));
            oomMethod.setProtected(modifierList.hasModifierProperty(PsiModifier.PROTECTED));
            oomMethod.setOverride(p.findSuperMethods().length > 0);
            methodList.add(oomMethod);
        }

        //attributeList
        attributeList = new ArrayList<>();
        for(PsiField p : psiClass.getFields()) {
            OOMAttribute oomAttribute = new OOMAttribute();
            PsiModifierList modifierList = p.getModifierList();
            oomAttribute.setPrivate(modifierList.hasModifierProperty(PsiModifier.PRIVATE));
            oomAttribute.setPublic(modifierList.hasModifierProperty(PsiModifier.PUBLIC));
            oomAttribute.setProtected(modifierList.hasModifierProperty(PsiModifier.PROTECTED));
            attributeList.add(oomAttribute);
        }

        //ClassLength
        System.out.println("CLASSLENGTH");
        Document document = PsiDocumentManager.getInstance(psiClass.getProject()).getDocument(psiClass.getContainingFile());
        ClassLength = document.getLineNumber(psiClass.getTextRange().getEndOffset()) - document.getLineNumber(psiClass.getTextRange().getStartOffset()) + 1;

        //parent
        if(psiClass.getParent() instanceof PsiClass) {
            parent = new OOMClass((PsiClass) psiClass.getParent());
        }
        else {
            parent = null;
        }

        //childrenList
        for(PsiClass c : psiClass.getInnerClasses()) {
            childrenList.add(new OOMClass(c));
        }
    }

    public List<OOMMethod> getMethodList() {
        return this.methodList;
    }

    public List<OOMAttribute> getAttributeList() {
        return this.attributeList;
    }

    public Integer getClassLength() {
        return this.ClassLength;
    }

    public OOMClass getParent() {
        return this.parent;
    }

    public List<OOMClass> getChildrenList() {
        return this.childrenList;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Public Methdos (PM): the number of public methods in a class
     * @return a map: [class name: number of public methods in this class]
     */
    public int calculatePM() {
        int classPM = 0;

        for (OOMMethod method:methodList) {
            if (method.isPublic()) {
                classPM++;
            }
        }

        return classPM;
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods (NM): count all the methods including public, 
     * private , protected of a class
     * @return a map: [class name: the number of all methods in this class]
     */
    public int calculateNM() {
        return methodList.size();
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Public Variables per class (NPV): the number of public 
     * variables in a class
     * @return a map: [class name: the number of public variables in this class]
     */
    public int calculateNPV() {
        int classNPV = 0;

        for (OOMAttribute attribute:attributeList) {
            if (attribute.isPublic()) {
                classNPV++;
            }
        }

        return classNPV;
    }

    /**
     * Lorenz Kidd metrics:
     * Number of Variables per class (NV): count all the variables which are public, 
     * private , protected of a class
     * @return a map: [class name: the number of all variables in this class]
     */
    public int calculateNV( ) {
        return attributeList.size();
    }

    
    /**
     * calculate the Number of Public and Protected attributes (NPPV)
     */
    public int calculateNPPV() {
        int classNPPV = 0;

        for (OOMAttribute attribute:attributeList) {
            if (!attribute.isPrivate()) {
                classNPPV++;
            }
        }

        return classNPPV;
    }

    /**
     * calculate the Number of Public and Protected methods (NPPM)
     */
    public int calculateNPPM() {
        int classNPPM = 0;

        for (OOMMethod method:methodList) {
            if (!method.isPrivate()) {
                classNPPM++;
            }
        }

        return classNPPM;
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods Inherited by a subclass (NMI): the number of 
     * methods inherited by a subclass
     * @return a map: [class name: the number of methods inherited by a subclass in this class]
     */
    public int calculateNMI() {
        return this.calculateNPPM();
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods override by a subclass(NMO): counts the number of override 
     * methods of a class
     * @return a map: [class name: the number of overridden methods in this class]
     */
    public int calculateNMO( ) {
        int classNMO = 0;

        for (OOMMethod method:methodList) {
            if (method.isOverride()) {
                classNMO++;
            }
        }

        return classNMO;
    }

    /**
     * Lorenz Kidd metrics:
     * calculate the Number of Methods Added by subclasses (NMA)
     * @return the number of methods subclass added
     */
    public int calculateNMA() {
        int classNMA = 0;

        for (OOMClass child:childrenList) {
            List<OOMMethod> childMethodList = child.getMethodList();

            for (OOMMethod childMethod:childMethodList) {
                for (OOMMethod parentMethod:this.methodList) {
                    if (childMethod.getName().equals(parentMethod.getName())) {
                        continue;
                    }
                    classNMA++;
                }
            }
        }

        return classNMA;
    }

    /**
     * Lorenz Kidd metrics:
     * Average method size of class : Ratio of the non comment, non blank source lines 
     * divided by the number of methods in the class
     * in map format: [class name: number of lines]
     * @return a map: [class name: the average method size in this class]
     */
    public double calculateAMS(){

      return (double)(this.getClassLength()/calculateNM());
    }

    /**
     * Abreu Metrics:
     * calculate the Polymorphism Factor (PF): Ratio of the number of overriding methods in a
     * class to the total possible number of overridden methods
     * @return a map: [class name: ratio of the number of override methods in this class]
     */
    public double calculatePF() {
        if (getParent() == null) {
            return 0;
        }

        return (double)calculateNMO()/getParent().calculateNPV();
    }

    /**
     * Abreu Metrics:
     * calculate the Method Hiding Factor (MHF): Ratio of hidden (private or protected) 
     * methods to total methods
     * @return a map: [class name: ratio of hidden methods in this class]
     */
    public double calculateMHF() {
        return (double)((calculateNM()-calculatePM())/calculateNM());
    }

    /**
     * Abreu Metrics:
     * calculate the Attribute Hiding Factor (AHF): Ratio of hidden (private or protected) 
     * attributes to total attributes
     * @return a map: [class name: ratio of hidden attributes in this class]
     */
    public double calculateAHF() {
        return (double)((calculateNV()-calculateNPV())/calculateNV());
    }

    /**
     * Chidamber and Kemerer Metrics:
     * calculate the Number of Children (NOC): number of subclasses belonging to a class
     * @return the number of subclasses belonging to this class
     */
    public double calculateNOC(){

        return (double)(childrenList.size());
    }
    /**
     * Abreu Metrics:
     * calculate the Attribute Inheritance Factor (AIF): the number of inherited attributes 
     * as a ratio of total attributes
     */
    public double calculateAIF() {
        if (getParent() == null) {
            return 0;
        }
        return (double)((getParent().calculateNPPV())/(calculateNV() + getParent().calculateNPPV()));
    }

    /**
     * Abreu Metrics:
     * calculate the method Inheritance Factor (MIF): the number of inherited methods
     * as a ratio of total methods
     */
    public double calculateMIF() {
        if (getParent() == null) {
            return 0;
        }
        return (double)((getParent().calculateNPPM())/(this.calculateNM()));
    }



}
