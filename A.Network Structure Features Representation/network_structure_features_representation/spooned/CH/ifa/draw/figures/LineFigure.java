/* @(#)LineFigure.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A line figure.
 */
public class LineFigure extends CH.ifa.draw.figures.PolyLineFigure {
    /* Serialization support. */
    private static final long serialVersionUID = 511503575249212371L;

    private int lineFigureSerializedDataVersion = 1;

    /**
     * Constructs a LineFigure with both start and end set to Point(0,0).
     */
    public LineFigure() {
        addPoint(0, 0);
        addPoint(0, 0);
    }

    /**
     * Gets a copy of the start point.
     */
    public java.awt.Point startPoint() {
        return pointAt(0);
    }

    /**
     * Gets a copy of the end point.
     */
    public java.awt.Point endPoint() {
        return pointAt(1);
    }

    /**
     * Sets the start point.
     */
    public void startPoint(int x, int y) {
        setPointAt(new java.awt.Point(x, y), 0);
    }

    /**
     * Sets the end point.
     */
    public void endPoint(int x, int y) {
        setPointAt(new java.awt.Point(x, y), 1);
    }

    /**
     * Sets the start and end point.
     */
    public void setPoints(java.awt.Point start, java.awt.Point end) {
        setPointAt(start, 0);
        setPointAt(end, 1);
    }

    public void basicDisplayBox(java.awt.Point origin, java.awt.Point corner) {
        setPoints(origin, corner);
    }
}