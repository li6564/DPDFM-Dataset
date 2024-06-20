/* @(#)ElbowConnection.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A LineConnection that constrains a connection to
 * orthogonal lines.
 */
public class ElbowConnection extends CH.ifa.draw.figures.LineConnection {
    /* Serialization support. */
    private static final long serialVersionUID = 2193968743082078559L;

    private int elbowConnectionSerializedDataVersion = 1;

    public ElbowConnection() {
        super();
    }

    public void updateConnection() {
        super.updateConnection();
        updatePoints();
    }

    public void layoutConnection() {
    }

    /**
     * Gets the handles of the figure.
     */
    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector(fPoints.size() * 2);
        handles.addElement(new CH.ifa.draw.standard.ChangeConnectionStartHandle(this));
        for (int i = 1; i < (fPoints.size() - 1); i++)
            handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.figures.PolyLineFigure.locator(i)));

        handles.addElement(new CH.ifa.draw.standard.ChangeConnectionEndHandle(this));
        for (int i = 0; i < (fPoints.size() - 1); i++)
            handles.addElement(new CH.ifa.draw.figures.ElbowHandle(this, i));

        return handles;
    }

    public CH.ifa.draw.framework.Locator connectedTextLocator(CH.ifa.draw.framework.Figure f) {
        return new CH.ifa.draw.figures.ElbowTextLocator();
    }

    protected void updatePoints() {
        willChange();
        java.awt.Point start = startPoint();
        java.awt.Point end = endPoint();
        fPoints.removeAllElements();
        fPoints.addElement(start);
        if ((start.x == end.x) || (start.y == end.y)) {
            fPoints.addElement(end);
        } else {
            java.awt.Rectangle r1 = start().owner().displayBox();
            java.awt.Rectangle r2 = end().owner().displayBox();
            int x1;
            int y1;
            int x2;
            int y2;
            int dir = CH.ifa.draw.util.Geom.direction(r1.x + (r1.width / 2), r1.y + (r1.height / 2), r2.x + (r2.width / 2), r2.y + (r2.height / 2));
            if ((dir == CH.ifa.draw.util.Geom.NORTH) || (dir == CH.ifa.draw.util.Geom.SOUTH)) {
                fPoints.addElement(new java.awt.Point(start.x, (start.y + end.y) / 2));
                fPoints.addElement(new java.awt.Point(end.x, (start.y + end.y) / 2));
            } else {
                fPoints.addElement(new java.awt.Point((start.x + end.x) / 2, start.y));
                fPoints.addElement(new java.awt.Point((start.x + end.x) / 2, end.y));
            }
            fPoints.addElement(end);
        }
        changed();
    }
}