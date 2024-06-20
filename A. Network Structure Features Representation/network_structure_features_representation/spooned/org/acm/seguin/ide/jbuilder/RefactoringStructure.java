/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
/**
 * Structure view that provides access to the refactorings
 *
 * @author Chris Seguin
 */
public class RefactoringStructure extends org.acm.seguin.ide.jbuilder.TextStructureDelegate {
    private java.lang.Object currentNode = null;

    public RefactoringStructure(org.acm.seguin.ide.jbuilder.TextStructure init) {
        super(init);
    }

    /**
     * Gets the Popup attribute of the RefactoringStructureView object
     *
     * @return The Popup value
     */
    public javax.swing.JPopupMenu getPopup() {
        javax.swing.JPopupMenu menu = super.getPopup();
        menu.addSeparator();
        menu.add(new javax.swing.JMenu("Refactor"));
        return menu;
    }

    /**
     * The mouse was pressed
     *
     * @param evt
     * 		the mouse event
     */
    public void mousePressed(java.awt.event.MouseEvent evt) {
        javax.swing.tree.TreePath path = getTree().getClosestPathForLocation(evt.getX(), evt.getY());
        currentNode = path.getLastPathComponent();
        java.lang.System.out.println((("Hit:  " + currentNode.toString()) + "  is a ") + currentNode.getClass().getName());
        super.mousePressed(evt);
    }
}