/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder.refactor;
import com.borland.jbuilder.node.JavaFileNode;
import com.borland.primetime.editor.EditorPane;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.node.Node;
import com.borland.primetime.vfs.Buffer;
import com.borland.primetime.viewer.AbstractTextNodeViewer;
/**
 * The object that determines the current summary for the JBuilder IDE.
 *
 * @author Chris Seguin
 */
public class JBuilderCurrentSummary extends org.acm.seguin.ide.common.action.CurrentSummary {
    private com.borland.primetime.node.Node lastActive;

    private com.borland.primetime.editor.EditorPane editor;

    /**
     * Constructor for the JBuilderCurrentSummary object
     */
    public JBuilderCurrentSummary() {
        super();
        editor = null;
    }

    /**
     * Gets the ActiveNode attribute of the CurrentSummary object
     *
     * @return The ActiveNode value
     */
    protected com.borland.primetime.node.Node getActiveNode() {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        return browser.getActiveNode();
    }

    /**
     * Register the current summary listener with the current document
     */
    protected void registerWithCurrentDocument() {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        com.borland.primetime.node.Node temp = getActiveNode();
        if (temp != lastActive) {
            lastActive = temp;
            upToDate = false;
            if (editor != null) {
                editor.getDocument().removeDocumentListener(this);
            }
            if ((lastActive == null) || (!(lastActive instanceof com.borland.jbuilder.node.JavaFileNode))) {
                return;
            }
            com.borland.primetime.viewer.AbstractTextNodeViewer sourceViewer = ((com.borland.primetime.viewer.AbstractTextNodeViewer) (browser.getViewerOfType(lastActive, com.borland.primetime.viewer.AbstractTextNodeViewer.class)));
            editor = sourceViewer.getEditor();
            editor.getDocument().addDocumentListener(this);
        }
    }

    /**
     * Gets the reloader
     *
     * @return The MetadataReloader value
     */
    protected org.acm.seguin.ide.common.MultipleDirClassDiagramReloader getMetadataReloader() {
        return org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.getFactory().getReloader();
    }
}