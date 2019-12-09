package com.SoftwareMatrix;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.project.*;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import static org.junit.Assert.*;

public class ParseTest {
    @Test
    public void tmp_test() {
//        ParseAdapter pa = new ParseAdapter();
//
//        Path currentRelativePath = Paths.get("");
//        String ProjectPath = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current Absolute path is: " + ProjectPath);
//
//        String filepath = ProjectPath + "/src/test/java/com/SoftwareMatrix/ParserTest_tmp";
//        System.out.println(filepath);
//        File file = new File(filepath);
//
//        Project[] projects = ProjectManager.getInstance().getOpenProjects(); // getInstance Failed
//        Project activeProject = null;
//        for (Project project : projects) {
//            Window window = WindowManager.getInstance().suggestParentWindow(project);
//            if (window != null && window.isActive()) {
//                activeProject = project;
//            }
//        }
//
//        if (activeProject == null){
//            System.out.println("NULL");
//        }
//
//        System.out.println(activeProject);
//
//        VirtualFile vf = LocalFileSystem.getInstance().findFileByIoFile(file); // getInstance Failed
//        System.out.println(vf);


        // If we can obtain activeProject or VirtualFile, we can make test code

//        Collection<VirtualFile> virtualFiles =
//                FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE,
//                        GlobalSearchScope.projectScope(activeProject));



//         1. Obtaining Project Variable from this editor, or view
//         2. Open VirtualFile from path or File
//         3. Get PsiFile or PsiElement or PsiClass from File variable
//         4. Making Independent PsiElement or PsiClass or somethings like that.
        // If we succeed any one among those 4 things, we can make test code easily


    }
}
