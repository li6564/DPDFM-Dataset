package org.acm.seguin.refactor.type;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class RemoveSamePackageTransform extends org.acm.seguin.refactor.TransformAST {
    /**
     * Update the syntax tree
     *
     * @param root
     * 		the root of the syntax tree
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.parser.ast.ASTName packageName = getPackageName(root);
        int ndx = 0;
        while (ndx < root.jjtGetNumChildren()) {
            org.acm.seguin.parser.Node next = root.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTImportDeclaration) {
                if (isImporting(packageName, ((org.acm.seguin.parser.ast.ASTImportDeclaration) (next)))) {
                    root.jjtDeleteChild(ndx);
                } else {
                    ndx++;
                }
            } else {
                ndx++;
            }
        } 
    }

    /**
     * Gets the PackageName attribute of the RemoveSamePackageTransform object
     *
     * @param root
     * 		Description of Parameter
     * @return The PackageName value
     */
    private org.acm.seguin.parser.ast.ASTName getPackageName(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.parser.ast.SimpleNode node = ((org.acm.seguin.parser.ast.SimpleNode) (root.jjtGetChild(0)));
        if (node instanceof org.acm.seguin.parser.ast.ASTPackageDeclaration) {
            return ((org.acm.seguin.parser.ast.ASTName) (node.jjtGetChild(0)));
        } else {
            return null;
        }
    }

    /**
     * Gets the Importing attribute of the RemoveSamePackageTransform object
     *
     * @param packageName
     * 		Description of Parameter
     * @param importDecl
     * 		Description of Parameter
     * @return The Importing value
     */
    private boolean isImporting(org.acm.seguin.parser.ast.ASTName packageName, org.acm.seguin.parser.ast.ASTImportDeclaration importDecl) {
        org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (importDecl.jjtGetChild(0)));
        if (packageName == null) {
            return name.getNameSize() == 1;
        }
        if (importDecl.isImportingPackage()) {
            return name.equals(packageName);
        } else {
            return ((packageName.getNameSize() + 1) == name.getNameSize()) && name.startsWith(packageName);
        }
    }
}