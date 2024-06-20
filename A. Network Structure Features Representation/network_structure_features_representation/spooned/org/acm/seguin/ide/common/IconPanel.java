package org.acm.seguin.ide.common;
/**
 * A little tool that allows an Icon to exist on a JPanel or other component
 *
 * @author Chris Seguin
 */
class IconPanel extends javax.swing.JPanel {
    private javax.swing.Icon icon;

    /**
     * Constructor for the IconPanel object
     *
     * @param init
     * 		Description of Parameter
     */
    public IconPanel(javax.swing.Icon init) {
        icon = init;
    }

    /**
     * Gets the PreferredSize attribute of the IconPanel object
     *
     * @return The PreferredSize value
     */
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(icon.getIconWidth() + 2, icon.getIconHeight() + 2);
    }

    /**
     * Gets the MinimumSize attribute of the IconPanel object
     *
     * @return The MinimumSize value
     */
    public java.awt.Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * Description of the Method
     *
     * @param g
     * 		Description of Parameter
     */
    public void paint(java.awt.Graphics g) {
        java.awt.Dimension dim = getSize();
        int x = ((dim.width - icon.getIconWidth()) / 2) - 1;
        int y = (dim.height - icon.getIconHeight()) / 2;
        icon.paintIcon(this, g, x, y);
    }
}