package org.acm.seguin.uml.refactor;
/**
 * Description of the Class
 *
 * @author Grant Watson
 * @created November 30, 2000
 */
public class ExtractInterfaceListener extends org.acm.seguin.uml.refactor.DialogViewListener {
    private org.acm.seguin.uml.UMLPackage current;

    private org.acm.seguin.summary.TypeSummary[] typeArray;

    /**
     * Constructor for the ExtractInterfaceListener object
     *
     * @param initPackage
     * 		Description of Parameter
     * @param initType
     * 		Description of Parameter
     * @param initMenu
     * 		Description of Parameter
     * @param initItem
     * 		Description of Parameter
     */
    public ExtractInterfaceListener(org.acm.seguin.uml.UMLPackage initPackage, org.acm.seguin.summary.TypeSummary[] initTypes, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
        current = initPackage;
        typeArray = initTypes;
    }

    /**
     * Creates an appropriate dialog to prompt the user for additional input
     *
     * @return the dialog box
     */
    protected javax.swing.JDialog createDialog() {
        return new org.acm.seguin.uml.refactor.ExtractInterfaceDialog(current, typeArray);
    }
}