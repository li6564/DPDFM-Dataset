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
public class UMLMouseAdapter extends java.awt.event.MouseAdapter {
    // Instance Variables
    private org.acm.seguin.uml.UMLPackage current;

    private org.acm.seguin.uml.UMLType type;

    private javax.swing.JPanel child;

    /**
     * Constructor for the UMLMouseAdapter object
     *
     * @param initType
     * 		Type object
     * @param initChild
     * 		Single line
     * @param currentPackage
     * 		Description of Parameter
     */
    public UMLMouseAdapter(org.acm.seguin.uml.UMLPackage currentPackage, org.acm.seguin.uml.UMLType initType, javax.swing.JPanel initChild) {
        current = currentPackage;
        type = initType;
        child = initChild;
    }

    /**
     * User has pressed a mouse button
     *
     * @param mevt
     * 		the mouse event
     */
    public void mousePressed(java.awt.event.MouseEvent mevt) {
        if ((mevt.getModifiers() & java.awt.event.MouseEvent.BUTTON1_MASK) == 0) {
            java.awt.Point pt;
            org.acm.seguin.uml.UMLPopupMenu upm;
            if (child == null) {
                upm = new org.acm.seguin.uml.UMLPopupMenu(current, type);
                pt = type.getLocationOnScreen();
            } else {
                upm = new org.acm.seguin.uml.UMLPopupMenu(current, child);
                pt = child.getLocationOnScreen();
            }
            javax.swing.JPopupMenu menu = upm.getMenu();
            menu.setLocation(mevt.getX() + pt.x, mevt.getY() + pt.y);
            menu.setVisible(true);
        }
    }
}