package org.acm.seguin.refactor.type;
/**
 * This object revises the package statement
 *
 * @author Chris Seguin
 * @created October 23, 1999
 */
public class ChangePackageTransform extends org.acm.seguin.refactor.TransformAST {
    private org.acm.seguin.parser.ast.ASTName name;

    /**
     * Constructor for the ChangePackageTransform object
     *
     * @param name
     * 		Description of Parameter
     */
    public ChangePackageTransform(org.acm.seguin.parser.ast.ASTName name) {
        this.name = name;
    }

    /**
     * Constructor for the ChangePackageTransform object
     *
     * @param packageName
     * 		Description of Parameter
     */
    public ChangePackageTransform(java.lang.String packageName) {
        name = org.acm.seguin.parser.factory.NameFactory.getName(packageName, null);
    }

    /**
     * Update the syntax tree
     *
     * @param root
     * 		the root of the syntax tree
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        if (name.getName().length() == 0) {
            removePackage(root);
        } else {
            addPackage(root);
        }
    }

    /**
     * Adds a feature to the Package attribute of the ChangePackageTransform
     *  object
     *
     * @param root
     * 		The feature to be added to the Package attribute
     */
    private void addPackage(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.parser.ast.SimpleNode first = ((org.acm.seguin.parser.ast.SimpleNode) (root.jjtGetChild(0)));
        org.acm.seguin.parser.ast.ASTPackageDeclaration pack = new org.acm.seguin.parser.ast.ASTPackageDeclaration(0);
        pack.jjtAddChild(name, 0);
        if (first instanceof org.acm.seguin.parser.ast.ASTPackageDeclaration) {
            root.jjtAddChild(pack, 0);
        } else {
            root.jjtInsertChild(pack, 0);
        }
    }

    /**
     * Description of the Method
     *
     * @param root
     * 		Description of Parameter
     */
    private void removePackage(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.parser.ast.SimpleNode first = ((org.acm.seguin.parser.ast.SimpleNode) (root.jjtGetChild(0)));
        if (first instanceof org.acm.seguin.parser.ast.ASTPackageDeclaration) {
            root.jjtDeleteChild(0);
        }
    }
}