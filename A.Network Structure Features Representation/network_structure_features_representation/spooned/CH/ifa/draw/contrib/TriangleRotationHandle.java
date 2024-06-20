/* Sun Mar  2 19:15:28 1997  Doug Lea  (dl at gee)
Based on RadiusHandle
 */
package CH.ifa.draw.contrib;
/**
 * A Handle to rotate a TriangleFigure
 */
class TriangleRotationHandle extends CH.ifa.draw.standard.AbstractHandle {
    private java.awt.Point fOrigin = null;

    private java.awt.Point fCenter = null;

    public TriangleRotationHandle(CH.ifa.draw.contrib.TriangleFigure owner) {
        super(owner);
    }

    public void invokeStart(int x, int y, CH.ifa.draw.framework.Drawing drawing) {
        fCenter = owner().center();
        fOrigin = getOrigin();
    }

    public void invokeStep(int dx, int dy, CH.ifa.draw.framework.Drawing drawing) {
        double angle = java.lang.Math.atan2((fOrigin.y + dy) - fCenter.y, (fOrigin.x + dx) - fCenter.x);
        ((CH.ifa.draw.contrib.TriangleFigure) (owner())).rotate(angle);
    }

    public void invokeEnd(int dx, int dy, CH.ifa.draw.framework.Drawing drawing) {
        fOrigin = null;
        fCenter = null;
    }

    public java.awt.Point locate() {
        return getOrigin();
    }

    java.awt.Point getOrigin() {
        // find a nice place to put handle
        // almost same code as PolygonScaleHandle
        java.awt.Polygon p = ((CH.ifa.draw.contrib.TriangleFigure) (owner())).polygon();
        java.awt.Point first = new java.awt.Point(p.xpoints[0], p.ypoints[0]);
        java.awt.Point ctr = owner().center();
        double len = CH.ifa.draw.util.Geom.length(first.x, first.y, ctr.x, ctr.y);
        // best we can do?
        if (len == 0)
            return new java.awt.Point(first.x - (CH.ifa.draw.standard.AbstractHandle.HANDLESIZE / 2), first.y + (CH.ifa.draw.standard.AbstractHandle.HANDLESIZE / 2));

        double u = CH.ifa.draw.standard.AbstractHandle.HANDLESIZE / len;
        // best we can do?
        if (u > 1.0)
            return new java.awt.Point(((first.x * 3) + ctr.x) / 4, ((first.y * 3) + ctr.y) / 4);
        else
            return new java.awt.Point(((int) ((first.x * (1.0 - u)) + (ctr.x * u))), ((int) ((first.y * (1.0 - u)) + (ctr.y * u))));

    }

    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.yellow);
        g.fillOval(r.x, r.y, r.width, r.height);
        g.setColor(java.awt.Color.black);
        g.drawOval(r.x, r.y, r.width, r.height);
    }
}