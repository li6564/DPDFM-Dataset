/* @(#)Storable.java 5.1 */
package CH.ifa.draw.util;
/**
 * Interface that is used by StorableInput and StorableOutput
 * to flatten and resurrect objects. Objects that implement
 * this interface and that are resurrected by StorableInput
 * have to provide a default constructor with no arguments.
 *
 * @see StorableInput
 * @see StorableOutput
 */
public interface Storable {
    /**
     * Writes the object to the StorableOutput.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw);

    /**
     * Reads the object from the StorableInput.
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException;
}