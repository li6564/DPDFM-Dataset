/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Dialog box that gets input for renaming the parameter
 *
 * @author Chris Seguin
 */
class RenameParameterDialog extends org.acm.seguin.uml.refactor.ClassNameDialog {
    private org.acm.seguin.summary.ParameterSummary param;

    private org.acm.seguin.summary.MethodSummary method;

    private javax.swing.JComboBox parameterSelection;

    /**
     * Constructor for the RenameParameterDialog object
     *
     * @param init
     * 		Description of Parameter
     * @param initParam
     * 		Description of Parameter
     */
    public RenameParameterDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.ParameterSummary initParam) {
        super(init, 0);
        param = initParam;
        method = ((org.acm.seguin.summary.MethodSummary) (param.getParent()));
        if (method == null) {
            java.lang.System.out.println("No method specified");
        }
        setTitle(getWindowTitle());
        pack();
        org.acm.seguin.awt.CenterDialog.center(this, init);
    }

    /**
     * Constructor for the RenameParameterDialog object
     *
     * @param init
     * 		Description of Parameter
     * @param initMethod
     * 		Description of Parameter
     */
    public RenameParameterDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.MethodSummary initMethod) {
        super(init, 1);
        param = null;
        method = initMethod;
        if (method == null) {
            java.lang.System.out.println("No method specified");
        }
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        javax.swing.JLabel newNameLabel = new javax.swing.JLabel("Parameter:  ");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        java.awt.GridBagLayout gridbag = ((java.awt.GridBagLayout) (getContentPane().getLayout()));
        gridbag.setConstraints(newNameLabel, gbc);
        getContentPane().add(newNameLabel);
        parameterSelection = new javax.swing.JComboBox();
        java.util.Iterator iter = method.getParameters();
        while (iter.hasNext()) {
            parameterSelection.addItem(iter.next());
        } 
        parameterSelection.setEditable(false);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridbag.setConstraints(parameterSelection, gbc);
        getContentPane().add(parameterSelection);
        setTitle(getWindowTitle());
        pack();
        org.acm.seguin.awt.CenterDialog.center(this, init);
    }

    /**
     * Gets the WindowTitle attribute of the RenameParameterDialog object
     *
     * @return The WindowTitle value
     */
    public java.lang.String getWindowTitle() {
        if (param == null) {
            return "Renaming a parameter";
        }
        return (("Renaming the parameter " + param.getName()) + " in ") + method.getName();
    }

    /**
     * Gets the LabelText attribute of the RenameParameterDialog object
     *
     * @return The LabelText value
     */
    public java.lang.String getLabelText() {
        return "New parameter name:";
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        org.acm.seguin.refactor.method.RenameParameterRefactoring rpr = org.acm.seguin.refactor.RefactoringFactory.get().renameParameter();
        rpr.setMethodSummary(method);
        if (param == null) {
            java.lang.Object selection = parameterSelection.getSelectedItem();
            rpr.setParameterSummary(((org.acm.seguin.summary.ParameterSummary) (selection)));
        } else {
            rpr.setParameterSummary(param);
        }
        rpr.setNewName(getClassName());
        return rpr;
    }
}