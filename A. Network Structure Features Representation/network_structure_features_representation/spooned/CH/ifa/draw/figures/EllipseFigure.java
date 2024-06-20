/* @(#)EllipseFigure.java 5.1 */
package CH.ifa.draw.figures;
/**
 * An ellipse figure.
 */
public class EllipseFigure extends CH.ifa.draw.figures.AttributeFigure {
    private java.awt.Rectangle fDisplayBox;

    /* Serialization support. */
    private static final long serialVersionUID = -6856203289355118951L;

    private int ellipseFigureSerializedDataVersion = 1;

    public EllipseFigure() {
        this(new java.awt.Point(0, 0), new java.awt.Point(0, 0));
    }

    public EllipseFigure(java.awt.Point origin, java.awt.Point corner) {
        basicDisplayBox(origin, corner);
    }

    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector();
        CH.ifa.draw.standard.BoxHandleKit.addHandles(this, handles);
        return handles;
    }

    public void basicDisplayBox(java.awt.Point origin, java.awt.Point corner) {
        fDisplayBox = new java.awt.Rectangle(origin);
        fDisplayBox.add(corner);
    }

    public java.awt.Rectangle displayBox() {
        return new java.awt.Rectangle(fDisplayBox.x, fDisplayBox.y, fDisplayBox.width, fDisplayBox.height);
    }

    protected void basicMoveBy(int x, int y) {
        fDisplayBox.translate(x, y);
    }

    public void drawBackground(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.fillOval(r.x, r.y, r.width, r.height);
    }

    public void drawFrame(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.drawOval(r.x, r.y, r.width - 1, r.height - 1);
    }

    public java.awt.Insets connectionInsets() {
        java.awt.Rectangle r = fDisplayBox;
        int cx = r.width / 2;
        int cy = r.height / 2;
        return new java.awt.Insets(cy, cx, cy, cx);
    }

    public CH.ifa.draw.framework.Connector connectorAt(int x, int y) {
        return new CH.ifa.draw.figures.ChopEllipseConnector(this);
    }

    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        fDisplayBox = new java.awt.Rectangle(dr.readInt(), dr.readInt(), dr.readInt(), dr.readInt());
    }
}