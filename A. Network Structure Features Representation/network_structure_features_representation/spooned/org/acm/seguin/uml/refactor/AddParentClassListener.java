/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Adds an abstract parent listener
 *
 * @author Chris Seguin
 */
public class AddParentClassListener extends org.acm.seguin.uml.refactor.DialogViewListener {
    private org.acm.seguin.uml.UMLPackage current;

    private org.acm.seguin.summary.TypeSummary[] typeArray;

    /**
     * Constructor for the AddParentClassListener object
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
    public AddParentClassListener(org.acm.seguin.uml.UMLPackage initPackage, org.acm.seguin.summary.TypeSummary[] initTypes, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
        current = initPackage;
        typeArray = initTypes;
    }

    /**
     * Creates an appropriate dialog to prompt the user for additional input
     *
     * @return the dialog box
     */
    protected javax.swing.JDialog createDialog() {
        return new org.acm.seguin.uml.refactor.AddAbstractParentDialog(current, typeArray);
    }
}