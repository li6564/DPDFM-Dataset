/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;
/**
 * The ordering is the basic tool to determine if the parse tree node is in
 *  the proper order. This object contains the base class for the order.
 *
 * @author Chris Seguin
 */
public class FieldInitializerOrder extends org.acm.seguin.pretty.sort.Ordering {
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
        if (oneIndex == 1) {
            return -1;
        } else if (twoIndex == 1) {
            return 1;
        } else {
            return 0;
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
        java.lang.Object data = ((org.acm.seguin.parser.ast.SimpleNode) (object)).jjtGetChild(0);
        if (data instanceof org.acm.seguin.parser.ast.ASTFieldDeclaration) {
            org.acm.seguin.parser.ast.ASTFieldDeclaration fieldDecl = ((org.acm.seguin.parser.ast.ASTFieldDeclaration) (data));
            int last = fieldDecl.jjtGetNumChildren();
            for (int ndx = 1; ndx < last; ndx++) {
                org.acm.seguin.parser.ast.ASTVariableDeclarator next = ((org.acm.seguin.parser.ast.ASTVariableDeclarator) (fieldDecl.jjtGetChild(ndx)));
                if (next.jjtGetNumChildren() > 1) {
                    return 1;
                }
            }
            return 0;
        }
        return -1;
    }
}