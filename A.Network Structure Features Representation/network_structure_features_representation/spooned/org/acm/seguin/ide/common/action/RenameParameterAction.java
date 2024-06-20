/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
/**
 * Rename a parameter of a method
 *
 * @author Chris Seguin
 */
public class RenameParameterAction extends org.acm.seguin.ide.common.action.RefactoringAction {
    /**
     * Constructor for the RenameParameterAction object
     */
    public RenameParameterAction() {
        super(new org.acm.seguin.ide.common.action.EmptySelectedFileSet());
        putValue(javax.swing.Action.NAME, "Rename Parameter");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Rename Parameter");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Rename a parameter of the method");
    }

    /**
     * Gets the Enabled attribute of the PushUpMethodAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        org.acm.seguin.ide.common.action.CurrentSummary cs = org.acm.seguin.ide.common.action.CurrentSummary.get();
        org.acm.seguin.summary.Summary summary = cs.getCurrentSummary();
        return ((summary != null) && (summary instanceof org.acm.seguin.summary.MethodSummary)) && (((org.acm.seguin.summary.MethodSummary) (summary)).getParameterCount() > 0);
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
        org.acm.seguin.ide.common.action.CurrentSummary cs = org.acm.seguin.ide.common.action.CurrentSummary.get();
        org.acm.seguin.summary.Summary summary = cs.getCurrentSummary();
        org.acm.seguin.uml.refactor.RenameParameterListener rpl = new org.acm.seguin.uml.refactor.RenameParameterListener(((org.acm.seguin.summary.MethodSummary) (summary)));
        rpl.actionPerformed(null);
    }
}