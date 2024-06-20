package org.acm.seguin.ide.elixir.version;
/**
 * Adds a file to source safe
 *
 * @author Chris Seguin
 * @created June 17, 1999
 */
public class AddListener implements java.awt.event.ActionListener {
    // Instance Variables
    private org.acm.seguin.ide.elixir.version.ElixirVersionControl ess;

    private java.lang.String fullName;

    private java.lang.String name;

    /**
     * Creates an instance of this
     *
     * @param init
     * 		Description of Parameter
     * @param fullName
     * 		Description of Parameter
     * @param name
     * 		Description of Parameter
     */
    public AddListener(org.acm.seguin.ide.elixir.version.ElixirVersionControl init, java.lang.String fullName, java.lang.String name) {
        ess = init;
        this.fullName = fullName;
        this.name = name;
    }

    /**
     * The menu item was selected
     *
     * @param evt
     * 		Description of Parameter
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        ess.add(fullName);
    }
}