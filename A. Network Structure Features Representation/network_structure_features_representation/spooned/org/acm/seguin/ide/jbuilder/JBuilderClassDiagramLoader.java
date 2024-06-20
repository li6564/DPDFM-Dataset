/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
/**
 * Responsible for reloading the class diagrams
 *
 * @author Chris Seguin
 */
public class JBuilderClassDiagramLoader extends org.acm.seguin.ide.common.MultipleDirClassDiagramReloader {
    /**
     * Reloads the diagrams (and the current jbuilder project)
     */
    public void reload() {
        super.reload();
        org.acm.seguin.ide.jbuilder.Browser.getActiveBrowser().getProjectView().refreshTree();
    }
}