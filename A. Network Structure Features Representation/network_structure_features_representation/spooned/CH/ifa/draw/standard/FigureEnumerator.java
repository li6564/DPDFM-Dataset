/* @(#)FigureEnumerator.java 5.1 */
package CH.ifa.draw.standard;
/**
 * An Enumeration for a Vector of Figures.
 */
public final class FigureEnumerator implements CH.ifa.draw.framework.FigureEnumeration {
    java.util.Enumeration fEnumeration;

    public FigureEnumerator(java.util.Vector v) {
        fEnumeration = v.elements();
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
     * Returns the next element of the enumeration. Calls to this
     * method will enumerate successive elements.
     *
     * @exception NoSuchElementException
     * 		If no more elements exist.
     */
    public CH.ifa.draw.framework.Figure nextFigure() {
        return ((CH.ifa.draw.framework.Figure) (fEnumeration.nextElement()));
    }
}