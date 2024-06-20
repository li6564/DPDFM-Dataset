/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.primetime.ide.Context;
import com.borland.primetime.node.Node;
import com.borland.primetime.viewer.AbstractNodeViewer;
/**
 * Stores a view of a UML class diagram
 *
 * @author Chris Seguin
 */
public class UMLNodeViewer extends com.borland.primetime.viewer.AbstractNodeViewer {
    private org.acm.seguin.uml.UMLPackage diagram;

    private org.acm.seguin.ide.common.ClassDiagramReloader reloader;

    /**
     * Constructor for the UMLNodeViewer object
     *
     * @param summary
     * 		Description of Parameter
     * @param init
     * 		Description of Parameter
     */
    public UMLNodeViewer(org.acm.seguin.summary.PackageSummary summary, org.acm.seguin.ide.common.ClassDiagramReloader init) {
        super(null);
        diagram = new org.acm.seguin.uml.UMLPackage(summary);
        reloader = init;
        reloader.add(diagram);
    }

    /**
     * Constructor for the UMLNodeViewer object
     *
     * @param context
     * 		Description of Parameter
     * @param init
     * 		Description of Parameter
     */
    public UMLNodeViewer(com.borland.primetime.ide.Context context, org.acm.seguin.ide.common.ClassDiagramReloader init) {
        super(context);
        com.borland.primetime.node.Node node = context.getNode();
        if (node instanceof org.acm.seguin.ide.jbuilder.UMLNode) {
            org.acm.seguin.ide.jbuilder.UMLNode umlNode = ((org.acm.seguin.ide.jbuilder.UMLNode) (node));
            diagram = umlNode.getDiagram();
            if (diagram == null) {
                try {
                    diagram = new org.acm.seguin.uml.UMLPackage(umlNode.getInputStream());
                } catch (java.io.IOException ioe) {
                    org.acm.seguin.awt.ExceptionPrinter.print(ioe);
                    diagram = null;
                }
                umlNode.setDiagram(diagram);
            }
        } else {
            diagram = null;
        }
        reloader = init;
        reloader.add(diagram);
    }

    /**
     * Gets the ViewerTitle attribute of the UMLNodeViewer object
     *
     * @return The ViewerTitle value
     */
    public java.lang.String getViewerTitle() {
        return "Class Diagram";
    }

    /**
     * Gets the Diagram attribute of the UMLNodeViewer object
     *
     * @return The Diagram value
     */
    public org.acm.seguin.uml.UMLPackage getDiagram() {
        return diagram;
    }

    /**
     * Creates the main viewer
     *
     * @return the viewer
     */
    public javax.swing.JComponent createViewerComponent() {
        if (diagram == null) {
            return null;
        }
        javax.swing.JScrollPane pane = new javax.swing.JScrollPane(diagram, javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        javax.swing.JScrollBar horiz = pane.getHorizontalScrollBar();
        horiz.setUnitIncrement(400);
        javax.swing.JScrollBar vert = pane.getVerticalScrollBar();
        vert.setUnitIncrement(400);
        diagram.setScrollPane(pane);
        return pane;
    }

    /**
     * Creates a summary component, which is blank
     *
     * @return the component
     */
    public javax.swing.JComponent createStructureComponent() {
        org.acm.seguin.ide.common.DividedSummaryPanel dsp = new org.acm.seguin.ide.common.DividedSummaryPanel(diagram.getSummary(), diagram);
        return dsp.getPane();
    }

    /**
     * Releases the viewer
     */
    public void releaseViewer() {
        try {
            diagram.save();
        } catch (java.io.IOException ioe) {
        }
        reloader.remove(diagram);
    }
}