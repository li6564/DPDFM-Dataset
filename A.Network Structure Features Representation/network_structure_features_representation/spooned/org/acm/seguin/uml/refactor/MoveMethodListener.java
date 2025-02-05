/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Listener for the move method menu item
 *
 * @author Chris Seguin
 */
public class MoveMethodListener extends org.acm.seguin.uml.refactor.DialogViewListener {
    private org.acm.seguin.uml.UMLPackage current;

    private org.acm.seguin.summary.TypeSummary typeSummary;

    private org.acm.seguin.summary.MethodSummary methodSummary;

    /**
     * Constructor for the MoveMethodListener object
     *
     * @param initPackage
     * 		Description of Parameter
     * @param initType
     * 		Description of Parameter
     * @param initMenu
     * 		The popup menu
     * @param initItem
     * 		The current item
     * @param method
     * 		Description of Parameter
     */
    public MoveMethodListener(org.acm.seguin.uml.UMLPackage initPackage, org.acm.seguin.summary.TypeSummary initType, org.acm.seguin.summary.MethodSummary method, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
        current = initPackage;
        typeSummary = initType;
        methodSummary = method;
        if (typeSummary == null) {
            typeSummary = ((org.acm.seguin.summary.TypeSummary) (methodSummary.getParent()));
        }
    }

    /**
     * Creates an appropriate dialog to prompt the user for additional input
     *
     * @return the dialog box
     */
    protected javax.swing.JDialog createDialog() {
        return new org.acm.seguin.uml.refactor.MoveMethodDialog(current, methodSummary);
    }
}