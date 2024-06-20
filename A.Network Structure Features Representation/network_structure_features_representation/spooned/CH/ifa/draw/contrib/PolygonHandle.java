/* Fri Feb 28 07:47:13 1997  Doug Lea  (dl at gee)
Based on PolyLineHandle
 */
package CH.ifa.draw.contrib;
/**
 * A handle for a node on the polygon.
 */
public class PolygonHandle extends CH.ifa.draw.standard.AbstractHandle {
    private int fIndex;

    private CH.ifa.draw.framework.Locator fLocator;

    /**
     * Constructs a polygon handle.
     *
     * @param owner
     * 		the owning polygon figure.
     * @l the locator
     * @index the index of the node associated with this handle.
     */
    public PolygonHandle(CH.ifa.draw.contrib.PolygonFigure owner, CH.ifa.draw.framework.Locator l, int index) {
        super(owner);
        fLocator = l;
        fIndex = index;
    }

    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        myOwner().setPointAt(new java.awt.Point(x, y), fIndex);
    }

    public void invokeEnd(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        myOwner().smoothPoints();
    }

    public java.awt.Point locate() {
        return fLocator.locate(owner());
    }

    private CH.ifa.draw.contrib.PolygonFigure myOwner() {
        return ((CH.ifa.draw.contrib.PolygonFigure) (owner()));
    }
}