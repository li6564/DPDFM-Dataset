package org.acm.seguin.refactor.type;
/**
 * This object traverses the syntax tree and renames the types. An old and a
 *  new value are provided.
 *
 * @author Chris Seguin
 * @created October 23, 1999
 */
public class RenameTypeTransform extends org.acm.seguin.refactor.TransformAST {
    private org.acm.seguin.parser.ast.ASTName oldName;

    private org.acm.seguin.parser.ast.ASTName newName;

    private org.acm.seguin.summary.TypeSummary summary;

    /**
     * Constructor for the RenameTypeTransform object
     *
     * @param oldValue
     * 		the old name
     * @param newValue
     * 		the new name
     * @param init
     * 		Description of Parameter
     */
    public RenameTypeTransform(org.acm.seguin.parser.ast.ASTName oldValue, org.acm.seguin.parser.ast.ASTName newValue, org.acm.seguin.summary.TypeSummary init) {
        oldName = oldValue;
        newName = newValue;
        summary = init;
    }

    /**
     * Constructor for the RenameTypeTransform object
     *
     * @param oldPackageName
     * 		the old package
     * @param newPackageName
     * 		Description of Parameter
     * @param className
     * 		Description of Parameter
     */
    public RenameTypeTransform(java.lang.String oldPackageName, java.lang.String newPackageName, java.lang.String className) {
        newName = org.acm.seguin.parser.factory.NameFactory.getName(newPackageName, className);
        oldName = org.acm.seguin.parser.factory.NameFactory.getName(oldPackageName, className);
        summary = org.acm.seguin.summary.query.GetTypeSummary.query(oldPackageName, className);
    }

    /**
     * Constructor for the RenameTypeTransform object
     *
     * @param oldPackageName
     * 		the old package
     * @param newValue
     * 		the new name
     * @param init
     * 		Description of Parameter
     */
    public RenameTypeTransform(java.lang.String oldPackageName, org.acm.seguin.parser.ast.ASTName newValue, org.acm.seguin.summary.TypeSummary init) {
        newName = newValue;
        oldName = new org.acm.seguin.parser.ast.ASTName(0);
        oldName.fromString(oldPackageName);
        oldName.addNamePart(newName.getNamePart(newName.getNameSize() - 1));
        summary = init;
    }

    /**
     * Update the syntax tree
     *
     * @param root
     * 		the root of the syntax tree
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.refactor.type.RenameTypeVisitor rtv = new org.acm.seguin.refactor.type.RenameTypeVisitor();
        root.jjtAccept(rtv, new org.acm.seguin.refactor.type.RenameTypeData(oldName, newName, summary));
    }
}