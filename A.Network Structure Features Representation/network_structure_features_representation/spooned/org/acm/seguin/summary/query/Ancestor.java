/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Checks to see if one type is an ancestor of another one
 *
 * @author Chris Seguin
 */
public class Ancestor {
    /**
     * Checks to see if one type is an ancestor of another one
     *
     * @param node
     * 		the type summary in question
     * @param ancestor
     * 		the ancestor type summary
     * @return true if node is child (or grandchild or great grandchild
    or ...) of ancestor
     */
    public static boolean query(org.acm.seguin.summary.TypeSummary node, org.acm.seguin.summary.TypeSummary ancestor) {
        org.acm.seguin.summary.TypeSummary current = node;
        if ((ancestor == null) || (current == null)) {
            return false;
        }
        if (ancestor.getName().equals("Object")) {
            return true;
        }
        do {
            org.acm.seguin.summary.TypeDeclSummary decl = current.getParentClass();
            current = org.acm.seguin.summary.query.GetTypeSummary.query(decl);
            if (current == ancestor) {
                return true;
            }
        } while (current != null );
        return false;
    }
}