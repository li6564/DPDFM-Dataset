/* @(#)BouncingDrawing.java 5.1 */
package CH.ifa.draw.samples.javadraw;
public class BouncingDrawing extends CH.ifa.draw.standard.StandardDrawing implements CH.ifa.draw.util.Animatable {
    /* Serialization support. */
    private static final long serialVersionUID = -8566272817418441758L;

    private int bouncingDrawingSerializedDataVersion = 1;

    public synchronized CH.ifa.draw.framework.Figure add(CH.ifa.draw.framework.Figure figure) {
        if (!(figure instanceof CH.ifa.draw.samples.javadraw.AnimationDecorator))
            figure = new CH.ifa.draw.samples.javadraw.AnimationDecorator(figure);

        return super.add(figure);
    }

    public synchronized CH.ifa.draw.framework.Figure remove(CH.ifa.draw.framework.Figure figure) {
        CH.ifa.draw.framework.Figure f = super.remove(figure);
        if (f instanceof CH.ifa.draw.samples.javadraw.AnimationDecorator)
            return ((CH.ifa.draw.samples.javadraw.AnimationDecorator) (f)).peelDecoration();

        return f;
    }

    public synchronized void replace(CH.ifa.draw.framework.Figure figure, CH.ifa.draw.framework.Figure replacement) {
        if (!(replacement instanceof CH.ifa.draw.samples.javadraw.AnimationDecorator))
            replacement = new CH.ifa.draw.samples.javadraw.AnimationDecorator(replacement);

        super.replace(figure, replacement);
    }

    public void animationStep() {
        java.util.Enumeration k = figures();
        while (k.hasMoreElements())
            ((CH.ifa.draw.samples.javadraw.AnimationDecorator) (k.nextElement())).animationStep();

    }
}