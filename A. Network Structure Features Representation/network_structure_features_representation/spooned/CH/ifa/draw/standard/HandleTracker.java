/* @(#)HandleTracker.java 5.1 */
package CH.ifa.draw.standard;
/**
 * HandleTracker implements interactions with the handles
 * of a Figure.
 *
 * @see SelectionTool
 */
public class HandleTracker extends CH.ifa.draw.standard.AbstractTool {
    private CH.ifa.draw.framework.Handle fAnchorHandle;

    public HandleTracker(CH.ifa.draw.framework.DrawingView view, CH.ifa.draw.framework.Handle anchorHandle) {
        super(view);
        fAnchorHandle = anchorHandle;
    }

    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);
        fAnchorHandle.invokeStart(x, y, view());
    }

    public void mouseDrag(java.awt.event.MouseEvent e, int x, int y) {
        super.mouseDrag(e, x, y);
        fAnchorHandle.invokeStep(x, y, fAnchorX, fAnchorY, view());
    }

    public void mouseUp(java.awt.event.MouseEvent e, int x, int y) {
        super.mouseDrag(e, x, y);
        fAnchorHandle.invokeEnd(x, y, fAnchorX, fAnchorY, view());
    }
}