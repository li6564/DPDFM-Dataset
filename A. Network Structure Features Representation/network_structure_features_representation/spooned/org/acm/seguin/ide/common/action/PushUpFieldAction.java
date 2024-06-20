/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
/**
 * Pushes a field into the parent class
 *
 * @author Chris Seguin
 */
public class PushUpFieldAction extends org.acm.seguin.ide.common.action.RefactoringAction {
    /**
     * Constructor for the PushUpFieldAction object
     */
    public PushUpFieldAction() {
        super(new org.acm.seguin.ide.common.action.EmptySelectedFileSet());
        putValue(javax.swing.Action.NAME, "Push Up Field");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Push Up Field");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Move a field into the parent class");
    }

    /**
     * Gets the Enabled attribute of the PushUpFieldAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        org.acm.seguin.ide.common.action.CurrentSummary cs = org.acm.seguin.ide.common.action.CurrentSummary.get();
        org.acm.seguin.summary.Summary summary = cs.getCurrentSummary();
        return (summary != null) && (summary instanceof org.acm.seguin.summary.FieldSummary);
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
        org.acm.seguin.summary.FieldSummary fieldSummary = ((org.acm.seguin.summary.FieldSummary) (cs.getCurrentSummary()));
        org.acm.seguin.uml.refactor.PushUpFieldListener pufl = new org.acm.seguin.uml.refactor.PushUpFieldListener(null, null, fieldSummary, null, null);
        pufl.actionPerformed(null);
    }
}