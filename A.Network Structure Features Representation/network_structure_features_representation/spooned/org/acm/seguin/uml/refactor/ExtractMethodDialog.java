/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
import org.acm.seguin.refactor.RefactoringException;
/**
 * User interface to enter the name of the method that was just extracted
 *
 * @author Chris Seguin
 */
public abstract class ExtractMethodDialog extends org.acm.seguin.uml.refactor.RefactoringDialog {
    // Instance Variables
    private javax.swing.JTextField newName;

    private org.acm.seguin.refactor.method.ExtractMethodRefactoring emr;

    private org.acm.seguin.awt.OrderableList list;

    private javax.swing.JRadioButton privateButton;

    private javax.swing.JRadioButton packageButton;

    private javax.swing.JRadioButton protectedButton;

    private javax.swing.JRadioButton publicButton;

    private javax.swing.JList returnList;

    private javax.swing.JTextField returnTextField;

    private javax.swing.JLabel signatureLabel;

    private org.acm.seguin.uml.refactor.SignatureUpdateAdapter sua;

    private javax.swing.JLabel sizer;

    private java.awt.Dimension originalSize;

    /**
     * Constructor for the ExtractMethodDialog object
     *
     * @param parent
     * 		the parent frame
     * @exception RefactoringException
     * 		problem in setting up the refactoring
     */
    public ExtractMethodDialog(javax.swing.JFrame parent) throws org.acm.seguin.refactor.RefactoringException {
        super(null, parent);
        sua = new org.acm.seguin.uml.refactor.SignatureUpdateAdapter(this);
        init();
        // Set the window size and layout
        setTitle(getWindowTitle());
        java.awt.GridBagLayout gridbag = new java.awt.GridBagLayout();
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        getContentPane().setLayout(gridbag);
        setSize(310, 120);
        java.awt.Insets zeroInsets = new java.awt.Insets(0, 0, 0, 0);
        // Add components
        int currentRow = 1;
        javax.swing.JLabel newNameLabel = new javax.swing.JLabel(getLabelText());
        gbc.gridx = 1;
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new java.awt.Insets(0, 0, 0, 10);
        gridbag.setConstraints(newNameLabel, gbc);
        getContentPane().add(newNameLabel);
        newName = new javax.swing.JTextField();
        newName.setColumns(40);
        newName.getDocument().addDocumentListener(sua);
        newName.addFocusListener(sua);
        gbc.gridx = 2;
        gbc.gridy = currentRow;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = zeroInsets;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(newName, gbc);
        getContentPane().add(newName);
        currentRow++;
        javax.swing.JLabel parameterLabel = new javax.swing.JLabel("Parameters:  ");
        gbc.gridx = 1;
        gbc.gridy = currentRow;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new java.awt.Insets(10, 0, 0, 0);
        getContentPane().add(parameterLabel, gbc);
        currentRow++;
        org.acm.seguin.summary.VariableSummary[] vs = emr.getParameters();
        if (vs.length > 1) {
            list = new org.acm.seguin.awt.OrderableList(vs, new org.acm.seguin.uml.refactor.VariableListCellRenderer());
            list.addListDataListener(sua);
            gbc.gridx = 1;
            gbc.gridy = currentRow;
            gbc.gridwidth = 3;
            gbc.gridheight = 1;
            gbc.insets = zeroInsets;
            gbc.fill = java.awt.GridBagConstraints.CENTER;
            gridbag.setConstraints(list, gbc);
            getContentPane().add(list);
        } else {
            javax.swing.JLabel label;
            if (vs.length == 0) {
                label = new javax.swing.JLabel("There are no parameters required for this method");
            } else {
                label = new javax.swing.JLabel("There is only one parameter required for this method:  " + vs[0].getName());
            }
            list = null;
            gbc.gridx = 1;
            gbc.gridy = currentRow;
            gbc.gridwidth = 3;
            gbc.gridheight = 1;
            gbc.insets = zeroInsets;
            gbc.fill = java.awt.GridBagConstraints.CENTER;
            gridbag.setConstraints(label, gbc);
            getContentPane().add(label);
        }
        currentRow++;
        javax.swing.JPanel panel = initRadioButtons();
        gbc.gridx = 1;
        gbc.gridy = currentRow;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.insets = zeroInsets;
        gbc.fill = java.awt.GridBagConstraints.CENTER;
        gridbag.setConstraints(panel, gbc);
        getContentPane().add(panel);
        currentRow++;
        javax.swing.JLabel returnNameLabel = new javax.swing.JLabel("Return:");
        gbc.gridx = 1;
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new java.awt.Insets(10, 0, 10, 10);
        gridbag.setConstraints(returnNameLabel, gbc);
        getContentPane().add(returnNameLabel);
        gbc.gridx = 2;
        gbc.gridy = currentRow;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = zeroInsets;
        gbc.insets = new java.awt.Insets(10, 0, 10, 0);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        if (emr.isStatement()) {
            returnList = new javax.swing.JList(emr.getReturnTypes());
            returnList.setSelectedIndex(0);
            gridbag.setConstraints(returnList, gbc);
            getContentPane().add(returnList);
            returnList.addListSelectionListener(sua);
            returnTextField = null;
        } else {
            returnTextField = new javax.swing.JTextField(emr.getReturnType().toString());
            gridbag.setConstraints(returnTextField, gbc);
            getContentPane().add(returnTextField);
            returnTextField.getDocument().addDocumentListener(sua);
            returnTextField.addFocusListener(sua);
            returnList = null;
        }
        currentRow++;
        javax.swing.JLabel signNameLabel = new javax.swing.JLabel("Signature:");
        gbc.gridx = 1;
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new java.awt.Insets(10, 0, 10, 10);
        gridbag.setConstraints(signNameLabel, gbc);
        getContentPane().add(signNameLabel);
        signatureLabel = new javax.swing.JLabel("");
        gbc.gridx = 2;
        gbc.gridy = currentRow;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = zeroInsets;
        gbc.insets = new java.awt.Insets(10, 0, 10, 0);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(signatureLabel, gbc);
        getContentPane().add(signatureLabel);
        currentRow++;
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        gbc.gridx = 2;
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = zeroInsets;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gridbag.setConstraints(okButton, gbc);
        okButton.addActionListener(this);
        getContentPane().add(okButton);
        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        gbc.gridx = 3;
        gbc.gridy = currentRow;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gridbag.setConstraints(cancelButton, gbc);
        cancelButton.addActionListener(this);
        getContentPane().add(cancelButton);
        update();
        pack();
        sizer = new javax.swing.JLabel();
        originalSize = signatureLabel.getSize();
        org.acm.seguin.awt.CenterDialog.center(this, parent);
    }

    /**
     * Gets the WindowTitle attribute of the ExtractMethodDialog object
     *
     * @return The WindowTitle value
     */
    public java.lang.String getWindowTitle() {
        return "Extract Method Dialog";
    }

    /**
     * Gets the LabelText attribute of the ExtractMethodDialog object
     *
     * @return The LabelText value
     */
    public java.lang.String getLabelText() {
        return "Method name:";
    }

    /**
     * Performs the update to the signature line
     */
    public void update() {
        createRefactoring();
        java.lang.String signature = emr.getSignature();
        if (sizer != null) {
            sizer.setText(signature);
            java.awt.Dimension current = sizer.getPreferredSize();
            int length = signature.length();
            while (current.width > originalSize.width) {
                length--;
                signature = signature.substring(0, length) + "...";
                sizer.setText(signature);
                current = sizer.getPreferredSize();
            } 
        }
        signatureLabel.setText(signature);
    }

    /**
     * Sets the StringInIDE attribute of the ExtractMethodDialog object
     *
     * @param value
     * 		The new StringInIDE value
     */
    protected abstract void setStringInIDE(java.lang.String value);

    /**
     * Gets the StringFromIDE attribute of the ExtractMethodDialog object
     *
     * @return The StringFromIDE value
     */
    protected abstract java.lang.String getStringFromIDE();

    /**
     * Gets the SelectionFromIDE attribute of the ExtractMethodDialog object
     *
     * @return The SelectionFromIDE value
     */
    protected abstract java.lang.String getSelectionFromIDE();

    /**
     * Follows up the refactoring by updating the text in the current window
     *
     * @param refactoring
     * 		Description of Parameter
     */
    protected void followup(org.acm.seguin.refactor.Refactoring refactoring) {
        org.acm.seguin.refactor.method.ExtractMethodRefactoring emr = ((org.acm.seguin.refactor.method.ExtractMethodRefactoring) (refactoring));
        setStringInIDE(emr.getFullFile());
    }

    /**
     * Creates the refactoring and fills in the data
     *
     * @return the extract method refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        emr.setMethodName(newName.getText());
        if (list == null) {
            // Don't need to set the parameter order!
        } else {
            emr.setParameterOrder(list.getData());
        }
        int prot = org.acm.seguin.refactor.method.ExtractMethodRefactoring.PRIVATE;
        if (packageButton.isSelected()) {
            prot = org.acm.seguin.refactor.method.ExtractMethodRefactoring.PACKAGE;
        }
        if (protectedButton.isSelected()) {
            prot = org.acm.seguin.refactor.method.ExtractMethodRefactoring.PROTECTED;
        }
        if (publicButton.isSelected()) {
            prot = org.acm.seguin.refactor.method.ExtractMethodRefactoring.PUBLIC;
        }
        emr.setProtection(prot);
        if (returnTextField == null) {
            java.lang.Object result = returnList.getSelectedValue();
            emr.setReturnType(result);
        } else {
            emr.setReturnType(returnTextField.getText());
        }
        return emr;
    }

    /**
     * Initialize the extract method refactoring
     *
     * @exception RefactoringException
     * 		Description of Exception
     */
    private void init() throws org.acm.seguin.refactor.RefactoringException {
        emr = org.acm.seguin.refactor.RefactoringFactory.get().extractMethod();
        java.lang.String full = getStringFromIDE();
        if (full == null) {
            dispose();
            throw new org.acm.seguin.refactor.RefactoringException("Invalid file for extracting the source code");
        } else {
            emr.setFullFile(full);
        }
        java.lang.String selection = getSelectionFromIDE();
        if (full == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "You must select a series of statements or an expression to extract.", "Extract Method Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            dispose();
            throw new org.acm.seguin.refactor.RefactoringException("Empty selection.");
        } else {
            emr.setSelection(selection);
        }
        emr.setMethodName("extractedMethod");
    }

    /**
     * Creates a panel of radio buttons with protection levels
     *
     * @return the panel
     */
    private javax.swing.JPanel initRadioButtons() {
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.FlowLayout());
        javax.swing.JLabel label = new javax.swing.JLabel("Protection:");
        panel.add(label);
        javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();
        privateButton = new javax.swing.JRadioButton("private");
        privateButton.setSelected(true);
        panel.add(privateButton);
        group.add(privateButton);
        privateButton.addActionListener(sua);
        packageButton = new javax.swing.JRadioButton("package");
        panel.add(packageButton);
        group.add(packageButton);
        packageButton.addActionListener(sua);
        protectedButton = new javax.swing.JRadioButton("protected");
        panel.add(protectedButton);
        group.add(protectedButton);
        protectedButton.addActionListener(sua);
        publicButton = new javax.swing.JRadioButton("public");
        panel.add(publicButton);
        group.add(publicButton);
        publicButton.addActionListener(sua);
        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        return panel;
    }
}