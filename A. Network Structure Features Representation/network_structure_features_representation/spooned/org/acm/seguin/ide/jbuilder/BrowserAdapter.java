/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.ide.BrowserListener;
import com.borland.primetime.ide.NodeViewer;
import com.borland.primetime.node.Node;
import com.borland.primetime.node.Project;
import com.borland.primetime.util.VetoException;
/**
 * Default implementation of the BrowserListener
 *
 * @author Chris Seguin
 */
public class BrowserAdapter implements com.borland.primetime.ide.BrowserListener {
    /**
     * Description of the Method
     *
     * @param browser
     * 		Description of Parameter
     */
    public void browserOpened(com.borland.primetime.ide.Browser browser) {
    }

    /**
     * Description of the Method
     *
     * @param browser
     * 		Description of Parameter
     */
    public void browserActivated(com.borland.primetime.ide.Browser browser) {
    }

    /**
     * Description of the Method
     *
     * @param browser
     * 		Description of Parameter
     */
    public void browserDeactivated(com.borland.primetime.ide.Browser browser) {
    }

    /**
     * Description of the Method
     *
     * @param browser
     * 		Description of Parameter
     * @exception VetoException
     * 		Description of Exception
     */
    public void browserClosing(com.borland.primetime.ide.Browser browser) throws com.borland.primetime.util.VetoException {
    }

    /**
     * Description of the Method
     *
     * @param browser
     * 		Description of Parameter
     */
    public void browserClosed(com.borland.primetime.ide.Browser browser) {
    }

    /**
     * A particular project was activated
     *
     * @param browser
     * 		The browser that it was activated in
     * @param project
     * 		The project
     */
    public void browserProjectActivated(com.borland.primetime.ide.Browser browser, com.borland.primetime.node.Project project) {
    }

    /**
     * A project was closed in a particular browser
     *
     * @param browser
     * 		the browser
     * @param project
     * 		the project
     */
    public void browserProjectClosed(com.borland.primetime.ide.Browser browser, com.borland.primetime.node.Project project) {
    }

    /**
     * Description of the Method
     *
     * @param browser
     * 		Description of Parameter
     * @param node
     * 		Description of Parameter
     */
    public void browserNodeActivated(com.borland.primetime.ide.Browser browser, com.borland.primetime.node.Node node) {
    }

    /**
     * Description of the Method
     *
     * @param browser
     * 		Description of Parameter
     * @param node
     * 		Description of Parameter
     */
    public void browserNodeClosed(com.borland.primetime.ide.Browser browser, com.borland.primetime.node.Node node) {
    }

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
    }

    /**
     * Description of the Method
     *
     * @param browser
     * 		Description of Parameter
     * @param node
     * 		Description of Parameter
     * @param viewer
     * 		Description of Parameter
     * @exception VetoException
     * 		Description of Exception
     */
    public void browserViewerDeactivating(com.borland.primetime.ide.Browser browser, com.borland.primetime.node.Node node, com.borland.primetime.ide.NodeViewer viewer) throws com.borland.primetime.util.VetoException {
    }
}