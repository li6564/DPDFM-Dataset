/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
/**
 * Reloads class diagrams
 *
 * @author Chris Seguin
 */
public class ReloadAction extends org.acm.seguin.ide.jbuilder.JBuilderAction {
    /**
     * Constructor for the PrintAction object
     */
    public ReloadAction() {
        putValue(org.acm.seguin.ide.jbuilder.NAME, "Load Refactoring Metadata");
        putValue(org.acm.seguin.ide.jbuilder.SHORT_DESCRIPTION, "Load Refactoring Metadata");
        putValue(org.acm.seguin.ide.jbuilder.LONG_DESCRIPTION, "Reloads the metadata for the class diagrams");
    }

    /**
     * Gets the Enabled attribute of the PrettyPrinterAction object
     *
     * @return The Enabled value
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * The pretty printer action
     *
     * @param evt
     * 		the action that occurred
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        org.acm.seguin.ide.common.MultipleDirClassDiagramReloader reloader = org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.getFactory().getReloader();
        reloader.setNecessary(true);
        reloader.reload();
        putValue(org.acm.seguin.ide.jbuilder.NAME, "Reload Refactoring Metadata");
        putValue(org.acm.seguin.ide.jbuilder.SHORT_DESCRIPTION, "Reload Refactoring Metadata");
    }
}