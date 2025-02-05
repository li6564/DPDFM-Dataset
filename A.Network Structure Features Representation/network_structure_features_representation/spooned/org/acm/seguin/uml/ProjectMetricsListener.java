/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Create a mouse listener for a package or a field or a title
 *
 * @author Chris Seguin
 * @created July 27, 1999
 */
public class ProjectMetricsListener extends org.acm.seguin.uml.PopupMenuListener {
    /**
     * Constructor for the ProjectMetricsListener object
     *
     * @param initMenu
     * 		Description of Parameter
     * @param initItem
     * 		Description of Parameter
     */
    public ProjectMetricsListener(javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
    }

    /**
     * A menu item has been selected
     *
     * @param evt
     * 		Description of Parameter
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        super.actionPerformed(evt);
        new org.acm.seguin.metrics.ProjectMetricsFrame();
    }
}