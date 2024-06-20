package org.acm.seguin.uml;
/**
 * Draws the class symbol
 *
 * @author Chris Seguin
 */
public class ClassIcon extends org.acm.seguin.uml.UMLIcon {
    /**
     * Constructor for the ClassIcon object
     *
     * @param wide
     * 		the size of the icon
     * @param high
     * 		the size of the icon
     */
    public ClassIcon(int wide, int high) {
        super(wide, high);
    }

    /**
     * Draws the icon
     *
     * @param c
     * 		The component on which we are drawing
     * @param g
     * 		The graphics object
     * @param x
     * 		the x location
     * @param y
     * 		the y location
     */
    public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        // Set the color to black
        g.setColor(java.awt.Color.black);
        // Draw the icons
        int scaledHeight = ((int) (scale * iconHeight));
        int scaledWidth = ((int) (scale * iconWidth));
        int scaledMargin = ((int) (scale));
        int third = scaledHeight / 3;
        g.drawRect(x + scaledMargin, y, scaledWidth, scaledHeight);
        g.drawLine(x + scaledMargin, y + third, (x + scaledMargin) + scaledWidth, y + third);
        g.drawLine(x + scaledMargin, y + (2 * third), (x + scaledMargin) + scaledWidth, y + (2 * third));
    }
}