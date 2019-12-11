package com.SoftwareMatrix;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class OOMParseTest extends LightJavaCodeInsightFixtureTestCase {

    private String basePath = "./src/test/resources/testcases/";

    /*
        This is an helper method to make PsiFile using path.
        However, this doesn't give the original file of filepath.
        This method just read the content of file at filepath,
        and returns PsiFile with that content.

        So, its return file is 'aaa.java' (I think this is just the default name)
        Anyway, the important point is that this doesn't give the original file of filepath.
     */
    private PsiFile makePsiFile(String filepath) {
        byte[] code = null;
        try {
            File file = new File(filepath);
            FileInputStream file_in = new FileInputStream(file);
            code = new byte[(int) file.length()];
            if(file_in.read(code) == -1) {
                fail(); // Unexpected EOF
            }
            file_in.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            fail();
        }

        return myFixture.configureByText(JavaFileType.INSTANCE, new String(code));
    }

    @Test
    public void testGetContainingClass() {
        String code1 = basePath + "code1.java";
        PsiFile psiFile = makePsiFile(code1);

        Collection<PsiClass> cla = PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class);

        for (PsiClass p : cla) {
            for (PsiMethod m : p.getMethods()) {
                assertEquals(p, ParseAdapter.getContainingClass(m));
            }
        }

    }


    @Test
    public void testGetBranch() {
        String code1 = basePath + "code1.java";
        PsiFile psiFile = makePsiFile(code1);

        Collection<PsiClass> classes = PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class);


        for(PsiClass _class: classes) {
            // There is two methods in "code1.java". "main" and "AddOne"

            Set<PsiElement> set = ParseAdapter.getBranch(_class);
            assertContainsElements(set); // two methods both contain one if statement
        }

    }

    @Test
    public void testGetTokensType() {
        String code1 = basePath + "code1.java";
        PsiFile psiFile = makePsiFile(code1);

        HashSet<PsiClass> set1 = new HashSet<>();
        HashSet<PsiClass> set2 = new HashSet<>();

        PsiElement[] elems = psiFile.getChildren();
        for(PsiElement e: elems) {
            if (e instanceof PsiClass)
                set1.add( (PsiClass) e);
        }

        Collection<?> col = ParseAdapter.getTokensType(psiFile, PsiClass.class);
        for (Object o : col) {
            PsiClass pc = (PsiClass) o;
            set2.add(pc);
        }

        assertEquals(set1, set2);
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
