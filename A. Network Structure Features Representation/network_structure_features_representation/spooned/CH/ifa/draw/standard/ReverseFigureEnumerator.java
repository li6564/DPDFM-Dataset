/* @(#)ReverseFigureEnumerator.java 5.1 */
package CH.ifa.draw.standard;
/**
 * An Enumeration that enumerates a vector of figures back (size-1) to front (0).
 */
public final class ReverseFigureEnumerator implements CH.ifa.draw.framework.FigureEnumeration {
    CH.ifa.draw.util.ReverseVectorEnumerator fEnumeration;

    public ReverseFigureEnumerator(java.util.Vector v) {
        fEnumeration = new CH.ifa.draw.util.ReverseVectorEnumerator(v);
    }

    /**
     * Returns true if the enumeration contains more elements; false
     * if its empty.
     */
    public boolean hasMoreElements() {
        return fEnumeration.hasMoreElements();
    }

    /**
     * Returns the next element of the enumeration. Calls to this
     * method will enumerate successive elements.
     *
     * @exception NoSuchElementException
     * 		If no more elements exist.
     */
    public java.lang.Object nextElement() {
        return fEnumeration.nextElement();
    }

    /**
     * Returns the next element casted as a figure of the enumeration. Calls to this
     * method will enumerate successive elements.
     *
     * @exception NoSuchElementException
     * 		If no more elements exist.
     */
    public CH.ifa.draw.framework.Figure nextFigure() {
        return ((CH.ifa.draw.framework.Figure) (fEnumeration.nextElement()));
    }
}