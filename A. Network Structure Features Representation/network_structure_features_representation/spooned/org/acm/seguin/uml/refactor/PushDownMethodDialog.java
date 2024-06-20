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
class PushDownMethodDialog extends org.acm.seguin.uml.refactor.ChildrenCheckboxDialog {
    private org.acm.seguin.summary.MethodSummary methodSummary;

    /**
     * Constructor for the PushDownMethodDialog object
     *
     * @param init
     * 		Description of Parameter
     * @param initType
     * 		Description of Parameter
     * @param method
     * 		Description of Parameter
     */
    public PushDownMethodDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.TypeSummary initType, org.acm.seguin.summary.MethodSummary method) {
        super(init, initType);
        methodSummary = method;
        setTitle(((("Push field " + methodSummary.toString()) + " from ") + parentType.getName()) + " to:");
    }

    /**
     * Creates a refactoring to be performed
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        org.acm.seguin.refactor.method.PushDownMethodRefactoring pushDown = org.acm.seguin.refactor.RefactoringFactory.get().pushDownMethod();
        pushDown.setMethod(methodSummary);
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