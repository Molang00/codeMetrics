package com.SoftwareMatrix;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.*;

public class MetricsResultWindow {
    /* Declare private fields here */
    JPanel myToolWindowContent;
    JTable overallScore;
    JScrollPane content;
    JPanel panel;
    Integer MIscore, OOscore;

    /**
     * Constructor of tool window
     */
    public MetricsResultWindow(ToolWindow toolWindow) {
        String header[] = { "", "score", "graph" };
        String body[][] = { { "MI", "", "" }, { "OO", "", "" } };
        DefaultTableModel model = new DefaultTableModel(body, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        overallScore = new JBTable();
        overallScore.setModel(model);
        settingAllStatus();
        overallScore.setValueAt(MIscore, 0, 1);
        overallScore.setValueAt(OOscore, 1, 1);
        TableColumnModel tcm = overallScore.getColumnModel();
        TableColumn tm = tcm.getColumn(2);
        tm.setCellRenderer(new ColoredTableCellRenderer());
        overallScore.setCellSelectionEnabled(false);
        overallScore.setFocusable(false);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JTree t = new Tutorial().getResult();

        t.setAlignmentX(Component.LEFT_ALIGNMENT);
        overallScore.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(t);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(overallScore);
        content = new JBScrollPane(panel);
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
        setMIscore(86);
        setOOscore(46);
    }

    /**
     * Returns content of this tool window
     * 
     * @return whole content of tool window
     */
    public JScrollPane getContent() {
        return content;
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
}
