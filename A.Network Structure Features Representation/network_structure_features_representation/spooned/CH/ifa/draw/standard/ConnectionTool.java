/* @(#)ConnectionTool.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A tool that can be used to connect figures, to split
 * connections, and to join two segments of a connection.
 * ConnectionTools turns the visibility of the Connectors
 * on when it enters a figure.
 * The connection object to be created is specified by a prototype.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld029.htm>Prototype</a></b><br>
 * ConnectionTools creates the connection by cloning a prototype.
 * <hr>
 *
 * @see ConnectionFigure
 * @see Object#clone
 */
public class ConnectionTool extends CH.ifa.draw.standard.AbstractTool {
    /**
     * the anchor point of the interaction
     */
    private CH.ifa.draw.framework.Connector fStartConnector;

    private CH.ifa.draw.framework.Connector fEndConnector;

    private CH.ifa.draw.framework.Connector fConnectorTarget = null;

    private CH.ifa.draw.framework.Figure fTarget = null;

    /**
     * the currently created figure
     */
    private CH.ifa.draw.framework.ConnectionFigure fConnection;

    /**
     * the currently manipulated connection point
     */
    private int fSplitPoint;

    /**
     * the currently edited connection
     */
    private CH.ifa.draw.framework.ConnectionFigure fEditedConnection = null;

    /**
     * the prototypical figure that is used to create new
     * connections.
     */
    private CH.ifa.draw.framework.ConnectionFigure fPrototype;

    public ConnectionTool(CH.ifa.draw.framework.DrawingView view, CH.ifa.draw.framework.ConnectionFigure prototype) {
        super(view);
        fPrototype = prototype;
    }

    /**
     * Handles mouse move events in the drawing view.
     */
    public void mouseMove(java.awt.event.MouseEvent e, int x, int y) {
        trackConnectors(e, x, y);
    }

    /**
     * Manipulates connections in a context dependent way. If the
     * mouse down hits a figure start a new connection. If the mousedown
     * hits a connection split a segment or join two segments.
     */
    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        int ex = e.getX();
        int ey = e.getY();
        fTarget = findConnectionStart(ex, ey, drawing());
        if (fTarget != null) {
            fStartConnector = findConnector(ex, ey, fTarget);
            if (fStartConnector != null) {
                java.awt.Point p = new java.awt.Point(ex, ey);
                fConnection = createConnection();
                fConnection.startPoint(p.x, p.y);
                fConnection.endPoint(p.x, p.y);
                view().add(fConnection);
            }
        } else {
            CH.ifa.draw.framework.ConnectionFigure connection = findConnection(ex, ey, drawing());
            if (connection != null) {
                if (!connection.joinSegments(ex, ey)) {
                    fSplitPoint = connection.splitSegment(ex, ey);
                    fEditedConnection = connection;
                } else {
                    fEditedConnection = null;
                }
            }
        }
    }

    /**
     * Adjust the created connection or split segment.
     */
    public void mouseDrag(java.awt.event.MouseEvent e, int x, int y) {
        java.awt.Point p = new java.awt.Point(e.getX(), e.getY());
        if (fConnection != null) {
            trackConnectors(e, x, y);
            if (fConnectorTarget != null)
                p = CH.ifa.draw.util.Geom.center(fConnectorTarget.displayBox());

            fConnection.endPoint(p.x, p.y);
        } else if (fEditedConnection != null) {
            java.awt.Point pp = new java.awt.Point(x, y);
            fEditedConnection.setPointAt(pp, fSplitPoint);
        }
    }

    /**
     * Connects the figures if the mouse is released over another
     * figure.
     */
    public void mouseUp(java.awt.event.MouseEvent e, int x, int y) {
        CH.ifa.draw.framework.Figure c = null;
        if (fStartConnector != null)
            c = findTarget(e.getX(), e.getY(), drawing());

        if (c != null) {
            fEndConnector = findConnector(e.getX(), e.getY(), c);
            if (fEndConnector != null) {
                fConnection.connectStart(fStartConnector);
                fConnection.connectEnd(fEndConnector);
                fConnection.updateConnection();
            }
        } else if (fConnection != null)
            view().remove(fConnection);

        fConnection = null;
        fStartConnector = fEndConnector = null;
        editor().toolDone();
    }

    public void deactivate() {
        super.deactivate();
        if (fTarget != null)
            fTarget.connectorVisibility(false);

    }

    /**
     * Creates the ConnectionFigure. By default the figure prototype is
     * cloned.
     */
    protected CH.ifa.draw.framework.ConnectionFigure createConnection() {
        return ((CH.ifa.draw.framework.ConnectionFigure) (fPrototype.clone()));
    }

    /**
     * Finds a connectable figure target.
     */
    protected CH.ifa.draw.framework.Figure findSource(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
        return findConnectableFigure(x, y, drawing);
    }

    /**
     * Finds a connectable figure target.
     */
    protected CH.ifa.draw.framework.Figure findTarget(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
        CH.ifa.draw.framework.Figure target = findConnectableFigure(x, y, drawing);
        CH.ifa.draw.framework.Figure start = fStartConnector.owner();
        if (((((target != null) && (fConnection != null)) && target.canConnect()) && (!target.includes(start))) && fConnection.canConnect(start, target))
            return target;

        return null;
    }

    /**
     * Finds an existing connection figure.
     */
    protected CH.ifa.draw.framework.ConnectionFigure findConnection(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
        java.util.Enumeration k = drawing.figuresReverse();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = ((CH.ifa.draw.framework.Figure) (k.nextElement()));
            figure = figure.findFigureInside(x, y);
            if ((figure != null) && (figure instanceof CH.ifa.draw.framework.ConnectionFigure))
                return ((CH.ifa.draw.framework.ConnectionFigure) (figure));

        } 
        return null;
    }

    /**
     * Gets the currently created figure
     */
    protected CH.ifa.draw.framework.ConnectionFigure createdFigure() {
        return fConnection;
    }

    protected void trackConnectors(java.awt.event.MouseEvent e, int x, int y) {
        CH.ifa.draw.framework.Figure c = null;
        if (fStartConnector == null)
            c = findSource(x, y, drawing());
        else
            c = findTarget(x, y, drawing());

        // track the figure containing the mouse
        if (c != fTarget) {
            if (fTarget != null)
                fTarget.connectorVisibility(false);

            fTarget = c;
            if (fTarget != null)
                fTarget.connectorVisibility(true);

        }
        CH.ifa.draw.framework.Connector cc = null;
        if (c != null)
            cc = findConnector(e.getX(), e.getY(), c);

        if (cc != fConnectorTarget)
            fConnectorTarget = cc;

        view().checkDamage();
    }

    private CH.ifa.draw.framework.Connector findConnector(int x, int y, CH.ifa.draw.framework.Figure f) {
        return f.connectorAt(x, y);
    }

    /**
     * Finds a connection start figure.
     */
    protected CH.ifa.draw.framework.Figure findConnectionStart(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
        CH.ifa.draw.framework.Figure target = findConnectableFigure(x, y, drawing);
        if ((target != null) && target.canConnect())
            return target;

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

    protected CH.ifa.draw.framework.Connector getStartConnector() {
        return fStartConnector;
    }

    protected CH.ifa.draw.framework.Connector getEndConnector() {
        return fEndConnector;
    }

    protected CH.ifa.draw.framework.Connector getTarget() {
        return fConnectorTarget;
    }
}