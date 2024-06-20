/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Finds a method associated with a particular type summary. A permission is
 *  also specified to insure that we find something interesting.
 *
 * @author Chris Seguin
 */
public class MethodQuery {
    /**
     * The method we are looking for can have any protection level
     */
    public static final int PRIVATE = 1;

    /**
     * The method we are looking for must have default protection level or higher
     */
    public static final int DEFAULT = 2;

    /**
     * The method we are looking for must have protected protection level or
     *  higher
     */
    public static final int PROTECTED = 3;

    /**
     * The method we are looking for must be public
     */
    public static final int PUBLIC = 4;

    /**
     * Finds an associated method
     *
     * @param typeSummary
     * 		the type
     * @param name
     * 		the name
     * @return the method found or null if none
     */
    public static org.acm.seguin.summary.MethodSummary find(org.acm.seguin.summary.TypeSummary typeSummary, java.lang.String name) {
        org.acm.seguin.summary.MethodSummary result = org.acm.seguin.summary.query.MethodQuery.query(typeSummary, name, org.acm.seguin.summary.query.MethodQuery.PRIVATE);
        if (result == null) {
            result = org.acm.seguin.summary.query.MethodQuery.queryAncestors(typeSummary, name, org.acm.seguin.summary.query.MethodQuery.PROTECTED);
        }
        return result;
    }

    /**
     * Finds the method associated with a type
     *
     * @param typeSummary
     * 		the type to search
     * @param name
     * 		the name of the method
     * @param protection
     * 		the minimum protection level
     * @return the method summary if one is found, null if none is
    found
     */
    public static org.acm.seguin.summary.MethodSummary query(org.acm.seguin.summary.TypeSummary typeSummary, java.lang.String name, int protection) {
        java.util.Iterator iter = typeSummary.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                if (org.acm.seguin.summary.query.MethodQuery.appropriate(next, name, protection)) {
                    return next;
                }
            } 
        }
        return null;
    }

    /**
     * Finds the method associated with a type in the ancestors of that type
     *
     * @param typeSummary
     * 		the type to search
     * @param name
     * 		the name of the method
     * @param protection
     * 		the minimum protection level
     * @return the method summary if one is found, null if none is
    found
     */
    public static org.acm.seguin.summary.MethodSummary queryAncestors(org.acm.seguin.summary.TypeSummary typeSummary, java.lang.String name, int protection) {
        org.acm.seguin.summary.TypeDeclSummary next = typeSummary.getParentClass();
        org.acm.seguin.summary.TypeSummary current = org.acm.seguin.summary.query.GetTypeSummary.query(next);
        while (current != null) {
            org.acm.seguin.summary.MethodSummary attempt = org.acm.seguin.summary.query.MethodQuery.query(current, name, protection);
            if (attempt != null) {
                return attempt;
            }
            next = current.getParentClass();
            current = org.acm.seguin.summary.query.GetTypeSummary.query(next);
        } 
        return null;
    }

    /**
     * Checks if the method we are considering is the correct type
     *
     * @param methodSummary
     * 		the summary of the method
     * @param name
     * 		the name of the method
     * @param protection
     * 		the protection level of the method
     * @return true if the method has the appropriate name and the
    appropriate protection level.
     */
    private static boolean appropriate(org.acm.seguin.summary.MethodSummary methodSummary, java.lang.String name, int protection) {
        if (methodSummary.getName().equals(name)) {
            org.acm.seguin.pretty.ModifierHolder mods = methodSummary.getModifiers();
            if (protection == org.acm.seguin.summary.query.MethodQuery.PRIVATE) {
                return true;
            } else if ((protection == org.acm.seguin.summary.query.MethodQuery.DEFAULT) && (!mods.isPrivate())) {
                return true;
            } else if ((protection == org.acm.seguin.summary.query.MethodQuery.PROTECTED) && (mods.isPublic() || mods.isProtected())) {
                return true;
            } else if ((protection == org.acm.seguin.summary.query.MethodQuery.PUBLIC) && mods.isPublic()) {
                return true;
            }
        }
        return false;
    }
}