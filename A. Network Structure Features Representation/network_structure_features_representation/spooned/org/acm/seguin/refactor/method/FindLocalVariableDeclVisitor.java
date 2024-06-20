/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Finds the local variable declaration
 *
 * @author Chris Seguin
 */
class FindLocalVariableDeclVisitor extends org.acm.seguin.parser.ChildrenVisitor {
    private boolean found = false;

    /**
     * Gets the Found attribute of the FindLocalVariableDeclVisitor object
     *
     * @return The Found value
     */
    public boolean isFound() {
        return found;
    }

    /**
     * Visits a block node. Stops traversing the tree if we come to a new class.
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBlockStatement node, java.lang.Object data) {
        org.acm.seguin.parser.Node child = node.jjtGetChild(0);
        if ((child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration) || (child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration)) {
            return java.lang.Boolean.FALSE;
        }
        return super.visit(node, data);
    }

    /**
     * Determines if it is used here
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration node, java.lang.Object data) {
        org.acm.seguin.summary.VariableSummary var = ((org.acm.seguin.summary.VariableSummary) (data));
        for (int ndx = 1; ndx < node.jjtGetNumChildren(); ndx++) {
            org.acm.seguin.parser.ast.ASTVariableDeclarator next = ((org.acm.seguin.parser.ast.ASTVariableDeclarator) (node.jjtGetChild(ndx)));
            org.acm.seguin.parser.ast.ASTVariableDeclaratorId id = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (next.jjtGetChild(0)));
            if (id.getName().equals(var.getName())) {
                found = true;
                return java.lang.Boolean.TRUE;
            }
        }
        return super.visit(node, data);
    }
}