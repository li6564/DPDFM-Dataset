/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
/**
 * Performs the extract method action
 *
 * @author Chris Seguin
 */
public class ExtractMethodAction extends org.acm.seguin.ide.common.action.GenericAction {
    /**
     * Constructor for the ExtractMethodAction object
     */
    public ExtractMethodAction() {
        super();
        putValue(javax.swing.Action.NAME, "Extract Method");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Extract Method");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Highlight the code to extract and select this menu option");
        putValue(org.acm.seguin.ide.common.action.GenericAction.ACCELERATOR, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.Event.CTRL_MASK | java.awt.Event.SHIFT_MASK));
    }

    /**
     * Gets the Enabled attribute of the ExtractMethodAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        return org.acm.seguin.ide.common.EditorOperations.get().isJavaFile();
    }

    /**
     * What to do when someone selects the extract method refactoring
     *
     * @param e
     * 		the button event
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        try {
            new org.acm.seguin.ide.common.action.GenericExtractMethod().show();
        } catch (org.acm.seguin.refactor.RefactoringException re) {
            javax.swing.JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        org.acm.seguin.ide.common.action.CurrentSummary.get().reset();
    }
}