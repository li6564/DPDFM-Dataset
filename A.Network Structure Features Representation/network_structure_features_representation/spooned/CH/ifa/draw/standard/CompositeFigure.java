/* @(#)CompositeFigure.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A Figure that is composed of several figures. A CompositeFigure
 * doesn't define any layout behavior. It is up to subclassers to
 * arrange the contained figures.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld012.htm>Composite</a></b><br>
 * CompositeFigure enables to treat a composition of figures like
 * a single figure.<br>
 *
 * @see Figure
 */
public abstract class CompositeFigure extends CH.ifa.draw.standard.AbstractFigure implements CH.ifa.draw.framework.FigureChangeListener {
    /**
     * The figures that this figure is composed of
     *
     * @see #add
     * @see #remove
     */
    protected java.util.Vector fFigures;

    /* Serialization support. */
    private static final long serialVersionUID = 7408153435700021866L;

    private int compositeFigureSerializedDataVersion = 1;

    protected CompositeFigure() {
        fFigures = new java.util.Vector();
    }

    /**
     * Adds a figure to the list of figures. Initializes the
     * the figure's container.
     */
    public CH.ifa.draw.framework.Figure add(CH.ifa.draw.framework.Figure figure) {
        if (!fFigures.contains(figure)) {
            fFigures.addElement(figure);
            figure.addToContainer(this);
        }
        return figure;
    }

    /**
     * Adds a vector of figures.
     *
     * @see #add
     */
    public void addAll(java.util.Vector newFigures) {
        java.util.Enumeration k = newFigures.elements();
        while (k.hasMoreElements())
            add(((CH.ifa.draw.framework.Figure) (k.nextElement())));

    }

    /**
     * Removes a figure from the composite.
     *
     * @see #removeAll
     */
    public CH.ifa.draw.framework.Figure remove(CH.ifa.draw.framework.Figure figure) {
        if (fFigures.contains(figure)) {
            figure.removeFromContainer(this);
            fFigures.removeElement(figure);
        }
        return figure;
    }

    /**
     * Removes a vector of figures.
     *
     * @see #remove
     */
    public void removeAll(java.util.Vector figures) {
        java.util.Enumeration k = figures.elements();
        while (k.hasMoreElements())
            remove(((CH.ifa.draw.framework.Figure) (k.nextElement())));

    }

    /**
     * Removes all children.
     *
     * @see #remove
     */
    public void removeAll() {
        CH.ifa.draw.framework.FigureEnumeration k = figures();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            figure.removeFromContainer(this);
        } 
        fFigures.removeAllElements();
    }

    /**
     * Removes a figure from the figure list, but
     * doesn't release it. Use this method to temporarily
     * manipulate a figure outside of the drawing.
     */
    public synchronized CH.ifa.draw.framework.Figure orphan(CH.ifa.draw.framework.Figure figure) {
        fFigures.removeElement(figure);
        return figure;
    }

    /**
     * Removes a vector of figures from the figure's list
     * without releasing the figures.
     *
     * @see orphan
     */
    public void orphanAll(java.util.Vector newFigures) {
        java.util.Enumeration k = newFigures.elements();
        while (k.hasMoreElements())
            orphan(((CH.ifa.draw.framework.Figure) (k.nextElement())));

    }

    /**
     * Replaces a figure in the drawing without
     * removing it from the drawing.
     */
    public synchronized void replace(CH.ifa.draw.framework.Figure figure, CH.ifa.draw.framework.Figure replacement) {
        int index = fFigures.indexOf(figure);
        if (index != (-1)) {
            replacement.addToContainer(this);// will invalidate figure

            figure.changed();
            fFigures.setElementAt(replacement, index);
        }
    }

    /**
     * Sends a figure to the back of the drawing.
     */
    public synchronized void sendToBack(CH.ifa.draw.framework.Figure figure) {
        if (fFigures.contains(figure)) {
            fFigures.removeElement(figure);
            fFigures.insertElementAt(figure, 0);
            figure.changed();
        }
    }

    /**
     * Brings a figure to the front.
     */
    public synchronized void bringToFront(CH.ifa.draw.framework.Figure figure) {
        if (fFigures.contains(figure)) {
            fFigures.removeElement(figure);
            fFigures.addElement(figure);
            figure.changed();
        }
    }

    /**
     * Draws all the contained figures
     *
     * @see Figure#draw
     */
    public void draw(java.awt.Graphics g) {
        CH.ifa.draw.framework.FigureEnumeration k = figures();
        while (k.hasMoreElements())
            k.nextFigure().draw(g);

    }

    /**
     * Gets a figure at the given index.
     */
    public CH.ifa.draw.framework.Figure figureAt(int i) {
        return ((CH.ifa.draw.framework.Figure) (fFigures.elementAt(i)));
    }

    /**
     * Returns an Enumeration for accessing the contained figures.
     * The figures are returned in the drawing order.
     */
    public final CH.ifa.draw.framework.FigureEnumeration figures() {
        return new CH.ifa.draw.standard.FigureEnumerator(fFigures);
    }

    /**
     * Gets number of child figures.
     */
    public int figureCount() {
        return fFigures.size();
    }

    /**
     * Returns an Enumeration for accessing the contained figures
     * in the reverse drawing order.
     */
    public final CH.ifa.draw.framework.FigureEnumeration figuresReverse() {
        return new CH.ifa.draw.standard.ReverseFigureEnumerator(fFigures);
    }

    /**
     * Finds a top level Figure. Use this call for hit detection that
     * should not descend into the figure's children.
     */
    public CH.ifa.draw.framework.Figure findFigure(int x, int y) {
        CH.ifa.draw.framework.FigureEnumeration k = figuresReverse();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            if (figure.containsPoint(x, y))
                return figure;

        } 
        return null;
    }

    /**
     * Finds a top level Figure that intersects the given rectangle.
     */
    public CH.ifa.draw.framework.Figure findFigure(java.awt.Rectangle r) {
        CH.ifa.draw.framework.FigureEnumeration k = figuresReverse();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            java.awt.Rectangle fr = figure.displayBox();
            if (r.intersects(fr))
                return figure;

        } 
        return null;
    }

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
    public CH.ifa.draw.framework.Figure findFigureWithout(int x, int y, CH.ifa.draw.framework.Figure without) {
        if (without == null)
            return findFigure(x, y);

        CH.ifa.draw.framework.FigureEnumeration k = figuresReverse();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            if (figure.containsPoint(x, y) && (!figure.includes(without)))
                return figure;

        } 
        return null;
    }

    /**
     * Finds a top level Figure that intersects the given rectangle.
     * It supresses the passed
     * in figure. Use this method to ignore a figure
     * that is temporarily inserted into the drawing.
     */
    public CH.ifa.draw.framework.Figure findFigure(java.awt.Rectangle r, CH.ifa.draw.framework.Figure without) {
        if (without == null)
            return findFigure(r);

        CH.ifa.draw.framework.FigureEnumeration k = figuresReverse();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            java.awt.Rectangle fr = figure.displayBox();
            if (r.intersects(fr) && (!figure.includes(without)))
                return figure;

        } 
        return null;
    }

    /**
     * Finds a figure but descends into a figure's
     * children. Use this method to implement <i>click-through</i>
     * hit detection, that is, you want to detect the inner most
     * figure containing the given point.
     */
    public CH.ifa.draw.framework.Figure findFigureInside(int x, int y) {
        CH.ifa.draw.framework.FigureEnumeration k = figuresReverse();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure().findFigureInside(x, y);
            if (figure != null)
                return figure;

        } 
        return null;
    }

    /**
     * Finds a figure but descends into a figure's
     * children. It supresses the passed
     * in figure. Use this method to ignore a figure
     * that is temporarily inserted into the drawing.
     */
    public CH.ifa.draw.framework.Figure findFigureInsideWithout(int x, int y, CH.ifa.draw.framework.Figure without) {
        CH.ifa.draw.framework.FigureEnumeration k = figuresReverse();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            if (figure != without) {
                CH.ifa.draw.framework.Figure found = figure.findFigureInside(x, y);
                if (found != null)
                    return found;

            }
        } 
        return null;
    }

    /**
     * Checks if the composite figure has the argument as one of
     * its children.
     */
    public boolean includes(CH.ifa.draw.framework.Figure figure) {
        if (super.includes(figure))
            return true;

        CH.ifa.draw.framework.FigureEnumeration k = figures();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure f = k.nextFigure();
            if (f.includes(figure))
                return true;

        } 
        return false;
    }

    /**
     * Moves all the given figures by x and y. Doesn't announce
     * any changes. Subclassers override
     * basicMoveBy. Clients usually call moveBy.
     *
     * @see moveBy
     */
    protected void basicMoveBy(int x, int y) {
        CH.ifa.draw.framework.FigureEnumeration k = figures();
        while (k.hasMoreElements())
            k.nextFigure().moveBy(x, y);

    }

    /**
     * Releases the figure and all its children.
     */
    public void release() {
        super.release();
        CH.ifa.draw.framework.FigureEnumeration k = figures();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            figure.release();
        } 
    }

    /**
     * Propagates the figureInvalidated event to my listener.
     *
     * @see FigureChangeListener
     */
    public void figureInvalidated(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (listener() != null)
            listener().figureInvalidated(e);

    }

    /**
     * Propagates the removeFromDrawing request up to the container.
     *
     * @see FigureChangeListener
     */
    public void figureRequestRemove(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (listener() != null)
            listener().figureRequestRemove(new CH.ifa.draw.framework.FigureChangeEvent(this));

    }

    /**
     * Propagates the requestUpdate request up to the container.
     *
     * @see FigureChangeListener
     */
    public void figureRequestUpdate(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (listener() != null)
            listener().figureRequestUpdate(e);

    }

    public void figureChanged(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    public void figureRemoved(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    /**
     * Writes the contained figures to the StorableOutput.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fFigures.size());
        java.util.Enumeration k = fFigures.elements();
        while (k.hasMoreElements())
            dw.writeStorable(((CH.ifa.draw.util.Storable) (k.nextElement())));

    }

    /**
     * Reads the contained figures from StorableInput.
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        int size = dr.readInt();
        fFigures = new java.util.Vector(size);
        for (int i = 0; i < size; i++)
            add(((CH.ifa.draw.framework.Figure) (dr.readStorable())));

    }

    private void readObject(java.io.ObjectInputStream s) throws java.lang.ClassNotFoundException, java.io.IOException {
        s.defaultReadObject();
        CH.ifa.draw.framework.FigureEnumeration k = figures();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            figure.addToContainer(this);
        } 
    }
}