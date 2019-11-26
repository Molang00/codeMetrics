package com.SoftwareMatrix.PageFactory;

import com.SoftwareMatrix.MetricsResultWindow;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;


class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
    JButton jb;
    String label;
    MetricsResultWindow resultWindow;

    public TableCell(MetricsResultWindow resultWindow) {
        this.resultWindow = resultWindow;
        jb = new JButton();
        jb.setOpaque(true);
        jb.addActionListener(e -> {
            System.out.println("Listen button clicked action at"+label);
            resultWindow.changeView("MI");
        });
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            jb.setForeground(table.getSelectionForeground());
            jb.setBackground(table.getSelectionBackground());
        } else {
            jb.setForeground(table.getForeground());
            jb.setBackground(UIManager.getColor("Button.background"));
        }
        jb.setText((value == null) ? "" : value.toString());
        return jb;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            jb.setForeground(table.getSelectionForeground());
            jb.setBackground(table.getSelectionBackground());
        } else {
            jb.setForeground(table.getForeground());
            jb.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        jb.setText(label);
        System.out.println(label);
        return jb;
    }
}