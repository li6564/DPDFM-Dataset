/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml.line;
/**
 * This adapter handles dragging panels around on another panel. This is used
 *  to relocate the class images on the class diagram.
 *
 * @author Chris Seguin
 * @created July 28, 1999
 */
public class DragPanelAdapter implements java.awt.event.MouseListener , java.awt.event.MouseMotionListener {
    private org.acm.seguin.uml.line.EndPointPanel panel;

    private org.acm.seguin.uml.line.LinedPanel parent;

    private java.awt.Point mouseStart;

    private java.awt.Point panelStart;

    /**
     * Constructor for the DragPanelAdapter object
     *
     * @param initPanel
     * 		Description of Parameter
     * @param initParent
     * 		Description of Parameter
     */
    public DragPanelAdapter(org.acm.seguin.uml.line.EndPointPanel initPanel, org.acm.seguin.uml.line.LinedPanel initParent) {
        panel = initPanel;
        parent = initParent;
    }

    /**
     * Process a mouse click action
     *
     * @param mevt
     * 		the mouse event
     */
    public void mouseClicked(java.awt.event.MouseEvent mevt) {
        boolean newState = !panel.isSelected();
        if ((mevt.getModifiers() & java.awt.event.MouseEvent.BUTTON1_MASK) == 0) {
            return;
        } else if (!mevt.isControlDown()) {
            parent.deselectAll();
        }
        panel.setSelected(newState);
    }

    /**
     * Process the mouse entering the component
     *
     * @param mevt
     * 		the mouse event
     */
    public void mouseEntered(java.awt.event.MouseEvent mevt) {
    }

    /**
     * Process the mouse leaving the component
     *
     * @param mevt
     * 		the mouse event
     */
    public void mouseExited(java.awt.event.MouseEvent mevt) {
    }

    /**
     * Process a mouse button press
     *
     * @param mevt
     * 		the mouse event
     */
    public void mousePressed(java.awt.event.MouseEvent mevt) {
        mouseStart = mevt.getPoint();
        panelStart = panel.getLocation(panelStart);
    }

    /**
     * User released the mouse button
     *
     * @param mevt
     * 		the mouse event
     */
    public void mouseReleased(java.awt.event.MouseEvent mevt) {
        if ((mevt.getModifiers() & java.awt.event.MouseEvent.BUTTON1_MASK) != 0) {
            if (parent instanceof org.acm.seguin.uml.line.LinedPanel) {
                ((org.acm.seguin.uml.line.LinedPanel) (parent)).drop();
            }
            if (parent instanceof org.acm.seguin.uml.UMLPackage) {
                ((org.acm.seguin.uml.UMLPackage) (parent)).setDirty();
            }
        }
    }

    /**
     * Notifies this object that it is being dragged
     *
     * @param mevt
     * 		The mouse object
     */
    public void mouseDragged(java.awt.event.MouseEvent mevt) {
        if ((mevt.getModifiers() & java.awt.event.MouseEvent.BUTTON1_MASK) != 0) {
            java.awt.Point currentMouse = mevt.getPoint();
            int deltaX = currentMouse.x - mouseStart.x;
            int deltaY = currentMouse.y - mouseStart.y;
            if (!panel.isSelected()) {
                panel.shift(deltaX, deltaY);
            } else {
                parent.shift(deltaX, deltaY);
            }
            parent.repaint();
        }
    }

    /**
     * What to do when the mouse moves. Nothing.
     *
     * @param mevt
     * 		the mouse event
     */
    public void mouseMoved(java.awt.event.MouseEvent mevt) {
    }
}