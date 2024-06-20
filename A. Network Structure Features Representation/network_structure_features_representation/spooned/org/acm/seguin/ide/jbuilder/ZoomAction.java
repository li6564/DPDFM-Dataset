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
 * Zooms in on a diagram by the specified factor
 *
 * @author Chris Seguin
 */
public class ZoomAction extends org.acm.seguin.ide.jbuilder.JBuilderAction {
    private double scalingFactor;

    /**
     * Constructor for the ZoomAction object
     *
     * @param init
     * 		Description of Parameter
     */
    public ZoomAction(double init) {
        scalingFactor = init;
        int text = ((int) (scalingFactor * 100));
        putValue(org.acm.seguin.ide.jbuilder.NAME, ("" + text) + "%");
        putValue(org.acm.seguin.ide.jbuilder.SHORT_DESCRIPTION, ("" + text) + "%");
        putValue(org.acm.seguin.ide.jbuilder.LONG_DESCRIPTION, ("Zooms in on a UML diagram to " + text) + "% of the full diagram");
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
     * Updates the diagram when the user zooms to this level
     *
     * @param evt
     * 		the action event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        org.acm.seguin.ide.jbuilder.UMLNode active = ((org.acm.seguin.ide.jbuilder.UMLNode) (browser.getActiveNode()));
        org.acm.seguin.uml.UMLPackage diagram = active.getDiagram();
        diagram.scale(scalingFactor);
        diagram.repaint();
    }
}