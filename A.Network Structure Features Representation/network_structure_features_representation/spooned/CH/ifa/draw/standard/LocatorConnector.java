/* @(#)LocatorConnector.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A LocatorConnector locates connection points with
 * the help of a Locator. It supports the definition
 * of connection points to semantic locations.
 *
 * @see Locator
 * @see Connector
 */
public class LocatorConnector extends CH.ifa.draw.standard.AbstractConnector {
    /**
     * The standard size of the connector. The display box
     * is centered around the located point.
     */
    public static final int SIZE = 8;

    private CH.ifa.draw.framework.Locator fLocator;

    /* Serialization support. */
    private static final long serialVersionUID = 5062833203337604181L;

    private int locatorConnectorSerializedDataVersion = 1;

    public LocatorConnector() {
        // only used for Storable
        fLocator = null;
    }

    public LocatorConnector(CH.ifa.draw.framework.Figure owner, CH.ifa.draw.framework.Locator l) {
        super(owner);
        fLocator = l;
    }

    protected java.awt.Point locate(CH.ifa.draw.framework.ConnectionFigure connection) {
        return fLocator.locate(owner());
    }

    /**
     * Tests if a point is contained in the connector.
     */
    public boolean containsPoint(int x, int y) {
        return displayBox().contains(x, y);
    }

    /**
     * Gets the display box of the connector.
     */
    public java.awt.Rectangle displayBox() {
        java.awt.Point p = fLocator.locate(owner());
        return new java.awt.Rectangle(p.x - (CH.ifa.draw.standard.LocatorConnector.SIZE / 2), p.y - (CH.ifa.draw.standard.LocatorConnector.SIZE / 2), CH.ifa.draw.standard.LocatorConnector.SIZE, CH.ifa.draw.standard.LocatorConnector.SIZE);
    }

    /**
     * Draws this connector.
     */
    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.blue);
        g.fillOval(r.x, r.y, r.width, r.height);
        g.setColor(java.awt.Color.black);
        g.drawOval(r.x, r.y, r.width, r.height);
    }

    /**
     * Stores the arrow tip to a StorableOutput.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeStorable(fLocator);
    }

    /**
     * Reads the arrow tip from a StorableInput.
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        fLocator = ((CH.ifa.draw.framework.Locator) (dr.readStorable()));
    }
}