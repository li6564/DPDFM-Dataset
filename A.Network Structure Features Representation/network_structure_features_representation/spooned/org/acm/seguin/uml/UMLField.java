/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Displays a single UML field in a line
 *
 * @author Chris Seguin
 * @created July 6, 1999
 */
public class UMLField extends org.acm.seguin.uml.UMLLine implements org.acm.seguin.uml.ISourceful {
    // Instance Variables
    private org.acm.seguin.summary.FieldSummary summary;

    private org.acm.seguin.uml.UMLPackage current;

    private boolean association;

    private org.acm.seguin.uml.line.DragPanelAdapter parentDragAdapter;

    private org.acm.seguin.uml.line.DragPanelAdapter fieldDragAdapter;

    /**
     * Create a new instance of a UMLLine
     *
     * @param initCurrent
     * 		Description of Parameter
     * @param parent
     * 		Description of Parameter
     * @param field
     * 		Description of Parameter
     * @param adapter
     * 		Description of Parameter
     */
    public UMLField(org.acm.seguin.uml.UMLPackage initCurrent, org.acm.seguin.uml.UMLType parent, org.acm.seguin.summary.FieldSummary field, org.acm.seguin.uml.line.DragPanelAdapter adapter) {
        super(parent, adapter);
        // Set the instance variables
        summary = field;
        current = initCurrent;
        association = false;
        // Reset the parent data
        org.acm.seguin.pretty.ModifierHolder modifiers = summary.getModifiers();
        setProtection(org.acm.seguin.uml.UMLLine.getProtectionCode(modifiers));
        setLabelText(summary.toString());
        setLabelFont(org.acm.seguin.uml.UMLLine.getProtectionFont(false, modifiers));
        // Reset the size
        setSize(getPreferredSize());
        // Create another adapter for draging this
        parentDragAdapter = adapter;
        fieldDragAdapter = new org.acm.seguin.uml.line.DragPanelAdapter(this, initCurrent);
        // Add a mouse listener
        addMouseListener(new org.acm.seguin.uml.UMLMouseAdapter(current, parent, this));
    }

    /**
     * Transform into an association
     *
     * @param way
     * 		Description of Parameter
     */
    public void setAssociation(boolean way) {
        association = way;
        if (association) {
            setLabelText(summary.getName());
            addMouseListener(fieldDragAdapter);
            addMouseMotionListener(fieldDragAdapter);
            removeMouseListener(parentDragAdapter);
            removeMouseMotionListener(parentDragAdapter);
            label.addMouseListener(fieldDragAdapter);
            label.addMouseMotionListener(fieldDragAdapter);
            label.removeMouseListener(parentDragAdapter);
            label.removeMouseMotionListener(parentDragAdapter);
        } else {
            setLabelText(summary.toString());
            addMouseListener(parentDragAdapter);
            addMouseMotionListener(parentDragAdapter);
            removeMouseListener(fieldDragAdapter);
            removeMouseMotionListener(fieldDragAdapter);
            label.addMouseListener(parentDragAdapter);
            label.addMouseMotionListener(parentDragAdapter);
            label.removeMouseListener(fieldDragAdapter);
            label.removeMouseMotionListener(fieldDragAdapter);
        }
        setSize(getPreferredSize());
    }

    /**
     * Return the summary
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.summary.FieldSummary getSummary() {
        return summary;
    }

    /**
     * Is this object represented as an association
     *
     * @return Description of the Returned Value
     */
    public boolean isAssociation() {
        return association;
    }

    /**
     * Is this object represented as an association
     *
     * @return Description of the Returned Value
     */
    public boolean isConvertable() {
        org.acm.seguin.summary.TypeDeclSummary typeDecl = summary.getTypeDecl();
        if (typeDecl.isPrimitive()) {
            return false;
        }
        org.acm.seguin.summary.TypeSummary typeSummary = org.acm.seguin.summary.query.GetTypeSummary.query(typeDecl);
        return typeSummary != null;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.summary.TypeSummary getType() {
        org.acm.seguin.summary.TypeDeclSummary typeDecl = summary.getTypeDecl();
        return org.acm.seguin.summary.query.GetTypeSummary.query(typeDecl);
    }

    /**
     * Return the default background color
     *
     * @return the color
     */
    protected java.awt.Color getDefaultBackground() {
        if (association) {
            return java.awt.Color.lightGray;
        } else {
            return super.getDefaultBackground();
        }
    }

    public org.acm.seguin.summary.Summary getSourceSummary() {
        return summary;
    }
}