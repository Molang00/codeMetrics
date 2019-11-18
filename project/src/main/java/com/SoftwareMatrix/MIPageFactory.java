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

public class MIPageFactory {
  /* Declare private fields here */
  MetricsResultWindow window;
  JPanel MIPage;
  JTable table, MITable, OOTable;
  JScrollPane tableContent;
  JButton defaultButton;
  JLabel mainLabel;
  JPanel tablePanel, bottomPanel, topPanel;

  public MIPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.MIPage = mainPanel;
  }

  /**
   * Constructor of tool window
   */
  public JPanel createMIPage() {
    MIPage.removeAll();
    MIPage.setLayout(new BorderLayout());

    topPanel = new JPanel();
    mainLabel = new JLabel("MI Details");
    topPanel.add(mainLabel);
    MIPage.add(topPanel, "North");

    tablePanel = new JPanel();
    table = new JTable();
    tableContent = new JScrollPane(table);
    generateMITable();
    tablePanel.add(tableContent);
    MIPage.add(tablePanel, "Center");

    bottomPanel = new JPanel();
    generateDefaultButtons();
    bottomPanel.add(defaultButton);
    MIPage.add(bottomPanel, "South");

    return MIPage;
  }

  void generateDefaultButtons() {
    defaultButton = new JButton("Default page");
    defaultButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at Default");
      window.changeView("Default");
    });
  }

  public void generateMITable() {
    String header[] = { "MI Features", "Score", "Graph" };
    String body[][] = { { "V (Haslstead's volume)", "", "" }, { "G Cyclomatic complexity)", "", "" },
        { "LOC(Source lines of code", "", "" } };
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
}