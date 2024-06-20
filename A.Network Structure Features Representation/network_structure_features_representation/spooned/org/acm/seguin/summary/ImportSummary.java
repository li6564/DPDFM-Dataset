/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Stores the summary of an import
 *
 * @author Chris Seguin
 * @created June 6, 1999
 */
public class ImportSummary extends org.acm.seguin.summary.Summary {
    // Instance Variables
    private org.acm.seguin.summary.PackageSummary packageSummary;

    private java.lang.String type;

    /**
     * Create an import summary
     *
     * @param parentSummary
     * 		the parent summary
     * @param importDecl
     * 		the import declaration
     */
    public ImportSummary(org.acm.seguin.summary.Summary parentSummary, org.acm.seguin.parser.ast.ASTImportDeclaration importDecl) {
        // Load parent class
        super(parentSummary);
        // Local Variables
        org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (importDecl.jjtGetChild(0)));
        if (importDecl.isImportingPackage()) {
            type = null;
            packageSummary = org.acm.seguin.summary.PackageSummary.getPackageSummary(name.getName());
        } else {
            int last = name.getNameSize() - 1;
            type = name.getNamePart(last).intern();
            java.lang.String packageName = getPackageName(last, name);
            packageSummary = org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName);
        }
    }

    /**
     * Get the package
     *
     * @return the package summary
     */
    public org.acm.seguin.summary.PackageSummary getPackage() {
        return packageSummary;
    }

    /**
     * Get the type
     *
     * @return the name of the type or null if this represents the entire
    package
     */
    public java.lang.String getType() {
        return type;
    }

    /**
     * Provide method to visit a node
     *
     * @param visitor
     * 		the visitor
     * @param data
     * 		the data for the visit
     * @return some new data
     */
    public java.lang.Object accept(org.acm.seguin.summary.SummaryVisitor visitor, java.lang.Object data) {
        return visitor.visit(this, data);
    }

    /**
     * Extract the name of the package
     *
     * @param last
     * 		the index of the last
     * @param name
     * 		the name
     * @return the package name
     */
    private java.lang.String getPackageName(int last, org.acm.seguin.parser.ast.ASTName name) {
        if (last > 0) {
            java.lang.StringBuffer buffer = new java.lang.StringBuffer(name.getNamePart(0));
            for (int ndx = 1; ndx < last; ndx++) {
                buffer.append(".");
                buffer.append(name.getNamePart(ndx));
            }
            return buffer.toString();
        }
        return "";
    }

    public java.lang.String toString() {
        if (type == null)
            return ("ImportSummary<" + packageSummary.getName()) + ".*>";

        return ((("ImportSummary<" + packageSummary.getName()) + ".") + type) + ">";
    }

    public java.lang.String getName() {
        return type;
    }
}