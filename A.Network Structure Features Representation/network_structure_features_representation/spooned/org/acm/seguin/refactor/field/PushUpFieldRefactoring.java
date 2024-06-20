/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Performs the pullup field refactoring
 *
 * @author Chris Seguin
 */
public class PushUpFieldRefactoring extends org.acm.seguin.refactor.field.FieldRefactoring {
    private org.acm.seguin.summary.TypeSummary parentType;

    /**
     * Constructor for the PushUpFieldRefactoring object
     */
    protected PushUpFieldRefactoring() {
    }

    /**
     * Gets the description of the refactoring
     *
     * @return the description
     */
    public java.lang.String getDescription() {
        return (("Moves a field " + field) + " into parent class named ") + parentType.getName();
    }

    /**
     * Gets the ID attribute of the PushUpFieldRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.PUSH_UP_FIELD;
    }

    /**
     * Preconditions that must be true for the refactoring to work
     *
     * @exception RefactoringException
     * 		a problem with performing this
     * 		refactoring
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        if (field == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No field specified");
        }
        if (typeSummary == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No type specified");
        }
        if (org.acm.seguin.summary.query.FieldQuery.query(typeSummary, field, org.acm.seguin.summary.query.FieldQuery.PRIVATE) == null) {
            throw new org.acm.seguin.refactor.RefactoringException((("Field named " + field) + " does not exist in ") + typeSummary.getName());
        }
        org.acm.seguin.summary.TypeDeclSummary parentDecl = typeSummary.getParentClass();
        parentType = org.acm.seguin.summary.query.GetTypeSummary.query(parentDecl);
        if (parentType == null) {
            throw new org.acm.seguin.refactor.RefactoringException("Can't push up a field into source code that you don't have");
        }
        checkDestinationFile(parentType, "Can't push up a field into source code that you don't have");
        if (org.acm.seguin.summary.query.FieldQuery.query(parentType, field, org.acm.seguin.summary.query.FieldQuery.PRIVATE) != null) {
            throw new org.acm.seguin.refactor.RefactoringException(("Field named " + field) + " already exists in parent class");
        }
        if (org.acm.seguin.summary.query.FieldQuery.queryAncestors(typeSummary, field, org.acm.seguin.summary.query.FieldQuery.PRIVATE) != null) {
            throw new org.acm.seguin.refactor.RefactoringException(("Field named " + field) + " already exists in an ancestor class");
        }
        if (((org.acm.seguin.summary.FileSummary) (parentType.getParent())).getFile() == null) {
            throw new org.acm.seguin.refactor.RefactoringException("Can't push up a field into source code that you don't have");
        }
        if (((org.acm.seguin.summary.FileSummary) (typeSummary.getParent())).getFile() == null) {
            throw new org.acm.seguin.refactor.RefactoringException("Can't push up a field from source code that you don't have");
        }
    }

    /**
     * Actually update the files
     */
    protected void transform() {
        org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (getFileSummary(typeSummary)));
        org.acm.seguin.refactor.field.RemoveFieldTransform rft = new org.acm.seguin.refactor.field.RemoveFieldTransform(field);
        org.acm.seguin.refactor.ComplexTransform transform = getComplexTransform();
        transform.add(rft);
        transform.apply(fileSummary.getFile(), fileSummary.getFile());
        // Update the field declaration to have the proper permissions
        org.acm.seguin.parser.ast.SimpleNode fieldDecl = rft.getFieldDeclaration();
        if (fieldDecl == null) {
            return;
        }
        org.acm.seguin.parser.ast.ASTFieldDeclaration decl = ((org.acm.seguin.parser.ast.ASTFieldDeclaration) (fieldDecl.jjtGetChild(0)));
        org.acm.seguin.pretty.ModifierHolder holder = decl.getModifiers();
        if (!holder.isPublic()) {
            holder.setPrivate(false);
            holder.setProtected(true);
        }
        org.acm.seguin.refactor.field.AddFieldTransform aft = new org.acm.seguin.refactor.field.AddFieldTransform(fieldDecl);
        transform.clear();
        transform.add(aft);
        java.lang.Object fieldType = getFieldType(fieldDecl, fileSummary);
        if (fieldType == null) {
            // Do nothing
        } else if ((fieldType instanceof org.acm.seguin.summary.TypeSummary) && (!isInJavaLang(((org.acm.seguin.summary.TypeSummary) (fieldType))))) {
            transform.add(new org.acm.seguin.refactor.AddImportTransform(((org.acm.seguin.summary.TypeSummary) (fieldType))));
        } else if ((fieldType instanceof org.acm.seguin.parser.ast.ASTName) && (!isInJavaLang(((org.acm.seguin.parser.ast.ASTName) (fieldType))))) {
            transform.add(new org.acm.seguin.refactor.AddImportTransform(((org.acm.seguin.parser.ast.ASTName) (fieldType))));
        }
        org.acm.seguin.summary.FileSummary parentFileSummary = ((org.acm.seguin.summary.FileSummary) (parentType.getParent()));
        transform.apply(parentFileSummary.getFile(), parentFileSummary.getFile());
        // Remove the field from all child classes
        new org.acm.seguin.refactor.field.RemoveFieldFromSubclassVisitor(parentType, typeSummary.getField(field), typeSummary, transform).visit(null);
    }
}