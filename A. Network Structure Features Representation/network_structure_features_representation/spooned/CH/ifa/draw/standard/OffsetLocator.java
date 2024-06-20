/* @(#)OffsetLocator.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A locator to offset another Locator.
 *
 * @see Locator
 */
public class OffsetLocator extends CH.ifa.draw.standard.AbstractLocator {
    /* Serialization support. */
    private static final long serialVersionUID = 2679950024611847621L;

    private int offsetLocatorSerializedDataVersion = 1;

    private CH.ifa.draw.framework.Locator fBase;

    private int fOffsetX;

    private int fOffsetY;

    public OffsetLocator() {
        fBase = null;
        fOffsetX = 0;
        fOffsetY = 0;
    }

    public OffsetLocator(CH.ifa.draw.framework.Locator base) {
        this();
        fBase = base;
    }

    public OffsetLocator(CH.ifa.draw.framework.Locator base, int offsetX, int offsetY) {
        this(base);
        fOffsetX = offsetX;
        fOffsetY = offsetY;
    }

    public java.awt.Point locate(CH.ifa.draw.framework.Figure owner) {
        java.awt.Point p = fBase.locate(owner);
        p.x += fOffsetX;
        p.y += fOffsetY;
        return p;
    }

    public void moveBy(int dx, int dy) {
        fOffsetX += dx;
        fOffsetY += dy;
    }

    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fOffsetX);
        dw.writeInt(fOffsetY);
        dw.writeStorable(fBase);
    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        fOffsetX = dr.readInt();
        fOffsetY = dr.readInt();
        fBase = ((CH.ifa.draw.framework.Locator) (dr.readStorable()));
    }
}