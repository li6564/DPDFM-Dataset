/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.undo;
/**
 * The stack of refactorings that we can undo. This stack holds all the
 *  refactorings that have occurred in the system. <P>
 *
 *  This object is a singleton object because we only want one object
 *  responsible for storing the refactorings that can be undone.
 *
 * @author Chris Seguin
 */
public class UndoStack {
    /**
     * The stack that contains the actual elements
     */
    private java.util.Stack stack;

    private static org.acm.seguin.refactor.undo.UndoStack singleton;

    /**
     * Constructor for the UndoStack object
     */
    public UndoStack() {
        if (!load()) {
            stack = new java.util.Stack();
        }
    }

    /**
     * Gets the StackEmpty attribute of the UndoStack object
     *
     * @return The StackEmpty value
     */
    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    /**
     * Adds a refactoring to the undo stack. You provide the refactoring, this
     *  method provides the undo action.
     *
     * @param ref
     * 		the refactoring about to be performed
     * @return an undo action
     */
    public org.acm.seguin.refactor.undo.UndoAction add(org.acm.seguin.refactor.Refactoring ref) {
        org.acm.seguin.refactor.undo.UndoAction action = new org.acm.seguin.refactor.undo.UndoAction(ref.getDescription());
        stack.push(action);
        return action;
    }

    /**
     * Return the top option without removing it from the stack
     *
     * @return the top object
     */
    public org.acm.seguin.refactor.undo.UndoAction peek() {
        return ((org.acm.seguin.refactor.undo.UndoAction) (stack.peek()));
    }

    /**
     * Lists the undo actions in the stack
     *
     * @return an iterator of undo actions
     */
    public java.util.Iterator list() {
        return stack.iterator();
    }

    /**
     * Performs an undo of the top action
     */
    public void undo() {
        org.acm.seguin.refactor.undo.UndoAction action = ((org.acm.seguin.refactor.undo.UndoAction) (stack.pop()));
        action.undo();
        org.acm.seguin.uml.loader.ReloaderSingleton.reload();
    }

    /**
     * Description of the Method
     */
    public void done() {
        save();
    }

    /**
     * Deletes the undo stack
     */
    public void delete() {
        java.io.File file = getFile();
        file.delete();
        stack = new java.util.Stack();
    }

    /**
     * Gets the stack file
     *
     * @return The File value
     */
    private java.io.File getFile() {
        java.io.File dir = new java.io.File(org.acm.seguin.util.FileSettings.getSettingsRoot());
        return new java.io.File(dir, "undo.stk");
    }

    /**
     * Saves the undo stack to the disk
     */
    private void save() {
        try {
            java.io.File file = getFile();
            java.io.ObjectOutputStream output = new java.io.ObjectOutputStream(new java.io.FileOutputStream(file));
            output.writeObject(stack);
            output.flush();
            output.close();
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace(java.lang.System.out);
        }
    }

    /**
     * Loads the undo stack from the disk
     *
     * @return Description of the Returned Value
     */
    private boolean load() {
        try {
            java.io.File file = getFile();
            java.io.ObjectInputStream input = new java.io.ObjectInputStream(new java.io.FileInputStream(file));
            stack = ((java.util.Stack) (input.readObject()));
            input.close();
            return true;
        } catch (java.io.FileNotFoundException fnfe) {
            // Expected - this is normal the first time
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace(java.lang.System.out);
        } catch (java.lang.ClassNotFoundException cnfe) {
            cnfe.printStackTrace(java.lang.System.out);
        }
        return false;
    }

    /**
     * Gets the singleton undo operation
     *
     * @return the undo stack for the system
     */
    public static org.acm.seguin.refactor.undo.UndoStack get() {
        if (org.acm.seguin.refactor.undo.UndoStack.singleton == null) {
            org.acm.seguin.refactor.undo.UndoStack.singleton = new org.acm.seguin.refactor.undo.UndoStack();
        }
        return org.acm.seguin.refactor.undo.UndoStack.singleton;
    }
}