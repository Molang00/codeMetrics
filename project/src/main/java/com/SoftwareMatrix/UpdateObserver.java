package com.SoftwareMatrix;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

/*
    Implement this interface if you want your class
    to update with every change event
 */
public interface UpdateObserver {
    void update(Project project, PsiElement elem); // note that elem may be null
}
