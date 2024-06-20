/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder.refactor;
import com.borland.jbuilder.node.JavaFileNode;
import com.borland.primetime.node.Node;
/**
 * Adds a child class
 *
 * @author Chris Seguin
 */
public class JBuilderAddChildClassAction extends org.acm.seguin.ide.common.action.AddChildClassAction {
    /**
     * Constructor for the AddChildClassAction object
     *
     * @param init
     * 		Description of Parameter
     */
    public JBuilderAddChildClassAction(com.borland.primetime.node.Node[] init) {
        super(new org.acm.seguin.ide.jbuilder.refactor.JBuilderSelectedFileSet(init));
    }
}