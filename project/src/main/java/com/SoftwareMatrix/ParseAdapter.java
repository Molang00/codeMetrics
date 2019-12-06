package com.SoftwareMatrix;

import android.os.SystemPropertiesProto;
import com.intellij.ide.projectView.ProjectViewSettings;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.ide.projectView.impl.nodes.ProjectViewDirectoryHelper;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
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


    /**
     * Returns the List of PsiPackages which are in target project
     *
     * @param project target project
     *
     * @return List of PsiPackages in that Project
     */
    public static List<PsiPackage> getPrjoectPackages (@NotNull Project project) {
        Project myProject = project;
        ViewSettings viewSettings = ProjectViewSettings.DEFAULT;

        final List<VirtualFile> sourceRoots = new ArrayList<VirtualFile>();
        final ProjectRootManager projectRootManager = ProjectRootManager.getInstance(myProject);
        ContainerUtil.addAll(sourceRoots, projectRootManager.getContentSourceRoots());

        final PsiManager psiManager = PsiManager.getInstance(myProject);
        final List<AbstractTreeNode> children = new ArrayList<AbstractTreeNode>();
        final Set<PsiPackage> topLevelPackages = new HashSet<PsiPackage>();

        for (final VirtualFile root : sourceRoots) {
            final PsiDirectory directory = psiManager.findDirectory(root);
            if (directory == null) {
                continue;
            }
            final PsiPackage directoryPackage = JavaDirectoryService.getInstance().getPackage(directory);
            if (directoryPackage == null || PackageUtil.isPackageDefault(directoryPackage)) {
                // add subpackages
                final PsiDirectory[] subdirectories = directory.getSubdirectories();
                for (PsiDirectory subdirectory : subdirectories) {
                    final PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(subdirectory);

                    if (aPackage != null && !PackageUtil.isPackageDefault(aPackage)) {
                        PsiPackage[] subPackages = aPackage.getSubPackages();
                        for (PsiPackage p : subPackages) {
                            System.out.println(p); // PsiPackage:com.google, PsiPackage:com.SoftwareMatrix, ... etc
                        }

                        topLevelPackages.add(aPackage); // Toplevels. com, META-INF, ... etc
                    }
                }
                // add non-dir items
                children.addAll(ProjectViewDirectoryHelper.getInstance(myProject).getDirectoryChildren(directory, viewSettings, false));
            } else {
                // this is the case when a source root has package prefix assigned
                topLevelPackages.add(directoryPackage);
            }
        }

        return new ArrayList<PsiPackage>(topLevelPackages);
    }

    /**
     * Returns the map of attributes of method in given class
     * Attibutes contains information about access modifier, is override
     * For access modifier, 0: private, 1: public, 2: protected, 3: none
     * For override, 0: not override, 1: override
     *
     * @param psiClass target class
     *
     * @return map of attributes of given method
     *         Attibutes contains information about access modifier, is override
     */
    public static Map<String, int[]> getMethodAttribute(@NotNull PsiClass psiClass) {
        Map<String, int[]> ret = new HashMap<String, int[]>();
        for(PsiMethod p : psiClass.getMethods()) {
            String name = p.getName();
            int[] attr = new int[2];
            PsiModifierList modifierList = p.getModifierList();
            boolean isPrivate = modifierList.hasModifierProperty(PsiModifier.PRIVATE);
            boolean isPublic = modifierList.hasModifierProperty(PsiModifier.PUBLIC);
            boolean isProtected = modifierList.hasModifierProperty(PsiModifier.PROTECTED);
            attr[0] = isPrivate ? 0 : isPublic ? 1 : isProtected ? 2 : 3;
            attr[1] = p.findSuperMethods().length > 0 ? 1 : 0;
            ret.put(name, attr);
        }
        return ret;
    }

    /**
     * Returns map with class name as key and methods as value for given package
     *
     * @param psiPackage target package
     *
     * @return map with class name as key and methods as value for given package
     */
    public static Map<String, Map> getClassMethod(@NotNull PsiPackage psiPackage) {
        Map<String, Map> ret = new HashMap<>();
        for(PsiClass p : psiPackage.getClasses()) {
            String name = p.getName();
            Map<String, int[]> attr = getMethodAttribute(p);
            ret.put(name, attr);
        }
        return ret;
    }

}
