package org.acm.seguin.parser.query;
/**
 * Gets the package name
 *
 * @author Chris Seguin
 * @created November 23, 1999
 */
public class PackageNameGetter {
    /**
     * Return the package name
     *
     * @param initialDir
     * 		Description of Parameter
     * @param filename
     * 		Description of Parameter
     * @return the package name
     */
    public static org.acm.seguin.parser.ast.ASTName query(java.io.File initialDir, java.lang.String filename) {
        // Create a factory to get a root
        java.io.File inputFile = new java.io.File(initialDir, filename);
        org.acm.seguin.parser.factory.ParserFactory factory = new org.acm.seguin.parser.factory.FileParserFactory(inputFile);
        org.acm.seguin.parser.ast.SimpleNode root = factory.getAbstractSyntaxTree(false);
        return org.acm.seguin.parser.query.PackageNameGetter.query(root);
    }

    /**
     * Gets the package name
     *
     * @param root
     * 		the syntax tree
     * @return the name of the package or null if there is none
     */
    public static org.acm.seguin.parser.ast.ASTName query(org.acm.seguin.parser.ast.SimpleNode root) {
        if (root == null) {
            java.lang.System.out.println("Unable to find the file!");
            return null;
        }
        org.acm.seguin.parser.ast.SimpleNode first = ((org.acm.seguin.parser.ast.SimpleNode) (root.jjtGetChild(0)));
        if (first instanceof org.acm.seguin.parser.ast.ASTPackageDeclaration) {
            return ((org.acm.seguin.parser.ast.ASTName) (first.jjtGetChild(0)));
        }
        return null;
    }
}