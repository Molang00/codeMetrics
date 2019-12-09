package com.SoftwareMatrix;

import android.os.SystemPropertiesProto;
import android.provider.Settings;
import com.intellij.ide.projectView.ProjectViewSettings;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.ide.projectView.impl.nodes.ProjectViewDirectoryHelper;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;

import java.io.File;
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

    /**
     * get operands from PsiExpression
     * @param exp : psi expression
     * @return list of operands
     */
    public static List<PsiElement> getOperands_Exp(PsiExpression exp)
    {
        List<PsiElement> operands = new ArrayList<PsiElement>();
        //Array Access
        if(exp instanceof PsiArrayAccessExpression) {
            operands.addAll(getOperands_Exp(((PsiArrayAccessExpression) exp).getArrayExpression()));
            operands.addAll(getOperands_Exp(((PsiArrayAccessExpression) exp).getIndexExpression()));
        }
        //Array Initializer???
        //Assign
        else if(exp instanceof PsiAssignmentExpression) {
            operands.addAll(getOperands_Exp(((PsiAssignmentExpression) exp).getLExpression()));
            operands.addAll(getOperands_Exp(((PsiAssignmentExpression) exp).getRExpression()));
        }
        //Binary
        else if(exp instanceof PsiBinaryExpression) {
            operands.addAll(getOperands_Exp(((PsiBinaryExpression) exp).getLOperand()));
            operands.addAll(getOperands_Exp(((PsiBinaryExpression) exp).getROperand()));
        }
        //Call Expression would be dealt through method call expression
        //Class Object Access
        else if(exp instanceof PsiClassObjectAccessExpression)
            operands.add(((PsiClassObjectAccessExpression) exp).getOperand());
        //Condition
        else if(exp instanceof PsiConditionalExpression) {
            operands.addAll(getOperands_Exp(((PsiConditionalExpression) exp).getCondition()));
            operands.addAll(getOperands_Exp(((PsiConditionalExpression) exp).getThenExpression()));
            operands.addAll(getOperands_Exp(((PsiConditionalExpression) exp).getElseExpression()));
        }
        //Functional Expression???
        //Instance of
        else if(exp instanceof PsiInstanceOfExpression)
            operands.addAll(getOperands_Exp(((PsiInstanceOfExpression) exp).getOperand()));
        //Lambda
        else if(exp instanceof PsiLambdaExpression) {
            operands.addAll(Arrays.asList((((PsiLambdaExpression) exp).getParameterList()).getParameters()));
            if(((PsiLambdaExpression) exp).getBody() instanceof PsiCodeBlock) {
                PsiCodeBlock pcb = (PsiCodeBlock)((PsiLambdaExpression) exp).getBody();
                for(PsiStatement s : pcb.getStatements())
                    operands.addAll(getOperands_Stmt(s));
            }
            else if(((PsiLambdaExpression) exp).getBody() instanceof PsiExpression)
                operands.addAll(getOperands_Exp((PsiExpression)((PsiLambdaExpression) exp).getBody()));
        }
        //literal?
        //Method call
        else if(exp instanceof PsiMethodCallExpression) {
            for(PsiExpression e : ((PsiMethodCallExpression) exp).getArgumentList().getExpressions())
                operands.addAll(getOperands_Exp(e));
        }
        //Method Reference ???
        //new(constructor) expression ???
        // ()
        else if(exp instanceof PsiParenthesizedExpression)
            operands.addAll(getOperands_Exp(((PsiParenthesizedExpression) exp).getExpression()));
        else if(exp instanceof PsiPolyadicExpression) {
            for(PsiExpression e : ((PsiPolyadicExpression) exp).getOperands())
                operands.addAll(getOperands_Exp(e));
        }
        else if(exp instanceof PsiPostfixExpression)
            operands.addAll(getOperands_Exp(((PsiPostfixExpression) exp).getOperand()));
        else if(exp instanceof PsiResourceExpression)
            operands.addAll(getOperands_Exp(((PsiResourceExpression) exp).getExpression()));
        else if(exp instanceof PsiTypeCastExpression)
            operands.addAll(getOperands_Exp(((PsiTypeCastExpression) exp).getOperand()));
        else if(exp instanceof PsiUnaryExpression)
            operands.addAll(getOperands_Exp(((PsiUnaryExpression) exp).getOperand()));
        else
            operands.add(exp);

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
            if (((PsiIfStatement) stmt).getElseBranch() != null)
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
            for(PsiExpression e : ((PsiSwitchLabelStatement) stmt).getCaseValues().getExpressions()) {
                operands.addAll(getOperands_Exp(e));
            }

            PsiCodeBlock pcb = ((PsiSwitchLabelStatement) stmt).getEnclosingSwitchBlock().getBody();
            for(PsiStatement s : pcb.getStatements()) {
                if (!(s instanceof PsiSwitchLabelStatement)) {
                    operands.addAll(getOperands_Stmt(s));
                }
            }


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
            if (((PsiTryStatement) stmt).getFinallyBlock() != null) {
                for (PsiStatement s : ((PsiTryStatement) stmt).getFinallyBlock().getStatements())
                    operands.addAll(getOperands_Stmt(s));
            }
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

            for(PsiStatement s : m.getBody().getStatements()) {
                operands.addAll(getOperands_Stmt(s));
            }
        }
        for(PsiClass c : _class.getAllInnerClasses())               //recursion for all inner-class
            operands.addAll(getOperands(c));

        while(operands.remove(null));

        return operands;
    }

    public static List<PsiElement> getOperators_Method(PsiMethod _method)
    {
        List<PsiElement> operators = new ArrayList<PsiElement>();

        //Functions calls are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiMethodCallExpression.class));
        //All looping statements are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiWhileStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiDoWhileStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiForStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiForeachStatement.class));
        //In control construct switch ( ) {case:...}, switch as well as all the case statements are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiSwitchStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiSwitchLabelStatement.class));
        //The reserve words like return, default, continue, break, sizeof, etc., are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiReturnStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiContinueStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiBreakStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiInstanceOfExpression.class));
        //All the brackets, commas, and terminators are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiArrayAccessExpression.class));
        //Normal binary/Assign expressions
        for(PsiElement e : PsiTreeUtil.findChildrenOfType(_method, PsiAssignmentExpression.class)) {
            operators.add(((PsiAssignmentExpression)e).getOperationSign());
        }
        for(PsiElement e : PsiTreeUtil.findChildrenOfType(_method, PsiBinaryExpression.class)) {
            operators.add(((PsiBinaryExpression)e).getOperationSign());
        }
        //No way to count . ->
        return operators;
    }
    public static List<PsiElement> getOperators(@NotNull PsiClass _class)
    {
        List<PsiElement> operators = new ArrayList<PsiElement>();
        for(PsiMethod m : _class.getAllMethods())
            operators.addAll(getOperators_Method(m));
        return operators;
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


    /**
     * Returns the List of PsiPackages which are in target project
     *
     * @param project target project
     *
     * @return List of PsiPackages in that Project
     */
    public static List<PsiPackage> getPrjoectPackages (@NotNull Project project) {
        Set<PsiPackage> ret = new HashSet<PsiPackage>();

        Collection<VirtualFile> virtualFiles =
                FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE,
                        GlobalSearchScope.projectScope(project));
        // Get only java files which is contained by PROJECT


        for (VirtualFile vf: virtualFiles) {
            PsiFile psifile = PsiManager.getInstance(project).findFile(vf);

            if (psifile instanceof PsiJavaFile) {
                PsiJavaFile psiJavaFile = (PsiJavaFile) psifile;
                String PackageName = psiJavaFile.getPackageName();
                PsiPackage pack = JavaPsiFacade.getInstance(project).findPackage(PackageName);
                ret.add(pack);
            }
        }

        if (ret.isEmpty()) {
            System.out.println("Can not find packages!");
            System.out.println("Empty list would be returned");
        }

        return Collections.unmodifiableList(new ArrayList<PsiPackage>(ret));

    }
}
