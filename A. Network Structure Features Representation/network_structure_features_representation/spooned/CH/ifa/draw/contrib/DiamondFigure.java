/* Hacked together by Doug lea
Tue Feb 25 17:39:44 1997  Doug Lea  (dl at gee)
 */
package CH.ifa.draw.contrib;
/**
 * A diamond with vertices at the midpoints of its enclosing rectangle
 */
/* public Point chop(Point p) {
return PolygonFigure.chop(polygon(), p);
}
 */
public class DiamondFigure extends CH.ifa.draw.figures.RectangleFigure {
    /* public Point chop(Point p) {
    return PolygonFigure.chop(polygon(), p);
    }
     */
    public DiamondFigure() {
        super(new java.awt.Point(0, 0), new java.awt.Point(0, 0));
    }

    public DiamondFigure(java.awt.Point origin, java.awt.Point corner) {
        super(origin, corner);
    }

    /**
     * Return the polygon describing the diamond *
     */
    protected java.awt.Polygon polygon() {
        java.awt.Rectangle r = displayBox();
        java.awt.Polygon p = new java.awt.Polygon();
        p.addPoint(r.x, r.y + (r.height / 2));
        p.addPoint(r.x + (r.width / 2), r.y);
        p.addPoint(r.x + r.width, r.y + (r.height / 2));
        p.addPoint(r.x + (r.width / 2), r.y + r.height);
        return p;
    }

    public void draw(java.awt.Graphics g) {
        java.awt.Polygon p = polygon();
        g.setColor(getFillColor());
        g.fillPolygon(p);
        g.setColor(getFrameColor());
        g.drawPolygon(p);
    }

    public java.awt.Insets connectionInsets() {
        java.awt.Rectangle r = displayBox();
        return new java.awt.Insets(r.height / 2, r.width / 2, r.height / 2, r.width / 2);
    }

    public boolean containsPoint(int x, int y) {
        return polygon().contains(x, y);
    }
}