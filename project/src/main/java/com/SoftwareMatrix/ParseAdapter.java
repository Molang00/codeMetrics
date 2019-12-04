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

    public static List<PsiElement> getOperands_Exp(PsiExpression exp)
    {
        List<PsiElement> operands = new ArrayList<PsiElement>();
        //empty now
        return operands;
    }

    /**
     * get Operands from PsiStatement for Halstead complexity
     * @param stmt : PsiStatement
     * @return List of PsiElement = List of operands. allow duplication.
     */
    public static List<PsiElement> getOperands_Stmt(@NotNull PsiStatement stmt)
    {
        List<PsiElement> operands = new ArrayList<PsiElement>();
        //Assertion
        if(stmt instanceof PsiAssertStatement)
            operands.addAll(getOperands_Exp(((PsiAssertStatement) stmt).getAssertCondition()));
        //Block
        else if(stmt instanceof PsiBlockStatement) {
            PsiCodeBlock pcb = ((PsiBlockStatement) stmt).getCodeBlock();
            for(PsiStatement s : pcb.getStatements())
                operands.addAll(getOperands_Stmt(s));
        }
        //Break, continue, label
        else if(stmt instanceof PsiBreakStatement)
            operands.add(((PsiBreakStatement) stmt).getLabelIdentifier());
        else if(stmt instanceof PsiContinueStatement)
            operands.add(((PsiContinueStatement) stmt).getLabelIdentifier());
        else if(stmt instanceof PsiLabeledStatement) {
            operands.add(((PsiContinueStatement) stmt).getLabelIdentifier());
            operands.addAll(getOperands_Stmt(((PsiLabeledStatement) stmt).getStatement()));
        }
        //Variable declaration
        else if(stmt instanceof PsiDeclarationStatement)
            operands.addAll(Arrays.asList(((PsiDeclarationStatement) stmt).getDeclaredElements()));
        //Do While
        else if(stmt instanceof PsiDoWhileStatement) {
            operands.addAll(getOperands_Exp(((PsiDoWhileStatement) stmt).getCondition()));
            operands.addAll(getOperands_Stmt(((PsiDoWhileStatement) stmt).getBody()));
        }
        //Expression List
        else if(stmt instanceof PsiExpressionListStatement) {
            for(PsiExpression e : ((PsiExpressionListStatement) stmt).getExpressionList().getExpressions())
                operands.addAll(getOperands_Exp(e));
        }
        //Expression
        else if(stmt instanceof PsiExpressionStatement)
            operands.addAll(getOperands_Exp(((PsiExpressionStatement) stmt).getExpression()));
        //For
        else if(stmt instanceof PsiForStatement) {
            operands.addAll(getOperands_Exp(((PsiForStatement) stmt).getCondition())); //Need to check duplicate with Initialization
            operands.addAll(getOperands_Stmt(((PsiForStatement) stmt).getInitialization()));
            operands.addAll(getOperands_Stmt(((PsiForStatement) stmt).getBody()));
        }
        //Foreach
        else if(stmt instanceof PsiForeachStatement) {
            operands.addAll(getOperands_Exp(((PsiForeachStatement) stmt).getIteratedValue()));
            operands.add(((PsiForeachStatement) stmt).getIterationParameter());
            operands.addAll(getOperands_Stmt(((PsiForeachStatement) stmt).getBody()));
        }
        //If
        else if(stmt instanceof PsiIfStatement) {
            operands.addAll(getOperands_Exp(((PsiIfStatement) stmt).getCondition()));
            operands.addAll(getOperands_Stmt(((PsiIfStatement) stmt).getThenBranch()));
            operands.addAll(getOperands_Stmt(((PsiIfStatement) stmt).getElseBranch()));
        }
        //return
        else if(stmt instanceof PsiReturnStatement)
            operands.addAll(getOperands_Exp(((PsiReturnStatement) stmt).getReturnValue()));
        //Switch
        else if(stmt instanceof PsiSwitchStatement){
            operands.addAll(getOperands_Exp(((PsiSwitchStatement) stmt).getExpression()));
            PsiCodeBlock pcb = ((PsiSwitchStatement) stmt).getBody();
            for(PsiStatement s : pcb.getStatements())
                operands.addAll(getOperands_Stmt(s));
        }
        else if(stmt instanceof PsiSwitchLabelStatement)
        {
            for(PsiExpression e : ((PsiSwitchLabelStatement) stmt).getCaseValues().getExpressions())
                operands.addAll(getOperands_Exp(e));
            PsiCodeBlock pcb = ((PsiSwitchLabelStatement) stmt).getEnclosingSwitchBlock().getBody();
            for(PsiStatement s : pcb.getStatements())
                operands.addAll(getOperands_Stmt(s));
        }
        //synchronized
        else if(stmt instanceof PsiSynchronizedStatement)
        {
            for(PsiStatement s : ((PsiSynchronizedStatement) stmt).getBody().getStatements())
                operands.addAll(getOperands_Stmt(s));
        }
        //throw
        else if(stmt instanceof PsiThrowStatement)
            operands.addAll(getOperands_Exp(((PsiThrowStatement) stmt).getException()));
        //try catch finally
        else if(stmt instanceof PsiTryStatement)
        {
            for(PsiStatement s : ((PsiTryStatement) stmt).getTryBlock().getStatements())
                operands.addAll(getOperands_Stmt(s));
            for(PsiCodeBlock pcb : ((PsiTryStatement) stmt).getCatchBlocks())
            {
                for(PsiStatement s : pcb.getStatements())
                    operands.addAll(getOperands_Stmt(s));
            }
            for(PsiStatement s : ((PsiTryStatement) stmt).getFinallyBlock().getStatements())
                operands.addAll(getOperands_Stmt(s));
            operands.addAll(Arrays.asList(((PsiTryStatement) stmt).getCatchBlockParameters()));
        }
        //while
        else if(stmt instanceof PsiWhileStatement)
        {
            operands.addAll(getOperands_Exp(((PsiWhileStatement) stmt).getCondition()));
            operands.addAll(getOperands_Stmt(((PsiWhileStatement) stmt).getBody()));
        }
        else
        {
            //System.out.println("Unexpected Statement Input");
            /* Consider Following statements;
            - class level declaration already dealt
            - Empty has no operand
            - Do not count import
            - package dealing
            - require/provide
            - uses statement
            - yield has no operand
             */
        }
        return operands;
    }
    public static List<PsiElement> getOperands(@NotNull PsiClass _class)
    {
        List<PsiElement> operands = new ArrayList<PsiElement>();
        operands.addAll(Arrays.asList(_class.getAllFields()));       //All global variables

        for(PsiMethod m : _class.getAllMethods())
        {
            for(PsiStatement s : m.getBody().getStatements())
                operands.addAll(getOperands_Stmt(s));
        }
        for(PsiClass c : _class.getAllInnerClasses())               //recursion for all inner-class
            operands.addAll(getOperands(c));

        return operands;
    }

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
