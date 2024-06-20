/* @(#)CommandMenu.java 5.1 */
package CH.ifa.draw.util;
public class CommandMenu extends java.awt.Menu implements java.awt.event.ActionListener {
    private java.util.Vector fCommands;

    public CommandMenu(java.lang.String name) {
        super(name);
        fCommands = new java.util.Vector(10);
    }

    /**
     * Adds a command to the menu. The item's label is
     * the command's name.
     */
    public synchronized void add(CH.ifa.draw.util.Command command) {
        java.awt.MenuItem m = new java.awt.MenuItem(command.name());
        m.addActionListener(this);
        add(m);
        fCommands.addElement(command);
    }

    /**
     * Adds a command with the given short cut to the menu. The item's label is
     * the command's name.
     */
    public synchronized void add(CH.ifa.draw.util.Command command, java.awt.MenuShortcut shortcut) {
        java.awt.MenuItem m = new java.awt.MenuItem(command.name(), shortcut);
        m.setName(command.name());
        m.addActionListener(this);
        add(m);
        fCommands.addElement(command);
    }

    public synchronized void remove(CH.ifa.draw.util.Command command) {
        java.lang.System.out.println("not implemented");
    }

    public synchronized void remove(java.awt.MenuItem item) {
        java.lang.System.out.println("not implemented");
    }

    /**
     * Changes the enabling/disabling state of a named menu item.
     */
    public synchronized void enable(java.lang.String name, boolean state) {
        for (int i = 0; i < getItemCount(); i++) {
            java.awt.MenuItem item = getItem(i);
            if (name.equals(item.getLabel())) {
                item.setEnabled(state);
                return;
            }
        }
    }

    public synchronized void checkEnabled() {
        int j = 0;
        for (int i = 0; i < getItemCount(); i++) {
            java.awt.MenuItem item = getItem(i);
            // ignore separators
            // a separator has a hyphen as its label
            if (item.getLabel().equals("-"))
                continue;

            CH.ifa.draw.util.Command cmd = ((CH.ifa.draw.util.Command) (fCommands.elementAt(j)));
            item.setEnabled(cmd.isExecutable());
            j++;
        }
    }

    /**
     * Executes the command.
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        int j = 0;
        java.lang.Object source = e.getSource();
        for (int i = 0; i < getItemCount(); i++) {
            java.awt.MenuItem item = getItem(i);
            // ignore separators
            // a separator has a hyphen as its label
            if (item.getLabel().equals("-"))
                continue;

            if (source == item) {
                CH.ifa.draw.util.Command cmd = ((CH.ifa.draw.util.Command) (fCommands.elementAt(j)));
                cmd.execute();
                break;
            }
            j++;
        }
    }
}