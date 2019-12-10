package com.SoftwareMatrix.PageFactory;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import java.awt.*;

class ProgressRenderer extends JProgressBar implements TableCellRenderer {
    public ProgressRenderer(int min, int max) {
        super(min, max);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus, int row, int column) {
        this.setValue(Integer.parseInt(table.getValueAt(row, column-1).toString()));
        return this;
    }
}