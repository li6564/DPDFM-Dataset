package org.acm.seguin.uml.line;
/**
 * A panel that can be an end point of a segmented line
 *
 * @author Chris Seguin
 */
public abstract class EndPointPanel extends org.acm.seguin.uml.line.ScalablePanel {
    /**
     * Constructor for the EndPointPanel object
     */
    public EndPointPanel() {
    }

    /**
     * Constructor for the EndPointPanel object
     *
     * @param doubleBuffered
     * 		Description of Parameter
     */
    public EndPointPanel(boolean doubleBuffered) {
        super(doubleBuffered);
    }

    /**
     * Constructor for the EndPointPanel object
     *
     * @param layout
     * 		Description of Parameter
     */
    public EndPointPanel(java.awt.LayoutManager layout) {
        super(layout);
    }

    /**
     * Constructor for the EndPointPanel object
     *
     * @param layout
     * 		Description of Parameter
     * @param doubleBuffered
     * 		Description of Parameter
     */
    public EndPointPanel(java.awt.LayoutManager layout, boolean doubleBuffered) {
        super(layout, doubleBuffered);
    }

    /**
     * Sets the Selected attribute of the EndPointPanel object
     *
     * @param value
     * 		The new Selected value
     */
    public abstract void setSelected(boolean value);

    /**
     * Gets the Selected attribute of the EndPointPanel object
     *
     * @return The Selected value
     */
    public abstract boolean isSelected();

    /**
     * Computes the location without the scaling factor
     *
     * @return the unscaled location
     */
    public java.awt.Point getUnscaledLocation() {
        java.awt.Point pt = getLocation();
        pt.x = ((int) (pt.x / getScale()));
        pt.y = ((int) (pt.y / getScale()));
        return pt;
    }
}