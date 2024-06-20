/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
/**
 * Pushes a field into the child classes
 *
 * @author Chris Seguin
 */
public class RenameFieldAction extends org.acm.seguin.ide.common.action.RefactoringAction {
    /**
     * Constructor for the RenameFieldAction object
     */
    public RenameFieldAction() {
        super(new org.acm.seguin.ide.common.action.EmptySelectedFileSet());
        putValue(javax.swing.Action.NAME, "Rename Field");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Rename Field");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Rename a field");
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
        org.acm.seguin.uml.refactor.RenameFieldListener pdfl = new org.acm.seguin.uml.refactor.RenameFieldListener(null, fieldSummary, null, null);
        pdfl.actionPerformed(null);
    }
}