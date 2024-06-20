/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
import java.io.IOException;
/**
 * Determines what the current summary is based on the information from the
 *  IDE.
 *
 * @author Chris Seguin
 */
public abstract class CurrentSummary extends java.lang.Object implements javax.swing.event.DocumentListener {
    /**
     * Has this file changed since the last time this was invoked
     */
    protected boolean upToDate;

    private org.acm.seguin.summary.Summary summary;

    private org.acm.seguin.summary.FileSummary fileSummary;

    private static org.acm.seguin.ide.common.action.CurrentSummary singleton = null;

    /**
     * Constructor for the CurrentSummary object
     */
    protected CurrentSummary() {
        summary = null;
        fileSummary = null;
        upToDate = false;
    }

    /**
     * Gets the CurrentSummary attribute of the CurrentSummary object
     *
     * @return The CurrentSummary value
     */
    public org.acm.seguin.summary.Summary getCurrentSummary() {
        if ((((summary == null) || upToDate) || (!isSameFile())) || (!isInSameSummary())) {
            lockAccess();
        }
        java.lang.System.out.println((("Summary is:  " + summary) + " from ") + getLineNumber());
        return summary;
    }

    /**
     * Method that receives notification when the editor changes
     *
     * @param evt
     * 		Description of Parameter
     */
    public void insertUpdate(javax.swing.event.DocumentEvent evt) {
        upToDate = false;
    }

    /**
     * Method that receives notification when the editor changes
     *
     * @param evt
     * 		Description of Parameter
     */
    public void removeUpdate(javax.swing.event.DocumentEvent evt) {
        upToDate = false;
    }

    /**
     * Method that receives notification when the editor changes
     *
     * @param evt
     * 		Description of Parameter
     */
    public void changedUpdate(javax.swing.event.DocumentEvent evt) {
        upToDate = false;
    }

    /**
     * Description of the Method
     */
    public void reset() {
        upToDate = false;
    }

    /**
     * Reloads all the metadata before attempting to perform a refactoring.
     */
    public void updateMetaData() {
        org.acm.seguin.ide.common.MultipleDirClassDiagramReloader reloader = getMetadataReloader();
        reloader.setNecessary(true);
        reloader.reload();
    }

    /**
     * Returns the initial line number
     *
     * @return The LineNumber value
     */
    protected int getLineNumber() {
        return org.acm.seguin.ide.common.EditorOperations.get().getLineNumber();
    }

    /**
     * Gets the ActiveFile attribute of the CurrentSummary object
     *
     * @return The ActiveFile value
     */
    protected java.io.File getActiveFile() {
        return org.acm.seguin.ide.common.EditorOperations.get().getFile();
    }

    /**
     * Gets the reloader
     *
     * @return The MetadataReloader value
     */
    protected abstract org.acm.seguin.ide.common.MultipleDirClassDiagramReloader getMetadataReloader();

    /**
     * Register with the current document
     */
    protected abstract void registerWithCurrentDocument();

    /**
     * Gets the SameNode attribute of the CurrentSummary object
     *
     * @return The SameNode value
     */
    private boolean isSameFile() {
        if (fileSummary == null) {
            return false;
        }
        boolean result = fileSummary.getFile() == getActiveFile();
        // System.out.println("Node is the same:  " + result);
        return result;
    }

    /**
     * Gets the InType attribute of the CurrentSummary object
     *
     * @param fileSummary
     * 		Description of Parameter
     * @param lineNumber
     * 		Description of Parameter
     * @return The InType value
     */
    private org.acm.seguin.summary.Summary getInType(org.acm.seguin.summary.FileSummary fileSummary, int lineNumber) {
        java.util.Iterator iter = fileSummary.getTypes();
        if (iter == null) {
            return null;
        }
        // System.out.println("Searching for:  " + lineNumber);
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            // System.out.println("Type:  " + next.toString() + " " + next.getStartLine() + ", " + next.getEndLine());
            if ((next.getStartLine() <= lineNumber) && (next.getEndLine() >= lineNumber)) {
                return findSummaryInType(next, lineNumber);
            }
        } 
        return null;
    }

    /**
     * Gets the InMethod attribute of the CurrentSummary object
     *
     * @param typeSummary
     * 		Description of Parameter
     * @param lineNumber
     * 		Description of Parameter
     * @return The InMethod value
     */
    private org.acm.seguin.summary.Summary isInMethod(org.acm.seguin.summary.TypeSummary typeSummary, int lineNumber) {
        java.util.Iterator iter = typeSummary.getMethods();
        if (iter == null) {
            return null;
        }
        while (iter.hasNext()) {
            org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
            // System.out.println("Method:  " + next.toString() + " " + next.getStartLine() + ", " + next.getEndLine());
            if ((next.getStartLine() <= lineNumber) && (next.getEndLine() >= lineNumber)) {
                return next;
            }
        } 
        return null;
    }

    /**
     * Gets the InField attribute of the CurrentSummary object
     *
     * @param typeSummary
     * 		Description of Parameter
     * @param lineNumber
     * 		Description of Parameter
     * @return The InField value
     */
    private org.acm.seguin.summary.Summary isInField(org.acm.seguin.summary.TypeSummary typeSummary, int lineNumber) {
        java.util.Iterator iter = typeSummary.getFields();
        if (iter == null) {
            return null;
        }
        while (iter.hasNext()) {
            org.acm.seguin.summary.FieldSummary next = ((org.acm.seguin.summary.FieldSummary) (iter.next()));
            // System.out.println("Field:  " + next.toString() + " " + next.getStartLine() + ", " + next.getEndLine());
            if ((next.getStartLine() <= lineNumber) && (next.getEndLine() >= lineNumber)) {
                return next;
            }
        } 
        return null;
    }

    /**
     * Gets the InNestedClass attribute of the CurrentSummary object
     *
     * @param typeSummary
     * 		Description of Parameter
     * @param lineNumber
     * 		Description of Parameter
     * @return The InNestedClass value
     */
    private org.acm.seguin.summary.Summary isInNestedClass(org.acm.seguin.summary.TypeSummary typeSummary, int lineNumber) {
        java.util.Iterator iter = typeSummary.getTypes();
        if (iter == null) {
            return null;
        }
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            // System.out.println("Type:  " + next.toString() + " " + next.getStartLine() + ", " + next.getEndLine());
            if ((next.getStartLine() <= lineNumber) && (next.getEndLine() >= lineNumber)) {
                return findSummaryInType(next, lineNumber);
            }
        } 
        return null;
    }

    /**
     * Gets the InSameSummary attribute of the CurrentSummary object
     *
     * @return The InSameSummary value
     */
    private boolean isInSameSummary() {
        int lineNumber = getLineNumber();
        return (summary.getStartLine() <= lineNumber) && (summary.getEndLine() >= lineNumber);
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    private org.acm.seguin.summary.Summary find() {
        try {
            registerWithCurrentDocument();
            int lineNumber = getLineNumber();
            if (lineNumber == (-1)) {
                // System.out.println("Unable to get the line number:  " + lastActive + "  " + lineNumber);
                return null;
            }
            if ((!upToDate) || (fileSummary == null)) {
                fileSummary = reloadNode();
            }
            if (fileSummary == null) {
                // System.out.println("Unable to load a file summary");
                return null;
            }
            org.acm.seguin.summary.Summary summary = getInType(fileSummary, lineNumber);
            if (summary != null) {
                // System.out.println("Found a summary:  " + summary);
                return summary;
            }
            // System.out.println("Just able to return the file summary");
            return fileSummary;
        } catch (java.io.IOException ioe) {
            return null;
        }
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     * @exception IOException
     * 		Description of Exception
     */
    private org.acm.seguin.summary.FileSummary reloadNode() throws java.io.IOException {
        if (org.acm.seguin.ide.common.EditorOperations.get().isJavaFile()) {
            java.lang.String contents = org.acm.seguin.ide.common.EditorOperations.get().getStringFromIDE();
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(contents.getBytes());
            return org.acm.seguin.summary.FileSummary.reloadFromBuffer(org.acm.seguin.ide.common.EditorOperations.get().getFile(), bais);
        }
        return null;
    }

    /**
     * Description of the Method
     *
     * @param next
     * 		Description of Parameter
     * @param lineNumber
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private org.acm.seguin.summary.Summary findSummaryInType(org.acm.seguin.summary.TypeSummary next, int lineNumber) {
        org.acm.seguin.summary.Summary result = isInMethod(next, lineNumber);
        if (result != null) {
            return result;
        }
        result = isInField(next, lineNumber);
        if (result != null) {
            return result;
        }
        result = isInNestedClass(next, lineNumber);
        if (result != null) {
            return result;
        }
        return next;
    }

    /**
     * Only does one find at a time
     */
    private synchronized void lockAccess() {
        if ((((summary == null) || (!upToDate)) || (!isSameFile())) || (!isInSameSummary())) {
            // System.out.println("About to find the summary");
            summary = find();
            upToDate = true;
            // System.out.println("Done");
        }
        // System.out.println("Finished lock access");
    }

    /**
     * Method to get the singleton object
     *
     * @return the current summary
     */
    public static org.acm.seguin.ide.common.action.CurrentSummary get() {
        return org.acm.seguin.ide.common.action.CurrentSummary.singleton;
    }

    /**
     * Register the current summary
     *
     * @param value
     * 		Description of Parameter
     */
    public static void register(org.acm.seguin.ide.common.action.CurrentSummary value) {
        org.acm.seguin.ide.common.action.CurrentSummary.singleton = value;
    }
}