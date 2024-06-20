/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Dialog for specifing where to push down the field into
 *
 * @author Chris Seguin
 */
class PushDownFieldDialog extends org.acm.seguin.uml.refactor.ChildrenCheckboxDialog {
    private java.lang.String field;

    /**
     * Constructor for the PushDownFieldDialog object
     *
     * @param init
     * 		Description of Parameter
     * @param name
     * 		Description of Parameter
     * @param initType
     * 		Description of Parameter
     */
    public PushDownFieldDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.TypeSummary initType, java.lang.String name) {
        super(init, initType);
        field = name;
        setTitle(((("Push field " + field) + " from ") + parentType.getName()) + " to:");
    }

    /**
     * Creates a refactoring to be performed
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        org.acm.seguin.refactor.field.PushDownFieldRefactoring pushDown = org.acm.seguin.refactor.RefactoringFactory.get().pushDownField();
        pushDown.setField(field);
        pushDown.setClass(parentType);
        java.util.Iterator iter = children.getCheckboxes();
        while (iter.hasNext()) {
            org.acm.seguin.uml.refactor.TypeCheckbox next = ((org.acm.seguin.uml.refactor.TypeCheckbox) (iter.next()));
            if (next.isSelected()) {
                pushDown.addChild(next.getTypeSummary());
            }
        } 
        return pushDown;
    }
}