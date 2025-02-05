/* @(#)ShortestDistanceConnector.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A ShortestDistance locates connection points by
 * finding the shortest distance between the start and
 * end of the connection.
 * It doesn't connect to the ares defined by Figure.connectionInsets()
 *
 * @see Figure#connectionInsets
 * @see Connector
 */
public class ShortestDistanceConnector extends CH.ifa.draw.standard.AbstractConnector {
    /* Serialization support. */
    private static final long serialVersionUID = -2273446020593433887L;

    public ShortestDistanceConnector() {
        // only used for Storable implementation
        super();
    }

    public ShortestDistanceConnector(CH.ifa.draw.framework.Figure owner) {
        super(owner);
    }

    public java.awt.Point findStart(CH.ifa.draw.framework.ConnectionFigure connection) {
        return findPoint(connection, true);
    }

    public java.awt.Point findEnd(CH.ifa.draw.framework.ConnectionFigure connection) {
        return findPoint(connection, false);
    }

    protected java.awt.Point findPoint(CH.ifa.draw.framework.ConnectionFigure connection, boolean getStart) {
        CH.ifa.draw.framework.Figure startFigure = connection.start().owner();
        CH.ifa.draw.framework.Figure endFigure = connection.end().owner();
        java.awt.Rectangle r1 = startFigure.displayBox();
        java.awt.Rectangle r2 = endFigure.displayBox();
        java.awt.Insets i1 = startFigure.connectionInsets();
        java.awt.Insets i2 = endFigure.connectionInsets();
        java.awt.Point p1;
        java.awt.Point p2;
        java.awt.Point start = null;
        java.awt.Point end = null;
        java.awt.Point s = null;
        java.awt.Point e = null;
        long len2 = java.lang.Long.MAX_VALUE;
        long l2;
        int x1;// connection points

        int x2;
        int y1;
        int y2;
        int xmin;
        int xmax;
        int ymin;
        int ymax;
        // X-dimension
        // constrain width connection insets
        int r1x;
        int r1width;
        int r2x;
        int r2width;
        int r1y;
        int r1height;
        int r2y;
        int r2height;
        r1x = r1.x + i1.left;
        r1width = ((r1.width - i1.left) - i1.right) - 1;
        r2x = r2.x + i2.left;
        r2width = ((r2.width - i2.left) - i2.right) - 1;
        // find x connection point
        if ((r1x + r1width) < r2x) {
            x1 = r1x + r1width;
            x2 = r2x;
        } else if (r1x > (r2x + r2width)) {
            x1 = r1x;
            x2 = r2x + r2width;
        } else {
            xmax = java.lang.Math.max(r1x, r2x);
            xmin = java.lang.Math.min(r1x + r1width, r2x + r2width);
            x1 = x2 = (xmax + xmin) / 2;
        }
        // Y-Dimension
        // constrain with connection insets
        r1y = r1.y + i1.top;
        r1height = ((r1.height - i1.top) - i1.bottom) - 1;
        r2y = r2.y + i2.top;
        r2height = ((r2.height - i2.top) - i2.bottom) - 1;
        // y connection point
        if ((r1y + r1height) < r2y) {
            y1 = r1y + r1height;
            y2 = r2y;
        } else if (r1y > (r2y + r2height)) {
            y1 = r1y;
            y2 = r2y + r2height;
        } else {
            ymax = java.lang.Math.max(r1y, r2y);
            ymin = java.lang.Math.min(r1y + r1height, r2y + r2height);
            y1 = y2 = (ymax + ymin) / 2;
        }
        // find shortest connection
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0 :
                    // EAST-WEST
                    p1 = CH.ifa.draw.util.Geom.east(r1);
                    p2 = CH.ifa.draw.util.Geom.west(r2);
                    s = new java.awt.Point(p1.x, y1);
                    e = new java.awt.Point(p2.x, y2);
                    break;
                case 1 :
                    // WEST-EAST
                    p1 = CH.ifa.draw.util.Geom.west(r1);
                    p2 = CH.ifa.draw.util.Geom.east(r2);
                    s = new java.awt.Point(p1.x, y1);
                    e = new java.awt.Point(p2.x, y2);
                    break;
                case 2 :
                    // NORTH-SOUTH
                    p1 = CH.ifa.draw.util.Geom.north(r1);
                    p2 = CH.ifa.draw.util.Geom.south(r2);
                    s = new java.awt.Point(x1, p1.y);
                    e = new java.awt.Point(x2, p2.y);
                    break;
                case 3 :
                    // SOUTH-NORTH
                    p1 = CH.ifa.draw.util.Geom.south(r1);
                    p2 = CH.ifa.draw.util.Geom.north(r2);
                    s = new java.awt.Point(x1, p1.y);
                    e = new java.awt.Point(x2, p2.y);
                    break;
            }
            l2 = CH.ifa.draw.util.Geom.length2(s.x, s.y, e.x, e.y);
            if (l2 < len2) {
                start = s;
                end = e;
                len2 = l2;
            }
        }
        if (getStart)
            return start;

        return end;
    }
}