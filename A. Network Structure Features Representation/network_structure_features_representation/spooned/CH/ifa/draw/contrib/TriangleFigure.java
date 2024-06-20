/* Hacked together by Doug lea
Tue Feb 25 17:30:58 1997  Doug Lea  (dl at gee)
 */
package CH.ifa.draw.contrib;
/**
 * A triangle with same dimensions as its enclosing rectangle,
 * and apex at any of 8 places
 */
public class TriangleFigure extends CH.ifa.draw.figures.RectangleFigure {
    static double[] rotations = new double[]{ (-java.lang.Math.PI) / 2, (-java.lang.Math.PI) / 4, 0.0, java.lang.Math.PI / 4, java.lang.Math.PI / 2, (java.lang.Math.PI * 3) / 4, java.lang.Math.PI, ((-java.lang.Math.PI) * 3) / 4 };

    protected int fRotation = 0;

    public TriangleFigure() {
        super(new java.awt.Point(0, 0), new java.awt.Point(0, 0));
    }

    public TriangleFigure(java.awt.Point origin, java.awt.Point corner) {
        super(origin, corner);
    }

    public java.util.Vector handles() {
        java.util.Vector h = super.handles();
        h.addElement(new CH.ifa.draw.contrib.TriangleRotationHandle(this));
        return h;
    }

    public void rotate(double angle) {
        willChange();
        // System.out.println("a:"+angle);
        double dist = java.lang.Double.MAX_VALUE;
        int best = 0;
        for (int i = 0; i < CH.ifa.draw.contrib.TriangleFigure.rotations.length; ++i) {
            double d = java.lang.Math.abs(angle - CH.ifa.draw.contrib.TriangleFigure.rotations[i]);
            if (d < dist) {
                dist = d;
                best = i;
            }
        }
        fRotation = best;
        changed();
    }

    /**
     * Return the polygon describing the triangle *
     */
    public java.awt.Polygon polygon() {
        java.awt.Rectangle r = displayBox();
        java.awt.Polygon p = new java.awt.Polygon();
        switch (fRotation) {
            case 0 :
                p.addPoint(r.x + (r.width / 2), r.y);
                p.addPoint(r.x + r.width, r.y + r.height);
                p.addPoint(r.x, r.y + r.height);
                break;
            case 1 :
                p.addPoint(r.x + r.width, r.y);
                p.addPoint(r.x + r.width, r.y + r.height);
                p.addPoint(r.x, r.y);
                break;
            case 2 :
                p.addPoint(r.x + r.width, r.y + (r.height / 2));
                p.addPoint(r.x, r.y + r.height);
                p.addPoint(r.x, r.y);
                break;
            case 3 :
                p.addPoint(r.x + r.width, r.y + r.height);
                p.addPoint(r.x, r.y + r.height);
                p.addPoint(r.x + r.width, r.y);
                break;
            case 4 :
                p.addPoint(r.x + (r.width / 2), r.y + r.height);
                p.addPoint(r.x, r.y);
                p.addPoint(r.x + r.width, r.y);
                break;
            case 5 :
                p.addPoint(r.x, r.y + r.height);
                p.addPoint(r.x, r.y);
                p.addPoint(r.x + r.width, r.y + r.height);
                break;
            case 6 :
                p.addPoint(r.x, r.y + (r.height / 2));
                p.addPoint(r.x + r.width, r.y);
                p.addPoint(r.x + r.width, r.y + r.height);
                break;
            case 7 :
                p.addPoint(r.x, r.y);
                p.addPoint(r.x + r.width, r.y);
                p.addPoint(r.x, r.y + r.height);
                break;
        }
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
        switch (fRotation) {
            case 0 :
                return new java.awt.Insets(r.height, r.width / 2, 0, r.width / 2);
            case 1 :
                return new java.awt.Insets(0, r.width, r.height, 0);
            case 2 :
                return new java.awt.Insets(r.height / 2, 0, r.height / 2, r.width);
            case 3 :
                return new java.awt.Insets(r.height, r.width, 0, 0);
            case 4 :
                return new java.awt.Insets(0, r.width / 2, r.height, r.width / 2);
            case 5 :
                return new java.awt.Insets(r.height, 0, 0, r.width);
            case 6 :
                return new java.awt.Insets(r.height / 2, r.width, r.height / 2, 0);
            case 7 :
                return new java.awt.Insets(0, 0, r.height, r.width);
            default :
                return null;
        }
    }

    public boolean containsPoint(int x, int y) {
        return polygon().contains(x, y);
    }

    public java.awt.Point center() {
        return CH.ifa.draw.contrib.PolygonFigure.center(polygon());
    }

    public java.awt.Point chop(java.awt.Point p) {
        return CH.ifa.draw.contrib.PolygonFigure.chop(polygon(), p);
    }

    public java.lang.Object clone() {
        CH.ifa.draw.contrib.TriangleFigure figure = ((CH.ifa.draw.contrib.TriangleFigure) (super.clone()));
        figure.fRotation = fRotation;
        return figure;
    }

    // -- store / load ----------------------------------------------
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fRotation);
    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        fRotation = dr.readInt();
    }
}