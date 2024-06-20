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
import java.awt.Component;
/**
 *
 * @class SizePanel
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This is a simple Component for displaying and changing another Components
dimensions.
 */
public class SizePanel extends javax.swing.JPanel {
    protected java.awt.Dimension size = new java.awt.Dimension();

    protected javax.swing.JTextField txtWidth = new javax.swing.JTextField();

    protected javax.swing.JTextField txtHeight = new javax.swing.JTextField();

    /**
     * Create a panel that will edit the size of a given Component
     *
     * @param Component
     */
    public SizePanel(java.awt.Component component) {
        this(component.getWidth(), component.getHeight());
    }

    /**
     * Create a panel that will edit the sizes given
     *
     * @param int
     * @param int
     */
    public SizePanel(int width, int height) {
        size.width = width;
        size.height = height;
        java.awt.GridBagLayout gridbag = new java.awt.GridBagLayout();
        java.awt.GridBagConstraints gc = new java.awt.GridBagConstraints();
        setLayout(gridbag);
        java.awt.Component comp = new javax.swing.JLabel("Width:");
        gc.anchor = java.awt.GridBagConstraints.WEST;
        gc.weightx = 1;
        gc.gridwidth = 1;
        gridbag.setConstraints(comp, gc);
        add(comp);
        comp = txtWidth;
        txtWidth.setText(java.lang.Integer.toString(size.width));
        gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(comp, gc);
        add(comp);
        comp = new javax.swing.JLabel("Height:");
        gc.weightx = 1;
        gc.gridwidth = 1;
        gc.fill = java.awt.GridBagConstraints.NONE;
        gridbag.setConstraints(comp, gc);
        add(comp);
        comp = txtHeight;
        txtHeight.setText(java.lang.Integer.toString(size.height));
        gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(comp, gc);
        add(comp);
    }

    public int getSelectedWidth() {
        try {
            size.width = java.lang.Integer.parseInt(txtWidth.getText());
        } catch (java.lang.Throwable t) {
        }
        return size.width;
    }

    public int getSelectedHeight() {
        try {
            size.height = java.lang.Integer.parseInt(txtHeight.getText());
        } catch (java.lang.Throwable t) {
        }
        return size.height;
    }

    /**
     * Get the chosen dimension
     */
    public java.awt.Dimension getDimension(java.awt.Dimension d) {
        if (d == null)
            d = new java.awt.Dimension();

        d.width = getSelectedWidth();
        d.height = getSelectedHeight();
        return d;
    }
}