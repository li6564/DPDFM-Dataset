package org.acm.seguin.awt;
/**
 * Prints exceptions
 *
 * @author Chris Seguin
 */
public class ExceptionPrinter {
    /**
     * Prints exceptions
     *
     * @param exc
     * 		the exception to be printed
     */
    public static void print(java.lang.Throwable exc) {
        exc.printStackTrace(java.lang.System.out);
    }

    /**
     * Prints exceptions
     *
     * @param exc
     * 		the exception to be printed
     */
    public static void dialog(java.lang.Throwable exc) {
        new org.acm.seguin.awt.ExceptionDialog(exc).setVisible(true);
    }
}