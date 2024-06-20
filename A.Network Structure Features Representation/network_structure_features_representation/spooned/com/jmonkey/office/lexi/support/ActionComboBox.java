package com.jmonkey.office.lexi.support;
public final class ActionComboBox extends javax.swing.JComboBox implements java.awt.event.ItemListener {
    private final java.util.Hashtable _ACTIONS = new java.util.Hashtable();

    public ActionComboBox() {
        super();
        this.addItemListener(this);
    }

    public ActionComboBox(javax.swing.Action[] items) {
        super();
        for (int i = 0; i < items.length; i++) {
            this.addItem(items[i]);
        }
        this.addItemListener(this);
    }

    public void addItem(javax.swing.Action a) {
        if (a != null) {
            if (!_ACTIONS.containsKey(((java.lang.String) (a.getValue(javax.swing.Action.NAME))))) {
                _ACTIONS.put(((java.lang.String) (a.getValue(javax.swing.Action.NAME))), a);
                super.addItem(((java.lang.String) (a.getValue(javax.swing.Action.NAME))));
            }
        }
    }

    public java.lang.Object getItemAt(int index) {
        java.lang.String name = ((java.lang.String) (super.getItemAt(index)));
        if (_ACTIONS.containsKey(name)) {
            return ((javax.swing.Action) (_ACTIONS.get(name)));
        } else {
            return null;
        }
    }

    public void insertItemAt(javax.swing.Action a, int index) {
        if (a != null) {
            if (!_ACTIONS.containsKey(((java.lang.String) (a.getValue(javax.swing.Action.NAME))))) {
                _ACTIONS.put(((java.lang.String) (a.getValue(javax.swing.Action.NAME))), a);
                super.insertItemAt(((java.lang.String) (a.getValue(javax.swing.Action.NAME))), index);
            }
        }
    }

    public void itemStateChanged(java.awt.event.ItemEvent e) {
        java.lang.String name = ((java.lang.String) (e.getItem()));
        if (_ACTIONS.containsKey(name)) {
            ((javax.swing.Action) (_ACTIONS.get(name))).actionPerformed(new java.awt.event.ActionEvent(this, java.awt.event.ActionEvent.ACTION_PERFORMED, name));
        }
    }

    public void removeAllItems() {
        _ACTIONS.clear();
        super.removeAllItems();
    }

    public void removeItem(java.lang.Object anObject) {
        throw new java.lang.RuntimeException(("Method removeItem(Object anObject) not implemented in " + com.jmonkey.office.lexi.support.ActionComboBox.class.getName()) + ". User removeAllItems() instead.");
    }

    public void removeItemAt(int anIndex) {
        throw new java.lang.RuntimeException(("Method removeItemAt(int anIndex) not implemented in " + com.jmonkey.office.lexi.support.ActionComboBox.class.getName()) + ". User removeAllItems() instead.");
    }
}