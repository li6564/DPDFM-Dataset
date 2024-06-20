/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * This class allows the user to select a series of classes in the UML class
 *  diagram and this converts the selected classes into an array of
 *  TypeSummary objects. This will allow us to decouple the user interface
 *  dialog boxes for entering information about the refactoring from the UML
 *  class diagrams.
 *
 * @author Chris Seguin
 */
public class SelectedSummaryList {
    /**
     * Get the selected type summaries
     *
     * @param umlPackage
     * 		the package
     * @param umlType
     * 		the type
     * @return the list of type summaruies
     */
    public static org.acm.seguin.summary.TypeSummary[] list(org.acm.seguin.uml.UMLPackage umlPackage, org.acm.seguin.uml.UMLType umlType) {
        org.acm.seguin.summary.TypeSummary[] result;
        // Add the types to the result array
        if (umlType.isSelected()) {
            result = org.acm.seguin.uml.SelectedSummaryList.getSelectedTypes(umlPackage);
        } else {
            result = new org.acm.seguin.summary.TypeSummary[1];
            result[0] = umlType.getSummary();
        }
        // Return the result array
        return result;
    }

    /**
     * Gets the SelectedTypes attribute of the SelectedSummaryList class
     *
     * @param umlPackage
     * 		Description of Parameter
     * @return The SelectedTypes value
     */
    private static org.acm.seguin.summary.TypeSummary[] getSelectedTypes(org.acm.seguin.uml.UMLPackage umlPackage) {
        org.acm.seguin.summary.TypeSummary[] result;
        int count = 0;
        org.acm.seguin.uml.UMLType[] types = umlPackage.getTypes();
        for (int ndx = 0; ndx < types.length; ndx++) {
            if (types[ndx].isSelected()) {
                count++;
            }
        }
        result = new org.acm.seguin.summary.TypeSummary[count];
        count = 0;
        for (int ndx = 0; ndx < types.length; ndx++) {
            if (types[ndx].isSelected()) {
                result[count] = types[ndx].getSummary();
                count++;
            }
        }
        return result;
    }
}