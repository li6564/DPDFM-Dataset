/* @(#)ImageFigure.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A Figure that shows an Image.
 * Images shown by an image figure are shared by using the Iconkit.
 *
 * @see Iconkit
 */
public class ImageFigure extends CH.ifa.draw.figures.AttributeFigure implements java.awt.image.ImageObserver {
    private java.lang.String fFileName;

    private transient java.awt.Image fImage;

    private java.awt.Rectangle fDisplayBox;

    /* Serialization support. */
    private static final long serialVersionUID = 148012030121282439L;

    private int imageFigureSerializedDataVersion = 1;

    public ImageFigure() {
        fFileName = null;
        fImage = null;
        fDisplayBox = null;
    }

    public ImageFigure(java.awt.Image image, java.lang.String fileName, java.awt.Point origin) {
        fFileName = fileName;
        fImage = image;
        fDisplayBox = new java.awt.Rectangle(origin.x, origin.y, 0, 0);
        fDisplayBox.width = fImage.getWidth(this);
        fDisplayBox.height = fImage.getHeight(this);
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

    public void draw(java.awt.Graphics g) {
        if (fImage == null)
            fImage = CH.ifa.draw.util.Iconkit.instance().getImage(fFileName);

        if (fImage != null)
            g.drawImage(fImage, fDisplayBox.x, fDisplayBox.y, fDisplayBox.width, fDisplayBox.height, this);
        else
            drawGhost(g);

    }

    private void drawGhost(java.awt.Graphics g) {
        g.setColor(java.awt.Color.gray);
        g.fillRect(fDisplayBox.x, fDisplayBox.y, fDisplayBox.width, fDisplayBox.height);
    }

    /**
     * Handles asynchroneous image updates.
     */
    public boolean imageUpdate(java.awt.Image img, int flags, int x, int y, int w, int h) {
        if ((flags & (java.awt.image.ImageObserver.FRAMEBITS | java.awt.image.ImageObserver.ALLBITS)) != 0) {
            invalidate();
            if (listener() != null)
                listener().figureRequestUpdate(new CH.ifa.draw.framework.FigureChangeEvent(this));

        }
        return (flags & (java.awt.image.ImageObserver.ALLBITS | java.awt.image.ImageObserver.ABORT)) == 0;
    }

    /**
     * Writes the ImageFigure to a StorableOutput. Only a reference to the
     * image, that is its pathname is saved.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
        dw.writeString(fFileName);
    }

    /**
     * Reads the ImageFigure from a StorableInput. It registers the
     * referenced figure to be loaded from the Iconkit.
     *
     * @see Iconkit#registerImage
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        fDisplayBox = new java.awt.Rectangle(dr.readInt(), dr.readInt(), dr.readInt(), dr.readInt());
        fFileName = dr.readString();
        CH.ifa.draw.util.Iconkit.instance().registerImage(fFileName);
    }

    private void readObject(java.io.ObjectInputStream s) throws java.lang.ClassNotFoundException, java.io.IOException {
        s.defaultReadObject();
        CH.ifa.draw.util.Iconkit.instance().registerImage(fFileName);
        fImage = null;
    }
}