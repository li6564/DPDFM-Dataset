/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Gets the type summary associated with a particular type declaration, file,
 *  or package.
 *
 * @author Chris Seguin
 * @created November 30, 1999
 */
public class GetTypeSummary {
    /**
     * Get the type summary that this object refers to. If the type summary is
     *  not found or the type is primitive, a null is returned. If the input is
     *  null, the output is also null.
     *
     * @param typeDecl
     * 		the place to start the search
     * @return the type summary or null
     */
    public static org.acm.seguin.summary.TypeSummary query(org.acm.seguin.summary.TypeDeclSummary typeDecl) {
        if (typeDecl == null) {
            return null;
        }
        // Check if it is primitive
        if (typeDecl.isPrimitive()) {
            return null;
        }
        java.lang.String packageName = typeDecl.getPackage();
        java.lang.String typeName = typeDecl.getType();
        if (packageName != null) {
            // Look up the type in the specified package
            org.acm.seguin.summary.PackageSummary packageSummary = org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName);
            return org.acm.seguin.summary.query.GetTypeSummary.query(packageSummary, typeName);
        } else {
            // Find file summary in parent
            org.acm.seguin.summary.Summary next = typeDecl.getParent();
            while (!(next instanceof org.acm.seguin.summary.FileSummary)) {
                next = next.getParent();
            } 
            org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (next));
            // Look up the type in the file summary
            return org.acm.seguin.summary.query.GetTypeSummary.query(fileSummary, typeName);
        }
    }

    /**
     * Searches a package for a particular type. This method returns the type,
     *  if it is found. If it is not found or the name is null, this method
     *  returns null.
     *
     * @param fileSummary
     * 		the file summary
     * @param name
     * 		the name of the type summary
     * @return the type summary if it is found and null otherwise
     */
    public static org.acm.seguin.summary.TypeSummary query(org.acm.seguin.summary.FileSummary fileSummary, java.lang.String name) {
        // System.out.println("Seaching " + fileSummary.getName() + "(FileSummary) for " + name);
        // Check for null pointers
        if (name == null) {
            return null;
        }
        org.acm.seguin.summary.TypeSummary result = null;
        // First try with the package
        result = org.acm.seguin.summary.query.GetTypeSummary.query(((org.acm.seguin.summary.PackageSummary) (fileSummary.getParent())), name);
        if (result != null) {
            // System.out.println("Found " + name + " in the current package");
            return result;
        }
        // Then try each import statement
        result = org.acm.seguin.summary.query.GetTypeSummary.checkImports(fileSummary, name);
        if (result != null) {
            // System.out.println("Found " + name + " in an import statement");
            return result;
        }
        // Try in java.lang
        org.acm.seguin.summary.PackageSummary nextPackage = org.acm.seguin.summary.PackageSummary.getPackageSummary("java.lang");
        result = org.acm.seguin.summary.query.GetTypeSummary.query(nextPackage, name);
        if (result != null) {
            // System.out.println("Found " + name + " in java.lang");
        }
        // Not found
        return result;
    }

    /**
     * Searches a package for a particular type. This method returns the type,
     *  if it is found. If it is not found or the name is null, this method
     *  returns null.
     *
     * @param packageSummary
     * 		the package summary
     * @param name
     * 		the name of the type summary
     * @return the type summary if it is found and null otherwise
     */
    public static org.acm.seguin.summary.TypeSummary query(org.acm.seguin.summary.PackageSummary packageSummary, java.lang.String name) {
        // System.out.println("Seaching " + packageSummary.getName() + "(PackageSummary) for " + name);
        // Check for null pointers
        if (name == null) {
            return null;
        }
        java.util.Iterator fileIterator = packageSummary.getFileSummaries();
        if (fileIterator != null) {
            while (fileIterator.hasNext()) {
                org.acm.seguin.summary.FileSummary nextFile = ((org.acm.seguin.summary.FileSummary) (fileIterator.next()));
                org.acm.seguin.summary.TypeSummary result = org.acm.seguin.summary.query.GetTypeSummary.checkType(nextFile, name);
                if (result != null) {
                    // System.out.println("Found " + name + " in " + nextFile.getName());
                    return result;
                }
            } 
        }
        // Not found
        return null;
    }

    /**
     * Searches a package for a particular type. This method returns the type,
     *  if it is found. If it is not found or the name is null, this method
     *  returns null.
     *
     * @param packageName
     * 		the package name
     * @param name
     * 		the name of the type summary
     * @return the type summary if it is found and null otherwise
     */
    public static org.acm.seguin.summary.TypeSummary query(java.lang.String packageName, java.lang.String name) {
        return org.acm.seguin.summary.query.GetTypeSummary.query(org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName), name);
    }

    /**
     * Finds a nested type based on the name of the object
     *
     * @param parent
     * 		the parent type
     * @param name
     * 		the name of the method
     * @return the type summary if found or null otherwise
     */
    public static org.acm.seguin.summary.TypeSummary query(org.acm.seguin.summary.TypeSummary parent, java.lang.String name) {
        java.util.Iterator iter = parent.getTypes();
        if (iter == null) {
            return null;
        }
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            if (next.getName().equals(name)) {
                return next;
            }
        } 
        return null;
    }

    /**
     * Finds a nested type based on the name of the object
     *
     * @param parent
     * 		the parent type
     * @param name
     * 		the name of the method
     * @return the type summary if found or null otherwise
     */
    public static org.acm.seguin.summary.TypeSummary query(org.acm.seguin.summary.MethodSummary parent, java.lang.String name) {
        java.util.Iterator iter = parent.getDependencies();
        if (iter == null) {
            return null;
        }
        while (iter.hasNext()) {
            org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
            if (next instanceof org.acm.seguin.summary.TypeSummary) {
                org.acm.seguin.summary.TypeSummary consider = ((org.acm.seguin.summary.TypeSummary) (next));
                if (consider.getName().equals(name)) {
                    return consider;
                }
            }
        } 
        return null;
    }

    /**
     * Searches for the type based on the imports
     *
     * @param fileSummary
     * 		the file summaries
     * @param name
     * 		the name we are looking for
     * @return Description of the Returned Value
     */
    private static org.acm.seguin.summary.TypeSummary checkImports(org.acm.seguin.summary.FileSummary fileSummary, java.lang.String name) {
        java.util.Iterator iter = fileSummary.getImports();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.ImportSummary next = ((org.acm.seguin.summary.ImportSummary) (iter.next()));
                // System.out.println("Seaching " + next.getPackage().getName()
                // + ((next.getType() == null) ? ".*" : ("." + next.getType()))
                // + "(ImportSummary) for " + name);
                java.lang.String nextTypeName = next.getType();
                if ((nextTypeName == null) || nextTypeName.equals(name)) {
                    org.acm.seguin.summary.PackageSummary nextPackage = next.getPackage();
                    org.acm.seguin.summary.TypeSummary result = org.acm.seguin.summary.query.GetTypeSummary.query(nextPackage, name);
                    if (result != null) {
                        return result;
                    }
                }
            } 
        }
        return null;
    }

    /**
     * Description of the Method
     *
     * @param summary
     * 		Description of Parameter
     * @param name
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private static org.acm.seguin.summary.TypeSummary checkType(org.acm.seguin.summary.FileSummary summary, java.lang.String name) {
        java.util.Iterator typeIterator = summary.getTypes();
        if (typeIterator != null) {
            while (typeIterator.hasNext()) {
                org.acm.seguin.summary.TypeSummary nextType = ((org.acm.seguin.summary.TypeSummary) (typeIterator.next()));
                // System.out.println("Seaching " + nextType.getName() + "(TypeSummary) for " + name);
                if ((nextType != null) && nextType.getName().equals(name)) {
                    return nextType;
                }
            } 
        }
        return null;
    }
}