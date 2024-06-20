/* Copyright (c) 1996, 1997 Erich Gamma
All Rights Reserved
 */
package CH.ifa.draw.contrib;
/**
 * A ChopPolygonConnector locates a connection point by
 * chopping the connection at the polygon boundary.
 */
public class ChopPolygonConnector extends CH.ifa.draw.standard.ChopBoxConnector {
    /* Serialization support. */
    private static final long serialVersionUID = -156024908227796826L;

    public ChopPolygonConnector() {
    }

    public ChopPolygonConnector(CH.ifa.draw.framework.Figure owner) {
        super(owner);
    }

    protected java.awt.Point chop(CH.ifa.draw.framework.Figure target, java.awt.Point from) {
        return ((CH.ifa.draw.contrib.PolygonFigure) (target)).chop(from);
    }
}