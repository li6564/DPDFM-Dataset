package org.acm.seguin.ide.command;
/**
 * Exits after this menu option is selected
 *
 * @author Chris Seguin
 * @created August 2, 1999
 */
public class ExitMenuSelection extends java.awt.event.WindowAdapter implements java.awt.event.ActionListener {
    /**
     * Exits when this menu items is selected
     *
     * @param evt
     * 		The triggering event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        finish();
    }

    /**
     * The window is closing
     *
     * @param evt
     * 		Description of Parameter
     */
    public void windowClosing(java.awt.event.WindowEvent evt) {
        finish();
    }

    /**
     * Close everything down
     */
    private void finish() {
        org.acm.seguin.ide.command.PackageSelectorPanel psp = org.acm.seguin.ide.command.PackageSelectorPanel.getMainPanel(null);
        if (psp != null) {
            try {
                psp.save();
            } catch (java.io.IOException ioe) {
                org.acm.seguin.awt.ExceptionPrinter.print(ioe);
            }
        }
        java.lang.System.exit(0);
    }
}