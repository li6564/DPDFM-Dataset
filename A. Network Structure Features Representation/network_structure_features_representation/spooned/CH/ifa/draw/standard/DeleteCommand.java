/* @(#)DeleteCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Command to delete the selection.
 */
public class DeleteCommand extends CH.ifa.draw.standard.FigureTransferCommand {
    /**
     * Constructs a delete command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     */
    public DeleteCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name, view);
    }

    public void execute() {
        deleteSelection();
        fView.checkDamage();
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 0;
    }
}