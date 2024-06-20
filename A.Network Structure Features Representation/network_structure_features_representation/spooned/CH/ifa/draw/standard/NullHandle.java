/* @(#)NullHandle.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A handle that doesn't change the owned figure. Its only purpose is
 * to show feedback that a figure is selected.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b>NullObject</b><br>
 * NullObject enables to treat handles that don't do
 * anything in the same way as other handles.
 */
public class NullHandle extends CH.ifa.draw.standard.LocatorHandle {
    /**
     * The handle's locator.
     */
    protected CH.ifa.draw.framework.Locator fLocator;

    public NullHandle(CH.ifa.draw.framework.Figure owner, CH.ifa.draw.framework.Locator locator) {
        super(owner, locator);
    }

    /**
     * Draws the NullHandle. NullHandles are drawn as a
     * red framed rectangle.
     */
    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.black);
        g.drawRect(r.x, r.y, r.width, r.height);
    }
}