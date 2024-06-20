package org.acm.seguin.ide.elixir;
/**
 * Essentially an adapter that performs the Undo
 *  Refactoring operation.
 *
 * @author Chris Seguin
 */
public class UndoMenuItem {
    /**
     * The static method that Elixir invokes to
     *  Undo the last refactoring.
     */
    public static void undo() {
        new org.acm.seguin.ide.common.UndoAdapter().actionPerformed(null);
    }
}