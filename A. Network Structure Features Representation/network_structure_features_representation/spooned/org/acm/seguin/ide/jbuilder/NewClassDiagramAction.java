/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.ide.NodeViewer;
import com.borland.primetime.node.Node;
import com.borland.primetime.node.Project;
import com.borland.primetime.vfs.Url;
/**
 * Package selector dialog box
 *
 * @author Chris Seguin
 */
public class NewClassDiagramAction extends org.acm.seguin.ide.jbuilder.JBuilderAction {
    /**
     * Constructor for the PrettyPrinterAction object
     */
    public NewClassDiagramAction() {
        putValue(org.acm.seguin.ide.jbuilder.NAME, "New UML Class Diagram");
        putValue(org.acm.seguin.ide.jbuilder.SHORT_DESCRIPTION, "New UML Class Diagram");
        putValue(org.acm.seguin.ide.jbuilder.LONG_DESCRIPTION, "Creates a new UML class diagram");
    }

    /**
     * Gets the Enabled attribute of the PrettyPrinterAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        org.acm.seguin.ide.common.MultipleDirClassDiagramReloader reloader = org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.getFactory().getReloader();
        return reloader.isNecessary();
    }

    /**
     * The pretty printer action
     *
     * @param evt
     * 		the action that occurred
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        org.acm.seguin.ide.common.PackageSelectorDialog psd = new org.acm.seguin.ide.common.PackageSelectorDialog(browser);
        psd.setVisible(true);
        org.acm.seguin.summary.PackageSummary summary = psd.getSummary();
        if (summary == null) {
            return;
        }
        com.borland.primetime.node.Project proj = browser.getActiveProject();
        com.borland.primetime.node.Node parent = proj;
        java.io.File diagramFile = getFile(summary);
        createFile(diagramFile, summary);
        com.borland.primetime.vfs.Url url = new com.borland.primetime.vfs.Url(diagramFile);
        org.acm.seguin.ide.jbuilder.UMLNode node = ((org.acm.seguin.ide.jbuilder.UMLNode) (proj.findNode(url)));
        if (node == null) {
            try {
                node = new org.acm.seguin.ide.jbuilder.UMLNode(proj, parent, url);
            } catch (com.borland.primetime.node.DuplicateNodeException dne) {
                dne.printStackTrace(java.lang.System.out);
            }
        }
        try {
            browser.setActiveNode(node, true);
            // NodeViewer[] viewers = browser.getViewers(node);
            // for (int ndx = 0; ndx < viewers.length; ndx++) {
            // if (viewers[ndx] instanceof UMLNodeViewer) {
            // browser.setActiveViewer(node, viewers[ndx], true);
            // }
            // }
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the file associated with the package summary
     *
     * @param summary
     * 		the package summary
     * @return the file to get
     */
    private java.io.File getFile(org.acm.seguin.summary.PackageSummary summary) {
        java.io.File dir = summary.getDirectory();
        java.io.File inputFile;
        if (dir == null) {
            dir = new java.io.File((((java.lang.System.getProperty("user.home") + java.io.File.separator) + ".Refactory") + java.io.File.separator) + "UML");
            dir.mkdirs();
            inputFile = new java.io.File(dir, summary.getName() + ".uml");
        } else {
            inputFile = new java.io.File(summary.getDirectory(), "package.uml");
        }
        return inputFile;
    }

    /**
     * Creates a file if one does not yet exist
     *
     * @param diagramFile
     * 		the file to create
     * @param summary
     * 		the associated package
     */
    private void createFile(java.io.File diagramFile, org.acm.seguin.summary.PackageSummary summary) {
        if (!diagramFile.exists()) {
            try {
                java.io.FileWriter output = new java.io.FileWriter(diagramFile);
                output.write(("V[1.1:" + summary.getName()) + "]\n");
                output.close();
            } catch (java.io.IOException ioe) {
            }
        }
    }
}