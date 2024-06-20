/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Stores a summary of a java file
 *
 * @author Chris Seguin
 * @created June 6, 1999
 */
public class FileSummary extends org.acm.seguin.summary.Summary {
    // Instance Variables
    private java.io.File theFile;

    private java.util.ArrayList importList;

    private java.util.LinkedList typeList;

    private boolean isMoving;

    private boolean delete;

    private java.util.Date lastModified;

    // Class Variables
    private static java.util.HashMap fileMap;

    /**
     * Creates a file map
     *
     * @param parentSummary
     * 		the parent summary
     * @param initFile
     * 		the file
     */
    protected FileSummary(org.acm.seguin.summary.Summary parentSummary, java.io.File initFile) {
        // Initialize parent class
        super(parentSummary);
        // Set instance Variables
        theFile = initFile;
        importList = null;
        typeList = null;
        isMoving = false;
        delete = false;
        lastModified = new java.util.Date();
    }

    /**
     * Change whether this file is moving or not
     *
     * @param way
     * 		the way that this parameter is changing
     */
    public void setMoving(boolean way) {
        isMoving = way;
    }

    /**
     * Mark whether this file should be deleted
     *
     * @param way
     * 		the way that this parameter is changing
     */
    public void setDeleted(boolean way) {
        delete = way;
    }

    /**
     * Return the name of the file
     *
     * @return a string containing the name
     */
    public java.lang.String getName() {
        if (theFile == null) {
            return "";
        }
        return theFile.getName();
    }

    /**
     * Return the file
     *
     * @return the file object
     */
    public java.io.File getFile() {
        return theFile;
    }

    /**
     * Return the list of imports
     *
     * @return an iterator containing the imports
     */
    public java.util.Iterator getImports() {
        if (importList == null) {
            return null;
        }
        return importList.iterator();
    }

    /**
     * Counts the types stored in the file
     *
     * @return the number of types in this file
     */
    public int getTypeCount() {
        if (typeList == null) {
            return 0;
        }
        return typeList.size();
    }

    /**
     * Get the list of types stored in this file
     *
     * @return an iterator over the types
     */
    public java.util.Iterator getTypes() {
        if (typeList == null) {
            return null;
        }
        return typeList.iterator();
    }

    /**
     * Is this file moving to a new package
     *
     * @return true if the file is moving
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Has this file been deleted
     *
     * @return true if the file is deleted
     */
    public boolean isDeleted() {
        return delete;
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
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public java.lang.String toString() {
        if (theFile == null) {
            return "FileSummary<Framework File>";
        }
        return ("FileSummary<" + theFile.getName()) + ">";
    }

    /**
     * Add an import summary
     *
     * @param importSummary
     * 		the summary of what was imported
     */
    protected void add(org.acm.seguin.summary.ImportSummary importSummary) {
        if (importSummary != null) {
            // Initialize the import list
            if (importList == null) {
                initImportList();
            }
            // Add it in
            importList.add(importSummary);
        }
    }

    /**
     * Add an type summary
     *
     * @param typeSummary
     * 		the summary of the type
     */
    protected void add(org.acm.seguin.summary.TypeSummary typeSummary) {
        if (typeSummary != null) {
            // Initialize the type list
            if (typeList == null) {
                initTypeList();
            }
            // Add it to the list
            typeList.add(typeSummary);
        }
    }

    /**
     * Initialize the type list
     */
    private void initTypeList() {
        typeList = new java.util.LinkedList();
    }

    /**
     * Initialize the import list
     */
    private void initImportList() {
        importList = new java.util.ArrayList();
    }

    /**
     * Get the file summary for a particular file
     *
     * @param file
     * 		the file we are looking up
     * @return the file summary
     */
    public static org.acm.seguin.summary.FileSummary getFileSummary(java.io.File file) {
        if (org.acm.seguin.summary.FileSummary.fileMap == null) {
            org.acm.seguin.summary.FileSummary.init();
        }
        org.acm.seguin.summary.FileSummary result = ((org.acm.seguin.summary.FileSummary) (org.acm.seguin.summary.FileSummary.fileMap.get(org.acm.seguin.summary.FileSummary.getKey(file))));
        if (result == null) {
            org.acm.seguin.summary.SummaryLoaderState state = org.acm.seguin.summary.FileSummary.loadNewFileSummary(file);
            result = org.acm.seguin.summary.FileSummary.linkFileSummary(state, file);
        } else {
            java.util.Date currentModificationTime = new java.util.Date(file.lastModified());
            if (currentModificationTime.after(result.lastModified)) {
                org.acm.seguin.summary.FileSummary.resetFileSummary(file, result);
                result.lastModified = new java.util.Date(file.lastModified());
                // Create a new file summary object
                org.acm.seguin.parser.factory.ParserFactory factory = new org.acm.seguin.parser.factory.FileParserFactory(file);
                org.acm.seguin.parser.ast.SimpleNode root = factory.getAbstractSyntaxTree(false);
                if (root == null) {
                    return null;
                }
                org.acm.seguin.summary.FileSummary.reloadFileSummary(file, result, root);
            }
        }
        return result;
    }

    /**
     * Get the file summary for a particular file
     *
     * @param buffer
     * 		the buffer that is used to load the summary
     * @return the file summary
     */
    public static org.acm.seguin.summary.FileSummary getFileSummary(java.lang.String buffer) {
        // Create a new file summary object
        org.acm.seguin.parser.factory.ParserFactory factory = new org.acm.seguin.parser.factory.BufferParserFactory(buffer);
        org.acm.seguin.parser.ast.SimpleNode root = factory.getAbstractSyntaxTree(false);
        if ((root == null) || (!org.acm.seguin.summary.FileSummary.hasType(root))) {
            return null;
        }
        // Start the summary
        org.acm.seguin.summary.SummaryLoaderState state = new org.acm.seguin.summary.SummaryLoaderState();
        state.setFile(null);
        root.jjtAccept(new org.acm.seguin.summary.SummaryLoadVisitor(), state);
        // Associate them together
        org.acm.seguin.summary.FileSummary result = ((org.acm.seguin.summary.FileSummary) (state.getCurrentSummary()));
        ((org.acm.seguin.summary.PackageSummary) (result.getParent())).addFileSummary(result);
        return result;
    }

    /**
     * Remove any file summaries that have been deleted
     */
    public static void removeDeletedSummaries() {
        if (org.acm.seguin.summary.FileSummary.fileMap == null) {
            org.acm.seguin.summary.FileSummary.init();
            return;
        }
        java.util.LinkedList temp = new java.util.LinkedList();
        java.util.Iterator keys = org.acm.seguin.summary.FileSummary.fileMap.values().iterator();
        while (keys.hasNext()) {
            org.acm.seguin.summary.FileSummary next = ((org.acm.seguin.summary.FileSummary) (keys.next()));
            java.io.File file = next.getFile();
            if ((file != null) && (!file.exists())) {
                temp.add(file);
            }
        } 
        java.util.Iterator iter = temp.iterator();
        while (iter.hasNext()) {
            java.io.File next = ((java.io.File) (iter.next()));
            org.acm.seguin.summary.FileSummary.removeFileSummary(next);
        } 
    }

    /**
     * Remove the file summary for a particular file
     *
     * @param file
     * 		the file we are looking up
     */
    public static void removeFileSummary(java.io.File file) {
        if (org.acm.seguin.summary.FileSummary.fileMap == null) {
            org.acm.seguin.summary.FileSummary.init();
        }
        java.lang.String key = org.acm.seguin.summary.FileSummary.getKey(file);
        org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (org.acm.seguin.summary.FileSummary.fileMap.get(key)));
        if (fileSummary != null) {
            // There is something to remove
            org.acm.seguin.summary.FileSummary.fileMap.remove(key);
            // Get the parent
            org.acm.seguin.summary.PackageSummary parent = ((org.acm.seguin.summary.PackageSummary) (fileSummary.getParent()));
            parent.deleteFileSummary(fileSummary);
        }
    }

    /**
     * Get the key that is used to index the files
     *
     * @param file
     * 		the file we are using to find the key
     * @return the key
     */
    protected static java.lang.String getKey(java.io.File file) {
        try {
            return file.getCanonicalPath();
        } catch (java.io.IOException ioe) {
            return "Unknown";
        }
    }

    /**
     * Registers a single new file. This method is used by the rapid metadata
     *  reloader
     *
     * @param summary
     * 		Description of Parameter
     */
    static void register(org.acm.seguin.summary.FileSummary summary) {
        if (org.acm.seguin.summary.FileSummary.fileMap == null) {
            org.acm.seguin.summary.FileSummary.init();
        }
        java.io.File file = summary.getFile();
        if (file == null) {
            return;
        }
        org.acm.seguin.summary.FileSummary.fileMap.put(org.acm.seguin.summary.FileSummary.getKey(file), summary);
    }

    /**
     * Scans through the tree looking for a type declaration
     *
     * @param root
     * 		the root of the abstract syntax tree
     * @return true if there is a type node present
     */
    private static boolean hasType(org.acm.seguin.parser.ast.SimpleNode root) {
        int last = root.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (root.jjtGetChild(ndx) instanceof org.acm.seguin.parser.ast.ASTTypeDeclaration) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initialization method
     */
    private static void init() {
        if (org.acm.seguin.summary.FileSummary.fileMap == null) {
            org.acm.seguin.summary.FileSummary.fileMap = new java.util.HashMap();
        }
    }

    /**
     * Description of the Method
     *
     * @param file
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private static org.acm.seguin.summary.SummaryLoaderState loadNewFileSummary(java.io.File file) {
        // Create a new file summary object
        org.acm.seguin.parser.factory.ParserFactory factory = new org.acm.seguin.parser.factory.FileParserFactory(file);
        org.acm.seguin.parser.ast.SimpleNode root = factory.getAbstractSyntaxTree(false);
        if (root == null) {
            return null;
        }
        // Start the summary
        org.acm.seguin.summary.SummaryLoaderState state = new org.acm.seguin.summary.SummaryLoaderState();
        state.setFile(file);
        root.jjtAccept(new org.acm.seguin.summary.SummaryLoadVisitor(), state);
        return state;
    }

    /**
     * Description of the Method
     *
     * @param state
     * 		Description of Parameter
     * @param file
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private static org.acm.seguin.summary.FileSummary linkFileSummary(org.acm.seguin.summary.SummaryLoaderState state, java.io.File file) {
        org.acm.seguin.summary.FileSummary result;
        // Associate them together
        result = ((org.acm.seguin.summary.FileSummary) (state.getCurrentSummary()));
        ((org.acm.seguin.summary.PackageSummary) (result.getParent())).addFileSummary(result);
        // Store it away
        org.acm.seguin.summary.FileSummary.fileMap.put(org.acm.seguin.summary.FileSummary.getKey(file), result);
        return result;
    }

    /**
     * Description of the Method
     *
     * @param file
     * 		Description of Parameter
     * @param result
     * 		Description of Parameter
     * @param root
     * 		Description of Parameter
     */
    private static void reloadFileSummary(java.io.File file, org.acm.seguin.summary.FileSummary result, org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.summary.SummaryLoaderState state = new org.acm.seguin.summary.SummaryLoaderState();
        state.setFile(file);
        state.startSummary(result);
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_FILE);
        root.jjtAccept(new org.acm.seguin.summary.SummaryLoadVisitor(), state);
    }

    /**
     * Description of the Method
     *
     * @param file
     * 		Description of Parameter
     */
    private static void resetFileSummary(java.io.File file, org.acm.seguin.summary.FileSummary result) {
        if (result == null) {
            return;
        }
        result.theFile = file;
        result.importList = null;
        result.typeList = null;
        result.isMoving = false;
        result.delete = false;
    }

    /**
     * This method allows JBuilder to load a file summary from
     *  the buffer
     *
     * @param file
     * 		the file
     * @param input
     * 		the input stream
     * @return the file summary loaded
     */
    public static org.acm.seguin.summary.FileSummary reloadFromBuffer(java.io.File file, java.io.InputStream input) {
        if (org.acm.seguin.summary.FileSummary.fileMap == null) {
            org.acm.seguin.summary.FileSummary.init();
        }
        if (file == null) {
            java.lang.System.out.println("No file!");
            return null;
        }
        java.lang.String key = org.acm.seguin.summary.FileSummary.getKey(file);
        if (key == null) {
            java.lang.System.out.println("No key:  " + file.toString());
            return null;
        }
        org.acm.seguin.summary.FileSummary result = ((org.acm.seguin.summary.FileSummary) (org.acm.seguin.summary.FileSummary.fileMap.get(key)));
        if (result == null) {
            java.lang.System.out.println("No initial file summary");
            org.acm.seguin.summary.SummaryLoaderState state = org.acm.seguin.summary.FileSummary.loadNewFileSummary(file);
            result = org.acm.seguin.summary.FileSummary.linkFileSummary(state, file);
            // If you still can't get something that makes sense abort
            if (result == null) {
                java.lang.System.out.println("Unable to load the file summary from the file");
                return null;
            }
        }
        org.acm.seguin.summary.FileSummary.resetFileSummary(file, result);
        result.lastModified = new java.util.Date(file.lastModified());
        // Create a new file summary object
        org.acm.seguin.parser.factory.ParserFactory factory;
        if (input == null) {
            factory = new org.acm.seguin.parser.factory.FileParserFactory(file);
        } else {
            factory = new org.acm.seguin.parser.factory.InputStreamParserFactory(input, key);
        }
        org.acm.seguin.parser.ast.SimpleNode root = factory.getAbstractSyntaxTree(false);
        if (root == null) {
            java.lang.System.out.println("Unable to get a parse tree for this file:  Using existing file summary");
            return result;
        }
        org.acm.seguin.summary.FileSummary.reloadFileSummary(file, result, root);
        return result;
    }
}