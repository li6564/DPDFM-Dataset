/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.print.text;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class LineSet {
    private java.util.ArrayList set;

    private static final int TAB_SIZE = 4;

    /**
     * Constructor for the LineSet object
     *
     * @param data
     * 		Description of Parameter
     */
    public LineSet(java.lang.String data) {
        set = new java.util.ArrayList();
        breakLine(data);
    }

    /**
     * Gets the Line attribute of the LineSet object
     *
     * @param index
     * 		Description of Parameter
     * @return The Line value
     */
    public java.lang.String getLine(int index) {
        if ((index < 0) || (index >= set.size())) {
            return null;
        }
        return expandTabs(((java.lang.String) (set.get(index))));
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public int size() {
        return set.size();
    }

    /**
     * Description of the Method
     *
     * @param input
     * 		Description of Parameter
     */
    private void breakLine(java.lang.String input) {
        int last = -1;
        int current = 0;
        int length = input.length();
        while (last < length) {
            while ((current < length) && (input.charAt(current) != '\n')) {
                current++;
            } 
            java.lang.String next = input.substring(last + 1, current);
            set.add(next);
            last = current;
            current++;
        } 
    }

    /**
     * Description of the Method
     *
     * @param line
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private java.lang.String expandTabs(java.lang.String line) {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        int last = line.length();
        for (int ndx = 0; ndx < last; ndx++) {
            char ch = line.charAt(ndx);
            if (ch == '\t') {
                int bufferLength = buffer.length();
                int spaces = bufferLength % org.acm.seguin.print.text.LineSet.TAB_SIZE;
                if (spaces == 0) {
                    spaces = org.acm.seguin.print.text.LineSet.TAB_SIZE;
                }
                for (int ndx2 = 0; ndx2 < spaces; ndx2++) {
                    buffer.append(' ');
                }
            } else if ((ch == '\r') || (ch == '\n')) {
                // Skip this character
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }
}