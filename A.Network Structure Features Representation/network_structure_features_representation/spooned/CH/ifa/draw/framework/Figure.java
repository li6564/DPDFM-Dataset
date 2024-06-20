/* @(#)Figure.java 5.1 */
package CH.ifa.draw.framework;
/**
 * The interface of a graphical figure. A figure knows
 * its display box and can draw itself. A figure can be
 * composed of several figures. To interact and manipulate
 * with a figure it can provide Handles and Connectors.<p>
 * A figure has a set of handles to manipulate its shape or attributes.
 * A figure has one or more connectors that define how
 * to locate a connection point.<p>
 * Figures can have an open ended set of attributes.
 * An attribute is identified by a string.<p>
 * Default implementations for the Figure interface are provided
 * by AbstractFigure.
 *
 * @see Handle
 * @see Connector
 * @see AbstractFigure
 */
public interface Figure extends CH.ifa.draw.util.Storable , java.lang.Cloneable , java.io.Serializable {
    /**
     * Moves the Figure to a new location.
     *
     * @param x
     * 		the x delta
     * @param y
     * 		the y delta
     */
    public void moveBy(int dx, int dy);

    /**
     * Changes the display box of a figure. This method is
     * always implemented in figure subclasses.
     * It only changes
     * the displaybox and does not announce any changes. It
     * is usually not called by the client. Clients typically call
     * displayBox to change the display box.
     *
     * @param origin
     * 		the new origin
     * @param corner
     * 		the new corner
     * @see #displayBox
     */
    public void basicDisplayBox(java.awt.Point origin, java.awt.Point corner);

    /**
     * Changes the display box of a figure. Clients usually
     * invoke this method. It changes the display box
     * and announces the corresponding changes.
     *
     * @param origin
     * 		the new origin
     * @param corner
     * 		the new corner
     * @see #displayBox
     */
    public void displayBox(java.awt.Point origin, java.awt.Point corner);

    /**
     * Gets the display box of a figure
     *
     * @see #basicDisplayBox
     */
    public java.awt.Rectangle displayBox();

    /**
     * Draws the figure.
     *
     * @param g
     * 		the Graphics to draw into
     */
    public void draw(java.awt.Graphics g);

    /**
     * Returns the handles used to manipulate
     * the figure. Handles is a Factory Method for
     * creating handle objects.
     *
     * @return a Vector of handles
     * @see Handle
     */
    public java.util.Vector handles();

    /**
     * Gets the size of the figure
     */
    public java.awt.Dimension size();

    /**
     * Gets the figure's center
     */
    public java.awt.Point center();

    /**
     * Checks if the Figure should be considered as empty.
     */
    public boolean isEmpty();

    /**
     * Returns an Enumeration of the figures contained in this figure
     */
    public CH.ifa.draw.framework.FigureEnumeration figures();

    /**
     * Returns the figure that contains the given point.
     */
    public CH.ifa.draw.framework.Figure findFigureInside(int x, int y);

    /**
     * Checks if a point is inside the figure.
     */
    public boolean containsPoint(int x, int y);

    /**
     * Returns a Clone of this figure
     */
    public java.lang.Object clone();

    /**
     * Changes the display box of a figure. This is a
     * convenience method. Implementors should only
     * have to override basicDisplayBox
     *
     * @see #displayBox
     */
    public void displayBox(java.awt.Rectangle r);

    /**
     * Checks whether the given figure is contained in this figure.
     */
    public boolean includes(CH.ifa.draw.framework.Figure figure);

    /**
     * Decomposes a figure into its parts. A figure is considered
     * as a part of itself.
     */
    public CH.ifa.draw.framework.FigureEnumeration decompose();

    /**
     * Sets the Figure's container and registers the container
     * as a figure change listener. A figure's container can be
     * any kind of FigureChangeListener. A figure is not restricted
     * to have a single container.
     */
    public void addToContainer(CH.ifa.draw.framework.FigureChangeListener c);

    /**
     * Removes a figure from the given container and unregisters
     * it as a change listener.
     */
    public void removeFromContainer(CH.ifa.draw.framework.FigureChangeListener c);

    /**
     * Gets the Figure's listeners.
     */
    public CH.ifa.draw.framework.FigureChangeListener listener();

    /**
     * Adds a listener for this figure.
     */
    public void addFigureChangeListener(CH.ifa.draw.framework.FigureChangeListener l);

    /**
     * Removes a listener for this figure.
     */
    public void removeFigureChangeListener(CH.ifa.draw.framework.FigureChangeListener l);

    /**
     * Releases a figure's resources. Release is called when
     * a figure is removed from a drawing. Informs the listeners that
     * the figure is removed by calling figureRemoved.
     */
    public void release();

    /**
     * Invalidates the figure. This method informs its listeners
     * that its current display box is invalid and should be
     * refreshed.
     */
    public void invalidate();

    /**
     * Informes that a figure is about to change such that its
     * display box is affected.
     * Here is an example of how it is used together with changed()
     * <pre>
     * public void move(int x, int y) {
     *      willChange();
     *      // change the figure's location
     *      changed();
     *  }
     * </pre>
     *
     * @see #invalidate
     * @see #changed
     */
    public void willChange();

    /**
     * Informes that a figure has changed its display box.
     * This method also triggers an update call for its
     * registered observers.
     *
     * @see #invalidate
     * @see #willChange
     */
    public void changed();

    /**
     * Checks if this figure can be connected
     */
    public boolean canConnect();

    /**
     * Gets a connector for this figure at the given location.
     * A figure can have different connectors at different locations.
     */
    public CH.ifa.draw.framework.Connector connectorAt(int x, int y);

    /**
     * Sets whether the connectors should be visible.
     * Connectors can be optionally visible. Implement
     * this method and react on isVisible to turn the
     * connectors on or off.
     */
    public void connectorVisibility(boolean isVisible);

    /**
     * Returns the connection inset. This is only a hint that
     * connectors can use to determine the connection location.
     * The inset defines the area where the display box of a
     * figure should not be connected.
     */
    public java.awt.Insets connectionInsets();

    /**
     * Returns the locator used to located connected text.
     */
    public CH.ifa.draw.framework.Locator connectedTextLocator(CH.ifa.draw.framework.Figure text);

    /**
     * Returns the named attribute or null if a
     * a figure doesn't have an attribute.
     * All figures support the attribute names
     * FillColor and FrameColor
     */
    public java.lang.Object getAttribute(java.lang.String name);

    /**
     * Sets the named attribute to the new value
     */
    public void setAttribute(java.lang.String name, java.lang.Object value);
}