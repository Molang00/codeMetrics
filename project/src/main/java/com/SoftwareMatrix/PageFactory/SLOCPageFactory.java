package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.MICalculator;
import com.SoftwareMatrix.MetricsResultWindow;
import com.SoftwareMatrix.ParseAdapter;
import com.SoftwareMatrix.UpdateObserver;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.*;

public class SLOCPageFactory implements PageFactoryInterface, UpdateObserver {
  private MetricsResultWindow window;
  private JPanel page;
  private JScrollPane tableContent;
  private JButton resetButton, backButton;
  private JPanel tablePanel;
  private Integer lloc, loc, cloc;
  private JTable table;

  public SLOCPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.page = mainPanel;

    lloc = 0; loc = 0; cloc = 0;
    generateTable();;
  }

  public void update(Project project, PsiElement elem){
    // color refresh has 5~10 seconds of delay
    if(elem != null && ParseAdapter.getContainingMethod(elem) != null) {
      lloc = ParseAdapter.getLLoc(ParseAdapter.getContainingClass(elem));
      loc = ParseAdapter.getLoc(ParseAdapter.getContainingClass(elem));
      cloc = ParseAdapter.getCLoc(ParseAdapter.getContainingClass(elem));

      table.setValueAt(loc, 0, 1); // Set loc value
      table.setValueAt(lloc, 1, 1); // Set lloc value
      table.setValueAt(cloc, 2, 1); //Set cloc value
      tablePanel.revalidate();

    }
  }



  @Override
  public void createPage() {
    // TODO Auto-generated method stub
    page.removeAll();
    page.setLayout(new BorderLayout());
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

    JLabel mainLabel = new JLabel("Source Lines of Code Details");
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
    JPanel bottomPanel = new JPanel();
    bottomPanel.add(backButton);

    page.add(bottomPanel, BorderLayout.SOUTH);
  }

  @Override
  public void generateButtons() {
    resetButton = new JButton("update");
    resetButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at reset");
      window.changeView("SLOC");
    });
    backButton = new JButton("back");
    backButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at Default");
      window.changeView("MI");
    });
  }

  @Override
  public void generateTable() {
    tablePanel = new JPanel();
    table = new JTable();
    tableContent = new JScrollPane(table);
    String[] header = { "SLOC Features", "Score" };
    String[][] body = { { "LOC (Physical Line of Code)", "" }, { "LLOC (Logical Line of Code)", "" }, {"CLOC (Comment Line of Code)", ""} };
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

    table.setValueAt(loc, 0, 1); // Set loc value
    table.setValueAt(lloc, 1, 1); // Set lloc value
    table.setValueAt(cloc, 2, 1); //Set cloc value

    table.getColumnModel().getColumn(0).setPreferredWidth(90);
    table.getColumnModel().getColumn(1).setPreferredWidth(20);
  }

}