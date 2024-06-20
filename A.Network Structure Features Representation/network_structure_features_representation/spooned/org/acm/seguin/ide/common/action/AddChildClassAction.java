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
public class AddChildClassAction extends org.acm.seguin.ide.common.action.RefactoringAction {
    /**
     * Constructor for the AddChildClassAction object
     *
     * @param init
     * 		Description of Parameter
     */
    public AddChildClassAction(org.acm.seguin.ide.common.action.SelectedFileSet init) {
        super(init);
        initNames();
    }

    /**
     * Gets the Enabled attribute of the AddChildClassAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        return isSingleJavaFile();
    }

    /**
     * Description of the Method
     *
     * @param evt
     * 		Description of Parameter
     * @param typeSummaryArray
     * 		Description of Parameter
     */
    protected void activateListener(org.acm.seguin.summary.TypeSummary[] typeSummaryArray, java.awt.event.ActionEvent evt) {
        org.acm.seguin.uml.refactor.AddChildClassListener accl = new org.acm.seguin.uml.refactor.AddChildClassListener(null, typeSummaryArray[0], null, null);
        accl.actionPerformed(evt);
    }

    /**
     * Description of the Method
     */
    protected void initNames() {
        putValue(javax.swing.Action.NAME, "Add Child Class");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Add Child Class");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Allows the user to add a child class");
    }
}