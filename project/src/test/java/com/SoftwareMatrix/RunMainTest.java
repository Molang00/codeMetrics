package com.SoftwareMatrix;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RunMainTest extends BasePlatformTestCase {
    public void testRunProperly() {
        ;
    }

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
    public void testClassLength() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");
        System.out.println("ClassLength Test");

        for (PsiClass psiClass : psiClasses) {
            OOMClass oomClass = new OOMClass(psiClass);
            System.out.println(oomClass.getClassLength());
            assertEquals(oomClass.getClassLength(), (Integer) 52);
        }
    }
}
