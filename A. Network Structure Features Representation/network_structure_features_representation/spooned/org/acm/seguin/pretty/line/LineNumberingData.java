/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty.line;
/**
 * The line number data
 *
 * @author Chris Seguin
 * @created October 14, 1999
 */
public class LineNumberingData extends org.acm.seguin.pretty.PrintData {
    /**
     * Create a print data object
     */
    public LineNumberingData() {
        super();
    }

    /**
     * Create a print data object
     *
     * @param out
     * 		the output stream
     */
    public LineNumberingData(java.io.OutputStream out) {
        super(out);
    }

    /**
     * Creates a line queue object
     *
     * @param output
     * 		the output stream
     * @return the queue
     */
    protected org.acm.seguin.pretty.LineQueue lineQueueFactory(java.io.PrintWriter output) {
        return new org.acm.seguin.pretty.line.NumberedLineQueue(output);
    }
}