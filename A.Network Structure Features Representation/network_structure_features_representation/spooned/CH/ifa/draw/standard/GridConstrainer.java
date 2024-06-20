/* @(#)GridConstrainer.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Constrains a point such that it falls on a grid.
 *
 * @see DrawingView
 */
public class GridConstrainer implements CH.ifa.draw.framework.PointConstrainer , java.io.Serializable {
    private int fGridX;

    private int fGridY;

    public GridConstrainer(int x, int y) {
        fGridX = java.lang.Math.max(1, x);
        fGridY = java.lang.Math.max(1, y);
    }

    /**
     * Constrains the given point.
     *
     * @return constrained point.
     */
    public java.awt.Point constrainPoint(java.awt.Point p) {
        p.x = ((p.x + (fGridX / 2)) / fGridX) * fGridX;
        p.y = ((p.y + (fGridY / 2)) / fGridY) * fGridY;
        return p;
    }

    /**
     * Gets the x offset to move an object.
     */
    public int getStepX() {
        return fGridX;
    }

    /**
     * Gets the y offset to move an object.
     */
    public int getStepY() {
        return fGridY;
    }
}