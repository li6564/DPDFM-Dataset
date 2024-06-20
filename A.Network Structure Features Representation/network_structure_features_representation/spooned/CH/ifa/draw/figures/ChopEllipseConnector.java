/* @(#)ChopEllipseConnector.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A ChopEllipseConnector locates a connection point by
 * chopping the connection at the ellipse defined by the
 * figure's display box.
 */
public class ChopEllipseConnector extends CH.ifa.draw.standard.ChopBoxConnector {
    /* Serialization support. */
    private static final long serialVersionUID = -3165091511154766610L;

    public ChopEllipseConnector() {
    }

    public ChopEllipseConnector(CH.ifa.draw.framework.Figure owner) {
        super(owner);
    }

    protected java.awt.Point chop(CH.ifa.draw.framework.Figure target, java.awt.Point from) {
        java.awt.Rectangle r = target.displayBox();
        double angle = CH.ifa.draw.util.Geom.pointToAngle(r, from);
        return CH.ifa.draw.util.Geom.ovalAngleToPoint(r, angle);
    }
}