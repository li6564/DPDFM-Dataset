/* @(#)ConnectionHandle.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A handle to connect figures.
 * The connection object to be created is specified by a prototype.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld029.htm>Prototype</a></b><br>
 * ConnectionHandle creates the connection by cloning a prototype.
 * <hr>
 *
 * @see ConnectionFigure
 * @see Object#clone
 */
public class ConnectionHandle extends CH.ifa.draw.standard.LocatorHandle {
    /**
     * the currently created connection
     */
    private CH.ifa.draw.framework.ConnectionFigure fConnection;

    /**
     * the prototype of the connection to be created
     */
    private CH.ifa.draw.framework.ConnectionFigure fPrototype;

    /**
     * the current target
     */
    private CH.ifa.draw.framework.Figure fTarget = null;

    /**
     * Constructs a handle with the given owner, locator, and connection prototype
     */
    public ConnectionHandle(CH.ifa.draw.framework.Figure owner, CH.ifa.draw.framework.Locator l, CH.ifa.draw.framework.ConnectionFigure prototype) {
        super(owner, l);
        fPrototype = prototype;
    }

    /**
     * Creates the connection
     */
    public void invokeStart(int x, int y, CH.ifa.draw.framework.DrawingView view) {
        fConnection = createConnection();
        java.awt.Point p = locate();
        fConnection.startPoint(p.x, p.y);
        fConnection.endPoint(p.x, p.y);
        view.drawing().add(fConnection);
    }

    /**
     * Tracks the connection.
     */
    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        java.awt.Point p = new java.awt.Point(x, y);
        CH.ifa.draw.framework.Figure f = findConnectableFigure(x, y, view.drawing());
        // track the figure containing the mouse
        if (f != fTarget) {
            if (fTarget != null)
                fTarget.connectorVisibility(false);

            fTarget = f;
            if (fTarget != null)
                fTarget.connectorVisibility(true);

        }
        CH.ifa.draw.framework.Connector target = findConnectionTarget(p.x, p.y, view.drawing());
        if (target != null)
            p = CH.ifa.draw.util.Geom.center(target.displayBox());

        fConnection.endPoint(p.x, p.y);
    }

    /**
     * Connects the figures if the mouse is released over another
     * figure.
     */
    public void invokeEnd(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        CH.ifa.draw.framework.Connector target = findConnectionTarget(x, y, view.drawing());
        if (target != null) {
            fConnection.connectStart(startConnector());
            fConnection.connectEnd(target);
            fConnection.updateConnection();
        } else
            view.drawing().remove(fConnection);

        fConnection = null;
        if (fTarget != null) {
            fTarget.connectorVisibility(false);
            fTarget = null;
        }
    }

    private CH.ifa.draw.framework.Connector startConnector() {
        java.awt.Point p = locate();
        return owner().connectorAt(p.x, p.y);
    }

    /**
     * Creates the ConnectionFigure. By default the figure prototype is
     * cloned.
     */
    protected CH.ifa.draw.framework.ConnectionFigure createConnection() {
        return ((CH.ifa.draw.framework.ConnectionFigure) (fPrototype.clone()));
    }

    /**
     * Finds a connection end figure.
     */
    protected CH.ifa.draw.framework.Connector findConnectionTarget(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
        CH.ifa.draw.framework.Figure target = findConnectableFigure(x, y, drawing);
        if ((((target != null) && target.canConnect()) && (!target.includes(owner()))) && fConnection.canConnect(owner(), target)) {
            return findConnector(x, y, target);
        }
        return null;
    }

    private CH.ifa.draw.framework.Figure findConnectableFigure(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
        CH.ifa.draw.framework.FigureEnumeration k = drawing.figuresReverse();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            if ((!figure.includes(fConnection)) && figure.canConnect()) {
                if (figure.containsPoint(x, y))
                    return figure;

            }
        } 
        return null;
    }

    protected CH.ifa.draw.framework.Connector findConnector(int x, int y, CH.ifa.draw.framework.Figure f) {
        return f.connectorAt(x, y);
    }

    /**
     * Draws the connection handle, by default the outline of a
     * blue circle.
     */
    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.blue);
        g.drawOval(r.x, r.y, r.width, r.height);
    }
}