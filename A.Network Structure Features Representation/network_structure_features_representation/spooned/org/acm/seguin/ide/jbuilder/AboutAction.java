/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
/**
 * Pretty printer action button
 *
 * @author Chris Seguin
 */
public class AboutAction extends org.acm.seguin.ide.jbuilder.JBuilderAction {
    /**
     * Constructor for the AboutAction object
     */
    public AboutAction() {
        putValue(org.acm.seguin.ide.jbuilder.NAME, "About");
        putValue(org.acm.seguin.ide.jbuilder.SHORT_DESCRIPTION, "About JRefactory");
        putValue(org.acm.seguin.ide.jbuilder.LONG_DESCRIPTION, "Shows the about box");
    }

    /**
     * Determines if this menu item should be enabled
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        return true;
    }

    /**
     * The pretty printer action
     *
     * @param evt
     * 		the action that occurred
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        org.acm.seguin.awt.AboutBox.run();
    }
}