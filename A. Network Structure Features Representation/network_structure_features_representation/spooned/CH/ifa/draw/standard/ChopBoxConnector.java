/* @(#)ChopBoxConnector.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A ChopBoxConnector locates connection points by
 * choping the connection between the centers of the
 * two figures at the display box.
 *
 * @see Connector
 */
public class ChopBoxConnector extends CH.ifa.draw.standard.AbstractConnector {
    /* Serialization support. */
    private static final long serialVersionUID = -1461450322712345462L;

    public ChopBoxConnector() {
        // only used for Storable implementation
    }

    public ChopBoxConnector(CH.ifa.draw.framework.Figure owner) {
        super(owner);
    }

    public java.awt.Point findStart(CH.ifa.draw.framework.ConnectionFigure connection) {
        CH.ifa.draw.framework.Figure startFigure = connection.start().owner();
        java.awt.Rectangle r2 = connection.end().displayBox();
        java.awt.Point r2c = null;
        if (connection.pointCount() == 2)
            r2c = new java.awt.Point(r2.x + (r2.width / 2), r2.y + (r2.height / 2));
        else
            r2c = connection.pointAt(1);

        return chop(startFigure, r2c);
    }

    public java.awt.Point findEnd(CH.ifa.draw.framework.ConnectionFigure connection) {
        CH.ifa.draw.framework.Figure endFigure = connection.end().owner();
        java.awt.Rectangle r1 = connection.start().displayBox();
        java.awt.Point r1c = null;
        if (connection.pointCount() == 2)
            r1c = new java.awt.Point(r1.x + (r1.width / 2), r1.y + (r1.height / 2));
        else
            r1c = connection.pointAt(connection.pointCount() - 2);

        return chop(endFigure, r1c);
    }

    protected java.awt.Point chop(CH.ifa.draw.framework.Figure target, java.awt.Point from) {
        java.awt.Rectangle r = target.displayBox();
        return CH.ifa.draw.util.Geom.angleToPoint(r, CH.ifa.draw.util.Geom.pointToAngle(r, from));
    }
}