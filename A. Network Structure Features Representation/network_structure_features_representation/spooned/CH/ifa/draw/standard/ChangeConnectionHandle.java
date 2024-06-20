/* @(#)ChangeConnectionHandle.java 5.1 */
package CH.ifa.draw.standard;
/**
 * ChangeConnectionHandle factors the common code for handles
 * that can be used to reconnect connections.
 *
 * @see ChangeConnectionEndHandle
 * @see ChangeConnectionStartHandle
 */
public abstract class ChangeConnectionHandle extends CH.ifa.draw.standard.AbstractHandle {
    protected CH.ifa.draw.framework.Connector fOriginalTarget;

    protected CH.ifa.draw.framework.Figure fTarget;

    protected CH.ifa.draw.framework.ConnectionFigure fConnection;

    protected java.awt.Point fStart;

    /**
     * Initializes the change connection handle.
     */
    protected ChangeConnectionHandle(CH.ifa.draw.framework.Figure owner) {
        super(owner);
        fConnection = ((CH.ifa.draw.framework.ConnectionFigure) (owner()));
        fTarget = null;
    }

    /**
     * Returns the target connector of the change.
     */
    protected abstract CH.ifa.draw.framework.Connector target();

    /**
     * Disconnects the connection.
     */
    protected abstract void disconnect();

    /**
     * Connect the connection with the given figure.
     */
    protected abstract void connect(CH.ifa.draw.framework.Connector c);

    /**
     * Sets the location of the target point.
     */
    protected abstract void setPoint(int x, int y);

    /**
     * Gets the side of the connection that is unaffected by
     * the change.
     */
    protected CH.ifa.draw.framework.Connector source() {
        if (target() == fConnection.start())
            return fConnection.end();

        return fConnection.start();
    }

    /**
     * Disconnects the connection.
     */
    public void invokeStart(int x, int y, CH.ifa.draw.framework.DrawingView view) {
        fOriginalTarget = target();
        fStart = new java.awt.Point(x, y);
        disconnect();
    }

    /**
     * Finds a new target of the connection.
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

        setPoint(p.x, p.y);
    }

    /**
     * Connects the figure to the new target. If there is no
     * new target the connection reverts to its original one.
     */
    public void invokeEnd(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        CH.ifa.draw.framework.Connector target = findConnectionTarget(x, y, view.drawing());
        if (target == null)
            target = fOriginalTarget;

        setPoint(x, y);
        connect(target);
        fConnection.updateConnection();
        if (fTarget != null) {
            fTarget.connectorVisibility(false);
            fTarget = null;
        }
    }

    private CH.ifa.draw.framework.Connector findConnectionTarget(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
        CH.ifa.draw.framework.Figure target = findConnectableFigure(x, y, drawing);
        if (((((target != null) && target.canConnect()) && (target != fOriginalTarget)) && (!target.includes(owner()))) && fConnection.canConnect(source().owner(), target)) {
            return findConnector(x, y, target);
        }
        return null;
    }

    protected CH.ifa.draw.framework.Connector findConnector(int x, int y, CH.ifa.draw.framework.Figure f) {
        return f.connectorAt(x, y);
    }

    /**
     * Draws this handle.
     */
    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.green);
        g.fillRect(r.x, r.y, r.width, r.height);
        g.setColor(java.awt.Color.black);
        g.drawRect(r.x, r.y, r.width, r.height);
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
}