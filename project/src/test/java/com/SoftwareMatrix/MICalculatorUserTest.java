package com.SoftwareMatrix;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class MICalculatorUserTest extends LightJavaCodeInsightFixtureTestCase {

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
    public void testCalculateEta() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");

        for (PsiClass psiClass : psiClasses) {
            Set<PsiElement> operands = ParseAdapter.getOperands(psiClass);
            Set<PsiElement> operators = ParseAdapter.getOperators(psiClass);

            assertEquals(MICalculator.calculateEta(operators, operands), 40);
        }
    }

    @Test
    public void testCalculateN() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");

        for (PsiClass psiClass : psiClasses) {
            Set<PsiElement> operands = ParseAdapter.getOperands(psiClass);
            Set<PsiElement> operators = ParseAdapter.getOperators(psiClass);

            assertEquals(MICalculator.calculateN(operators, operands), 56);
        }
    }

    @Test
    public void testCalculateHalstead() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");

        for (PsiClass psiClass : psiClasses) {
            Set<PsiElement> operands = ParseAdapter.getOperands(psiClass);
            Set<PsiElement> operators = ParseAdapter.getOperators(psiClass);

            assertEquals(MICalculator.calculateHalstead(operators, operands), 298);
        }
    }


    @Test
    public void testCalculateCC() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");

        for (PsiClass psiClass : psiClasses) {
            Set<PsiElement> branches = ParseAdapter.getBranch(psiClass);

            assertEquals(MICalculator.calculateCC(branches), 7);
        }
    }

    @Test
    public void testCalculateMI() {
        PsiClass[] psiClasses = this.getPsiClasses("./src/test/resources/testcases/TestCase2.txt");

        for (PsiClass psiClass : psiClasses) {
            Set<PsiElement> operands = ParseAdapter.getOperands(psiClass);
            Set<PsiElement> operators = ParseAdapter.getOperators(psiClass);
            Set<PsiElement> branches = ParseAdapter.getBranch(psiClass);
            int lloc = ParseAdapter.getLLoc(psiClass);
            int loc = ParseAdapter.getLoc(psiClass);
            int cloc = ParseAdapter.getCLoc(psiClass);

            assertEquals(MICalculator.calculateMI(operators, operands, branches, lloc, loc, cloc), 53);
        }
    }
}