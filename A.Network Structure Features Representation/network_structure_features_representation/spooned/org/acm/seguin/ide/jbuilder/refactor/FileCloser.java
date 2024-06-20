/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder.refactor;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.node.FileNode;
import com.borland.primetime.node.Node;
import com.borland.primetime.vfs.Url;
/**
 * Makes sure that the node is no longer open before a refactoring is
 *  performed which will remove or move the node.
 *
 * @author Chris Seguin
 */
public class FileCloser {
    /**
     * Closes a file in JBuilder
     *
     * @param file
     * 		Description of Parameter
     */
    public static void close(java.io.File file) {
        // yikes! the nodes are about to be moved, so close them if they are open
        com.borland.primetime.ide.Browser browser = com.borland.primetime.ide.Browser.getActiveBrowser();
        com.borland.primetime.node.Node[] possible = browser.getOpenNodes();
        com.borland.primetime.node.Node nodeToClose = null;
        com.borland.primetime.vfs.Url url = new com.borland.primetime.vfs.Url(file);
        for (int ndx = 0; ndx < possible.length; ndx++) {
            if (possible[ndx] instanceof com.borland.primetime.node.FileNode) {
                com.borland.primetime.node.FileNode fileNode = ((com.borland.primetime.node.FileNode) (possible[ndx]));
                if (fileNode.getUrl().equals(url)) {
                    nodeToClose = fileNode;
                    break;
                }
            }
        }
        if (nodeToClose == null) {
            return;
        }
        try {
            browser.closeNode(nodeToClose);
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
        }
    }
}