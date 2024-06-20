package org.acm.seguin.ide.common;
/**
 * Simple adapter that exits the application when the frame is closed
 *
 * @author Chris Seguin
 */
public class ExitOnCloseAdapter extends java.awt.event.WindowAdapter {
    /**
     * The window is closing
     *
     * @param evt
     * 		Description of Parameter
     */
    public void windowClosing(java.awt.event.WindowEvent evt) {
        java.lang.System.exit(0);
    }
}