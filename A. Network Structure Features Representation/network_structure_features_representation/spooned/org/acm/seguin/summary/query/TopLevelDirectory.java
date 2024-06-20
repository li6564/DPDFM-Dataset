/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * This class is used to infer the top level directory
 *
 * @author Chris Seguin
 */
public class TopLevelDirectory {
    /**
     * Gets the PackageDirectory attribute of the TopLevelDirectory class
     *
     * @param initialSummary
     * 		Description of Parameter
     * @param packageName
     * 		Description of Parameter
     * @return The PackageDirectory value
     */
    public static java.io.File getPackageDirectory(org.acm.seguin.summary.Summary initialSummary, java.lang.String packageName) {
        java.io.File rootDir = null;
        if (initialSummary != null) {
            org.acm.seguin.summary.FileSummary fileSummary = org.acm.seguin.summary.query.TopLevelDirectory.getFileSummary(initialSummary);
            rootDir = org.acm.seguin.summary.query.TopLevelDirectory.query(fileSummary);
        }
        if (rootDir == null) {
            rootDir = org.acm.seguin.summary.query.TopLevelDirectory.query();
        }
        java.util.StringTokenizer tok = new java.util.StringTokenizer(packageName, ".");
        java.io.File current = rootDir;
        while (tok.hasMoreTokens()) {
            current = new java.io.File(current, tok.nextToken());
        } 
        return current;
    }

    /**
     * Return the top level directory from a FileSummary
     *
     * @param fileSummary
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static java.io.File query(org.acm.seguin.summary.FileSummary fileSummary) {
        java.io.File current = fileSummary.getFile();
        if (current == null) {
            return null;
        }
        java.io.File currentDir = current.getParentFile();
        org.acm.seguin.summary.PackageSummary packageSummary = ((org.acm.seguin.summary.PackageSummary) (fileSummary.getParent()));
        java.lang.String name = packageSummary.getName();
        if (name.length() == 0) {
            return currentDir;
        }
        int index = name.indexOf(".");
        currentDir = currentDir.getParentFile();
        while (index != (-1)) {
            index = name.indexOf(".", index + 1);
            currentDir = currentDir.getParentFile();
        } 
        return currentDir;
    }

    /**
     * Return the top level directory
     *
     * @param initialDir
     * 		Description of Parameter
     * @param filename
     * 		Description of Parameter
     * @return the top level directory
     */
    public static java.io.File query(java.io.File initialDir, java.lang.String filename) {
        // Create a factory to get a root
        java.io.File inputFile = new java.io.File(initialDir, filename);
        org.acm.seguin.parser.factory.ParserFactory factory = new org.acm.seguin.parser.factory.FileParserFactory(inputFile);
        org.acm.seguin.parser.ast.SimpleNode root = factory.getAbstractSyntaxTree(false);
        java.io.File topLevel = org.acm.seguin.summary.query.TopLevelDirectory.getParent(inputFile);
        org.acm.seguin.parser.ast.ASTName packageName = org.acm.seguin.parser.query.PackageNameGetter.query(root);
        if (packageName != null) {
            for (int ndx = 0; ndx < packageName.getNameSize(); ndx++) {
                topLevel = org.acm.seguin.summary.query.TopLevelDirectory.getParent(topLevel);
            }
        }
        // Return that directory
        return topLevel;
    }

    /**
     * Given a file, it returns the parent file
     *
     * @param input
     * 		the input file
     * @return the parent of that file
     */
    private static java.io.File getParent(java.io.File input) {
        try {
            java.lang.String path = input.getCanonicalPath();
            java.io.File temp = new java.io.File(path);
            return temp.getParentFile();
        } catch (java.io.IOException ioe) {
        }
        return input.getParentFile();
    }

    /**
     * Gets the FileSummary attribute of the TopLevelDirectory class
     *
     * @param initialSummary
     * 		Description of Parameter
     * @return The FileSummary value
     */
    private static org.acm.seguin.summary.FileSummary getFileSummary(org.acm.seguin.summary.Summary initialSummary) {
        org.acm.seguin.summary.Summary currentSummary = initialSummary;
        while (!(currentSummary instanceof org.acm.seguin.summary.FileSummary)) {
            currentSummary = currentSummary.getParent();
            if (currentSummary == null) {
                return null;
            }
        } 
        return ((org.acm.seguin.summary.FileSummary) (currentSummary));
    }

    /**
     * Searches all the packages for an appropriate file and infers the source
     *  root directory
     *
     * @return the root directory
     */
    private static java.io.File query() {
        org.acm.seguin.summary.FileSummary appropriate = org.acm.seguin.summary.query.TopLevelDirectory.findFileSummary();
        if (appropriate == null) {
            return new java.io.File(java.lang.System.getProperty("user.dir"));
        }
        org.acm.seguin.summary.PackageSummary packageSummary = ((org.acm.seguin.summary.PackageSummary) (appropriate.getParent()));
        java.lang.String packageName = packageSummary.getName();
        java.util.StringTokenizer tok = new java.util.StringTokenizer(packageName, ".");
        java.io.File startingPoint = null;
        try {
            startingPoint = new java.io.File(appropriate.getFile().getCanonicalPath());
        } catch (java.io.IOException ioe) {
            startingPoint = appropriate.getFile();
        }
        java.io.File current = startingPoint.getParentFile();
        while (tok.hasMoreTokens()) {
            current = current.getParentFile();
            java.lang.String value = tok.nextToken();
        } 
        return current;
    }

    /**
     * Searches for a file summary with a file (rather than a null) in the file
     *
     * @return the file summary
     */
    private static org.acm.seguin.summary.FileSummary findFileSummary() {
        java.util.Iterator iter = org.acm.seguin.summary.PackageSummary.getAllPackages();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.PackageSummary next = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
                java.util.Iterator iter2 = next.getFileSummaries();
                while ((iter2 != null) && iter2.hasNext()) {
                    org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (iter2.next()));
                    if (fileSummary.getFile() != null) {
                        return fileSummary;
                    }
                } 
            } 
        }
        return null;
    }
}