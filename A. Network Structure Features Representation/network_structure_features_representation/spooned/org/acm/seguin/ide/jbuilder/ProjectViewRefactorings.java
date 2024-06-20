/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.primetime.actions.ActionGroup;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.ide.ContextActionProvider;
import com.borland.primetime.node.Node;
/**
 * Adds the refactorings onto the project view
 *
 * @author Chris Seguin
 */
public class ProjectViewRefactorings implements com.borland.primetime.ide.ContextActionProvider {
    /**
     * Gets the ContextAction attribute of the ProjectViewRefactorings object
     *
     * @param browser
     * 		Description of Parameter
     * @param nodes
     * 		Description of Parameter
     * @return The ContextAction value
     */
    public javax.swing.Action getContextAction(com.borland.primetime.ide.Browser browser, com.borland.primetime.node.Node[] nodes) {
        com.borland.primetime.actions.ActionGroup group = new com.borland.primetime.actions.ActionGroup("JRefactory");
        group.setPopup(true);
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderRenameClassAction(nodes));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderMoveClassAction(nodes));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderAddParentClassAction(nodes));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderAddChildClassAction(nodes));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderRemoveClassAction(nodes));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderExtractInterfaceAction(nodes));
        return group;
    }
}