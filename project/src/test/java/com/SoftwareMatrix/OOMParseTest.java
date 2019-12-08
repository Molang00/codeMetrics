package com.SoftwareMatrix;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateBuilder;
import com.intellij.codeInsight.template.TemplateBuilderImpl;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.structuralsearch.StructuralSearchTemplateBuilder;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Test;
import org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class OOMParseTest extends LightJavaCodeInsightFixtureTestCase {

    private String TestResourcePath = "./src/test/resources/testcases/";

    /*
        This is an helper method to make PsiFile using path.
        However, this doesn't give the original file of filepath.
        This method just read the content of file at filepath,
        and returns PsiFile with that content.

        So, its return file is 'aaa.java' (I think this is just the default name)
        Anyway, the important point is that this doesn't give the original file of filepath.
     */
    public PsiFile MakePsiFile(String filepath) {
        byte[] code = null;
        try {
            File file = new File(filepath);
            FileInputStream file_in = new FileInputStream(file);
            code = new byte[(int) file.length()];
            file_in.read(code);
            file_in.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        return myFixture.configureByText(JavaFileType.INSTANCE, new String(code));
    }


    @Test
    public void testEmpty() {
        PsiFile psiFile = myFixture.configureByText(JavaFileType.INSTANCE, "class A {}");

        // test code

        System.out.println(PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class));
        assertNotNull(PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class));

    }


    @Test
    public void testGetContainingMethod() {
        String code1 = TestResourcePath + "code1.java";
        PsiFile psiFile = MakePsiFile(code1);

        Collection<PsiClass> cla = PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class);
        assertNotNull(PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class));
    }


    @Test
    public void testGetContainingClass() {
        String code1 = TestResourcePath + "code1.java";
        PsiFile psiFile = MakePsiFile(code1);

        ParseAdapter pa = new ParseAdapter();
        Collection<PsiClass> cla = PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class);

        for (PsiClass p : cla) {
            for (PsiMethod m : p.getMethods()) {
                assertEquals(p, pa.getContainingClass(m));
            }
        }

    }


    @Test
    public void testGetBranch() {
        String code1 = TestResourcePath + "code1.java";
        PsiFile psiFile = MakePsiFile(code1);

        ParseAdapter pa = new ParseAdapter();
        Collection<PsiMethod> methods = PsiTreeUtil.findChildrenOfType(psiFile, PsiMethod.class);


        for(PsiMethod method: methods) {
            // There is two methods in "code1.java". "main" and "AddOne"

            List<PsiElement> list = pa.getBranch(method);
            assertContainsElements(list); // two methods both contain one if statement
        }

    }

    @Test
    public void testGetTokensType() {
        String code1 = TestResourcePath + "code1.java";
        PsiFile psiFile = MakePsiFile(code1);

        HashSet<PsiClass> set1 = new HashSet<>();
        HashSet<PsiClass> set2 = new HashSet<>();
        ParseAdapter pa = new ParseAdapter();

        PsiElement[] elems = psiFile.getChildren();
        for(PsiElement e: elems) {
            if (e instanceof PsiClass)
                set1.add( (PsiClass) e);
        }

        Collection<?> col = pa.getTokensType(psiFile, PsiClass.class);
        Iterator<?> it  = col.iterator();
        while (it.hasNext()) {
            PsiClass pc = (PsiClass) it.next();
            set2.add(pc);
        }

        assertTrue(set1.equals(set2));
    }


    // I think ParserAdapter.getContainingPackage works well on particular circumstance
//    @Test
//    public void testGetContainingPackage() {
//        String code1 = TestResourcePath + "code1.java";
//        PsiFile psiFile = MakePsiFile(code1);
//
//        assertTrue(psiFile instanceof PsiJavaFile); // We made java PsiFile from java file
//
//        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
//        System.out.println("psiJavaFile: "+psiJavaFile.getPackageName()); // "com.testcode" is printed.
//
//        ParseAdapter pa = new ParseAdapter();
//        Collection<PsiMethod> methods = PsiTreeUtil.findChildrenOfType(psiFile, PsiMethod.class);
//
//        for(PsiMethod method: methods) {
//            System.out.println("method: " + pa.getContainingPackage(method));
//
//            List<PsiElement> list = pa.getBranch(method);
//            for (PsiElement elem : list)
//                System.out.println("elem: " + pa.getContainingPackage(elem));
//        }
//
//    }

}
