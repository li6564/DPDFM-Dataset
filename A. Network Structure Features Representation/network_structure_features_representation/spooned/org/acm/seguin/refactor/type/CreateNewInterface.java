/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
import org.acm.seguin.refactor.RefactoringException;
/**
 * This object creates an interface from nothing. It is responsible for
 *  building up the parse tree from scratch to create a new interface.
 *
 * @author Grant Watson
 * @created November 28, 2000
 */
public class CreateNewInterface {
    private java.lang.String m_interfaceName;

    private java.lang.String m_packageName;

    private org.acm.seguin.summary.Summary m_summary;

    /**
     * Constructor for the CreateNewInterface object
     *
     * @param interfaceName
     * 		Description of Parameter
     * @param packageName
     * 		Description of Parameter
     * @param summary
     * 		Description of Parameter
     */
    public CreateNewInterface(org.acm.seguin.summary.Summary summary, java.lang.String packageName, java.lang.String interfaceName) {
        m_summary = summary;
        m_packageName = packageName;
        m_interfaceName = interfaceName;
    }

    /**
     * Constructor for the CreateNewInterface object
     */
    CreateNewInterface() {
        m_interfaceName = null;
        m_packageName = null;
    }

    /**
     * Creates the the designated class
     *
     * @return Description of the Returned Value
     * @exception RefactoringException
     * 		Description of Exception
     */
    public java.io.File run() throws org.acm.seguin.refactor.RefactoringException {
        if (m_packageName == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No package name specified");
        }
        if (m_interfaceName == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No interface name specified");
        }
        // Create the AST
        org.acm.seguin.parser.ast.ASTCompilationUnit root = new org.acm.seguin.parser.ast.ASTCompilationUnit(0);
        // Create the package statement
        int nextIndex = 0;
        if ((m_packageName != null) && (m_packageName.length() > 0)) {
            org.acm.seguin.parser.ast.ASTPackageDeclaration packDecl = createPackageDeclaration();
            root.jjtAddChild(packDecl, 0);
            nextIndex++;
        }
        org.acm.seguin.parser.ast.ASTName parentName = new org.acm.seguin.parser.ast.ASTName(0);
        // Create the class
        org.acm.seguin.parser.ast.ASTTypeDeclaration td = createTypeDeclaration();
        root.jjtAddChild(td, nextIndex);
        // Print this new one
        java.io.File dest = print(m_interfaceName, root);
        return dest;
    }

    /**
     * Creates the package declaration
     *
     * @return the package declaration
     */
    org.acm.seguin.parser.ast.ASTPackageDeclaration createPackageDeclaration() {
        org.acm.seguin.parser.ast.ASTPackageDeclaration packDecl = new org.acm.seguin.parser.ast.ASTPackageDeclaration(0);
        org.acm.seguin.parser.ast.ASTName packName = new org.acm.seguin.parser.ast.ASTName(0);
        packName.fromString(m_packageName);
        packDecl.jjtAddChild(packName, 0);
        return packDecl;
    }

    /**
     * Creates the type declaration
     *
     * @return the modified class
     */
    org.acm.seguin.parser.ast.ASTTypeDeclaration createTypeDeclaration() {
        org.acm.seguin.parser.ast.ASTTypeDeclaration td = new org.acm.seguin.parser.ast.ASTTypeDeclaration(0);
        org.acm.seguin.parser.ast.ASTInterfaceDeclaration id = createModifiedClass();
        td.jjtAddChild(id, 0);
        return td;
    }

    /**
     * Creates the modified class
     *
     * @return the modified class
     */
    org.acm.seguin.parser.ast.ASTInterfaceDeclaration createModifiedClass() {
        org.acm.seguin.parser.ast.ASTInterfaceDeclaration id = new org.acm.seguin.parser.ast.ASTInterfaceDeclaration(0);
        id.addModifier("public");
        org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration uid = createClassBody(m_interfaceName);
        id.jjtAddChild(uid, 0);
        return id;
    }

    /**
     * Creates the body. The protection level is package so it can be easily
     *  tested.
     *
     * @param parentName
     * 		Description of Parameter
     * @return the class
     */
    org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration createClassBody(java.lang.String parentName) {
        org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration uid = new org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration(0);
        uid.setName(parentName);
        org.acm.seguin.parser.ast.ASTInterfaceBody ib = new org.acm.seguin.parser.ast.ASTInterfaceBody(0);
        uid.jjtAddChild(ib, 0);
        return uid;
    }

    /**
     * Prints the file
     *
     * @param root
     * 		Description of Parameter
     * @param interfaceName
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    java.io.File print(java.lang.String interfaceName, org.acm.seguin.parser.ast.SimpleNode root) {
        java.io.File parent = org.acm.seguin.summary.query.TopLevelDirectory.getPackageDirectory(m_summary, m_packageName);
        // Create directory if it doesn't exist
        if (!parent.exists()) {
            parent.mkdir();
        }
        java.io.File destFile = new java.io.File(parent, interfaceName + ".java");
        try {
            new org.acm.seguin.pretty.PrettyPrintFile().apply(destFile, root);
        } catch (java.lang.Throwable thrown) {
            thrown.printStackTrace(java.lang.System.out);
        }
        return destFile;
    }

    /**
     * Gets the package summary
     *
     * @param base
     * 		Description of Parameter
     * @return the package summary
     */
    private org.acm.seguin.summary.PackageSummary getPackageSummary(org.acm.seguin.summary.Summary base) {
        org.acm.seguin.summary.Summary current = base;
        while (!(current instanceof org.acm.seguin.summary.PackageSummary)) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.PackageSummary) (current));
    }

    /**
     * Gets the SameParent attribute of the AddAbstractParent object
     *
     * @param one
     * 		Description of Parameter
     * @param two
     * 		Description of Parameter
     * @return The SameParent value
     */
    private boolean isSameParent(org.acm.seguin.summary.TypeSummary one, org.acm.seguin.summary.TypeSummary two) {
        if (isObject(one)) {
            return isObject(two);
        }
        if (isObject(two)) {
            return false;
        }
        return one.equals(two);
    }

    /**
     * Gets the Object attribute of the AddAbstractParent object
     *
     * @param item
     * 		Description of Parameter
     * @return The Object value
     */
    private boolean isObject(org.acm.seguin.summary.TypeSummary item) {
        if (item == null) {
            return true;
        }
        if (item.getName().equals("Object")) {
            return true;
        }
        return false;
    }
}