/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.primetime.ide.Context;
import com.borland.primetime.ide.NodeViewer;
import com.borland.primetime.ide.NodeViewerFactory;
import com.borland.primetime.node.Node;
/**
 * Factory for node viewers
 *
 * @author Chris Seguin
 */
public class UMLNodeViewerFactory implements com.borland.primetime.ide.NodeViewerFactory {
    private org.acm.seguin.ide.common.MultipleDirClassDiagramReloader reloader;

    private static org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory factory = null;

    /**
     * Constructor for the UMLNodeViewerFactory object
     */
    private UMLNodeViewerFactory() {
        reloader = new org.acm.seguin.ide.jbuilder.JBuilderClassDiagramLoader();
    }

    /**
     * Gets the class diagram reloader
     *
     * @return the reloader
     */
    public org.acm.seguin.ide.common.MultipleDirClassDiagramReloader getReloader() {
        return reloader;
    }

    /**
     * Determines if this factory can view this type of file
     *
     * @param node
     * 		the type of file
     * @return true if it can be displayed
     */
    public boolean canDisplayNode(com.borland.primetime.node.Node node) {
        return node instanceof org.acm.seguin.ide.jbuilder.UMLNode;
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
            if (!reloader.isNecessary()) {
                reloader.setNecessary(true);
                reloader.reload();
            }
            org.acm.seguin.ide.jbuilder.UMLNodeViewer viewer = new org.acm.seguin.ide.jbuilder.UMLNodeViewer(context, reloader);
            return viewer;
        }
        return null;
    }

    /**
     * Creates the node viewer
     *
     * @param summary
     * 		Description of Parameter
     * @return the viewer
     */
    public com.borland.primetime.ide.NodeViewer createNodeViewer(org.acm.seguin.summary.PackageSummary summary) {
        if (!reloader.isNecessary()) {
            reloader.setNecessary(true);
            reloader.reload();
        }
        org.acm.seguin.ide.jbuilder.UMLNodeViewer viewer = new org.acm.seguin.ide.jbuilder.UMLNodeViewer(summary, reloader);
        return viewer;
    }

    /**
     * Gets the Factory attribute of the UMLNodeViewerFactory class
     *
     * @return The Factory value
     */
    public static org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory getFactory() {
        if (org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.factory == null) {
            org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.factory = new org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory();
        }
        return org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.factory;
    }
}