/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Converts between associations and attributes
 *
 * @author Chris Seguin
 * @created August 17, 1999
 */
public class ConvertAdapter implements java.awt.event.ActionListener {
    private org.acm.seguin.uml.UMLPackage packagePanel;

    private org.acm.seguin.uml.UMLType typePanel;

    private org.acm.seguin.uml.UMLField fieldPanel;

    /**
     * Constructor for the ConvertAdapter object
     *
     * @param packagePanel
     * 		the package panel
     * @param fieldPanel
     * 		the field panel
     */
    public ConvertAdapter(org.acm.seguin.uml.UMLPackage packagePanel, org.acm.seguin.uml.UMLField fieldPanel) {
        this.packagePanel = packagePanel;
        this.fieldPanel = fieldPanel;
        typePanel = packagePanel.findType(((org.acm.seguin.summary.TypeSummary) (fieldPanel.getSummary().getParent())));
    }

    /**
     * Menu item is selected
     *
     * @param ev
     * 		selection event
     */
    public void actionPerformed(java.awt.event.ActionEvent ev) {
        if (fieldPanel.isAssociation()) {
            fieldPanel.setAssociation(false);
            typePanel.convertToAttribute(packagePanel, fieldPanel);
        } else {
            fieldPanel.setAssociation(true);
            typePanel.convertToAssociation(packagePanel, fieldPanel);
        }
    }
}