/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
/**
 * Pushes a method into the parent class
 *
 * @author Chris Seguin
 */
public class PushDownMethodAction extends org.acm.seguin.ide.common.action.RefactoringAction {
    /**
     * Constructor for the PushDownMethodAction object
     */
    public PushDownMethodAction() {
        super(new org.acm.seguin.ide.common.action.EmptySelectedFileSet());
        putValue(javax.swing.Action.NAME, "Push Down Method");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Push Down Method");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Move a method into the child classes");
    }

    /**
     * Gets the Enabled attribute of the PushDownMethodAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        org.acm.seguin.ide.common.action.CurrentSummary cs = org.acm.seguin.ide.common.action.CurrentSummary.get();
        org.acm.seguin.summary.Summary summary = cs.getCurrentSummary();
        return (summary != null) && (summary instanceof org.acm.seguin.summary.MethodSummary);
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
        org.acm.seguin.summary.MethodSummary methodSummary = ((org.acm.seguin.summary.MethodSummary) (cs.getCurrentSummary()));
        org.acm.seguin.uml.refactor.PushDownMethodListener listener = new org.acm.seguin.uml.refactor.PushDownMethodListener(null, null, methodSummary, null, null);
        listener.actionPerformed(null);
    }
}