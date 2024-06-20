/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Holds the roles associated with a type
 *
 * @author Chris Seguin
 * @created September 30, 1999
 */
public class RoleHolder extends javax.swing.JPanel {
    private java.util.LinkedList labels;

    private int wide;

    private int high;

    private org.acm.seguin.uml.UMLMouseAdapter popupMenuListener;

    private org.acm.seguin.uml.line.DragPanelAdapter panelDragAdapter;

    /**
     * Constructor for the RoleHolder object
     *
     * @param popupMenuListener
     * 		listener that launches the popup menu
     * @param panelDragAdapter
     * 		listener that drags the type
     */
    public RoleHolder(org.acm.seguin.uml.UMLMouseAdapter popupMenuListener, org.acm.seguin.uml.line.DragPanelAdapter panelDragAdapter) {
        setLayout(null);
        labels = new java.util.LinkedList();
        wide = 0;
        high = 0;
        this.popupMenuListener = popupMenuListener;
        this.panelDragAdapter = panelDragAdapter;
    }

    /**
     * Gets the preferred size
     *
     * @return the preferred size for this object
     */
    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(wide, high);
    }

    /**
     * Gets the minimum size
     *
     * @return The minimum size for this object
     */
    public java.awt.Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * Adds a role
     *
     * @param msg
     * 		the role name
     */
    public void add(java.lang.String msg) {
        org.acm.seguin.uml.line.SizableLabel roleLabel = new org.acm.seguin.uml.line.SizableLabel(msg);
        roleLabel.setSLFont(org.acm.seguin.uml.UMLLine.defaultFont);
        roleLabel.setSLHorizontalAlignment(javax.swing.JLabel.CENTER);
        roleLabel.setLocation(0, high);
        add(roleLabel);
        java.awt.Dimension dim = roleLabel.getPreferredSize();
        roleLabel.setSize(dim);
        wide = java.lang.Math.max(wide, dim.width);
        high = high + dim.height;
        roleLabel.addMouseListener(popupMenuListener);
        roleLabel.addMouseListener(panelDragAdapter);
        roleLabel.addMouseMotionListener(panelDragAdapter);
        labels.add(roleLabel);
    }

    /**
     * Determines if there are any roles
     *
     * @return Description of the Returned Value
     */
    public boolean hasAny() {
        return high > 0;
    }

    /**
     * Reset width
     *
     * @param newWidth
     * 		the new width
     */
    public void resetWidth(int newWidth) {
        java.awt.Dimension temp = getPreferredSize();
        temp.width = newWidth;
        setSize(temp);
        java.util.Iterator iter = labels.iterator();
        while (iter.hasNext()) {
            org.acm.seguin.uml.line.SizableLabel next = ((org.acm.seguin.uml.line.SizableLabel) (iter.next()));
            temp = next.getPreferredSize();
            temp.width = newWidth;
            next.setSize(temp);
        } 
    }

    /**
     * Print the roles
     *
     * @param g
     * 		Description of Parameter
     * @param x
     * 		Description of Parameter
     * @param y
     * 		Description of Parameter
     */
    public void print(java.awt.Graphics g, int x, int y) {
        java.awt.Rectangle bounds = getBounds();
        g.setFont(org.acm.seguin.uml.UMLLine.defaultFont);
        java.awt.FontMetrics fm = g.getFontMetrics();
        java.util.Iterator iter = labels.iterator();
        while (iter.hasNext()) {
            org.acm.seguin.uml.line.SizableLabel roleLabel = ((org.acm.seguin.uml.line.SizableLabel) (iter.next()));
            java.awt.Point pt = roleLabel.getLocation();
            roleLabel.print(g, x + pt.x, y + pt.y);
        } 
    }

    /**
     * Sets the scaling factor
     *
     * @param value
     * 		scaling factor
     */
    public void scale(double value) {
        java.util.Iterator iter = labels.iterator();
        while (iter.hasNext()) {
            org.acm.seguin.uml.line.SizableLabel roleLabel = ((org.acm.seguin.uml.line.SizableLabel) (iter.next()));
            roleLabel.scale(value);
        } 
    }
}