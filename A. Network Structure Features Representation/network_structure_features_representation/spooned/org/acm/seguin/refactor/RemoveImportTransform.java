/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor;
/**
 * This object revises the import statements in the tree.
 *
 * @author Chris Seguin
 */
public class RemoveImportTransform extends org.acm.seguin.refactor.TransformAST {
    private org.acm.seguin.parser.ast.ASTName name;

    private boolean packageImport;

    /**
     * Constructor for the RemoveImportTransform object
     *
     * @param name
     * 		Description of Parameter
     */
    public RemoveImportTransform(org.acm.seguin.parser.ast.ASTName name) {
        this.name = name;
        packageImport = false;
    }

    /**
     * Constructor for the RemoveImportTransform object
     *
     * @param packageName
     * 		Description of Parameter
     * @param className
     * 		Description of Parameter
     */
    public RemoveImportTransform(java.lang.String packageName, java.lang.String className) {
        name = org.acm.seguin.parser.factory.NameFactory.getName(packageName, className);
        packageImport = false;
    }

    /**
     * Constructor for the RemoveImportTransform object
     *
     * @param summary
     * 		Description of Parameter
     */
    public RemoveImportTransform(org.acm.seguin.summary.PackageSummary summary) {
        this.name = new org.acm.seguin.parser.ast.ASTName(0);
        this.name.fromString(summary.getName());
        packageImport = true;
    }

    /**
     * Update the syntax tree
     *
     * @param root
     * 		Description of Parameter
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        // Local Variables
        int ndx = 0;
        // While we aren't done
        while (ndx < root.jjtGetNumChildren()) {
            if (isInvalid(((org.acm.seguin.parser.ast.SimpleNode) (root.jjtGetChild(ndx))))) {
                // Delete this child
                root.jjtDeleteChild(ndx);
            } else {
                ndx++;
            }
        } 
    }

    /**
     * This method determines if the particular child of the compilation unit
     *  should be deleted.
     *
     * @param child
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected boolean isInvalid(org.acm.seguin.parser.ast.SimpleNode child) {
        if (child instanceof org.acm.seguin.parser.ast.ASTImportDeclaration) {
            // Cast this to an import
            org.acm.seguin.parser.ast.ASTImportDeclaration importDecl = ((org.acm.seguin.parser.ast.ASTImportDeclaration) (child));
            // Check each of the targets
            if (!packageImport) {
                return importDecl.jjtGetChild(0).equals(name);
            } else {
                org.acm.seguin.parser.ast.ASTName nameNode = ((org.acm.seguin.parser.ast.ASTName) (importDecl.jjtGetChild(0)));
                java.lang.String code = nameNode.getName();
                java.lang.String packageName = name.getName();
                return code.equals(packageName);
            }
        }
        // We have passed all the tests - so it can't be the one
        return false;
    }
}