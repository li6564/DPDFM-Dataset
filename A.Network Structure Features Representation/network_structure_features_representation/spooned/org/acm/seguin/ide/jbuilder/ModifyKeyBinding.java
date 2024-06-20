/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
/**
 * Modifies the key bindings whenever the keymap changes
 *
 * @author Chris Seguin
 */
public class ModifyKeyBinding implements java.beans.PropertyChangeListener {
    private javax.swing.Action prettyPrint;

    private javax.swing.Action extractMethod;

    /**
     * Constructor for the ModifyKeyBinding object
     *
     * @param one
     * 		Description of Parameter
     * @param two
     * 		Description of Parameter
     */
    public ModifyKeyBinding(javax.swing.Action one, javax.swing.Action two) {
        prettyPrint = one;
        extractMethod = two;
        setHotKeys();
    }

    /**
     * The EditorManager will call this function anytime it fires a property
     *  change
     *
     * @param e
     * 		the event
     */
    public void propertyChange(java.beans.PropertyChangeEvent e) {
        java.lang.String propertyName = e.getPropertyName();
        // We are only interested in keymap changes
        if (propertyName.equals(EditorManager.keymapAttribute)) {
            setHotKeys();
        }
    }

    /**
     * Sets the HotKeys attribute of the ModifyKeyBinding object
     */
    private void setHotKeys() {
        javax.swing.text.Keymap keymap = org.acm.seguin.ide.jbuilder.EditorManager.getKeymap();
        if (keymap == null) {
            java.lang.System.out.println("No keymap");
            return;
        }
        javax.swing.KeyStroke stroke = ((javax.swing.KeyStroke) (prettyPrint.getValue(org.acm.seguin.ide.common.action.GenericAction.ACCELERATOR)));
        if (stroke != null) {
            keymap.addActionForKeyStroke(stroke, prettyPrint);
        }
        stroke = ((javax.swing.KeyStroke) (extractMethod.getValue(org.acm.seguin.ide.common.action.GenericAction.ACCELERATOR)));
        if (stroke != null) {
            keymap.addActionForKeyStroke(stroke, extractMethod);
        }
    }
}