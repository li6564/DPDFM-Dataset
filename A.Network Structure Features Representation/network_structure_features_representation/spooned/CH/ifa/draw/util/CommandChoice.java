/* @(#)CommandChoice.java 5.1 */
package CH.ifa.draw.util;
/**
 * A Command enabled choice. Selecting a choice executes the
 * corresponding command.
 *
 * @see Command
 */
public class CommandChoice extends java.awt.Choice implements java.awt.event.ItemListener {
    private java.util.Vector fCommands;

    public CommandChoice() {
        fCommands = new java.util.Vector(10);
        addItemListener(this);
    }

    /**
     * Adds a command to the menu.
     */
    public synchronized void addItem(CH.ifa.draw.util.Command command) {
        addItem(command.name());
        fCommands.addElement(command);
    }

    /**
     * Executes the command.
     */
    public void itemStateChanged(java.awt.event.ItemEvent e) {
        CH.ifa.draw.util.Command command = ((CH.ifa.draw.util.Command) (fCommands.elementAt(getSelectedIndex())));
        command.execute();
    }
}