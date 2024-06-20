package org.acm.seguin.ide.elixir.version;
/**
 * Interact with version control
 *
 * @author Chris Seguin
 * @created July 12, 1999
 */
public class ElixirContainsThread extends java.lang.Thread {
    // Instance Variables
    private org.acm.seguin.version.VersionControl delegate;

    private javax.swing.JMenuItem menu;

    private org.acm.seguin.ide.elixir.version.TNode parent;

    private org.acm.seguin.ide.elixir.version.ElixirVersionControl evc;

    /**
     * Constructor
     *
     * @param initMenuItem
     * 		The menu item
     * @param initParent
     * 		The initial parent
     * @param initDelegate
     * 		The delegate
     * @param initEVC
     * 		Description of Parameter
     */
    public ElixirContainsThread(javax.swing.JMenuItem initMenuItem, org.acm.seguin.ide.elixir.version.TNode initParent, org.acm.seguin.version.VersionControl initDelegate, org.acm.seguin.ide.elixir.version.ElixirVersionControl initEVC) {
        menu = initMenuItem;
        parent = initParent;
        delegate = initDelegate;
        evc = initEVC;
    }

    /**
     * Actually do the work
     */
    public void run() {
        java.lang.String name = parent.getName();
        if (!isUnderSourceControl(name)) {
            menu.setText("Not under source control");
            menu.setEnabled(false);
            return;
        }
        java.lang.System.out.println("Full Name:  " + parent.getFullName());
        java.io.File file = new java.io.File(parent.getFullName());
        if (!file.canWrite()) {
            checkOut();
        } else if (contains(parent.getFullName()) == org.acm.seguin.version.VersionControlCache.ADD) {
            add();
        } else {
            checkIn();
        }
        menu.repaint();
    }

    /**
     * Is this file contained in visual source safe?
     *
     * @param filename
     * 		The full path of the file in question
     * @return Returns true if it is in source safe
     */
    public int contains(java.lang.String filename) {
        // Start with the cache
        org.acm.seguin.version.VersionControlCache cache = org.acm.seguin.version.VersionControlCache.getCache();
        if (cache.isInCache(filename)) {
            return cache.lookup(filename);
        }
        boolean way = delegate.contains(filename);
        int result;
        if (way) {
            result = org.acm.seguin.version.VersionControlCache.CHECK_IN;
        } else {
            result = org.acm.seguin.version.VersionControlCache.ADD;
        }
        cache.add(filename, result);
        return result;
    }

    /**
     * Determines if the file is under souce control
     *
     * @param name
     * 		the name of the file
     * @return true if it is under source control
     */
    private boolean isUnderSourceControl(java.lang.String name) {
        if (name == null) {
            return false;
        }
        try {
            org.acm.seguin.util.FileSettings bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "vss");
            int index = 1;
            while (true) {
                java.lang.String next = bundle.getString("extension." + index);
                java.lang.System.out.println(((("\t\tComparing:  [" + name) + "] to [") + next) + "]");
                if (name.endsWith(next)) {
                    java.lang.System.out.println("\t\tFound it");
                    return true;
                }
                index++;
            } 
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            // Finished
        }
        return false;
    }

    /**
     * Sets the menu up to say that it is being checked out
     */
    private void checkOut() {
        boolean enabled = true;
        java.lang.String filename = parent.getFullName();
        org.acm.seguin.version.VersionControlCache cache = org.acm.seguin.version.VersionControlCache.getCache();
        if (cache.isInCache(filename)) {
            enabled = cache.lookup(filename) == org.acm.seguin.version.VersionControlCache.CHECK_OUT;
        } else {
            cache.add(filename, org.acm.seguin.version.VersionControlCache.CHECK_OUT);
        }
        menu.setText("Check Out");
        menu.setEnabled(enabled);
        menu.addActionListener(new org.acm.seguin.ide.elixir.version.CheckOutListener(evc, filename, parent.getName()));
    }

    /**
     * Description of the Method
     */
    private void add() {
        menu.setText("Add");
        menu.setEnabled(false);
        menu.addActionListener(new org.acm.seguin.ide.elixir.version.AddListener(evc, parent.getFullName(), parent.getName()));
    }

    /**
     * Description of the Method
     */
    private void checkIn() {
        org.acm.seguin.version.VersionControlCache cache = org.acm.seguin.version.VersionControlCache.getCache();
        java.lang.String filename = parent.getFullName();
        menu.setText("Check In");
        menu.setEnabled(cache.lookup(filename) == org.acm.seguin.version.VersionControlCache.CHECK_IN);
        menu.addActionListener(new org.acm.seguin.ide.elixir.version.CheckInListener(evc, filename, parent.getName()));
    }
}