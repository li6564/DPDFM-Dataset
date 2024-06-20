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
public class PopupMenuListener extends java.awt.event.MouseAdapter implements java.awt.event.ActionListener {
    // Instance Variables
    private javax.swing.JPopupMenu menu;

    private javax.swing.JMenuItem menuItem;

    /**
     * Constructor for the PopupMenuListener object
     *
     * @param initMenu
     * 		Description of Parameter
     * @param initItem
     * 		Description of Parameter
     */
    public PopupMenuListener(javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        menu = initMenu;
        menuItem = initItem;
    }

    /**
     * A menu item has been selected
     *
     * @param evt
     * 		Description of Parameter
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (menuItem == null) {
            return;
        }
        if (menuItem instanceof javax.swing.JMenu) {
            // Do nothing
        } else {
            menu.setVisible(false);
        }
    }

    /**
     * A menu item has been selected
     *
     * @param mevt
     * 		mouse event
     */
    public void mouseEntered(java.awt.event.MouseEvent mevt) {
        menuItem.setSelected(true);
    }

    /**
     * A menu item has been selected
     *
     * @param mevt
     * 		mouse event
     */
    public void mouseExited(java.awt.event.MouseEvent mevt) {
        menuItem.setSelected(false);
    }
}