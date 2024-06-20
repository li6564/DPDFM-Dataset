/* @(#)AbstractHandle.java 5.1 */
package CH.ifa.draw.standard;
/**
 * AbstractHandle provides defaulf implementation for the
 * Handle interface.
 *
 * @see Figure
 * @see Handle
 */
public abstract class AbstractHandle implements CH.ifa.draw.framework.Handle {
    /**
     * The standard size of a handle.
     */
    public static final int HANDLESIZE = 8;

    private CH.ifa.draw.framework.Figure fOwner;

    /**
     * Initializes the owner of the figure.
     */
    public AbstractHandle(CH.ifa.draw.framework.Figure owner) {
        fOwner = owner;
    }

    /**
     * Locates the handle on the figure. The handle is drawn
     * centered around the returned point.
     */
    public abstract java.awt.Point locate();

    /**
     *
     * @ deprecated As of version 4.1,
    use invokeStart(x, y, drawingView)
    Tracks the start of the interaction. The default implementation
    does nothing.
     * @param x
     * 		the x position where the interaction started
     * @param y
     * 		the y position where the interaction started
     */
    public void invokeStart(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
    }

    /**
     *
     * @ deprecated As of version 4.1,
    use invokeStart(x, y, drawingView)
    Tracks the start of the interaction. The default implementation
    does nothing.
     * @param x
     * 		the x position where the interaction started
     * @param y
     * 		the y position where the interaction started
     * @param view
     * 		the handles container
     */
    public void invokeStart(int x, int y, CH.ifa.draw.framework.DrawingView view) {
        invokeStart(x, y, view.drawing());
    }

    /**
     *
     * @ deprecated As of version 4.1,
    use invokeStep(x, y, anchorX, anchorY, drawingView)

    Tracks a step of the interaction.
     * @param dx
     * 		x delta of this step
     * @param dy
     * 		y delta of this step
     */
    public void invokeStep(int dx, int dy, CH.ifa.draw.framework.Drawing drawing) {
    }

    /**
     * Tracks a step of the interaction.
     *
     * @param x
     * 		the current x position
     * @param y
     * 		the current y position
     * @param anchorX
     * 		the x position where the interaction started
     * @param anchorY
     * 		the y position where the interaction started
     */
    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        invokeStep(x - anchorX, y - anchorY, view.drawing());
    }

    /**
     * Tracks the end of the interaction.
     *
     * @param x
     * 		the current x position
     * @param y
     * 		the current y position
     * @param anchorX
     * 		the x position where the interaction started
     * @param anchorY
     * 		the y position where the interaction started
     */
    public void invokeEnd(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        invokeEnd(x - anchorX, y - anchorY, view.drawing());
    }

    /**
     *
     * @deprecated As of version 4.1,
    use invokeEnd(x, y, anchorX, anchorY, drawingView).

    Tracks the end of the interaction.
     */
    public void invokeEnd(int dx, int dy, CH.ifa.draw.framework.Drawing drawing) {
    }

    /**
     * Gets the handle's owner.
     */
    public CH.ifa.draw.framework.Figure owner() {
        return fOwner;
    }

    /**
     * Gets the display box of the handle.
     */
    public java.awt.Rectangle displayBox() {
        java.awt.Point p = locate();
        return new java.awt.Rectangle(p.x - (CH.ifa.draw.standard.AbstractHandle.HANDLESIZE / 2), p.y - (CH.ifa.draw.standard.AbstractHandle.HANDLESIZE / 2), CH.ifa.draw.standard.AbstractHandle.HANDLESIZE, CH.ifa.draw.standard.AbstractHandle.HANDLESIZE);
    }

    /**
     * Tests if a point is contained in the handle.
     */
    public boolean containsPoint(int x, int y) {
        return displayBox().contains(x, y);
    }

    /**
     * Draws this handle.
     */
    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.white);
        g.fillRect(r.x, r.y, r.width, r.height);
        g.setColor(java.awt.Color.black);
        g.drawRect(r.x, r.y, r.width, r.height);
    }
}