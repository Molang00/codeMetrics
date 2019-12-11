package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.*;
import java.util.Set;

public class OOPageFactory implements PageFactoryInterface, UpdateObserver {
  /* Declare private fields here */
  private MetricsResultWindow window;
  private JPanel OOPage;
  private JScrollPane tableContent;
  private JButton resetButton, defaultButton;
  private JPanel tablePanel;
  private Integer NM, PM, NPV, NV, NMI, NMO, AMS, PF, MHF, MIF, AHF, AIF, DIT, NOC, WMC;
  private OOMCalculator oomCalculator;

  public OOPageFactory(MetricsResultWindow window, JPanel mainPanel) {
    this.window = window;
    this.OOPage = mainPanel;

    NM = 0; PM = 0; NPV = 0;
    NV = 0; NMI = 0; NMO = 0;
    AMS = 0; PF = 0; MHF = 0;
    MIF = 0; AHF = 0; AIF = 0;
    DIT = 0; NOC = 0; WMC = 0;

    generateTable();;
  }

  public void update(Project project, PsiElement elem){

//    if(elem != null && ParseAdapter.getContainingMethod(elem) != null) {
//      Set<PsiPackage> allPackages = ParseAdapter.getProjectPackages(project);
//
//      NM = 0; PM = 0; NPV = 0;
//      NV = 0; NMI = 0; NMO = 0;
//      AMS = 0; PF = 0; MHF = 0;
//      MIF = 0; AHF = 0; AIF = 0;
//      DIT = 0; NOC = 0; WMC = 0;
//
//      for(PsiPackage p : allPackages) {
//        oomCalculator = new OOMCalculator(p);
//
//        NM += oomCalculator.GetPackageNM();
//        PM += oomCalculator.GetPackagePM();
//        NPV += oomCalculator.GetPackageNPV();
//
//        NV += oomCalculator.GetPackageNV();
//        NMI += oomCalculator.GetPackageNMI();
//        NMO += oomCalculator.GetPackageNMO();
//
//        AMS += oomCalculator.GetPackageAMS();
//        PF += oomCalculator.GetPackagePF();
//        MHF += oomCalculator.GetPackageMHF();
//
//        MIF += oomCalculator.GetPackageMIF();
//        AHF += oomCalculator.GetPackageAHF();
//        AIF += oomCalculator.GetPackageAIF();
//
//        DIT += oomCalculator.GetPackageDIT();
//        NOC += oomCalculator.GetPackageNOC();
//        WMC += oomCalculator.GetPackageWMC();
//      }
//    }
  }

  @Override
  public void createPage() {
    OOPage.removeAll();
    OOPage.setLayout(new BorderLayout());
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

    JLabel mainLabel = new JLabel("OO Details");
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
    JPanel bottomPanel = new JPanel();
    bottomPanel.add(defaultButton);

    OOPage.add(bottomPanel, BorderLayout.SOUTH);
  }

  @Override
  public void generateButtons() {
    resetButton = new JButton("update");
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
    JTable table = new JTable();
    tableContent = new JScrollPane(table);
    String[] header = { "OO Features", "Score", "Graph" };
    String[][] body = {
            { "NM (Number of Methods in Project)", "", "" },
            { "PM (Number of Public Methdos in Project)", "", "" },
            { "NPV (Number of Public Variables in Project)", "", "" },

            { "NV (Number of Variables in Project)", "", "" },
            { "NMI (Number of Methods Inherited by a subclass in Project)", "", "" },
            { "NMO (Number of Methods overridden by a subclass in Project)", "", "" },

            { "AMS (Average method size of class in Project", "", "" },
            { "PF (Polymorphism Factor in Project)", "", "" },
            { "MHF (Method Hiding Factor in Project)", "", "" },

            { "MIF (Method Inheritance Factor in Project)", "", "" },
            { "AHF (Attribute Hiding Factor in Project)", "", "" },
            { "AIF (Attribute Inheritance Factor in Project", "", "" },

            { "DIT (Depth of Inheritance Tree in Project", "", "" },
            { "NOC (Number Of Children in Project)", "", "" },
            { "WMC (Weighted Methods per Class in Project)", "", "" }
    };

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

    table.setValueAt(NM, 0, 1); // Set NM value
    table.setValueAt(PM, 1, 1); // Set PM value
    table.setValueAt(NPV, 2, 1); // Set NPV value

    table.setValueAt(NV, 3, 1); // Set NV value
    table.setValueAt(NMI, 4, 1); // Set NMI value
    table.setValueAt(NMO, 5, 1); // Set NMO value

    table.setValueAt(AMS, 6, 1); // Set NV value
    table.setValueAt(PF, 7, 1); // Set NMI value
    table.setValueAt(MHF, 8, 1); // Set NMO value

    table.setValueAt(MIF, 9, 1); // Set NV value
    table.setValueAt(AHF, 10, 1); // Set NMI value
    table.setValueAt(AIF, 11, 1); // Set NMO value

    table.setValueAt(DIT, 12, 1); // Set NV value
    table.setValueAt(NOC, 13, 1); // Set NMI value
    table.setValueAt(WMC, 14, 1); // Set NMO value


    table.getColumn("Graph").setCellRenderer(new ProgressRenderer(0, 20));

    table.getColumnModel().getColumn(0).setPreferredWidth(120);
    table.getColumnModel().getColumn(1).setPreferredWidth(10);
    table.getColumnModel().getColumn(2).setPreferredWidth(40);
  }
}