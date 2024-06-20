/* @(#)Drawing.java 5.1 */
package CH.ifa.draw.framework;
/**
 * Drawing is a container for figures.
 * <p>
 * Drawing sends out DrawingChanged events to DrawingChangeListeners
 * whenever a part of its area was invalidated.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld026.htm>Observer</a></b><br>
 * The Observer pattern is used to decouple the Drawing from its views and
 * to enable multiple views.<hr>
 *
 * @see Figure
 * @see DrawingView
 * @see FigureChangeListener
 */
public interface Drawing extends CH.ifa.draw.util.Storable , CH.ifa.draw.framework.FigureChangeListener , java.io.Serializable {
    /**
     * Releases the drawing and its contained figures.
     */
    public void release();

    /**
     * Returns an enumeration to iterate in
     * Z-order back to front over the figures.
     */
    public CH.ifa.draw.framework.FigureEnumeration figures();

    /**
     * Returns an enumeration to iterate in
     * Z-order front to back over the figures.
     */
    public CH.ifa.draw.framework.FigureEnumeration figuresReverse();

    /**
     * Finds a top level Figure. Use this call for hit detection that
     * should not descend into the figure's children.
     */
    public CH.ifa.draw.framework.Figure findFigure(int x, int y);

    /**
     * Finds a top level Figure that intersects the given rectangle.
     */
    public CH.ifa.draw.framework.Figure findFigure(java.awt.Rectangle r);

    /**
     * Finds a top level Figure, but supresses the passed
     * in figure. Use this method to ignore a figure
     * that is temporarily inserted into the drawing.
     *
     * @param x
     * 		the x coordinate
     * @param y
     * 		the y coordinate
     * @param without
     * 		the figure to be ignored during
     * 		the find.
     */
    public CH.ifa.draw.framework.Figure findFigureWithout(int x, int y, CH.ifa.draw.framework.Figure without);

    /**
     * Finds a top level Figure that intersects the given rectangle.
     * It supresses the passed
     * in figure. Use this method to ignore a figure
     * that is temporarily inserted into the drawing.
     */
    public CH.ifa.draw.framework.Figure findFigure(java.awt.Rectangle r, CH.ifa.draw.framework.Figure without);

    /**
     * Finds a figure but descends into a figure's
     * children. Use this method to implement <i>click-through</i>
     * hit detection, that is, you want to detect the inner most
     * figure containing the given point.
     */
    public CH.ifa.draw.framework.Figure findFigureInside(int x, int y);

    /**
     * Finds a figure but descends into a figure's
     * children. It supresses the passed
     * in figure. Use this method to ignore a figure
     * that is temporarily inserted into the drawing.
     *
     * @param x
     * 		the x coordinate
     * @param y
     * 		the y coordinate
     * @param without
     * 		the figure to be ignored during
     * 		the find.
     */
    public CH.ifa.draw.framework.Figure findFigureInsideWithout(int x, int y, CH.ifa.draw.framework.Figure without);

    /**
     * Adds a listener for this drawing.
     */
    public void addDrawingChangeListener(CH.ifa.draw.framework.DrawingChangeListener listener);

    /**
     * Removes a listener from this drawing.
     */
    public void removeDrawingChangeListener(CH.ifa.draw.framework.DrawingChangeListener listener);

    /**
     * Gets the listeners of a drawing.
     */
    public java.util.Enumeration drawingChangeListeners();

    /**
     * Adds a figure and sets its container to refer
     * to this drawing.
     *
     * @return the figure that was inserted.
     */
    public CH.ifa.draw.framework.Figure add(CH.ifa.draw.framework.Figure figure);

    /**
     * Adds a vector of figures.
     */
    public void addAll(java.util.Vector newFigures);

    /**
     * Removes the figure from the drawing and releases it.
     */
    public CH.ifa.draw.framework.Figure remove(CH.ifa.draw.framework.Figure figure);

    /**
     * Removes a figure from the figure list, but
     * doesn't release it. Use this method to temporarily
     * manipulate a figure outside of the drawing.
     */
    public CH.ifa.draw.framework.Figure orphan(CH.ifa.draw.framework.Figure figure);

    /**
     * Removes a vector of figures from the figure's list
     * without releasing the figures.
     *
     * @see orphan
     */
    public void orphanAll(java.util.Vector newFigures);

    /**
     * Removes a vector of figures .
     *
     * @see remove
     */
    public void removeAll(java.util.Vector figures);

    /**
     * Replaces a figure in the drawing without
     * removing it from the drawing.
     */
    public void replace(CH.ifa.draw.framework.Figure figure, CH.ifa.draw.framework.Figure replacement);

    /**
     * Sends a figure to the back of the drawing.
     */
    public void sendToBack(CH.ifa.draw.framework.Figure figure);

    /**
     * Brings a figure to the front.
     */
    public void bringToFront(CH.ifa.draw.framework.Figure figure);

    /**
     * Draws all the figures back to front.
     */
    public void draw(java.awt.Graphics g);

    /**
     * Invalidates a rectangle and merges it with the
     * existing damaged area.
     */
    public void figureInvalidated(CH.ifa.draw.framework.FigureChangeEvent e);

    /**
     * Forces an update of the drawing change listeners.
     */
    public void figureRequestUpdate(CH.ifa.draw.framework.FigureChangeEvent e);

    /**
     * Handles a removeFrfigureRequestRemove request that
     * is passed up the figure container hierarchy.
     *
     * @see FigureChangeListener
     */
    public void figureRequestRemove(CH.ifa.draw.framework.FigureChangeEvent e);

    /**
     * Acquires the drawing lock.
     */
    public void lock();

    /**
     * Releases the drawing lock.
     */
    public void unlock();
}