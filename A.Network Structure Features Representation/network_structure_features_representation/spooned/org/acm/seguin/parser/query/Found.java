package org.acm.seguin.parser.query;
/**
 * Description of where the search ended.  The results
 *  include the node where the search found the parse
 *  tree and the index into the children of that node.
 *
 * @author Chris Seguin
 */
public class Found {
    private org.acm.seguin.parser.Node root;

    private int index;

    /**
     * Constructor for the Found object
     *
     * @param initRoot
     * 		Description of Parameter
     * @param initIndex
     * 		Description of Parameter
     */
    public Found(org.acm.seguin.parser.Node initRoot, int initIndex) {
        root = initRoot;
        index = initIndex;
    }

    /**
     * Gets the Index attribute of the Found object
     *
     * @return The Index value
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets the Root attribute of the Found object
     *
     * @return The Root value
     */
    public org.acm.seguin.parser.Node getRoot() {
        return root;
    }
}