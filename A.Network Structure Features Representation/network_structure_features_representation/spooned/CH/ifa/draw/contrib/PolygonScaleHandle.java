/* Sat Mar  1 09:06:09 1997  Doug Lea  (dl at gee)
Based on RadiusHandle
 */
package CH.ifa.draw.contrib;
/**
 * A Handle to scale and rotate a PolygonFigure
 */
class PolygonScaleHandle extends CH.ifa.draw.standard.AbstractHandle {
    private java.awt.Point fOrigin = null;

    private java.awt.Point fCurrent = null;

    private java.awt.Polygon fOrigPoly = null;

    public PolygonScaleHandle(CH.ifa.draw.contrib.PolygonFigure owner) {
        super(owner);
    }

    public void invokeStart(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
        fOrigPoly = ((CH.ifa.draw.contrib.PolygonFigure) (owner())).getPolygon();
        fOrigin = getOrigin();
        fCurrent = new java.awt.Point(fOrigin.x, fOrigin.y);
    }

    public void invokeStep(int dx, int dy, CH.ifa.draw.framework.Drawing drawing) {
        fCurrent = new java.awt.Point(fOrigin.x + dx, fOrigin.y + dy);
        ((CH.ifa.draw.contrib.PolygonFigure) (owner())).scaleRotate(fOrigin, fOrigPoly, fCurrent);
    }

    public void invokeEnd(int dx, int dy, CH.ifa.draw.framework.Drawing drawing) {
        fOrigPoly = null;
        fOrigin = null;
        fCurrent = null;
    }

    public java.awt.Point locate() {
        if (fCurrent != null)
            return fCurrent;
        else
            return getOrigin();

    }

    java.awt.Point getOrigin() {
        // find a nice place to put handle
        // Need to pick a place that will not overlap with point handle
        // and is internal to polygon
        // Try for one HANDLESIZE step away from outermost toward center
        java.awt.Point outer = ((CH.ifa.draw.contrib.PolygonFigure) (owner())).outermostPoint();
        java.awt.Point ctr = ((CH.ifa.draw.contrib.PolygonFigure) (owner())).center();
        double len = CH.ifa.draw.util.Geom.length(outer.x, outer.y, ctr.x, ctr.y);
        // best we can do?
        if (len == 0)
            return new java.awt.Point(outer.x - (CH.ifa.draw.standard.AbstractHandle.HANDLESIZE / 2), outer.y + (CH.ifa.draw.standard.AbstractHandle.HANDLESIZE / 2));

        double u = CH.ifa.draw.standard.AbstractHandle.HANDLESIZE / len;
        // best we can do?
        if (u > 1.0)
            return new java.awt.Point(((outer.x * 3) + ctr.x) / 4, ((outer.y * 3) + ctr.y) / 4);
        else
            return new java.awt.Point(((int) ((outer.x * (1.0 - u)) + (ctr.x * u))), ((int) ((outer.y * (1.0 - u)) + (ctr.y * u))));

    }

    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.yellow);
        g.fillOval(r.x, r.y, r.width, r.height);
        g.setColor(java.awt.Color.black);
        g.drawOval(r.x, r.y, r.width, r.height);
        /* for debugging ...
        Point ctr = ((PolygonFigure)(owner())).center();
        g.setColor(Color.blue);
        g.fillOval(ctr.x, ctr.y, r.width, r.height);

        g.setColor(Color.black);
        g.drawOval(ctr.x, ctr.y, r.width, r.height);
         */
    }
}