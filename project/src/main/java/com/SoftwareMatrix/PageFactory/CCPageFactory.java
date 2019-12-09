package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.MetricsResultWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.*;

public class CCPageFactory implements PageFactoryInterface {
  /* Declare private fields here */
  MetricsResultWindow window;
  JPanel page;
  JTable table;
  JScrollPane tableContent;
  JButton resetButton, backButton;
  JLabel mainLabel;
  JPanel tablePanel, bottomPanel, topPanel;
  Integer edge = 5, node = 4, CCValue = 11;

  public CCPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.page = mainPanel;
  }

  @Override
  public JPanel createPage() {
    page.removeAll();
    page.setLayout(new BorderLayout());
    generateButtons();
    generateTable();

    generateTopView();
    generateCenterView();
    generateBottomView();

    return page;
  }

  @Override
  public void generateTopView() {
    topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1, 3));

    mainLabel = new JLabel("Cyclomatic Complexity Details");
    mainLabel.setHorizontalAlignment(JLabel.CENTER);

    JPanel jp = new JPanel();

    topPanel.add(jp);
    topPanel.add(mainLabel);
    topPanel.add(resetButton);

    page.add(topPanel, BorderLayout.NORTH);
  }

  @Override
  public void generateCenterView() {
    tableContent.setPreferredSize(new Dimension(page.getWidth(), page.getHeight() / 2));
    tablePanel.add(tableContent);

    page.add(tablePanel, BorderLayout.CENTER);
  }

  @Override
  public void generateBottomView() {
    bottomPanel = new JPanel();
    bottomPanel.add(backButton);

    page.add(bottomPanel, BorderLayout.SOUTH);
  }

  @Override
  public void generateButtons() {
    resetButton = new JButton("reset");
    resetButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at reset");
      window.changeView("G");
    });
    backButton = new JButton("back");
    backButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at back");
      window.changeView("MI");
    });
  }

  @Override
  public void generateTable() {
    tablePanel = new JPanel();
    table = new JTable();
    tableContent = new JScrollPane(table);
    String header[] = { "CC Features", "Score", "Graph" };
    String body[][] = { { "Edge", "", "" }, { "Node", "", "" }, { "CC Value", "", "" } };
    DefaultTableModel model = new DefaultTableModel(body, header) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table.setModel(model);
    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
    dtcr.setHorizontalAlignment(SwingConstants.CENTER);
    table.getColumnModel().getColumn(0).setCellRenderer(dtcr);
    table.getColumnModel().getColumn(1).setCellRenderer(dtcr);

    table.setValueAt(edge, 0, 1); // Set edge value
    table.setValueAt(node, 1, 1); // Set node value
    table.setValueAt(CCValue, 2, 1); // Set CC value

    table.getColumn("Graph").setCellRenderer(new ProgressRenderer(0, 20));

    table.getColumnModel().getColumn(0).setPreferredWidth(90);
    table.getColumnModel().getColumn(1).setPreferredWidth(20);
    table.getColumnModel().getColumn(2).setPreferredWidth(50);
  }
}