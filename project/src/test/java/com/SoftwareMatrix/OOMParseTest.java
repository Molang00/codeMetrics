package com.SoftwareMatrix;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateBuilder;
import com.intellij.codeInsight.template.TemplateBuilderImpl;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.structuralsearch.StructuralSearchTemplateBuilder;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OOMParseTest extends LightJavaCodeInsightFixtureTestCase {

    public void testEmpty() {
        PsiFile psiFile = myFixture.configureByText(JavaFileType.INSTANCE, "class A {}");

        // test code

        System.out.println(PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class));
        assertNotNull(PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class));


    }

    public void testGetContainingMethod() {
        byte[] code = null;
        System.out.println(System.getProperty("user.dir"));
        try {
            File file = new File("./src/test/resources/testcases/code1.java");
            FileInputStream file_in = new FileInputStream(file);
            code = new byte[(int) file.length()];
            file_in.read(code);
            file_in.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        PsiFile psiFile = myFixture.configureByText(JavaFileType.INSTANCE, new String(code));

        System.out.println(PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class));
        assertNotNull(PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class));
    }
}
