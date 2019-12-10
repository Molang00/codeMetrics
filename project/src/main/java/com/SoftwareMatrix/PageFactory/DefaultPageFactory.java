package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.*;

public class DefaultPageFactory implements PageFactoryInterface, UpdateObserver {
  /* Declare private fields here */
  private MetricsResultWindow window;
  private JPanel DefaultPage;
  private Integer MIscore, OOscore;
  private JScrollPane tableContent;
  private JButton resetButton, MIButton, OOButton;
  private JPanel tablePanel;
  Object[] operators, operands;
  int edge, node, lloc, loc, cloc;

  public DefaultPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.DefaultPage = mainPanel;
  }

  public void update(Project project, PsiElement elem){
    // color refresh has 5~10 seconds of delay
    if(elem != null && ParseAdapter.getContainingMethod(elem) != null) {
      edge = ParseAdapter.getEdge(ParseAdapter.getContainingMethod(elem));
      node = ParseAdapter.getNode(ParseAdapter.getContainingMethod(elem));


    }

    System.out.println("111" + elem);
    System.out.println("222" + ParseAdapter.getContainingMethod(elem));
    System.out.println("333" + ParseAdapter.getContainingClass(elem));
    System.out.println("444" + ParseAdapter.getContainingPackage(elem));
  }

  /**
   * Constructor of tool window
   */
  public void createPage() {
    DefaultPage.removeAll();
    DefaultPage.setLayout(new BorderLayout());
    generateButtons();
    generateTable();

    generateTopView();
    generateCenterView();
    generateBottomView();

  }

  public void generateTopView() {
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(1, 3));

    JLabel mainLabel = new JLabel("Software Metrics");
    mainLabel.setHorizontalAlignment(JLabel.CENTER);

    JPanel jp = new JPanel();

    topPanel.add(jp);
    topPanel.add(mainLabel);
    topPanel.add(resetButton);

    DefaultPage.add(topPanel, BorderLayout.NORTH);
  }

  public void generateCenterView() {
    if (DefaultPage.getWidth() + DefaultPage.getHeight() != 0)
      tableContent.setPreferredSize(new Dimension(DefaultPage.getWidth(), DefaultPage.getHeight() / 2));

    JTree t = new Tutorial().getResult();
    t.setBackground(new Color(0, 0, 0, 1));

    tablePanel.add(t);
    tablePanel.add(tableContent);

    DefaultPage.add(tablePanel, BorderLayout.CENTER);
  }

  public void generateBottomView() {
    JPanel bottomPanel = new JPanel();
    bottomPanel.add(MIButton);
    bottomPanel.add(OOButton);

    DefaultPage.add(bottomPanel, BorderLayout.SOUTH);
  }

  public void generateButtons() {
    resetButton = new JButton("update");
    resetButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at reset");
      window.changeView("Default");
    });
    MIButton = new JButton("MI detail");
    MIButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at MI");
      window.changeView("MI");
    });
    OOButton = new JButton("OO detail");
    OOButton.addActionListener(e -> {
      System.out.println("Listen button clicked action at OO");
      window.changeView("OO");
    });
  }

  public void generateTable() {
    tablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JTable table = new JTable();
    tableContent = new JScrollPane(table);
    String[] header = { "Type", "Score", "Graph" };
    String[][] body = { { "MI", "", "" }, { "OO", "", "" } };
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

    settingAllStatus();
    table.setValueAt(MIscore, 0, 1);
    table.setValueAt(OOscore, 1, 1);

    table.getColumn("Graph").setCellRenderer(new ColoredTableCellRenderer());

    table.getColumnModel().getColumn(0).setPreferredWidth(90);
    table.getColumnModel().getColumn(1).setPreferredWidth(20);
    table.getColumnModel().getColumn(2).setPreferredWidth(50);
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