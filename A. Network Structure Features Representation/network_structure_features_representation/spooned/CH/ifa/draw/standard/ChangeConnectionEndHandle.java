/* @(#)ChangeConnectionEndHandle.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A handle to reconnect the end point of
 * a connection to another figure.
 */
public class ChangeConnectionEndHandle extends CH.ifa.draw.standard.ChangeConnectionHandle {
    /**
     * Constructs the connection handle.
     */
    public ChangeConnectionEndHandle(CH.ifa.draw.framework.Figure owner) {
        super(owner);
    }

    /**
     * Gets the end figure of a connection.
     */
    protected CH.ifa.draw.framework.Connector target() {
        return fConnection.end();
    }

    /**
     * Disconnects the end figure.
     */
    protected void disconnect() {
        fConnection.disconnectEnd();
    }

    /**
     * Sets the end of the connection.
     */
    protected void connect(CH.ifa.draw.framework.Connector c) {
        fConnection.connectEnd(c);
    }

    /**
     * Sets the end point of the connection.
     */
    protected void setPoint(int x, int y) {
        fConnection.endPoint(x, y);
    }

    /**
     * Returns the end point of the connection.
     */
    public java.awt.Point locate() {
        return fConnection.endPoint();
    }
}