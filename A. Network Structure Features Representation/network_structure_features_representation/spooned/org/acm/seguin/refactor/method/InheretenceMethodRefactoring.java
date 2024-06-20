package org.acm.seguin.refactor.method;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Moves a method between levels in the inheretence hierarchy
 *
 * @author Chris Seguin
 */
abstract class InheretenceMethodRefactoring extends org.acm.seguin.refactor.method.MethodRefactoring {
    /**
     * the method that is being moved around
     */
    protected org.acm.seguin.summary.MethodSummary methodSummary = null;

    /**
     * Sets the Method attribute of the PushUpMethodRefactoring object
     *
     * @param value
     * 		The new Method value
     */
    public void setMethod(org.acm.seguin.summary.MethodSummary value) {
        methodSummary = value;
    }

    /**
     * Description of the Method
     *
     * @param dest
     * 		Description of Parameter
     * @exception RefactoringException
     * 		Description of Exception
     */
    protected void checkDestination(org.acm.seguin.summary.TypeSummary dest) throws org.acm.seguin.refactor.RefactoringException {
        org.acm.seguin.summary.MethodSummary alternate = org.acm.seguin.summary.query.SameMethod.find(dest, methodSummary);
        if (alternate != null) {
            org.acm.seguin.pretty.ModifierHolder holder = alternate.getModifiers();
            if (!holder.isAbstract()) {
                throw new org.acm.seguin.refactor.RefactoringException(("A method with the same signature (name and parameter types) already exists in the " + dest.getName()) + " class");
            }
        } else if (org.acm.seguin.summary.query.SameMethod.findConflict(dest, methodSummary) != null) {
            throw new org.acm.seguin.refactor.RefactoringException(("A method with the conflicting signature (name and parameter types) already exists in the " + dest.getName()) + " class");
        }
    }

    /**
     * Description of the Method
     *
     * @param methodDecl
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected org.acm.seguin.parser.ast.ASTMethodDeclaration updateMethod(org.acm.seguin.parser.ast.SimpleNode methodDecl) {
        org.acm.seguin.parser.ast.ASTMethodDeclaration decl = ((org.acm.seguin.parser.ast.ASTMethodDeclaration) (methodDecl.jjtGetChild(0)));
        org.acm.seguin.pretty.ModifierHolder holder = decl.getModifiers();
        if (!holder.isPublic()) {
            holder.setPrivate(false);
            holder.setProtected(true);
        }
        return decl;
    }

    /**
     * Adds the method to the destination class
     *
     * @param transform
     * 		The feature to be added to the MethodToDest attribute
     * @param rft
     * 		The feature to be added to the MethodToDest attribute
     * @param methodDecl
     * 		The feature to be added to the MethodToDest attribute
     * @param dest
     * 		The feature to be added to the MethodToDest attribute
     */
    protected void addMethodToDest(org.acm.seguin.refactor.ComplexTransform transform, org.acm.seguin.refactor.method.RemoveMethodTransform rft, org.acm.seguin.parser.ast.SimpleNode methodDecl, org.acm.seguin.summary.TypeSummary dest) {
        transform.clear();
        transform.add(rft);
        org.acm.seguin.refactor.method.AddMethodTransform aft = new org.acm.seguin.refactor.method.AddMethodTransform(methodDecl);
        transform.add(aft);
        org.acm.seguin.refactor.method.AddMethodTypeVisitor visitor = new org.acm.seguin.refactor.method.AddMethodTypeVisitor();
        methodSummary.accept(visitor, transform);
        // Add appropriate import statements - to be determined later
        org.acm.seguin.summary.FileSummary parentFileSummary = ((org.acm.seguin.summary.FileSummary) (dest.getParent()));
        transform.apply(parentFileSummary.getFile(), parentFileSummary.getFile());
    }
}