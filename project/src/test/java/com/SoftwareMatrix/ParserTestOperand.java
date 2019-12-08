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

public class ParserTestOperand extends LightJavaCodeInsightFixtureTestCase {

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
    public void testGetOperandsSimple() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase1.txt");
        System.out.println("test 1 : " + psiClasses.length);
        for(int i = 0; i < psiClasses.length; i++) {
            List<PsiElement> operands = pa.getOperands(psiClasses[i]);
            System.out.println(operands.size());
            assertFalse(operands.isEmpty());
        }
    }


    @Test
    public void testGetOperandsMany() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");
        System.out.println("test 2 : " + psiClasses.length);
        for(int i = 0; i < psiClasses.length; i++) {
            List<PsiElement> operands = pa.getOperands(psiClasses[i]);
            System.out.println(operands.toString());
            assertFalse(operands.isEmpty());
        }
    }

}