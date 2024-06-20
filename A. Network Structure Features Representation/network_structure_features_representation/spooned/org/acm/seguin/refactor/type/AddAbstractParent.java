/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
/**
 * Adds an abstract parent to a class or a set of classes in the same
 *  package. The parent class that is created must be in the same package as
 *  the child classes that extend it. It is created with the package level
 *  scope, to minimize interactions elsewhere in the system. However, the
 *  other source files that import this class are proactively updated to
 *  minimize the name conflicts in the event that the user later wants to make
 *  the abstract class a public class.
 *
 * @author Chris Seguin
 */
public class AddAbstractParent extends org.acm.seguin.refactor.type.AddClassRefactoring {
    /**
     * Constructor for the AddAbstractParent object
     */
    protected AddAbstractParent() {
        super();
    }

    /**
     * Sets the ParentName attribute of the AddAbstractParent object
     *
     * @param parent
     * 		The new ParentName value
     */
    public void setParentName(java.lang.String parent) {
        setNewClassName(parent);
    }

    /**
     * Gets the description of the refactoring
     *
     * @return the description
     */
    public java.lang.String getDescription() {
        return "Adds a parent class named " + getNewClassName();
    }

    /**
     * Gets the id for this refactoring to track which refactorings are used.
     *
     * @return the id
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.ADD_PARENT;
    }

    /**
     * Sets the ChildClass attribute of the AddClassRefactoring object
     *
     * @param packageName
     * 		The feature to be added to the ChildClass attribute
     * @param className
     * 		The feature to be added to the ChildClass attribute
     */
    public void addChildClass(java.lang.String packageName, java.lang.String className) {
        addTargetClass(org.acm.seguin.summary.query.GetTypeSummary.query(org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName), className));
    }

    /**
     * Sets the ChildClass attribute of the AddClassRefactoring object
     *
     * @param summary
     * 		The feature to be added to the ChildClass attribute
     */
    public void addChildClass(org.acm.seguin.summary.TypeSummary summary) {
        addTargetClass(summary);
    }

    /**
     * Creates a class
     *
     * @param existingType
     * 		the existing type
     * @param className
     * 		the name of the new class
     */
    protected void createClass(org.acm.seguin.summary.TypeSummary existingType, java.lang.String className) {
        try {
            org.acm.seguin.refactor.type.CreateClass cc = new org.acm.seguin.refactor.type.CreateClass(existingType, className, true);
            java.io.File newFile = cc.run();
            getComplexTransform().createFile(newFile);
        } catch (org.acm.seguin.refactor.RefactoringException re) {
            java.lang.System.out.println(re.getMessage());
        }
    }

    /**
     * Transforms the original AST
     *
     * @param typeSummary
     * 		the particular type that is being changed
     */
    protected void transformOriginal(org.acm.seguin.summary.TypeSummary typeSummary) {
        org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (typeSummary.getParent()));
        java.io.File file = fileSummary.getFile();
        org.acm.seguin.summary.PackageSummary packageSummary = ((org.acm.seguin.summary.PackageSummary) (fileSummary.getParent()));
        org.acm.seguin.refactor.ComplexTransform ref = getComplexTransform();
        ref.add(createRenameType(typeSummary, packageSummary));
        ref.apply(file, file);
    }

    /**
     * Creates a rename parent type transformation
     *
     * @param typeSummary
     * 		the type to be changed
     * @param packageSummary
     * 		the package to be changed
     * @return the transform
     */
    org.acm.seguin.refactor.type.RenameParentTypeTransform createRenameType(org.acm.seguin.summary.TypeSummary typeSummary, org.acm.seguin.summary.PackageSummary packageSummary) {
        org.acm.seguin.refactor.type.RenameParentTypeTransform rptt = new org.acm.seguin.refactor.type.RenameParentTypeTransform();
        rptt.setNewName(getNewClassName());
        rptt.setOldName(typeSummary.getName());
        return rptt;
    }
}