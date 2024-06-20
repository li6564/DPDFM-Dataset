/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Main program for repackaging. This object simply stores the main program
 *  and interprets the command line arguments for repackaging one or more
 *  files.
 *
 * @author Chris Seguin
 * @created June 2, 1999
 */
public class MoveClass extends org.acm.seguin.refactor.Refactoring {
    // Instance Variables
    /**
     * The directory
     */
    protected java.lang.String initDir;

    /**
     * The list of filenames
     */
    protected java.util.LinkedList fileList;

    private java.lang.String oldPackage;

    private java.io.File base;

    private java.lang.String srcPackage;

    private java.lang.String destPackage;

    /**
     * Constructor for repackage
     */
    protected MoveClass() {
        destPackage = null;
        initDir = java.lang.System.getProperty("user.dir");
        fileList = new java.util.LinkedList();
    }

    /**
     * Set the destination package
     *
     * @param dest
     * 		the package name
     */
    public void setDestinationPackage(java.lang.String dest) {
        destPackage = dest;
    }

    /**
     * Set the directory
     *
     * @param dir
     * 		the initial directory
     */
    public void setDirectory(java.lang.String dir) {
        initDir = dir;
    }

    /**
     * Gets the Description attribute of the MoveClass object
     *
     * @return The Description value
     */
    public java.lang.String getDescription() {
        return (("Repackaging classes from " + srcPackage) + " to ") + destPackage;
    }

    /**
     * Gets the id for this refactoring to track which refactorings are used.
     *
     * @return the id
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.REPACKAGE;
    }

    /**
     * Add a file to the list. The file name includes only the name, and not the
     *  entire path.
     *
     * @param filename
     * 		the file to add
     */
    public void add(java.lang.String filename) {
        fileList.add(filename);
    }

    /**
     * Main processing method for the MoveClass object
     *
     * @exception RefactoringException
     * 		Description of Exception
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        if ((destPackage == null) || (fileList.size() == 0)) {
            return;
        }
        java.io.File startDir = new java.io.File(initDir);
        java.lang.String firstFilename = ((java.lang.String) (fileList.get(0)));
        org.acm.seguin.parser.ast.ASTName srcPackageName = org.acm.seguin.parser.query.PackageNameGetter.query(startDir, firstFilename);
        srcPackage = "";
        if (srcPackageName != null) {
            srcPackage = srcPackageName.getName();
        }
        base = org.acm.seguin.summary.query.TopLevelDirectory.query(startDir, firstFilename);
        java.lang.String topLevelDir = base.getPath();
        try {
            topLevelDir = base.getCanonicalPath();
        } catch (java.io.IOException ioe) {
        }
        new org.acm.seguin.summary.SummaryTraversal(topLevelDir).go();
    }

    /**
     * Performs the transformation of the class
     */
    protected void transform() {
        org.acm.seguin.refactor.type.MoveClassVisitor mcv = new org.acm.seguin.refactor.type.MoveClassVisitor(srcPackage, destPackage, base, getComplexTransform());
        java.util.Iterator iter = fileList.iterator();
        while (iter.hasNext()) {
            // Get the next file
            java.lang.String nextFile = ((java.lang.String) (iter.next()));
            int start = java.lang.Math.max(0, nextFile.indexOf(java.io.File.separator));
            int end = nextFile.indexOf(".java");
            java.lang.String nextClass = "";
            if (end > 0) {
                nextClass = nextFile.substring(start, end);
            } else {
                nextClass = nextFile.substring(start);
            }
            mcv.add(nextClass);
        } 
        mcv.visit(null);
    }
}