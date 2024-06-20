/* @(#)AbstractFigure.java 5.1 */
package CH.ifa.draw.standard;
/**
 * AbstractFigure provides default implementations for
 * the Figure interface.
 *
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld036.htm>Template Method</a></b><br>
 * Template Methods implement default and invariant behavior for
 * figure subclasses.
 * <hr>
 *
 * @see Figure
 * @see Handle
 */
public abstract class AbstractFigure implements CH.ifa.draw.framework.Figure {
    /**
     * The listeners for a figure's changes.
     *
     * @see #invalidate
     * @see #changed
     * @see #willChange
     */
    private transient CH.ifa.draw.framework.FigureChangeListener fListener;

    /* Serialization support. */
    private static final long serialVersionUID = -10857585979273442L;

    private int abstractFigureSerializedDataVersion = 1;

    protected AbstractFigure() {
    }

    /**
     * Moves the figure by the given offset.
     */
    public void moveBy(int dx, int dy) {
        willChange();
        basicMoveBy(dx, dy);
        changed();
    }

    /**
     * Moves the figure. This is the
     * method that subclassers override. Clients usually
     * call displayBox.
     *
     * @see moveBy
     */
    protected abstract void basicMoveBy(int dx, int dy);

    /**
     * Changes the display box of a figure. Clients usually
     * call this method. It changes the display box
     * and announces the corresponding change.
     *
     * @param origin
     * 		the new origin
     * @param corner
     * 		the new corner
     * @see displayBox
     */
    public void displayBox(java.awt.Point origin, java.awt.Point corner) {
        willChange();
        basicDisplayBox(origin, corner);
        changed();
    }

    /**
     * Sets the display box of a figure. This is the
     * method that subclassers override. Clients usually
     * call displayBox.
     *
     * @see displayBox
     */
    public abstract void basicDisplayBox(java.awt.Point origin, java.awt.Point corner);

    /**
     * Gets the display box of a figure.
     */
    public abstract java.awt.Rectangle displayBox();

    /**
     * Returns the handles of a Figure that can be used
     * to manipulate some of its attributes.
     *
     * @return a Vector of handles
     * @see Handle
     */
    public abstract java.util.Vector handles();

    /**
     * Returns an Enumeration of the figures contained in this figure.
     *
     * @see CompositeFigure
     */
    public CH.ifa.draw.framework.FigureEnumeration figures() {
        java.util.Vector figures = new java.util.Vector(1);
        figures.addElement(this);
        return new CH.ifa.draw.standard.FigureEnumerator(figures);
    }

    /**
     * Gets the size of the figure. A convenience method.
     */
    public java.awt.Dimension size() {
        return new java.awt.Dimension(displayBox().width, displayBox().height);
    }

    /**
     * Checks if the figure is empty. The default implementation returns
     * true if the width or height of its display box is < 3
     *
     * @see Figure#isEmpty
     */
    public boolean isEmpty() {
        return (size().width < 3) || (size().height < 3);
    }

    /**
     * Returns the figure that contains the given point.
     * In contrast to containsPoint it returns its
     * innermost figure that contains the point.
     *
     * @see #containsPoint
     */
    public CH.ifa.draw.framework.Figure findFigureInside(int x, int y) {
        if (containsPoint(x, y))
            return this;

        return null;
    }

    /**
     * Checks if a point is inside the figure.
     */
    public boolean containsPoint(int x, int y) {
        return displayBox().contains(x, y);
    }

    /**
     * Changes the display box of a figure. This is a
     * convenience method. Implementors should only
     * have to override basicDisplayBox
     *
     * @see displayBox
     */
    public void displayBox(java.awt.Rectangle r) {
        displayBox(new java.awt.Point(r.x, r.y), new java.awt.Point(r.x + r.width, r.y + r.height));
    }

    /**
     * Checks whether the given figure is contained in this figure.
     */
    public boolean includes(CH.ifa.draw.framework.Figure figure) {
        return figure == this;
    }

    /**
     * Decomposes a figure into its parts. It returns a Vector
     * that contains itself.
     *
     * @return an Enumeration for a Vector with itself as the
    only element.
     */
    public CH.ifa.draw.framework.FigureEnumeration decompose() {
        java.util.Vector figures = new java.util.Vector(1);
        figures.addElement(this);
        return new CH.ifa.draw.standard.FigureEnumerator(figures);
    }

    /**
     * Sets the Figure's container and registers the container
     * as a figure change listener. A figure's container can be
     * any kind of FigureChangeListener. A figure is not restricted
     * to have a single container.
     */
    public void addToContainer(CH.ifa.draw.framework.FigureChangeListener c) {
        addFigureChangeListener(c);
        invalidate();
    }

    /**
     * Removes a figure from the given container and unregisters
     * it as a change listener.
     */
    public void removeFromContainer(CH.ifa.draw.framework.FigureChangeListener c) {
        invalidate();
        removeFigureChangeListener(c);
        changed();
    }

    /**
     * Adds a listener for this figure.
     */
    public void addFigureChangeListener(CH.ifa.draw.framework.FigureChangeListener l) {
        fListener = CH.ifa.draw.standard.FigureChangeEventMulticaster.add(fListener, l);
    }

    /**
     * Removes a listener for this figure.
     */
    public void removeFigureChangeListener(CH.ifa.draw.framework.FigureChangeListener l) {
        fListener = CH.ifa.draw.standard.FigureChangeEventMulticaster.remove(fListener, l);
    }

    /**
     * Gets the figure's listners.
     */
    public CH.ifa.draw.framework.FigureChangeListener listener() {
        return fListener;
    }

    /**
     * A figure is released from the drawing. You never call this
     * method directly. Release notifies its listeners.
     *
     * @see Figure#release
     */
    public void release() {
        if (fListener != null)
            fListener.figureRemoved(new CH.ifa.draw.framework.FigureChangeEvent(this));

    }

    /**
     * Invalidates the figure. This method informs the listeners
     * that the figure's current display box is invalid and should be
     * refreshed.
     */
    public void invalidate() {
        if (fListener != null) {
            java.awt.Rectangle r = displayBox();
            r.grow(CH.ifa.draw.framework.Handle.HANDLESIZE, CH.ifa.draw.framework.Handle.HANDLESIZE);
            fListener.figureInvalidated(new CH.ifa.draw.framework.FigureChangeEvent(this, r));
        }
    }

    /**
     * Informes that a figure is about to change something that
     * affects the contents of its display box.
     *
     * @see Figure#willChange
     */
    public void willChange() {
        invalidate();
    }

    /**
     * Informs that a figure changed the area of its display box.
     *
     * @see FigureChangeEvent
     * @see Figure#changed
     */
    public void changed() {
        invalidate();
        if (fListener != null)
            fListener.figureChanged(new CH.ifa.draw.framework.FigureChangeEvent(this));

    }

    /**
     * Gets the center of a figure. A convenice
     * method that is rarely overridden.
     */
    public java.awt.Point center() {
        return CH.ifa.draw.util.Geom.center(displayBox());
    }

    /**
     * Checks if this figure can be connected. By default
     * AbstractFigures can be connected.
     */
    public boolean canConnect() {
        return true;
    }

    /**
     * Returns the connection inset. The connection inset
     * defines the area where the display box of a
     * figure can't be connected. By default the entire
     * display box can be connected.
     */
    public java.awt.Insets connectionInsets() {
        return new java.awt.Insets(0, 0, 0, 0);
    }

    /**
     * Returns the Figures connector for the specified location.
     * By default a ChopBoxConnector is returned.
     *
     * @see ChopBoxConnector
     */
    public CH.ifa.draw.framework.Connector connectorAt(int x, int y) {
        return new CH.ifa.draw.standard.ChopBoxConnector(this);
    }

    /**
     * Sets whether the connectors should be visible.
     * By default they are not visible and
     */
    public void connectorVisibility(boolean isVisible) {
    }

    /**
     * Returns the locator used to located connected text.
     */
    public CH.ifa.draw.framework.Locator connectedTextLocator(CH.ifa.draw.framework.Figure text) {
        return CH.ifa.draw.standard.RelativeLocator.center();
    }

    /**
     * Returns the named attribute or null if a
     * a figure doesn't have an attribute.
     * By default
     * figures don't have any attributes getAttribute
     * returns null.
     */
    public java.lang.Object getAttribute(java.lang.String name) {
        return null;
    }

    /**
     * Sets the named attribute to the new value. By default
     * figures don't have any attributes and the request is ignored.
     */
    public void setAttribute(java.lang.String name, java.lang.Object value) {
    }

    /**
     * Clones a figure. Creates a clone by using the storable
     * mechanism to flatten the Figure to stream followed by
     * resurrecting it from the same stream.
     *
     * @see Figure#clone
     */
    public java.lang.Object clone() {
        java.lang.Object clone = null;
        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream(200);
        try {
            java.io.ObjectOutput writer = new java.io.ObjectOutputStream(output);
            writer.writeObject(this);
            writer.close();
        } catch (java.io.IOException e) {
            java.lang.System.out.println("Class not found: " + e);
        }
        java.io.InputStream input = new java.io.ByteArrayInputStream(output.toByteArray());
        try {
            java.io.ObjectInput reader = new java.io.ObjectInputStream(input);
            clone = ((java.lang.Object) (reader.readObject()));
        } catch (java.io.IOException e) {
            java.lang.System.out.println(e.toString());
        } catch (java.lang.ClassNotFoundException e) {
            java.lang.System.out.println("Class not found: " + e);
        }
        return clone;
    }

    /**
     * Stores the Figure to a StorableOutput.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
    }

    /**
     * Reads the Figure from a StorableInput.
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
    }
}