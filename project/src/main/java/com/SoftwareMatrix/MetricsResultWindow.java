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
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.awt.image.BufferedImage;

public class MetricsResultWindow {
    /* Declare private fields here */
    JPanel myToolWindowContent;
    JTable table, MITable, OOTable;
    JScrollPane tableContent;
    Integer MIscore, OOscore;
    JButton MIButton, OOButton, defaultButton;
    JLabel mainLabel;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        table = new JTable();
        mainLabel = new JLabel("Software Metrics");
        tableContent = new JScrollPane(table);
        generateDefaultTable();
        generateDefaultButtons();
        myToolWindowContent = new JPanel();
        myToolWindowContent.add(mainLabel);
        myToolWindowContent.add(defaultButton);
        myToolWindowContent.add(MIButton);
        myToolWindowContent.add(tableContent);
    }
    
    void generateDefaultButtons(){
        MIButton = new JButton("MI detail");
        MIButton.addActionListener(e -> {
            System.out.println("Listen button clicked action at MI");
            this.changeView("MI");
        });
        defaultButton = new JButton("Default page");
        defaultButton.addActionListener(e -> {
            System.out.println("Listen button clicked action at Default");
            this.changeView("Default");
        });
    }

    void generateDefaultTable(){
        String header[] = { "Type", "Score", "Graph" };
        String body[][] = { { "MI", "", "" }, { "OO", "", "" } };
        DefaultTableModel model = new DefaultTableModel(body, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);

        settingAllStatus();
        table.setValueAt(MIscore, 0, 1);
        table.setValueAt(OOscore, 1, 1);
        table.getColumn("Type").setCellRenderer(new TableCell(this));
        table.getColumn("Type").setCellEditor(new TableCell(this));
        table.getColumn("Graph").setCellRenderer(new ColoredTableCellRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
    }

    /**
     * Returns content of this tool window
     * 
     * @return whole content of tool window
     */
    public JPanel getContent() {        
        return myToolWindowContent;
    }

    public void changeView(String label){
        if(label == "MI"){
            generateMITable();
        }
        if(label == "Default"){
            generateDefaultTable();
        }
    }

    public void generateMITable(){
        String header[] = { "MI Features", "Score", "Graph" };
        String body[][] = { { "V (Haslstead's volume)", "", "" }, { "G Cyclomatic complexity)", "", "" }, { "LOC(Source lines of code", "", "" } };
        DefaultTableModel model = new DefaultTableModel(body, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);

        table.setValueAt(150, 0, 1); // Set V value
        table.setValueAt(20, 1, 1); // Set G value
        table.setValueAt(300, 2, 1); // Set LOC value
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
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
}
