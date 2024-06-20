/* @(#)AbstractLocator.java 5.1 */
package CH.ifa.draw.standard;
/**
 * AbstractLocator provides default implementations for
 * the Locator interface.
 *
 * @see Locator
 * @see Handle
 */
public abstract class AbstractLocator implements CH.ifa.draw.framework.Locator , CH.ifa.draw.util.Storable , java.lang.Cloneable {
    protected AbstractLocator() {
    }

    public java.lang.Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
            throw new java.lang.InternalError();
        }
    }

    /**
     * Stores the arrow tip to a StorableOutput.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
    }

    /**
     * Reads the arrow tip from a StorableInput.
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
    }
}