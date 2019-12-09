package com.SoftwareMatrix;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/*
    Singleton class that manages update events
 */
public class UpdateManager {
    private static UpdateManager instance = null;

    private static Thread thread = null; // to track update time

    private Project project;
    private List<UpdateObserver> observers; // observer list

    private PsiElement lastTrackedElement; // last caret position

    private void notifyObserversWrapper() {
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
                                notifyObservers();
                            }
                        });
                    } catch (InterruptedException ex) {
                        ; // do nothing
                    }
                }
            };
            thread.start();
        }
    }

    private UpdateManager(@NotNull Project project) {
        observers = new ArrayList<>();
        lastTrackedElement = null;

        this.project = project;
        PsiManager.getInstance(project).addPsiTreeChangeListener(new PsiTreeChangeAdapter() {
            @Override
            public void childAdded(@NotNull PsiTreeChangeEvent event) {
                notifyObserversWrapper();
            }

            @Override
            public void childRemoved(@NotNull PsiTreeChangeEvent event) {
                notifyObserversWrapper();
            }

            @Override
            public void childReplaced(@NotNull PsiTreeChangeEvent event) {
                notifyObserversWrapper();
            }
        });

        CaretListener caretListener = new CaretListener() {
            @Override
            public void caretPositionChanged(@NotNull CaretEvent event) {
                PsiFile pFile = PsiDocumentManager.getInstance(project).getPsiFile(event.getEditor().getDocument());
                Caret caret = event.getCaret();
                if(caret != null && pFile != null) {
                    lastTrackedElement = pFile.findElementAt(caret.getOffset());
                }
                notifyObserversWrapper();
            }

            @Override
            public void caretAdded(@NotNull CaretEvent event) {
                PsiFile pFile = PsiDocumentManager.getInstance(project).getPsiFile(event.getEditor().getDocument());
                Caret caret = event.getCaret();
                if(caret != null && pFile != null) {
                    lastTrackedElement = pFile.findElementAt(caret.getOffset());
                }
                notifyObserversWrapper();
            }
        };

        MessageBus messageBus = project.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileOpened(source, file);
                Editor editor = source.getSelectedTextEditor();
                if(editor != null) {
                    editor.getCaretModel().removeCaretListener(caretListener);
                    editor.getCaretModel().addCaretListener(caretListener);

                    PsiFile pFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
                    Caret caret = editor.getCaretModel().getCurrentCaret();
                    if(pFile != null) {
                        lastTrackedElement = pFile.findElementAt(caret.getOffset());
                    }
                    notifyObserversWrapper();
                }
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileClosed(source, file);
                notifyObserversWrapper();
            }

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                super.selectionChanged(event);
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                if(editor != null) {
                    editor.getCaretModel().removeCaretListener(caretListener);
                    editor.getCaretModel().addCaretListener(caretListener);

                    PsiFile pFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
                    Caret caret = editor.getCaretModel().getCurrentCaret();
                    if(pFile != null) {
                        lastTrackedElement = pFile.findElementAt(caret.getOffset());
                    }
                    notifyObserversWrapper();
                }
            }
        });

        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if(editor != null) {
            editor.getCaretModel().removeCaretListener(caretListener);
            editor.getCaretModel().addCaretListener(caretListener);
            notifyObserversWrapper();
        }
    }

    public static UpdateManager getInstance(@NotNull Project project) {
        if(instance == null) {
            instance = new UpdateManager(project);
        }
        return instance;
    }

    public void notifyObservers() {
        for(UpdateObserver observer: observers) {
            System.out.println(lastTrackedElement.toString());
            observer.update(project, lastTrackedElement); // note that lastTrackedElement may be null

        }
    }

    public boolean addObserver(UpdateObserver observer) {
        return observers.add(observer);
    }

    public boolean removeObserver(UpdateObserver observer) {
        return observers.remove(observer);
    }
}
