package com.jmonkey.office.lexi.support;
// JMonkey Imports
// import com.jmonkey.core.Registry;
public final class PropertySheet extends javax.swing.JPanel {
    private java.util.Properties _P = null;

    private java.awt.Frame _PARENT = null;

    private final class PairTableModel extends javax.swing.table.AbstractTableModel {
        public PairTableModel() {
            super();
        }

        public int getRowCount() {
            return getProperties().size();
        }

        public int getColumnCount() {
            return 2;
        }

        public java.lang.String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0 :
                    return "Key";
                case 1 :
                    return "Value";
                default :
                    return null;
            }
        }

        public java.lang.Class getColumnClass(int columnIndex) {
            return java.lang.String.class;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0 :
                    return false;
                case 1 :
                    return true;
                default :
                    return false;
            }
        }

        public java.lang.Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0 :
                    return ((java.lang.String) (getProperties().keySet().toArray()[rowIndex].toString()));
                case 1 :
                    return ((java.lang.String) (getProperties().getProperty(((java.lang.String) (getProperties().keySet().toArray()[rowIndex].toString())))));
                default :
                    return "";
            }
        }

        public void setValueAt(java.lang.Object aValue, int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0 :
                    // getProperties().keySet().toArray()[rowIndex] = aValue.toString();
                    break;
                case 1 :
                    getProperties().setProperty(((java.lang.String) (getProperties().keySet().toArray()[rowIndex].toString())), aValue.toString());
                    break;
            }
        }
    }

    public PropertySheet(java.awt.Frame parent, java.util.Properties p) {
        super();
        this._PARENT = parent;
        this._P = p;
        this.init();
    }

    public final java.util.Properties getProperties() {
        return _P;
    }

    private void init() {
        this.setLayout(new java.awt.BorderLayout());
        this.add(new javax.swing.JScrollPane(new javax.swing.JTable(new com.jmonkey.office.lexi.support.PropertySheet.PairTableModel())), java.awt.BorderLayout.CENTER);
    }
}