/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml.line;
/**
 * Class responsible for computing the size of a label
 *
 * @author Chris Seguin
 */
public class LabelSizeComputation {
    private java.awt.Graphics g;

    private static org.acm.seguin.uml.line.LabelSizeComputation singleton;

    /**
     * Constructor for the LabelSizeComputation object
     */
    private LabelSizeComputation() {
        java.awt.image.BufferedImage doubleBuffer = new java.awt.image.BufferedImage(300, 25, java.awt.image.BufferedImage.TYPE_INT_RGB);
        g = doubleBuffer.getGraphics();
    }

    /**
     * Computes the size of a piece of text given a font
     *
     * @param text
     * 		the text
     * @param font
     * 		the font
     * @return information about the size of the text
     */
    public org.acm.seguin.uml.line.TextInfo compute(java.lang.String text, java.awt.Font font) {
        org.acm.seguin.uml.line.TextInfo result = new org.acm.seguin.uml.line.TextInfo();
        // Determine the appropriate size
        g.setFont(font);
        java.awt.FontMetrics fm = g.getFontMetrics();
        result.height = java.lang.Math.max(1, fm.getHeight());
        if (text != null) {
            result.width = java.lang.Math.max(1, fm.stringWidth(text));
        } else {
            result.width = 1;
        }
        result.ascent = fm.getAscent();
        return result;
    }

    /**
     * Computes the size of a piece of text given a font
     *
     * @param text
     * 		the text
     * @param font
     * 		the font
     * @return information about the size of the text
     */
    public int computeHeight(java.lang.String text, java.awt.Font font) {
        org.acm.seguin.uml.line.TextInfo ti = compute(text, font);
        return ti.height;
    }

    /**
     * Factory method for this object
     *
     * @return Creates a single instance of this object
     */
    public static org.acm.seguin.uml.line.LabelSizeComputation get() {
        if (org.acm.seguin.uml.line.LabelSizeComputation.singleton == null) {
            org.acm.seguin.uml.line.LabelSizeComputation.init();
        }
        return org.acm.seguin.uml.line.LabelSizeComputation.singleton;
    }

    /**
     * Initializer for the singleton
     */
    private static synchronized void init() {
        if (org.acm.seguin.uml.line.LabelSizeComputation.singleton == null) {
            org.acm.seguin.uml.line.LabelSizeComputation.singleton = new org.acm.seguin.uml.line.LabelSizeComputation();
        }
    }
}