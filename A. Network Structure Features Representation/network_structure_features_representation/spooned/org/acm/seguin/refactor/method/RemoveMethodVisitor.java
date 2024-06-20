/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Visitor that traverses an AST and removes a specified method
 *
 * @author Chris Seguin
 */
public class RemoveMethodVisitor extends org.acm.seguin.refactor.method.IdentifyMethodVisitor {
    private org.acm.seguin.parser.ast.SimpleNode methodDecl;

    /**
     * Constructor for RemoveMethodVisitor that specifies the method to remove
     *
     * @param method
     * 		the name of the method
     */
    public RemoveMethodVisitor(org.acm.seguin.summary.MethodSummary method) {
        super(method);
        methodDecl = null;
    }

    /**
     * Gets the MethodDeclaration attribute of the RemoveMethodVisitor object
     *
     * @return The MethodDeclaration value
     */
    public org.acm.seguin.parser.ast.SimpleNode getMethodDeclaration() {
        return methodDecl;
    }

    /**
     * Visit a class body
     *
     * @param node
     * 		the class body node
     * @param data
     * 		the data for the visitor
     * @return the method if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBody node, java.lang.Object data) {
        java.lang.Object result = removeMethod(node);
        if (result == null) {
            result = super.visit(node, data);
        }
        return result;
    }

    /**
     * Visit an interface body
     *
     * @param node
     * 		the interface body node
     * @param data
     * 		data for the visitor
     * @return the method that was removed
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceBody node, java.lang.Object data) {
        java.lang.Object result = removeMethod(node);
        if (result == null) {
            result = super.visit(node, data);
        }
        return result;
    }

    /**
     * Skip nested classes
     *
     * @param node
     * 		the nested class
     * @param data
     * 		the data for the visitor
     * @return the method if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNestedClassDeclaration node, java.lang.Object data) {
        return null;
    }

    /**
     * Skip nested interfaces
     *
     * @param node
     * 		the nested interface
     * @param data
     * 		the data for the visitor
     * @return the method if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration node, java.lang.Object data) {
        return null;
    }

    /**
     * Remove the method, if it is the correct one. Return the method
     *  declaration that was removed
     *
     * @param node
     * 		The node we are considering removing the method from
     * @return The method declaration
     */
    private java.lang.Object removeMethod(org.acm.seguin.parser.ast.SimpleNode node) {
        int loop = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < loop; ndx++) {
            org.acm.seguin.parser.ast.SimpleNode next = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(ndx)));
            org.acm.seguin.parser.ast.SimpleNode possible = ((org.acm.seguin.parser.ast.SimpleNode) (next.jjtGetChild(0)));
            if (isFound(possible)) {
                removeSingle(node, next, ndx);
                return next;
            }
        }
        return null;
    }

    /**
     * Removes a method declaration where only a single variable was declared
     *
     * @param node
     * 		the class or interface body node
     * @param next
     * 		the container for the method declaration
     * @param ndx
     * 		the index of the node to delete
     */
    private void removeSingle(org.acm.seguin.parser.ast.SimpleNode node, org.acm.seguin.parser.ast.SimpleNode next, int ndx) {
        methodDecl = next;
        node.jjtDeleteChild(ndx);
    }
}