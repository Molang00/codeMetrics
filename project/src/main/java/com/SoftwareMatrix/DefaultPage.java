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

public class DefaultPage {
  /* Declare private fields here */
  MetricsResultWindow window;
  JPanel page;
  JTable table;
  JScrollPane tableContent;
  JButton button;
  JLabel mainLabel;
  JPanel centerPanel, bottomPanel, topPanel;
  Integer CCscore, OOscore;

  public DefaultPage(MetricsResultWindow window, JPanel mainPanel) {
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
    mainLabel = new JLabel("Software Metrics");
    topPanel.add(mainLabel);
    page.add(topPanel, "North");

    centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    table = new JBTable();
    //table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tableContent = new JBScrollPane(table);
    generateTable();

    JTree t = new Tutorial().getResult();
    t.setBackground(new Color(0,0,0,1));

    centerPanel.add(t);

    centerPanel.add(tableContent);
    page.add(centerPanel, "Center");

    bottomPanel = new JPanel();
    generateDefaultButtons();
    bottomPanel.add(button);
    page.add(bottomPanel, "South");

    return page;
  }

  private void generateDefaultButtons() {
    button = new JButton("CC detail");
    button.addActionListener(e -> {
      System.out.println("Listen button clicked action at CC");
      window.changeView("CC");
    });
  }

  private void generateTable() {
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

  public Integer getOOscore() {
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