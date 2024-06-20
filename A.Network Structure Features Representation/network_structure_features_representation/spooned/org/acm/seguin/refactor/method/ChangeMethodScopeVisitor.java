/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Changes the scope of the method (from private to protected, etc.)
 *
 * @author Chris Seguin
 */
class ChangeMethodScopeVisitor extends org.acm.seguin.refactor.method.IdentifyMethodVisitor {
    private int changeTo;

    static final int PRIVATE = 1;

    static final int PACKAGE = 2;

    static final int PROTECTED = 3;

    static final int PUBLIC = 4;

    /**
     * Constructor for the ChangeMethodScopeVisitor object
     *
     * @param init
     * 		Description of Parameter
     * @param newScope
     * 		Description of Parameter
     */
    public ChangeMethodScopeVisitor(org.acm.seguin.summary.MethodSummary init, int newScope) {
        super(init);
        changeTo = newScope;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclaration node, java.lang.Object data) {
        if (isFound(node)) {
            changeScope(node);
        }
        return null;
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
     * Changes the scope on a method declaration
     *
     * @param decl
     * 		the declaration to change scope on
     */
    private void changeScope(org.acm.seguin.parser.ast.ASTMethodDeclaration decl) {
        org.acm.seguin.pretty.ModifierHolder holder = decl.getModifiers();
        switch (changeTo) {
            case org.acm.seguin.refactor.method.ChangeMethodScopeVisitor.PUBLIC :
                holder.setPrivate(false);
                holder.setProtected(false);
                holder.setPublic(true);
                break;
            case org.acm.seguin.refactor.method.ChangeMethodScopeVisitor.PROTECTED :
                holder.setPrivate(false);
                holder.setProtected(true);
                holder.setPublic(false);
                break;
            case org.acm.seguin.refactor.method.ChangeMethodScopeVisitor.PACKAGE :
                holder.setPrivate(false);
                holder.setProtected(false);
                holder.setPublic(false);
                break;
            case org.acm.seguin.refactor.method.ChangeMethodScopeVisitor.PRIVATE :
                holder.setPrivate(true);
                holder.setProtected(false);
                holder.setPublic(false);
                break;
        }
    }
}