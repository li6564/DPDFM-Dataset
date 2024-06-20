/* @(#)GroupCommand.java 5.1 */
package CH.ifa.draw.figures;
/**
 * Command to group the selection into a GroupFigure.
 *
 * @see GroupFigure
 */
public class GroupCommand extends CH.ifa.draw.util.Command {
    private CH.ifa.draw.framework.DrawingView fView;

    /**
     * Constructs a group command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     */
    public GroupCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name);
        fView = view;
    }

    public void execute() {
        java.util.Vector selected = fView.selectionZOrdered();
        CH.ifa.draw.framework.Drawing drawing = fView.drawing();
        if (selected.size() > 0) {
            fView.clearSelection();
            drawing.orphanAll(selected);
            CH.ifa.draw.figures.GroupFigure group = new CH.ifa.draw.figures.GroupFigure();
            group.addAll(selected);
            fView.addToSelection(drawing.add(group));
        }
        fView.checkDamage();
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 0;
    }
}