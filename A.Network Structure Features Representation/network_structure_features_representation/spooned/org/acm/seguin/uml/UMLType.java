/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Displays the summary of a type object
 *
 * @author Chris Seguin
 * @created June 7, 1999
 */
public class UMLType extends org.acm.seguin.uml.line.EndPointPanel implements org.acm.seguin.uml.ISourceful {
    /**
     * Description of the Field
     */
    protected int borderWidth = 2;

    /**
     * Description of the Field
     */
    protected int lineSize = 0;

    // Instance Variables
    private org.acm.seguin.uml.UMLPackage parent;

    private org.acm.seguin.uml.line.SizableLabel nameLabel;

    private org.acm.seguin.uml.RoleHolder roles;

    private org.acm.seguin.summary.TypeSummary type;

    private int wide;

    private int high;

    private int titleHeight;

    private int state;

    // Colors
    private static java.awt.Color defaultBG = null;

    private static java.awt.Color selectedBG;

    private static java.awt.Color foreignBG;

    private static java.awt.Color selectedForeignBG;

    // States
    private static final int DEFAULT = 0;

    private static final int SELECTED = 1;

    private static final int FOREIGN = 2;

    private static final int TITLE_BORDER = 4;

    /**
     * Create a new instance of a UMLType
     *
     * @param initParent
     * 		the parent
     * @param initType
     * 		the initial type data
     * @param foreign
     * 		Description of Parameter
     */
    public UMLType(org.acm.seguin.uml.UMLPackage initParent, org.acm.seguin.summary.TypeSummary initType, boolean foreign) {
        super(null, true);
        // Remember local variables
        parent = initParent;
        type = initType;
        wide = 0;
        high = 0;
        if (foreign) {
            state = org.acm.seguin.uml.UMLType.FOREIGN;
        } else {
            state = org.acm.seguin.uml.UMLType.DEFAULT;
        }
        // Create a mouse listener
        org.acm.seguin.uml.UMLMouseAdapter listener = new org.acm.seguin.uml.UMLMouseAdapter(parent, this, null);
        addMouseListener(listener);
        // Create another adapter for draging this
        org.acm.seguin.uml.line.DragPanelAdapter adapter = new org.acm.seguin.uml.line.DragPanelAdapter(this, parent);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        // Add the name label
        nameLabel = new org.acm.seguin.uml.line.SizableLabel(type.getName());
        nameLabel.setLocation(borderWidth, borderWidth);
        nameLabel.setSLHorizontalAlignment(javax.swing.JLabel.CENTER);
        nameLabel.setSLFont(org.acm.seguin.uml.UMLLine.getProtectionFont(true, type.getModifiers()));
        java.awt.Dimension titleSize = nameLabel.getPreferredSize();
        titleHeight = titleSize.height;
        wide = titleSize.width + (2 * org.acm.seguin.uml.UMLType.TITLE_BORDER);
        add(nameLabel);
        nameLabel.addMouseListener(listener);
        nameLabel.addMouseListener(adapter);
        nameLabel.addMouseMotionListener(adapter);
        // Check to see if we need a role
        roles = new org.acm.seguin.uml.RoleHolder(listener, adapter);
        if (type.isInterface()) {
            roles.add((((char) (171)) + "Interface") + ((char) (187)));
        }
        if (foreign) {
            roles.add("Package:  " + getPackageName());
        }
        if (roles.hasAny()) {
            roles.setLocation(borderWidth, borderWidth + titleSize.height);
            add(roles);
            java.awt.Dimension roleSize = roles.getPreferredSize();
            roles.setSize(roleSize);
            wide = java.lang.Math.max(wide, roleSize.width);
            titleHeight += roleSize.height;
        }
        // Determine the size of a line
        lineSize = computeLineSize();
        // Add attribute labels
        int nY = titleHeight + (borderWidth * 2);
        java.util.Iterator iter = type.getFields();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.uml.UMLField field = new org.acm.seguin.uml.UMLField(parent, this, ((org.acm.seguin.summary.FieldSummary) (iter.next())), adapter);
                field.setLocation(borderWidth, nY);
                add(field);
                lineSize = field.getPreferredSize().height;
                nY += field.getPreferredSize().height;
                wide = java.lang.Math.max(wide, field.getPreferredSize().width);
            } 
        } else {
            nY += lineSize;
        }
        // Add operation label
        nY += borderWidth;
        iter = type.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary nextMethod = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                if (!nextMethod.isInitializer()) {
                    org.acm.seguin.uml.UMLMethod method = new org.acm.seguin.uml.UMLMethod(parent, this, nextMethod, adapter);
                    method.setLocation(borderWidth, nY);
                    add(method);
                    nY += method.getPreferredSize().height;
                    wide = java.lang.Math.max(wide, method.getPreferredSize().width);
                }
            } 
        } else {
            nY += lineSize;
        }
        // Add nested types label
        int nestedTypes = type.getTypeCount();
        if (nestedTypes > 0) {
            nY += borderWidth;
            iter = type.getTypes();
            if (iter != null) {
                while (iter.hasNext()) {
                    org.acm.seguin.uml.UMLNestedType nestedType = new org.acm.seguin.uml.UMLNestedType(parent, this, ((org.acm.seguin.summary.TypeSummary) (iter.next())), adapter);
                    nestedType.setLocation(borderWidth, nY);
                    add(nestedType);
                    nY += nestedType.getPreferredSize().height;
                    wide = java.lang.Math.max(wide, nestedType.getPreferredSize().width);
                } 
            }
        }
        // Add the final extra space at the bottom
        high = nY + borderWidth;
        // Set the size
        nameLabel.setSize(wide, titleSize.height);
        if (roles.hasAny()) {
            roles.resetWidth(wide);
        }
        // Revise the width
        wide += 2 * borderWidth;
        // Set the size for the whole thing
        setSize(getPreferredSize());
    }

    /**
     * Sets the Selected attribute of the UMLType object
     *
     * @param way
     * 		The new Selected value
     */
    public void setSelected(boolean way) {
        if (way) {
            select();
        } else {
            deselect();
        }
    }

    /**
     * Get the summary
     *
     * @return the summary
     */
    public org.acm.seguin.summary.TypeSummary getSummary() {
        return type;
    }

    /**
     * Returns the minimum size
     *
     * @return The size
     */
    public java.awt.Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * Returns the preferred size
     *
     * @return The size
     */
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(wide, high);
    }

    /**
     * Get the UML package that is holding this
     *
     * @return the package
     */
    public org.acm.seguin.uml.UMLPackage getPackage() {
        return parent;
    }

    /**
     * Determine if this is selected
     *
     * @return true if this is selected
     */
    public boolean isSelected() {
        return (state & org.acm.seguin.uml.UMLType.SELECTED) > 0;
    }

    /**
     * Determine if this is foreign
     *
     * @return true if this is foreign
     */
    public boolean isForeign() {
        return (state & org.acm.seguin.uml.UMLType.FOREIGN) > 0;
    }

    /**
     * Return the background color
     *
     * @return the background color
     */
    public java.awt.Color getBackgroundColor() {
        if (org.acm.seguin.uml.UMLType.defaultBG == null) {
            org.acm.seguin.uml.UMLType.initColors();
        }
        if (state == org.acm.seguin.uml.UMLType.SELECTED) {
            return org.acm.seguin.uml.UMLType.selectedBG;
        }
        if (state == org.acm.seguin.uml.UMLType.FOREIGN) {
            return org.acm.seguin.uml.UMLType.foreignBG;
        }
        if (isSelected() && isForeign()) {
            return org.acm.seguin.uml.UMLType.selectedForeignBG;
        }
        return org.acm.seguin.uml.UMLType.defaultBG;
    }

    /**
     * Returns an identifier for a type
     *
     * @return an identifier for this panel
     */
    public java.lang.String getID() {
        return (type.getPackageSummary().getName() + ":") + type.getName();
    }

    /**
     * Count the number of attributes
     *
     * @param name
     * 		Description of Parameter
     * @return the number of attributes
     */
    public org.acm.seguin.uml.UMLField getField(java.lang.String name) {
        if (name == null) {
            return null;
        }
        java.awt.Component[] children = getComponents();
        int last = children.length;
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLField) {
                org.acm.seguin.uml.UMLField field = ((org.acm.seguin.uml.UMLField) (children[ndx]));
                if (name.equals(field.getSummary().getName())) {
                    return field;
                }
            }
        }
        return null;
    }

    /**
     * Paint this object
     *
     * @param g
     * 		the graphics object
     */
    public void paint(java.awt.Graphics g) {
        // Set the background color
        java.awt.Color bg = getBackgroundColor();
        setBackground(bg);
        roles.setBackground(bg);
        // Paint the components
        super.paint(g);
        drawFrame(g, 0, 0);
    }

    /**
     * Print this object
     *
     * @param g
     * 		the graphics object
     * @param x
     * 		the x coordinate
     * @param y
     * 		the y coordinate
     */
    public void print(java.awt.Graphics g, int x, int y) {
        // Set the background color
        java.awt.Rectangle bounds = getBounds();
        g.setColor(getBackgroundColor());
        g.fillRect(x, y, bounds.width, bounds.height);
        // Draw the title
        java.awt.Point pt = nameLabel.getLocation();
        nameLabel.print(g, x + pt.x, y + pt.y);
        // Draw the role
        if (roles.hasAny()) {
            pt = roles.getLocation();
            roles.print(g, x + pt.x, y + pt.y);
        }
        // Paint the components
        java.awt.Component[] children = getComponents();
        int last = children.length;
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLLine) {
                pt = children[ndx].getLocation();
                ((org.acm.seguin.uml.UMLLine) (children[ndx])).print(g, x + pt.x, y + pt.y);
            }
        }
        drawFrame(g, x, y);
    }

    /**
     * Resizes and repositions the compontents
     */
    public void resize() {
        // Local Variables
        java.awt.Component[] children = getComponents();
        int last = children.length;
        // Set the default sizes
        wide = 0;
        high = 0;
        // Get the size of the title
        java.awt.Dimension titleSize = nameLabel.getPreferredSize();
        titleHeight = titleSize.height;
        wide = titleSize.width + (2 * borderWidth);
        if (roles.hasAny()) {
            java.awt.Dimension roleSize = roles.getPreferredSize();
            titleHeight += roleSize.height;
            wide = java.lang.Math.max(roleSize.width, wide);
        }
        // Add attribute labels
        int nY = titleHeight + (2 * borderWidth);
        boolean foundField = false;
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLField) {
                org.acm.seguin.uml.UMLField field = ((org.acm.seguin.uml.UMLField) (children[ndx]));
                field.setLocation(borderWidth, nY);
                nY += lineSize;
                wide = java.lang.Math.max(wide, field.getPreferredSize().width);
                foundField = true;
            }
        }
        if (!foundField) {
            nY += lineSize;
        }
        // Add operation label
        nY += borderWidth;
        boolean foundMethod = false;
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLMethod) {
                org.acm.seguin.uml.UMLMethod method = ((org.acm.seguin.uml.UMLMethod) (children[ndx]));
                method.setLocation(borderWidth, nY);
                nY += lineSize;
                wide = java.lang.Math.max(wide, method.getPreferredSize().width);
                foundMethod = true;
            }
        }
        if (!foundMethod) {
            nY += lineSize;
        }
        // Add nested types label
        int nestedTypes = type.getTypeCount();
        if (nestedTypes > 0) {
            nY += borderWidth;
            for (int ndx = 0; ndx < last; ndx++) {
                if (children[ndx] instanceof org.acm.seguin.uml.UMLNestedType) {
                    org.acm.seguin.uml.UMLNestedType nestedType = ((org.acm.seguin.uml.UMLNestedType) (children[ndx]));
                    nestedType.setLocation(borderWidth, nY);
                    nY += lineSize;
                    wide = java.lang.Math.max(wide, nestedType.getPreferredSize().width);
                }
            }
        }
        // Add the final extra space at the bottom
        high = nY + borderWidth;
        // Set the size
        nameLabel.setSize(wide, titleSize.height);
        if (roles.hasAny()) {
            roles.resetWidth(wide);
        }
        // Revise the width
        wide += 2 * borderWidth;
        // Set the size for the whole thing
        setSize(getPreferredSize());
        parent.repaint();
    }

    /**
     * Select this item
     */
    public void select() {
        state = state | org.acm.seguin.uml.UMLType.SELECTED;
        repaint();
    }

    /**
     * Select this item
     */
    public void deselect() {
        state = state & (~org.acm.seguin.uml.UMLType.SELECTED);
        repaint();
    }

    /**
     * Toggle the selected state
     */
    public void toggleSelect() {
        state = state ^ org.acm.seguin.uml.UMLType.SELECTED;
        repaint();
    }

    /**
     * Save the files
     *
     * @param output
     * 		the output stream
     */
    public void save(java.io.PrintWriter output) {
        java.awt.Point pt = getUnscaledLocation();
        output.println(((((("P[" + getID()) + "]{") + pt.x) + ",") + pt.y) + "}");
    }

    /**
     * Load the type
     *
     * @param buffer
     * 		the buffer
     */
    public void load(java.lang.String buffer) {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(buffer, ",");
        java.lang.String strX = tok.nextToken();
        java.lang.String strY = tok.nextToken();
        try {
            setLocation(java.lang.Integer.parseInt(strX), java.lang.Integer.parseInt(strY));
        } catch (java.lang.NumberFormatException nfe) {
        }
    }

    /**
     * Convert an attribute to an association
     *
     * @param packagePanel
     * 		the package panel
     * @param fieldPanel
     * 		the field panel
     * @return the new segmented line
     */
    public org.acm.seguin.uml.line.AssociationRelationship convertToAssociation(org.acm.seguin.uml.UMLPackage packagePanel, org.acm.seguin.uml.UMLField fieldPanel) {
        remove(fieldPanel);
        resize();
        packagePanel.add(fieldPanel);
        org.acm.seguin.summary.TypeSummary typeSummary = fieldPanel.getType();
        javax.swing.JPanel endPanel = packagePanel.findType(typeSummary);
        if (endPanel == null) {
            endPanel = new org.acm.seguin.uml.UMLType(packagePanel, typeSummary, true);
            packagePanel.add(endPanel);
            endPanel.setLocation(0, 0);
        }
        org.acm.seguin.uml.line.AssociationRelationship result = new org.acm.seguin.uml.line.AssociationRelationship(this, ((org.acm.seguin.uml.line.EndPointPanel) (endPanel)), fieldPanel);
        packagePanel.add(result);
        return result;
    }

    /**
     * Convert from an association to an attribute
     *
     * @param packagePanel
     * 		the package panel
     * @param fieldPanel
     * 		the field panel
     */
    public void convertToAttribute(org.acm.seguin.uml.UMLPackage packagePanel, org.acm.seguin.uml.UMLField fieldPanel) {
        packagePanel.remove(fieldPanel);
        packagePanel.removeAssociation(fieldPanel);
        add(fieldPanel);
        resize();
    }

    /**
     * Sets the scaling factor
     *
     * @param value
     * 		scaling factor
     */
    public void scale(double value) {
        super.scale(value);
        nameLabel.scale(value);
        roles.scale(value);
        // Rescale the children
        java.awt.Component[] children = getComponents();
        int last = children.length;
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLLine) {
                ((org.acm.seguin.uml.UMLLine) (children[ndx])).scale(value);
            }
        }
    }

    /**
     * Returns the type summary for this class
     *
     * @return the type summary
     */
    public org.acm.seguin.summary.Summary getSourceSummary() {
        return type;
    }

    /**
     * Get the name of the package
     *
     * @return the package name
     */
    private java.lang.String getPackageName() {
        org.acm.seguin.summary.Summary current = type;
        while (!(current instanceof org.acm.seguin.summary.PackageSummary)) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.PackageSummary) (current)).getName();
    }

    /**
     * Count the number of attributes
     *
     * @return the number of attributes
     */
    private int getAttributeCount() {
        int result = 0;
        java.awt.Component[] children = getComponents();
        int last = children.length;
        for (int ndx = 0; ndx < last; ndx++) {
            if (children[ndx] instanceof org.acm.seguin.uml.UMLField) {
                result++;
            }
        }
        return result;
    }

    /**
     * Draws the frame
     *
     * @param g
     * 		the graphics object
     * @param x
     * 		the x coordinate
     * @param y
     * 		the y coordinate
     */
    private void drawFrame(java.awt.Graphics g, int x, int y) {
        g.setColor(org.acm.seguin.uml.UMLType.getFrameColor());
        java.awt.Dimension size = getSize();
        double scalingFactor = getScale();
        // Draw outer edge
        g.drawRect(x, y, size.width - 1, size.height - 1);
        g.drawRect(x + 1, y + 1, size.width - 3, size.height - 3);
        // Separate name from field
        g.drawLine(x, ((int) (y + (scalingFactor * (titleHeight + 4)))), (x + size.width) - 1, ((int) (y + (scalingFactor * (titleHeight + 4)))));
        g.drawLine(x, ((int) (y + (scalingFactor * (titleHeight + 5)))), (x + size.width) - 1, ((int) (y + (scalingFactor * (titleHeight + 5)))));
        // Separate field from methods
        int high = ((int) (scalingFactor * ((titleHeight + 4) + (lineSize * java.lang.Math.max(1, getAttributeCount())))));
        g.drawLine(x, y + high, (x + size.width) - 1, y + high);
        g.drawLine(x, (y + high) + 1, (x + size.width) - 1, (y + high) + 1);
        // Check if there are any nested types - if so draw their frame
        int typeCount = type.getTypeCount();
        if (typeCount > 0) {
            int previousLabels = java.lang.Math.max(1, getAttributeCount()) + java.lang.Math.max(1, type.getMethodCount());
            high = ((int) (scalingFactor * ((titleHeight + 4) + (lineSize * previousLabels))));
            g.drawLine(x, y + high, (x + size.width) - 1, y + high);
            g.drawLine(x, (y + high) + 1, (x + size.width) - 1, (y + high) + 1);
        }
    }

    /**
     * Compute the line size
     *
     * @return Description of the Returned Value
     */
    private int computeLineSize() {
        org.acm.seguin.uml.line.LabelSizeComputation lsc = org.acm.seguin.uml.line.LabelSizeComputation.get();
        int height = lsc.computeHeight("Test", org.acm.seguin.uml.UMLLine.defaultFont);
        return height + (2 * org.acm.seguin.uml.UMLLine.labelMargin);
    }

    /**
     * Return the frame color
     *
     * @return the frame color
     */
    private static java.awt.Color getFrameColor() {
        return java.awt.Color.black;
    }

    /**
     * Initializes the background colors for the various classes
     */
    private static synchronized void initColors() {
        if (org.acm.seguin.uml.UMLType.defaultBG == null) {
            org.acm.seguin.uml.UMLType.defaultBG = java.awt.Color.white;
            org.acm.seguin.uml.UMLType.selectedBG = new java.awt.Color(250, 255, 220);
            org.acm.seguin.uml.UMLType.foreignBG = new java.awt.Color(200, 200, 255);
            org.acm.seguin.uml.UMLType.selectedForeignBG = new java.awt.Color(220, 255, 220);
        }
    }
}