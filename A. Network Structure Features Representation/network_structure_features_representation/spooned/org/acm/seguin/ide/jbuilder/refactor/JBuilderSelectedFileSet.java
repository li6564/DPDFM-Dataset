/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder.refactor;
import com.borland.jbuilder.node.JavaFileNode;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.node.Node;
import com.borland.primetime.vfs.Buffer;
/**
 * The concrete implementation of this class for JBuilder
 *
 * @author Chris Seguin
 */
public class JBuilderSelectedFileSet extends org.acm.seguin.ide.common.action.SelectedFileSet {
    private com.borland.primetime.node.Node[] initialNodes;

    /**
     * Constructor for the JBuilderSelectedFileSet object
     *
     * @param init
     * 		Description of Parameter
     */
    public JBuilderSelectedFileSet(com.borland.primetime.node.Node[] init) {
        initialNodes = init;
    }

    /**
     * Gets the AllJava attribute of the SelectedFileSet object
     *
     * @return The AllJava value
     */
    public boolean isAllJava() {
        com.borland.primetime.node.Node[] nodeArray = getNodes();
        for (int ndx = 0; ndx < nodeArray.length; ndx++) {
            if (!(nodeArray[0] instanceof com.borland.jbuilder.node.JavaFileNode)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the SingleJavaFile attribute of the SelectedFileSet object
     *
     * @return The SingleJavaFile value
     */
    public boolean isSingleJavaFile() {
        com.borland.primetime.node.Node[] nodeArray = getNodes();
        return (nodeArray.length == 1) && (nodeArray[0] instanceof com.borland.jbuilder.node.JavaFileNode);
    }

    /**
     * Gets the TypeSummaryArray attribute of the SelectedFileSet object
     *
     * @return The TypeSummaryArray value
     */
    public org.acm.seguin.summary.TypeSummary[] getTypeSummaryArray() {
        com.borland.primetime.node.Node[] nodeArray = getNodes();
        org.acm.seguin.summary.TypeSummary[] typeSummaryArray = new org.acm.seguin.summary.TypeSummary[nodeArray.length];
        for (int ndx = 0; ndx < nodeArray.length; ndx++) {
            org.acm.seguin.summary.TypeSummary typeSummary = getTypeSummaryFromNode(nodeArray[ndx]);
            if (typeSummary == null) {
                return null;
            }
            typeSummaryArray[ndx] = typeSummary;
        }
        return typeSummaryArray;
    }

    /**
     * Gets the TypeSummaryFromNode attribute of the AddParentClassAction object
     *
     * @param node
     * 		Description of Parameter
     * @return The TypeSummaryFromNode value
     */
    private org.acm.seguin.summary.TypeSummary getTypeSummaryFromNode(com.borland.primetime.node.Node node) {
        org.acm.seguin.summary.FileSummary fileSummary = reloadNode(node);
        if (fileSummary == null) {
            return null;
        }
        return getTypeSummary(fileSummary);
    }

    /**
     * Gets the Nodes attribute of the JBuilderRefactoringAction object
     *
     * @return The Nodes value
     */
    private com.borland.primetime.node.Node[] getNodes() {
        if (initialNodes == null) {
            com.borland.primetime.node.Node[] nodeArray = new com.borland.primetime.node.Node[1];
            com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
            nodeArray[0] = browser.getActiveNode();
            return nodeArray;
        } else {
            return initialNodes;
        }
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private org.acm.seguin.summary.FileSummary reloadNode(com.borland.primetime.node.Node node) {
        try {
            if (node instanceof com.borland.jbuilder.node.JavaFileNode) {
                com.borland.jbuilder.node.JavaFileNode jtn = ((com.borland.jbuilder.node.JavaFileNode) (node));
                com.borland.primetime.vfs.Buffer buffer = jtn.getBuffer();
                byte[] contents = buffer.getContent();
                java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(contents);
                return reloadFile(jtn.getUrl().getFileObject(), bais);
            }
        } catch (java.io.IOException ioe) {
            // Unable to get the buffer for that node, so fail
        }
        return null;
    }
}