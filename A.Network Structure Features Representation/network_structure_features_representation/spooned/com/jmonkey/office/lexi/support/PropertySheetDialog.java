package com.jmonkey.office.lexi.support;
// JMonkey Imports
// import com.jmonkey.core.Registry;
public final class PropertySheetDialog extends javax.swing.JDialog {
    private java.util.Properties _P = null;

    private java.awt.Frame _PARENT = null;

    private boolean _ALLOW_ADD = false;

    private com.jmonkey.office.lexi.support.PropertySheetDialog.PairTableModel _MODEL = null;

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

    private PropertySheetDialog(java.awt.Frame parent, java.util.Properties p, boolean allowAdd) {
        super(parent);
        this._PARENT = parent;
        this._P = p;
        this._ALLOW_ADD = allowAdd;
        this.init();
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    public static final java.util.Properties display(java.awt.Frame parent, java.util.Properties p) {
        com.jmonkey.office.lexi.support.PropertySheetDialog psd = new com.jmonkey.office.lexi.support.PropertySheetDialog(parent, p, false);
        return psd.getProperties();
    }

    public static final java.util.Properties display(java.awt.Frame parent, java.util.Properties p, boolean allowAdd) {
        com.jmonkey.office.lexi.support.PropertySheetDialog psd = new com.jmonkey.office.lexi.support.PropertySheetDialog(parent, p, allowAdd);
        return psd.getProperties();
    }

    private void doExit() {
        this.dispose();
    }

    protected final java.util.Properties getProperties() {
        return _P;
    }

    private void init() {
        javax.swing.JPanel content = new javax.swing.JPanel();
        content.setLayout(new java.awt.BorderLayout());
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.setLayout(new java.awt.BorderLayout());
        javax.swing.JPanel spacerPanel = new javax.swing.JPanel();
        spacerPanel.setLayout(new java.awt.GridLayout());
        if (_ALLOW_ADD) {
            javax.swing.JButton addButton = new javax.swing.JButton("Add Key");
            addButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    java.lang.String inputValue = javax.swing.JOptionPane.showInputDialog("What is the key you want to add?");
                    if (inputValue != null) {
                        if (inputValue.trim().length() > 0) {
                            _P.setProperty(inputValue, "");
                            // redraw the table
                            if (_MODEL != null) {
                                _MODEL.fireTableDataChanged();
                            }
                        }
                    }
                }
            });
            spacerPanel.add(addButton);
        }
        javax.swing.JButton closeButton = new javax.swing.JButton("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                doExit();
            }
        });
        spacerPanel.add(closeButton);
        buttonPanel.add(spacerPanel, java.awt.BorderLayout.EAST);
        content.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        _MODEL = new com.jmonkey.office.lexi.support.PropertySheetDialog.PairTableModel();
        content.add(new javax.swing.JScrollPane(new javax.swing.JTable(_MODEL)), java.awt.BorderLayout.CENTER);
        this.setContentPane(content);
        // Added this to dispose of
        // the main app window when
        // it gets closed.
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                doExit();
            }
        });
    }
}