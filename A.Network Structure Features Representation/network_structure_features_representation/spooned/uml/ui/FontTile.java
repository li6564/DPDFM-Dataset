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
/**
 *
 * @class FontTile
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class FontTile extends javax.swing.JButton {
    private java.lang.String title;

    private java.lang.String fontProperty;

    /**
     * Create a new ColorTilefor the given UIManager property.
     *
     * @param String
     * 		property
     */
    public FontTile(java.lang.String fontProperty) {
        this(fontProperty, "Choose a Color");
    }

    /**
     * Create a new ColorTilefor teh given UIManager property and the given title
     * on the font chooser.
     *
     * @param String
     * 		property
     * @param String
     * 		chooser title
     */
    public FontTile(java.lang.String fontProperty, java.lang.String title) {
        this.fontProperty = fontProperty;
        this.title = title;
        java.awt.Font font = javax.swing.UIManager.getFont(fontProperty);
        if (font == null)
            font = javax.swing.UIManager.getFont("Label.font");

        if (font != null) {
            setFont(font);
            setText(font.getName());
        }
        setHorizontalAlignment(javax.swing.JLabel.CENTER);
    }

    public java.awt.Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public java.awt.Dimension getPreferredSize() {
        java.awt.Dimension dim = super.getPreferredSize();
        dim.height = 35;
        return dim;
    }

    /**
     * Listen for press event
     */
    protected void fireActionPerformed(java.awt.event.ActionEvent event) {
        java.awt.Font font = uml.ui.JFontChooser.showDialog(this, title, javax.swing.UIManager.getFont(fontProperty));
        if (font != null) {
            javax.swing.UIManager.put(fontProperty, font);
            setText(font.getName());
        }
    }
}