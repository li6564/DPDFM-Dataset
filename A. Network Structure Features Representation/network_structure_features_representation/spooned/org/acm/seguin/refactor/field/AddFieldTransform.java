package org.acm.seguin.refactor.field;
/**
 * Adds a field declaration to a AST
 *
 * @author Chris Seguin
 */
public class AddFieldTransform extends org.acm.seguin.refactor.TransformAST {
    private org.acm.seguin.parser.ast.SimpleNode fieldDecl;

    /**
     * Constructor for the AddFieldTransform object
     *
     * @param init
     * 		the field declaration to add
     */
    public AddFieldTransform(org.acm.seguin.parser.ast.SimpleNode init) {
        fieldDecl = init;
    }

    /**
     * Updates the AST
     *
     * @param root
     * 		the root of the AST
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        // Apply each individual transformation
        org.acm.seguin.refactor.field.AddFieldVisitor afv = new org.acm.seguin.refactor.field.AddFieldVisitor(fieldDecl);
        afv.visit(root, null);
    }
}