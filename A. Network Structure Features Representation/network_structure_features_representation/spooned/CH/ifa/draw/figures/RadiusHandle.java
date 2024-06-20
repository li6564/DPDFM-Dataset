/* @(#)RadiusHandle.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A Handle to manipulate the radius of a round corner rectangle.
 */
class RadiusHandle extends CH.ifa.draw.standard.AbstractHandle {
    private java.awt.Point fRadius;

    private CH.ifa.draw.figures.RoundRectangleFigure fOwner;

    private static final int OFFSET = 4;

    public RadiusHandle(CH.ifa.draw.figures.RoundRectangleFigure owner) {
        super(owner);
        fOwner = owner;
    }

    public void invokeStart(int x, int y, CH.ifa.draw.framework.DrawingView view) {
        fRadius = fOwner.getArc();
        fRadius.x = fRadius.x / 2;
        fRadius.y = fRadius.y / 2;
    }

    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        int dx = x - anchorX;
        int dy = y - anchorY;
        java.awt.Rectangle r = fOwner.displayBox();
        int rx = CH.ifa.draw.util.Geom.range(0, r.width, 2 * (fRadius.x + dx));
        int ry = CH.ifa.draw.util.Geom.range(0, r.height, 2 * (fRadius.y + dy));
        fOwner.setArc(rx, ry);
    }

    public java.awt.Point locate() {
        java.awt.Point radius = fOwner.getArc();
        java.awt.Rectangle r = fOwner.displayBox();
        return new java.awt.Point((r.x + (radius.x / 2)) + CH.ifa.draw.figures.RadiusHandle.OFFSET, (r.y + (radius.y / 2)) + CH.ifa.draw.figures.RadiusHandle.OFFSET);
    }

    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.yellow);
        g.fillOval(r.x, r.y, r.width, r.height);
        g.setColor(java.awt.Color.black);
        g.drawOval(r.x, r.y, r.width, r.height);
    }
}