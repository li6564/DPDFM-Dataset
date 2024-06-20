/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class ExtractInterfaceAction extends org.acm.seguin.ide.common.action.RefactoringAction {
    /**
     * Constructor for the ExtractInterfaceAction object
     *
     * @param init
     * 		Description of Parameter
     */
    public ExtractInterfaceAction(org.acm.seguin.ide.common.action.SelectedFileSet init) {
        super(init);
        initNames();
    }

    /**
     * Gets the Enabled attribute of the ExtractInterfaceAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        return isAllJava();
    }

    /**
     * Description of the Method
     *
     * @param typeSummaryArray
     * 		Description of Parameter
     * @param evt
     * 		Description of Parameter
     */
    protected void activateListener(org.acm.seguin.summary.TypeSummary[] typeSummaryArray, java.awt.event.ActionEvent evt) {
        org.acm.seguin.uml.refactor.ExtractInterfaceListener eil = new org.acm.seguin.uml.refactor.ExtractInterfaceListener(null, typeSummaryArray, null, null);
        eil.actionPerformed(evt);
    }

    /**
     * Description of the Method
     */
    protected void initNames() {
        putValue(javax.swing.Action.NAME, "Extract Interface");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Extract Interface");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Allows the user to extract an interface");
    }
}