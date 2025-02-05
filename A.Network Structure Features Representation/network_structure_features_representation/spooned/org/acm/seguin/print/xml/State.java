/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.print.xml;
/**
 * State pattern that is used to print the XML file
 *
 * @author Chris Seguin
 */
public abstract class State {
    /**
     * the font
     */
    protected java.awt.Font font;

    protected java.awt.Color color;

    private java.awt.Graphics g;

    private int x;

    private int y;

    private int fontSize;

    /**
     * Constructor for the State object
     */
    public State() {
        font = null;
        fontSize = -1;
        color = java.awt.Color.black;
    }

    /**
     * Sets the FontSize attribute of the State object
     *
     * @param value
     * 		The new FontSize value
     */
    public void setFontSize(int value) {
        if (value != fontSize) {
            fontSize = value;
            font = null;
        }
    }

    /**
     * Sets the Graphics attribute of the State object
     *
     * @param value
     * 		The new Graphics value
     */
    public void setGraphics(java.awt.Graphics value) {
        g = value;
    }

    /**
     * Sets the X attribute of the State object
     *
     * @param value
     * 		The new X value
     */
    public void setX(int value) {
        x = value;
    }

    /**
     * Sets the Y attribute of the State object
     *
     * @param value
     * 		The new Y value
     */
    public void setY(int value) {
        y = value;
    }

    /**
     * Gets the FontSize attribute of the State object
     *
     * @return The FontSize value
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Gets the Font attribute of the State object
     *
     * @return The Font value
     */
    public abstract java.awt.Font getFont();

    /**
     * Gets the Graphics attribute of the State object
     *
     * @return The Graphics value
     */
    public java.awt.Graphics getGraphics() {
        return g;
    }

    /**
     * Gets the X attribute of the State object
     *
     * @return The X value
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y attribute of the State object
     *
     * @return The Y value
     */
    public int getY() {
        return y;
    }

    /**
     * Processes a single line and returns the state that is in effect at the
     *  end of the line.
     *
     * @param line
     * 		the line
     * @return the state
     */
    public org.acm.seguin.print.xml.State processLine(java.lang.String line) {
        return processLine(line, 0, new java.lang.StringBuffer());
    }

    /**
     * The actual worker method that processes the line. This is what is defined
     *  by the various states
     *
     * @param line
     * 		the line
     * @param index
     * 		the index of the character
     * @param buf
     * 		the buffer
     * @return the state at the end of the line
     */
    protected abstract org.acm.seguin.print.xml.State processLine(java.lang.String line, int index, java.lang.StringBuffer buf);

    /**
     * Set the state for the next state
     *
     * @param next
     * 		the next state
     */
    protected void initState(org.acm.seguin.print.xml.State next) {
        next.setGraphics(getGraphics());
        next.setX(getX());
        next.setY(getY());
        next.setFontSize(getFontSize());
    }

    /**
     * Prints the buffer
     *
     * @param buf
     * 		the buffer
     */
    protected void print(java.lang.StringBuffer buf) {
        java.lang.String out = buf.toString();
        if (out.length() == 0) {
            return;
        }
        // System.out.println("Printing:  [" + out + "] (" + getClass().getName() + ") " + fontSize + "(" + x + ", " + y +")");
        // font = new Font("Monospaced", Font.PLAIN, fontSize);
        g.setFont(getFont());
        g.setColor(color);
        g.drawString(out, x, y);
        java.awt.FontMetrics fm = g.getFontMetrics();
        x = x + fm.stringWidth(out);
    }
}