/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
/**
 * Pretty printer action button
 *
 * @author Chris Seguin
 */
public class PrettyPrinterAction extends org.acm.seguin.ide.common.action.GenericAction {
    /**
     * Constructor for the PrettyPrinterAction object
     */
    public PrettyPrinterAction() {
        putValue(javax.swing.Action.NAME, "Pretty Printer");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Pretty Printer");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Reindents java source file\n" + "Adds intelligent javadoc comments");
        putValue(org.acm.seguin.ide.common.action.GenericAction.ACCELERATOR, javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.Event.CTRL_MASK | java.awt.Event.SHIFT_MASK));
    }

    /**
     * Determines if this menu item should be enabled
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        if (!enabled) {
            return false;
        }
        return org.acm.seguin.ide.common.EditorOperations.get().isJavaFile();
    }

    /**
     * The pretty printer action
     *
     * @param evt
     * 		the action that occurred
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        org.acm.seguin.ide.common.action.GenericPrettyPrinter jbpp = new org.acm.seguin.ide.common.action.GenericPrettyPrinter();
        jbpp.prettyPrintCurrentWindow();
        org.acm.seguin.ide.common.action.CurrentSummary.get().reset();
    }
}