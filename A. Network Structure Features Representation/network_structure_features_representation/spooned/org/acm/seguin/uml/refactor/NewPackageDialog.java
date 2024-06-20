/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Creates a dialog box to prompt for the new package name
 *
 * @author Chris Seguin
 */
public class NewPackageDialog extends javax.swing.JDialog implements java.awt.event.ActionListener {
    // Instance Variables
    private javax.swing.JComboBox packageName;

    private org.acm.seguin.summary.TypeSummary[] typeArray;

    /**
     * Constructor for NewPackageDialog
     *
     * @param initTypes
     * 		Description of Parameter
     */
    public NewPackageDialog(org.acm.seguin.summary.TypeSummary[] initTypes) {
        super();
        typeArray = initTypes;
        // Set the window size and layout
        setTitle("Move class to new package");
        java.awt.GridBagLayout gridbag = new java.awt.GridBagLayout();
        getContentPane().setLayout(gridbag);
        setSize(310, 150);
        // Add components
        org.acm.seguin.uml.refactor.PackageList packageList = new org.acm.seguin.uml.refactor.PackageList();
        packageName = packageList.add(this);
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gridbag.setConstraints(okButton, gbc);
        okButton.addActionListener(this);
        getContentPane().add(okButton);
        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gridbag.setConstraints(cancelButton, gbc);
        cancelButton.addActionListener(this);
        getContentPane().add(cancelButton);
        javax.swing.JPanel blank = new javax.swing.JPanel();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        java.awt.Dimension blankDim = new java.awt.Dimension(50, cancelButton.getPreferredSize().height * 4);
        blank.setMinimumSize(blankDim);
        blank.setPreferredSize(blankDim);
        getContentPane().add(blank, gbc);
        pack();
        org.acm.seguin.awt.CenterDialog.center(this);
    }

    /**
     * Respond to a button press
     *
     * @param evt
     * 		the action event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getActionCommand().equals("OK")) {
            dispose();
            java.lang.String result = ((java.lang.String) (packageName.getSelectedItem()));
            if (result.startsWith("<")) {
                result = "";
            }
            repackage(result);
        } else if (evt.getActionCommand().equals("Cancel")) {
            dispose();
        }
    }

    /**
     * Repackage the files
     *
     * @param destinationPackage
     * 		the new package name
     */
    public void repackage(java.lang.String destinationPackage) {
        // Create the move class
        org.acm.seguin.refactor.type.MoveClass moveClass = org.acm.seguin.refactor.RefactoringFactory.get().moveClass();
        // Set the destination package
        moveClass.setDestinationPackage(destinationPackage);
        // Get the files
        java.lang.String parentDir = null;
        for (int ndx = 0; ndx < typeArray.length; ndx++) {
            parentDir = addType(typeArray[ndx], moveClass);
        }
        // Run it
        try {
            moveClass.run();
        } catch (org.acm.seguin.refactor.RefactoringException re) {
            javax.swing.JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        org.acm.seguin.uml.loader.ReloaderSingleton.reload();
    }

    /**
     * Adds a type to the refactoring
     *
     * @param moveClass
     * 		the refactoring
     * @param type
     * 		The feature to be added to the Type attribute
     * @return Description of the Returned Value
     */
    private java.lang.String addType(org.acm.seguin.summary.TypeSummary type, org.acm.seguin.refactor.type.MoveClass moveClass) {
        java.lang.String parentDir = null;
        org.acm.seguin.summary.FileSummary parent = ((org.acm.seguin.summary.FileSummary) (type.getParent()));
        java.io.File file = parent.getFile();
        if (file == null) {
            return null;
        }
        try {
            java.lang.String canonicalPath = file.getCanonicalPath();
            parentDir = new java.io.File(canonicalPath).getParent();
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
        moveClass.setDirectory(parentDir);
        moveClass.add(file.getName());
        return parentDir;
    }
}