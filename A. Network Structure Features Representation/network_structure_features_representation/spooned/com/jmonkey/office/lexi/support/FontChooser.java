package com.jmonkey.office.lexi.support;
// jMonkey imports
// import com.jmonkey.office.Lexi;
public final class FontChooser extends javax.swing.JDialog implements java.awt.event.ActionListener , javax.swing.event.ListSelectionListener , java.awt.event.ItemListener {
    javax.swing.JList fontList = null;

    javax.swing.JTextArea prevArea = null;

    javax.swing.JComboBox sizeBox = null;

    javax.swing.JCheckBox boldBox = null;

    javax.swing.JCheckBox italicBox = null;

    javax.swing.JCheckBox bItalic = null;

    javax.swing.JCheckBox plainBox = null;

    // Lexi _OWNER = null;
    // Add this to store the font
    // while the window is destroyed.
    private java.awt.Font _PICKED_FONT = null;

    // this.addActionListener(this);.
    public FontChooser(javax.swing.JFrame owner) {
        this(owner, "Pick your Font...", true);
    }

    /**
     * Display the FontChooser and return the slected font.
     *
     * @param owner
     * 		javax.swing.JFrame the owner frame.
     * @return java.awt.Font The selected font, or null.
     */
    public FontChooser(javax.swing.JFrame owner, java.lang.String title, boolean modal) {
        super(owner, title, modal);
        this.setSize(700, 500);
        this.init(owner);
    }

    /**
     * Handle the actions associated with the 'ok' and the 'cancel' buttons
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("can-button")) {
            this.dispose();
        }
        if (e.getActionCommand().equals("ok-button")) {
            this.doExit();
        }
    }

    public static final java.awt.Font display(javax.swing.JFrame owner) {
        java.awt.Font myfont = null;
        com.jmonkey.office.lexi.support.FontChooser fc = new com.jmonkey.office.lexi.support.FontChooser(owner, "Font Chooser", true);
        fc.setVisible(true);
        myfont = fc.getSelectedFont();
        return myfont;
    }

    public void doExit() {
        if (fontList.getSelectedValue() == null) {
            if (boldBox.isSelected() == true) {
                // _OWNER.setBold();
                _PICKED_FONT = new java.awt.Font("TimesRoman", java.awt.Font.BOLD, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
            }
            if (italicBox.isSelected() == true) {
                // _OWNER.setItalic();
                _PICKED_FONT = new java.awt.Font("TimesRoman", java.awt.Font.ITALIC, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
            }
            if (plainBox.isSelected() == true) {
                // _OWNER.setRegular();
                _PICKED_FONT = new java.awt.Font("TimesRoman", java.awt.Font.PLAIN, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
            }
            // _OWNER.setNewFont("TimesRoman", Integer.parseInt(sizeBox.getSelectedItem().toString()));
            // _PICKED_FONT = new Font(fontList.getSelectedValue().toString(), Font.BOLD, Integer.parseInt(sizeBox.getSelectedItem().toString()));
        } else {
            if (boldBox.isSelected() == true) {
                // _OWNER.setBold();
                _PICKED_FONT = new java.awt.Font(fontList.getSelectedValue().toString(), java.awt.Font.BOLD, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
            }
            if (italicBox.isSelected() == true) {
                // _OWNER.setItalic();
                _PICKED_FONT = new java.awt.Font(fontList.getSelectedValue().toString(), java.awt.Font.ITALIC, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
            }
            if (plainBox.isSelected() == true) {
                // _OWNER.setRegular();
                _PICKED_FONT = new java.awt.Font(fontList.getSelectedValue().toString(), java.awt.Font.PLAIN, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
            }
            // _OWNER.setNewFont(fontList.getSelectedValue().toString(), Integer.parseInt(sizeBox.getSelectedItem().toString()));
            // _PICKED_FONT = new Font(fontList.getSelectedValue().toString(), Font.BOLD, Integer.parseInt(sizeBox.getSelectedItem().toString()));
        }
        this.dispose();
    }

    public final java.awt.Font getSelectedFont() {
        return _PICKED_FONT;
    }

    /**
     * Sets us up with the panels needed to create this font chooser
     */
    private void init(java.awt.Component c) {
        javax.swing.JPanel main = new javax.swing.JPanel();
        javax.swing.JPanel buttonPanes = new javax.swing.JPanel();
        javax.swing.JPanel listPanes = new javax.swing.JPanel();
        javax.swing.JPanel fontPanes = new javax.swing.JPanel();
        javax.swing.JPanel optionPanes = new javax.swing.JPanel();
        javax.swing.JPanel previewPanes = new javax.swing.JPanel();
        main.setLayout(new java.awt.BorderLayout());
        buttonPanes.setLayout(new java.awt.FlowLayout());
        fontPanes.setLayout(new java.awt.BorderLayout());
        optionPanes.setLayout(new java.awt.GridLayout(3, 2));
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                doExit();
            }
        });
        java.lang.String[] families = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (int i = 0; i < families.length; i++) {
            if (families[i].indexOf(".") == (-1)) {
                // we have to test to a "." so
                // we don't get duplicates, there
                // appears to be a bug in the VM
            }
        }
        // FontPane Stuff
        fontList = new javax.swing.JList(families);
        fontList.addListSelectionListener(this);
        javax.swing.JScrollPane scroller = new javax.swing.JScrollPane(fontList);
        listPanes.add(scroller);
        fontPanes.add(listPanes, java.awt.BorderLayout.WEST);
        fontPanes.add(optionPanes, java.awt.BorderLayout.EAST);
        // Options Pane
        java.lang.String[] sizes = new java.lang.String[]{ "8", "10", "12", "14", "16", "18", "20", "24", "26", "28", "30", "36", "40", "46", "52", "58", "64", "70", "76" };
        sizeBox = new javax.swing.JComboBox();
        for (int k = 0; k < sizes.length; k++) {
            sizeBox.addItem(sizes[k]);
        }
        sizeBox.setEditable(true);
        sizeBox.setSelectedItem("12");
        sizeBox.setSize(15, 15);
        sizeBox.addItemListener(this);
        optionPanes.add(sizeBox);
        optionPanes.add(new javax.swing.JSeparator());
        boldBox = new javax.swing.JCheckBox("Bold", false);
        boldBox.addItemListener(this);
        boldBox.setEnabled(false);
        italicBox = new javax.swing.JCheckBox("Italic", false);
        italicBox.addItemListener(this);
        italicBox.setEnabled(false);
        plainBox = new javax.swing.JCheckBox("Regular", true);
        plainBox.addItemListener(this);
        plainBox.setEnabled(true);
        // bItalic = new JCheckBox("Bold Italic", false);
        optionPanes.add(plainBox);
        optionPanes.add(italicBox);
        optionPanes.add(boldBox);
        // optionPanes.add(bItalic);
        // Button Pane
        javax.swing.JButton okB = new javax.swing.JButton("OK");
        okB.setActionCommand("ok-button");
        okB.addActionListener(this);
        javax.swing.JButton cancelB = new javax.swing.JButton("Cancel");
        cancelB.setActionCommand("can-button");
        cancelB.addActionListener(this);
        buttonPanes.add(okB);
        buttonPanes.add(cancelB);
        // Preview Pane
        prevArea = new javax.swing.JTextArea("The Quick Brown Fox...");
        prevArea.setSize(200, 200);
        prevArea.setEditable(false);
        previewPanes.add(prevArea);
        // Add Stuff to Main
        main.add(fontPanes, java.awt.BorderLayout.NORTH);
        main.add(previewPanes, java.awt.BorderLayout.CENTER);
        main.add(buttonPanes, java.awt.BorderLayout.SOUTH);
        this.getContentPane().add(main);
        this.pack();
        // set the position of the dialog.
        this.setLocationRelativeTo(c);
    }

    /**
     * Handles the list of fonts and the changes
     */
    public void itemStateChanged(java.awt.event.ItemEvent iEv) {
        if ((iEv.getItem() == plainBox) && (iEv.getStateChange() == java.awt.event.ItemEvent.SELECTED)) {
            boldBox.setEnabled(false);
            italicBox.setEnabled(false);
        }
        if ((iEv.getItem() == plainBox) && (iEv.getStateChange() == java.awt.event.ItemEvent.DESELECTED)) {
            boldBox.setEnabled(true);
            italicBox.setEnabled(true);
        }
        if (iEv.getItem() == sizeBox) {
            java.awt.Font newFont;
            if (fontList.getSelectedValue() == null) {
                newFont = new java.awt.Font("TimesRoman", java.awt.Font.PLAIN, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
            } else {
                newFont = new java.awt.Font(fontList.getSelectedValue().toString(), java.awt.Font.PLAIN, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
            }
            prevArea.setFont(newFont);
            prevArea.repaint();
        }
    }

    /**
     * Handles the changes in the font size ComboBox
     */
    public void valueChanged(javax.swing.event.ListSelectionEvent listEv) {
        java.awt.Font newFont;
        if (fontList.getSelectedValue() == null) {
            newFont = new java.awt.Font("TimesRoman", java.awt.Font.PLAIN, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
        } else {
            newFont = new java.awt.Font(fontList.getSelectedValue().toString(), java.awt.Font.BOLD, java.lang.Integer.parseInt(sizeBox.getSelectedItem().toString()));
        }
        prevArea.setFont(newFont);
        prevArea.repaint();
    }
}