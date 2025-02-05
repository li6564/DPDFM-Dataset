/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Basic summary class. Provides a single point for a visitor to encounter
 *  and a parent summary. All summaries have a parent except package
 *  summaries.
 *
 * @author Chris Seguin
 * @created May 12, 1999
 */
public abstract class Summary implements java.io.Serializable {
    // Local Variables
    private org.acm.seguin.summary.Summary parent;

    private int start;

    private int end;

    /**
     * Create a summary object
     *
     * @param initParent
     * 		the parent
     */
    public Summary(org.acm.seguin.summary.Summary initParent) {
        parent = initParent;
        start = -1;
        end = -1;
    }

    /**
     * Return the parent object
     *
     * @return the parent object
     */
    public org.acm.seguin.summary.Summary getParent() {
        return parent;
    }

    /**
     * Gets the StartLine attribute of the Summary object
     *
     * @return The StartLine value
     */
    public int getStartLine() {
        return start;
    }

    /**
     * Gets the EndLine attribute of the Summary object
     *
     * @return The EndLine value
     */
    public int getEndLine() {
        return end;
    }

    /**
     * Gets the DeclarationLine attribute of the MethodSummary object
     *
     * @return The DeclarationLine value
     */
    public int getDeclarationLine() {
        return java.lang.Math.min(start + 1, end);
    }

    /**
     * Returns the name
     *
     * @return the name
     */
    public abstract java.lang.String getName();

    /**
     * Provide method to visit a node
     *
     * @param visitor
     * 		the visitor
     * @param data
     * 		the data for the visit
     * @return some new data
     */
    public java.lang.Object accept(org.acm.seguin.summary.SummaryVisitor visitor, java.lang.Object data) {
        return visitor.visit(this, data);
    }

    /**
     * Sets the StartLine attribute of the Summary object
     *
     * @param value
     * 		The new StartLine value
     */
    protected void setStartLine(int value) {
        start = value;
    }

    /**
     * Sets the EndLine attribute of the Summary object
     *
     * @param value
     * 		The new EndLine value
     */
    protected void setEndLine(int value) {
        end = value;
        if (end < start) {
            start = end;
        }
    }
}