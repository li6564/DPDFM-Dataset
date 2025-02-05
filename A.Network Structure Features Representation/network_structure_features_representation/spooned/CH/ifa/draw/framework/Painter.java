/* @(#)Painter.java 5.1 */
package CH.ifa.draw.framework;
/**
 * Painter defines the interface for drawing a layer
 * into a DrawingView.<p>
 *
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld034.htm>Strategy</a></b><br>
 * Painter encapsulates an algorithm to render something in
 * the DrawingView. The DrawingView plays the role of the StrategyContext.
 * <hr>
 *
 * @see DrawingView
 */
public interface Painter extends java.io.Serializable {
    /**
     * Draws into the given DrawingView.
     */
    public void draw(java.awt.Graphics g, CH.ifa.draw.framework.DrawingView view);
}