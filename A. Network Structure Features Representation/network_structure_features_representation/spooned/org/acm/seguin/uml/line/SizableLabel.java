/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml.line;
/**
 * Creates a label that can be resized by setting it's scaling factor.
 *
 * @author Chris Seguin
 */
public class SizableLabel extends org.acm.seguin.uml.line.ScalablePanel {
    private boolean bufferCreated = false;

    private java.awt.Image scaledImage;

    private java.awt.Color foreground;

    private java.awt.Color background;

    private java.awt.Font font;

    private int height = 200;

    private int width = 25;

    private java.lang.String text;

    private int align;

    private int ascent;

    private static java.awt.Font defaultFont = null;

    private static java.awt.Color defaultColor = null;

    /**
     * Constructor for the SizableLabel object
     */
    public SizableLabel() {
        this(" ");
    }

    /**
     * Constructor for the SizableLabel object
     *
     * @param init
     * 		Description of Parameter
     */
    public SizableLabel(java.lang.String init) {
        if (org.acm.seguin.uml.line.SizableLabel.defaultFont == null) {
            org.acm.seguin.uml.line.SizableLabel.init();
        }
        font = org.acm.seguin.uml.line.SizableLabel.defaultFont;
        foreground = org.acm.seguin.uml.line.SizableLabel.defaultColor;
        setText(init);
        setSize(getPreferredSize());
        setDoubleBuffered(false);
    }

    /**
     * Sets the Foreground attribute of the SizableLabel object
     *
     * @param value
     * 		The new Foreground value
     */
    public void setSLForeground(java.awt.Color value) {
        foreground = value;
        bufferCreated = false;
    }

    /**
     * Sets the Font attribute of the SizableLabel object
     *
     * @param value
     * 		The new Font value
     */
    public void setSLFont(java.awt.Font value) {
        font = value;
        setSize(getPreferredSize());
    }

    /**
     * Sets the Text attribute of the SizableLabel object
     *
     * @param value
     * 		The new Text value
     */
    public void setText(java.lang.String value) {
        if ((value == null) || (value == "")) {
            text = " ";
        } else {
            text = value;
        }
        bufferCreated = false;
        setSize(getPreferredSize());
    }

    /**
     * Sets the HorizontalAlignment attribute of the SizableLabel object
     *
     * @param value
     * 		The new HorizontalAlignment value
     */
    public void setSLHorizontalAlignment(int value) {
        align = value;
    }

    /**
     * Gets the Text attribute of the SizableLabel object
     *
     * @return The Text value
     */
    public java.lang.String getText() {
        return text;
    }

    /**
     * Gets the PreferredSize attribute of the SizableLabel object
     *
     * @return The PreferredSize value
     */
    public java.awt.Dimension getPreferredSize() {
        determineSize();
        java.awt.Dimension dim = new java.awt.Dimension();
        dim.width = scaleInteger(width);
        dim.height = scaleInteger(height);
        bufferCreated = false;
        return dim;
    }

    /**
     * Paints the sizable label
     *
     * @param g
     * 		the graphics area to print in
     */
    public void paint(java.awt.Graphics g) {
        print(g, 0, 0);
    }

    /**
     * Prints the label at the proper location
     *
     * @param g
     * 		the graphics object
     * @param x
     * 		the x coordinate
     * @param y
     * 		the y coordinate
     */
    public void print(java.awt.Graphics g, int x, int y) {
        if ((!bufferCreated) || (!getParentBackground().equals(background))) {
            createBuffer();
        }
        java.awt.Dimension dim = getSize();
        // g.setColor(background);
        // g.fillRect(x, y, dim.width, dim.height);
        if (align == javax.swing.JLabel.LEFT) {
            g.drawImage(scaledImage, x, y, this);
        } else if (align == javax.swing.JLabel.RIGHT) {
            int deltax = ((int) (dim.width - scaleInteger(width)));
            g.drawImage(scaledImage, x + deltax, y, this);
        } else if (align == javax.swing.JLabel.CENTER) {
            int deltax = ((int) ((dim.width - scaleInteger(width)) * 0.5));
            g.drawImage(scaledImage, x + deltax, y, this);
        }
    }

    /**
     * Scales the image
     *
     * @param value
     * 		the amount to scale
     */
    public void scale(double value) {
        if (java.lang.Math.abs(getScale() - value) > 0.001) {
            super.scale(value);
            bufferCreated = false;
        }
    }

    /**
     * Gets the ParentBackground attribute of the SizableLabel object
     *
     * @return The ParentBackground value
     */
    private java.awt.Color getParentBackground() {
        java.awt.Component parent = getParent();
        if (parent == null) {
            return java.awt.Color.gray;
        } else {
            return parent.getBackground();
        }
    }

    /**
     * Creates the buffer from which to draw the resized text
     */
    private void createBuffer() {
        bufferCreated = true;
        determineSize();
        java.awt.Dimension dim = new java.awt.Dimension(width, height);
        int tempWidth = java.lang.Math.max(1, scaleInteger(width + 10));
        int tempHeight = java.lang.Math.max(1, scaleInteger(height + 10));
        scaledImage = new java.awt.image.BufferedImage(tempWidth, tempHeight, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = ((java.awt.Graphics2D) (scaledImage.getGraphics()));
        g.scale(getScale(), getScale());
        // Fill in the background
        background = getParentBackground();
        g.setColor(background);
        g.fillRect(0, 0, dim.width + 10, dim.height + 10);
        // Draw the text
        g.setColor(foreground);
        g.setFont(font);
        g.drawString(text, 0, ascent);
    }

    /**
     * Description of the Method
     */
    private void determineSize() {
        org.acm.seguin.uml.line.TextInfo ti = org.acm.seguin.uml.line.LabelSizeComputation.get().compute(text, font);
        height = ti.height;
        width = ti.width;
        ascent = ti.ascent;
    }

    /**
     * Prints some debug information about this label
     */
    private void debug() {
        java.lang.System.out.println("Label:  " + text);
        java.lang.System.out.println("    Scale:  " + getScale());
        java.lang.System.out.println("    Height:  " + height);
        java.lang.System.out.println("    Width:   " + width);
        java.lang.System.out.println("    Align:   " + align);
        java.lang.System.out.println("    Color:   " + foreground);
        java.lang.System.out.println("    Shape:   " + getBounds());
    }

    /**
     * Initializes the static font and color
     */
    private static synchronized void init() {
        if (org.acm.seguin.uml.line.SizableLabel.defaultFont == null) {
            org.acm.seguin.uml.line.SizableLabel.defaultFont = new java.awt.Font("Serif", java.awt.Font.PLAIN, 12);
            org.acm.seguin.uml.line.SizableLabel.defaultColor = new java.awt.Color(0, 80, 180);
        }
    }
}