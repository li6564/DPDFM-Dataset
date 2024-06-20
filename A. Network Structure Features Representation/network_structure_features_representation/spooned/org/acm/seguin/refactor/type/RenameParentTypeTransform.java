package org.acm.seguin.refactor.type;
/**
 * This object revises the import statements in the tree.
 *
 * @author Chris Seguin
 * @created October 23, 1999
 */
public class RenameParentTypeTransform extends org.acm.seguin.refactor.TransformAST {
    private java.lang.String newName = null;

    private java.lang.String oldName = null;

    private org.acm.seguin.summary.TypeSummary summary = null;

    /**
     * Sets the OldName attribute of the RenameParentTypeTransform object
     *
     * @param name
     * 		The new OldName value
     */
    public void setOldName(java.lang.String name) {
        oldName = name;
    }

    /**
     * Sets the NewName attribute of the RenameParentTypeTransform object
     *
     * @param name
     * 		The new NewName value
     */
    public void setNewName(java.lang.String name) {
        newName = name;
    }

    /**
     * Sets the TypeSummary attribute of the RenameParentTypeTransform object
     *
     * @param value
     * 		The new TypeSummary value
     */
    public void setTypeSummary(org.acm.seguin.summary.TypeSummary value) {
        summary = value;
    }

    /**
     * Update the syntax tree
     *
     * @param root
     * 		the root of the syntax tree
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        root.jjtAccept(new org.acm.seguin.refactor.type.RenameParentVisitor(), new org.acm.seguin.refactor.type.RenameTypeData(oldName, newName, summary));
    }
}