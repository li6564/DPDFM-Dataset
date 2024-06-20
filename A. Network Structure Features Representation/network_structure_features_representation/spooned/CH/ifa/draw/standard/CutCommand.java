/* @(#)CutCommand.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Delete the selection and move the selected figures to
 * the clipboard.
 *
 * @see Clipboard
 */
public class CutCommand extends CH.ifa.draw.standard.FigureTransferCommand {
    /**
     * Constructs a cut command.
     *
     * @param name
     * 		the command name
     * @param view
     * 		the target view
     */
    public CutCommand(java.lang.String name, CH.ifa.draw.framework.DrawingView view) {
        super(name, view);
    }

    public void execute() {
        copySelection();
        deleteSelection();
        fView.checkDamage();
    }

    public boolean isExecutable() {
        return fView.selectionCount() > 0;
    }
}