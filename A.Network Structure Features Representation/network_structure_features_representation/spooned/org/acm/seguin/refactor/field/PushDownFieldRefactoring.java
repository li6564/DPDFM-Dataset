/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Performs the push down field refactoring
 *
 * @author Chris Seguin
 */
public class PushDownFieldRefactoring extends org.acm.seguin.refactor.field.FieldRefactoring {
    private java.util.LinkedList childTypes;

    /**
     * Constructor for the PushDownFieldRefactoring object
     */
    protected PushDownFieldRefactoring() {
        childTypes = new java.util.LinkedList();
        field = null;
        typeSummary = null;
    }

    /**
     * Gets the description of the refactoring
     *
     * @return the description
     */
    public java.lang.String getDescription() {
        return (("Moves a field " + field) + " into child classes of ") + typeSummary.getName();
    }

    /**
     * Gets the ID attribute of the PushDownFieldRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.PUSH_DOWN_FIELD;
    }

    /**
     * Adds a child class where the field should be pushed into
     *
     * @param packageName
     * 		the package name
     * @param className
     * 		the class name
     */
    public void addChild(java.lang.String packageName, java.lang.String className) {
        addChild(org.acm.seguin.summary.query.GetTypeSummary.query(org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName), className));
    }

    /**
     * Adds a child class where the field should be pushed into
     *
     * @param init
     * 		The new Class value
     */
    public void addChild(org.acm.seguin.summary.TypeSummary init) {
        if (init != null) {
            java.lang.System.out.println("Adding " + init.getName());
            childTypes.add(init);
        }
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
        if (childTypes.size() == 0) {
            throw new org.acm.seguin.refactor.RefactoringException("No child types specified");
        }
        if (org.acm.seguin.summary.query.FieldQuery.query(typeSummary, field, org.acm.seguin.summary.query.FieldQuery.PRIVATE) == null) {
            throw new org.acm.seguin.refactor.RefactoringException((("Field named " + field) + " does not exist in ") + typeSummary.getName());
        }
        if (((org.acm.seguin.summary.FileSummary) (typeSummary.getParent())).getFile() == null) {
            throw new org.acm.seguin.refactor.RefactoringException("Can't push down a field from source code that you don't have");
        }
        java.util.Iterator iter = childTypes.iterator();
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            if (next == null) {
                throw new org.acm.seguin.refactor.RefactoringException("Can't push down a field into source code that you don't have");
            }
            if (org.acm.seguin.summary.query.FieldQuery.query(next, field, org.acm.seguin.summary.query.FieldQuery.PRIVATE) != null) {
                throw new org.acm.seguin.refactor.RefactoringException((("Field named " + field) + " already exists in ") + next.getName());
            }
            if (((org.acm.seguin.summary.FileSummary) (next.getParent())).getFile() == null) {
                throw new org.acm.seguin.refactor.RefactoringException("Can't push up a field into source code that you don't have");
            }
            org.acm.seguin.summary.TypeDeclSummary parentDecl = next.getParentClass();
            org.acm.seguin.summary.TypeSummary parentTypeSummary = org.acm.seguin.summary.query.GetTypeSummary.query(parentDecl);
            if (parentTypeSummary != typeSummary) {
                throw new org.acm.seguin.refactor.RefactoringException(((("Trying to push a field from " + typeSummary.getName()) + " to ") + next.getName()) + " and the destination is not a subclass of the source");
            }
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
        java.util.Iterator iter = childTypes.iterator();
        while (iter.hasNext()) {
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
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            org.acm.seguin.summary.FileSummary nextFileSummary = ((org.acm.seguin.summary.FileSummary) (next.getParent()));
            transform.apply(nextFileSummary.getFile(), nextFileSummary.getFile());
        } 
    }
}