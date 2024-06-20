/* @(#)FigureChangeListener.java 5.1 */
package CH.ifa.draw.framework;
/**
 * Listener interested in Figure changes.
 */
public interface FigureChangeListener extends java.util.EventListener {
    /**
     * Sent when an area is invalid
     */
    public void figureInvalidated(CH.ifa.draw.framework.FigureChangeEvent e);

    /**
     * Sent when a figure changed
     */
    public void figureChanged(CH.ifa.draw.framework.FigureChangeEvent e);

    /**
     * Sent when a figure was removed
     */
    public void figureRemoved(CH.ifa.draw.framework.FigureChangeEvent e);

    /**
     * Sent when requesting to remove a figure.
     */
    public void figureRequestRemove(CH.ifa.draw.framework.FigureChangeEvent e);

    /**
     * Sent when an update should happen.
     */
    public void figureRequestUpdate(CH.ifa.draw.framework.FigureChangeEvent e);
}