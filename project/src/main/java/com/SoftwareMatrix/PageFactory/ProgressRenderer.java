package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.metrics.Metric;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import java.awt.*;

/**
 * Class for rendering the graph of table
 */
class ProgressRenderer extends JProgressBar implements TableCellRenderer {
    /**
     * Constructor of ProgressRenderer
     *
     * @param min Minimum value of the graph
     * @param max Maximum value of the graph
     */
    public ProgressRenderer(int min, int max) { super(min, max); }

    /**
     * Update the graph depending on table's value
     *
     * @param table JTable that contains the value of the graph
     * @param value UNUSED
     * @param isSelected UNUSED
     * @param hasFocus UNUSED
     * @param row location of data
     * @param column UNUSED
     * @return
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus, int row, int column) {
        this.setValue((int)Math.round(((Metric)(table.getValueAt(row, 0))).getRatio()*100.0));
        return this;
    }
}