/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;
/**
 * Orders the items in a class according to type.
 *
 * @author Chris Seguin
 * @created August 3, 1999
 */
public class StaticOrder extends org.acm.seguin.pretty.sort.Ordering {
    // Instance variables
    private boolean staticFirst;

    /**
     * Constructor for the StaticOrder object <P>
     *
     *  The string should either be "instance", "static", or "class". "instance"
     *  means that instance variables should go first. Either of the other two
     *  ordering strings indicate that the class variables or methods should go
     *  first.
     *
     * @param ordering
     * 		A user specified string that describes the order.
     */
    public StaticOrder(java.lang.String ordering) {
        char ch = ordering.trim().charAt(0);
        staticFirst = (((ch == 'c') || (ch == 'C')) || (ch == 's')) || (ch == 'S');
    }

    /**
     * Return the index of the item in the order array
     *
     * @param object
     * 		the object we are checking
     * @return the objects index if it is found or 7 if it is not
     */
    protected int getIndex(java.lang.Object object) {
        java.lang.Object data = ((org.acm.seguin.parser.ast.SimpleNode) (object)).jjtGetChild(0);
        if (data instanceof org.acm.seguin.parser.ast.ASTClassBodyDeclaration) {
            data = ((org.acm.seguin.parser.ast.ASTClassBodyDeclaration) (data)).jjtGetChild(0);
        } else if (data instanceof org.acm.seguin.parser.ast.ASTInterfaceMemberDeclaration) {
            data = ((org.acm.seguin.parser.ast.ASTInterfaceMemberDeclaration) (data)).jjtGetChild(0);
        }
        boolean currentIsStatic = false;
        // Now that we have data, determine the type of data
        if (data instanceof org.acm.seguin.parser.ast.ASTFieldDeclaration) {
            currentIsStatic = ((org.acm.seguin.parser.ast.ASTFieldDeclaration) (data)).isStatic();
        } else if (data instanceof org.acm.seguin.parser.ast.ASTConstructorDeclaration) {
            currentIsStatic = false;
        } else if (data instanceof org.acm.seguin.parser.ast.ASTMethodDeclaration) {
            currentIsStatic = ((org.acm.seguin.parser.ast.ASTMethodDeclaration) (data)).isStatic();
        } else if (data instanceof org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration) {
            currentIsStatic = ((org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration) (data)).isStatic();
        } else if (data instanceof org.acm.seguin.parser.ast.ASTNestedClassDeclaration) {
            currentIsStatic = ((org.acm.seguin.parser.ast.ASTNestedClassDeclaration) (data)).isStatic();
        } else if (data instanceof org.acm.seguin.parser.ast.ASTInitializer) {
            currentIsStatic = ((org.acm.seguin.parser.ast.ASTInitializer) (data)).isUsingStatic();
        } else {
            return 2;
        }
        int result = 0;
        if (currentIsStatic) {
            if (staticFirst) {
                result = 0;
            } else {
                result = 1;
            }
        } else if (staticFirst) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }
}