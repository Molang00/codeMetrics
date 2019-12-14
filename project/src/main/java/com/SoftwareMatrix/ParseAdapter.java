package com.SoftwareMatrix;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 *   Contains functions related to parsing
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
     * get branch PsiElements - If/While/DoWhile/For/Switch/Foreach - as list in class.
     * In Switch, default case also be added but need to be filtered after.
     *
     * @param _class : a class to get branches
     * @return the set of PsiElement
     */
    public static Set<PsiElement> getBranch(@NotNull PsiClass _class) {
        Set<PsiElement> branches = new HashSet<>();
        for(PsiMethod m : _class.getConstructors()) {
            branches.addAll(getBranchMethod(m));
        }
        for(PsiMethod m : _class.getMethods()) {
            branches.addAll(getBranchMethod(m));
        }
        return branches;
    }

    /**
     * get branch PsiElements - If/While/DoWhile/For/Switch/Foreach - as list in method.
     * In Switch, default case also be added but need to be filtered after.
     *
     * @param method : a method to get branches
     * @return the set of PsiElement
     */
    public static Set<PsiElement> getBranchMethod(@NotNull PsiMethod method) {
        Set<PsiElement> branch = new HashSet<>();
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
     * @return set of operands
     */
    public static Set<PsiElement> getOperandsExp(PsiExpression exp)
    {
        Set<PsiElement> operands = new HashSet<>();
        //Array Access
        if(exp instanceof PsiArrayAccessExpression) {
            operands.addAll(getOperandsExp(((PsiArrayAccessExpression) exp).getArrayExpression()));
            operands.addAll(getOperandsExp(((PsiArrayAccessExpression) exp).getIndexExpression()));
        }
        //Array Initializer???
        //Assign
        else if(exp instanceof PsiAssignmentExpression) {
            operands.addAll(getOperandsExp(((PsiAssignmentExpression) exp).getLExpression()));
            operands.addAll(getOperandsExp(((PsiAssignmentExpression) exp).getRExpression()));
        }
        //Binary
        else if(exp instanceof PsiBinaryExpression) {
            operands.addAll(getOperandsExp(((PsiBinaryExpression) exp).getLOperand()));
            operands.addAll(getOperandsExp(((PsiBinaryExpression) exp).getROperand()));
        }
        //Call Expression would be dealt through method call expression
        //Class Object Access
        else if(exp instanceof PsiClassObjectAccessExpression)
            operands.add(((PsiClassObjectAccessExpression) exp).getOperand());
            //Condition
        else if(exp instanceof PsiConditionalExpression) {
            operands.addAll(getOperandsExp(((PsiConditionalExpression) exp).getCondition()));
            operands.addAll(getOperandsExp(((PsiConditionalExpression) exp).getThenExpression()));
            operands.addAll(getOperandsExp(((PsiConditionalExpression) exp).getElseExpression()));
        }
        //Functional Expression???
        //Instance of
        else if(exp instanceof PsiInstanceOfExpression) {
            operands.addAll(getOperandsExp(((PsiInstanceOfExpression) exp).getOperand()));
            operands.add(((PsiInstanceOfExpression) exp).getCheckType());
        }
        //Lambda
        else if(exp instanceof PsiLambdaExpression) {
            operands.addAll(Arrays.asList((((PsiLambdaExpression) exp).getParameterList()).getParameters()));
            if(((PsiLambdaExpression) exp).getBody() instanceof PsiCodeBlock) {
                PsiCodeBlock pcb = (PsiCodeBlock)((PsiLambdaExpression) exp).getBody();
                if(pcb != null)
                    for(PsiStatement s : pcb.getStatements())
                        operands.addAll(getOperandsStmt(s));
            }
            else if(((PsiLambdaExpression) exp).getBody() instanceof PsiExpression)
                operands.addAll(getOperandsExp((PsiExpression)((PsiLambdaExpression) exp).getBody()));
        }
        //literal?
        //Method call
        else if(exp instanceof PsiMethodCallExpression) {
            for(PsiExpression e : ((PsiMethodCallExpression) exp).getArgumentList().getExpressions())
                operands.addAll(getOperandsExp(e));
        }
        //Method Reference ???
        //new(constructor) expression ???
        // ()
        else if(exp instanceof PsiParenthesizedExpression)
            operands.addAll(getOperandsExp(((PsiParenthesizedExpression) exp).getExpression()));
        else if(exp instanceof PsiPolyadicExpression) {
            for(PsiExpression e : ((PsiPolyadicExpression) exp).getOperands())
                operands.addAll(getOperandsExp(e));
        }
        else if(exp instanceof PsiPostfixExpression)
            operands.addAll(getOperandsExp(((PsiPostfixExpression) exp).getOperand()));
        else if(exp instanceof PsiResourceExpression)
            operands.addAll(getOperandsExp(((PsiResourceExpression) exp).getExpression()));
        else if(exp instanceof PsiTypeCastExpression)
            operands.addAll(getOperandsExp(((PsiTypeCastExpression) exp).getOperand()));
        else if(exp instanceof PsiUnaryExpression)
            operands.addAll(getOperandsExp(((PsiUnaryExpression) exp).getOperand()));
        else
            operands.add(exp);

        return operands;
    }

    /**
     * get Operands from PsiStatement for Halstead complexity
     * @param stmt : PsiStatement
     * @return Set of PsiElement = Set of operands. allow duplication.
     */
    public static Set<PsiElement> getOperandsStmt(@NotNull PsiStatement stmt)
    {
        Set<PsiElement> operands = new HashSet<>();
        //Assertion
        if(stmt instanceof PsiAssertStatement)
            operands.addAll(getOperandsExp(((PsiAssertStatement) stmt).getAssertCondition()));
            //Block
        else if(stmt instanceof PsiBlockStatement) {
            PsiCodeBlock pcb = ((PsiBlockStatement) stmt).getCodeBlock();
            for(PsiStatement s : pcb.getStatements())
                operands.addAll(getOperandsStmt(s));
        }
        //Break, continue, label
        else if(stmt instanceof PsiBreakStatement)
            operands.add(((PsiBreakStatement) stmt).getLabelIdentifier());
        else if(stmt instanceof PsiContinueStatement)
            operands.add(((PsiContinueStatement) stmt).getLabelIdentifier());
        else if(stmt instanceof PsiLabeledStatement) {
            PsiStatement lStmt = ((PsiLabeledStatement) stmt).getStatement();
            if(lStmt != null)
                operands.addAll(getOperandsStmt(lStmt));
        }
        //Variable declaration
        else if(stmt instanceof PsiDeclarationStatement)
            operands.addAll(Arrays.asList(((PsiDeclarationStatement) stmt).getDeclaredElements()));
            //Do While
        else if(stmt instanceof PsiDoWhileStatement) {
            operands.addAll(getOperandsExp(((PsiDoWhileStatement) stmt).getCondition()));
            PsiStatement body = ((PsiDoWhileStatement) stmt).getBody();
            if(body != null)
                operands.addAll(getOperandsStmt(body));
        }
        //Expression List
        else if(stmt instanceof PsiExpressionListStatement) {
            for(PsiExpression e : ((PsiExpressionListStatement) stmt).getExpressionList().getExpressions())
                operands.addAll(getOperandsExp(e));
        }
        //Expression
        else if(stmt instanceof PsiExpressionStatement)
            operands.addAll(getOperandsExp(((PsiExpressionStatement) stmt).getExpression()));
            //For
        else if(stmt instanceof PsiForStatement) {
            operands.addAll(getOperandsExp(((PsiForStatement) stmt).getCondition())); //Need to check duplicate with Initialization
            PsiStatement init = ((PsiForStatement) stmt).getInitialization();
            if(init != null)
                operands.addAll(getOperandsStmt(init));
            PsiStatement body = ((PsiForStatement) stmt).getBody();
            if(body != null)
                operands.addAll(getOperandsStmt(body));
        }
        //Foreach
        else if(stmt instanceof PsiForeachStatement) {
            operands.addAll(getOperandsExp(((PsiForeachStatement) stmt).getIteratedValue()));
            operands.add(((PsiForeachStatement) stmt).getIterationParameter());
            PsiStatement body = ((PsiForeachStatement) stmt).getBody();
            if(body != null)
                operands.addAll(getOperandsStmt(body));
        }
        //If
        else if(stmt instanceof PsiIfStatement) {
            operands.addAll(getOperandsExp(((PsiIfStatement) stmt).getCondition()));
            PsiStatement thenBranch = ((PsiIfStatement) stmt).getThenBranch();
            if(thenBranch != null)
                operands.addAll(getOperandsStmt(thenBranch));
            PsiStatement elseBranch = ((PsiIfStatement) stmt).getElseBranch();
            if (elseBranch != null)
                operands.addAll(getOperandsStmt(elseBranch));
        }
        //return
        else if(stmt instanceof PsiReturnStatement)
            operands.addAll(getOperandsExp(((PsiReturnStatement) stmt).getReturnValue()));
            //Switch
        else if(stmt instanceof PsiSwitchStatement){
            operands.addAll(getOperandsExp(((PsiSwitchStatement) stmt).getExpression()));
            PsiCodeBlock pcb = ((PsiSwitchStatement) stmt).getBody();
            if(pcb != null)
                for(PsiStatement s : pcb.getStatements())
                    operands.addAll(getOperandsStmt(s));
        }
        else if(stmt instanceof PsiSwitchLabelStatement)
        {
            PsiExpressionList eList = ((PsiSwitchLabelStatement) stmt).getCaseValues();
            if(eList != null) {
                for (PsiExpression e : eList.getExpressions()){
                    operands.addAll(getOperandsExp(e));
                }
            }

            PsiSwitchBlock switchBlock = ((PsiSwitchLabelStatement) stmt).getEnclosingSwitchBlock();
            PsiCodeBlock pcb = switchBlock == null ? null : switchBlock.getBody();
            if(pcb != null) {
                for (PsiStatement s : pcb.getStatements()) {
                    if (!(s instanceof PsiSwitchLabelStatement)) {
                        operands.addAll(getOperandsStmt(s));
                    }
                }
            }
        }
        //synchronized
        else if(stmt instanceof PsiSynchronizedStatement)
        {
            PsiCodeBlock body = ((PsiSynchronizedStatement) stmt).getBody();
            if(body != null) {
                for (PsiStatement s : body.getStatements())
                    operands.addAll(getOperandsStmt(s));
            }
        }
        //throw
        else if(stmt instanceof PsiThrowStatement)
            operands.addAll(getOperandsExp(((PsiThrowStatement) stmt).getException()));
            //try catch finally
        else if(stmt instanceof PsiTryStatement)
        {
            PsiCodeBlock tryBlock = ((PsiTryStatement) stmt).getTryBlock();
            if(tryBlock != null) {
                for (PsiStatement s : tryBlock.getStatements())
                    operands.addAll(getOperandsStmt(s));
            }
            for(PsiCodeBlock pcb : ((PsiTryStatement) stmt).getCatchBlocks())
            {
                for(PsiStatement s : pcb.getStatements())
                    operands.addAll(getOperandsStmt(s));
            }
            PsiCodeBlock finallyBlock = ((PsiTryStatement) stmt).getFinallyBlock();
            if (finallyBlock != null) {

                for (PsiStatement s : finallyBlock.getStatements())
                    operands.addAll(getOperandsStmt(s));
            }
            operands.addAll(Arrays.asList(((PsiTryStatement) stmt).getCatchBlockParameters()));
        }
        //while
        else if(stmt instanceof PsiWhileStatement)
        {
            operands.addAll(getOperandsExp(((PsiWhileStatement) stmt).getCondition()));
            PsiStatement body = ((PsiWhileStatement) stmt).getBody();
            if(body != null)
                operands.addAll(getOperandsStmt(body));
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

    /**
     * get List of operands from class, checking fields, methods, inner class with following order.
     * @param _class : class to calculate the list of operands
     * @return List of Operands
     */
    public static Set<PsiElement> getOperands(@NotNull PsiClass _class)
    {
        Set<PsiElement> operands = new HashSet<>(Arrays.asList(_class.getAllFields()));       //All global variables

        for(PsiMethod m : _class.getConstructors()) {
            PsiCodeBlock body = m.getBody();
            if(body == null) continue;
            for(PsiStatement s : body.getStatements()) {
                operands.addAll(getOperandsStmt(s));
            }
        }
        for(PsiMethod m : _class.getAllMethods())
        {
            PsiCodeBlock body = m.getBody();
            if(body == null) continue;
            for(PsiStatement s : body.getStatements()) {
                operands.addAll(getOperandsStmt(s));
            }
        }
        for(PsiClass c : _class.getAllInnerClasses())               //recursion for all inner-class
            operands.addAll(getOperands(c));

        for(; operands.remove(null); );

        return operands;
    }

    /**
     * get List of operands from a given method
     * @param _method : method to calculate list of operands
     * @return list of operands
     */
    public static Set<PsiElement> getOperatorsMethod(PsiMethod _method)
    {
        Set<PsiElement> operators = new HashSet<>();

        // Functions calls are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiMethodCallExpression.class));

        // All looping statements are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiIfStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiWhileStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiDoWhileStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiForStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiForeachStatement.class));

        // In control construct switch ( ) {case:...}, switch as well as all the case statements are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiSwitchStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiSwitchLabelStatement.class));

        // The reserve words like return, default, continue, break, sizeof, etc., are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiReturnStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiContinueStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiBreakStatement.class));
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiInstanceOfExpression.class));

        // All the brackets, commas, and terminators are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfType(_method, PsiArrayAccessExpression.class));

        // All conditional expressions are considered as operators.
        operators.addAll(PsiTreeUtil.findChildrenOfAnyType(_method, PsiConditionalExpression.class));

        // Normal binary/Assign expressions
        for(PsiElement e : PsiTreeUtil.findChildrenOfType(_method, PsiAssignmentExpression.class)) {
            operators.add(((PsiAssignmentExpression)e).getOperationSign());
        }
        for(PsiElement e : PsiTreeUtil.findChildrenOfType(_method, PsiBinaryExpression.class)) {
            operators.add(((PsiBinaryExpression)e).getOperationSign());
        }
        for(PsiElement e : PsiTreeUtil.findChildrenOfType(_method, PsiUnaryExpression.class)) {
            operators.add(((PsiUnaryExpression)e).getOperationSign());
        }

        // No way to count . ->
        return operators;
    }

    /**
     * get List of operator in given class. It checks Constructor and methods
     * @param _class : class to calculate list of operator
     * @return list of operator
     */
    public static Set<PsiElement> getOperators(@NotNull PsiClass _class)
    {
        Set<PsiElement> operators = new HashSet<>();
        for(PsiMethod m : _class.getConstructors()) {
            if(m != null) {
                System.out.println("branch const name : " + m.getName());
                operators.addAll(getOperatorsMethod(m));
            }
        }
        for(PsiMethod m : _class.getAllMethods()) {
            if(m != null) {
                System.out.println("branch function name : " + m.getName());
                operators.addAll(getOperatorsMethod(m));
            }
        }
        return operators;
    }

    /**
     * return lines of code in a class
     * @param _class : psiClass object that want to get loc.
     * @return lines of code of its class.
     */
    public static int getLoc(@NotNull PsiClass _class) {
        String str = _class.getText();
        if(str == null) {
            return 0;
        }
        else {
            String[] lines = str.split("\r\n|\r|\n");
            return  lines.length;
        }
    }

    /**
     * return logical lines of code in a class
     * @param _class : psiClass object that want to get lloc.
     * @return logical lines of code of its class.
     */
    public static int getLLoc(@NotNull PsiClass _class) {
        return PsiTreeUtil.findChildrenOfType(_class, PsiStatement.class).size();
    }

    /**
     *
     * @param _class
     * @return
     */
    public static int getCLoc(@NotNull PsiClass _class) {
        Set<PsiComment> set = new HashSet<>();
        int cloc = 0;

        set.addAll(PsiTreeUtil.findChildrenOfType(_class, PsiComment.class));


        for (PsiComment comment : set) {
            String str = comment.getText();
            String[] lines = str.split("\r\n|\r|\n");
            cloc += lines.length;
        }

        return cloc;
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
     * Returns the Set of PsiPackages which are in target project
     *
     * @param project target project
     *
     * @return Set of PsiPackages in that Project
     */
    public static Set<PsiPackage> getProjectPackages (@NotNull Project project) {
        Set<PsiPackage> ret = new HashSet<>();

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

        return Set.copyOf(ret);

    }
}