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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;

public class MetricsResultWindow {
    /* Declare private fields here */
    JPanel myToolWindowContent;
    JTable tableStructure;
    JScrollPane tableContent;
    Integer OOscore, MIscore;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        String header[] = { "\\", "score" };
        String body[][] = { { "MI", "" }, { "OO", "" } };
        DefaultTableModel model = new DefaultTableModel(body, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableStructure = new JTable();
        tableStructure.setModel(model);
        tableContent = new JScrollPane(tableStructure);

        settingAllStatus();
        tableStructure.setValueAt(MIscore, 0, 1);
        tableStructure.setValueAt(OOscore, 1, 1);
    }

    public Integer getOOsocre() {
        return OOscore;
    }

    public Integer getMIscore() {
        return MIscore;
    }

    public void setOOscore(Integer s) {
        OOscore = s;
    }

    public void setMIscore(Integer s) {
        MIscore = s;
    }

    public void settingAllStatus() {
        setMIscore(86);
        setOOscore(70);
    }

    /**
     * Returns content of this tool window
     * 
     * @return whole content of tool window
     */
    public JScrollPane getContent() {
        return tableContent;
    }
}
