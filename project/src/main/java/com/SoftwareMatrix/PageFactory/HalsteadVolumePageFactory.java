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
import java.util.HashSet;
import java.util.Set;

public class HalsteadVolumePageFactory implements PageFactoryInterface, UpdateObserver {
  private MetricsResultWindow window;
  private JPanel page;
  private JScrollPane tableContent;
  private JButton resetButton, backButton;
  private JPanel tablePanel;
  private Set<PsiElement> operators, operands;
  private Integer eta, n;
  private JTable table;

  public HalsteadVolumePageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.page = mainPanel;
    operators = new HashSet<>();
    operands = new HashSet<>();
    eta = 0;
    n = 0;

    generateTable();;
  }

  @Override
  public void createPage() {
    page.removeAll();
    page.setLayout(new BorderLayout());
    generateButtons();
    generateTable();

    generateTopView();
    generateCenterView();
    generateBottomView();
  }

  public void update(Project project, PsiElement elem){
    // color refresh has 5~10 seconds of delay
    if(elem != null && ParseAdapter.getContainingMethod(elem) != null) {
      operators = ParseAdapter.getOperators(ParseAdapter.getContainingClass(elem));
      operands = ParseAdapter.getOperands(ParseAdapter.getContainingClass(elem));
      eta = MICalculator.calculateEta(operators, operands);
      n = MICalculator.calculateN(operators, operands);

      table.setValueAt(eta, 0, 1); // Set V value
      table.setValueAt(n, 1, 1); // Set G value

      tablePanel.revalidate();
    }
  }

  @Override
  public void generateTopView() {
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1, 3));

    JLabel mainLabel = new JLabel("Haslsted Volume Details");
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
      window.changeView("V");
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
    String[] header = { "V Features", "Score" };
    String[][] body = { { "Eta (number of distinct operators & operands)", "" },
        { "N (total number of operators & operands)", "" } };
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

    table.setValueAt(eta, 0, 1); // Set V value
    table.setValueAt(n, 1, 1); // Set G value

    table.getColumnModel().getColumn(0).setPreferredWidth(90);
    table.getColumnModel().getColumn(1).setPreferredWidth(20);
  }

}