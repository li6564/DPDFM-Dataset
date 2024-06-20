/* @(#)ToggleGridCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A command to toggle the snap to grid behavior.
 */
public class ToggleGridCommand extends CH.ifa.draw.util.Command {
    private CH.ifa.draw.framework.DrawingView fView;

    private java.awt.Point fGrid;

    /**
     * Constructs a toggle grid command.
     *
     * @param name
     * 		the command name
     * @param image
     * 		the pathname of the image
     * @param grid
     * 		the grid size. A grid size of 1,1 turns grid snapping off.
     */
    public ToggleGridCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view, java.awt.Point grid) {
        super(name);
        fView = view;
        fGrid = new java.awt.Point(grid.x, grid.y);
    }

    public void execute() {
        CH.ifa.draw.framework.PointConstrainer grid = fView.getConstrainer();
        if (grid != null) {
            fView.setConstrainer(null);
        } else {
            fView.setConstrainer(new CH.ifa.draw.standard.GridConstrainer(fGrid.x, fGrid.y));
        }
    }
}