/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * Just the package selector area
 *
 * @author Chris Seguin
 */
public class PackageSelectorArea extends javax.swing.JPanel {
    /**
     * The list box of packages
     */
    protected javax.swing.JList listbox;

    private javax.swing.JScrollPane pane;

    /**
     * Constructor for the PackageSelectorArea object
     */
    public PackageSelectorArea() {
        // Setup the UI
        setLayout(null);
        super.setSize(220, 300);
        // Create the list
        listbox = new javax.swing.JList();
        pane = new javax.swing.JScrollPane(listbox);
        pane.setBounds(10, 10, 200, 280);
        add(pane);
    }

    /**
     * Gets the ScrollPane attribute of the PackageSelectorArea object
     *
     * @return The ScrollPane value
     */
    public javax.swing.JScrollPane getScrollPane() {
        return pane;
    }

    /**
     * Gets the Selection attribute of the PackageSelectorArea object
     *
     * @return The Selection value
     */
    public org.acm.seguin.summary.PackageSummary getSelection() {
        java.lang.Object[] selection = listbox.getSelectedValues();
        return ((org.acm.seguin.summary.PackageSummary) (selection[0]));
    }

    /**
     * Load the summaries
     */
    public void loadSummaries() {
    }

    /**
     * Loads the package into the listbox
     */
    public void loadPackages() {
        org.acm.seguin.uml.PackageSummaryListModel model = new org.acm.seguin.uml.PackageSummaryListModel();
        // Get the list of packages
        java.util.Iterator iter = org.acm.seguin.summary.PackageSummary.getAllPackages();
        if (iter == null) {
            return;
        }
        // Add in the packages
        org.acm.seguin.uml.UMLPackage view = null;
        org.acm.seguin.summary.PackageSummary packageSummary = null;
        org.acm.seguin.ide.common.PackageListFilter filter = org.acm.seguin.ide.common.PackageListFilter.get();
        while (iter.hasNext()) {
            packageSummary = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
            if (filter.isIncluded(packageSummary)) {
                model.add(packageSummary);
            }
        } 
        // Set the model
        listbox.setModel(model);
    }
}