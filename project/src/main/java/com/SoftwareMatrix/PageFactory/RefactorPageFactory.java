package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.*;
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
import java.util.*;
import java.util.List;

public class RefactorPageFactory implements UpdateObserver {
    private List<Metric> metricList;
    private List<JButton> buttonList;
    private JPanel mainPanel;
    private MetricsResultWindow window;
    private String pageName;
    private JTable table;
    private JPanel tablePanel;

    public RefactorPageFactory(String name, MetricsResultWindow _window, JPanel _mainPanel){
        window = _window;
        mainPanel = _mainPanel;
        pageName = name;

        metricList = new ArrayList<>();
        buttonList = new ArrayList<>();
    }

    public void createPage() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        tablePanel = new JPanel();
        table = new JTable();
        JScrollPane tableContent = new JScrollPane(table);

        String[] header = { pageName+" Features", "Score", "Graph" };
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
        for(Metric m : metricList){
            table.setValueAt(m, i, 0);
            table.setValueAt(0, i, 1);
            i++;
        }

        table.getColumn("Graph").setCellRenderer(new ProgressRenderer(0, 100));

        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);


        JPanel topPanel = new JPanel();
        JLabel mainLabel = new JLabel(pageName+" Details");
        mainLabel.setHorizontalAlignment(JLabel.CENTER);

        topPanel.add(mainLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        if(mainPanel.getWidth() != 0)
            tableContent.setPreferredSize(new Dimension(mainPanel.getWidth(), (mainPanel.getHeight() * 3) / 4));

        JTree t = new Tutorial().getResult();
        t.setBackground(new Color(0, 0, 0, 1));

        tablePanel.add(t);
        tablePanel.add(tableContent);

        mainPanel.add(tablePanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        JLabel warningMessage = new JLabel("<html>&nbsp;&nbsp;&nbsp;&nbsp;If any value above is -1,<br>&nbsp;&nbsp;&nbsp;&nbsp;it is a value that cannot be calculated <br>&nbsp;&nbsp;&nbsp;&nbsp;in the currently selected class.</html>");
        warningMessage.setPreferredSize(new Dimension(mainPanel.getWidth(), 50));
        bottomPanel.add(warningMessage, BorderLayout.NORTH);

        for (JButton button: buttonList) {
            buttonPanel.add(button);
        }
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                tableContent.setPreferredSize(new Dimension(mainPanel.getWidth(), (mainPanel.getHeight() * 3) / 4));
            }
            @Override
            public void componentShown(ComponentEvent e){
                tableContent.setPreferredSize(new Dimension(mainPanel.getWidth(), (mainPanel.getHeight() * 3) / 4));
            }
        });
    }

    public boolean addMetric(Metric metric) {
        return metricList.add(metric);
    }

    public boolean addButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(e -> {
            window.changeView(name);
        });
        return buttonList.add(button);
    }

    @Override
    public void update(Project project, PsiElement elem){
        // color refresh has 5~10 seconds of delay
        if(elem == null) return;
        PsiClass _class = ParseAdapter.getContainingClass(elem);
        if(_class != null) {
            int i = 0;
            for(Metric m : metricList) {
                table.setValueAt(Math.round(m.calculate(project, _class)*100.0)/100.0, i++, 1); // Set edge value
            }
        }
        tablePanel.revalidate();
    }
}
