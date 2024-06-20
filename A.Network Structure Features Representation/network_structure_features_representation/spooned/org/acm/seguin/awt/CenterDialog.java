/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.awt;
/**
 * This object is responsible for centering the dialog box on the screen.
 *
 * @author Chris Seguin
 */
public class CenterDialog {
    /**
     * Constructor for the CenterDialog object
     */
    private CenterDialog() {
    }

    /**
     * Actually does the work
     *
     * @param dialog
     * 		The dialog box
     * @param parent
     * 		the frame we are centering the dialog over or null if we
     * 		should center it on the screen
     */
    public static void center(javax.swing.JDialog dialog, javax.swing.JFrame parent) {
        java.awt.Dimension dim = dialog.getPreferredSize();
        java.awt.Dimension frameSize;
        int x;
        int y;
        if (parent == null) {
            frameSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            x = 0;
            y = 0;
        } else {
            frameSize = parent.getSize();
            java.awt.Point loc = parent.getLocation();
            x = loc.x;
            y = loc.y;
        }
        x += (frameSize.width - dim.width) / 2;
        y += (frameSize.height - dim.height) / 2;
        dialog.setLocation(x, y);
    }

    /**
     * Actually does the work to center the dialog, but uses the
     *  EditorOperation's frame to determine what to center the dialog over.
     *
     * @param dialog
     * 		The dialog box
     */
    public static void center(javax.swing.JDialog dialog) {
        org.acm.seguin.ide.common.EditorOperations eo = org.acm.seguin.ide.common.EditorOperations.get();
        if (eo == null) {
            org.acm.seguin.awt.CenterDialog.center(dialog, ((javax.swing.JFrame) (null)));
        } else {
            org.acm.seguin.awt.CenterDialog.center(dialog, eo.getEditorFrame());
        }
    }

    /**
     * Center the dailog on a diagram's frame
     *
     * @param dialog
     * 		the diagram
     * @param umlPackage
     * 		the frame
     */
    public static void center(javax.swing.JDialog dialog, org.acm.seguin.uml.UMLPackage umlPackage) {
        if (umlPackage == null) {
            org.acm.seguin.awt.CenterDialog.center(dialog);
            return;
        }
        java.awt.Component current = umlPackage.getParent();
        while (!(current instanceof javax.swing.JFrame)) {
            current = current.getParent();
        } 
        org.acm.seguin.awt.CenterDialog.center(dialog, ((javax.swing.JFrame) (current)));
    }
}