package org.acm.seguin.refactor.type;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Adds a class that is either a parent or a child of an existing class.
 *
 * @author Chris Seguin
 */
public abstract class AddClassRefactoring extends org.acm.seguin.refactor.Refactoring {
    private java.util.Vector summaryList;

    private java.lang.String className = null;

    /**
     * Constructor for the AddClassRefactoring object
     */
    public AddClassRefactoring() {
        summaryList = new java.util.Vector();
    }

    /**
     * Sets the name of the new class
     *
     * @param value
     * 		the name of the new class
     */
    protected void setNewClassName(java.lang.String value) {
        className = value;
    }

    /**
     * Gets the name of the new class
     *
     * @return the name
     */
    protected java.lang.String getNewClassName() {
        return className;
    }

    /**
     * Adds a target class - either the parent or the child, depending on what
     *  we are adding
     *
     * @param summary
     * 		the summary to be extended
     */
    protected void addTargetClass(org.acm.seguin.summary.TypeSummary summary) {
        if (summary != null) {
            summaryList.addElement(summary);
        }
    }

    /**
     * Describes the preconditions that must be true for this refactoring to be
     *  applied
     *
     * @exception RefactoringException
     * 		thrown if one or more of the
     * 		preconditions is not satisfied. The text of the exception provides a
     * 		hint of what went wrong.
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        if (summaryList.size() == 0) {
            throw new org.acm.seguin.refactor.RefactoringException("Unable to find type to extend");
        }
        if (className == null) {
            throw new org.acm.seguin.refactor.RefactoringException("New class name is not specified");
        }
        // Get the package
        org.acm.seguin.summary.PackageSummary summary = getPackageSummary(((org.acm.seguin.summary.Summary) (summaryList.elementAt(0))));
        org.acm.seguin.summary.TypeSummary abstractParent = org.acm.seguin.summary.query.GetTypeSummary.query(summary, className);
        if (abstractParent != null) {
            throw new org.acm.seguin.refactor.RefactoringException("Type with that name already exists");
        }
        org.acm.seguin.summary.TypeSummary anySummary = ((org.acm.seguin.summary.TypeSummary) (summaryList.elementAt(0)));
        org.acm.seguin.summary.TypeSummary originalParent = org.acm.seguin.summary.query.GetTypeSummary.query(anySummary.getParentClass());
        Enumeration = summaryList.elements();
    }

    /**
     * Performs the transform on the rest of the classes
     */
    protected void transform() {
        Enumeration = summaryList.elements();
    }

    /**
     * Creates a class
     *
     * @param existingType
     * 		the existing type
     * @param className
     * 		the name of the new class
     */
    protected abstract void createClass(org.acm.seguin.summary.TypeSummary existingType, java.lang.String className);

    /**
     * Transforms the original AST
     *
     * @param typeSummary
     * 		the particular type that is being changed
     */
    protected abstract void transformOriginal(org.acm.seguin.summary.TypeSummary typeSummary);

    /**
     * Gets the package summary
     *
     * @param base
     * 		Description of Parameter
     * @return the package summary
     */
    private org.acm.seguin.summary.PackageSummary getPackageSummary(org.acm.seguin.summary.Summary base) {
        org.acm.seguin.summary.Summary current = base;
        while (!(current instanceof org.acm.seguin.summary.PackageSummary)) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.PackageSummary) (current));
    }

    /**
     * Gets the SameParent attribute of the AddClassRefactoring object
     *
     * @param one
     * 		Description of Parameter
     * @param two
     * 		Description of Parameter
     * @return The SameParent value
     */
    private boolean isSameParent(org.acm.seguin.summary.TypeSummary one, org.acm.seguin.summary.TypeSummary two) {
        if (isObject(one)) {
            return isObject(two);
        }
        if (isObject(two)) {
            return false;
        }
        return one.equals(two);
    }

    /**
     * Gets the Object attribute of the AddClassRefactoring object
     *
     * @param item
     * 		Description of Parameter
     * @return The Object value
     */
    private boolean isObject(org.acm.seguin.summary.TypeSummary item) {
        if (item == null) {
            return true;
        }
        if (item.getName().equals("Object")) {
            return true;
        }
        return false;
    }
}