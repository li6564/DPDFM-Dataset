package org.acm.seguin.awt;
public class OrderableList extends javax.swing.JPanel {
    private org.acm.seguin.awt.OrderableListModel olm;

    public OrderableList(java.lang.Object[] data, javax.swing.ListCellRenderer render) {
        setLayout(null);
        olm = new org.acm.seguin.awt.OrderableListModel();
        olm.setData(data);
        javax.swing.JList list = new javax.swing.JList(olm);
        olm.setList(list);
        if (render != null)
            list.setCellRenderer(render);

        java.awt.Dimension dim = list.getPreferredSize();
        list.setSize(dim);
        list.setLocation(10, 10);
        add(list);
        javax.swing.JButton upButton = new javax.swing.JButton("Up");
        upButton.addActionListener(new org.acm.seguin.awt.MoveItemAdapter(olm, list, -1));
        java.awt.Dimension buttonSize = upButton.getPreferredSize();
        upButton.setSize(buttonSize);
        int top = java.lang.Math.max(10, (10 + (dim.height / 2)) - ((3 * buttonSize.height) / 2));
        int bottom = top + buttonSize.height;
        upButton.setLocation(dim.width + 20, top);
        add(upButton);
        javax.swing.JButton downButton = new javax.swing.JButton("Down");
        downButton.addActionListener(new org.acm.seguin.awt.MoveItemAdapter(olm, list, 1));
        buttonSize = downButton.getPreferredSize();
        downButton.setSize(buttonSize);
        upButton.setSize(buttonSize);
        top = java.lang.Math.max(bottom + 10, (10 + (dim.height / 2)) + (buttonSize.height / 2));
        bottom = top + buttonSize.height;
        downButton.setLocation(dim.width + 20, top);
        add(downButton);
        java.awt.Dimension panelSize = new java.awt.Dimension((30 + dim.width) + buttonSize.width, java.lang.Math.max(10 + bottom, 20 + dim.height));
        setPreferredSize(panelSize);
        list.setLocation(10, (panelSize.height - dim.height) / 2);
    }

    public static void main(java.lang.String[] args) {
        javax.swing.JFrame frame = new javax.swing.JFrame();
        java.lang.Object[] data = new java.lang.Object[]{ "one", "two", "three" };
        frame.getContentPane().add(new org.acm.seguin.awt.OrderableList(data, null));
        frame.pack();
        frame.show();
    }

    /**
     * Gets the correctly ordered data
     */
    public java.lang.Object[] getData() {
        return olm.getData();
    }

    public void addListDataListener(javax.swing.event.ListDataListener l) {
        olm.addListDataListener(l);
    }

    public void removeListDataListener(javax.swing.event.ListDataListener l) {
        olm.removeListDataListener(l);
    }
}