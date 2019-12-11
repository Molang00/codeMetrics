package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.metrics.Metric;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import java.awt.*;

class ProgressRenderer extends JProgressBar implements TableCellRenderer {
    public ProgressRenderer(int min, int max) { super(min, max); }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus, int row, int column) {
        this.setValue((int)Math.round(((Metric)(table.getValueAt(row, 0))).getRatio()*100.0));
        return this;
    }
}