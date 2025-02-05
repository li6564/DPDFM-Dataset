/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Visitor that seeks out and update the tree
 *
 * @author Chris Seguin
 */
class RenameParameterVisitor extends org.acm.seguin.refactor.method.IdentifyMethodVisitor {
    /**
     * Constructor for the RenameParameterVisitor object
     *
     * @param init
     * 		Description of Parameter
     */
    public RenameParameterVisitor(org.acm.seguin.summary.MethodSummary init) {
        super(init);
    }

    /**
     * Search and find the correct method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.method.RenameParameterTransform rpt = ((org.acm.seguin.refactor.method.RenameParameterTransform) (data));
        if (isFound(node)) {
            rpt.setRightTree(true);
            // Update the javadoc
            // Continue traversal
            super.visit(node, data);
            rpt.setRightTree(false);
            return null;
        }
        return super.visit(node, data);
    }

    /**
     * Search and find the correct method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConstructorDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.method.RenameParameterTransform rpt = ((org.acm.seguin.refactor.method.RenameParameterTransform) (data));
        if (isFound(node)) {
            rpt.setRightTree(true);
            // Update the javadoc
            // Continue traversal
            super.visit(node, data);
            rpt.setRightTree(false);
            return null;
        }
        return super.visit(node, data);
    }

    /**
     * Updates the parameter name
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTVariableDeclaratorId node, java.lang.Object data) {
        org.acm.seguin.refactor.method.RenameParameterTransform rpt = ((org.acm.seguin.refactor.method.RenameParameterTransform) (data));
        if (rpt.isRightTree()) {
            if (node.getName().equals(rpt.getParameter().getName())) {
                node.setName(rpt.getNewName());
            }
        }
        return null;
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
        org.acm.seguin.refactor.method.RenameParameterTransform rpt = ((org.acm.seguin.refactor.method.RenameParameterTransform) (data));
        if (rpt.isRightTree()) {
            org.acm.seguin.parser.Node child = node.jjtGetChild(0);
            if ((child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration) || (child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration)) {
                return null;
            }
        }
        return super.visit(node, data);
    }

    /**
     * Updates where that parameter is used
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimaryExpression node, java.lang.Object data) {
        org.acm.seguin.refactor.method.RenameParameterTransform rpt = ((org.acm.seguin.refactor.method.RenameParameterTransform) (data));
        if (rpt.isRightTree()) {
            org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix = ((org.acm.seguin.parser.ast.ASTPrimaryPrefix) (node.jjtGetChild(0)));
            org.acm.seguin.parser.ast.ASTPrimarySuffix suffix = null;
            if (node.jjtGetNumChildren() > 1) {
                suffix = ((org.acm.seguin.parser.ast.ASTPrimarySuffix) (node.jjtGetChild(1)));
            }
            if (((prefix.jjtGetNumChildren() > 0) && (prefix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName)) && (((suffix == null) || (suffix.jjtGetNumChildren() == 0)) || (!(suffix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTArguments)))) {
                org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (prefix.jjtGetChild(0)));
                if (name.getNamePart(0).equals(rpt.getParameter().getName())) {
                    name.setNamePart(0, rpt.getNewName());
                }
            }
        }
        return super.visit(node, data);
    }
}