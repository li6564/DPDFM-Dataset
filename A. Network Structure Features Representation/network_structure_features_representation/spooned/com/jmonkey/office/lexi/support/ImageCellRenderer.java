package com.jmonkey.office.lexi.support;
public class ImageCellRenderer extends javax.swing.JLabel implements javax.swing.ListCellRenderer {
    public ImageCellRenderer() {
        setOpaque(true);
    }

    public java.awt.Component getListCellRendererComponent(javax.swing.JList list, java.lang.Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        setBackground(isSelected ? java.awt.Color.red : java.awt.Color.white);
        setForeground(isSelected ? java.awt.Color.white : java.awt.Color.black);
        return this;
    }
}