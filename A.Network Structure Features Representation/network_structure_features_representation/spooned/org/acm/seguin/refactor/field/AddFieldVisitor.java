package org.acm.seguin.refactor.field;
/**
 * Adds a field to the tree
 *
 * @author Chris Seguin
 */
public class AddFieldVisitor extends org.acm.seguin.parser.ChildrenVisitor {
    private org.acm.seguin.parser.ast.SimpleNode field;

    /**
     * Constructor for the AddFieldVisitor object
     *
     * @param init
     * 		Description of Parameter
     */
    public AddFieldVisitor(org.acm.seguin.parser.ast.SimpleNode init) {
        field = init;
    }

    /**
     * Visit a class body
     *
     * @param node
     * 		the class body node
     * @param data
     * 		the data for the visitor
     * @return always returns null
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBody node, java.lang.Object data) {
        node.jjtInsertChild(field, node.jjtGetNumChildren());
        return null;
    }

    /**
     * Visit an interface body
     *
     * @param node
     * 		the interface body node
     * @param data
     * 		data for the visitor
     * @return always returns null
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceBody node, java.lang.Object data) {
        node.jjtInsertChild(field, node.jjtGetNumChildren());
        return null;
    }
}