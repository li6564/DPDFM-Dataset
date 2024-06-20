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
public class PackageMetricsListener extends org.acm.seguin.uml.PopupMenuListener {
    // Instance Variables
    private org.acm.seguin.summary.PackageSummary packageSummary;

    /**
     * Constructor for the PackageMetricsListener object
     *
     * @param panel
     * 		Description of Parameter
     * @param initMenu
     * 		Description of Parameter
     * @param initItem
     * 		Description of Parameter
     */
    public PackageMetricsListener(org.acm.seguin.uml.UMLPackage panel, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
        packageSummary = panel.getSummary();
    }

    /**
     * A menu item has been selected
     *
     * @param evt
     * 		Description of Parameter
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        super.actionPerformed(evt);
        new org.acm.seguin.metrics.PackageMetricsFrame(packageSummary);
    }
}