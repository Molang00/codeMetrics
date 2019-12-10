package com.SoftwareMatrix;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiElement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.util.Random;

import com.SoftwareMatrix.PageFactory.DefaultPageFactory;
import com.SoftwareMatrix.PageFactory.HalsteadVolumePageFactory;
import com.SoftwareMatrix.PageFactory.SLOCPageFactory;
import com.SoftwareMatrix.PageFactory.MIPageFactory;
import com.SoftwareMatrix.PageFactory.OOPageFactory;
import com.SoftwareMatrix.PageFactory.CCPageFactory;
import org.jetbrains.annotations.NotNull;


public class MetricsResultWindow implements UpdateObserver {
    /* Declare private fields here */
    private JPanel myToolWindowContent;
    private DefaultPageFactory defaultpageFactory;
    private MIPageFactory mipageFactory;
    private OOPageFactory oopageFactory;
    private CCPageFactory ccpageFactory;
    private HalsteadVolumePageFactory halsteadVolumePageFactory;
    private SLOCPageFactory SLOCpageFactory;
    private String label;
    private UpdateManager uManager;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow, @NotNull Project project) {
        uManager = UpdateManager.getInstance(project); // init update manager
        settingAllStatus();
    }

    public void settingAllStatus() {

        myToolWindowContent = new JPanel();
        defaultpageFactory = new DefaultPageFactory(this, myToolWindowContent);
        mipageFactory = new MIPageFactory(this, myToolWindowContent);
        oopageFactory = new OOPageFactory(this, myToolWindowContent);
        ccpageFactory = new CCPageFactory(this, myToolWindowContent);
        halsteadVolumePageFactory = new HalsteadVolumePageFactory(this, myToolWindowContent);
        SLOCpageFactory = new SLOCPageFactory(this, myToolWindowContent);

        uManager.addObserver(ccpageFactory);
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
        settingAllStatus();

        // color refresh has 5~10 seconds of delay

        System.out.println(elem);
        System.out.println(ParseAdapter.getContainingMethod(elem));
        System.out.println(ParseAdapter.getContainingClass(elem));
        System.out.println(ParseAdapter.getContainingPackage(elem));
    }

    public void changeView(String label) {
        this.label=label;
        switch (label) {
        case "Default":
            defaultpageFactory.createPage();
            break;
        case "MI":
            mipageFactory.createPage();
            break;
        case "OO":
            oopageFactory.createPage();
            break;
        case "V":
            halsteadVolumePageFactory.createPage();
            break;
        case "G":
            ccpageFactory.createPage();
            break;
        case "SLOC":
            SLOCpageFactory.createPage();
            break;
        }
        myToolWindowContent.revalidate();
    }
}