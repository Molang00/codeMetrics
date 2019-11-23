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

public class CCPageFactory {
  /* Declare private fields here */
  MetricsResultWindow window;
  JPanel CCPage;
  JTable table;
  JScrollPane tableContent;
  JButton defaultButton;
  JLabel mainLabel;
  JPanel tablePanel, bottomPanel, topPanel;
  int edge = 5, node = 4, CCValue = 11;

  public CCPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.CCPage = mainPanel;
  }

  /**
   * Constructor of tool window
   */
  public JPanel createCCPage() {
    CCPage.removeAll();
    CCPage.setLayout(new BorderLayout());

    topPanel = new JPanel();
    mainLabel = new JLabel("CC Details");
    topPanel.add(mainLabel);
    CCPage.add(topPanel, "North");

    tablePanel = new JPanel();
    table = new JBTable();
    tableContent = new JBScrollPane(table);
    generateCCTable();
    tablePanel.add(tableContent);
    CCPage.add(tablePanel, "Center");

    bottomPanel = new JPanel();
    generateDefaultButtons();
    bottomPanel.add(defaultButton);
    CCPage.add(bottomPanel, "South");

    return CCPage;
  }

  void generateDefaultButtons() {
    defaultButton = new JButton("Default page");
    defaultButton.addActionListener(e -> {
      window.changeView("Default");
    });
  }

  public void generateCCTable() {
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
        this.setValue(20 - (Integer) edge);
      } else if (row == 1){
        this.setValue(20 - (Integer) node);
      } else {
        this.setValue(20 - (Integer) CCValue);
      }
      return this;
    }
  }
}
