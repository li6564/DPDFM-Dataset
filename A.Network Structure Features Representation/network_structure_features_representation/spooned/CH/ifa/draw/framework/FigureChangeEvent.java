/* @(#)FigureChangeEvent.java 5.1 */
package CH.ifa.draw.framework;
/**
 * FigureChange event passed to FigureChangeListeners.
 */
public class FigureChangeEvent extends java.util.EventObject {
    private java.awt.Rectangle fRectangle;

    private static final java.awt.Rectangle fgEmptyRectangle = new java.awt.Rectangle(0, 0, 0, 0);

    /**
     * Constructs an event for the given source Figure. The rectangle is the
     * area to be invalvidated.
     */
    public FigureChangeEvent(CH.ifa.draw.framework.Figure source, java.awt.Rectangle r) {
        super(source);
        fRectangle = r;
    }

    public FigureChangeEvent(CH.ifa.draw.framework.Figure source) {
        super(source);
        fRectangle = CH.ifa.draw.framework.FigureChangeEvent.fgEmptyRectangle;
    }

    /**
     * Gets the changed figure
     */
    public CH.ifa.draw.framework.Figure getFigure() {
        return ((CH.ifa.draw.framework.Figure) (getSource()));
    }

    /**
     * Gets the changed rectangle
     */
    public java.awt.Rectangle getInvalidatedRectangle() {
        return fRectangle;
    }
}