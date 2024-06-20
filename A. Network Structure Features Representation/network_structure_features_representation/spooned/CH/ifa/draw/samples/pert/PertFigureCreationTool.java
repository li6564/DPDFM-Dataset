/* @(#)PertFigureCreationTool.java 5.1 */
package CH.ifa.draw.samples.pert;
/**
 * A more efficient version of the generic Pert creation
 * tool that is not based on cloning.
 */
public class PertFigureCreationTool extends CH.ifa.draw.standard.CreationTool {
    public PertFigureCreationTool(CH.ifa.draw.framework.DrawingView view) {
        super(view);
    }

    /**
     * Creates a new PertFigure.
     */
    protected CH.ifa.draw.framework.Figure createFigure() {
        return new CH.ifa.draw.samples.pert.PertFigure();
    }
}