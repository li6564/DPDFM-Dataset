/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.node.Node;
import com.borland.primetime.node.TextFileNode;
import com.borland.primetime.vfs.Buffer;
/**
 * Pretty printer action button
 *
 * @author Chris Seguin
 */
public class PrintAction extends org.acm.seguin.ide.common.TextPrinter implements javax.swing.Action {
    private java.beans.PropertyChangeSupport support;

    private java.util.HashMap values;

    private boolean enabled;

    /**
     * Constructor for the PrintAction object
     */
    public PrintAction() {
        support = new java.beans.PropertyChangeSupport(this);
        values = new java.util.HashMap();
        enabled = true;
        putValue(javax.swing.Action.NAME, "Print");
        putValue(javax.swing.Action.SHORT_DESCRIPTION, "Print");
        putValue(javax.swing.Action.LONG_DESCRIPTION, "Prints the current file");
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
        com.borland.primetime.node.Node active = getActiveNode();
        return (active instanceof com.borland.primetime.node.TextFileNode) || (active instanceof org.acm.seguin.ide.jbuilder.UMLNode);
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

    /**
     * The pretty printer action
     *
     * @param evt
     * 		the action that occurred
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        com.borland.primetime.node.Node active = getActiveNode();
        if (active instanceof com.borland.primetime.node.TextFileNode) {
            // Get the data from the window
            java.lang.String windowText = getStringFromIDE();
            java.lang.String fullFilename = getFilenameFromIDE();
            print(fullFilename, windowText);
        } else if (active instanceof org.acm.seguin.ide.jbuilder.UMLNode) {
            org.acm.seguin.ide.jbuilder.UMLNode node = ((org.acm.seguin.ide.jbuilder.UMLNode) (active));
            new org.acm.seguin.uml.print.PrintingThread(node.getDiagram()).start();
        }
    }

    /**
     * Gets the initial string from the IDE
     *
     * @return The file in string format
     */
    protected java.lang.String getFilenameFromIDE() {
        com.borland.primetime.node.Node active = getActiveNode();
        if (active instanceof com.borland.primetime.node.TextFileNode) {
            com.borland.primetime.node.TextFileNode jtn = ((com.borland.primetime.node.TextFileNode) (active));
            return jtn.getDisplayName();
        }
        return "Unknown filename";
    }

    /**
     * Gets the initial string from the IDE
     *
     * @return The file in string format
     */
    protected java.lang.String getStringFromIDE() {
        com.borland.primetime.node.Node active = getActiveNode();
        if (active instanceof com.borland.primetime.node.TextFileNode) {
            com.borland.primetime.node.TextFileNode jtn = ((com.borland.primetime.node.TextFileNode) (active));
            try {
                com.borland.primetime.vfs.Buffer buffer = jtn.getBuffer();
                byte[] contents = buffer.getContent();
                return new java.lang.String(contents);
            } catch (java.io.IOException ioex) {
                ioex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Gets the ActiveNode attribute of the PrintAction object
     *
     * @return The ActiveNode value
     */
    private com.borland.primetime.node.Node getActiveNode() {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        return browser.getActiveNode();
    }
}