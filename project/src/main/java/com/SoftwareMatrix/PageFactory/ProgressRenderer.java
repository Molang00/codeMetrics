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
        System.out.println(row+" "+((Metric)(table.getValueAt(row, 0))).getRatio()+" "+(int)Math.round(((Metric)(table.getValueAt(row, 0))).getRatio()*100.0));
        this.setValue((int)Math.round(((Metric)(table.getValueAt(row, 0))).getRatio()*100.0));
//        System.out.println();
//        this.setValue((int)Math.round(Double.parseDouble(table.getValueAt(row, 1).toString())*100));
        return this;
    }
}