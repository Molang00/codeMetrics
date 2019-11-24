package com.SoftwareMatrix;

import com.google.common.collect.Tables;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.awt.image.BufferedImage;

public class MetricsResultWindow {
    /* Declare private fields here */
    JPanel myToolWindowContent;
    MIPageFactory mipageFactory;
    OOPageFactory oopageFactory;
    DefaultPageFactory defaultpageFactory;
    String label;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        myToolWindowContent = new JPanel();
        defaultpageFactory = new DefaultPageFactory(this, myToolWindowContent);
        mipageFactory = new MIPageFactory(this, myToolWindowContent);
        oopageFactory = new OOPageFactory(this, myToolWindowContent);
        label = "Default";
        defaultpageFactory.createDefaultPage();
    }

    /**
     * Returns content of this tool window
     * 
     * @return whole content of tool window
     */
    public JPanel getContent() {
        return myToolWindowContent;
    }

    public void changeView(String label) {
        this.label = label;
        if (label == "MI") {
            mipageFactory.createMIPage();
        }
        if (label == "OO") {
            oopageFactory.createOOPage();
        }
        if (label == "Default") {
            defaultpageFactory.createDefaultPage();
        }
        myToolWindowContent.revalidate();
    }
}