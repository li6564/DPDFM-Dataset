/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Creates a label and a jcombo box and adds it into the JDialog. The combo
 *  box contains a list of all the packages that have been created so far.
 *  This assumes that the dialog box has used a GridBagLayout, and makes the
 *  label fill one column and the combo box fill 2 columns. <P>
 *
 *  The usage: <BR>
 *  <TT><BR>
 *  PackageList pl = new PackageList(); <BR>
 *  JComboBox save = pl.add(this); <BR>
 *  </TT>
 *
 * @author Chris Seguin
 */
class PackageList {
    /**
     * Adds a label and the combo box to the designated dialog
     *
     * @param dialog
     * 		the dialog window
     * @return the combo box that was added
     */
    public javax.swing.JComboBox add(javax.swing.JDialog dialog) {
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        javax.swing.JLabel packageLabel = new javax.swing.JLabel("Package:");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        dialog.getContentPane().add(packageLabel, gbc);
        javax.swing.JComboBox packageName = new javax.swing.JComboBox();
        packageName.setEditable(true);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        dialog.getContentPane().add(packageName, gbc);
        addPackages(packageName);
        javax.swing.JPanel blank = new javax.swing.JPanel();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        java.awt.Dimension blankDim = new java.awt.Dimension(50, packageName.getPreferredSize().height * 5);
        blank.setMinimumSize(blankDim);
        blank.setPreferredSize(blankDim);
        dialog.getContentPane().add(blank, gbc);
        return packageName;
    }

    /**
     * Fills in the combo box with the names of the packages
     *
     * @param comboBox
     * 		the combo box to fill in
     */
    private void addPackages(javax.swing.JComboBox comboBox) {
        // Add the package names
        java.util.Iterator iter = org.acm.seguin.summary.PackageSummary.getAllPackages();
        java.util.TreeSet set = new java.util.TreeSet();
        while (iter.hasNext()) {
            org.acm.seguin.summary.PackageSummary next = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
            set.add(next.toString());
        } 
        iter = set.iterator();
        while (iter.hasNext()) {
            comboBox.addItem(iter.next().toString());
        } 
    }
}