/* @(#)FigureEnumeration.java 5.1 */
package CH.ifa.draw.framework;
/**
 * Interface for Enumerations that access Figures.
 * It provides a method nextFigure, that hides the down casting
 * from client code.
 */
public interface FigureEnumeration extends java.util.Enumeration {
    /**
     * Returns the next element of the enumeration. Calls to this
     * method will enumerate successive elements.
     *
     * @exception NoSuchElementException
     * 		If no more elements exist.
     */
    public CH.ifa.draw.framework.Figure nextFigure();
}