/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.node.Node;
/**
 * Menu item to create a JPG file
 *
 * @author Chris Seguin
 */
public class JPGFileAction extends org.acm.seguin.ide.jbuilder.JBuilderAction {
    /**
     * Constructor for the JPGFileAction object
     */
    public JPGFileAction() {
        putValue(org.acm.seguin.ide.jbuilder.NAME, "JPG File");
        putValue(org.acm.seguin.ide.jbuilder.SHORT_DESCRIPTION, "JPG File");
        putValue(org.acm.seguin.ide.jbuilder.LONG_DESCRIPTION, "Creates a JPG file from the UML diagram");
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
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        com.borland.primetime.node.Node active = browser.getActiveNode();
        return active instanceof org.acm.seguin.ide.jbuilder.UMLNode;
    }

    /**
     * Saves the UML diagram as a JPG file
     *
     * @param evt
     * 		the action event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        org.acm.seguin.ide.jbuilder.UMLNode active = ((org.acm.seguin.ide.jbuilder.UMLNode) (browser.getActiveNode()));
        org.acm.seguin.uml.UMLPackage diagram = active.getDiagram();
        org.acm.seguin.uml.jpg.SaveAdapter adapter = new org.acm.seguin.uml.jpg.SaveAdapter(diagram);
        adapter.actionPerformed(evt);
    }
}