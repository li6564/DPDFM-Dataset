/* @(#)AbstractConnector.java 5.1 */
package CH.ifa.draw.standard;
/**
 * AbstractConnector provides default implementation for
 * the Connector interface.
 *
 * @see Connector
 */
public abstract class AbstractConnector implements CH.ifa.draw.framework.Connector {
    /**
     * the owner of the connector
     */
    private CH.ifa.draw.framework.Figure fOwner;

    /* Serialization support. */
    private static final long serialVersionUID = -5170007865562687545L;

    private int abstractConnectorSerializedDataVersion = 1;

    /**
     * Constructs a connector that has no owner. It is only
     * used internally to resurrect a connectors from a
     * StorableOutput. It should never be called directly.
     */
    public AbstractConnector() {
        fOwner = null;
    }

    /**
     * Constructs a connector with the given owner figure.
     */
    public AbstractConnector(CH.ifa.draw.framework.Figure owner) {
        fOwner = owner;
    }

    /**
     * Gets the connector's owner.
     */
    public CH.ifa.draw.framework.Figure owner() {
        return fOwner;
    }

    public java.awt.Point findStart(CH.ifa.draw.framework.ConnectionFigure connection) {
        return findPoint(connection);
    }

    public java.awt.Point findEnd(CH.ifa.draw.framework.ConnectionFigure connection) {
        return findPoint(connection);
    }

    /**
     * Gets the connection point. Override when the connector
     * does not need to distinguish between the start and end
     * point of a connection.
     */
    protected java.awt.Point findPoint(CH.ifa.draw.framework.ConnectionFigure connection) {
        return CH.ifa.draw.util.Geom.center(displayBox());
    }

    /**
     * Gets the display box of the connector.
     */
    public java.awt.Rectangle displayBox() {
        return owner().displayBox();
    }

    /**
     * Tests if a point is contained in the connector.
     */
    public boolean containsPoint(int x, int y) {
        return owner().containsPoint(x, y);
    }

    /**
     * Draws this connector. By default connectors are invisible.
     */
    public void draw(java.awt.Graphics g) {
        // invisible by default
    }

    /**
     * Stores the connector and its owner to a StorableOutput.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        dw.writeStorable(fOwner);
    }

    /**
     * Reads the connector and its owner from a StorableInput.
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        fOwner = ((CH.ifa.draw.framework.Figure) (dr.readStorable()));
    }
}