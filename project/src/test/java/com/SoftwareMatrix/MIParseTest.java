package com.SoftwareMatrix;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Test;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class MIParseTest extends LightJavaCodeInsightFixtureTestCase {

    private PsiClass[] getPsiClasses(String path) {
        byte[] data = null;

        try {
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            data = new byte[(int) f.length()];
            if(fis.read(data) == -1) {
                fail(); // Unexpected EOF
            }
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        String str = null;
        str = new String(data, StandardCharsets.UTF_8);

        PsiFile psiFile = myFixture.configureByText(JavaFileType.INSTANCE, str);
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
        return psiJavaFile.getClasses();
    }

    @Test
    public void testMISimple() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase1.txt");

        for (PsiClass psiClass : psiClasses) {
            Set<PsiElement> operands = ParseAdapter.getOperands(psiClass);
            System.out.println("test simple operand size = " + operands.size());
            int j = 0;
            for(PsiElement elem: operands) {
                System.out.println("operand " + (++j) + " : " + elem.toString());
            }
            assertEquals(operands.size(), 4);

            Set<PsiElement> operators = ParseAdapter.getOperators(psiClass);
            System.out.println("test simple operator size = " + operators.size());
            j = 0;
            for(PsiElement elem: operators) {
                System.out.println("operators " + (++j) + " : " + elem.toString() + " at " + elem.getTextOffset());
            }
            assertEquals(operators.size(), 2);
        }
    }


    @Test
    public void testMIMany() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");
        for (PsiClass psiClass : psiClasses) {
            Set<PsiElement> operands = ParseAdapter.getOperands(psiClass);
            System.out.println("test many operand size = " + operands.size());
            int j = 0;
            for(PsiElement elem: operands) {
                System.out.println("operand " + (++j) + " : " + elem.toString());
            }
            assertEquals(operands.size(), 31);

            Set<PsiElement> operators = ParseAdapter.getOperators(psiClass);
            System.out.println("test many operator size = " + operators.size());
            j = 0;
            for(PsiElement elem: operators) {
                System.out.println("operators " + (++j) + " : " + elem.toString() + " at " + elem.getTextOffset());
            }
            assertEquals(operators.size(), 22);
        }
    }

    @Test
    public void testGetOperatorAll() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase3.txt");
        for (PsiClass psiClass : psiClasses) {

            int j = 0;

            Set<PsiElement> operators = ParseAdapter.getOperators(psiClass);
            System.out.println("test operator all operator size = " + operators.size());
            j = 0;
            for(PsiElement elem: operators) {
                System.out.println("operators " + (++j) + " : " + elem.toString() + " at " + elem.getTextOffset());
            }
            assertEquals(operators.size(), 75);
        }
    }
}