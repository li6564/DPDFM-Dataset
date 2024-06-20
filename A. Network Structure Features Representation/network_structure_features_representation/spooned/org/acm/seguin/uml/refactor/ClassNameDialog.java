/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Prompts the user for a class name. The class name can then be used to
 *  rename a class, add an abstract parent, or add a child.
 *
 * @author Chris Seguin
 */
public abstract class ClassNameDialog extends org.acm.seguin.uml.refactor.RefactoringDialog {
    // Instance Variables
    private javax.swing.JTextField newName;

    /**
     * Constructor for ClassNameDialog
     *
     * @param init
     * 		The package where this operation is occuring
     * @param startRow
     * 		Description of Parameter
     */
    public ClassNameDialog(org.acm.seguin.uml.UMLPackage init, int startRow) {
        super(init);
        // Set the window size and layout
        setTitle(getWindowTitle());
        java.awt.GridBagLayout gridbag = new java.awt.GridBagLayout();
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        getContentPane().setLayout(gridbag);
        setSize(310, 120);
        // Add components
        javax.swing.JLabel newNameLabel = new javax.swing.JLabel(getLabelText());
        gbc.gridx = 1;
        gbc.gridy = startRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gridbag.setConstraints(newNameLabel, gbc);
        getContentPane().add(newNameLabel);
        newName = new javax.swing.JTextField();
        newName.setColumns(25);
        gbc.gridx = 2;
        gbc.gridy = startRow;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(newName, gbc);
        getContentPane().add(newName);
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        gbc.gridx = 2;
        gbc.gridy = startRow + 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gridbag.setConstraints(okButton, gbc);
        okButton.addActionListener(this);
        getContentPane().add(okButton);
        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        gbc.gridx = 3;
        gbc.gridy = startRow + 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gridbag.setConstraints(cancelButton, gbc);
        cancelButton.addActionListener(this);
        getContentPane().add(cancelButton);
        pack();
        org.acm.seguin.awt.CenterDialog.center(this, init);
    }

    /**
     * Returns the window title
     *
     * @return the title
     */
    public abstract java.lang.String getWindowTitle();

    /**
     * Gets the label for the text
     *
     * @return the text for the label
     */
    public abstract java.lang.String getLabelText();

    /**
     * Gets the ClassName attribute of the ClassNameDialog object Gets the
     *  ClassName attribute of the ClassNameDialog object
     *
     * @return The ClassName value
     */
    protected java.lang.String getClassName() {
        return newName.getText();
    }
}