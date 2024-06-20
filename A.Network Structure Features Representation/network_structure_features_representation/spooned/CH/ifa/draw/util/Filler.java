/* @(#)Filler.java 5.1 */
package CH.ifa.draw.util;
/**
 * A component that can be used to reserve white space in a layout.
 */
public class Filler extends java.awt.Canvas {
    private int fWidth;

    private int fHeight;

    private java.awt.Color fBackground;

    public Filler(int width, int height) {
        this(width, height, null);
    }

    public Filler(int width, int height, java.awt.Color background) {
        fWidth = width;
        fHeight = height;
        fBackground = background;
    }

    public java.awt.Dimension getMinimumSize() {
        return new java.awt.Dimension(fWidth, fHeight);
    }

    public java.awt.Dimension getPreferredSize() {
        return getMinimumSize();
    }

    public java.awt.Color getBackground() {
        if (fBackground != null)
            return fBackground;

        return super.getBackground();
    }
}