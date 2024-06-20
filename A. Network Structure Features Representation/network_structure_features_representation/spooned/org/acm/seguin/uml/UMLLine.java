/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Displays a single UML line
 *
 * @author Chris Seguin
 * @created July 6, 1999
 */
public class UMLLine extends org.acm.seguin.uml.line.EndPointPanel {
    // Instance Variables
    /**
     * Description of the Field
     */
    protected org.acm.seguin.uml.line.SizableLabel label;

    /**
     * Description of the Field
     */
    protected int wide;

    /**
     * Description of the Field
     */
    protected int high;

    /**
     * Stores the icon for this object
     */
    protected org.acm.seguin.uml.UMLIcon icon;

    /**
     * Description of the Field
     */
    private int iconWidth = 8;

    /**
     * Description of the Field
     */
    private int iconHeight = 8;

    private int protection;

    private org.acm.seguin.uml.UMLType parent;

    private java.awt.Font font;

    // Class Variables
    /**
     * Description of the Field
     */
    public static final int PUBLIC = 0;

    /**
     * Description of the Field
     */
    public static final int PROTECTED_PRIVATE = 1;

    /**
     * Description of the Field
     */
    public static final int PROTECTED = 2;

    /**
     * Description of the Field
     */
    public static final int DEFAULT = 3;

    /**
     * Description of the Field
     */
    public static final int PRIVATE = 4;

    /**
     * Description of the Field
     */
    protected static java.awt.Color[] protectionColors = null;

    /**
     * Description of the Field
     */
    protected static java.awt.Font defaultFont = null;

    /**
     * Description of the Field
     */
    protected static java.awt.Font staticFont = null;

    /**
     * Description of the Field
     */
    protected static java.awt.Font abstractFont = null;

    /**
     * Description of the Field
     */
    protected static java.awt.Font titleFont = null;

    /**
     * Description of the Field
     */
    protected static java.awt.Font abstractTitleFont = null;

    /**
     * Description of the Field
     */
    protected static final int iconMargin = 1;

    /**
     * Description of the Field
     */
    protected static final int labelMargin = 1;

    /**
     * Create a new instance of a UMLLine
     *
     * @param parent
     * 		Description of Parameter
     * @param adapter
     * 		Description of Parameter
     */
    public UMLLine(org.acm.seguin.uml.UMLType parent, org.acm.seguin.uml.line.DragPanelAdapter adapter) {
        super(null, true);
        this.parent = parent;
        label = new org.acm.seguin.uml.line.SizableLabel("");
        label.setLocation((iconWidth + (2 * org.acm.seguin.uml.UMLLine.iconMargin)) + org.acm.seguin.uml.UMLLine.labelMargin, org.acm.seguin.uml.UMLLine.labelMargin);
        add(label);
        // Add listeners
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        icon = new org.acm.seguin.uml.ProtectionIcon(iconWidth, iconHeight);
    }

    /**
     * Set the protection code
     *
     * @param code
     * 		the code
     */
    public void setProtection(int code) {
        protection = code;
        if (icon instanceof org.acm.seguin.uml.ProtectionIcon) {
            ((org.acm.seguin.uml.ProtectionIcon) (icon)).setProtection(code);
        }
    }

    /**
     * Set the text
     *
     * @param msg
     * 		the message
     */
    public void setLabelText(java.lang.String msg) {
        label.setText(msg);
        // Reset the height and width
        java.awt.Dimension labelSize = label.getPreferredSize();
        label.setSize(labelSize);
        high = java.lang.Math.max(iconWidth + (2 * org.acm.seguin.uml.UMLLine.iconMargin), labelSize.height + (2 * org.acm.seguin.uml.UMLLine.labelMargin));
        wide = ((labelSize.width + iconWidth) + (2 * org.acm.seguin.uml.UMLLine.iconMargin)) + (2 * org.acm.seguin.uml.UMLLine.labelMargin);
    }

    /**
     * Set the font
     *
     * @param font
     * 		the new font
     */
    public void setLabelFont(java.awt.Font font) {
        label.setSLFont(font);
        // Reset the height and width
        java.awt.Dimension labelSize = label.getPreferredSize();
        label.setSize(labelSize);
        high = java.lang.Math.max(iconWidth + (2 * org.acm.seguin.uml.UMLLine.iconMargin), labelSize.height + (2 * org.acm.seguin.uml.UMLLine.labelMargin));
        wide = ((labelSize.width + iconWidth) + (2 * org.acm.seguin.uml.UMLLine.iconMargin)) + (2 * org.acm.seguin.uml.UMLLine.labelMargin);
        // Save the font
        this.font = font;
    }

    /**
     * Sets the Selected attribute of the UMLLine object
     *
     * @param value
     * 		The new Selected value
     */
    public void setSelected(boolean value) {
        parent.setSelected(value);
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
     * Gets the ParentType attribute of the UMLLine object
     *
     * @return The ParentType value
     */
    public org.acm.seguin.uml.UMLType getParentType() {
        return parent;
    }

    /**
     * Gets the Selected attribute of the UMLLine object
     *
     * @return The Selected value
     */
    public boolean isSelected() {
        return parent.isSelected();
    }

    /**
     * Paint this object
     *
     * @param g
     * 		the graphics object
     */
    public void paint(java.awt.Graphics g) {
        setBackground(getDefaultBackground());
        super.paint(g);
        drawIcon(g, 0, 0);
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
        java.awt.Point pt = label.getLocation();
        label.print(g, x + pt.x, y + pt.y);
        drawIcon(g, x, y);
    }

    /**
     * Add a mouse listener
     *
     * @param listener
     * 		the new listener
     */
    public void addMouseListener(java.awt.event.MouseListener listener) {
        label.addMouseListener(listener);
        super.addMouseListener(listener);
    }

    /**
     * Remove a mouse listener
     *
     * @param listener
     * 		the new listener
     */
    public void removeMouseListener(java.awt.event.MouseListener listener) {
        label.removeMouseListener(listener);
        super.removeMouseListener(listener);
    }

    /**
     * Add a mouse listener
     *
     * @param listener
     * 		the new listener
     */
    public void addMouseMotionListener(java.awt.event.MouseMotionListener listener) {
        label.addMouseMotionListener(listener);
        super.addMouseMotionListener(listener);
    }

    /**
     * Remove a mouse listener
     *
     * @param listener
     * 		the new listener
     */
    public void removeMouseMotionListener(java.awt.event.MouseMotionListener listener) {
        label.removeMouseMotionListener(listener);
        super.removeMouseMotionListener(listener);
    }

    /**
     * Sets the scaling factor
     *
     * @param value
     * 		scaling factor
     */
    public void scale(double value) {
        super.scale(value);
        label.scale(value);
    }

    /**
     * Return the default background color
     *
     * @return the color
     */
    protected java.awt.Color getDefaultBackground() {
        return parent.getBackground();
    }

    /**
     * Draws the icon
     *
     * @param g
     * 		the graphics object
     * @param x
     * 		Description of Parameter
     * @param y
     * 		Description of Parameter
     */
    protected void drawIcon(java.awt.Graphics g, int x, int y) {
        // Initialize local variables
        int nY = y + ((high - icon.getIconHeight()) / 2);
        double scale = getScale();
        // Draw the icons
        icon.setScale(scale);
        icon.paintIcon(this, g, x, nY);
    }

    /**
     * Add the protection information for this field or method
     *
     * @param modifiers
     * 		the modifier information
     * @return Description of the Returned Value
     */
    protected static int getProtectionCode(org.acm.seguin.pretty.ModifierHolder modifiers) {
        if (modifiers.isPublic()) {
            return org.acm.seguin.uml.UMLLine.PUBLIC;
        } else if (modifiers.isProtected() && modifiers.isPrivate()) {
            return org.acm.seguin.uml.UMLLine.PROTECTED_PRIVATE;
        } else if (modifiers.isPrivate()) {
            return org.acm.seguin.uml.UMLLine.PRIVATE;
        } else if (modifiers.isProtected()) {
            return org.acm.seguin.uml.UMLLine.PROTECTED;
        } else {
            return org.acm.seguin.uml.UMLLine.DEFAULT;
        }
    }

    /**
     * Get the font appropriate for the level of protection
     *
     * @param title
     * 		is this a title
     * @param modifiers
     * 		the modifiers
     * @return Description of the Returned Value
     */
    protected static java.awt.Font getProtectionFont(boolean title, org.acm.seguin.pretty.ModifierHolder modifiers) {
        if (org.acm.seguin.uml.UMLLine.staticFont == null) {
            org.acm.seguin.uml.UMLLine.initFonts();
        }
        if (modifiers == null) {
            return org.acm.seguin.uml.UMLLine.defaultFont;
        }
        if (modifiers.isAbstract()) {
            if (title) {
                return org.acm.seguin.uml.UMLLine.abstractTitleFont;
            } else {
                return org.acm.seguin.uml.UMLLine.abstractFont;
            }
        } else if (modifiers.isStatic()) {
            return org.acm.seguin.uml.UMLLine.staticFont;
        } else if (title) {
            return org.acm.seguin.uml.UMLLine.titleFont;
        } else {
            return org.acm.seguin.uml.UMLLine.defaultFont;
        }
    }

    /**
     * Get the color associated with a level of protection
     *
     * @param level
     * 		the level that we need to know
     * @return the color
     */
    protected static java.awt.Color getProtectionColor(int level) {
        if (org.acm.seguin.uml.UMLLine.protectionColors == null) {
            // Initialize
            org.acm.seguin.uml.UMLLine.protectionColors = new java.awt.Color[5];
            org.acm.seguin.uml.UMLLine.protectionColors[0] = java.awt.Color.green;
            org.acm.seguin.uml.UMLLine.protectionColors[1] = java.awt.Color.blue;
            org.acm.seguin.uml.UMLLine.protectionColors[2] = java.awt.Color.yellow;
            org.acm.seguin.uml.UMLLine.protectionColors[3] = java.awt.Color.orange;
            org.acm.seguin.uml.UMLLine.protectionColors[4] = java.awt.Color.red;
        }
        return org.acm.seguin.uml.UMLLine.protectionColors[level];
    }

    /**
     * Initialize the fonts
     */
    private static void initFonts() {
        org.acm.seguin.uml.UMLLine.defaultFont = new java.awt.Font("Serif", java.awt.Font.PLAIN, 12);
        org.acm.seguin.uml.UMLLine.staticFont = new java.awt.Font("Serif", java.awt.Font.BOLD, 12);
        org.acm.seguin.uml.UMLLine.abstractFont = new java.awt.Font("Serif", java.awt.Font.ITALIC, 12);
        org.acm.seguin.uml.UMLLine.titleFont = new java.awt.Font("Serif", java.awt.Font.PLAIN, 16);
        org.acm.seguin.uml.UMLLine.abstractTitleFont = new java.awt.Font("Serif", java.awt.Font.ITALIC, 16);
    }
}