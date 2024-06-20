/* @(#)StandardDrawing.java 5.1 */
package CH.ifa.draw.standard;
/**
 * The standard implementation of the Drawing interface.
 *
 * @see Drawing
 */
public class StandardDrawing extends CH.ifa.draw.standard.CompositeFigure implements CH.ifa.draw.framework.Drawing {
    /**
     * the registered listeners
     */
    private transient java.util.Vector fListeners;

    /**
     * boolean that serves as a condition variable
     * to lock the access to the drawing.
     * The lock is recursive and we keep track of the current
     * lock holder.
     */
    private transient java.lang.Thread fDrawingLockHolder = null;

    /* Serialization support */
    private static final long serialVersionUID = -2602151437447962046L;

    private int drawingSerializedDataVersion = 1;

    /**
     * Constructs the Drawing.
     */
    public StandardDrawing() {
        super();
        fListeners = new java.util.Vector(2);
    }

    /**
     * Adds a listener for this drawing.
     */
    public void addDrawingChangeListener(CH.ifa.draw.framework.DrawingChangeListener listener) {
        fListeners.addElement(listener);
    }

    /**
     * Removes a listener from this drawing.
     */
    public void removeDrawingChangeListener(CH.ifa.draw.framework.DrawingChangeListener listener) {
        fListeners.removeElement(listener);
    }

    /**
     * Adds a listener for this drawing.
     */
    public java.util.Enumeration drawingChangeListeners() {
        return fListeners.elements();
    }

    /**
     * Removes the figure from the drawing and releases it.
     */
    public synchronized CH.ifa.draw.framework.Figure remove(CH.ifa.draw.framework.Figure figure) {
        // ensure that we remove the top level figure in a drawing
        if (figure.listener() != null) {
            figure.listener().figureRequestRemove(new CH.ifa.draw.framework.FigureChangeEvent(figure, null));
            return figure;
        }
        return null;
    }

    /**
     * Handles a removeFromDrawing request that
     * is passed up the figure container hierarchy.
     *
     * @see FigureChangeListener
     */
    public void figureRequestRemove(CH.ifa.draw.framework.FigureChangeEvent e) {
        CH.ifa.draw.framework.Figure figure = e.getFigure();
        if (fFigures.contains(figure)) {
            fFigures.removeElement(figure);
            figure.removeFromContainer(this);// will invalidate figure

            figure.release();
        } else
            java.lang.System.out.println("Attempt to remove non-existing figure");

    }

    /**
     * Invalidates a rectangle and merges it with the
     * existing damaged area.
     *
     * @see FigureChangeListener
     */
    public void figureInvalidated(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (fListeners != null) {
            for (int i = 0; i < fListeners.size(); i++) {
                CH.ifa.draw.framework.DrawingChangeListener l = ((CH.ifa.draw.framework.DrawingChangeListener) (fListeners.elementAt(i)));
                l.drawingInvalidated(new CH.ifa.draw.framework.DrawingChangeEvent(this, e.getInvalidatedRectangle()));
            }
        }
    }

    /**
     * Forces an update
     */
    public void figureRequestUpdate(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (fListeners != null) {
            for (int i = 0; i < fListeners.size(); i++) {
                CH.ifa.draw.framework.DrawingChangeListener l = ((CH.ifa.draw.framework.DrawingChangeListener) (fListeners.elementAt(i)));
                l.drawingRequestUpdate(new CH.ifa.draw.framework.DrawingChangeEvent(this, null));
            }
        }
    }

    /**
     * Return's the figure's handles. This is only used when a drawing
     * is nested inside another drawing.
     */
    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector();
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.northWest()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.northEast()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.southWest()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.southEast()));
        return handles;
    }

    /**
     * Gets the display box. This is the union of all figures.
     */
    public java.awt.Rectangle displayBox() {
        if (fFigures.size() > 0) {
            CH.ifa.draw.framework.FigureEnumeration k = figures();
            java.awt.Rectangle r = k.nextFigure().displayBox();
            while (k.hasMoreElements())
                r.add(k.nextFigure().displayBox());

            return r;
        }
        return new java.awt.Rectangle(0, 0, 0, 0);
    }

    public void basicDisplayBox(java.awt.Point p1, java.awt.Point p2) {
    }

    /**
     * Acquires the drawing lock.
     */
    public synchronized void lock() {
        // recursive lock
        java.lang.Thread current = java.lang.Thread.currentThread();
        if (fDrawingLockHolder == current)
            return;

        while (fDrawingLockHolder != null) {
            try {
                wait();
            } catch (java.lang.InterruptedException ex) {
            }
        } 
        fDrawingLockHolder = current;
    }

    /**
     * Releases the drawing lock.
     */
    public synchronized void unlock() {
        if (fDrawingLockHolder != null) {
            fDrawingLockHolder = null;
            notifyAll();
        }
    }

    private void readObject(java.io.ObjectInputStream s) throws java.lang.ClassNotFoundException, java.io.IOException {
        s.defaultReadObject();
        fListeners = new java.util.Vector(2);
    }
}