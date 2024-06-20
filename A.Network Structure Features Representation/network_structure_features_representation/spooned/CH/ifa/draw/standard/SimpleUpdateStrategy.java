/* @(#)SimpleUpdateStrategy.java 5.1 */
package CH.ifa.draw.standard;
/**
 * The SimpleUpdateStrategy implements an update
 * strategy that directly redraws a DrawingView.
 *
 * @see DrawingView
 */
public class SimpleUpdateStrategy implements CH.ifa.draw.framework.Painter {
    /* Serialization support. In JavaDraw only the Drawing is serialized.
    However, for beans support SimpleUpdateStrategy supports
    serialization
     */
    private static final long serialVersionUID = -7539925820692134566L;

    /**
     * Draws the view contents.
     */
    public void draw(java.awt.Graphics g, CH.ifa.draw.framework.DrawingView view) {
        view.drawAll(g);
    }
}