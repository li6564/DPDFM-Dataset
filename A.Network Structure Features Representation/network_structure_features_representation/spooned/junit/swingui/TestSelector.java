package junit.swingui;
/**
 * A test class selector. A simple dialog to pick the name of a test suite.
 */
class TestSelector extends javax.swing.JDialog {
    private javax.swing.JButton fCancel;

    private javax.swing.JButton fOk;

    private javax.swing.JList fList;

    private javax.swing.JScrollPane fScrolledList;

    private javax.swing.JLabel fDescription;

    private java.lang.String fSelectedItem;

    /**
     * Renders TestFailures in a JList
     */
    static class TestCellRenderer extends javax.swing.DefaultListCellRenderer {
        javax.swing.Icon fLeafIcon;

        javax.swing.Icon fSuiteIcon;

        public TestCellRenderer() {
            fLeafIcon = javax.swing.UIManager.getIcon("Tree.leafIcon");
            fSuiteIcon = javax.swing.UIManager.getIcon("Tree.closedIcon");
        }

        public java.awt.Component getListCellRendererComponent(javax.swing.JList list, java.lang.Object value, int modelIndex, boolean isSelected, boolean cellHasFocus) {
            java.awt.Component c = super.getListCellRendererComponent(list, value, modelIndex, isSelected, cellHasFocus);
            java.lang.String displayString = junit.swingui.TestSelector.TestCellRenderer.displayString(((java.lang.String) (value)));
            if (displayString.startsWith("AllTests"))
                setIcon(fSuiteIcon);
            else
                setIcon(fLeafIcon);

            setText(displayString);
            return c;
        }

        public static java.lang.String displayString(java.lang.String className) {
            int typeIndex = className.lastIndexOf('.');
            if (typeIndex < 0)
                return className;

            return (className.substring(typeIndex + 1) + " - ") + className.substring(0, typeIndex);
        }

        public static boolean matchesKey(java.lang.String s, char ch) {
            return ch == java.lang.Character.toUpperCase(s.charAt(junit.swingui.TestSelector.TestCellRenderer.typeIndex(s)));
        }

        private static int typeIndex(java.lang.String s) {
            int typeIndex = s.lastIndexOf('.');
            int i = 0;
            if (typeIndex > 0)
                i = typeIndex + 1;

            return i;
        }
    }

    protected class DoubleClickListener extends java.awt.event.MouseAdapter {
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (e.getClickCount() == 2) {
                okSelected();
            }
        }
    }

    protected class KeySelectListener extends java.awt.event.KeyAdapter {
        public void keyTyped(java.awt.event.KeyEvent e) {
            keySelectTestClass(e.getKeyChar());
        }
    }

    public TestSelector(java.awt.Frame parent, junit.runner.TestCollector testCollector) {
        super(parent, true);
        setSize(350, 300);
        setResizable(false);
        setLocationRelativeTo(parent);
        setTitle("Test Selector");
        java.util.Vector list = null;
        try {
            parent.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
            list = createTestList(testCollector);
        } finally {
            parent.setCursor(java.awt.Cursor.getDefaultCursor());
        }
        fList = new javax.swing.JList(list);
        fList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fList.setCellRenderer(new junit.swingui.TestSelector.TestCellRenderer());
        fScrolledList = new javax.swing.JScrollPane(fList);
        fCancel = new javax.swing.JButton("Cancel");
        fDescription = new javax.swing.JLabel("Select the Test class:");
        fOk = new javax.swing.JButton("OK");
        fOk.setEnabled(false);
        getRootPane().setDefaultButton(fOk);
        defineLayout();
        addListeners();
    }

    private void addListeners() {
        fCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
        fOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                okSelected();
            }
        });
        fList.addMouseListener(new junit.swingui.TestSelector.DoubleClickListener());
        fList.addKeyListener(new junit.swingui.TestSelector.KeySelectListener());
        fList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                checkEnableOK(e);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
            }
        });
    }

    private void defineLayout() {
        getContentPane().setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints labelConstraints = new java.awt.GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.gridwidth = 1;
        labelConstraints.gridheight = 1;
        labelConstraints.fill = java.awt.GridBagConstraints.BOTH;
        labelConstraints.anchor = java.awt.GridBagConstraints.WEST;
        labelConstraints.weightx = 1.0;
        labelConstraints.weighty = 0.0;
        labelConstraints.insets = new java.awt.Insets(8, 8, 0, 8);
        getContentPane().add(fDescription, labelConstraints);
        java.awt.GridBagConstraints listConstraints = new java.awt.GridBagConstraints();
        listConstraints.gridx = 0;
        listConstraints.gridy = 1;
        listConstraints.gridwidth = 4;
        listConstraints.gridheight = 1;
        listConstraints.fill = java.awt.GridBagConstraints.BOTH;
        listConstraints.anchor = java.awt.GridBagConstraints.CENTER;
        listConstraints.weightx = 1.0;
        listConstraints.weighty = 1.0;
        listConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        getContentPane().add(fScrolledList, listConstraints);
        java.awt.GridBagConstraints okConstraints = new java.awt.GridBagConstraints();
        okConstraints.gridx = 2;
        okConstraints.gridy = 2;
        okConstraints.gridwidth = 1;
        okConstraints.gridheight = 1;
        okConstraints.anchor = java.awt.GridBagConstraints.EAST;
        okConstraints.insets = new java.awt.Insets(0, 8, 8, 8);
        getContentPane().add(fOk, okConstraints);
        java.awt.GridBagConstraints cancelConstraints = new java.awt.GridBagConstraints();
        cancelConstraints.gridx = 3;
        cancelConstraints.gridy = 2;
        cancelConstraints.gridwidth = 1;
        cancelConstraints.gridheight = 1;
        cancelConstraints.anchor = java.awt.GridBagConstraints.EAST;
        cancelConstraints.insets = new java.awt.Insets(0, 8, 8, 8);
        getContentPane().add(fCancel, cancelConstraints);
    }

    public void checkEnableOK(javax.swing.event.ListSelectionEvent e) {
        fOk.setEnabled(fList.getSelectedIndex() != (-1));
    }

    public void okSelected() {
        fSelectedItem = ((java.lang.String) (fList.getSelectedValue()));
        dispose();
    }

    public boolean isEmpty() {
        return fList.getModel().getSize() == 0;
    }

    public void keySelectTestClass(char ch) {
        javax.swing.ListModel model = fList.getModel();
        if (!java.lang.Character.isJavaIdentifierStart(ch))
            return;

        for (int i = 0; i < model.getSize(); i++) {
            java.lang.String s = ((java.lang.String) (model.getElementAt(i)));
            if (junit.swingui.TestSelector.TestCellRenderer.matchesKey(s, java.lang.Character.toUpperCase(ch))) {
                fList.setSelectedIndex(i);
                fList.ensureIndexIsVisible(i);
                return;
            }
        }
        java.awt.Toolkit.getDefaultToolkit().beep();
    }

    public java.lang.String getSelectedItem() {
        return fSelectedItem;
    }

    private java.util.Vector createTestList(junit.runner.TestCollector collector) {
        java.util.Enumeration each = collector.collectTests();
        java.util.Vector v = new java.util.Vector(200);
        java.util.Vector displayVector = new java.util.Vector(v.size());
        while (each.hasMoreElements()) {
            java.lang.String s = ((java.lang.String) (each.nextElement()));
            v.addElement(s);
            displayVector.addElement(junit.swingui.TestSelector.TestCellRenderer.displayString(s));
        } 
        if (v.size() > 0)
            junit.runner.Sorter.sortStrings(displayVector, 0, displayVector.size() - 1, new junit.swingui.TestSelector.ParallelSwapper(v));

        return v;
    }

    private class ParallelSwapper implements junit.runner.Sorter.Swapper {
        java.util.Vector fOther;

        ParallelSwapper(java.util.Vector other) {
            fOther = other;
        }

        public void swap(java.util.Vector values, int left, int right) {
            java.lang.Object tmp = values.elementAt(left);
            values.setElementAt(values.elementAt(right), left);
            values.setElementAt(tmp, right);
            java.lang.Object tmp2 = fOther.elementAt(left);
            fOther.setElementAt(fOther.elementAt(right), left);
            fOther.setElementAt(tmp2, right);
        }
    }
}