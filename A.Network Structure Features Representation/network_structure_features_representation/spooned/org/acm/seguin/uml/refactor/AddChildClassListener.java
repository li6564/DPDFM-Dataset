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
public class AddChildClassListener extends org.acm.seguin.uml.refactor.DialogViewListener {
    private org.acm.seguin.uml.UMLPackage current;

    private org.acm.seguin.summary.TypeSummary typeSummary;

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
     */
    public AddChildClassListener(org.acm.seguin.uml.UMLPackage initPackage, org.acm.seguin.summary.TypeSummary initType, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
        current = initPackage;
        typeSummary = initType;
    }

    /**
     * Creates an appropriate dialog to prompt the user for additional input
     *
     * @return the dialog box
     */
    protected javax.swing.JDialog createDialog() {
        return new org.acm.seguin.uml.refactor.AddChildClassDialog(current, typeSummary);
    }
}