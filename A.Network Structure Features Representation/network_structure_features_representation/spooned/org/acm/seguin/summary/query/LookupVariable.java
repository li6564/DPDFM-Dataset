/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Performs a local variable lookup
 *
 * @author Chris Seguin
 */
public class LookupVariable {
    /**
     * Looks up the variable
     *
     * @param method
     * 		the method summary
     * @param name
     * 		the name of the variable to find
     * @return the variable summary if found or null otherwise
     */
    public static org.acm.seguin.summary.VariableSummary query(org.acm.seguin.summary.MethodSummary method, java.lang.String name) {
        org.acm.seguin.summary.VariableSummary result = org.acm.seguin.summary.query.LookupVariable.getLocal(method, name);
        if (result != null) {
            return result;
        }
        org.acm.seguin.summary.TypeSummary currentType = ((org.acm.seguin.summary.TypeSummary) (method.getParent()));
        return org.acm.seguin.summary.query.LookupVariable.queryFieldSummary(currentType, name);
    }

    /**
     * Get a field summary
     *
     * @param currentType
     * 		the type to search in
     * @param name
     * 		the name of the field
     * @return the field summary found or null if none was found
     */
    public static org.acm.seguin.summary.VariableSummary queryFieldSummary(org.acm.seguin.summary.TypeSummary currentType, java.lang.String name) {
        org.acm.seguin.summary.VariableSummary result = org.acm.seguin.summary.query.LookupVariable.getField(currentType, name, true);
        if (result != null) {
            return result;
        }
        org.acm.seguin.summary.TypeDeclSummary parentType = currentType.getParentClass();
        currentType = org.acm.seguin.summary.query.GetTypeSummary.query(parentType);
        while (currentType != null) {
            result = org.acm.seguin.summary.query.LookupVariable.getField(currentType, name, false);
            if (result != null) {
                return result;
            }
        } 
        return null;
    }

    /**
     * Finds a field in a type summary
     *
     * @param type
     * 		the type to search
     * @param name
     * 		the name of the variable
     * @param isPrivateAllowed
     * 		is the field allowed to be private
     * @return The FieldSummary if found, null otherwise
     */
    private static org.acm.seguin.summary.VariableSummary getField(org.acm.seguin.summary.TypeSummary type, java.lang.String name, boolean isPrivateAllowed) {
        java.util.Iterator iter = type.getFields();
        if (iter == null) {
            return null;
        }
        while (iter.hasNext()) {
            org.acm.seguin.summary.FieldSummary next = ((org.acm.seguin.summary.FieldSummary) (iter.next()));
            if ((!isPrivateAllowed) || next.getModifiers().isPrivate()) {
                if (next.getName().equals(name)) {
                    return next;
                }
            }
        } 
        return null;
    }

    /**
     * Looks up the variable inside the method
     *
     * @param method
     * 		the method summary
     * @param name
     * 		the name of the variable to find
     * @return the variable summary if found or null otherwise
     */
    public static org.acm.seguin.summary.VariableSummary getLocal(org.acm.seguin.summary.MethodSummary method, java.lang.String name) {
        java.util.Iterator iter = method.getParameters();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.ParameterSummary param = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
                if (param.getName().equals(name)) {
                    return param;
                }
            } 
        }
        iter = method.getDependencies();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                if ((next instanceof org.acm.seguin.summary.LocalVariableSummary) && next.getName().equals(name)) {
                    return ((org.acm.seguin.summary.VariableSummary) (next));
                }
            } 
        }
        return null;
    }
}