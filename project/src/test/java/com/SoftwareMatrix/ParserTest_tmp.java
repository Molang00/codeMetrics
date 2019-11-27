package com.SoftwareMatrix;

import org.junit.Test;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import static org.junit.Assert.*;

public class ParserTest_tmp {
    @Test
    public void calc_test() throws FileNotFoundException {
        javaparsing jp = new javaparsing("example2.txt", "MyRenamedClass");
        System.out.println(jp.get_branch_num_method(jp.get_list_method().get(0)));
        System.out.println(jp.get_branch_num());
        System.out.println(jp.get_branch_pos());
    }
}
