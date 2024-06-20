/* @(#)FigureChangeEventMulticaster.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Manages a list of FigureChangeListeners to be notified of
 * specific FigureChangeEvents.
 */
public class FigureChangeEventMulticaster extends java.awt.AWTEventMulticaster implements CH.ifa.draw.framework.FigureChangeListener {
    public FigureChangeEventMulticaster(java.util.EventListener a, java.util.EventListener b) {
        super(a, b);
    }

    public void figureInvalidated(CH.ifa.draw.framework.FigureChangeEvent e) {
        ((CH.ifa.draw.framework.FigureChangeListener) (a)).figureInvalidated(e);
        ((CH.ifa.draw.framework.FigureChangeListener) (b)).figureInvalidated(e);
    }

    public void figureRequestRemove(CH.ifa.draw.framework.FigureChangeEvent e) {
        ((CH.ifa.draw.framework.FigureChangeListener) (a)).figureRequestRemove(e);
        ((CH.ifa.draw.framework.FigureChangeListener) (b)).figureRequestRemove(e);
    }

    public void figureRequestUpdate(CH.ifa.draw.framework.FigureChangeEvent e) {
        ((CH.ifa.draw.framework.FigureChangeListener) (a)).figureRequestUpdate(e);
        ((CH.ifa.draw.framework.FigureChangeListener) (b)).figureRequestUpdate(e);
    }

    public void figureChanged(CH.ifa.draw.framework.FigureChangeEvent e) {
        ((CH.ifa.draw.framework.FigureChangeListener) (a)).figureChanged(e);
        ((CH.ifa.draw.framework.FigureChangeListener) (b)).figureChanged(e);
    }

    public void figureRemoved(CH.ifa.draw.framework.FigureChangeEvent e) {
        ((CH.ifa.draw.framework.FigureChangeListener) (a)).figureRemoved(e);
        ((CH.ifa.draw.framework.FigureChangeListener) (b)).figureRemoved(e);
    }

    public static CH.ifa.draw.framework.FigureChangeListener add(CH.ifa.draw.framework.FigureChangeListener a, CH.ifa.draw.framework.FigureChangeListener b) {
        return ((CH.ifa.draw.framework.FigureChangeListener) (CH.ifa.draw.standard.FigureChangeEventMulticaster.addInternal(a, b)));
    }

    public static CH.ifa.draw.framework.FigureChangeListener remove(CH.ifa.draw.framework.FigureChangeListener l, CH.ifa.draw.framework.FigureChangeListener oldl) {
        return ((CH.ifa.draw.framework.FigureChangeListener) (CH.ifa.draw.standard.FigureChangeEventMulticaster.removeInternal(l, oldl)));
    }

    protected java.util.EventListener remove(java.util.EventListener oldl) {
        if (oldl == a)
            return b;

        if (oldl == b)
            return a;

        java.util.EventListener a2 = CH.ifa.draw.standard.FigureChangeEventMulticaster.removeInternal(((CH.ifa.draw.framework.FigureChangeListener) (a)), oldl);
        java.util.EventListener b2 = CH.ifa.draw.standard.FigureChangeEventMulticaster.removeInternal(((CH.ifa.draw.framework.FigureChangeListener) (b)), oldl);
        if ((a2 == a) && (b2 == b))
            return this;
        else
            return CH.ifa.draw.standard.FigureChangeEventMulticaster.addInternal(((CH.ifa.draw.framework.FigureChangeListener) (a2)), ((CH.ifa.draw.framework.FigureChangeListener) (b2)));

    }

    protected static java.util.EventListener addInternal(CH.ifa.draw.framework.FigureChangeListener a, CH.ifa.draw.framework.FigureChangeListener b) {
        if (a == null)
            return b;

        if (b == null)
            return a;

        return new CH.ifa.draw.standard.FigureChangeEventMulticaster(a, b);
    }

    protected static java.util.EventListener removeInternal(java.util.EventListener l, java.util.EventListener oldl) {
        if ((l == oldl) || (l == null)) {
            return null;
        } else if (l instanceof CH.ifa.draw.standard.FigureChangeEventMulticaster) {
            return ((CH.ifa.draw.standard.FigureChangeEventMulticaster) (l)).remove(oldl);
        } else {
            return l;// it's not here

        }
    }
}