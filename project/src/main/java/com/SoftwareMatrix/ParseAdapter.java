package com.SoftwareMatrix;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/*
    Contains functions related to parsing
 */
public class ParseAdapter {
    /**
     * Returns the containing method of element
     *
     * @param elem target element
     * @return PsiMethod instance of containing method, or null if failed
     */
    public static PsiMethod getContainingMethod(@NotNull PsiElement elem) {
        return PsiTreeUtil.getParentOfType(elem, PsiMethod.class, false);
    }

    /**
     * Returns the containing class of element
     *
     * @param elem target element
     * @return PsiClass instance of containing class, or null if failed
     */
    public static PsiClass getContainingClass(@NotNull PsiElement elem) {
        return PsiTreeUtil.getParentOfType(elem, PsiClass.class, false);
    }

    /**
     * Returns the containing package of element
     *
     * @param elem target element
     * @return PsiPackage instance of containing package, or null if failed
     */
    public static PsiPackage getContainingPackage(@NotNull PsiElement elem) {
        PsiPackage pElem = PsiTreeUtil.getParentOfType(elem, PsiPackage.class, false);
        PsiClass cElem = getContainingClass(elem);
        if (pElem == null && cElem != null) {
            pElem = JavaDirectoryService.getInstance().getPackage(
                    cElem.getContainingFile().getContainingDirectory());
        }
        return pElem;
    }

    /**
     * Returns the list of instances of given class type
     * that exists in target element
     *
     * @param elem target element
     * @param type target class type (e.g PsiIfStatement.class)
     * @return list of instances of given class type
     */
    public static Collection<?> getTokensType(@NotNull PsiElement elem,
                                              Class<? extends PsiElement> type) {
        return Collections.unmodifiableCollection(
                PsiTreeUtil.findChildrenOfType(elem, type));
    }

    /**
     * Returns the list of instances of given class type
     * that exists in containing method of target element
     *
     * @param elem target element
     * @param type target class type (e.g PsiIfStatement.class)
     * @return list of instances of given class type
     * that exists in containing method of target element
     * empty list if containing method does not exist
     */
    public static Collection<?> getTokensTypeMethod(@NotNull PsiElement elem,
                                                    Class<? extends PsiElement> type) {
        PsiMethod m = getContainingMethod(elem);
        if (m == null)
            return Collections.unmodifiableCollection(Collections.emptyList());

        return Collections.unmodifiableCollection(
                PsiTreeUtil.findChildrenOfType(m, type));
    }
/*
    public static int getBranchNum(@NotNull PsiMethod method)
    {
        int branch_num=0;
        Collection<PsiSwitchLabelStatement> switch_label = PsiTreeUtil.findChildrenOfType(method, PsiSwitchLabelStatement.class);
        branch_num += PsiTreeUtil.findChildrenOfType(method, PsiIfStatement.class).size();
        branch_num += PsiTreeUtil.findChildrenOfType(method, PsiWhileStatement.class).size();
        branch_num += PsiTreeUtil.findChildrenOfType(method, PsiDoWhileStatement.class).size();
        branch_num += PsiTreeUtil.findChildrenOfType(method, PsiForStatement.class).size();
        switch_label.removeIf(PsiSwitchLabelStatementBase::isDefaultCase);
        branch_num += switch_label.size();
        branch_num += PsiTreeUtil.findChildrenOfType(method, PsiForeachStatement.class).size();
        return branch_num;
    }
*/

    /**
     * get branch PsiElements - If/While/DoWhile/For/Switch/Foreach - as list.
     * In Switch, default case also be added but need to be filtered after.
     *
     * @param method : a method to get branches
     * @return the list of PsiElement
     */
    public static List<PsiElement> getBranch(@NotNull PsiMethod method) {
        List<PsiElement> branch = new ArrayList<PsiElement>();
        branch.addAll(PsiTreeUtil.findChildrenOfType(method, PsiIfStatement.class));
        branch.addAll(PsiTreeUtil.findChildrenOfType(method, PsiWhileStatement.class));
        branch.addAll(PsiTreeUtil.findChildrenOfType(method, PsiDoWhileStatement.class));
        branch.addAll(PsiTreeUtil.findChildrenOfType(method, PsiForStatement.class));
        branch.addAll(PsiTreeUtil.findChildrenOfType(method, PsiSwitchLabelStatement.class));
        branch.addAll(PsiTreeUtil.findChildrenOfType(method, PsiForeachStatement.class));
        return branch;
    }
/*
    public static List<PsiVariable> getOperands(@NotNull PsiClass _class)
    {
        List<PsiVariable> operands = new ArrayList<PsiVariable>();
        operands.addAll(Arrays.asList(_class.getAllFields()));       //All global variables

        for(PsiMethod m : _class.getAllMethods())
        {
            for(PsiStatement s : m.getBody().getStatements())
            {
                if(s instanceof PsiAssertStatement)
                {
                    PsiExpression cond = ((PsiAssertStatement) s).getAssertCondition();
                    cond.
                }
            }
        }
        for(PsiClass c : _class.getAllInnerClasses())
            operands.addAll(getOperands(c));

        return operands;
    }
*/
    /**
     * Returns the list of instances of given class type
     *  that exists in containing class of target element
     *
     * @param elem target element
     * @param type target class type (e.g PsiIfStatement.class)
     *
     * @return list of instances of given class type
     *         that exists in containing class of target element
     *         empty list if containing class does not exist
     */
    public static Collection<?> getTokensTypeClass(@NotNull PsiElement elem,
                                                   Class<? extends PsiElement> type) {
        PsiClass c = getContainingClass(elem);
        if(c == null)
            return Collections.unmodifiableCollection(Collections.emptyList());

        return Collections.unmodifiableCollection(
                PsiTreeUtil.findChildrenOfType(c, type));
    }

    /**
     * Returns the list of instances of given class type
     *  that exists in containing method of target element
     *
     * @param elem target element
     * @param type target class type (e.g PsiIfStatement.class)
     *
     * @return list of instances of given class type
     *         that exists in containing package of target element
     *         empty list if containing package does not exist
     */
    public static Collection<?> getTokensTypePackage(@NotNull PsiElement elem,
                                                     Class<? extends PsiElement> type) {
        PsiPackage p = getContainingPackage(elem);
        if(p == null)
            return Collections.unmodifiableCollection(Collections.emptyList());

        return Collections.unmodifiableCollection(
                PsiTreeUtil.findChildrenOfType(p, type));
    }


}
