package org.acm.seguin.refactor.field;
/**
 * A transform that removes a specific field
 *
 * @author Chris Seguin
 */
public class RemoveFieldTransform extends org.acm.seguin.refactor.TransformAST {
    private org.acm.seguin.parser.ast.SimpleNode fieldDecl;

    private java.lang.String field;

    /**
     * Constructor for the RemoveFieldTransform object
     *
     * @param init
     * 		the name of the field
     */
    public RemoveFieldTransform(java.lang.String init) {
        field = init;
    }

    /**
     * Gets the FieldDeclaration attribute of the RemoveFieldTransform object
     *
     * @return The FieldDeclaration value
     */
    public org.acm.seguin.parser.ast.SimpleNode getFieldDeclaration() {
        return fieldDecl;
    }

    /**
     * Updates the root
     *
     * @param root
     * 		the ro
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.refactor.field.RemoveFieldVisitor rfv = new org.acm.seguin.refactor.field.RemoveFieldVisitor(field);
        rfv.visit(root, null);
        fieldDecl = rfv.getFieldDeclaration();
    }
}