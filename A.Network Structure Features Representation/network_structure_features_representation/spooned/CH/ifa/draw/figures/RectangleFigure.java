/* @(#)RectangleFigure.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A rectangle figure.
 */
public class RectangleFigure extends CH.ifa.draw.figures.AttributeFigure {
    private java.awt.Rectangle fDisplayBox;

    /* Serialization support. */
    private static final long serialVersionUID = 184722075881789163L;

    private int rectangleFigureSerializedDataVersion = 1;

    public RectangleFigure() {
        this(new java.awt.Point(0, 0), new java.awt.Point(0, 0));
    }

    public RectangleFigure(java.awt.Point origin, java.awt.Point corner) {
        basicDisplayBox(origin, corner);
    }

    public void basicDisplayBox(java.awt.Point origin, java.awt.Point corner) {
        fDisplayBox = new java.awt.Rectangle(origin);
        fDisplayBox.add(corner);
    }

    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector();
        CH.ifa.draw.standard.BoxHandleKit.addHandles(this, handles);
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
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    public void drawFrame(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.drawRect(r.x, r.y, r.width - 1, r.height - 1);
    }

    // -- store / load ----------------------------------------------
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