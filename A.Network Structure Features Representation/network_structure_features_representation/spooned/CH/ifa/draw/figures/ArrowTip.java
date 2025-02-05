/* @(#)ArrowTip.java 5.1 */
package CH.ifa.draw.figures;
/**
 * An arrow tip line decoration.
 *
 * @see PolyLineFigure
 */
public class ArrowTip implements CH.ifa.draw.figures.LineDecoration {
    private double fAngle;

    // pointiness of arrow
    private double fOuterRadius;

    private double fInnerRadius;

    /* Serialization support. */
    private static final long serialVersionUID = -3459171428373823638L;

    private int arrowTipSerializedDataVersion = 1;

    public ArrowTip() {
        fAngle = 0.4;// 0.35;

        fOuterRadius = 8;// 15;

        fInnerRadius = 8;// 12;

    }

    /**
     * Constructs an arrow tip with the given angle and radius.
     */
    public ArrowTip(double angle, double outerRadius, double innerRadius) {
        fAngle = angle;
        fOuterRadius = outerRadius;
        fInnerRadius = innerRadius;
    }

    /**
     * Draws the arrow tip in the direction specified by the given two
     * points..
     */
    public void draw(java.awt.Graphics g, int x1, int y1, int x2, int y2) {
        // TBD: reuse the Polygon object
        java.awt.Polygon p = outline(x1, y1, x2, y2);
        g.fillPolygon(p.xpoints, p.ypoints, p.npoints);
    }

    /**
     * Calculates the outline of an arrow tip.
     */
    public java.awt.Polygon outline(int x1, int y1, int x2, int y2) {
        double dir = (java.lang.Math.PI / 2) - java.lang.Math.atan2(x2 - x1, y1 - y2);
        return outline(x1, y1, dir);
    }

    private java.awt.Polygon outline(int x, int y, double direction) {
        java.awt.Polygon shape = new java.awt.Polygon();
        shape.addPoint(x, y);
        addPointRelative(shape, x, y, fOuterRadius, direction - fAngle);
        addPointRelative(shape, x, y, fInnerRadius, direction);
        addPointRelative(shape, x, y, fOuterRadius, direction + fAngle);
        shape.addPoint(x, y);// Closing the polygon (TEG 97-04-23)

        return shape;
    }

    private void addPointRelative(java.awt.Polygon shape, int x, int y, double radius, double angle) {
        shape.addPoint(x + ((int) (radius * java.lang.Math.cos(angle))), y - ((int) (radius * java.lang.Math.sin(angle))));
    }

    /**
     * Stores the arrow tip to a StorableOutput.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
    }

    /**
     * Reads the arrow tip from a StorableInput.
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
    }
}