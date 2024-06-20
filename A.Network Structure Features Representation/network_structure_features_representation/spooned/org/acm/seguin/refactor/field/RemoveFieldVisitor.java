/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
/**
 * Visitor that traverses an AST and removes a specified field
 *
 * @author Chris Seguin
 */
public class RemoveFieldVisitor extends org.acm.seguin.parser.ChildrenVisitor {
    private java.lang.String fieldName;

    private org.acm.seguin.parser.ast.SimpleNode fieldDecl;

    /**
     * Constructor for RemoveFieldVisitor that specifies the field to remove
     *
     * @param field
     * 		the name of the field
     */
    public RemoveFieldVisitor(java.lang.String field) {
        fieldName = field;
        fieldDecl = null;
    }

    /**
     * Gets the FieldDeclaration attribute of the RemoveFieldVisitor object
     *
     * @return The FieldDeclaration value
     */
    public org.acm.seguin.parser.ast.SimpleNode getFieldDeclaration() {
        return fieldDecl;
    }

    /**
     * Visit a class body
     *
     * @param node
     * 		the class body node
     * @param data
     * 		the data for the visitor
     * @return the field if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBody node, java.lang.Object data) {
        java.lang.Object result = removeField(node);
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
     * @return the field that was removed
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceBody node, java.lang.Object data) {
        java.lang.Object result = removeField(node);
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
     * @return the field if it is found
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
     * @return the field if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration node, java.lang.Object data) {
        return null;
    }

    /**
     * Have we found the field declaration that we are going to move?
     *
     * @param next
     * 		Description of Parameter
     * @return The Found value
     */
    private boolean isFound(org.acm.seguin.parser.ast.SimpleNode next) {
        if (!(next instanceof org.acm.seguin.parser.ast.ASTFieldDeclaration)) {
            return false;
        }
        int loop = next.jjtGetNumChildren();
        for (int ndx = 1; ndx < loop; ndx++) {
            if (checkDeclaration(next, ndx)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if we are visiting a node that has multiple fields declared in
     *  a single statement
     *
     * @param field
     * 		Description of Parameter
     * @return The Multiple value
     */
    private boolean isMultiple(org.acm.seguin.parser.ast.SimpleNode field) {
        return field.jjtGetNumChildren() > 2;
    }

    /**
     * Remove the field, if it is the correct one. Return the field declaration
     *  that was removed
     *
     * @param node
     * 		The node we are considering removing the field from
     * @return The field declaration
     */
    private java.lang.Object removeField(org.acm.seguin.parser.ast.SimpleNode node) {
        int loop = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < loop; ndx++) {
            org.acm.seguin.parser.ast.SimpleNode next = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(ndx)));
            org.acm.seguin.parser.ast.SimpleNode possible = ((org.acm.seguin.parser.ast.SimpleNode) (next.jjtGetChild(0)));
            if (isFound(possible)) {
                if (isMultiple(possible)) {
                    removeMultiple(((org.acm.seguin.parser.ast.ASTFieldDeclaration) (possible)), next instanceof org.acm.seguin.parser.ast.ASTClassBodyDeclaration);
                } else {
                    removeSingle(node, next, ndx);
                }
                return next;
            }
        }
        return null;
    }

    /**
     * Removes a field declaration where only a single variable was declared
     *
     * @param node
     * 		the class or interface body node
     * @param next
     * 		the container for the field declaration
     * @param ndx
     * 		the index of the node to delete
     */
    private void removeSingle(org.acm.seguin.parser.ast.SimpleNode node, org.acm.seguin.parser.ast.SimpleNode next, int ndx) {
        fieldDecl = next;
        node.jjtDeleteChild(ndx);
    }

    /**
     * Removes a field that is declared as one of many
     *
     * @param next
     * 		the field declaration
     * @param isClass
     * 		was this in a class or an interface
     */
    private void removeMultiple(org.acm.seguin.parser.ast.ASTFieldDeclaration next, boolean isClass) {
        if (isClass) {
            fieldDecl = new org.acm.seguin.parser.ast.ASTClassBodyDeclaration(0);
        } else {
            fieldDecl = new org.acm.seguin.parser.ast.ASTInterfaceMemberDeclaration(0);
        }
        // Create the field declaration
        org.acm.seguin.parser.ast.ASTFieldDeclaration afd = new org.acm.seguin.parser.ast.ASTFieldDeclaration(0);
        fieldDecl.jjtInsertChild(afd, 0);
        // Copy the type
        afd.jjtInsertChild(next.jjtGetChild(0), 0);
        // Find the variable and remove it from the old and add it to the new
        int loop = next.jjtGetNumChildren();
        for (int ndx = 1; ndx < loop; ndx++) {
            if (checkDeclaration(next, ndx)) {
                afd.jjtInsertChild(next.jjtGetChild(ndx), 1);
                next.jjtDeleteChild(ndx);
                return;
            }
        }
    }

    /**
     * Checks a single variable declaration to see if it is the one we are
     *  looking for
     *
     * @param next
     * 		the field declaration that we are checking
     * @param index
     * 		the index of the id that we are checking
     * @return true if we have found the field
     */
    private boolean checkDeclaration(org.acm.seguin.parser.ast.SimpleNode next, int index) {
        org.acm.seguin.parser.ast.ASTVariableDeclarator decl = ((org.acm.seguin.parser.ast.ASTVariableDeclarator) (next.jjtGetChild(index)));
        org.acm.seguin.parser.ast.ASTVariableDeclaratorId id = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (decl.jjtGetChild(0)));
        return id.getName().equals(fieldName);
    }
}