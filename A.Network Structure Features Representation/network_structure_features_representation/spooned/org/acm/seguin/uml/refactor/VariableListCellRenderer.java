package org.acm.seguin.uml.refactor;
class VariableListCellRenderer extends javax.swing.JLabel implements javax.swing.ListCellRenderer {
    public VariableListCellRenderer() {
        setOpaque(true);
    }

    public java.awt.Component getListCellRendererComponent(javax.swing.JList list, java.lang.Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof org.acm.seguin.summary.VariableSummary) {
            org.acm.seguin.summary.VariableSummary varSummary = ((org.acm.seguin.summary.VariableSummary) (value));
            setText(((varSummary.getName() + " (") + varSummary.getType()) + ")");
        } else {
            setText(value.toString());
        }
        setBackground(isSelected ? java.awt.Color.red : java.awt.Color.white);
        setForeground(isSelected ? java.awt.Color.white : java.awt.Color.black);
        return this;
    }
}