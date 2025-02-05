/* @(#)ElbowHandle.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A Handle to move an ElbowConnection left/right or up/down.
 */
public class ElbowHandle extends CH.ifa.draw.standard.AbstractHandle {
    private int fSegment;

    private int fLastX;

    private int fLastY;

    // previous mouse position
    public ElbowHandle(CH.ifa.draw.figures.LineConnection owner, int segment) {
        super(owner);
        fSegment = segment;
    }

    public void invokeStart(int x, int y, CH.ifa.draw.framework.DrawingView view) {
        fLastX = x;
        fLastY = y;
    }

    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        CH.ifa.draw.figures.LineConnection line = ownerConnection();
        java.awt.Point p1 = line.pointAt(fSegment);
        java.awt.Point p2 = line.pointAt(fSegment + 1);
        int ddx = x - fLastX;
        int ddy = y - fLastY;
        java.awt.Point np1;
        java.awt.Point np2;
        if (isVertical(p1, p2)) {
            int cx = constrainX(p1.x + ddx);
            np1 = new java.awt.Point(cx, p1.y);
            np2 = new java.awt.Point(cx, p2.y);
        } else {
            int cy = constrainY(p1.y + ddy);
            np1 = new java.awt.Point(p1.x, cy);
            np2 = new java.awt.Point(p2.x, cy);
        }
        line.setPointAt(np1, fSegment);
        line.setPointAt(np2, fSegment + 1);
        fLastX = x;
        fLastY = y;
    }

    private boolean isVertical(java.awt.Point p1, java.awt.Point p2) {
        return p1.x == p2.x;
    }

    public java.awt.Point locate() {
        CH.ifa.draw.figures.LineConnection line = ownerConnection();
        int segment = java.lang.Math.min(fSegment, line.pointCount() - 2);
        java.awt.Point p1 = line.pointAt(segment);
        java.awt.Point p2 = line.pointAt(segment + 1);
        return new java.awt.Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.yellow);
        g.fillOval(r.x, r.y, r.width, r.height);
        g.setColor(java.awt.Color.black);
        g.drawOval(r.x, r.y, r.width, r.height);
    }

    private int constrainX(int x) {
        CH.ifa.draw.figures.LineConnection line = ownerConnection();
        CH.ifa.draw.framework.Figure startFigure = line.start().owner();
        CH.ifa.draw.framework.Figure endFigure = line.end().owner();
        java.awt.Rectangle start = startFigure.displayBox();
        java.awt.Rectangle end = endFigure.displayBox();
        java.awt.Insets i1 = startFigure.connectionInsets();
        java.awt.Insets i2 = endFigure.connectionInsets();
        int r1x;
        int r1width;
        int r2x;
        int r2width;
        r1x = start.x + i1.left;
        r1width = ((start.width - i1.left) - i1.right) - 1;
        r2x = end.x + i2.left;
        r2width = ((end.width - i2.left) - i2.right) - 1;
        if (fSegment == 0)
            x = CH.ifa.draw.util.Geom.range(r1x, r1x + r1width, x);

        if (fSegment == (line.pointCount() - 2))
            x = CH.ifa.draw.util.Geom.range(r2x, r2x + r2width, x);

        return x;
    }

    private int constrainY(int y) {
        CH.ifa.draw.figures.LineConnection line = ownerConnection();
        CH.ifa.draw.framework.Figure startFigure = line.start().owner();
        CH.ifa.draw.framework.Figure endFigure = line.end().owner();
        java.awt.Rectangle start = startFigure.displayBox();
        java.awt.Rectangle end = endFigure.displayBox();
        java.awt.Insets i1 = startFigure.connectionInsets();
        java.awt.Insets i2 = endFigure.connectionInsets();
        int r1y;
        int r1height;
        int r2y;
        int r2height;
        r1y = start.y + i1.top;
        r1height = ((start.height - i1.top) - i1.bottom) - 1;
        r2y = end.y + i2.top;
        r2height = ((end.height - i2.top) - i2.bottom) - 1;
        if (fSegment == 0)
            y = CH.ifa.draw.util.Geom.range(r1y, r1y + r1height, y);

        if (fSegment == (line.pointCount() - 2))
            y = CH.ifa.draw.util.Geom.range(r2y, r2y + r2height, y);

        return y;
    }

    private CH.ifa.draw.figures.LineConnection ownerConnection() {
        return ((CH.ifa.draw.figures.LineConnection) (owner()));
    }
}