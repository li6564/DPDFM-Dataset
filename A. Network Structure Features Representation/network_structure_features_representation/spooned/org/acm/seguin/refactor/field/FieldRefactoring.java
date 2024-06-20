/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
/**
 * Base class for all field refactorings
 *
 * @author Chris Seguin
 */
abstract class FieldRefactoring extends org.acm.seguin.refactor.Refactoring {
    /**
     * The name of the field
     */
    protected java.lang.String field;

    /**
     * The type summary that contains the field
     */
    protected org.acm.seguin.summary.TypeSummary typeSummary;

    /**
     * Constructor for the FieldRefactoring object
     */
    public FieldRefactoring() {
        super();
    }

    /**
     * Sets the Class attribute of the PullupFieldRefactoring object
     *
     * @param packageName
     * 		the package name
     * @param className
     * 		the class name
     */
    public void setClass(java.lang.String packageName, java.lang.String className) {
        setClass(org.acm.seguin.summary.query.GetTypeSummary.query(org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName), className));
    }

    /**
     * Sets the Class attribute of the PullupFieldRefactoring object
     *
     * @param init
     * 		The new Class value
     */
    public void setClass(org.acm.seguin.summary.TypeSummary init) {
        typeSummary = init;
    }

    /**
     * Sets the Field attribute of the PullupFieldRefactoring object
     *
     * @param fieldName
     * 		The new Field value
     */
    public void setField(java.lang.String fieldName) {
        field = fieldName;
    }

    /**
     * Determines if the specified type is in java.lang package
     *
     * @param type
     * 		the type
     * @return true if it is in the package
     */
    protected boolean isInJavaLang(org.acm.seguin.parser.ast.ASTName type) {
        return ((type.getNameSize() == 3) && type.getNamePart(0).equals("java")) && type.getNamePart(1).equals("lang");
    }

    /**
     * Determines if the specified type is in java.lang package
     *
     * @param type
     * 		the type
     * @return true if it is in the package
     */
    protected boolean isInJavaLang(org.acm.seguin.summary.TypeSummary type) {
        return getPackage(type).getName().equals("java.lang");
    }

    /**
     * Gets the package summary for the specific object
     *
     * @param current
     * 		the summary
     * @return the package summary
     */
    protected org.acm.seguin.summary.PackageSummary getPackage(org.acm.seguin.summary.Summary current) {
        while (!(current instanceof org.acm.seguin.summary.PackageSummary)) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.PackageSummary) (current));
    }

    /**
     * Gets the package summary for the specific object
     *
     * @param current
     * 		the summary
     * @return the package summary
     */
    protected org.acm.seguin.summary.FileSummary getFileSummary(org.acm.seguin.summary.Summary current) {
        while (!(current instanceof org.acm.seguin.summary.FileSummary)) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.FileSummary) (current));
    }

    /**
     * Gets the FieldType attribute of the PullupFieldRefactoring object
     *
     * @param node
     * 		Description of Parameter
     * @param fileSummary
     * 		Description of Parameter
     * @return The FieldType value
     */
    protected java.lang.Object getFieldType(org.acm.seguin.parser.ast.SimpleNode node, org.acm.seguin.summary.FileSummary fileSummary) {
        org.acm.seguin.parser.ast.ASTFieldDeclaration child = ((org.acm.seguin.parser.ast.ASTFieldDeclaration) (node.jjtGetChild(0)));
        org.acm.seguin.parser.ast.ASTType type = ((org.acm.seguin.parser.ast.ASTType) (child.jjtGetChild(0)));
        if (type.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimitiveType) {
            return null;
        }
        org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (type.jjtGetChild(0)));
        if (name.getNameSize() == 1) {
            return org.acm.seguin.summary.query.GetTypeSummary.query(fileSummary, name.getName());
        } else {
            return name;
        }
    }
}