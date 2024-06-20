/**
 * QuickUML; A simple UML tool that demonstrates one use of the
 * Java Diagram Package
 *
 * Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uml.ui;
import java.awt.Font;
/**
 *
 * @class JFontChooser
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This component implements a font chooser. The layout is based on Jext's FontChooser.
TODO: Expose listmodels for family, style & size
 */
public class JFontChooser extends javax.swing.JComponent {
    protected static final java.lang.String[] STYLES = new java.lang.String[]{ "plain", "bold", "italic", "boldItalic" };

    protected static final java.lang.String[] SIZES = new java.lang.String[]{ "9", "10", "12", "14", "16", "18", "24" };

    protected static final java.lang.String[] FONTS = uml.ui.JFontChooser.getAvailableFontFamilyNames();

    private javax.swing.JList familyList;

    private javax.swing.JList sizesList;

    private javax.swing.JList stylesList;

    private javax.swing.JTextField familyField;

    private javax.swing.JTextField sizesField;

    private javax.swing.JTextField stylesField;

    private uml.ui.JFontChooser.SampleLabel previewLabel;

    /**
     * Create a new FontChooser on the given component with the given Font
     *
     * @param Font
     */
    public JFontChooser(java.awt.Font font) {
        this(font, null);
    }

    /**
     * Create a new FontChooser on the given component with the given Font
     *
     * @param Font
     */
    public JFontChooser(java.awt.Font font, java.lang.String sample) {
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        java.awt.GridBagConstraints gc = new java.awt.GridBagConstraints();
        setLayout(layout);
        gc.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gc.gridwidth = gc.gridheight = 1;
        gc.insets = new java.awt.Insets(4, 4, 4, 4);
        gc.weightx = 1.0;
        gc.weighty = 0.0;
        // Add the labels
        javax.swing.JLabel lbl = new javax.swing.JLabel("Family", javax.swing.JLabel.CENTER);
        gc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        layout.setConstraints(lbl, gc);
        add(lbl);
        lbl = new javax.swing.JLabel("Size", javax.swing.JLabel.CENTER);
        layout.setConstraints(lbl, gc);
        add(lbl);
        lbl = new javax.swing.JLabel("Style", javax.swing.JLabel.CENTER);
        gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        layout.setConstraints(lbl, gc);
        add(lbl);
        // Add text fields
        familyField = new javax.swing.JTextField(10);
        familyField.setEnabled(false);
        gc.gridwidth = 1;
        gc.weighty = 1.0;
        layout.setConstraints(familyField, gc);
        add(familyField);
        sizesField = new javax.swing.JTextField(10);
        sizesField.setEnabled(false);
        layout.setConstraints(sizesField, gc);
        add(sizesField);
        stylesField = new javax.swing.JTextField(10);
        stylesField.setEnabled(false);
        gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        layout.setConstraints(stylesField, gc);
        add(stylesField);
        // Add lists
        familyList = new javax.swing.JList(uml.ui.JFontChooser.FONTS);
        javax.swing.JScrollPane scrolPane = new javax.swing.JScrollPane(familyList);
        gc.ipadx = gc.ipady = 10;
        gc.gridwidth = 1;
        gc.gridheight = 4;
        gc.fill = java.awt.GridBagConstraints.BOTH;
        layout.setConstraints(scrolPane, gc);
        add(scrolPane);
        sizesList = new javax.swing.JList(uml.ui.JFontChooser.SIZES);
        scrolPane = new javax.swing.JScrollPane(sizesList);
        layout.setConstraints(scrolPane, gc);
        add(scrolPane);
        stylesList = new javax.swing.JList(uml.ui.JFontChooser.STYLES);
        scrolPane = new javax.swing.JScrollPane(stylesList);
        gc.fill = java.awt.GridBagConstraints.BOTH;
        gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        layout.setConstraints(scrolPane, gc);
        add(scrolPane);
        // Add preview panel
        previewLabel = new uml.ui.JFontChooser.SampleLabel(sample);
        this.addPropertyChangeListener(previewLabel);
        gc.weighty = 0.0;
        gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gc.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gc.fill = java.awt.GridBagConstraints.BOTH;
        layout.setConstraints(previewLabel, gc);
        add(previewLabel);
        // Add listeners to the list
        uml.ui.JFontChooser.SelectionHandler handler = new uml.ui.JFontChooser.SelectionHandler();
        familyList.addListSelectionListener(handler);
        sizesList.addListSelectionListener(handler);
        stylesList.addListSelectionListener(handler);
        if (font != null)
            setFont(font);

    }

    /**
     * Select the current font
     */
    public void setFont(java.awt.Font font) {
        // Go by the default font on the label if a null font is selected
        if (font == null)
            font = previewLabel.getFont();

        super.setFont(font);
        // firePropertyChange
        if (font != null) {
            familyList.setSelectedValue(font.getName(), true);
            familyField.setText(java.lang.String.valueOf(font.getName()));
            stylesList.setSelectedIndex(font.getStyle());
            stylesField.setText(((java.lang.String) (stylesList.getSelectedValue())));
            sizesField.setText(java.lang.String.valueOf(font.getSize()));
            sizesList.setSelectedValue(java.lang.String.valueOf(font.getSize()), true);
        }
    }

    /**
     * Gets a list of all available font family names.
     */
    public static java.lang.String[] getAvailableFontFamilyNames() {
        java.util.Vector v = new java.util.Vector();
        java.lang.String names[] = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (int i = 0; i < names.length; i++) {
            if ((!names[i].equals(".bold")) && (!names[i].equals(".italic")))
                v.addElement(names[i]);

        }
        return ((java.lang.String[]) (v.toArray(new java.lang.String[v.size()])));
    }

    /**
     * Get the last selected font
     */
    public java.awt.Font getSelectedFont() {
        return super.getFont();
    }

    /**
     * Get the last selected font
     */
    public java.lang.String getSelectedFamily() {
        return getFont().getFamily();
    }

    /**
     * Get the last selected font
     */
    public float getSelectedSize() {
        return getFont().getSize();
    }

    /**
     * Get the last selected font
     */
    public int getSelectedStyle() {
        return getFont().getStyle();
    }

    /**
     * Notify of the property change
     */
    protected void firePropertyChange(java.lang.String propertyName, java.lang.Object oldValue, java.lang.Object newValue) {
        super.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     *
     * @class SelectionHandler

    Derive a new Font as a selection is made and fire the property change
     */
    protected class SelectionHandler implements javax.swing.event.ListSelectionListener {
        /**
         * Listen for selections
         */
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
            java.lang.Object source = e.getSource();
            java.awt.Font font = JFontChooser.this.getFont();
            java.awt.Font oldFont = font;
            if (source == familyList) {
                font = new java.awt.Font(((java.lang.String) (familyList.getSelectedValue())), font.getStyle(), font.getSize());
                firePropertyChange("font.family", oldFont, font);
            } else if (source == sizesList) {
                // Calculate the new font size
                int fontSize;
                try {
                    fontSize = java.lang.Integer.parseInt(((java.lang.String) (sizesList.getSelectedValue())));
                } catch (java.lang.Exception ex) {
                    fontSize = 12;
                }
                font = font.deriveFont(((float) (fontSize)));
                firePropertyChange("font.size", oldFont, font);
            } else if (source == stylesList) {
                font = font.deriveFont(stylesList.getSelectedIndex());
                firePropertyChange("font.style", oldFont, font);
            }
            // Update selected font
            if (!oldFont.equals(font))
                uml.ui.JFontChooser.super.setFont(font);

        }
    }

    /**
     *
     * @class SampleLabel
     */
    protected class SampleLabel extends javax.swing.JLabel implements java.beans.PropertyChangeListener {
        public SampleLabel() {
            this(null);
        }

        public SampleLabel(java.lang.String text) {
            super(text == null ? "Sample Text" : text);
            setHorizontalAlignment(javax.swing.JLabel.CENTER);
        }

        public void propertyChange(java.beans.PropertyChangeEvent e) {
            java.lang.String name = e.getPropertyName();
            if ((name.equals("font.style") || name.equals("font.size")) || name.equals("font.family"))
                this.setFont(((java.awt.Font) (e.getNewValue())));

        }

        public java.awt.Dimension getMinimumSize() {
            return getPreferredSize();
        }

        public java.awt.Dimension getPreferredSize() {
            java.awt.Dimension dim = super.getPreferredSize();
            dim.height = 35;
            return dim;
        }
    }

    /**
     * Create a modal dialog for choosing a font
     */
    public static java.awt.Font showDialog(java.awt.Component component) {
        return uml.ui.JFontChooser.showDialog(component, null);
    }

    public static java.awt.Font showDialog(java.awt.Component component, java.awt.Font font) {
        return uml.ui.JFontChooser.showDialog(component, "Select font", font);
    }

    /**
     * Create a modal dialog for choosing a font
     */
    public static java.awt.Font showDialog(java.awt.Component component, java.lang.String title, java.awt.Font font) {
        if (font == null)
            font = component.getFont();

        uml.ui.JFontChooser.FontDialog dlg = new uml.ui.JFontChooser.FontDialog(component, title, font);
        return dlg.getSelectedFont();
    }

    /**
     *
     * @class FontDialog
     */
    protected static class FontDialog extends javax.swing.JDialog {
        private static uml.ui.JFontChooser chooser = new uml.ui.JFontChooser(null);

        /**
         * Create a new FontDialog
         */
        public FontDialog(java.awt.Component component, java.lang.String title, java.awt.Font font) {
            super(javax.swing.JOptionPane.getFrameForComponent(component), title, true);
            java.awt.Container content = this.getContentPane();
            content.setLayout(new java.awt.BorderLayout());
            // Button panel
            javax.swing.JPanel buttonsPanel = new javax.swing.JPanel();
            buttonsPanel.add(new javax.swing.JButton(new uml.ui.JFontChooser.FontDialog.AcceptAction()));
            buttonsPanel.add(new javax.swing.JButton(new uml.ui.JFontChooser.FontDialog.CancelAction()));
            content.add(buttonsPanel, java.awt.BorderLayout.SOUTH);
            uml.ui.JFontChooser.FontDialog.chooser.setFont(font);
            content.add(uml.ui.JFontChooser.FontDialog.chooser);
            pack();
            this.setResizable(true);
            this.setVisible(true);
        }

        public java.awt.Font getSelectedFont() {
            return uml.ui.JFontChooser.FontDialog.chooser.getSelectedFont();
        }

        /**
         *
         * @class AcceptAction
         */
        protected class AcceptAction extends javax.swing.AbstractAction {
            public AcceptAction() {
                super("OK");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                uml.ui.JFontChooser.FontDialog.this.setVisible(false);
            }
        }

        /**
         *
         * @class CancelAction
         */
        protected class CancelAction extends javax.swing.AbstractAction {
            public CancelAction() {
                super("Cancel");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                uml.ui.JFontChooser.FontDialog.this.setVisible(false);
            }
        }
    }
}