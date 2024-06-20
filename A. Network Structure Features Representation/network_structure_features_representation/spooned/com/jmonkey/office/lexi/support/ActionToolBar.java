package com.jmonkey.office.lexi.support;
public final class ActionToolBar extends javax.swing.JToolBar {
    /* registry of listeners created for Action-JButton
    linkage.  This is needed so that references can
    be cleaned up at remove time to allow GC.
     */
    private static java.util.Hashtable listenerRegistry = null;

    /**
     * Add a new JButton which dispatches the action.
     *
     * @param a
     * 		the Action object to add as a new menu item
     */
    public javax.swing.JButton add(javax.swing.Action a) {
        javax.swing.JButton b = new javax.swing.JButton(((java.lang.String) (a.getValue(javax.swing.Action.NAME))), ((javax.swing.Icon) (a.getValue(javax.swing.Action.SMALL_ICON))));
        b.setHorizontalTextPosition(javax.swing.JButton.CENTER);
        b.setVerticalTextPosition(javax.swing.JButton.BOTTOM);
        b.setEnabled(a.isEnabled());
        b.addActionListener(a);
        add(b);
        java.beans.PropertyChangeListener actionPropertyChangeListener = createActionChangeListener(b);
        if (com.jmonkey.office.lexi.support.ActionToolBar.listenerRegistry == null) {
            com.jmonkey.office.lexi.support.ActionToolBar.listenerRegistry = new java.util.Hashtable();
        }
        com.jmonkey.office.lexi.support.ActionToolBar.listenerRegistry.put(b, actionPropertyChangeListener);
        com.jmonkey.office.lexi.support.ActionToolBar.listenerRegistry.put(actionPropertyChangeListener, a);
        a.addPropertyChangeListener(actionPropertyChangeListener);
        return b;
    }

    /**
     * Add a new JButton which dispatches the action.
     *
     * @param a
     * 		the Action object to add as a new menu item
     * @param showText
     * 		true if the button should show the action text.
     */
    public javax.swing.JButton add(boolean showText, javax.swing.Action a) {
        javax.swing.JButton b = (showText) ? new javax.swing.JButton(((java.lang.String) (a.getValue(javax.swing.Action.NAME))), ((javax.swing.Icon) (a.getValue(javax.swing.Action.SMALL_ICON)))) : new javax.swing.JButton(((javax.swing.Icon) (a.getValue(javax.swing.Action.SMALL_ICON))));
        if (showText) {
            b.setHorizontalTextPosition(javax.swing.JButton.CENTER);
            b.setVerticalTextPosition(javax.swing.JButton.BOTTOM);
        } else {
            b.setMargin(new java.awt.Insets(0, 0, 0, 0));
        }
        b.setEnabled(a.isEnabled());
        b.addActionListener(a);
        add(b);
        // PropertyChangeListener actionPropertyChangeListener =
        // createActionChangeListener(b);
        // if (listenerRegistry == null) {
        // listenerRegistry = new Hashtable();
        // }
        // listenerRegistry.put(b, actionPropertyChangeListener);
        // listenerRegistry.put(actionPropertyChangeListener, a);
        // a.addPropertyChangeListener(actionPropertyChangeListener);
        b.setAction(a);
        return b;
    }
}