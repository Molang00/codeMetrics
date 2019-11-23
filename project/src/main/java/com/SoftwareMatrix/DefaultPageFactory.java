package com.SoftwareMatrix;

import com.google.common.collect.Tables;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
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
  Integer CCscore, OOscore;
  JTable table;
  JScrollPane tableContent;
  JButton CCButton, OOButton;
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
    table = new JBTable();
    tableContent = new JBScrollPane(table);
    generateDefaultTable();
    tablePanel.add(tableContent);
    DefaultPage.add(tablePanel, "Center");

    bottomPanel = new JPanel();
    generateDefaultButtons();
    bottomPanel.add(CCButton);
    DefaultPage.add(bottomPanel, "South");

    return DefaultPage;
  }

  void generateDefaultButtons() {
    CCButton = new JButton("CC detail");
    CCButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at CC");
      window.changeView("CC");
    });
  }

  void generateDefaultTable() {
    String header[] = { "Type", "Score", "Graph" };
    String body[][] = { { "CC", "", "" }, { "OO", "", "" } };
    DefaultTableModel model = new DefaultTableModel(body, header) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table.setModel(model);

    settingAllStatus();
    table.setValueAt(CCscore, 0, 1);
    table.setValueAt(OOscore, 1, 1);
    table.getColumn("Graph").setCellRenderer(new ColoredTableCellRenderer());
    table.getColumnModel().getColumn(0).setPreferredWidth(100);
  }

  class ColoredTableCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
        int row, int column) {
      setEnabled(table == null || table.isEnabled());
      int r, g, b = 0;

      if (row == 0) {
        r = (int) ((100 - CCscore) * 2.55);
        g = (int) ((CCscore) * 2.55);
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

  public Integer getCCscore() {
    return CCscore;
  }

  public void setOOscore(Integer s) {
    OOscore = s;
  }

  public void setCCscore(Integer s) {
    CCscore = s;
  }

  public void settingAllStatus() {
    setCCscore(90);
    setOOscore(10);
  }
}