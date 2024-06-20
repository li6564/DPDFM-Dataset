/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Pushes up the signature of an abstract method into the parent class
 *
 * @author Chris Seguin
 */
public class PushUpAbstractMethodRefactoring extends org.acm.seguin.refactor.method.PushUpMethodRefactoring {
    /**
     * Constructor for the PushUpAbstractMethodRefactoring object
     */
    protected PushUpAbstractMethodRefactoring() {
    }

    /**
     * Gets the ID attribute of the PushUpAbstractMethodRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.PUSH_UP_ABSTRACT_METHOD;
    }

    /**
     * Moves the method to the parent class
     */
    protected void transform() {
        org.acm.seguin.refactor.ComplexTransform transform = getComplexTransform();
        org.acm.seguin.summary.FileSummary fileSummary;
        org.acm.seguin.summary.TypeSummary typeSummary = ((org.acm.seguin.summary.TypeSummary) (methodSummary.getParent()));
        org.acm.seguin.pretty.ModifierHolder holder = methodSummary.getModifiers();
        if (!(holder.isPublic() || holder.isProtected())) {
            transform.add(new org.acm.seguin.refactor.method.ChangeMethodScopeTransform(methodSummary, org.acm.seguin.refactor.method.ChangeMethodScopeVisitor.PROTECTED));
            fileSummary = ((org.acm.seguin.summary.FileSummary) (typeSummary.getParent()));
            transform.apply(fileSummary.getFile(), fileSummary.getFile());
            transform.clear();
        }
        org.acm.seguin.summary.TypeDeclSummary parentDecl = typeSummary.getParentClass();
        org.acm.seguin.summary.TypeSummary parentSummary = org.acm.seguin.summary.query.GetTypeSummary.query(parentDecl);
        transform.add(new org.acm.seguin.refactor.method.AddAbstractMethod(methodSummary));
        org.acm.seguin.refactor.method.AddMethodTypeVisitor visitor = new org.acm.seguin.refactor.method.AddMethodTypeVisitor(false);
        methodSummary.accept(visitor, transform);
        fileSummary = ((org.acm.seguin.summary.FileSummary) (parentSummary.getParent()));
        transform.apply(fileSummary.getFile(), fileSummary.getFile());
    }
}