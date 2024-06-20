/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Displays a single UML nested type in a line
 *
 * @author Chris Seguin
 * @created July 6, 1999
 */
public class UMLNestedType extends org.acm.seguin.uml.UMLLine implements org.acm.seguin.uml.ISourceful {
    // Instance Variables
    private org.acm.seguin.summary.TypeSummary summary;

    private org.acm.seguin.uml.UMLPackage current;

    /**
     * Create a new instance of a UMLLine
     *
     * @param initCurrent
     * 		Description of Parameter
     * @param parent
     * 		Description of Parameter
     * @param nestedType
     * 		Description of Parameter
     * @param adapter
     * 		Description of Parameter
     */
    public UMLNestedType(org.acm.seguin.uml.UMLPackage initCurrent, org.acm.seguin.uml.UMLType parent, org.acm.seguin.summary.TypeSummary nestedType, org.acm.seguin.uml.line.DragPanelAdapter adapter) {
        super(parent, adapter);
        // Set the instance variables
        summary = nestedType;
        current = initCurrent;
        // Reset the parent data
        org.acm.seguin.pretty.ModifierHolder modifiers = summary.getModifiers();
        setLabelText(summary.toString());
        setLabelFont(org.acm.seguin.uml.UMLLine.getProtectionFont(false, modifiers));
        // Reset the size
        setSize(getPreferredSize());
        // Add a mouse listener
        addMouseListener(new org.acm.seguin.uml.UMLMouseAdapter(current, parent, this));
        if (summary.isInterface()) {
            icon = new org.acm.seguin.uml.InterfaceIcon(8, 8);
        } else {
            icon = new org.acm.seguin.uml.ClassIcon(8, 8);
        }
    }

    /**
     * Return the summary
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.summary.TypeSummary getSummary() {
        return summary;
    }

    /**
     * Gets the SourceSummary attribute of the UMLNestedType object
     *
     * @return The SourceSummary value
     */
    public org.acm.seguin.summary.Summary getSourceSummary() {
        return summary;
    }
}