/* @(#)PointConstrainer.java 5.1 */
package CH.ifa.draw.framework;
/**
 * Interface to constrain a Point. This can be used to implement
 * different kinds of grids.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld034.htm>Strategy</a></b><br>
 * DrawingView is the StrategyContext.<br>
 *
 * @see DrawingView
 */
public interface PointConstrainer {
    /**
     * Constrains the given point.
     *
     * @return constrained point.
     */
    public java.awt.Point constrainPoint(java.awt.Point p);

    /**
     * Gets the x offset to move an object.
     */
    public int getStepX();

    /**
     * Gets the y offset to move an object.
     */
    public int getStepY();
}