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
public class RenameClassAction extends org.acm.seguin.ide.common.action.RefactoringAction {
    /**
     * Constructor for the RenameClassAction object
     *
     * @param init
     * 		Description of Parameter
     */
    public RenameClassAction(org.acm.seguin.ide.common.action.SelectedFileSet init) {
        super(init);
        initNames();
    }

    /**
     * Gets the Enabled attribute of the RenameClassAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        return isSingleJavaFile();
    }

    /**
     * The listener to activate with the specified types
     *
     * @param typeSummaryArray
     * 		Description of Parameter
     * @param evt
     * 		Description of Parameter
     */
    protected void activateListener(org.acm.seguin.summary.TypeSummary[] typeSummaryArray, java.awt.event.ActionEvent evt) {
        org.acm.seguin.uml.refactor.AddRenameClassListener rcl = new org.acm.seguin.uml.refactor.AddRenameClassListener(null, typeSummaryArray[0], null, null);
        rcl.actionPerformed(evt);
    }

    /**
     * Description of the Method
     */
    protected void initNames() {
        putValue(javax.swing.Action.NAME, "Rename Class");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Rename Class");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Allows the user to rename the class");
    }
}