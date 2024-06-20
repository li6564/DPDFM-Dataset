package org.acm.seguin.awt;
/**
 * Little panel that holds an image
 *
 * @author Chris Seguin
 */
public class ImagePanel extends javax.swing.JPanel {
    private javax.swing.ImageIcon imgIcon;

    private int wide;

    private int high;

    /**
     * Constructor for the ImagePanel object
     *
     * @param init
     * 		Description of Parameter
     */
    public ImagePanel(java.lang.String init) {
        java.lang.ClassLoader cl = getClass().getClassLoader();
        java.net.URL url = cl.getResource(init);
        imgIcon = new javax.swing.ImageIcon(url);
        wide = imgIcon.getIconWidth();
        high = imgIcon.getIconHeight();
        java.awt.Dimension dim = new java.awt.Dimension(wide, high);
        setPreferredSize(dim);
        setSize(dim);
    }

    /**
     * Draw the image on the panel
     *
     * @param g
     * 		the graphics context
     */
    public void paint(java.awt.Graphics g) {
        imgIcon.paintIcon(this, g, 0, 0);
    }
}