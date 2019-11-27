package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.MetricsResultWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.*;

public class OOPageFactory implements PageFactoryInterface {
  /* Declare private fields here */
  MetricsResultWindow window;
  JPanel OOPage;
  JTable table;
  JScrollPane tableContent;
  JButton resetButton, defaultButton;
  JLabel mainLabel;
  JPanel tablePanel, bottomPanel, topPanel;

  public OOPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.OOPage = mainPanel;
  }

  @Override
  public JPanel createPage() {
    OOPage.removeAll();
    OOPage.setLayout(new BorderLayout());
    generateButtons();
    generateTable();

    generateTopView();
    generateCenterView();
    generateBottomView();

    return OOPage;
  }

  @Override
  public void generateTopView() {
    topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1, 3));

    mainLabel = new JLabel("OO Details");
    mainLabel.setHorizontalAlignment(JLabel.CENTER);

    JPanel jp = new JPanel();

    topPanel.add(jp);
    topPanel.add(mainLabel);
    topPanel.add(resetButton);

    OOPage.add(topPanel, BorderLayout.NORTH);
  }

  @Override
  public void generateCenterView() {
    tableContent.setPreferredSize(new Dimension(OOPage.getWidth(), OOPage.getHeight() / 2));
    tablePanel.add(tableContent);

    OOPage.add(tablePanel, BorderLayout.CENTER);
  }

  @Override
  public void generateBottomView() {
    bottomPanel = new JPanel();
    bottomPanel.add(defaultButton);

    OOPage.add(bottomPanel, BorderLayout.SOUTH);
  }

  @Override
  public void generateButtons() {
    resetButton = new JButton("reset");
    resetButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at reset");
      window.changeView("OO");
    });
    defaultButton = new JButton("back");
    defaultButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at Default");
      window.changeView("Default");
    });
  }

  @Override
  public void generateTable() {
    tablePanel = new JPanel();
    table = new JTable();
    tableContent = new JScrollPane(table);
    String header[] = { "OO Features", "Score", "Graph" };
    String body[][] = { { "WMC (Weighted method per class", "", "" }, { "DIT (Depth of Inheritance Tree", "", "" },
        { "NOC (Number Of Children)", "", "" }, { "CBO (Coupling Between Object classes)", "", "" },
        { "RFC (Response For Class)", "", "" }, { "LOCM (Lack Of Cohesion Metrics)", "", "" } };
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

    table.setValueAt(15, 0, 1); // Set WMC value
    table.setValueAt(10, 1, 1); // Set DIT value
    table.setValueAt(5, 2, 1); // Set NOC value
    table.setValueAt(2, 3, 1); // Set CBO value
    table.setValueAt(7, 4, 1); // Set RFC value
    table.setValueAt(3, 5, 1); // Set LOCM value

    table.getColumn("Graph").setCellRenderer(new ProgressRenderer(0, 20));

    table.getColumnModel().getColumn(0).setPreferredWidth(120);
    table.getColumnModel().getColumn(1).setPreferredWidth(10);
    table.getColumnModel().getColumn(2).setPreferredWidth(40);
  }
}