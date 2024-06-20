package org.acm.seguin.awt;
/**
 * Contains a list of items for a list box that can be reordered
 *
 * @author Chris Seguin
 */
class OrderableListModel extends javax.swing.AbstractListModel {
    private java.lang.Object[] data;

    private javax.swing.JList list;

    /**
     * Constructor for the OrderableListModel object
     */
    public OrderableListModel() {
    }

    /**
     * Sets the Data attribute of the OrderableListModel object
     *
     * @param value
     * 		The new Data value
     */
    public void setData(java.lang.Object[] value) {
        data = value;
    }

    /**
     * Sets the List attribute of the OrderableListModel object
     *
     * @param value
     * 		The new List value
     */
    public void setList(javax.swing.JList value) {
        list = value;
    }

    /**
     * Gets the Data attribute of the OrderableListModel object
     *
     * @return The Data value
     */
    public java.lang.Object[] getData() {
        return data;
    }

    /**
     * Gets the Element At from the data array
     *
     * @param index
     * 		the index into the array
     * @return The ElementAt value
     */
    public java.lang.Object getElementAt(int index) {
        return data[index];
    }

    /**
     * Gets the Size attribute of the OrderableListModel object
     *
     * @return The Size value
     */
    public int getSize() {
        return data.length;
    }

    /**
     * Swaps two items in the list box
     *
     * @param first
     * 		the first one
     * @param second
     * 		the second one
     */
    public void swap(int first, int second) {
        java.lang.Object temp = data[first];
        data[first] = data[second];
        data[second] = temp;
        fireContentsChanged(this, java.lang.Math.min(first, second), java.lang.Math.max(first, second));
    }
}