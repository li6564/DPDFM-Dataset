/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary;
import java.io.IOException;
/**
 * Creates a summary of a package
 *
 * @author Chris Seguin
 * @created May 5, 1999
 */
public class PackageSummary extends org.acm.seguin.summary.Summary {
    // Instance Variables
    private java.lang.String name;

    private java.util.LinkedList fileList;

    // Class Variables
    private static java.util.HashMap packageMap;

    /**
     * Constructor for the package summary
     *
     * @param packageName
     * 		the name of the package
     */
    protected PackageSummary(java.lang.String packageName) {
        // Initialize package summary - packages have no parents
        super(null);
        name = packageName.intern();
        fileList = null;
    }

    /**
     * Get the name of the package
     *
     * @return the package name
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * Get a file summary by file name
     *
     * @param name
     * 		the name of the file summary
     * @return the file summary if it is found and null otherwise
     */
    public org.acm.seguin.summary.FileSummary getFileSummary(java.lang.String name) {
        // Check for null pointers
        if (name == null) {
            return null;
        }
        // Local Variables
        if (fileList != null) {
            java.util.Iterator iter = fileList.iterator();
            // Check for it
            while (iter.hasNext()) {
                org.acm.seguin.summary.FileSummary next = ((org.acm.seguin.summary.FileSummary) (iter.next()));
                if (name.equals(next.getName())) {
                    return next;
                }
            } 
        }
        // Hmm...  not found
        return null;
    }

    /**
     * Return an iterator of the files
     *
     * @return the iterator
     */
    public java.util.Iterator getFileSummaries() {
        if (fileList == null) {
            return null;
        }
        return fileList.iterator();
    }

    /**
     * Get the directory associated with this package
     *
     * @return a file or null if none
     */
    public java.io.File getDirectory() {
        java.util.Iterator iter = getFileSummaries();
        if (iter == null) {
            return null;
        }
        while (iter.hasNext()) {
            org.acm.seguin.summary.FileSummary next = ((org.acm.seguin.summary.FileSummary) (iter.next()));
            java.io.File result = next.getFile();
            if (result != null) {
                result = result.getParentFile();
                if (result != null) {
                    return result;
                }
            }
        } 
        return null;
    }

    /**
     * Determines if it is the top level package
     *
     * @return true if it is the top level
     */
    public boolean isTopLevel() {
        return (name == null) || (name.length() == 0);
    }

    /**
     * Delete a file summary
     *
     * @param fileSummary
     * 		the file summary object that we are removing
     */
    public void deleteFileSummary(org.acm.seguin.summary.FileSummary fileSummary) {
        if (fileSummary != null) {
            if (fileList == null) {
                initFileList();
            }
            fileList.remove(fileSummary);
        }
    }

    /**
     * Converts this object to a string
     *
     * @return the string
     */
    public java.lang.String toString() {
        if (!isTopLevel()) {
            return name;
        } else {
            return "<Top Level Package>";
        }
    }

    /**
     * Provide method to visit a node
     *
     * @param visitor
     * 		the visitor
     * @param data
     * 		the data for the visit
     * @return some new data
     */
    public java.lang.Object accept(org.acm.seguin.summary.SummaryVisitor visitor, java.lang.Object data) {
        return visitor.visit(this, data);
    }

    /**
     * Add a file summary
     *
     * @param fileSummary
     * 		the file summary that we are adding
     */
    protected void addFileSummary(org.acm.seguin.summary.FileSummary fileSummary) {
        if (fileSummary != null) {
            if (fileList == null) {
                initFileList();
            }
            fileList.add(fileSummary);
        }
    }

    /**
     * Initialize the file list
     */
    private void initFileList() {
        fileList = new java.util.LinkedList();
    }

    /**
     * Get a package summary object
     *
     * @param name
     * 		the name of the package that we are creating
     * @return The PackageSummary value
     */
    public static org.acm.seguin.summary.PackageSummary getPackageSummary(java.lang.String name) {
        if (org.acm.seguin.summary.PackageSummary.packageMap == null) {
            org.acm.seguin.summary.PackageSummary.init();
        }
        org.acm.seguin.summary.PackageSummary result = ((org.acm.seguin.summary.PackageSummary) (org.acm.seguin.summary.PackageSummary.packageMap.get(name)));
        if (result == null) {
            result = new org.acm.seguin.summary.PackageSummary(name);
            org.acm.seguin.summary.PackageSummary.packageMap.put(name, result);
        }
        return result;
    }

    /**
     * Get a package summary object
     *
     * @return all package summaries
     */
    public static java.util.Iterator getAllPackages() {
        if (org.acm.seguin.summary.PackageSummary.packageMap == null) {
            org.acm.seguin.summary.PackageSummary.init();
        }
        return org.acm.seguin.summary.PackageSummary.packageMap.values().iterator();
    }

    /**
     * Saves all the packages to an object output stream
     *
     * @param out
     * 		Description of Parameter
     * @exception IOException
     * 		Description of Exception
     */
    public static void saveAll(java.io.ObjectOutputStream out) throws java.io.IOException {
        out.writeObject(org.acm.seguin.summary.PackageSummary.packageMap);
    }

    /**
     * Loads all the packages from the object input stream
     *
     * @param in
     * 		Description of Parameter
     * @exception IOException
     * 		Description of Exception
     */
    public static void loadAll(java.io.ObjectInputStream in) throws java.io.IOException {
        try {
            org.acm.seguin.summary.PackageSummary.packageMap = ((java.util.HashMap) (in.readObject()));
            if ((org.acm.seguin.summary.PackageSummary.packageMap == null) || (org.acm.seguin.summary.PackageSummary.packageMap.values() == null)) {
                return;
            }
            java.util.Iterator iter = org.acm.seguin.summary.PackageSummary.packageMap.values().iterator();
            while (iter.hasNext()) {
                java.lang.System.out.print("*");
                org.acm.seguin.summary.PackageSummary nextPackage = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
                java.util.Iterator iter2 = nextPackage.getFileSummaries();
                while ((iter2 != null) && iter2.hasNext()) {
                    java.lang.System.out.print(".");
                    org.acm.seguin.summary.FileSummary nextFile = ((org.acm.seguin.summary.FileSummary) (iter2.next()));
                    org.acm.seguin.summary.FileSummary.register(nextFile);
                } 
            } 
            java.lang.System.out.println(" ");
        } catch (java.lang.ClassNotFoundException cnfe) {
            org.acm.seguin.summary.PackageSummary.packageMap = null;
            cnfe.printStackTrace(java.lang.System.out);
        }
    }

    /**
     * Initialization method
     */
    private static void init() {
        if (org.acm.seguin.summary.PackageSummary.packageMap == null) {
            org.acm.seguin.summary.PackageSummary.packageMap = new java.util.HashMap();
        }
    }
}