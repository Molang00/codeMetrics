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

import java.awt.*;
import java.util.Calendar;
import java.awt.image.BufferedImage;

public class MetricsResultWindow {
    /* Declare private fields here */
    JPanel myToolWindowContent;
    JTable tableStructure;
    JScrollPane tableContent;
    Integer MIscore, OOscore;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        String header[] = { "Type", "Score", "Graph" };
        String body[][] = { { "MI", "", "" }, { "OO", "", "" } };
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
        tableStructure.getColumn("Type").setCellRenderer(new ButtonRenderer());
        tableStructure.getColumn("Type").setCellEditor(new ButtonEditor(new JCheckBox()));
        TableColumnModel tcm = tableStructure.getColumnModel();
        TableColumn tm = tcm.getColumn(2);
        tm.setCellRenderer(new ColoredTableCellRenderer());
        tableStructure.getColumnModel().getColumn(0).setPreferredWidth(100);

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
        setMIscore(90);
        setOOscore(10);
    }

    /**
     * Returns content of this tool window
     * 
     * @return whole content of tool window
     */
    public JScrollPane getContent() {
        return tableContent;
    }

    class ColoredTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
                int row, int column) {
            setEnabled(table == null || table.isEnabled()); // see question above
            int r, g, b = 0;

            if (row == 0) {
                r = (int) ((100 - MIscore) * 2.55);
                g = (int) ((MIscore) * 2.55);
                Color c = new Color(r, g, b);
                setBackground(c);
            } else {
                r = (int) ((100 - OOscore) * 2.55);
                g = (int) ((OOscore) * 2.55);
                Color c = new Color(r, g, b);
                setBackground(c);
            }

            super.getTableCellRendererComponent(table, value, selected, focused, row, column);

            return this;
        }
    }

}
