/* @(#)GroupHandle.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A Handle for a GroupFigure.
 */
final class GroupHandle extends CH.ifa.draw.standard.NullHandle {
    public GroupHandle(CH.ifa.draw.framework.Figure owner, CH.ifa.draw.framework.Locator locator) {
        super(owner, locator);
    }

    /**
     * Draws the Group handle.
     */
    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.black);
        g.drawRect(r.x, r.y, r.width, r.height);
        r.grow(-1, -1);
        g.setColor(java.awt.Color.white);
        g.drawRect(r.x, r.y, r.width, r.height);
    }
}