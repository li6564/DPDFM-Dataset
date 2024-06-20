/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.jbuilder.node.JavaFileNode;
import com.borland.primetime.editor.EditorPane;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.node.FileNode;
import com.borland.primetime.node.Node;
import com.borland.primetime.vfs.Buffer;
import com.borland.primetime.vfs.ReadOnlyException;
import com.borland.primetime.viewer.AbstractTextNodeViewer;
/**
 * The implementation of the editor operations for JBuilder
 *
 * @author Chris Seguin
 */
public class JBuilderEditorOperations extends org.acm.seguin.ide.common.EditorOperations {
    private com.borland.primetime.vfs.Buffer buffer;

    /**
     * Sets the string in the IDE
     *
     * @param value
     * 		The new file contained in a string
     */
    public void setStringInIDE(java.lang.String value) {
        if (value != null) {
            try {
                buffer.setContent(value.getBytes());
            } catch (com.borland.primetime.vfs.ReadOnlyException roe) {
                javax.swing.JOptionPane.showMessageDialog(null, "The file that you ran the pretty printer on is read only.", "Read Only Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Sets the line number
     *
     * @param value
     * 		The new LineNumber value
     */
    public void setLineNumber(int value) {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        com.borland.primetime.node.Node active = browser.getActiveNode();
        com.borland.primetime.viewer.AbstractTextNodeViewer sourceViewer = ((com.borland.primetime.viewer.AbstractTextNodeViewer) (browser.getViewerOfType(active, com.borland.primetime.viewer.AbstractTextNodeViewer.class)));
        com.borland.primetime.editor.EditorPane editor = sourceViewer.getEditor();
        editor.gotoPosition(value, 1, false, EditorPane.CENTER_ALWAYS);
    }

    /**
     * Gets the initial string from the IDE
     *
     * @return The file in string format
     */
    public java.lang.String getStringFromIDE() {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        com.borland.primetime.node.Node active = browser.getActiveNode();
        if (active instanceof com.borland.jbuilder.node.JavaFileNode) {
            com.borland.jbuilder.node.JavaFileNode jtn = ((com.borland.jbuilder.node.JavaFileNode) (active));
            try {
                buffer = jtn.getBuffer();
                byte[] contents = buffer.getContent();
                return new java.lang.String(contents);
            } catch (java.io.IOException ioex) {
                ioex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Returns the initial line number
     *
     * @return The LineNumber value
     */
    public int getLineNumber() {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        com.borland.primetime.node.Node active = browser.getActiveNode();
        if (active == null)
            return -1;

        com.borland.primetime.viewer.AbstractTextNodeViewer sourceViewer = ((com.borland.primetime.viewer.AbstractTextNodeViewer) (browser.getViewerOfType(active, com.borland.primetime.viewer.AbstractTextNodeViewer.class)));
        if (sourceViewer == null)
            return -1;

        com.borland.primetime.editor.EditorPane editor = sourceViewer.getEditor();
        if (editor == null)
            return -1;

        int pos = editor.getCaretPosition();
        return editor.getLineNumber(pos);
    }

    /**
     * Gets the SelectionFromIDE attribute of the JBuilderExtractMethod object
     *
     * @return The SelectionFromIDE value
     */
    public java.lang.String getSelectionFromIDE() {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        com.borland.primetime.node.Node active = browser.getActiveNode();
        com.borland.primetime.viewer.AbstractTextNodeViewer sourceViewer = ((com.borland.primetime.viewer.AbstractTextNodeViewer) (com.borland.primetime.ide.Browser.getActiveBrowser().getViewerOfType(active, com.borland.primetime.viewer.AbstractTextNodeViewer.class)));
        com.borland.primetime.editor.EditorPane editor = sourceViewer.getEditor();
        return editor.getSelectedText();
    }

    /**
     * Returns the frame that contains the editor. If this is not available or
     *  you want dialog boxes to be centered on the screen return null from this
     *  operation.
     *
     * @return the frame
     */
    public javax.swing.JFrame getEditorFrame() {
        return com.borland.primetime.ide.Browser.getActiveBrowser();
    }

    /**
     * Returns true if the current file being edited is a java file
     *
     * @return true if the current file is a java file
     */
    public boolean isJavaFile() {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        com.borland.primetime.node.Node active = browser.getActiveNode();
        return active instanceof com.borland.jbuilder.node.JavaFileNode;
    }

    /**
     * Gets the file that is being edited
     *
     * @return The File value
     */
    public java.io.File getFile() {
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        com.borland.primetime.node.Node active = browser.getActiveNode();
        if (active instanceof com.borland.primetime.node.FileNode) {
            return ((com.borland.primetime.node.FileNode) (active)).getUrl().getFileObject();
        } else {
            return null;
        }
    }
}