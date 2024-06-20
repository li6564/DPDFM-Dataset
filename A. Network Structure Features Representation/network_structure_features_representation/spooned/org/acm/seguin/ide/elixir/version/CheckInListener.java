package org.acm.seguin.ide.elixir.version;
/**
 * Checks a file into source safe
 *
 * @author Chris Seguin
 * @created June 17, 1999
 */
public class CheckInListener implements java.awt.event.ActionListener {
    // Instance Variables
    private org.acm.seguin.ide.elixir.version.ElixirVersionControl ess;

    private java.lang.String fullName;

    private java.lang.String name;

    /**
     * Creates an instance of this
     *
     * @param init
     * 		The elixir version control unit
     * @param fullName
     * 		the file's full name
     * @param name
     * 		the file's name
     */
    public CheckInListener(org.acm.seguin.ide.elixir.version.ElixirVersionControl init, java.lang.String fullName, java.lang.String name) {
        ess = init;
        this.fullName = fullName;
        this.name = name;
    }

    /**
     * The menu item was selected
     *
     * @param evt
     * 		the menu selection event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        org.acm.seguin.version.VersionControlCache cache = org.acm.seguin.version.VersionControlCache.getCache();
        cache.add(fullName, org.acm.seguin.version.VersionControlCache.CHECK_IN_PROGRESS);
        ess.checkIn(fullName);
        cache.add(fullName, org.acm.seguin.version.VersionControlCache.CHECK_OUT);
    }
}