/* @(#)LocatorHandle.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A LocatorHandle implements a Handle by delegating the location requests to
 * a Locator object.
 *
 * @see Locator
 */
public class LocatorHandle extends CH.ifa.draw.standard.AbstractHandle {
    private CH.ifa.draw.framework.Locator fLocator;

    /**
     * Initializes the LocatorHandle with the given Locator.
     */
    public LocatorHandle(CH.ifa.draw.framework.Figure owner, CH.ifa.draw.framework.Locator l) {
        super(owner);
        fLocator = l;
    }

    /**
     * Locates the handle on the figure by forwarding the request
     * to its figure.
     */
    public java.awt.Point locate() {
        return fLocator.locate(owner());
    }
}