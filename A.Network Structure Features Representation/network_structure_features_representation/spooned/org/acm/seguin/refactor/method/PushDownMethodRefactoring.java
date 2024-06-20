/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Performs the push down method refactoring
 *
 * @author Chris Seguin
 */
public class PushDownMethodRefactoring extends org.acm.seguin.refactor.method.InheretenceMethodRefactoring {
    private java.util.LinkedList destinations;

    private org.acm.seguin.summary.TypeSummary typeSummary;

    /**
     * Constructor for the PushDownMethodRefactoring object
     */
    protected PushDownMethodRefactoring() {
        destinations = new java.util.LinkedList();
    }

    /**
     * Gets the description of the refactoring
     *
     * @return the description
     */
    public java.lang.String getDescription() {
        return (("Moves a method " + methodSummary.getName()) + " down into the child classes of ") + typeSummary.getName();
    }

    /**
     * Gets the ID attribute of the PushDownMethodRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.PUSH_DOWN_METHOD;
    }

    /**
     * Adds a feature to the Child attribute of the PushDownMethodRefactoring
     *  object
     *
     * @param type
     * 		The feature to be added to the Child attribute
     */
    public void addChild(org.acm.seguin.summary.TypeSummary type) {
        destinations.add(type);
    }

    /**
     * This specifies the preconditions for applying the refactoring
     *
     * @exception RefactoringException
     * 		Description of Exception
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        if (methodSummary == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No method specified");
        }
        typeSummary = ((org.acm.seguin.summary.TypeSummary) (methodSummary.getParent()));
        java.util.Iterator iter = destinations.iterator();
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            org.acm.seguin.summary.TypeDeclSummary parent = next.getParentClass();
            org.acm.seguin.summary.TypeSummary parentSummary = org.acm.seguin.summary.query.GetTypeSummary.query(parent);
            if (parentSummary != typeSummary) {
                throw new org.acm.seguin.refactor.RefactoringException((next.getName() + " is not a subclass of ") + typeSummary.getName());
            }
            checkDestination(next);
        } 
    }

    /**
     * Moves the method to the parent class
     */
    protected void transform() {
        org.acm.seguin.refactor.method.RemoveMethodTransform rft = new org.acm.seguin.refactor.method.RemoveMethodTransform(methodSummary);
        org.acm.seguin.refactor.ComplexTransform transform = getComplexTransform();
        removeMethod(typeSummary, transform, rft);
        // Update the method declaration to have the proper permissions
        org.acm.seguin.parser.ast.SimpleNode methodDecl = rft.getMethodDeclaration();
        if (methodDecl == null) {
            return;
        }
        org.acm.seguin.parser.ast.ASTMethodDeclaration decl = updateMethod(methodDecl);
        java.util.Iterator iter = destinations.iterator();
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            addMethodToDest(transform, rft, methodDecl, next);
        } 
    }

    /**
     * Description of the Method
     *
     * @param source
     * 		Description of Parameter
     * @param transform
     * 		Description of Parameter
     * @param rft
     * 		Description of Parameter
     */
    protected void removeMethod(org.acm.seguin.summary.TypeSummary source, org.acm.seguin.refactor.ComplexTransform transform, org.acm.seguin.refactor.method.RemoveMethodTransform rft) {
        transform.add(new org.acm.seguin.refactor.method.AddAbstractMethod(methodSummary));
        super.removeMethod(source, transform, rft);
    }
}