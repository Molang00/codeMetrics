package com.SoftwareMatrix;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Test;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class MIParseTest extends LightJavaCodeInsightFixtureTestCase {

    ParseAdapter pa = new ParseAdapter();

    private PsiClass[] getPsiClasses(String path) {
        byte[] data = null;

        try {
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            data = new byte[(int) f.length()];
            fis.read(data);
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(null, "fail during file read");
        }

        String str = null;
        try {
            str = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        PsiFile psiFile = myFixture.configureByText(JavaFileType.INSTANCE, str);
        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
        return psiJavaFile.getClasses();
    }

    @Test
    public void testMISimple() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase1.txt");

        for(int i = 0; i < psiClasses.length; i++) {
            List<PsiElement> operands = pa.getOperands(psiClasses[i]);
            System.out.println("test simple operand size = " + operands.size());
            for(int j = 0; j < operands.size(); j++) {
                System.out.println("operand " + j + " : " + operands.get(j).toString());
            }
            assertEquals(operands.size(), 4);

            List<PsiElement> operators = pa.getOperators(psiClasses[i]);
            System.out.println("test simple operator size = " + operators.size());
            for(int j = 0; j < operators.size(); j++) {
                System.out.println("operator " + j + " : " + operators.get(j).toString());
            }
            assertEquals(operators.size(), 2);
        }
    }


    @Test
    public void testMIMany() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");
        for(int i = 0; i < psiClasses.length; i++) {
            List<PsiElement> operands = pa.getOperands(psiClasses[i]);
            System.out.println("test many operand size = " + operands.size());
            for(int j = 0; j < operands.size(); j++) {
                Object obj = operands.get(j);
                System.out.println("operand " + j + " : " + obj.toString());
            }
            assertEquals(operands.size(), 41);

            List<PsiElement> operators = pa.getOperators(psiClasses[i]);
            System.out.println("test many operator size = " + operators.size());
            for(int j = 0; j < operators.size(); j++) {
                System.out.println("operator " + j + " : " + operators.get(j).toString());
            }
            assertEquals(operators.size(), 21);
        }
    }

}