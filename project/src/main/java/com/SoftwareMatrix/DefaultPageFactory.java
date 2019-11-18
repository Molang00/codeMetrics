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

public class DefaultPageFactory {
  /* Declare private fields here */
  MetricsResultWindow window;
  JPanel DefaultPage;
  Integer MIscore, OOscore;
  JTable table;
  JScrollPane tableContent;
  JButton MIButton, OOButton;
  JLabel mainLabel;
  JPanel tablePanel, bottomPanel, topPanel;

  public DefaultPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.DefaultPage = mainPanel;
  }

  /**
   * Constructor of tool window
   */
  public JPanel createDefaultPage() {
    DefaultPage.removeAll();
    DefaultPage.setLayout(new BorderLayout());

    topPanel = new JPanel();
    mainLabel = new JLabel("Software Metrics");
    topPanel.add(mainLabel);
    DefaultPage.add(topPanel, "North");

    tablePanel = new JPanel();
    table = new JTable();
    tableContent = new JScrollPane(table);
    generateDefaultTable();
    tablePanel.add(tableContent);
    DefaultPage.add(tablePanel, "Center");

    bottomPanel = new JPanel();
    generateDefaultButtons();
    bottomPanel.add(MIButton);
    DefaultPage.add(bottomPanel, "South");

    return DefaultPage;
  }

  void generateDefaultButtons() {
    MIButton = new JButton("MI detail");
    MIButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at MI");
      window.changeView("MI");
    });
  }

  void generateDefaultTable() {
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
    table.getColumn("Graph").setCellRenderer(new ColoredTableCellRenderer());
    table.getColumnModel().getColumn(0).setPreferredWidth(100);
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