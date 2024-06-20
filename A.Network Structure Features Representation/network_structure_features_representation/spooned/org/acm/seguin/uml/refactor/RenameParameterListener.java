/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Menu listener that invokes the refactoring dialog for renaming a parameter
 *
 * @author Chris Seguin
 */
public class RenameParameterListener extends org.acm.seguin.uml.refactor.DialogViewListener {
    private org.acm.seguin.summary.ParameterSummary param;

    private org.acm.seguin.summary.MethodSummary method;

    private org.acm.seguin.uml.UMLPackage packageDiagram;

    /**
     * Constructor for the RenameParameterListener object
     *
     * @param initMenu
     * 		Description of Parameter
     * @param initItem
     * 		Description of Parameter
     * @param initPackage
     * 		Description of Parameter
     * @param init
     * 		Description of Parameter
     */
    public RenameParameterListener(javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem, org.acm.seguin.uml.UMLPackage initPackage, org.acm.seguin.summary.ParameterSummary init) {
        super(initMenu, initItem);
        param = init;
        packageDiagram = initPackage;
        method = null;
    }

    /**
     * Constructor for the RenameParameterListener object
     *
     * @param init
     * 		Description of Parameter
     */
    public RenameParameterListener(org.acm.seguin.summary.MethodSummary init) {
        super(null, null);
        param = null;
        packageDiagram = null;
        method = init;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    protected javax.swing.JDialog createDialog() {
        if (param == null) {
            return new org.acm.seguin.uml.refactor.RenameParameterDialog(packageDiagram, method);
        } else {
            return new org.acm.seguin.uml.refactor.RenameParameterDialog(packageDiagram, param);
        }
    }
}