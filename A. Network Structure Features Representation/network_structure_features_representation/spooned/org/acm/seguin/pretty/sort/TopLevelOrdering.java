/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;
/**
 * Sorts the items in a java file at the top level
 *
 * @author Chris Seguin
 */
public class TopLevelOrdering extends org.acm.seguin.pretty.sort.Ordering {
    /**
     * Compare two items
     *
     * @param one
     * 		the first item
     * @param two
     * 		the second item
     * @return 1 if the first item is greater than the second, -1 if the
    first item is less than the second, and 0 otherwise.
     */
    public int compare(java.lang.Object one, java.lang.Object two) {
        int oneIndex = getIndex(one);
        int twoIndex = getIndex(two);
        if (oneIndex > twoIndex) {
            return 1;
        } else if (oneIndex < twoIndex) {
            return -1;
        } else {
            return fineCompare(one, two);
        }
    }

    /**
     * Return the index of the item in the order array
     *
     * @param object
     * 		the object we are checking
     * @return the objects index if it is found or 7 if it is not
     */
    protected int getIndex(java.lang.Object object) {
        if (object instanceof org.acm.seguin.parser.ast.ASTPackageDeclaration) {
            return 1;
        } else if (object instanceof org.acm.seguin.parser.ast.ASTImportDeclaration) {
            return 2;
        } else if (object instanceof org.acm.seguin.parser.ast.ASTTypeDeclaration) {
            org.acm.seguin.parser.ast.ASTTypeDeclaration type = ((org.acm.seguin.parser.ast.ASTTypeDeclaration) (object));
            org.acm.seguin.parser.Node child = type.jjtGetChild(0);
            if (child instanceof org.acm.seguin.parser.ast.ASTClassDeclaration) {
                org.acm.seguin.parser.ast.ASTClassDeclaration classDecl = ((org.acm.seguin.parser.ast.ASTClassDeclaration) (child));
                if (classDecl.isPublic()) {
                    return 3;
                } else {
                    return 4;
                }
            } else {
                org.acm.seguin.parser.ast.ASTInterfaceDeclaration interfaceDecl = ((org.acm.seguin.parser.ast.ASTInterfaceDeclaration) (child));
                if (interfaceDecl.isPublic()) {
                    return 3;
                } else {
                    return 4;
                }
            }
        }
        return 5;
    }

    /**
     * Fine grain comparison based on knowing what the types are
     *
     * @param obj1
     * 		the object
     * @param obj2
     * 		the second object
     * @return -1 if obj1 is less than obj2, 0 if they are the same, and +1 if
    obj1 is greater than obj2
     */
    private int fineCompare(java.lang.Object obj1, java.lang.Object obj2) {
        if (obj1 instanceof org.acm.seguin.parser.ast.ASTImportDeclaration) {
            return compareImports(((org.acm.seguin.parser.ast.ASTImportDeclaration) (obj1)), ((org.acm.seguin.parser.ast.ASTImportDeclaration) (obj2)));
        }
        return 0;
    }

    /**
     * Compares two import statements
     *
     * @param import1
     * 		the first statement
     * @param import2
     * 		the second statement
     * @return -1 if import1 is less than import2, 0 if they are the same, and +1 if
    import1 is greater than import2
     */
    private int compareImports(org.acm.seguin.parser.ast.ASTImportDeclaration import1, org.acm.seguin.parser.ast.ASTImportDeclaration import2) {
        org.acm.seguin.parser.ast.ASTName firstName = ((org.acm.seguin.parser.ast.ASTName) (import1.jjtGetChild(0)));
        org.acm.seguin.parser.ast.ASTName secondName = ((org.acm.seguin.parser.ast.ASTName) (import2.jjtGetChild(0)));
        return firstName.getName().compareTo(secondName.getName());
    }
}