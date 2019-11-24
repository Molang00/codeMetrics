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

public class OOPageFactory {
  /* Declare private fields here */
  MetricsResultWindow window;
  JPanel OOPage;
  JTable table;
  JScrollPane tableContent;
  JButton defaultButton;
  JLabel mainLabel;
  JPanel tablePanel, bottomPanel, topPanel;

  public OOPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.OOPage = mainPanel;
  }

  /**
   * Constructor of tool window
   */
  public JPanel createOOPage() {
    OOPage.removeAll();
    OOPage.setLayout(new BorderLayout());

    topPanel = new JPanel();
    mainLabel = new JLabel("OO Details");
    topPanel.add(mainLabel);
    OOPage.add(topPanel, "North");

    tablePanel = new JPanel();
    table = new JTable();
    tableContent = new JScrollPane(table);
    generateOOTable();
    tablePanel.add(tableContent);
    OOPage.add(tablePanel, "Center");

    bottomPanel = new JPanel();
    generateDefaultButtons();
    bottomPanel.add(defaultButton);
    OOPage.add(bottomPanel, "South");

    return OOPage;
  }

  void generateDefaultButtons() {
    defaultButton = new JButton("Default page");
    defaultButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at Default");
      window.changeView("Default");
    });
  }

  public void generateOOTable() {
    String header[] = { "OO Features", "Score" };
    String body[][] = { { "WMC (Weighted method per class", "" }, { "DIT (Depth of Inheritance Tree", "" },
        { "NOC (Number Of Children)", "" }, { "CBO (Coupling Between Object classes)", "" },
        { "RFC (Response For Class)", "" }, { "LOCM (Lack Of Cohesion Metrics)", "" } };
    DefaultTableModel model = new DefaultTableModel(body, header) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table.setModel(model);

    table.setValueAt(15, 0, 1); // Set WMC value
    table.setValueAt(10, 1, 1); // Set DIT value
    table.setValueAt(5, 2, 1); // Set NOC value
    table.setValueAt(2, 3, 1); // Set CBO value
    table.setValueAt(7, 4, 1); // Set RFC value
    table.setValueAt(3, 5, 1); // Set LOCM value
    table.getColumnModel().getColumn(0).setPreferredWidth(200);
  }
}