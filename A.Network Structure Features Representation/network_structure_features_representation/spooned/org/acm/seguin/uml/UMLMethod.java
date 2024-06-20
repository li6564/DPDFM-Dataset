/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Displays a single UML method in a line
 *
 * @author Chris Seguin
 * @created July 6, 1999
 */
public class UMLMethod extends org.acm.seguin.uml.UMLLine implements org.acm.seguin.uml.ISourceful {
    // Instance Variables
    private org.acm.seguin.summary.MethodSummary summary;

    private org.acm.seguin.uml.UMLPackage current;

    /**
     * Create a new instance of a UMLLine
     *
     * @param initCurrent
     * 		Description of Parameter
     * @param parent
     * 		Description of Parameter
     * @param method
     * 		Description of Parameter
     * @param adapter
     * 		Description of Parameter
     */
    public UMLMethod(org.acm.seguin.uml.UMLPackage initCurrent, org.acm.seguin.uml.UMLType parent, org.acm.seguin.summary.MethodSummary method, org.acm.seguin.uml.line.DragPanelAdapter adapter) {
        super(parent, adapter);
        // Set the instance variables
        summary = method;
        current = initCurrent;
        // Reset the parent data
        org.acm.seguin.pretty.ModifierHolder modifiers = summary.getModifiers();
        setProtection(org.acm.seguin.uml.UMLLine.getProtectionCode(modifiers));
        setLabelText(summary.toString());
        setLabelFont(org.acm.seguin.uml.UMLLine.getProtectionFont(false, modifiers));
        // Reset the size
        setSize(getPreferredSize());
        // Add a mouse listener
        addMouseListener(new org.acm.seguin.uml.UMLMouseAdapter(current, parent, this));
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.summary.MethodSummary getSummary() {
        return summary;
    }

    public org.acm.seguin.summary.Summary getSourceSummary() {
        return summary;
    }
}