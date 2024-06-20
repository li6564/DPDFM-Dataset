/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Create a mouse listener for a method or a field or a title
 *
 * @author Chris Seguin
 * @created July 7, 1999
 */
public class MethodMetricsListener extends org.acm.seguin.uml.PopupMenuListener {
    // Instance Variables
    private org.acm.seguin.summary.MethodSummary methodSummary;

    /**
     * Constructor for the MethodMetricsListener object
     *
     * @param initSummary
     * 		Description of Parameter
     * @param initMenu
     * 		Description of Parameter
     * @param initItem
     * 		Description of Parameter
     */
    public MethodMetricsListener(org.acm.seguin.summary.MethodSummary initSummary, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
        methodSummary = initSummary;
    }

    /**
     * A menu item has been selected
     *
     * @param evt
     * 		Description of Parameter
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        super.actionPerformed(evt);
        new org.acm.seguin.metrics.MethodMetricsFrame(methodSummary);
    }
}