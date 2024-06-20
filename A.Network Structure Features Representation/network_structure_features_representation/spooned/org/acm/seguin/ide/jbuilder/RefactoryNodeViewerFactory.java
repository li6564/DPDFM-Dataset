/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.jbuilder.node.JavaFileNode;
import com.borland.primetime.ide.Context;
import com.borland.primetime.ide.NodeViewer;
import com.borland.primetime.ide.NodeViewerFactory;
import com.borland.primetime.node.Node;
/**
 * Factory for node viewers for Refactoring editor
 *
 * @author Chris Seguin
 */
public class RefactoryNodeViewerFactory implements com.borland.primetime.ide.NodeViewerFactory {
    private static org.acm.seguin.ide.jbuilder.RefactoryNodeViewerFactory factory = null;

    /**
     * Constructor for the RefactoryNodeViewerFactory object
     */
    private RefactoryNodeViewerFactory() {
    }

    /**
     * Determines if this factory can view this type of file
     *
     * @param node
     * 		the type of file
     * @return true if it can be displayed
     */
    public boolean canDisplayNode(com.borland.primetime.node.Node node) {
        return node instanceof com.borland.jbuilder.node.JavaFileNode;
    }

    /**
     * Creates the node viewer
     *
     * @param context
     * 		the information about what is to be displayed
     * @return the viewer
     */
    public com.borland.primetime.ide.NodeViewer createNodeViewer(com.borland.primetime.ide.Context context) {
        if (canDisplayNode(context.getNode())) {
            org.acm.seguin.ide.jbuilder.RefactoringBrowser viewer = new org.acm.seguin.ide.jbuilder.RefactoringBrowser(context);
            return viewer;
        }
        return null;
    }

    /**
     * Gets the Factory attribute of the RefactoryNodeViewerFactory class
     *
     * @return The Factory value
     */
    public static org.acm.seguin.ide.jbuilder.RefactoryNodeViewerFactory getFactory() {
        if (org.acm.seguin.ide.jbuilder.RefactoryNodeViewerFactory.factory == null) {
            org.acm.seguin.ide.jbuilder.RefactoryNodeViewerFactory.factory = new org.acm.seguin.ide.jbuilder.RefactoryNodeViewerFactory();
        }
        return org.acm.seguin.ide.jbuilder.RefactoryNodeViewerFactory.factory;
    }
}