/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Finds a field associated with a particular type summary. A permission is
 *  also specified to insure that we find something interesting.
 *
 * @author Chris Seguin
 */
public class FieldQuery {
    /**
     * The field we are looking for can have any protection level
     */
    public static final int PRIVATE = 1;

    /**
     * The field we are looking for must have default protection level or higher
     */
    public static final int DEFAULT = 2;

    /**
     * The field we are looking for must have protected protection level or
     *  higher
     */
    public static final int PROTECTED = 3;

    /**
     * The field we are looking for must be public
     */
    public static final int PUBLIC = 4;

    /**
     * Finds an associated field
     *
     * @param typeSummary
     * 		the type
     * @param name
     * 		the name
     * @return the field found or null if none
     */
    public static org.acm.seguin.summary.FieldSummary find(org.acm.seguin.summary.TypeSummary typeSummary, java.lang.String name) {
        org.acm.seguin.summary.FieldSummary result = org.acm.seguin.summary.query.FieldQuery.query(typeSummary, name, org.acm.seguin.summary.query.FieldQuery.PRIVATE);
        if (result == null) {
            result = org.acm.seguin.summary.query.FieldQuery.queryAncestors(typeSummary, name, org.acm.seguin.summary.query.FieldQuery.PROTECTED);
        }
        return result;
    }

    /**
     * Finds the field associated with a type
     *
     * @param typeSummary
     * 		the type to search
     * @param name
     * 		the name of the field
     * @param protection
     * 		the minimum protection level
     * @return the field summary if one is found, null if none is
    found
     */
    public static org.acm.seguin.summary.FieldSummary query(org.acm.seguin.summary.TypeSummary typeSummary, java.lang.String name, int protection) {
        java.util.Iterator iter = typeSummary.getFields();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.FieldSummary next = ((org.acm.seguin.summary.FieldSummary) (iter.next()));
                if (org.acm.seguin.summary.query.FieldQuery.appropriate(next, name, protection)) {
                    return next;
                }
            } 
        }
        return null;
    }

    /**
     * Finds the field associated with a type in the ancestors of that type
     *
     * @param typeSummary
     * 		the type to search
     * @param name
     * 		the name of the field
     * @param protection
     * 		the minimum protection level
     * @return the field summary if one is found, null if none is
    found
     */
    public static org.acm.seguin.summary.FieldSummary queryAncestors(org.acm.seguin.summary.TypeSummary typeSummary, java.lang.String name, int protection) {
        org.acm.seguin.summary.TypeDeclSummary next = typeSummary.getParentClass();
        org.acm.seguin.summary.TypeSummary current = org.acm.seguin.summary.query.GetTypeSummary.query(next);
        while (current != null) {
            org.acm.seguin.summary.FieldSummary attempt = org.acm.seguin.summary.query.FieldQuery.query(current, name, protection);
            if (attempt != null) {
                return attempt;
            }
            next = current.getParentClass();
            current = org.acm.seguin.summary.query.GetTypeSummary.query(next);
        } 
        return null;
    }

    /**
     * Checks if the field we are considering is the correct type
     *
     * @param fieldSummary
     * 		the summary of the field
     * @param name
     * 		the name of the field
     * @param protection
     * 		the protection level of the field
     * @return true if the field has the appropriate name and the
    appropriate protection level.
     */
    private static boolean appropriate(org.acm.seguin.summary.FieldSummary fieldSummary, java.lang.String name, int protection) {
        if (fieldSummary.getName().equals(name)) {
            org.acm.seguin.pretty.ModifierHolder mods = fieldSummary.getModifiers();
            if (protection == org.acm.seguin.summary.query.FieldQuery.PRIVATE) {
                return true;
            } else if ((protection == org.acm.seguin.summary.query.FieldQuery.DEFAULT) && (!mods.isPrivate())) {
                return true;
            } else if ((protection == org.acm.seguin.summary.query.FieldQuery.PROTECTED) && (mods.isPublic() || mods.isProtected())) {
                return true;
            } else if ((protection == org.acm.seguin.summary.query.FieldQuery.PUBLIC) && mods.isPublic()) {
                return true;
            }
        }
        return false;
    }
}