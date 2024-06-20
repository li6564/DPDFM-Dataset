/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.print.xml;
/**
 * Prints a single line
 *
 * @author Chris Seguin
 */
public class XMLLinePrinter extends org.acm.seguin.print.text.LinePrinter {
    private java.util.ArrayList list;

    private int fontSize;

    private java.awt.Font lineNo;

    /**
     * Constructor for the JavaLinePrinter object
     */
    public XMLLinePrinter() {
        list = new java.util.ArrayList();
        fontSize = -1;
    }

    /**
     * Sets the FontSize attribute of the LinePrinter object
     *
     * @param value
     * 		The new FontSize value
     */
    public void setFontSize(int value) {
        if (value != fontSize) {
            fontSize = value;
            lineNo = new java.awt.Font("Monospaced", java.awt.Font.PLAIN, fontSize);
        }
    }

    /**
     * Gets the LineHeight attribute of the LinePrinter object
     *
     * @param g
     * 		Description of Parameter
     * @return The LineHeight value
     */
    public int getLineHeight(java.awt.Graphics g) {
        init(g);
        g.setFont(lineNo);
        java.awt.FontMetrics fm = g.getFontMetrics();
        return fm.getHeight();
    }

    /**
     * Prints the line
     *
     * @param g
     * 		The graphics device
     * @param line
     * 		The string to print
     * @param x
     * 		The x location on the graphics device
     * @param y
     * 		The y location on the graphics device
     * @param set
     * 		The set of lines
     * @param index
     * 		The line we are printing
     */
    public void print(java.awt.Graphics g, java.lang.String line, int x, int y, org.acm.seguin.print.text.LineSet set, int index) {
        org.acm.seguin.print.xml.State state;
        if (index == 0) {
            list.add(0, org.acm.seguin.print.xml.TextState.getState());
        }
        state = ((org.acm.seguin.print.xml.State) (list.get(index)));
        if (line.length() == 0) {
            list.add(index + 1, state);
            return;
        }
        if (state instanceof org.acm.seguin.print.xml.TextState) {
            if (line.charAt(0) == '<')
                state = org.acm.seguin.print.xml.TagState.getState();

        }
        java.lang.String output = org.acm.seguin.util.TextFormatter.rightJustifyNumber(index + 1, 5) + ":  ";
        g.setFont(lineNo);
        java.awt.FontMetrics fm = g.getFontMetrics();
        g.drawString(output, x, y);
        state.setGraphics(g);
        state.setX(x + fm.stringWidth(output));
        state.setY(y);
        state.setFontSize(fontSize);
        list.add(index + 1, state.processLine(line));
    }
}