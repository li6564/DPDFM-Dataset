/* @(#)LineConnection.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A LineConnection is a standard implementation of the
 * ConnectionFigure interface. The interface is implemented with PolyLineFigure.
 *
 * @see ConnectionFigure
 */
public class LineConnection extends CH.ifa.draw.figures.PolyLineFigure implements CH.ifa.draw.framework.ConnectionFigure {
    protected CH.ifa.draw.framework.Connector fStart = null;

    protected CH.ifa.draw.framework.Connector fEnd = null;

    /* Serialization support. */
    private static final long serialVersionUID = 6883731614578414801L;

    private int lineConnectionSerializedDataVersion = 1;

    /**
     * Constructs a LineConnection. A connection figure has
     * an arrow decoration at the start and end.
     */
    public LineConnection() {
        super(4);
        setStartDecoration(new CH.ifa.draw.figures.ArrowTip());
        setEndDecoration(new CH.ifa.draw.figures.ArrowTip());
    }

    /**
     * Tests whether a figure can be a connection target.
     * ConnectionFigures cannot be connected and return false.
     */
    public boolean canConnect() {
        return false;
    }

    /**
     * Ensures that a connection is updated if the connection
     * was moved.
     */
    protected void basicMoveBy(int dx, int dy) {
        // don't move the start and end point since they are connected
        for (int i = 1; i < (fPoints.size() - 1); i++)
            ((java.awt.Point) (fPoints.elementAt(i))).translate(dx, dy);

        updateConnection();// make sure that we are still connected

    }

    /**
     * Sets the start figure of the connection.
     */
    public void connectStart(CH.ifa.draw.framework.Connector start) {
        fStart = start;
        startFigure().addFigureChangeListener(this);
    }

    /**
     * Sets the end figure of the connection.
     */
    public void connectEnd(CH.ifa.draw.framework.Connector end) {
        fEnd = end;
        endFigure().addFigureChangeListener(this);
        handleConnect(startFigure(), endFigure());
    }

    /**
     * Disconnects the start figure.
     */
    public void disconnectStart() {
        startFigure().removeFigureChangeListener(this);
        fStart = null;
    }

    /**
     * Disconnects the end figure.
     */
    public void disconnectEnd() {
        handleDisconnect(startFigure(), endFigure());
        endFigure().removeFigureChangeListener(this);
        fEnd = null;
    }

    /**
     * Tests whether a connection connects the same figures
     * as another ConnectionFigure.
     */
    public boolean connectsSame(CH.ifa.draw.framework.ConnectionFigure other) {
        return (other.start() == start()) && (other.end() == end());
    }

    /**
     * Handles the disconnection of a connection.
     * Override this method to handle this event.
     */
    protected void handleDisconnect(CH.ifa.draw.framework.Figure start, CH.ifa.draw.framework.Figure end) {
    }

    /**
     * Handles the connection of a connection.
     * Override this method to handle this event.
     */
    protected void handleConnect(CH.ifa.draw.framework.Figure start, CH.ifa.draw.framework.Figure end) {
    }

    /**
     * Gets the start figure of the connection.
     */
    public CH.ifa.draw.framework.Figure startFigure() {
        if (start() != null)
            return start().owner();

        return null;
    }

    /**
     * Gets the end figure of the connection.
     */
    public CH.ifa.draw.framework.Figure endFigure() {
        if (end() != null)
            return end().owner();

        return null;
    }

    /**
     * Gets the start figure of the connection.
     */
    public CH.ifa.draw.framework.Connector start() {
        return fStart;
    }

    /**
     * Gets the end figure of the connection.
     */
    public CH.ifa.draw.framework.Connector end() {
        return fEnd;
    }

    /**
     * Tests whether two figures can be connected.
     */
    public boolean canConnect(CH.ifa.draw.framework.Figure start, CH.ifa.draw.framework.Figure end) {
        return true;
    }

    /**
     * Sets the start point.
     */
    public void startPoint(int x, int y) {
        willChange();
        if (fPoints.size() == 0)
            fPoints.addElement(new java.awt.Point(x, y));
        else
            fPoints.setElementAt(new java.awt.Point(x, y), 0);

        changed();
    }

    /**
     * Sets the end point.
     */
    public void endPoint(int x, int y) {
        willChange();
        if (fPoints.size() < 2)
            fPoints.addElement(new java.awt.Point(x, y));
        else
            fPoints.setElementAt(new java.awt.Point(x, y), fPoints.size() - 1);

        changed();
    }

    /**
     * Gets the start point.
     */
    public java.awt.Point startPoint() {
        java.awt.Point p = ((java.awt.Point) (fPoints.firstElement()));
        return new java.awt.Point(p.x, p.y);
    }

    /**
     * Gets the end point.
     */
    public java.awt.Point endPoint() {
        java.awt.Point p = ((java.awt.Point) (fPoints.lastElement()));
        return new java.awt.Point(p.x, p.y);
    }

    /**
     * Gets the handles of the figure. It returns the normal
     * PolyLineHandles but adds ChangeConnectionHandles at the
     * start and end.
     */
    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector(fPoints.size());
        handles.addElement(new CH.ifa.draw.standard.ChangeConnectionStartHandle(this));
        for (int i = 1; i < (fPoints.size() - 1); i++)
            handles.addElement(new CH.ifa.draw.figures.PolyLineHandle(this, CH.ifa.draw.figures.PolyLineFigure.locator(i), i));

        handles.addElement(new CH.ifa.draw.standard.ChangeConnectionEndHandle(this));
        return handles;
    }

    /**
     * Sets the point and updates the connection.
     */
    public void setPointAt(java.awt.Point p, int i) {
        super.setPointAt(p, i);
        layoutConnection();
    }

    /**
     * Inserts the point and updates the connection.
     */
    public void insertPointAt(java.awt.Point p, int i) {
        super.insertPointAt(p, i);
        layoutConnection();
    }

    /**
     * Removes the point and updates the connection.
     */
    public void removePointAt(int i) {
        super.removePointAt(i);
        layoutConnection();
    }

    /**
     * Updates the connection.
     */
    public void updateConnection() {
        if (fStart != null) {
            java.awt.Point start = fStart.findStart(this);
            startPoint(start.x, start.y);
        }
        if (fEnd != null) {
            java.awt.Point end = fEnd.findEnd(this);
            endPoint(end.x, end.y);
        }
    }

    /**
     * Lays out the connection. This is called when the connection
     * itself changes. By default the connection is recalculated
     */
    public void layoutConnection() {
        updateConnection();
    }

    public void figureChanged(CH.ifa.draw.framework.FigureChangeEvent e) {
        updateConnection();
    }

    public void figureRemoved(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (listener() != null)
            listener().figureRequestRemove(new CH.ifa.draw.framework.FigureChangeEvent(this));

    }

    public void figureRequestRemove(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    public void figureInvalidated(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    public void figureRequestUpdate(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    public void release() {
        super.release();
        handleDisconnect(startFigure(), endFigure());
        if (fStart != null)
            startFigure().removeFigureChangeListener(this);

        if (fEnd != null)
            endFigure().removeFigureChangeListener(this);

    }

    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeStorable(fStart);
        dw.writeStorable(fEnd);
    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        CH.ifa.draw.framework.Connector start = ((CH.ifa.draw.framework.Connector) (dr.readStorable()));
        if (start != null)
            connectStart(start);

        CH.ifa.draw.framework.Connector end = ((CH.ifa.draw.framework.Connector) (dr.readStorable()));
        if (end != null)
            connectEnd(end);

        if ((start != null) && (end != null))
            updateConnection();

    }

    private void readObject(java.io.ObjectInputStream s) throws java.lang.ClassNotFoundException, java.io.IOException {
        s.defaultReadObject();
        if (fStart != null)
            connectStart(fStart);

        if (fEnd != null)
            connectEnd(fEnd);

    }
}