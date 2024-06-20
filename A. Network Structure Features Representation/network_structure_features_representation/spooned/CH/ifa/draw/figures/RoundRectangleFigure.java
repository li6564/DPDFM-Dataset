/* @(#)RoundRectangleFigure.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A round rectangle figure.
 *
 * @see RadiusHandle
 */
public class RoundRectangleFigure extends CH.ifa.draw.figures.AttributeFigure {
    private java.awt.Rectangle fDisplayBox;

    private int fArcWidth;

    private int fArcHeight;

    private static final int DEFAULT_ARC = 8;

    /* Serialization support. */
    private static final long serialVersionUID = 7907900248924036885L;

    private int roundRectangleSerializedDataVersion = 1;

    public RoundRectangleFigure() {
        this(new java.awt.Point(0, 0), new java.awt.Point(0, 0));
        fArcWidth = fArcHeight = CH.ifa.draw.figures.RoundRectangleFigure.DEFAULT_ARC;
    }

    public RoundRectangleFigure(java.awt.Point origin, java.awt.Point corner) {
        basicDisplayBox(origin, corner);
        fArcWidth = fArcHeight = CH.ifa.draw.figures.RoundRectangleFigure.DEFAULT_ARC;
    }

    public void basicDisplayBox(java.awt.Point origin, java.awt.Point corner) {
        fDisplayBox = new java.awt.Rectangle(origin);
        fDisplayBox.add(corner);
    }

    /**
     * Sets the arc's witdh and height.
     */
    public void setArc(int width, int height) {
        willChange();
        fArcWidth = width;
        fArcHeight = height;
        changed();
    }

    /**
     * Gets the arc's width and height.
     */
    public java.awt.Point getArc() {
        return new java.awt.Point(fArcWidth, fArcHeight);
    }

    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector();
        CH.ifa.draw.standard.BoxHandleKit.addHandles(this, handles);
        handles.addElement(new CH.ifa.draw.figures.RadiusHandle(this));
        return handles;
    }

    public java.awt.Rectangle displayBox() {
        return new java.awt.Rectangle(fDisplayBox.x, fDisplayBox.y, fDisplayBox.width, fDisplayBox.height);
    }

    protected void basicMoveBy(int x, int y) {
        fDisplayBox.translate(x, y);
    }

    public void drawBackground(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.fillRoundRect(r.x, r.y, r.width, r.height, fArcWidth, fArcHeight);
    }

    public void drawFrame(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.drawRoundRect(r.x, r.y, r.width - 1, r.height - 1, fArcWidth, fArcHeight);
    }

    public java.awt.Insets connectionInsets() {
        return new java.awt.Insets(fArcHeight / 2, fArcWidth / 2, fArcHeight / 2, fArcWidth / 2);
    }

    public CH.ifa.draw.framework.Connector connectorAt(int x, int y) {
        return new CH.ifa.draw.figures.ShortestDistanceConnector(this);// just for demo purposes

    }

    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
        dw.writeInt(fArcWidth);
        dw.writeInt(fArcHeight);
    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        fDisplayBox = new java.awt.Rectangle(dr.readInt(), dr.readInt(), dr.readInt(), dr.readInt());
        fArcWidth = dr.readInt();
        fArcHeight = dr.readInt();
    }
}