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
 * Renames the class
 *
 * @author Chris Seguin
 */
public class JBuilderRenameClassAction extends org.acm.seguin.ide.common.action.RenameClassAction {
    /**
     * Constructor for the RenameClassAction object
     *
     * @param init
     * 		Description of Parameter
     */
    public JBuilderRenameClassAction(com.borland.primetime.node.Node[] init) {
        super(new org.acm.seguin.ide.jbuilder.refactor.JBuilderSelectedFileSet(init));
    }
}