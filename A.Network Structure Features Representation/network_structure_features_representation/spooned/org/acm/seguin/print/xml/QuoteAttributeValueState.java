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
public class QuoteAttributeValueState extends org.acm.seguin.print.xml.State {
    private static org.acm.seguin.print.xml.State state = null;

    /**
     * Gets the Font attribute of the State object
     *
     * @return The Font value
     */
    public java.awt.Font getFont() {
        if (font == null) {
            font = new java.awt.Font("SansSerif", java.awt.Font.ITALIC, getFontSize());
        }
        return font;
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
    protected org.acm.seguin.print.xml.State processLine(java.lang.String line, int index, java.lang.StringBuffer buf) {
        org.acm.seguin.print.xml.State nextState = null;
        int length = line.length();
        int start = index;
        while (nextState == null) {
            if (index == length) {
                print(buf);
                return this;
            }
            if (index != start) {
                if (((length != (index + 1)) && (line.charAt(index) == '\"')) && (line.charAt(index + 1) == '>')) {
                    nextState = org.acm.seguin.print.xml.TagState.getState();
                } else if (((length != (index + 1)) && (line.charAt(index) == '\"')) && (line.charAt(index + 1) == '?')) {
                    nextState = org.acm.seguin.print.xml.TagState.getState();
                } else if ((length != (index + 1)) && (line.charAt(index) == '\"')) {
                    nextState = org.acm.seguin.print.xml.AttributeState.getState();
                }
            }
            buf.append(line.charAt(index));
            index++;
        } 
        print(buf);
        initState(nextState);
        buf.setLength(0);
        return nextState.processLine(line, index, buf);
    }

    /**
     * Gets the State attribute of the TextState class
     *
     * @return The State value
     */
    public static org.acm.seguin.print.xml.State getState() {
        if (org.acm.seguin.print.xml.QuoteAttributeValueState.state == null) {
            org.acm.seguin.print.xml.QuoteAttributeValueState.state = new org.acm.seguin.print.xml.QuoteAttributeValueState();
        }
        return org.acm.seguin.print.xml.QuoteAttributeValueState.state;
    }
}