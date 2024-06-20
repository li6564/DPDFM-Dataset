/* @(#)DrawingChangeEvent.java 5.1 */
package CH.ifa.draw.framework;
/**
 * The event passed to DrawingChangeListeners.
 */
public class DrawingChangeEvent extends java.util.EventObject {
    private java.awt.Rectangle fRectangle;

    /**
     * Constructs a drawing change event.
     */
    public DrawingChangeEvent(CH.ifa.draw.framework.Drawing source, java.awt.Rectangle r) {
        super(source);
        fRectangle = r;
    }

    /**
     * Gets the changed drawing
     */
    public CH.ifa.draw.framework.Drawing getDrawing() {
        return ((CH.ifa.draw.framework.Drawing) (getSource()));
    }

    /**
     * Gets the changed rectangle
     */
    public java.awt.Rectangle getInvalidatedRectangle() {
        return fRectangle;
    }
}