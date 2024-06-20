/* @(#)ReverseVectorEnumerator.java 5.1 */
package CH.ifa.draw.util;
/**
 * An Enumeration that enumerates a vector back (size-1) to front (0).
 */
public class ReverseVectorEnumerator implements java.util.Enumeration {
    java.util.Vector vector;

    int count;

    public ReverseVectorEnumerator(java.util.Vector v) {
        vector = v;
        count = vector.size() - 1;
    }

    public boolean hasMoreElements() {
        return count >= 0;
    }

    public java.lang.Object nextElement() {
        if (count >= 0) {
            return vector.elementAt(count--);
        }
        throw new java.util.NoSuchElementException("ReverseVectorEnumerator");
    }
}