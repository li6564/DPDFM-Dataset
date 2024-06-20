/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.line;
/**
 * MouseAdapter
 *
 * @author Chris Seguin
 * @created July 28, 1999
 */
public class LineMouseAdapter implements java.awt.event.MouseListener , java.awt.event.MouseMotionListener {
    private org.acm.seguin.uml.line.LinedPanel panel;

    /**
     * Constructor for the LineMouseAdapter object
     *
     * @param init
     * 		the panel that contains segmented lines
     */
    public LineMouseAdapter(org.acm.seguin.uml.line.LinedPanel init) {
        panel = init;
    }

    /**
     * Description of the Method
     *
     * @param mevt
     * 		the mouse event
     */
    public void mouseClicked(java.awt.event.MouseEvent mevt) {
    }

    /**
     * Description of the Method
     *
     * @param mevt
     * 		the mouse event
     */
    public void mouseEntered(java.awt.event.MouseEvent mevt) {
    }

    /**
     * Description of the Method
     *
     * @param mevt
     * 		the mouse event
     */
    public void mouseExited(java.awt.event.MouseEvent mevt) {
    }

    /**
     * Description of the Method
     *
     * @param mevt
     * 		the mouse event
     */
    public void mousePressed(java.awt.event.MouseEvent mevt) {
        java.awt.Point result = mevt.getPoint();
        panel.hit(result);
    }

    /**
     * Description of the Method
     *
     * @param mevt
     * 		the mouse event
     */
    public void mouseReleased(java.awt.event.MouseEvent mevt) {
        panel.drop();
    }

    /**
     * Description of the Method
     *
     * @param mevt
     * 		Description of Parameter
     */
    public void mouseDragged(java.awt.event.MouseEvent mevt) {
        panel.drag(mevt.getPoint());
    }

    /**
     * Description of the Method
     *
     * @param mevt
     * 		Description of Parameter
     */
    public void mouseMoved(java.awt.event.MouseEvent mevt) {
    }
}