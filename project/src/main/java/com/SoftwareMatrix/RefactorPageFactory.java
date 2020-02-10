package com.SoftwareMatrix;

import com.SoftwareMatrix.metrics.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * PageFactory that implements UpdateObservers, which shows calculation result
 * on the panel.
 */
public class RefactorPageFactory implements UpdateObserver {
    private List<Metric> metricList;
    private JPanel mainPanel;
    private MetricsResultWindow window;
    private String pageName;
    private JTable table;
    private JPanel tablePanel;

    /**
     * Constructor of RefactorPageFacotry
     *
     * @param name       Name of page
     * @param _window    Window containing page, which panels will be located in
     * @param _mainPanel Main panel for result
     */
    public RefactorPageFactory(String name, MetricsResultWindow _window, JPanel _mainPanel) {
        window = _window;
        mainPanel = _mainPanel;
        pageName = name;

        metricList = new ArrayList<>();
    }

    /**
     * Build tables and basic GUI on the window. Adds tutorial, makes result table,
     * and buttons
     */
    public void createPage() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        tablePanel = new JPanel();
        table = new JTable();
        JScrollPane tableContent = new JScrollPane(table);

        String[] header = { pageName + " Features", "Score" };
        DefaultTableModel model = new DefaultTableModel(header, metricList.size()) {
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

        int i = 0;
        for (Metric m : metricList) {
            table.setValueAt(m, i, 0);
            table.setValueAt(0, i, 1);
            i++;
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);

        JPanel topPanel = new JPanel();
        JLabel mainLabel = new JLabel(pageName + "Code Metrics");
        mainLabel.setHorizontalAlignment(JLabel.CENTER);

        topPanel.add(mainLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        if (mainPanel.getWidth() != 0)
            tableContent.setPreferredSize(new Dimension(mainPanel.getWidth(), (mainPanel.getHeight() * 3) / 4));

        tablePanel.add(tableContent);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                tableContent.setPreferredSize(new Dimension(mainPanel.getWidth(), (mainPanel.getHeight() * 3) / 4));
            }

            @Override
            public void componentShown(ComponentEvent e) {
                tableContent.setPreferredSize(new Dimension(mainPanel.getWidth(), (mainPanel.getHeight() * 3) / 4));
            }
        });

        JButton reportButton = new JButton("Generate Report");
        reportButton.addActionListener(e -> {
            try {
                window.exitReport();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(reportButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Add new Metric
     *
     * @param metric Metric to add
     * @return true if metric is added to list successfully, otherwise false
     */
    public boolean addMetric(Metric metric) {
        return metricList.add(metric);
    }

    /**
     * Update table with given calculated value.
     *
     * @param project Project that our plugin analyzes and shows about
     * @param elem    PsiElement that makes event
     */
    @Override
    public void update(Project project, PsiElement elem) {
        // color refresh has 5~10 seconds of delay
        if (elem == null)
            return;
        PsiClass _class = ParseAdapter.getContainingClass(elem);
        if (_class != null) {
            int i = 0;
            for (Metric m : metricList) {
                table.setValueAt(Math.round(m.calculate(project, _class) * 100.0) / 100.0, i++, 1); // Set edge value
            }
        }
        tablePanel.revalidate();
    }
}
