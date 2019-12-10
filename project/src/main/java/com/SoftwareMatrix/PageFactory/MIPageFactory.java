package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.MetricsResultWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.*;

public class MIPageFactory implements PageFactoryInterface {
  /* Declare private fields here */
  private MetricsResultWindow window;
  private JPanel MIPage;
  private JScrollPane tableContent;
  private JButton resetButton, defaultButton, VButton, GButton, LOCButton;
  private JPanel tablePanel;

  public MIPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.MIPage = mainPanel;
  }

  @Override
  public void createPage() {
    MIPage.removeAll();
    MIPage.setLayout(new BorderLayout());
    generateButtons();
    generateTable();

    generateTopView();
    generateCenterView();
    generateBottomView();

  }

  @Override
  public void generateTopView() {
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1, 3));

    JLabel mainLabel = new JLabel("MI Details");
    mainLabel.setHorizontalAlignment(JLabel.CENTER);

    JPanel jp = new JPanel();

    topPanel.add(jp);
    topPanel.add(mainLabel);
    topPanel.add(resetButton);

    MIPage.add(topPanel, BorderLayout.NORTH);
  }

  @Override
  public void generateCenterView() {
    tableContent.setPreferredSize(new Dimension(MIPage.getWidth(), MIPage.getHeight() / 2));
    tablePanel.add(tableContent);

    MIPage.add(tablePanel, BorderLayout.CENTER);
  }

  @Override
  public void generateBottomView() {
    JPanel bottomPanel = new JPanel();
    bottomPanel.add(defaultButton);
    bottomPanel.add(VButton);
    bottomPanel.add(GButton);
    bottomPanel.add(LOCButton);

    MIPage.add(bottomPanel, BorderLayout.SOUTH);
  }

  @Override
  public void generateButtons() {
    resetButton = new JButton("update");
    resetButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at reset");
      window.changeView("MI");
    });
    defaultButton = new JButton("back");
    defaultButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at Default");
      window.changeView("Default");
    });
    VButton = new JButton("V page");
    VButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at V");
      window.changeView("V");
    });
    GButton = new JButton("G page");
    GButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at G");
      window.changeView("G");
    });
    LOCButton = new JButton("SLOC page");
    LOCButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at SLOC");
      window.changeView("SLOC");
    });
  }

  @Override
  public void generateTable() {
    tablePanel = new JPanel();
    JTable table = new JTable();
    tableContent = new JScrollPane(table);
    String[] header = { "MI Features", "Score", "Graph" };
    String[][] body = { { "V (Haslstead's volume)", "", "" }, { "G (Cyclomatic complexity)", "", "" },
        { "SLOC(Source lines of code)", "", "" } };
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

    table.setValueAt(150, 0, 1); // Set V value
    table.setValueAt(20, 1, 1); // Set G value
    table.setValueAt(300, 2, 1); // Set SLOC value

    table.getColumn("Graph").setCellRenderer(new ProgressRenderer(0, 500));

    table.getColumnModel().getColumn(0).setPreferredWidth(90);
    table.getColumnModel().getColumn(1).setPreferredWidth(20);
    table.getColumnModel().getColumn(2).setPreferredWidth(50);
  }
}