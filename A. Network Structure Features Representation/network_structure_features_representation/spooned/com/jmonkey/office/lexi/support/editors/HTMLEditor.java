package com.jmonkey.office.lexi.support.editors;
import com.jmonkey.office.lexi.support.Editor;
/**
 */
public final class HTMLEditor extends com.jmonkey.office.lexi.support.Editor implements java.awt.event.MouseListener , java.awt.event.KeyListener {
    // Public members
    /**
     * The Content type of the editor.
     */
    public static final java.lang.String CONTENT_TYPE = VALID_CONTENT_TYPES[1];

    /**
     * File Extensions this editor will handle.
     */
    public static final java.lang.String[] FILE_EXTENSIONS = new java.lang.String[]{ "html", "htm" };

    // Private Members
    private javax.swing.JTextPane _EDITOR = null;

    private boolean _CHANGED = false;

    private com.jmonkey.office.lexi.support.editors.HTMLEditor.E _EVENT_LISTENER = null;

    private java.io.File _FILE = null;

    // Stuff for ActionManager ==================
    private javax.swing.text.Element _CURRENT_RUN = null;

    private javax.swing.text.Element _CURRENT_PARAGRAPH = null;

    // Inner Classes ==========================================================
    /**
     * Document Event manager
     */
    private final class E extends java.lang.Object implements javax.swing.event.DocumentListener , javax.swing.event.UndoableEditListener , javax.swing.event.HyperlinkListener , java.awt.event.FocusListener , java.beans.VetoableChangeListener , javax.swing.event.ChangeListener {
        private com.jmonkey.office.lexi.support.editors.HTMLEditor _PARENT = null;

        /**
         * Default DFL constructor.
         *
         * @param parent
         * 		com.jmonkey.common.StyledEditor
         */
        protected E(com.jmonkey.office.lexi.support.editors.HTMLEditor parent) {
            _PARENT = parent;
        }

        /**
         * FocusListener reciver.
         *
         * @param e
         * 		java.awt.event.FocusEvent
         */
        public void focusGained(java.awt.event.FocusEvent e) {
            // Code.event("focusGained:" + e.toString());
            _PARENT.activate();
        }

        /**
         * FocusListener reciver.
         *
         * @param e
         * 		java.awt.event.FocusEvent
         */
        public void focusLost(java.awt.event.FocusEvent e) {
            // This causes a problem, because
            // it gets called when menus or
            // dialogs are opened.
            // if(!e.isTemporary()){
            // _PARENT.deactivate();
            // }
        }

        /**
         * DocumentListener reciver.
         *
         * @param e
         * 		javax.swing.event.DocumentEvent
         */
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            // Code.event("insertUpdate:" + e.toString());
            if (!_PARENT.isChanged()) {
                _PARENT.setChanged(true);
            }
        }

        /**
         * DocumentListener reciver.
         *
         * @param e
         * 		javax.swing.event.DocumentEvent
         */
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            // Code.event("removeUpdate:" + e.toString());
            if (!_PARENT.isChanged()) {
                _PARENT.setChanged(true);
            }
        }

        /**
         * DocumentListener reciver.
         *
         * @param e
         * 		javax.swing.event.DocumentEvent
         */
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            // Code.event("changedUpdate:" + e.toString());
            if (!_PARENT.isChanged()) {
                _PARENT.setChanged(true);
            }
        }

        /**
         * UndoableEditListener reciver.
         *
         * @param e
         * 		javax.swing.event.UndoableEditEvent
         */
        public void undoableEditHappened(javax.swing.event.UndoableEditEvent e) {
            _PARENT.getUndoManager().addEdit(e.getEdit());
        }

        /**
         * HyperlinkListener reciver.
         *
         * @param e
         * 		javax.swing.event.HyperlinkEvent
         */
        public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent e) {
            // Code.event("hyperlinkUpdate:" + e.toString());
        }

        /**
         * VetoableChangeListener reciver.
         *
         * @param evt
         * 		java.beans.PropertyChangeEvent
         * @exception java.beans.PropertyVetoException
         */
        public void vetoableChange(java.beans.PropertyChangeEvent evt) throws java.beans.PropertyVetoException {
            // Code.event("vetoableChange:" + evt.toString());
        }

        /**
         * ChangeListener reciver.
         *
         * @param e
         * 		javax.swing.event.ChangeEvent
         */
        public void stateChanged(javax.swing.event.ChangeEvent e) {
            // Code.event("stateChanged:" + e.toString());
        }
    }

    /**
     * Default Document Constructor.
     */
    public HTMLEditor() {
        super();
        this.init();
    }

    public void append(java.io.File file) throws java.io.IOException {
        com.jmonkey.office.lexi.support.EditorActionManager.threads(new com.jmonkey.office.lexi.support.editors.FRThread(file) {
            public void run() {
                // ===============================
                java.io.BufferedInputStream bis = null;
                try {
                    bis = new java.io.BufferedInputStream(new java.io.FileInputStream(this.file));
                    ((javax.swing.text.StyledEditorKit) (_EDITOR.getEditorKit())).read(bis, _EDITOR.getStyledDocument(), _EDITOR.getStyledDocument().getLength());
                    setChanged(true);
                } catch (javax.swing.text.BadLocationException ble0) {
                    // Code.failed(ble0);
                    // throw new IOException(ble0.getMessage());
                } catch (java.io.FileNotFoundException fnfe0) {
                    // Code.failed(fnfe0);
                    // throw new IOException(fnfe0.getMessage());
                } catch (java.io.IOException ioe0) {
                    // Code.failed(ioe0);
                    // throw ioe0;
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (java.lang.Throwable t) {
                            // ignore it.
                            // not the best solution
                            // but it will do for now.
                        }
                    }
                }
                // ===============================
            }
        });
    }

    /**
     *
     * @param start
     * 		int
     * @param length
     * 		int
     * @param wordsOnly
     * 		boolean expand or
     * 		contract length to match the nearest
     * 		whole word.
     */
    public void documentSetSelection(int start, int length, boolean wordsOnly) {
        try {
            _EDITOR.getCaret().setDot(wordsOnly ? javax.swing.text.Utilities.getWordStart(_EDITOR, start) : start);
            _EDITOR.getCaret().moveDot(wordsOnly ? javax.swing.text.Utilities.getWordEnd(_EDITOR, length) : length);
        } catch (javax.swing.text.BadLocationException ble0) {
            // what should we do here?
        }
    }

    /**
     * Returns the content type as a MIME string.
     *
     * @return java.lang.String
     */
    public final java.lang.String getContentType() {
        return _EDITOR.getContentType();
    }

    public javax.swing.text.Element getCurrentParagraph() {
        return _CURRENT_PARAGRAPH;
    }

    public javax.swing.text.Element getCurrentRun() {
        return _CURRENT_RUN;
    }

    /**
     * Returns the content type as a MIME string.
     *
     * @return java.lang.String
     */
    private com.jmonkey.office.lexi.support.editors.HTMLEditor.E getEventListener() {
        if (_EVENT_LISTENER == null) {
            _EVENT_LISTENER = new com.jmonkey.office.lexi.support.editors.HTMLEditor.E(this);
        }
        return _EVENT_LISTENER;
    }

    /**
     * Returns the content type as a MIME string.
     *
     * @return java.lang.String
     */
    public final java.lang.String[] getFileExtensions() {
        return com.jmonkey.office.lexi.support.editors.HTMLEditor.FILE_EXTENSIONS;
    }

    public javax.swing.text.MutableAttributeSet getInputAttributes() {
        return _EDITOR.getInputAttributes();// getCharacterAttributes();

    }

    public javax.swing.JEditorPane getTextComponent() {
        return _EDITOR;
    }

    public void hasBeenActivated(com.jmonkey.office.lexi.support.Editor editor) {
        if (editor == this) {
            // Code.debug("hasBeenActivated");
            com.jmonkey.office.lexi.support.EditorActionManager.enableFormatActions(true);
            com.jmonkey.office.lexi.support.EditorActionManager.enableGenericActions(true);
            com.jmonkey.office.lexi.support.EditorActionManager.enableDocumentActions(true);
            // Test the state of the contained file.
            if (this.hasFile()) {
                if (this.isNew()) {
                    com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_REVERT_ACTION_PREFIX, false);
                    com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVE_ACTION_PREFIX, true);
                } else if (this.isChanged()) {
                    com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_REVERT_ACTION_PREFIX, true);
                    com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVE_ACTION_PREFIX, true);
                } else {
                    com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_REVERT_ACTION_PREFIX, true);
                    com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVE_ACTION_PREFIX, false);
                }
            } else {
                com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVE_ACTION_PREFIX, true);
                com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_REVERT_ACTION_PREFIX, false);
            }
            // Enable/disable redo
            com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.REDO_ACTION_PREFIX, getUndoManager().canRedo());
            // Enable/disable undo
            com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.UNDO_ACTION_PREFIX, getUndoManager().canUndo());
        }
    }

    public void hasBeenDeactivated(com.jmonkey.office.lexi.support.Editor editor) {
        if (editor == this) {
            // Code.debug("hasBeenDeactivated");
            // ActionManager.enableFormatActions(false);
        }
    }

    public void init() {
        // JPanel contentPane = new JPanel();
        this.setLayout(new java.awt.BorderLayout());
        this.getRegistry();
        // Editor Setup
        javax.swing.JScrollPane sp = new javax.swing.JScrollPane();
        _EDITOR = new javax.swing.JTextPane();
        _EDITOR.setContentType(com.jmonkey.office.lexi.support.editors.HTMLEditor.CONTENT_TYPE);// set to plain text.

        _EDITOR.setCaretColor(java.awt.Color.black);
        _EDITOR.getCaret().setBlinkRate(300);
        // Event Listeners
        _EDITOR.addFocusListener(this.getEventListener());
        _EDITOR.getDocument().addDocumentListener(this.getEventListener());
        _EDITOR.getDocument().addUndoableEditListener(this.getUndoManager());
        _EDITOR.addMouseListener(this);
        _EDITOR.addKeyListener(this);
        // this should be settable...
        _EDITOR.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
        // finalize init
        sp.setViewportView(_EDITOR);
        this.add(sp, java.awt.BorderLayout.CENTER);
    }

    public void insert(java.io.File file, int position) throws java.io.IOException {
        com.jmonkey.office.lexi.support.EditorActionManager.threads(new com.jmonkey.office.lexi.support.editors.FRThread(file) {
            public void run() {
                // ===============================
                java.io.BufferedInputStream bis = null;
                try {
                    bis = new java.io.BufferedInputStream(new java.io.FileInputStream(this.file));
                    ((javax.swing.text.StyledEditorKit) (_EDITOR.getEditorKit())).read(bis, _EDITOR.getStyledDocument(), this.position);
                    setChanged(true);
                } catch (javax.swing.text.BadLocationException ble0) {
                    // Code.failed(ble0);
                    // throw new IOException(ble0.getMessage());
                } catch (java.io.FileNotFoundException fnfe0) {
                    // Code.failed(fnfe0);
                    // throw new IOException(fnfe0.getMessage());
                } catch (java.io.IOException ioe0) {
                    // Code.failed(ioe0);
                    // throw ioe0;
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (java.lang.Throwable t) {
                            // ignore it.
                            // not the best solution
                            // but it will do for now.
                        }
                    }
                }
                // ===============================
            }
        });
    }

    /**
     * Has the document changed since we loaded/created it?
     *
     * @return boolean
     */
    public final boolean isChanged() {
        return _CHANGED;
    }

    /**
     * Does the document contain any data?
     *
     * @return boolean
     */
    public final boolean isEmpty() {
        // I think this will need some work...
        // The document object most likely will add
        // some hidden char at the begining of the
        // document, depending on the content type.
        // however, we don't want to include those
        // as not empty.
        return !(_EDITOR.getText().length() > 0);
    }

    /**
     * Does the document contain formatting, or
     * can we write it as plain text without
     * loosing anything.
     *
     * @return boolean
     */
    public final boolean isFormatted() {
        // Again, the easy way out...
        // How can this be improved?
        return false;
    }

    /**
     * Does the document represent a new file?
     *
     * @return boolean
     */
    public final boolean isNew() {
        if (_FILE != null) {
            return !(_FILE.exists() && _FILE.isFile());
        } else {
            return true;
        }
    }

    public void keyPressed(java.awt.event.KeyEvent kp) {
        if (kp.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
            java.lang.System.out.println("Caret Position: " + _EDITOR.getCaretPosition());
            _EDITOR.setCaretPosition(_EDITOR.getCaretPosition() + 5);
        }
    }

    public void keyReleased(java.awt.event.KeyEvent kr) {
    }

    public void keyTyped(java.awt.event.KeyEvent kt) {
    }

    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (javax.swing.SwingUtilities.isRightMouseButton(e) == true) {
            javax.swing.JPopupMenu popUP = this.getPopup();
            popUP.show(this, e.getX(), e.getY());
        }
    }

    public void mouseEntered(java.awt.event.MouseEvent e) {
    }

    public void mouseExited(java.awt.event.MouseEvent e) {
    }

    public void mousePressed(java.awt.event.MouseEvent e) {
    }

    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    public void read(java.io.File file) throws java.io.IOException {
        com.jmonkey.office.lexi.support.EditorActionManager.threads(new com.jmonkey.office.lexi.support.editors.FRThread(file) {
            public void run() {
                // ===============================
                java.io.BufferedInputStream bis = null;
                try {
                    bis = new java.io.BufferedInputStream(new java.io.FileInputStream(this.file));
                    ((javax.swing.text.StyledEditorKit) (_EDITOR.getEditorKit())).read(bis, _EDITOR.getStyledDocument(), 0);
                    setChanged(false);
                } catch (javax.swing.text.BadLocationException ble0) {
                    // Code.failed(ble0);
                    // throw new IOException(ble0.getMessage());
                } catch (java.io.FileNotFoundException fnfe0) {
                    // Code.failed(fnfe0);
                    // throw new IOException(fnfe0.getMessage());
                } catch (java.io.IOException ioe0) {
                    // Code.failed(ioe0);
                    // throw ioe0;
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (java.lang.Throwable t) {
                            // ignore it.
                            // not the best solution
                            // but it will do for now.
                        }
                    }
                }
                // ===============================
            }
        });
    }

    /* Gives focus to the editor */
    public void requestFocus() {
        _EDITOR.requestFocus();
    }

    /**
     * Sets the blink rate of the Caret.
     *
     * @param colour
     * 		java.awt.Color
     */
    public final void setCaretBlinkRate(int rate) {
        _EDITOR.getCaret().setBlinkRate(rate);
    }

    /**
     * Sets the colour of the Caret.
     *
     * @param colour
     * 		java.awt.Color
     */
    public final void setCaretColor(java.awt.Color colour) {
        _EDITOR.setCaretColor(colour);
    }

    /**
     * Set the document changed flag.
     *
     * @param changed
     * 		boolean
     */
    public final void setChanged(boolean changed) {
        _CHANGED = changed;
        this.hasBeenActivated(this);
    }

    public void setCurrentParagraph(javax.swing.text.Element paragraph) {
        _CURRENT_PARAGRAPH = paragraph;
    }

    public void setCurrentRun(javax.swing.text.Element run) {
        _CURRENT_RUN = run;
    }

    /**
     * Sets the selection colour.
     *
     * @param colour
     * 		java.awt.Color
     */
    public final void setSelectionColor(java.awt.Color colour) {
        _EDITOR.setSelectionColor(colour);
    }

    public void write(java.io.File file) throws java.io.IOException {
        com.jmonkey.office.lexi.support.EditorActionManager.threads(new com.jmonkey.office.lexi.support.editors.FWThread(file) {
            public void run() {
                java.io.BufferedOutputStream bos = null;
                try {
                    bos = new java.io.BufferedOutputStream(new java.io.FileOutputStream(this.file));
                    ((javax.swing.text.StyledEditorKit) (_EDITOR.getEditorKit())).write(bos, _EDITOR.getStyledDocument(), 0, _EDITOR.getStyledDocument().getLength());
                    setChanged(false);
                } catch (javax.swing.text.BadLocationException ble0) {
                    // Code.failed(ble0);
                    // throw new IOException(ble0.getMessage());
                } catch (java.io.IOException ioe0) {
                    // Code.failed(ioe0);
                    // throw ioe0;
                } finally {
                    if (bos != null) {
                        try {
                            bos.flush();
                            bos.close();
                        } catch (java.lang.Throwable t) {
                            // ignore it.
                            // not the best solution
                            // but it will do for now.
                        }
                    }
                }
            }
        });
    }
}