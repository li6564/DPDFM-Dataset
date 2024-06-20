/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Adds a child class listener
 *
 * @author Chris Seguin
 */
public class PushDownFieldListener extends org.acm.seguin.uml.refactor.DialogViewListener {
    private org.acm.seguin.uml.UMLPackage current;

    private org.acm.seguin.summary.TypeSummary typeSummary;

    private java.lang.String fieldName;

    /**
     * Constructor for the AddChildClassListener object
     *
     * @param initPackage
     * 		Description of Parameter
     * @param initType
     * 		Description of Parameter
     * @param initMenu
     * 		The popup menu
     * @param initItem
     * 		The current item
     * @param field
     * 		Description of Parameter
     */
    public PushDownFieldListener(org.acm.seguin.uml.UMLPackage initPackage, org.acm.seguin.summary.TypeSummary initType, org.acm.seguin.summary.FieldSummary field, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
        current = initPackage;
        typeSummary = initType;
        fieldName = field.getName();
        if (typeSummary == null) {
            typeSummary = ((org.acm.seguin.summary.TypeSummary) (field.getParent()));
        }
    }

    /**
     * Creates an appropriate dialog to prompt the user for additional input
     *
     * @return the dialog box
     */
    protected javax.swing.JDialog createDialog() {
        return new org.acm.seguin.uml.refactor.PushDownFieldDialog(current, typeSummary, fieldName);
    }
}