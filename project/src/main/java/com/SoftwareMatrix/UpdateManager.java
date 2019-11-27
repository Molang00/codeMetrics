package com.SoftwareMatrix;

import com.intellij.codeInsight.template.postfix.templates.SoutPostfixTemplate;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Singleton class that manages update events
 */
public class UpdateManager {
    private static UpdateManager instance = null;

    private static Thread thread = null; // to track update time

    private Project project;
    private List<UpdateObserver> observers; // observer list

    private void notifyObserversWrapper(PsiTreeChangeEvent event) {
        System.out.println("CHANGED");
        if(thread != null && thread.isAlive()) {
            ; // do nothing
        }
        else {
            thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        ApplicationManager.getApplication().runReadAction(new Runnable() {
                            @Override
                            public void run() {
                                notifyObservers(event);
                            }
                        });
                    } catch (InterruptedException ex) {
                        ; // do nothing
                    }
                }
            };
            thread.start();
//            System.out.println(Arrays.toString(JavaPsiFacade.getInstance(project)
//                    .findClasses("", GlobalSearchScope.allScope(project))));
        }
    }

    private UpdateManager(@NotNull Project project) {
        observers = new ArrayList<>();

        this.project = project;
        PsiManager.getInstance(project).addPsiTreeChangeListener(new PsiTreeChangeAdapter() {
            @Override
            public void childAdded(@NotNull PsiTreeChangeEvent event) {
                System.out.println(event.getParent());
                System.out.println(event.getChild());
                System.out.println(PsiTreeUtil.findChildrenOfType(event.getParent(), PsiIfStatement.class));
                System.out.println(PsiTreeUtil.findChildrenOfType(event.getChild(), PsiIfStatement.class));
                System.out.println(PsiTreeUtil.getContextOfType(event.getParent(), PsiIfStatement.class, false));
                notifyObserversWrapper(event);
            }

            @Override
            public void childRemoved(@NotNull PsiTreeChangeEvent event) {
                System.out.println(event.getParent());
                System.out.println(event.getChild());
                System.out.println(PsiTreeUtil.findChildrenOfType(event.getParent(), PsiIfStatement.class));
                System.out.println(PsiTreeUtil.findChildrenOfType(event.getChild(), PsiIfStatement.class));
                System.out.println(PsiTreeUtil.getContextOfType(event.getParent(), PsiIfStatement.class, false));
                notifyObserversWrapper(event);
            }

            @Override
            public void childReplaced(@NotNull PsiTreeChangeEvent event) {
                System.out.println(event.getParent());
                System.out.println(event.getChild());
                System.out.println(PsiTreeUtil.findChildrenOfType(event.getParent(), PsiIfStatement.class));
                System.out.println(PsiTreeUtil.findChildrenOfType(event.getChild(), PsiIfStatement.class));
                System.out.println(PsiTreeUtil.getContextOfType(event.getParent(), PsiIfStatement.class, false));
                notifyObserversWrapper(event);
            }
        });
    }

    public static UpdateManager getInstance(@NotNull Project project) {
        if(instance == null) {
            instance = new UpdateManager(project);
        }
        return instance;
    }

    public void notifyObservers(PsiTreeChangeEvent event) {
        System.out.println("NOTIFY COMPLETE");
        for(UpdateObserver observer: observers) {
            observer.update(project);
        }
    }

    public boolean addObserver(UpdateObserver observer) {
        return observers.add(observer);
    }

    public boolean removeObserver(UpdateObserver observer) {
        return observers.remove(observer);
    }
}
