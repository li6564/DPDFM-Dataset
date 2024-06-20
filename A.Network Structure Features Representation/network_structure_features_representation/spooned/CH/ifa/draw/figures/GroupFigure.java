/* @(#)GroupFigure.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A Figure that groups a collection of figures.
 */
public class GroupFigure extends CH.ifa.draw.standard.CompositeFigure {
    /* Serialization support. */
    private static final long serialVersionUID = 8311226373023297933L;

    private int groupFigureSerializedDataVersion = 1;

    /**
     * GroupFigures cannot be connected
     */
    public boolean canConnect() {
        return false;
    }

    /**
     * Gets the display box. The display box is defined as the union
     * of the contained figures.
     */
    public java.awt.Rectangle displayBox() {
        CH.ifa.draw.framework.FigureEnumeration k = figures();
        java.awt.Rectangle r = k.nextFigure().displayBox();
        while (k.hasMoreElements())
            r.add(k.nextFigure().displayBox());

        return r;
    }

    public void basicDisplayBox(java.awt.Point origin, java.awt.Point corner) {
        // do nothing
        // we could transform all components proportionally
    }

    public CH.ifa.draw.framework.FigureEnumeration decompose() {
        return new CH.ifa.draw.standard.FigureEnumerator(fFigures);
    }

    /**
     * Gets the handles for the GroupFigure.
     */
    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector();
        handles.addElement(new CH.ifa.draw.figures.GroupHandle(this, CH.ifa.draw.standard.RelativeLocator.northWest()));
        handles.addElement(new CH.ifa.draw.figures.GroupHandle(this, CH.ifa.draw.standard.RelativeLocator.northEast()));
        handles.addElement(new CH.ifa.draw.figures.GroupHandle(this, CH.ifa.draw.standard.RelativeLocator.southWest()));
        handles.addElement(new CH.ifa.draw.figures.GroupHandle(this, CH.ifa.draw.standard.RelativeLocator.southEast()));
        return handles;
    }

    /**
     * Sets the attribute of all the contained figures.
     */
    public void setAttribute(java.lang.String name, java.lang.Object value) {
        super.setAttribute(name, value);
        CH.ifa.draw.framework.FigureEnumeration k = figures();
        while (k.hasMoreElements())
            k.nextFigure().setAttribute(name, value);

    }
}