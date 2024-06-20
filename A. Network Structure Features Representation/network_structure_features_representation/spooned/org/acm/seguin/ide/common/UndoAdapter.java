package org.acm.seguin.ide.common;
/**
 * General software component that can perform an undo operation
 *
 * @author Chris Seguin
 */
public class UndoAdapter implements java.awt.event.ActionListener {
    /**
     * When the menu item is selected, do this
     *
     * @param evt
     * 		The event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (org.acm.seguin.refactor.undo.UndoStack.get().isStackEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "No more refactorings to undo.", "Undo Refactoring", javax.swing.JOptionPane.ERROR_MESSAGE);
        } else {
            org.acm.seguin.refactor.undo.UndoAction action = org.acm.seguin.refactor.undo.UndoStack.get().peek();
            int result = javax.swing.JOptionPane.showConfirmDialog(null, "Would you like to undo the following refactoring?\n" + action.getDescription(), "Undo Refactoring", javax.swing.JOptionPane.YES_NO_OPTION);
            if (result == javax.swing.JOptionPane.YES_OPTION) {
                org.acm.seguin.refactor.undo.UndoStack.get().undo();
            }
        }
    }
}