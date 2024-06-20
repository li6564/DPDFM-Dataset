/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Create a mouse listener for a type or a field or a title
 *
 * @author Chris Seguin
 * @created July 7, 1999
 */
public class TypeMetricsListener extends org.acm.seguin.uml.PopupMenuListener {
    // Instance Variables
    private org.acm.seguin.summary.TypeSummary typeSummary;

    /**
     * Constructor for the TypeMetricsListener object
     *
     * @param panel
     * 		Description of Parameter
     * @param initMenu
     * 		Description of Parameter
     * @param initItem
     * 		Description of Parameter
     */
    public TypeMetricsListener(javax.swing.JPanel panel, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
        if (panel instanceof org.acm.seguin.uml.UMLMethod) {
            typeSummary = ((org.acm.seguin.summary.TypeSummary) (((org.acm.seguin.uml.UMLMethod) (panel)).getSummary().getParent()));
        } else if (panel instanceof org.acm.seguin.uml.UMLField) {
            typeSummary = ((org.acm.seguin.summary.TypeSummary) (((org.acm.seguin.uml.UMLField) (panel)).getSummary().getParent()));
        } else if (panel instanceof org.acm.seguin.uml.UMLNestedType) {
            typeSummary = ((org.acm.seguin.summary.TypeSummary) (((org.acm.seguin.uml.UMLNestedType) (panel)).getSummary().getParent()));
        } else {
            typeSummary = ((org.acm.seguin.summary.TypeSummary) (((org.acm.seguin.uml.UMLType) (panel)).getSummary()));
        }
    }

    /**
     * A menu item has been selected
     *
     * @param evt
     * 		Description of Parameter
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        super.actionPerformed(evt);
        new org.acm.seguin.metrics.TypeMetricsFrame(typeSummary);
    }
}