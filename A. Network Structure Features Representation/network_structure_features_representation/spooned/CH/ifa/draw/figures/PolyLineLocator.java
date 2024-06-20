/* @(#)PolyLineLocator.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A poly line figure consists of a list of points.
 * It has an optional line decoration at the start and end.
 *
 * @see LineDecoration
 */
class PolyLineLocator extends CH.ifa.draw.standard.AbstractLocator {
    int fIndex;

    public PolyLineLocator(int index) {
        fIndex = index;
    }

    public java.awt.Point locate(CH.ifa.draw.framework.Figure owner) {
        CH.ifa.draw.figures.PolyLineFigure plf = ((CH.ifa.draw.figures.PolyLineFigure) (owner));
        // guard against changing PolyLineFigures -> temporary hack
        if (fIndex < plf.pointCount())
            return ((CH.ifa.draw.figures.PolyLineFigure) (owner)).pointAt(fIndex);

        return new java.awt.Point(0, 0);
    }
}