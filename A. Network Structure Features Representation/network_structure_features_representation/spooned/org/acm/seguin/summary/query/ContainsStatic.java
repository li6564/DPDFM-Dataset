/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
public class ContainsStatic {
    public static boolean query(org.acm.seguin.summary.TypeSummary type, java.lang.String name, boolean isMethod) {
        if (type == null) {
            return true;
        }
        if (isMethod) {
            return org.acm.seguin.summary.query.ContainsStatic.searchMethods(type, name);
        } else {
            return org.acm.seguin.summary.query.ContainsStatic.searchFields(type, name);
        }
    }

    private static boolean searchMethods(org.acm.seguin.summary.TypeSummary type, java.lang.String name) {
        java.util.Iterator iter = type.getMethods();
        while (iter.hasNext()) {
            org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
            if (next.getName().equals(name)) {
                return true;
            }
        } 
        org.acm.seguin.summary.TypeDeclSummary parentDecl = type.getParentClass();
        org.acm.seguin.summary.TypeSummary parent = org.acm.seguin.summary.query.GetTypeSummary.query(parentDecl);
        if (parent != null) {
            return org.acm.seguin.summary.query.ContainsStatic.searchMethods(parent, name);
        }
        return false;
    }

    private static boolean searchFields(org.acm.seguin.summary.TypeSummary type, java.lang.String name) {
        java.util.Iterator iter = type.getFields();
        while (iter.hasNext()) {
            org.acm.seguin.summary.FieldSummary next = ((org.acm.seguin.summary.FieldSummary) (iter.next()));
            if (next.getName().equals(name)) {
                return true;
            }
        } 
        org.acm.seguin.summary.TypeDeclSummary parentDecl = type.getParentClass();
        org.acm.seguin.summary.TypeSummary parent = org.acm.seguin.summary.query.GetTypeSummary.query(parentDecl);
        if (parent != null) {
            return org.acm.seguin.summary.query.ContainsStatic.searchFields(parent, name);
        }
        return false;
    }
}