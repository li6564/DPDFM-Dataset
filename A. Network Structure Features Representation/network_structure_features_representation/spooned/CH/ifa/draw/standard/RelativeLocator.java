/* @(#)RelativeLocator.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A locator that specfies a point that is relative to the bounds
 * of a figure.
 *
 * @see Locator
 */
public class RelativeLocator extends CH.ifa.draw.standard.AbstractLocator {
    /* Serialization support. */
    private static final long serialVersionUID = 2619148876087898602L;

    private int relativeLocatorSerializedDataVersion = 1;

    double fRelativeX;

    double fRelativeY;

    public RelativeLocator() {
        fRelativeX = 0.0;
        fRelativeY = 0.0;
    }

    public RelativeLocator(double relativeX, double relativeY) {
        fRelativeX = relativeX;
        fRelativeY = relativeY;
    }

    public java.awt.Point locate(CH.ifa.draw.framework.Figure owner) {
        java.awt.Rectangle r = owner.displayBox();
        return new java.awt.Point(r.x + ((int) (r.width * fRelativeX)), r.y + ((int) (r.height * fRelativeY)));
    }

    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeDouble(fRelativeX);
        dw.writeDouble(fRelativeY);
    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        fRelativeX = dr.readDouble();
        fRelativeY = dr.readDouble();
    }

    public static CH.ifa.draw.framework.Locator east() {
        return new CH.ifa.draw.standard.RelativeLocator(1.0, 0.5);
    }

    /**
     * North.
     */
    public static CH.ifa.draw.framework.Locator north() {
        return new CH.ifa.draw.standard.RelativeLocator(0.5, 0.0);
    }

    /**
     * West.
     */
    public static CH.ifa.draw.framework.Locator west() {
        return new CH.ifa.draw.standard.RelativeLocator(0.0, 0.5);
    }

    /**
     * North east.
     */
    public static CH.ifa.draw.framework.Locator northEast() {
        return new CH.ifa.draw.standard.RelativeLocator(1.0, 0.0);
    }

    /**
     * North west.
     */
    public static CH.ifa.draw.framework.Locator northWest() {
        return new CH.ifa.draw.standard.RelativeLocator(0.0, 0.0);
    }

    /**
     * South.
     */
    public static CH.ifa.draw.framework.Locator south() {
        return new CH.ifa.draw.standard.RelativeLocator(0.5, 1.0);
    }

    /**
     * South east.
     */
    public static CH.ifa.draw.framework.Locator southEast() {
        return new CH.ifa.draw.standard.RelativeLocator(1.0, 1.0);
    }

    /**
     * South west.
     */
    public static CH.ifa.draw.framework.Locator southWest() {
        return new CH.ifa.draw.standard.RelativeLocator(0.0, 1.0);
    }

    /**
     * Center.
     */
    public static CH.ifa.draw.framework.Locator center() {
        return new CH.ifa.draw.standard.RelativeLocator(0.5, 0.5);
    }
}