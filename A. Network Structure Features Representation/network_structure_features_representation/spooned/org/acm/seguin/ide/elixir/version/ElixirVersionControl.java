package org.acm.seguin.ide.elixir.version;
/**
 * Interact with version control
 *
 * @author Chris Seguin
 * @created June 29, 1999
 */
public class ElixirVersionControl implements org.acm.seguin.ide.elixir.version.IVersionControl {
    // Instance Variables
    private org.acm.seguin.version.VersionControl delegate = null;

    /**
     * Creates a menu item
     *
     * @param parent
     * 		Node that describes the file
     * @return The menu item
     */
    public javax.swing.JMenuItem getMenu(org.acm.seguin.ide.elixir.version.TNode parent) {
        java.lang.String name = parent.getName();
        javax.swing.JMenuItem jmi = new javax.swing.JMenuItem("Querying source control...");
        jmi.setEnabled(false);
        if (delegate == null) {
            init();
        }
        org.acm.seguin.ide.elixir.version.ElixirContainsThread ect = new org.acm.seguin.ide.elixir.version.ElixirContainsThread(jmi, parent, delegate, this);
        ect.start();
        return jmi;
    }

    /**
     * Is this file contained in visual source safe?
     *
     * @param filename
     * 		The full path of the file in question
     * @return Returns true if it is in source safe
     */
    public boolean contains(java.lang.String filename) {
        org.acm.seguin.version.VersionControlCache cache = org.acm.seguin.version.VersionControlCache.getCache();
        return cache.lookup(filename) != org.acm.seguin.version.VersionControlCache.ADD;
    }

    /**
     * Adds a file to visual source safe
     *
     * @param filename
     * 		The full path to the file
     */
    public void add(java.lang.String filename) {
        java.lang.System.out.println("Add:  " + filename);
        org.acm.seguin.version.VersionControlCache cache = org.acm.seguin.version.VersionControlCache.getCache();
        cache.add(filename, org.acm.seguin.version.VersionControlCache.ADD_PROGRESS);
        if (delegate == null) {
            init();
        }
        java.lang.Thread evct = new org.acm.seguin.ide.elixir.version.ElixirVersionControlThread(delegate, filename, org.acm.seguin.ide.elixir.version.ElixirVersionControlThread.ADD);
        evct.start();
    }

    /**
     * Checks in a file to visual source safe
     *
     * @param filename
     * 		The full pathname of the file
     */
    public void checkIn(java.lang.String filename) {
        java.lang.System.out.println("Check In:  " + filename);
        if (delegate == null) {
            init();
        }
        java.lang.Thread evct = new org.acm.seguin.ide.elixir.version.ElixirVersionControlThread(delegate, filename, org.acm.seguin.ide.elixir.version.ElixirVersionControlThread.CHECK_IN);
        evct.start();
    }

    /**
     * Checks out a file from visual source safe
     *
     * @param filename
     * 		The full path name of the file
     */
    public void checkOut(java.lang.String filename) {
        java.lang.System.out.println("Check Out:  " + filename);
        if (delegate == null) {
            init();
        }
        java.lang.Thread evct = new org.acm.seguin.ide.elixir.version.ElixirVersionControlThread(delegate, filename, org.acm.seguin.ide.elixir.version.ElixirVersionControlThread.CHECK_OUT);
        evct.start();
    }

    /**
     * Adds an array of files
     *
     * @param filenames
     * 		The array of files to add
     */
    public void add(java.lang.String[] filenames) {
        java.lang.System.out.println("Multiple Add");
        for (int ndx = 0; ndx < filenames.length; ndx++) {
            add(filenames[ndx]);
        }
    }

    /**
     * Checks in multiple files
     *
     * @param filenames
     * 		Multiple files to check in
     */
    public void checkIn(java.lang.String[] filenames) {
        java.lang.System.out.println("Multiple Check In");
        for (int ndx = 0; ndx < filenames.length; ndx++) {
            checkIn(filenames[ndx]);
        }
    }

    /**
     * Constructor for ElixirVersionControl object
     */
    private synchronized void init() {
        if (delegate == null) {
            // Make sure everything is installed properly
            new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
            delegate = new org.acm.seguin.version.SourceSafe();
        }
    }
}