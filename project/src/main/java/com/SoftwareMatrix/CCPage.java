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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

public class CCPage {
  /* Declare private fields here */
  MetricsResultWindow window;
  JPanel page;
  JTable table;
  JScrollPane tableContent;
  JButton button;
  JLabel mainLabel;
  JPanel tablePanel, bottomPanel, topPanel;
  Integer edge = 5, node = 4, CCValue = 11;

  public CCPage(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.page = mainPanel;
  }

  /**
   * Constructor of tool window
   */
  public JPanel getPage() {
    page.removeAll();
    page.setLayout(new BorderLayout());

    topPanel = new JPanel();
    mainLabel = new JLabel("CC Details");
    topPanel.add(mainLabel);
    page.add(topPanel, "North");

    tablePanel = new JPanel();
    table = new JBTable();
    tableContent = new JBScrollPane(table);
    generateTable();
    tablePanel.add(tableContent);
    page.add(tablePanel, "Center");

    bottomPanel = new JPanel();
    generateButtons();
    bottomPanel.add(button);
    page.add(bottomPanel, "South");

    return page;
  }

  private void generateButtons() {
    button = new JButton("Default page");
    button.addActionListener(e -> {
      window.changeView("Default");
    });
  }

  private void generateTable() {
    String header[] = {"CC Features", "Score", "Graph"};
    String body[][] = {{"Edge", "", ""}, {"Node", "", ""}, {"CC Value", "", ""}};
    DefaultTableModel model = new DefaultTableModel(body, header) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table.setModel(model);

    table.setValueAt(edge, 0, 1); // Set edge value
    table.setValueAt(node, 1, 1); // Set node value
    table.setValueAt(CCValue, 2, 1); // Set CC value

    table.getColumn("Graph").setCellRenderer(new ProgressRenderer(0, 20));
    table.getColumnModel().getColumn(0).setPreferredWidth(200);
  }

  class ProgressRenderer extends JProgressBar implements TableCellRenderer {
    public ProgressRenderer(int min, int max) {
      super(min, max);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
      if (row == 0) {
        this.setValue(20 - edge);
      } else if (row == 1){
        this.setValue(20 - node);
      } else {
        this.setValue(20 - CCValue);
      }
      return this;
    }
  }
}
