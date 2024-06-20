/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Pushes up a method into a parent class
 *
 * @author Chris Seguin
 * @created April 5, 2000
 */
public class PushUpMethodRefactoring extends org.acm.seguin.refactor.method.InheretenceMethodRefactoring {
    private org.acm.seguin.summary.TypeSummary typeSummary;

    private org.acm.seguin.summary.TypeSummary parentType;

    /**
     * Constructor for the PushUpMethodRefactoring object
     */
    protected PushUpMethodRefactoring() {
    }

    /**
     * Gets the description of the refactoring
     *
     * @return the description
     */
    public java.lang.String getDescription() {
        return (("Moves a method " + methodSummary.getName()) + " down into the parent class ") + parentType.getName();
    }

    /**
     * Gets the ID attribute of the PushUpMethodRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.PUSH_UP_METHOD;
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
        org.acm.seguin.summary.TypeDeclSummary parent = typeSummary.getParentClass();
        parentType = org.acm.seguin.summary.query.GetTypeSummary.query(parent);
        checkDestination(parentType);
        checkDestinationFile(parentType, "Can't push up a method into source code that you don't have");
        org.acm.seguin.refactor.method.NearMissVisitor nmv = new org.acm.seguin.refactor.method.NearMissVisitor(parentType, methodSummary, typeSummary);
        nmv.visit(null);
        if (nmv.getProblem() != null) {
            throw new org.acm.seguin.refactor.RefactoringException((("Method with a signature of " + nmv.getProblem().toString()) + " found in child of ") + parentType.getName());
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
        addMethodToDest(transform, rft, methodDecl, parentType);
        // Remove the method from all child classes
        new org.acm.seguin.refactor.method.RemoveMethodFromSubclassVisitor(parentType, methodSummary, typeSummary, transform).visit(null);
    }
}