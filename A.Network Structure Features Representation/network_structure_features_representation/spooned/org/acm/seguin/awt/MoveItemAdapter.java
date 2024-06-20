package org.acm.seguin.awt;
/**
 * Adapter to move items around in the listbox
 *
 * @author Chris Seguin
 */
class MoveItemAdapter implements java.awt.event.ActionListener {
    private org.acm.seguin.awt.OrderableListModel olm;

    private javax.swing.JList list;

    private int direction;

    /**
     * Constructor for the MoveItemAdapter object
     *
     * @param model
     * 		the list model
     * @param list
     * 		the list
     * @param direction
     * 		the direction of movement
     */
    public MoveItemAdapter(org.acm.seguin.awt.OrderableListModel model, javax.swing.JList list, int direction) {
        olm = model;
        this.list = list;
        this.direction = direction;
    }

    /**
     * Swap the item's on the user's command
     *
     * @param evt
     * 		the command to swap
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        int item = list.getSelectedIndex();
        if (item == (-1)) {
            return;
        }
        int newPos = item + direction;
        if ((newPos < 0) || (newPos >= olm.getSize())) {
            return;
        }
        olm.swap(item, newPos);
        list.setSelectedIndex(newPos);
    }
}