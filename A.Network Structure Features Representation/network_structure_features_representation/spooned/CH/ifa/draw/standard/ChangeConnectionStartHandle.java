/* @(#)ChangeConnectionStartHandle.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Handle to reconnect the
 * start of a connection to another figure.
 */
public class ChangeConnectionStartHandle extends CH.ifa.draw.standard.ChangeConnectionHandle {
    /**
     * Constructs the connection handle for the given start figure.
     */
    public ChangeConnectionStartHandle(CH.ifa.draw.framework.Figure owner) {
        super(owner);
    }

    /**
     * Gets the start figure of a connection.
     */
    protected CH.ifa.draw.framework.Connector target() {
        return fConnection.start();
    }

    /**
     * Disconnects the start figure.
     */
    protected void disconnect() {
        fConnection.disconnectStart();
    }

    /**
     * Sets the start of the connection.
     */
    protected void connect(CH.ifa.draw.framework.Connector c) {
        fConnection.connectStart(c);
    }

    /**
     * Sets the start point of the connection.
     */
    protected void setPoint(int x, int y) {
        fConnection.startPoint(x, y);
    }

    /**
     * Returns the start point of the connection.
     */
    public java.awt.Point locate() {
        return fConnection.startPoint();
    }
}