/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.elixir;
/**
 * View manager for a particular UML file
 *
 * @author Chris Seguin
 */
public class UMLViewManager implements org.acm.seguin.ide.elixir.ViewManager {
    private org.acm.seguin.ide.elixir.UMLDocManager docManager;

    private org.acm.seguin.uml.UMLPackage packagePanel;

    private org.acm.seguin.summary.PackageSummary summary;

    private javax.swing.JScrollPane pane;

    private java.lang.String filename;

    private java.lang.String packageName;

    /**
     * Constructor for the UMLViewManager object
     *
     * @param parent
     * 		the parent document manager
     * @param name
     * 		the name of the file to view
     * @param base
     * 		Description of Parameter
     */
    public UMLViewManager(org.acm.seguin.ide.elixir.UMLDocManager parent, java.lang.String name, java.lang.String base) {
        /* Creating this instance requires that the summaries
        have been loaded at least once, but shouldn't
        block further opertions.
         */
        org.acm.seguin.ide.common.SummaryLoaderThread.waitForLoading();
        docManager = parent;
        if (name != null) {
            filename = name;
            packagePanel = new org.acm.seguin.uml.UMLPackage(filename);
        } else {
            org.acm.seguin.ide.common.PackageSelectorDialog dialog = new org.acm.seguin.ide.common.PackageSelectorDialog(org.acm.seguin.ide.elixir.FrameManager.current().getFrame());
            dialog.setVisible(true);
            summary = dialog.getSummary();
            filename = null;
            packagePanel = new org.acm.seguin.uml.UMLPackage(summary);
        }
        parent.getReloader().add(packagePanel);
        pane = new javax.swing.JScrollPane(packagePanel, javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        javax.swing.JScrollBar horiz = pane.getHorizontalScrollBar();
        horiz.setUnitIncrement(400);
        javax.swing.JScrollBar vert = pane.getVerticalScrollBar();
        vert.setUnitIncrement(400);
        loadPackageName();
    }

    /**
     * Get the actions currently supported (may vary with state)
     *
     * @return The Actions value
     */
    public org.acm.seguin.ide.elixir.ActionEx[] getActions() {
        return new org.acm.seguin.ide.elixir.ActionEx[0];
    }

    /**
     * Get the document manager responsible for this view
     *
     * @return The DocManager value
     */
    public org.acm.seguin.ide.elixir.DocManager getDocManager() {
        return docManager;
    }

    /**
     * Get the title of the document being viewed
     *
     * @return The Title value
     */
    public java.lang.String getTitle() {
        if (packageName.length() > 0) {
            return packageName;
        } else {
            return "<Top Level Package>";
        }
    }

    /**
     * Get the view component which renders/edits the document
     *
     * @return The View value
     */
    public javax.swing.JComponent getView() {
        return pane;
    }

    /**
     * Gets the Diagram attribute of the UMLViewManager object
     *
     * @return The Diagram value
     */
    public org.acm.seguin.uml.UMLPackage getDiagram() {
        return packagePanel;
    }

    /**
     * Notify the view manager that it has been closed
     */
    public void closed() {
        save();
    }

    /**
     * Notify the view manager that it is about to close
     */
    public void closing() {
    }

    /**
     * Determine whether it is ok to close the view.
     *
     * @return Description of the Returned Value
     */
    public boolean okToClose() {
        return true;
    }

    /**
     * Reload the document from its storage (if it has one).
     *
     * @return Description of the Returned Value
     */
    public boolean reload() {
        packagePanel.reload();
        return true;
    }

    /**
     * Save the current document.
     *
     * @return Description of the Returned Value
     */
    public boolean save() {
        try {
            packagePanel.save();
        } catch (java.io.IOException ioe) {
            return false;
        }
        return true;
    }

    /**
     * Loads the package name from the file
     */
    private void loadPackageName() {
        if (filename == null) {
            packageName = summary.getName();
            return;
        }
        org.acm.seguin.ide.common.PackageNameLoader pnl = new org.acm.seguin.ide.common.PackageNameLoader();
        packageName = pnl.load(filename);
    }
}