package org.acm.seguin.ide.command;
/**
 * Zooms in/out on a particular panel based on a scalar input
 *
 * @author Chris Seguin
 */
public class ZoomAdapter implements java.awt.event.ActionListener {
    private org.acm.seguin.uml.line.LinedPanel panel;

    private double scale;

    /**
     * Constructor for the ZoomAction object
     *
     * @param panel
     * 		The panel
     * @param scale
     * 		the scaling factor
     */
    public ZoomAdapter(org.acm.seguin.uml.line.LinedPanel panel, double scale) {
        this.panel = panel;
        this.scale = scale;
    }

    /**
     * The button has been pressed, do it!
     *
     * @param evt
     * 		the action event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        panel.scale(scale);
        panel.repaint();
    }
}