/* @(#)PertDependency.java 5.1 */
package CH.ifa.draw.samples.pert;
public class PertDependency extends CH.ifa.draw.figures.LineConnection {
    /* Serialization support. */
    private static final long serialVersionUID = -7959500008698525009L;

    private int pertDependencySerializedDataVersion = 1;

    public PertDependency() {
        setEndDecoration(new CH.ifa.draw.figures.ArrowTip());
        setStartDecoration(null);
    }

    public void handleConnect(CH.ifa.draw.framework.Figure start, CH.ifa.draw.framework.Figure end) {
        CH.ifa.draw.samples.pert.PertFigure source = ((CH.ifa.draw.samples.pert.PertFigure) (start));
        CH.ifa.draw.samples.pert.PertFigure target = ((CH.ifa.draw.samples.pert.PertFigure) (end));
        if (source.hasCycle(target)) {
            setAttribute("FrameColor", java.awt.Color.red);
        } else {
            target.addPreTask(source);
            source.addPostTask(target);
            source.notifyPostTasks();
        }
    }

    public void handleDisconnect(CH.ifa.draw.framework.Figure start, CH.ifa.draw.framework.Figure end) {
        CH.ifa.draw.samples.pert.PertFigure source = ((CH.ifa.draw.samples.pert.PertFigure) (start));
        CH.ifa.draw.samples.pert.PertFigure target = ((CH.ifa.draw.samples.pert.PertFigure) (end));
        if (target != null) {
            target.removePreTask(source);
            target.updateDurations();
        }
        if (source != null)
            source.removePostTask(target);

    }

    public boolean canConnect(CH.ifa.draw.framework.Figure start, CH.ifa.draw.framework.Figure end) {
        return (start instanceof CH.ifa.draw.samples.pert.PertFigure) && (end instanceof CH.ifa.draw.samples.pert.PertFigure);
    }

    public java.util.Vector handles() {
        java.util.Vector handles = super.handles();
        // don't allow to reconnect the starting figure
        handles.setElementAt(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.figures.PolyLineFigure.locator(0)), 0);
        return handles;
    }
}