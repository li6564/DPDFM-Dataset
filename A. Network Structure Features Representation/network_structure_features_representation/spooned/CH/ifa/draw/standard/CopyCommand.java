/* @(#)CopyCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Copy the selection to the clipboard.
 *
 * @see Clipboard
 */
public class CopyCommand extends CH.ifa.draw.standard.FigureTransferCommand {
    /**
     * Constructs a copy command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     */
    public CopyCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name, view);
    }

    public void execute() {
        copySelection();
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 0;
    }
}