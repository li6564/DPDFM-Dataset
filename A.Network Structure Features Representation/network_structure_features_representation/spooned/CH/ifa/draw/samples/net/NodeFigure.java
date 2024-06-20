/* @(#)NodeFigure.java 5.1 */
package CH.ifa.draw.samples.net;
public class NodeFigure extends CH.ifa.draw.figures.TextFigure {
    private static final int BORDER = 6;

    private java.util.Vector fConnectors;

    private boolean fConnectorsVisible;

    public NodeFigure() {
        initialize();
        fConnectors = null;
    }

    public java.awt.Rectangle displayBox() {
        java.awt.Rectangle box = super.displayBox();
        int d = CH.ifa.draw.samples.net.NodeFigure.BORDER;
        box.grow(d, d);
        return box;
    }

    public boolean containsPoint(int x, int y) {
        // add slop for connectors
        if (fConnectorsVisible) {
            java.awt.Rectangle r = displayBox();
            int d = CH.ifa.draw.standard.LocatorConnector.SIZE / 2;
            r.grow(d, d);
            return r.contains(x, y);
        }
        return super.containsPoint(x, y);
    }

    private void drawBorder(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(getFrameColor());
        g.drawRect(r.x, r.y, r.width - 1, r.height - 1);
    }

    public void draw(java.awt.Graphics g) {
        super.draw(g);
        drawBorder(g);
        drawConnectors(g);
    }

    public java.util.Vector handles() {
        CH.ifa.draw.framework.ConnectionFigure prototype = new CH.ifa.draw.figures.LineConnection();
        java.util.Vector handles = new java.util.Vector();
        handles.addElement(new CH.ifa.draw.standard.ConnectionHandle(this, CH.ifa.draw.standard.RelativeLocator.east(), prototype));
        handles.addElement(new CH.ifa.draw.standard.ConnectionHandle(this, CH.ifa.draw.standard.RelativeLocator.west(), prototype));
        handles.addElement(new CH.ifa.draw.standard.ConnectionHandle(this, CH.ifa.draw.standard.RelativeLocator.south(), prototype));
        handles.addElement(new CH.ifa.draw.standard.ConnectionHandle(this, CH.ifa.draw.standard.RelativeLocator.north(), prototype));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.southEast()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.southWest()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.northEast()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.northWest()));
        return handles;
    }

    private void drawConnectors(java.awt.Graphics g) {
        if (fConnectorsVisible) {
            java.util.Enumeration e = connectors().elements();
            while (e.hasMoreElements())
                ((CH.ifa.draw.framework.Connector) (e.nextElement())).draw(g);

        }
    }

    /**
     */
    public void connectorVisibility(boolean isVisible) {
        fConnectorsVisible = isVisible;
        invalidate();
    }

    /**
     */
    public CH.ifa.draw.framework.Connector connectorAt(int x, int y) {
        return findConnector(x, y);
    }

    /**
     */
    private java.util.Vector connectors() {
        if (fConnectors == null)
            createConnectors();

        return fConnectors;
    }

    private void createConnectors() {
        fConnectors = new java.util.Vector(4);
        fConnectors.addElement(new CH.ifa.draw.standard.LocatorConnector(this, CH.ifa.draw.standard.RelativeLocator.north()));
        fConnectors.addElement(new CH.ifa.draw.standard.LocatorConnector(this, CH.ifa.draw.standard.RelativeLocator.south()));
        fConnectors.addElement(new CH.ifa.draw.standard.LocatorConnector(this, CH.ifa.draw.standard.RelativeLocator.west()));
        fConnectors.addElement(new CH.ifa.draw.standard.LocatorConnector(this, CH.ifa.draw.standard.RelativeLocator.east()));
    }

    private CH.ifa.draw.framework.Connector findConnector(int x, int y) {
        // return closest connector
        long min = java.lang.Long.MAX_VALUE;
        CH.ifa.draw.framework.Connector closest = null;
        java.util.Enumeration e = connectors().elements();
        while (e.hasMoreElements()) {
            CH.ifa.draw.framework.Connector c = ((CH.ifa.draw.framework.Connector) (e.nextElement()));
            java.awt.Point p2 = CH.ifa.draw.util.Geom.center(c.displayBox());
            long d = CH.ifa.draw.util.Geom.length2(x, y, p2.x, p2.y);
            if (d < min) {
                min = d;
                closest = c;
            }
        } 
        return closest;
    }

    private void initialize() {
        setText("node");
        java.awt.Font fb = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 12);
        setFont(fb);
        createConnectors();
    }
}