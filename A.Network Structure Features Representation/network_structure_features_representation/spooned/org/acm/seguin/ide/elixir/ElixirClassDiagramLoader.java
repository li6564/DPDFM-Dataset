package org.acm.seguin.ide.elixir;
/**
 * Object responsible for loading the UML class diagrams for
 *  the Elixir IDE.
 *
 * @author Chris Seguin
 */
public class ElixirClassDiagramLoader {
    private static org.acm.seguin.ide.common.SingleDirClassDiagramReloader singleton;

    /**
     * Constructor for the ElixirClassDiagramLoader object
     */
    ElixirClassDiagramLoader() {
    }

    /**
     * Order from the user to reload the diagrams
     */
    public static void reload() {
        org.acm.seguin.ide.elixir.ElixirClassDiagramLoader.singleton.setNecessary(true);
        org.acm.seguin.ide.elixir.ElixirClassDiagramLoader.singleton.reload();
    }

    /**
     * Registers a reloader with Elixir IDE
     *
     * @param init
     * 		the reloader
     */
    public static void register(org.acm.seguin.ide.common.SingleDirClassDiagramReloader init) {
        org.acm.seguin.ide.elixir.ElixirClassDiagramLoader.singleton = init;
    }
}