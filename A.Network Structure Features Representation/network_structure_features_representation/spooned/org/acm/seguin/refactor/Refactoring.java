/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor;
/**
 * Adds a class that is either a parent or a child of an existing class.
 *
 * @author Chris Seguin
 */
public abstract class Refactoring {
    private org.acm.seguin.refactor.ComplexTransform complex = null;

    /**
     * The repackage refactoring
     */
    public static final int REPACKAGE = 1;

    /**
     * The rename class refactoring
     */
    public static final int RENAME_CLASS = 2;

    /**
     * The add child refactoring
     */
    public static final int ADD_CHILD = 4;

    /**
     * The add parent refactoring
     */
    public static final int ADD_PARENT = 3;

    /**
     * The remove class refactoring
     */
    public static final int REMOVE_CLASS = 5;

    /**
     * Extracts the interface
     */
    public static final int EXTRACT_INTERFACE = 6;

    /**
     * Pushes the field into the parent class
     */
    public static final int PUSH_DOWN_FIELD = 101;

    /**
     * Pushes the field into the child classes
     */
    public static final int PUSH_UP_FIELD = 102;

    /**
     * Renames the field
     */
    public static final int RENAME_FIELD = 103;

    /**
     * Pushes the method into the parent class
     */
    public static final int PUSH_UP_METHOD = 201;

    /**
     * Pushes the method signature into the parent class
     */
    public static final int PUSH_UP_ABSTRACT_METHOD = 202;

    /**
     * Pushes the method into the child classes
     */
    public static final int PUSH_DOWN_METHOD = 203;

    /**
     * Moves the method into another class
     */
    public static final int MOVE_METHOD = 204;

    /**
     * Extracts code from one method to create a new method
     */
    public static final int EXTRACT_METHOD = 205;

    /**
     * Extracts code from one method to create a new method
     */
    public static final int RENAME_METHOD = 206;

    /**
     * Renames a parameter
     */
    public static final int RENAME_PARAMETER = 251;

    /**
     * Constructor for the Refactoring object
     */
    public Refactoring() {
    }

    /**
     * Gets the description of the refactoring
     *
     * @return the description
     */
    public abstract java.lang.String getDescription();

    /**
     * Gets the id for this refactoring to track which refactorings are used.
     *
     * @return the id
     */
    public abstract int getID();

    /**
     * Main program that performst the transformation
     *
     * @exception RefactoringException
     * 		Description of Exception
     */
    public void run() throws org.acm.seguin.refactor.RefactoringException {
        try {
            preconditions();
            transform();
            org.acm.seguin.refactor.undo.UndoStack.get().done();
            recordUsage();
        } catch (org.acm.seguin.refactor.RefactoringException re) {
            throw re;
        } catch (java.lang.Throwable thrown) {
            thrown.printStackTrace(java.lang.System.out);
        }
    }

    /**
     * Gets a complex transform object for this refactoring
     *
     * @return The ComplexTransform value
     */
    protected org.acm.seguin.refactor.ComplexTransform getComplexTransform() {
        if (complex == null) {
            org.acm.seguin.refactor.undo.UndoAction undo = org.acm.seguin.refactor.undo.UndoStack.get().add(this);
            complex = new org.acm.seguin.refactor.ComplexTransform(undo);
        }
        return complex;
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
    protected abstract void preconditions() throws org.acm.seguin.refactor.RefactoringException;

    /**
     * Performs the transform on the rest of the classes
     */
    protected abstract void transform();

    /**
     * Check that we are allowed to adjust the destination
     *
     * @param loop
     * 		the summary
     * @param message
     * 		the message
     * @exception RefactoringException
     * 		problem report
     */
    protected void checkDestinationFile(org.acm.seguin.summary.Summary loop, java.lang.String message) throws org.acm.seguin.refactor.RefactoringException {
        while (loop != null) {
            if (loop instanceof org.acm.seguin.summary.FileSummary) {
                org.acm.seguin.summary.FileSummary temp = ((org.acm.seguin.summary.FileSummary) (loop));
                if (temp.getFile() == null) {
                    throw new org.acm.seguin.refactor.RefactoringException(message);
                }
                loop = null;
            } else {
                loop = loop.getParent();
            }
        } 
    }

    /**
     * Record the refactoring usage
     */
    private void recordUsage() {
        try {
            java.lang.String dir = org.acm.seguin.util.FileSettings.getSettingsRoot();
            java.io.FileWriter fileWriter = new java.io.FileWriter((dir + java.io.File.separator) + "log.txt", true);
            java.io.PrintWriter output = new java.io.PrintWriter(fileWriter);
            java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.SHORT);
            output.println((getID() + ", ") + df.format(new java.util.Date()));
            output.close();
        } catch (java.io.IOException ioe) {
        }
    }
}