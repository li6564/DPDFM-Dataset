/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Stores information about a field
 *
 * @author Chris Seguin
 * @created June 10, 1999
 */
public class FieldSummary extends org.acm.seguin.summary.VariableSummary {
    // Instance Variables
    private org.acm.seguin.pretty.ModifierHolder modifiers;

    /**
     * Construct a method from a method declaration node
     *
     * @param parentSummary
     * 		the parent summary
     * @param id
     * 		The id of the variable
     * @param typeNode
     * 		Description of Parameter
     */
    public FieldSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTType typeNode, org.acm.seguin.parser.ast.ASTVariableDeclaratorId id) {
        super(parentSummary, typeNode, id);
    }

    /**
     * Returns the modifier holder
     *
     * @return the holder
     */
    public org.acm.seguin.pretty.ModifierHolder getModifiers() {
        return modifiers;
    }

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
     * Sets the modifier holder
     *
     * @param mod
     * 		the holder
     */
    protected void setModifiers(org.acm.seguin.pretty.ModifierHolder mod) {
        modifiers = mod;
    }
}