package com.SoftwareMatrix;

import com.SoftwareMatrix.PageFactory.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiElement;
import com.SoftwareMatrix.metrics.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.jetbrains.annotations.NotNull;


public class MetricsResultWindow implements UpdateObserver {
    /* Declare private fields here */
    private JPanel myToolWindowContent;
    private RefactorPageFactory PageFactory;
    private DefaultPageFactory defaultpageFactory;
//    private MIPageFactory mipageFactory;
//    private OOPageFactory oopageFactory;
//    private CCPageFactory ccpageFactory;
//    private HalsteadVolumePageFactory halsteadVolumePageFactory;
//    private SLOCPageFactory SLOCpageFactory;
    private String label;
    private UpdateManager uManager;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow, @NotNull Project project) {
        uManager = UpdateManager.getInstance(project); // init update manager
        myToolWindowContent = new JPanel();

        PageFactory = new RefactorPageFactory(this, myToolWindowContent);

//        settingAllStatus();
    }

    private void settingAllStatus() {

//        defaultpageFactory = new DefaultPageFactory(this, myToolWindowContent);
//        mipageFactory = new MIPageFactory(this, myToolWindowContent);
//        oopageFactory = new OOPageFactory(this, myToolWindowContent);
//        ccpageFactory = new CCPageFactory(this, myToolWindowContent);
//        halsteadVolumePageFactory = new HalsteadVolumePageFactory(this, myToolWindowContent);
//        SLOCpageFactory = new SLOCPageFactory(this, myToolWindowContent);

        uManager.addObserver(defaultpageFactory);
        uManager.addObserver(mipageFactory);
        uManager.addObserver(oopageFactory);
        uManager.addObserver(ccpageFactory);
        uManager.addObserver(halsteadVolumePageFactory);
        uManager.addObserver(SLOCpageFactory);
        label = "Default";
        defaultpageFactory.createPage();
    }

    /**
     * Returns content of this tool window
     * 
     * @return whole content of tool window
     */
    public JPanel getContent() {
        return myToolWindowContent;
    }

    @Override
    public void update(Project project, PsiElement elem) {
//        settingAllStatus();
        myToolWindowContent.revalidate();
    }

    public void changeView(String label) {
        this.label=label;
        PageFactory.createPage(label);
        myToolWindowContent.revalidate();
    }
}