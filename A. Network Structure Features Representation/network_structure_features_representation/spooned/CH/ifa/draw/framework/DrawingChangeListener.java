/* @(#)DrawingChangeListener.java 5.1 */
package CH.ifa.draw.framework;
/**
 * Listener interested in Drawing changes.
 */
public interface DrawingChangeListener extends java.util.EventListener {
    /**
     * Sent when an area is invalid
     */
    public void drawingInvalidated(CH.ifa.draw.framework.DrawingChangeEvent e);

    /**
     * Sent when the drawing wants to be refreshed
     */
    public void drawingRequestUpdate(CH.ifa.draw.framework.DrawingChangeEvent e);
}