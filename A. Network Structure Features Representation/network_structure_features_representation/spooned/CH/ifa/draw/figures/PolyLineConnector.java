/* @(#)PolyLineConnector.java 5.1 */
package CH.ifa.draw.figures;
/**
 * PolyLineConnector finds connection points on a
 * PolyLineFigure.
 *
 * @see PolyLineFigure
 */
public class PolyLineConnector extends CH.ifa.draw.standard.ChopBoxConnector {
    /* Serialization support. */
    private static final long serialVersionUID = 6018435940519102865L;

    public PolyLineConnector() {
        super();
    }

    /**
     * Constructs a connector with the given owner figure.
     */
    public PolyLineConnector(CH.ifa.draw.framework.Figure owner) {
        super(owner);
    }

    protected java.awt.Point chop(CH.ifa.draw.framework.Figure target, java.awt.Point from) {
        CH.ifa.draw.figures.PolyLineFigure p = ((CH.ifa.draw.figures.PolyLineFigure) (owner()));
        // *** based on PolygonFigure's heuristic
        java.awt.Point ctr = p.center();
        int cx = -1;
        int cy = -1;
        long len = java.lang.Long.MAX_VALUE;
        // Try for points along edge
        for (int i = 0; i < (p.pointCount() - 1); i++) {
            java.awt.Point p1 = p.pointAt(i);
            java.awt.Point p2 = p.pointAt(i + 1);
            java.awt.Point chop = CH.ifa.draw.util.Geom.intersect(p1.x, p1.y, p2.x, p2.y, from.x, from.y, ctr.x, ctr.y);
            if (chop != null) {
                long cl = CH.ifa.draw.util.Geom.length2(chop.x, chop.y, from.x, from.y);
                if (cl < len) {
                    len = cl;
                    cx = chop.x;
                    cy = chop.y;
                }
            }
        }
        // if none found, pick closest vertex
        // if (len ==  Long.MAX_VALUE) {
        {
            // try anyway
            for (int i = 0; i < p.pointCount(); i++) {
                java.awt.Point pp = p.pointAt(i);
                long l = CH.ifa.draw.util.Geom.length2(pp.x, pp.y, from.x, from.y);
                if (l < len) {
                    len = l;
                    cx = pp.x;
                    cy = pp.y;
                }
            }
        }
        return new java.awt.Point(cx, cy);
    }
}