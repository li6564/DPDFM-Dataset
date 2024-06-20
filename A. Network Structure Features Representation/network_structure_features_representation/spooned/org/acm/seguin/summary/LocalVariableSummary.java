/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Stores information about the formal parameter
 *
 * @author Chris Seguin
 * @created June 13, 1999
 */
public class LocalVariableSummary extends org.acm.seguin.summary.VariableSummary {
    /**
     * Creates a parameter summary
     *
     * @param parentSummary
     * 		the parent summary
     * @param typeNode
     * 		the type of parameter
     * @param id
     * 		the id of the parameter
     */
    public LocalVariableSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTType typeNode, org.acm.seguin.parser.ast.ASTVariableDeclaratorId id) {
        super(parentSummary, typeNode, id);
    }

    /**
     * Constructor for the LocalVariableSummary object
     *
     * @param parentSummary
     * 		Description of Parameter
     * @param type
     * 		Description of Parameter
     * @param name
     * 		Description of Parameter
     */
    public LocalVariableSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.summary.TypeDeclSummary type, java.lang.String name) {
        super(parentSummary, type, name);
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
     * Factory method
     *
     * @param parentSummary
     * 		the parent summary
     * @param field
     * 		the field declarator
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.summary.LocalVariableSummary[] createNew(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTLocalVariableDeclaration field) {
        // Local Variables
        int last = field.jjtGetNumChildren();
        org.acm.seguin.summary.LocalVariableSummary[] result = new org.acm.seguin.summary.LocalVariableSummary[last - 1];
        org.acm.seguin.parser.ast.ASTType type = ((org.acm.seguin.parser.ast.ASTType) (field.jjtGetChild(0)));
        // Create a summary for each field
        for (int ndx = 1; ndx < last; ndx++) {
            org.acm.seguin.parser.Node next = field.jjtGetChild(ndx);
            result[ndx - 1] = new org.acm.seguin.summary.LocalVariableSummary(parentSummary, type, ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (next.jjtGetChild(0))));
        }
        // Return the result
        return result;
    }
}