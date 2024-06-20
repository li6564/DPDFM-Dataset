/* @(#)Geom.java 5.1 */
package CH.ifa.draw.util;
/**
 * Some geometric utilities.
 */
public class Geom {
    // never instantiated
    private Geom() {
    }

    /**
     * Tests if a point is on a line.
     */
    public static boolean lineContainsPoint(int x1, int y1, int x2, int y2, int px, int py) {
        java.awt.Rectangle r = new java.awt.Rectangle(new java.awt.Point(x1, y1));
        r.add(x2, y2);
        r.grow(2, 2);
        if (!r.contains(px, py))
            return false;

        double a;
        double b;
        double x;
        double y;
        if (x1 == x2)
            return java.lang.Math.abs(px - x1) < 3;

        if (y1 == y2)
            return java.lang.Math.abs(py - y1) < 3;

        a = ((double) (y1 - y2)) / ((double) (x1 - x2));
        b = ((double) (y1)) - (a * ((double) (x1)));
        x = (py - b) / a;
        y = (a * px) + b;
        return java.lang.Math.min(java.lang.Math.abs(x - px), java.lang.Math.abs(y - py)) < 4;
    }

    // never instantiated
    public static final int NORTH = 1;

    public static final int SOUTH = 2;

    public static final int WEST = 3;

    public static final int EAST = 4;

    /**
     * Returns the direction NORTH, SOUTH, WEST, EAST from
     * one point to another one.
     */
    public static int direction(int x1, int y1, int x2, int y2) {
        int direction = 0;
        int vx = x2 - x1;
        int vy = y2 - y1;
        if ((vy < vx) && (vx > (-vy)))
            direction = CH.ifa.draw.util.Geom.EAST;
        else if ((vy > vx) && (vy > (-vx)))
            direction = CH.ifa.draw.util.Geom.NORTH;
        else if ((vx < vy) && (vx < (-vy)))
            direction = CH.ifa.draw.util.Geom.WEST;
        else
            direction = CH.ifa.draw.util.Geom.SOUTH;

        return direction;
    }

    public static java.awt.Point south(java.awt.Rectangle r) {
        return new java.awt.Point(r.x + (r.width / 2), r.y + r.height);
    }

    public static java.awt.Point center(java.awt.Rectangle r) {
        return new java.awt.Point(r.x + (r.width / 2), r.y + (r.height / 2));
    }

    public static java.awt.Point west(java.awt.Rectangle r) {
        return new java.awt.Point(r.x, r.y + (r.height / 2));
    }

    public static java.awt.Point east(java.awt.Rectangle r) {
        return new java.awt.Point(r.x + r.width, r.y + (r.height / 2));
    }

    public static java.awt.Point north(java.awt.Rectangle r) {
        return new java.awt.Point(r.x + (r.width / 2), r.y);
    }

    /**
     * Constains a value to the given range.
     *
     * @return the constrained value
     */
    public static int range(int min, int max, int value) {
        if (value < min)
            value = min;

        if (value > max)
            value = max;

        return value;
    }

    /**
     * Gets the square distance between two points.
     */
    public static long length2(int x1, int y1, int x2, int y2) {
        return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1));
    }

    /**
     * Gets the distance between to points
     */
    public static long length(int x1, int y1, int x2, int y2) {
        return ((long) (java.lang.Math.sqrt(CH.ifa.draw.util.Geom.length2(x1, y1, x2, y2))));
    }

    /**
     * Gets the angle of a point relative to a rectangle.
     */
    public static double pointToAngle(java.awt.Rectangle r, java.awt.Point p) {
        int px = p.x - (r.x + (r.width / 2));
        int py = p.y - (r.y + (r.height / 2));
        return java.lang.Math.atan2(py * r.width, px * r.height);
    }

    /**
     * Gets the point on a rectangle that corresponds to the given angle.
     */
    public static java.awt.Point angleToPoint(java.awt.Rectangle r, double angle) {
        double si = java.lang.Math.sin(angle);
        double co = java.lang.Math.cos(angle);
        double e = 1.0E-4;
        int x = 0;
        int y = 0;
        if (java.lang.Math.abs(si) > e) {
            x = ((int) (((1.0 + (co / java.lang.Math.abs(si))) / 2.0) * r.width));
            x = CH.ifa.draw.util.Geom.range(0, r.width, x);
        } else if (co >= 0.0)
            x = r.width;

        if (java.lang.Math.abs(co) > e) {
            y = ((int) (((1.0 + (si / java.lang.Math.abs(co))) / 2.0) * r.height));
            y = CH.ifa.draw.util.Geom.range(0, r.height, y);
        } else if (si >= 0.0)
            y = r.height;

        return new java.awt.Point(r.x + x, r.y + y);
    }

    /**
     * Converts a polar to a point
     */
    public static java.awt.Point polarToPoint(double angle, double fx, double fy) {
        double si = java.lang.Math.sin(angle);
        double co = java.lang.Math.cos(angle);
        return new java.awt.Point(((int) ((fx * co) + 0.5)), ((int) ((fy * si) + 0.5)));
    }

    /**
     * Gets the point on an oval that corresponds to the given angle.
     */
    public static java.awt.Point ovalAngleToPoint(java.awt.Rectangle r, double angle) {
        java.awt.Point center = CH.ifa.draw.util.Geom.center(r);
        java.awt.Point p = CH.ifa.draw.util.Geom.polarToPoint(angle, r.width / 2, r.height / 2);
        return new java.awt.Point(center.x + p.x, center.y + p.y);
    }

    /**
     * Standard line intersection algorithm
     * Return the point of intersection if it exists, else null
     */
    // from Doug Lea's PolygonFigure
    public static java.awt.Point intersect(int xa, // line 1 point 1 x
    int ya, // line 1 point 1 y
    int xb, // line 1 point 2 x
    int yb, // line 1 point 2 y
    int xc, // line 2 point 1 x
    int yc, // line 2 point 1 y
    int xd, // line 2 point 2 x
    int yd) {
        // line 2 point 2 y
        // source: http://vision.dai.ed.ac.uk/andrewfg/c-g-a-faq.html
        // eq: for lines AB and CD
        // (YA-YC)(XD-XC)-(XA-XC)(YD-YC)
        // r = -----------------------------  (eqn 1)
        // (XB-XA)(YD-YC)-(YB-YA)(XD-XC)
        // 
        // (YA-YC)(XB-XA)-(XA-XC)(YB-YA)
        // s = -----------------------------  (eqn 2)
        // (XB-XA)(YD-YC)-(YB-YA)(XD-XC)
        // XI = XA + r(XB-XA)
        // YI = YA + r(YB-YA)
        double denom = ((xb - xa) * (yd - yc)) - ((yb - ya) * (xd - xc));
        double rnum = ((ya - yc) * (xd - xc)) - ((xa - xc) * (yd - yc));
        if (denom == 0.0) {
            // parallel
            if (rnum == 0.0) {
                // coincident; pick one end of first line
                if (((xa < xb) && ((xb < xc) || (xb < xd))) || ((xa > xb) && ((xb > xc) || (xb > xd))))
                    return new java.awt.Point(xb, yb);
                else
                    return new java.awt.Point(xa, ya);

            } else
                return null;

        }
        double r = rnum / denom;
        double snum = ((ya - yc) * (xb - xa)) - ((xa - xc) * (yb - ya));
        double s = snum / denom;
        if ((((0.0 <= r) && (r <= 1.0)) && (0.0 <= s)) && (s <= 1.0)) {
            int px = ((int) (xa + ((xb - xa) * r)));
            int py = ((int) (ya + ((yb - ya) * r)));
            return new java.awt.Point(px, py);
        } else
            return null;

    }
}