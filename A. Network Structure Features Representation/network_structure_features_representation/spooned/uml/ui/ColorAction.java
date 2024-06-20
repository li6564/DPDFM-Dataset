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
 * @class ColorAction
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class ColorAction extends javax.swing.AbstractAction {
    private javax.swing.JComponent comp;

    private uml.ui.ColorAction.ColorPanel panel;

    public ColorAction(javax.swing.JComponent comp, java.lang.String[] properties) {
        super("Colors ...");
        this.comp = comp;
        this.panel = new uml.ui.ColorAction.ColorPanel(properties);
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        javax.swing.JOptionPane.showMessageDialog(comp, panel, "Change colors", javax.swing.JOptionPane.PLAIN_MESSAGE);
        // Force repaint to update the color changes.
        comp.paintImmediately(0, 0, comp.getWidth(), comp.getHeight());
    }

    /**
     *
     * @class ColorPanel

    Panel for displaying a set of Color that are mapped to UIManager settings.
     */
    public class ColorPanel extends javax.swing.JPanel implements java.beans.PropertyChangeListener {
        protected java.util.HashMap tiles = new java.util.HashMap();

        /**
         * Create a panel for editing the given set of UIManager color
         * properties.
         *
         * @param String[]
         */
        protected ColorPanel(java.lang.String[] colorProperties) {
            javax.swing.UIManager.getDefaults().addPropertyChangeListener(this);
            java.awt.GridBagLayout gridbag = new java.awt.GridBagLayout();
            java.awt.GridBagConstraints gc = new java.awt.GridBagConstraints();
            setLayout(gridbag);
            java.awt.Font font = getFont().deriveFont(java.awt.Font.PLAIN);
            gc.insets = new java.awt.Insets(1, 1, 1, 1);
            for (int i = 0; i < colorProperties.length; i++) {
                java.lang.String color = colorProperties[i];
                javax.swing.JLabel lbl = new javax.swing.JLabel(color);
                lbl.setHorizontalTextPosition(javax.swing.JLabel.LEFT);
                lbl.setFont(font);
                gc.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gc.weightx = 1;
                gc.gridwidth = 1;
                gridbag.setConstraints(lbl, gc);
                add(lbl);
                uml.ui.ColorTile c = new uml.ui.ColorTile(color);
                gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
                gridbag.setConstraints(c, gc);
                add(c);
                tiles.put(color, c);
            }
        }

        /**
         * Listen for color selections
         */
        public void propertyChange(java.beans.PropertyChangeEvent e) {
            uml.ui.ColorTile c = ((uml.ui.ColorTile) (tiles.get(e.getPropertyName())));
            if (c != null)
                c.setBackground(((java.awt.Color) (e.getNewValue())));

        }
    }
}