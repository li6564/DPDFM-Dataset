/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class EliminatePackageImportVisitor extends org.acm.seguin.refactor.type.TypeChangeVisitor {
    private org.acm.seguin.summary.PackageSummary packageSummary;

    private java.util.LinkedList filterList = new java.util.LinkedList();

    /**
     * Constructor for the EliminatePackageImportVisitor object
     *
     * @param complex
     * 		Description of Parameter
     */
    public EliminatePackageImportVisitor(org.acm.seguin.refactor.ComplexTransform complex) {
        super(complex);
    }

    /**
     * Sets the Package attribute of the EliminatePackageImportVisitor object
     *
     * @param summary
     * 		The new Package value
     */
    public void setPackageSummary(org.acm.seguin.summary.PackageSummary summary) {
        packageSummary = summary;
    }

    /**
     * Adds a feature to the FilterClass attribute of the
     *  EliminatePackageImportVisitor object
     *
     * @param name
     * 		The feature to be added to the FilterClass attribute
     */
    public void addFilterClass(java.lang.String name) {
        filterList.add(name);
    }

    /**
     * Gets the File Specific Transform
     *
     * @param summary
     * 		Description of Parameter
     * @return The FileSpecificTransform value
     */
    protected org.acm.seguin.refactor.TransformAST getFileSpecificTransform(org.acm.seguin.summary.FileSummary summary) {
        return new org.acm.seguin.refactor.RemoveImportTransform(packageSummary);
    }

    /**
     * Gets the New Imports transform
     *
     * @param node
     * 		Description of Parameter
     * @param className
     * 		Description of Parameter
     * @return The NewImports value
     */
    protected org.acm.seguin.refactor.AddImportTransform getNewImports(org.acm.seguin.summary.FileSummary node, java.lang.String className) {
        return new org.acm.seguin.refactor.AddImportTransform(packageSummary.getName(), className);
    }

    /**
     * Gets the Remove Imports transform
     *
     * @param node
     * 		Description of Parameter
     * @return The transform
     */
    protected org.acm.seguin.refactor.RemoveImportTransform getRemoveImportTransform(org.acm.seguin.summary.ImportSummary node) {
        return null;
    }

    /**
     * Gets the list of classes to iterate over
     *
     * @param node
     * 		Description of Parameter
     * @return The list
     */
    protected java.util.LinkedList getAppropriateClasses(org.acm.seguin.summary.FileSummary node) {
        java.util.LinkedList list = new java.util.LinkedList();
        java.util.Iterator fileIterator = packageSummary.getFileSummaries();
        if (fileIterator != null) {
            while (fileIterator.hasNext()) {
                org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (fileIterator.next()));
                addTypesFromFile(fileSummary, list);
            } 
        }
        return list;
    }

    /**
     * Gets the reference to the file where the refactored output should be sent
     *
     * @param node
     * 		Description of Parameter
     * @return The NewFile value
     */
    protected java.io.File getNewFile(org.acm.seguin.summary.FileSummary node) {
        return node.getFile();
    }

    /**
     * Return the current package
     *
     * @return the current package of the class
     */
    protected java.lang.String getCurrentPackage() {
        return packageSummary.getName();
    }

    /**
     * Checks any preconditions
     *
     * @param summary
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected boolean preconditions(org.acm.seguin.summary.FileSummary summary) {
        if (summary.getParent() == packageSummary) {
            return false;
        }
        java.util.Iterator iter = summary.getImports();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.ImportSummary next = ((org.acm.seguin.summary.ImportSummary) (iter.next()));
                if (isImportingPackage(next)) {
                    return true;
                }
            } 
        }
        return false;
    }

    /**
     * Gets the RenamingTransform
     *
     * @param refactoring
     * 		The feature to be added to the RenamingTransforms
     * 		attribute
     * @param node
     * 		The feature to be added to the RenamingTransforms
     * 		attribute
     * @param className
     * 		The feature to be added to the RenamingTransforms
     * 		attribute
     */
    protected void addRenamingTransforms(org.acm.seguin.refactor.ComplexTransform refactoring, org.acm.seguin.summary.FileSummary node, java.lang.String className) {
    }

    /**
     * Gets the InFilter attribute of the EliminatePackageImportVisitor object
     *
     * @param type
     * 		Description of Parameter
     * @return The InFilter value
     */
    private boolean isInFilter(org.acm.seguin.summary.TypeSummary type) {
        java.util.Iterator iter = filterList.iterator();
        java.lang.String name = type.getName();
        while (iter.hasNext()) {
            if (name.equals(iter.next())) {
                return true;
            }
        } 
        return false;
    }

    /**
     * Determines if we are importing the package that we are eliminiating
     *
     * @param next
     * 		the import statement in question
     * @return true if this is the import statement
     */
    private boolean isImportingPackage(org.acm.seguin.summary.ImportSummary next) {
        return (next.getType() == null) && (next.getPackage() == packageSummary);
    }

    /**
     * Adds a feature to the TypesFromFile attribute of the
     *  EliminatePackageImportVisitor object
     *
     * @param fileSummary
     * 		The feature to be added to the TypesFromFile attribute
     * @param list
     * 		The feature to be added to the TypesFromFile attribute
     */
    private void addTypesFromFile(org.acm.seguin.summary.FileSummary fileSummary, java.util.LinkedList list) {
        java.util.Iterator typeIterator = fileSummary.getTypes();
        if (typeIterator != null) {
            while (typeIterator.hasNext()) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (typeIterator.next()));
                if (next.getModifiers().isPublic() && (!isInFilter(next))) {
                    list.add(next.getName());
                }
            } 
        }
    }
}