/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
/**
 * Performs the undo operation
 *
 * @author Chris Seguin
 */
public class UndoAction extends org.acm.seguin.ide.common.UndoAdapter implements javax.swing.Action {
    private java.beans.PropertyChangeSupport support;

    private java.util.HashMap values;

    private boolean enabled;

    /**
     * Constructor for the UndoAction object
     */
    public UndoAction() {
        support = new java.beans.PropertyChangeSupport(this);
        values = new java.util.HashMap();
        enabled = true;
        putValue(javax.swing.Action.NAME, "Undo");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Undo Refactoring");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Undoes the last refactoring");
    }

    /**
     * Sets the Enabled attribute of the PrettyPrinterAction object
     *
     * @param value
     * 		The new Enabled value
     */
    public void setEnabled(boolean value) {
        enabled = value;
    }

    /**
     * Gets the Value attribute of the PrettyPrinterAction object
     *
     * @param key
     * 		Description of Parameter
     * @return The Value value
     */
    public java.lang.Object getValue(java.lang.String key) {
        return values.get(key);
    }

    /**
     * Gets the Enabled attribute of the PrettyPrinterAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        if (!enabled) {
            return false;
        }
        return !org.acm.seguin.refactor.undo.UndoStack.get().isStackEmpty();
    }

    /**
     * Sets the Value attribute of the PrettyPrinterAction object
     *
     * @param key
     * 		The new key value
     * @param value
     * 		The new value value
     */
    public void putValue(java.lang.String key, java.lang.Object value) {
        java.lang.Object oldValue = getValue(key);
        java.lang.Object newValue = value;
        support.firePropertyChange(key, oldValue, newValue);
        values.put(key, value);
    }

    /**
     * Adds a feature to the PropertyChangeListener attribute of the
     *  PrettyPrinterAction object
     *
     * @param listener
     * 		The feature to be added to the PropertyChangeListener
     * 		attribute
     */
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a listener
     *
     * @param listener
     * 		the listener to be removed
     */
    public void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}