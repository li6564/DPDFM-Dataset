/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.jbuilder.node.JavaFileNode;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.ide.BrowserListener;
import com.borland.primetime.ide.NodeViewer;
import com.borland.primetime.node.Node;
import com.borland.primetime.node.Project;
import com.borland.primetime.util.VetoException;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class RefactoringAdapter extends org.acm.seguin.ide.jbuilder.BrowserAdapter {
    /**
     * Description of the Method
     *
     * @param browser
     * 		Description of Parameter
     * @param node
     * 		Description of Parameter
     * @param viewer
     * 		Description of Parameter
     */
    public void browserViewerActivated(com.borland.primetime.ide.Browser browser, com.borland.primetime.node.Node node, com.borland.primetime.ide.NodeViewer viewer) {
        java.lang.System.out.println("We are activating a viewer!");
        if (node instanceof com.borland.jbuilder.node.JavaFileNode) {
            java.lang.System.out.println("  Viewer:  " + viewer.getClass().getName());
            java.lang.System.out.println("  TextStructure:  " + ((com.borland.jbuilder.node.JavaFileNode) (node)).getTextStructureClass().getName());
            javax.swing.JComponent structure = viewer.getStructureComponent();
            if (structure == null) {
                java.lang.System.out.println("No structure viewer");
            } else {
                java.lang.System.out.println("  Structure:  " + structure.getClass().getName());
                java.awt.Component[] comps = structure.getComponents();
                for (int ndx = 0; ndx < comps.length; ndx++) {
                    java.lang.System.out.println("    Contains:  " + comps[ndx].getClass().getName());
                }
            }
        }
    }
}