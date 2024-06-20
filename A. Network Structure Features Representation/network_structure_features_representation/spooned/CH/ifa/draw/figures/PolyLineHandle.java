/* @(#)PolyLineHandle.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A handle for a node on the polyline.
 */
public class PolyLineHandle extends CH.ifa.draw.standard.LocatorHandle {
    private int fIndex;

    private java.awt.Point fAnchor;

    /**
     * Constructs a poly line handle.
     *
     * @param owner
     * 		the owning polyline figure.
     * @l the locator
     * @index the index of the node associated with this handle.
     */
    public PolyLineHandle(CH.ifa.draw.figures.PolyLineFigure owner, CH.ifa.draw.framework.Locator l, int index) {
        super(owner, l);
        fIndex = index;
    }

    public void invokeStart(int x, int y, CH.ifa.draw.framework.DrawingView view) {
        fAnchor = new java.awt.Point(x, y);
    }

    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        myOwner().setPointAt(new java.awt.Point(x, y), fIndex);
    }

    private CH.ifa.draw.figures.PolyLineFigure myOwner() {
        return ((CH.ifa.draw.figures.PolyLineFigure) (owner()));
    }
}