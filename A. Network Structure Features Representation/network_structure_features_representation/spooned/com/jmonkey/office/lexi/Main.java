/* This is the Main.java of the Main word processor.
This is the main application
 */
package com.jmonkey.office.lexi;
import com.jmonkey.office.lexi.support.Editor;
public class Main extends javax.swing.JFrame implements java.awt.event.ActionListener {
    // used by the document frame..
    // its up here because internal
    // classes can't have static members.
    private static int COUNTER = 0;

    private static int _FILE_HISTROY_COUNT = 0;

    private static com.jmonkey.office.lexi.Main.MainDesktop _DESKTOP = null;

    private static javax.swing.JFileChooser _FILE_CHOOSER = null;

    private static javax.swing.JLabel _STATUS_LABEL = null;

    private static javax.swing.JToolBar _FILE_TOOL_BAR = null;

    private static javax.swing.JToolBar _FORMAT_TOOL_BAR = null;

    private static javax.swing.filechooser.FileFilter[] _FILE_FILTERS = null;

    private static int _DOCUMENT_COUNT = 0;

    // Action listener for File Histroy items only.
    protected final java.awt.event.ActionListener _FILE_HISTROY_ACTION = new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            // Code.debug("History: " + e.getActionCommand());
            try {
                getDesktopManager().editorOpen(new java.io.File(e.getActionCommand()));
            } catch (java.lang.Throwable t) {
                // Code.failed("Open History File: " + e.getActionCommand());
            }
        }
    };

    // Action listener for Open Window items only.
    protected final java.awt.event.ActionListener _OPEN_WINDOW_ACTION = new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            // Code.debug("Open Window: " + e.getActionCommand());
            // activate the window here...
            getDesktopManager().activateFrame(((com.jmonkey.office.lexi.Main.DocumentManager) (getDesktopManager())).getOpenDocument(e.getActionCommand()));
        }
    };

    // ====================================================
    // Use the Registry to store the previously opened files
    private com.jmonkey.export.Registry _REGISTRY = null;

    // The apps menu bar.
    private javax.swing.JMenuBar _MENU_BAR = null;

    // File Histroy menu.
    protected javax.swing.JMenu _FILE_HISTORY = null;

    // Open document list menu.
    private javax.swing.JMenu _OPEN_WINDOWS = null;

    /**
     * DocumentFrame.class  provides the internal frame for our application.
     *
     * @see javax.swing.event.InternalFrameEvent
     * @see java.awt.event.FocusEvent
     * @author Matthew Schmidt
     */
    protected final class DocumentFrame extends javax.swing.JInternalFrame implements javax.swing.event.InternalFrameListener , java.awt.event.FocusListener , java.beans.VetoableChangeListener {
        private com.jmonkey.office.lexi.Main _APP = null;

        // This can't be static, or all instances
        // will use the same StyledDocument.
        private com.jmonkey.office.lexi.support.Editor _EDITOR;

        private javax.swing.JLabel STATUS = null;

        private boolean _VETO = true;

        private int fileLen;

        private javax.swing.JFileChooser chooser;

        public DocumentFrame(com.jmonkey.office.lexi.Main app, java.lang.String contentType) {
            super();
            _APP = app;
            this.setFrameIcon(new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("text_window16.gif")));
            this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            // DISPOSE_ON_CLOSE
            this.setIconifiable(true);
            this.setMaximizable(true);
            this.setResizable(true);
            this.setClosable(true);
            this.addInternalFrameListener(this);
            this.addFocusListener(this);
            this.addVetoableChangeListener(this);
            _EDITOR = com.jmonkey.office.lexi.support.Editor.getEditorForContentType(contentType);
            // We need to check focus on the editor as well,
            // so the frame comes to the front when the editor
            // is clicked, as well as when the frame is clicked.
            _EDITOR.addFocusListener(this);
            this.setContentPane(_EDITOR);
            // Added... just to make it
            // get focus when first opened...
            _EDITOR.activate();// requestFocus();

            // _EDITOR.requestFocus();
            // this.activate();
        }

        public java.lang.String getName() {
            return this.getTitle();
        }

        /**
         * Returns the editor of the DocumentFrame.
         *
         * @return com.jmonkey.common.StyledEditor
         */
        protected com.jmonkey.office.lexi.support.Editor getEditor() {
            return _EDITOR;
        }

        public void vetoableChange(java.beans.PropertyChangeEvent evt) throws java.beans.PropertyVetoException {
            // //Code.event(evt);
            // //Code.message(evt.getPropertyName());
            if (evt.getPropertyName().equals("closed")) {
                if (_VETO) {
                    if (!_APP.getDesktopManager().closeActiveDocument()) {
                        throw new java.beans.PropertyVetoException("closed", evt);
                    }
                    _VETO = !_VETO;
                } else {
                    _VETO = !_VETO;
                }
            }
        }

        public void internalFrameOpened(javax.swing.event.InternalFrameEvent e) {
        }

        /**
         * Should Handle saving whatever is in the editor.
         */
        public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
            try {
                // Code.event(e); //paramString()
                // Code.message(e.paramString());
                // ((DocumentManager)_APP.getDesktopManager())
                // this.setClosed(_APP.getDesktopManager().closeActiveDocument());
                this.dispose();
            } catch (java.lang.NullPointerException nullp) {
                return;
            }
            // } catch  (java.beans.PropertyVetoException propV) {
            // return;
            // }
        }

        public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
        }

        public void internalFrameIconified(javax.swing.event.InternalFrameEvent e) {
        }

        public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent e) {
        }

        /**
         * Calls this.activate() to make sure the editor receives focus along with the frame
         */
        public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
            this.activate();
        }

        public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent e) {
        }

        public void focusGained(java.awt.event.FocusEvent e) {
            activate();
            if (!e.isTemporary()) {
                this.activate();
            }
        }

        public void focusLost(java.awt.event.FocusEvent e) {
        }

        /**
         * Activates this frame and makes sure the
         * editor gets focused along with the frame
         */
        public void activate() {
            this.moveToFront();
            try {
                this.setSelected(true);
            } catch (java.beans.PropertyVetoException pve0) {
            }
            _EDITOR.activate();// requestFocus();

            // _EDITOR.requestFocus();
            // Added this... no need to activate
            // this frame if its already active.
            if (((com.jmonkey.office.lexi.Main.DocumentManager) (_APP.getDesktopManager())).active() != this) {
                _APP.getDesktopManager().activateFrame(this);
            }
        }
    }

    /**
     * Keeps a list of the active frames.
     */
    protected final class DocumentManager extends javax.swing.DefaultDesktopManager implements javax.swing.DesktopManager , com.jmonkey.office.lexi.support.FileActionListener {
        private com.jmonkey.office.lexi.Main _PARENT = null;

        private java.util.Vector _DOC_LIST = null;

        private com.jmonkey.office.lexi.Main.DocumentFrame _CUR_DOC = null;

        /**
         * DocumentManager constructor comment.
         */
        public DocumentManager(com.jmonkey.office.lexi.Main parent) {
            super();
            _PARENT = parent;
            this.init();
        }

        public void editorNew() {
            // Code.debug("editorNew");
            this.createDocumentFrame();
        }

        public void editorOpen(java.io.File file) {
            // Code.debug("editorOpen(File)");
            if (file != null) {
                java.lang.String mime = com.jmonkey.office.lexi.support.Mime.findContentType(file);
                try {
                    this.createDocumentFrame(null, file.getName(), mime).getEditor().read(file);
                    _PARENT.addToFileHistory(file);
                } catch (java.io.IOException ioe0) {
                    javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        public void editorOpen() {
            // Code.debug("editorOpen");
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setDialogTitle(_PARENT.getRegistry().getString("MAIN", "dialog.open.title", "Open File..."));
            chooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
            chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
            javax.swing.filechooser.FileFilter filter = null;
            filter = new com.jmonkey.office.lexi.Main.DynamicFileFilter("java c cc cpp h txt text", "Plain Text Format (*.txt, *.text)");
            chooser.addChoosableFileFilter(filter);
            filter = new com.jmonkey.office.lexi.Main.DynamicFileFilter("rtf", "Rich Text Format (*.rtf)");
            chooser.addChoosableFileFilter(filter);
            filter = new com.jmonkey.office.lexi.Main.DynamicFileFilter("htm html shtml", "Hypertext Format (*.html, *.htm)");
            chooser.addChoosableFileFilter(filter);
            filter = chooser.getAcceptAllFileFilter();
            chooser.addChoosableFileFilter(filter);
            // Set the current directory to the default document directory.
            chooser.setCurrentDirectory(new java.io.File(com.jmonkey.export.Runtime.ensureDirectory(_PARENT.getRegistry().getString("USER", "default.documents.directory", (java.lang.System.getProperty("user.home") + java.io.File.separator) + "documents"))));
            // chooser.setSelectedFile(new File("*." + _PARENT.getRegistry().getString("default.filefilter.ext", "txt")));
            chooser.showOpenDialog(_PARENT);// showDialog(this, null);

            java.io.File fileToOpen = chooser.getSelectedFile();
            if (fileToOpen != null) {
                java.lang.String mime = com.jmonkey.office.lexi.support.Mime.findContentType(fileToOpen);
                try {
                    this.createDocumentFrame(null, fileToOpen.getName(), mime).getEditor().read(fileToOpen);
                    _PARENT.addToFileHistory(fileToOpen);
                } catch (java.io.IOException ioe0) {
                    javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        public void editorOpenAs() {
            // Code.debug("editorOpenAs");
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setDialogTitle(_PARENT.getRegistry().getString("MAIN", "dialog.openas.title", "Open File As..."));
            chooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
            chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
            javax.swing.filechooser.FileFilter filter = null;
            filter = new com.jmonkey.office.lexi.Main.DynamicFileFilter("*", "Any File As Plain Text (*.*)");
            chooser.addChoosableFileFilter(filter);
            filter = new com.jmonkey.office.lexi.Main.DynamicFileFilter("*", "Any File As Rich Text (*.rtf)");
            chooser.addChoosableFileFilter(filter);
            filter = new com.jmonkey.office.lexi.Main.DynamicFileFilter("*", "Any File As Hypertext (*.html)");
            chooser.addChoosableFileFilter(filter);
            filter = chooser.getAcceptAllFileFilter();
            chooser.addChoosableFileFilter(filter);
            // _PARENT.getFileChooser().setFileFilter(_PARENT.getFileFilters()[3]);
            // Set the current directory to the default document directory.
            chooser.setCurrentDirectory(new java.io.File(com.jmonkey.export.Runtime.ensureDirectory(_PARENT.getRegistry().getString("USER", "default.documents.directory", (java.lang.System.getProperty("user.home") + java.io.File.separator) + "documents"))));
            // _PARENT.getFileChooser().setSelectedFile(new File(""));
            chooser.showOpenDialog(_PARENT);// showDialog(this, null);

            java.io.File fileToOpen = chooser.getSelectedFile();
            if (fileToOpen != null) {
                java.lang.String desc = chooser.getFileFilter().getDescription();
                java.lang.String mime = "text/plain";
                if (desc.startsWith("Any File As Plain Text (*.*)")) {
                    mime = "text/plain";
                } else if (desc.startsWith("Any File As Rich Text (*.rtf)")) {
                    mime = "text/rtf";
                } else if (desc.startsWith("Any File As Hypertext (*.html)")) {
                    mime = "text/html";
                } else {
                    mime = "text/plain";
                }
                try {
                    this.createDocumentFrame(null, fileToOpen.getName(), mime).getEditor().read(fileToOpen);
                    // Add the opened file to the histroy menu.
                    _PARENT.addToFileHistory(fileToOpen);
                } catch (java.io.IOException ioe0) {
                    javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        public void editorRevert(com.jmonkey.office.lexi.support.Editor editor) {
            // Code.debug("editorRevert");
            if (editor.getFile() == null) {
                javax.swing.JOptionPane.showMessageDialog(_PARENT, _PARENT.getRegistry().getString("MAIN", "dialog.revert.warning.0", "The document has not yet\nbeen saved you must\nsave the file before\nyou can revert to\nthe saved version."), "Bad State", javax.swing.JOptionPane.ERROR_MESSAGE);
            } else {
                switch (javax.swing.JOptionPane.showConfirmDialog(getParent(), _PARENT.getRegistry().getString("MAIN", "dialog.revert.warning.1", "You are about to revert to a\nsaved version of this document.\nAll changes will be lost.\nAre you sure you want\nto do this?"), "Revert to Saved?", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE)) {
                    case javax.swing.JOptionPane.YES_OPTION :
                        if (!editor.isEmpty()) {
                            // first clear the contents of the file.
                            editor.getTextComponent().setText("");
                        }
                        try {
                            editor.read(editor.getFile());
                        } catch (java.io.IOException ioe0) {
                            javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case javax.swing.JOptionPane.NO_OPTION :
                    case javax.swing.JOptionPane.CANCEL_OPTION :
                    case javax.swing.JOptionPane.CLOSED_OPTION :
                        break;
                }
            }
        }

        public void editorSave(com.jmonkey.office.lexi.support.Editor editor) {
            // Code.debug("editorSave");
            if (editor.getFile() == null) {
                javax.swing.JFileChooser chooser = new javax.swing.JFileChooser(_PARENT.getRegistry().getString("MAIN", "dialog.saveas.title", "Save File As..."));
                chooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
                chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
                // Set the current directory to the default document directory.
                chooser.setCurrentDirectory(new java.io.File(com.jmonkey.export.Runtime.ensureDirectory(_PARENT.getRegistry().getString("USER", "default.documents.directory", (java.lang.System.getProperty("user.home") + java.io.File.separator) + "documents"))));
                // _PARENT.getFileChooser().setSelectedFile(null);
                // FileFilter filter = null;
                java.lang.String ct = editor.getContentType();
                java.lang.String defaultExt = "*.*";
                if (ct.equals("text/rtf")) {
                    chooser.setFileFilter(new com.jmonkey.office.lexi.Main.DynamicFileFilter("rtf", "Rich Text Format (*.rtf)"));
                    defaultExt = "*.rtf";
                } else if (ct.equals("text/html")) {
                    chooser.setFileFilter(new com.jmonkey.office.lexi.Main.DynamicFileFilter("htm,html,shtml", "Hypertext Format (*.html, *.htm)"));
                    defaultExt = "*.html";
                } else if (ct.equals("text/plain")) {
                    chooser.setFileFilter(new com.jmonkey.office.lexi.Main.DynamicFileFilter("java c cc cpp h txt text", "Plain Text Format (*.txt, *.text)"));
                    defaultExt = "*.txt";
                } else {
                    chooser.setFileFilter(chooser.getAcceptAllFileFilter());
                    defaultExt = "*.*";
                }
                // chooser.addChoosableFileFilter(filter);
                // Set the default file name.
                chooser.setSelectedFile(new java.io.File(defaultExt));
                chooser.showSaveDialog(_PARENT);
                java.io.File fileToOpen = chooser.getSelectedFile();
                // if(!fileToOpen.getName().equals(defaultExt)){
                java.lang.String mime = com.jmonkey.office.lexi.support.Mime.findContentType(fileToOpen);
                if (!fileToOpen.getName().startsWith("*")) {
                    if (!ct.equals(mime)) {
                        switch (javax.swing.JOptionPane.showConfirmDialog(_PARENT, _PARENT.getRegistry().getString("MAIN", "dialog.save.warning.0", "The extension of the file you specified\ndoes not match the content type of\nthe document. If you use this extension,\nyou may have trouble reopening the file\nat a later time.\nAre you sure you want\nto do this?"), "Extension Mismatch?", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE)) {
                            case javax.swing.JOptionPane.YES_OPTION :
                                try {
                                    editor.write(fileToOpen);
                                    editor.setFile(fileToOpen);
                                } catch (java.io.IOException ioe0) {
                                    javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            case javax.swing.JOptionPane.NO_OPTION :
                            case javax.swing.JOptionPane.CANCEL_OPTION :
                            case javax.swing.JOptionPane.CLOSED_OPTION :
                                break;
                        }
                    } else {
                        try {
                            editor.write(fileToOpen);
                            editor.setFile(fileToOpen);
                        } catch (java.io.IOException ioe0) {
                            javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    // Can't start a file with a *
                }
            } else if (editor.isNew() || editor.isChanged()) {
                try {
                    editor.write(editor.getFile());
                } catch (java.io.IOException ioe0) {
                    javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        public void editorSaveAs(com.jmonkey.office.lexi.support.Editor editor) {
            // Code.debug("editorSaveAs");
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setDialogTitle(_PARENT.getRegistry().getString("MAIN", "dialog.saveas.title", "Save File As..."));
            chooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
            chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
            // Set the current directory to the default document directory.
            chooser.setCurrentDirectory(new java.io.File(com.jmonkey.export.Runtime.ensureDirectory(_PARENT.getRegistry().getString("USER", "default.documents.directory", (java.lang.System.getProperty("user.home") + java.io.File.separator) + "documents"))));
            // _PARENT.getFileChooser().setSelectedFile(null);
            java.lang.String ct = editor.getContentType();
            java.lang.String defaultExt = "*.*";
            if (ct.equals("text/rtf")) {
                chooser.setFileFilter(new com.jmonkey.office.lexi.Main.DynamicFileFilter("rtf", "Rich Text Format (*.rtf)"));
                defaultExt = "*.rtf";
            } else if (ct.equals("text/html")) {
                chooser.setFileFilter(new com.jmonkey.office.lexi.Main.DynamicFileFilter("htm,html,shtml", "Hypertext Format (*.html, *.htm)"));
                defaultExt = "*.html";
            } else if (ct.equals("text/plain")) {
                chooser.setFileFilter(new com.jmonkey.office.lexi.Main.DynamicFileFilter("java c cc cpp h txt text", "Plain Text Format (*.txt, *.text)"));
                defaultExt = "*.txt";
            } else {
                chooser.setFileFilter(chooser.getAcceptAllFileFilter());
                defaultExt = "*.*";
            }
            // Set the default file name.
            chooser.setSelectedFile(new java.io.File(defaultExt));
            chooser.showSaveDialog(_PARENT);
            java.io.File fileToOpen = chooser.getSelectedFile();
            java.lang.String mime = com.jmonkey.office.lexi.support.Mime.findContentType(fileToOpen);
            if (!fileToOpen.getName().startsWith("*")) {
                if (!ct.equals(mime)) {
                    switch (javax.swing.JOptionPane.showConfirmDialog(_PARENT, _PARENT.getRegistry().getString("MAIN", "dialog.save.warning.0", "The extension of the file you specified\ndoes not match the content type of\nthe document. If you use this extension,\nyou may have trouble reopening the file\nat a later time.\nAre you sure you want\nto do this?"), "Extension Mismatch?", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE)) {
                        case javax.swing.JOptionPane.YES_OPTION :
                            try {
                                editor.write(fileToOpen);
                                editor.setFile(fileToOpen);
                            } catch (java.io.IOException ioe0) {
                                javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case javax.swing.JOptionPane.NO_OPTION :
                        case javax.swing.JOptionPane.CANCEL_OPTION :
                        case javax.swing.JOptionPane.CLOSED_OPTION :
                            break;
                    }
                } else {
                    try {
                        editor.write(fileToOpen);
                        editor.setFile(fileToOpen);
                    } catch (java.io.IOException ioe0) {
                        javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        public void editorSaveCopy(com.jmonkey.office.lexi.support.Editor editor) {
            // Code.debug("editorSaveCopy");
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setDialogTitle(_PARENT.getRegistry().getString("MAIN", "dialog.savecopy.title", "Save Copy As..."));
            chooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
            chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
            // Set the current directory to the default document directory.
            chooser.setCurrentDirectory(new java.io.File(com.jmonkey.export.Runtime.ensureDirectory(_PARENT.getRegistry().getString("USER", "default.documents.directory", (java.lang.System.getProperty("user.home") + java.io.File.separator) + "documents"))));
            // _PARENT.getFileChooser().setSelectedFile(null);
            java.lang.String ct = editor.getContentType();
            java.lang.String defaultExt = "*.*";
            if (ct.equals("text/rtf")) {
                chooser.setFileFilter(new com.jmonkey.office.lexi.Main.DynamicFileFilter("rtf", "Rich Text Format (*.rtf)"));
                defaultExt = "*.rtf";
            } else if (ct.equals("text/html")) {
                chooser.setFileFilter(new com.jmonkey.office.lexi.Main.DynamicFileFilter("htm,html,shtml", "Hypertext Format (*.html, *.htm)"));
                defaultExt = "*.html";
            } else if (ct.equals("text/plain")) {
                chooser.setFileFilter(new com.jmonkey.office.lexi.Main.DynamicFileFilter("java c cc cpp h txt text", "Plain Text Format (*.txt, *.text)"));
                defaultExt = "*.txt";
            } else {
                chooser.setFileFilter(chooser.getAcceptAllFileFilter());
                defaultExt = "*.*";
            }
            // Set the default file name.
            chooser.setSelectedFile(new java.io.File(defaultExt));
            chooser.showSaveDialog(_PARENT);
            java.io.File fileToOpen = chooser.getSelectedFile();
            java.lang.String mime = com.jmonkey.office.lexi.support.Mime.findContentType(fileToOpen);
            if (!fileToOpen.getName().startsWith("*")) {
                if (!ct.equals(mime)) {
                    switch (javax.swing.JOptionPane.showConfirmDialog(_PARENT, _PARENT.getRegistry().getString("MAIN", "dialog.save.warning.0", "The extension of the file you specified\ndoes not match the content type of\nthe document. If you use this extension,\nyou may have trouble reopening the file\nat a later time.\nAre you sure you want\nto do this?"), "Extension Mismatch?", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE)) {
                        case javax.swing.JOptionPane.YES_OPTION :
                            try {
                                editor.write(fileToOpen);
                                // editor.setFile(fileToOpen);
                            } catch (java.io.IOException ioe0) {
                                javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case javax.swing.JOptionPane.NO_OPTION :
                        case javax.swing.JOptionPane.CANCEL_OPTION :
                        case javax.swing.JOptionPane.CLOSED_OPTION :
                            break;
                    }
                } else {
                    try {
                        editor.write(fileToOpen);
                        // editor.setFile(fileToOpen);
                    } catch (java.io.IOException ioe0) {
                        javax.swing.JOptionPane.showMessageDialog(_PARENT, "Exception\n" + ioe0.getMessage(), "Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        /**
         * Creates a new instance of DocumentFrame
         * I changed this to create a generic frame,
         * This way, we can remove all the frame
         * creation code from every method that
         * might need a new frame. Sets the frame's
         * title to the name of the opened file.
         *
         * @param newName
         * 		java.lang.String
         * @param contentType
         * 		java.lang.String
         * @return com.jmonkey.office.lexi.Main.DocumentFrame
         */
        protected final com.jmonkey.office.lexi.Main.DocumentFrame createDocumentFrame(java.io.File file, java.lang.String newName, java.lang.String contentType) {
            // Code.debug("Creating New Document: " + newName);
            com.jmonkey.office.lexi.Main.DocumentFrame doc = new com.jmonkey.office.lexi.Main.DocumentFrame(_PARENT, contentType);
            doc.setIconifiable(true);
            doc.setResizable(true);
            doc.setMaximizable(true);
            doc.setTitle(newName);
            doc.setVisible(true);
            _PARENT._DESKTOP.add(doc, doc.getName());
            this.cascade(doc);
            _CUR_DOC = doc;
            _CUR_DOC.activate();
            _PARENT.updateOpenWindowsMenu();
            if (file != null) {
                if (file.exists() && file.isFile()) {
                    this.editorOpen(file);
                }
            }
            return doc;
        }

        /**
         * Creates a new document with the speified file.
         */
        protected final com.jmonkey.office.lexi.Main.DocumentFrame createDocumentFrame(java.io.File file) {
            return this.createDocumentFrame(file, _PARENT.getRegistry().getString("MAIN", "new.document.title", "New Document") + _PARENT.getDocumentNumber(), _PARENT.getRegistry().getString("MAIN", "default.content.type", com.jmonkey.office.lexi.support.Editor.VALID_CONTENT_TYPES[2]));
        }

        /**
         * Same as above, only keeps the title as New Document
         */
        protected final com.jmonkey.office.lexi.Main.DocumentFrame createDocumentFrame(java.lang.String contentType) {
            // Removed the code here
            // so we don't duplicate
            // the code from the above
            // method. -- Brill 03/18/1999
            return this.createDocumentFrame(null, _PARENT.getRegistry().getString("MAIN", "new.document.title", "New Document") + _PARENT.getDocumentNumber(), contentType);
        }

        /**
         * Same as above, only keeps the title as New Document
         */
        protected final com.jmonkey.office.lexi.Main.DocumentFrame createDocumentFrame() {
            // Removed the code here
            // so we don't duplicate
            // the code from the above
            // method. -- Brill 03/18/1999
            return /* _PARENT.getRegistry().getString("MAIN", "new.document.title", "New Document ") + _PARENT.getDocumentNumber() */
            this.createDocumentFrame(null, "New Document " + (_PARENT.getDocumentNumber() + 1), _PARENT.getRegistry().getString("MAIN", "default.content.type", com.jmonkey.office.lexi.support.Editor.VALID_CONTENT_TYPES[2]));
        }

        /**
         * Returns a list of open documents in the system.
         *
         * @return java.lang.String[]
         */
        public final java.lang.String[] openDocumentList() {
            // Code.debug("Getting Open Document List...");
            java.util.Vector v = new java.util.Vector();
            java.awt.Component[] comps = _PARENT._DESKTOP.getComponents();
            for (int i = 0; i < comps.length; i++) {
                try {
                    if (comps[i] instanceof javax.swing.JInternalFrame) {
                        v.addElement(((javax.swing.JInternalFrame) (comps[i])).getTitle());
                    }
                } catch (java.lang.ClassCastException cEX) {
                }
            }
            java.lang.String[] names = new java.lang.String[v.size()];
            v.copyInto(names);
            return names;
        }

        /**
         * Returns the named DocumentFrame. The frame may or may not be active.
         *
         * @return com.jmonkey.office.Main.DocumentFrame
         */
        public final com.jmonkey.office.lexi.Main.DocumentFrame getOpenDocument(java.lang.String name) {
            java.awt.Component[] comps = _PARENT._DESKTOP.getComponents();
            for (int i = 0; i < comps.length; i++) {
                if (comps[i] instanceof javax.swing.JInternalFrame) {
                    if (((javax.swing.JInternalFrame) (comps[i])).getTitle().equals(name)) {
                        return ((com.jmonkey.office.lexi.Main.DocumentFrame) ((javax.swing.JInternalFrame) (comps[i])));
                    }
                }
            }
            throw new java.lang.IllegalStateException(("The document " + name) + " does not exist, or no longer exists.");
        }

        /**
         * activateFrame sets our current active frame.
         */
        public void activateFrame(javax.swing.JInternalFrame f) {
            _CUR_DOC = ((com.jmonkey.office.lexi.Main.DocumentFrame) (f));
            if (_PARENT != null) {
                // _PARENT.setTitle("[" + f.getTitle() + (_CUR_DOC.getEditor().isChanged() ? "] *" : "]"));
                this.documentChanged(_CUR_DOC, false);
            }
            super.activateFrame(f);
            _CUR_DOC.activate();
        }

        /**
         * Returns the current active frame.
         *
         * @return com.jmonkey.office.Main.DocumentFrame
         */
        protected final com.jmonkey.office.lexi.Main.DocumentFrame active() {
            return _CUR_DOC;
        }

        /**
         * Returns which application is the parent for this
         *
         * @return com.jmonkey.office.Main
         */
        protected final com.jmonkey.office.lexi.Main getApp() {
            return _PARENT;
        }

        /**
         * Starts off this whole mess
         */
        private void init() {
            _DOC_LIST = new java.util.Vector();
        }

        protected final void documentChanged(com.jmonkey.office.lexi.Main.DocumentFrame frame, boolean textSelected) {
            _PARENT.a_documentFrameChanged(frame, textSelected);
            // System.out.println("FrameChanged: " + frame.getTitle());
        }

        /**
         * This method needs documentation.
         *
         * @param dframe
         * 		com.jm.wp.DocumentFrame
         */
        protected final void cascade(com.jmonkey.office.lexi.Main.DocumentFrame dframe) {
            java.awt.Dimension dsize = _PARENT._DESKTOP.getSize();
            int targetWidth = (3 * dsize.width) / 4;
            int targetHeight = (3 * dsize.height) / 4;
            int nextX = 0;
            int nextY = 0;
            if (_CUR_DOC != null) {
                if (_CUR_DOC.isMaximum()) {
                    try {
                        dframe.setMaximum(true);
                    } catch (java.beans.PropertyVetoException pve0) {
                    }
                    return;
                }
                java.awt.Point p = _CUR_DOC.getLocation();
                nextX = p.x;
                nextY = p.y;
                // If the active frame is near the edge,
                // then we should cascade the new frame.
                nextX += 24;
                nextY += 24;
            }
            // Make sure we're not 'out of bounds'.
            if (((nextX + targetWidth) > dsize.width) || ((nextY + targetHeight) > dsize.height)) {
                nextX = 0;
                nextY = 0;
            }
            _PARENT._DESKTOP.getDesktopManager().setBoundsForFrame(dframe, nextX, nextY, targetWidth, targetHeight);
        }

        /**
         * Show/Hide format toolbar action.
         *
         * @version 1.0 Revision 0
         * @author Brill Pappin 21-APR-1999
         */
        protected final class CascadeAction extends javax.swing.AbstractAction {
            public CascadeAction() {
                super("Cascade Windows");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                cascadeAll();
            }
        }

        protected final javax.swing.Action getCascadeAction() {
            return new com.jmonkey.office.lexi.Main.DocumentManager.CascadeAction();
        }

        /**
         * Cascade Windows
         */
        protected final void cascadeAll() {
            java.awt.Component[] comps = _PARENT._DESKTOP.getComponents();
            java.awt.Dimension dsize = _PARENT._DESKTOP.getSize();
            int targetWidth = (3 * dsize.width) / 4;
            int targetHeight = (3 * dsize.height) / 4;
            int nextX = 0;
            int nextY = 0;
            for (int i = 0; i < comps.length; i++) {
                if (((comps[i] instanceof javax.swing.JInternalFrame) && comps[i].isVisible()) && (!((javax.swing.JInternalFrame) (comps[i])).isIcon())) {
                    if (((nextX + targetWidth) > dsize.width) || ((nextY + targetHeight) > dsize.height)) {
                        nextX = 0;
                        nextY = 0;
                    }
                    _PARENT._DESKTOP.getDesktopManager().setBoundsForFrame(((javax.swing.JComponent) (comps[i])), nextX, nextY, targetWidth, targetHeight);
                    ((javax.swing.JInternalFrame) (comps[i])).toFront();
                    nextX += 24;
                    nextY += 24;
                }
            }
        }

        /**
         * Action to close the active docunment.
         *
         * @version 1.0 Revision 0
         * @author Brill Pappin 21-APR-1999
         */
        protected final class CloseAction extends javax.swing.AbstractAction {
            public CloseAction() {
                super("Close");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                closeActiveDocument();
            }
        }

        protected final javax.swing.Action getCloseAction() {
            return new com.jmonkey.office.lexi.Main.DocumentManager.CloseAction();
        }

        /**
         * Close the current document.
         */
        protected final boolean closeActiveDocument() {
            if (this.active().getEditor().isChanged()) {
                switch (javax.swing.JOptionPane.showConfirmDialog(_PARENT, ("Document Changed!\n\"" + this.active().getTitle()) + "\"\nDo you want to save the changes?", "Save Changes?", javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE)) {
                    case javax.swing.JOptionPane.YES_OPTION :
                        if (this.active().getEditor().isNew()) {
                            this.editorSaveAs(this.active().getEditor());
                        } else {
                            this.editorSave(this.active().getEditor());
                        }
                        // this.active().setClosed(true);
                        return !this.active().getEditor().isChanged();
                    case javax.swing.JOptionPane.NO_OPTION :
                        // this.active().setClosed(true);
                        return true;
                    case javax.swing.JOptionPane.CANCEL_OPTION :
                    case javax.swing.JOptionPane.CLOSED_OPTION :
                    default :
                        return false;
                }
            } else {
                // this.editorSave(this.active().getEditor());
                return !this.active().getEditor().isChanged();
            }
        }

        /**
         * Action to close all the docunments.
         *
         * @version 1.0 Revision 0
         * @author Brill Pappin 21-APR-1999
         */
        protected final class CloseAllAction extends javax.swing.AbstractAction {
            public CloseAllAction() {
                super("Close All");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                closeAllDocuments();
            }
        }

        protected final javax.swing.Action getCloseAllAction() {
            return new com.jmonkey.office.lexi.Main.DocumentManager.CloseAllAction();
        }

        /**
         * Close all document frames
         * taken from the old JWord
         * code, and need porting.
         */
        protected final void closeAllDocuments() {
            java.awt.Component[] comps = _PARENT._DESKTOP.getComponents();
            for (int i = 0; i < comps.length; i++) {
                if (((comps[i] instanceof com.jmonkey.office.lexi.Main.DocumentFrame)/* JInternalFrame */
                 && comps[i].isVisible()) && ((javax.swing.JInternalFrame) (comps[i])).isClosable()) {
                    com.jmonkey.office.lexi.Main.DocumentFrame actOnDoc = ((com.jmonkey.office.lexi.Main.DocumentFrame) (comps[i]));
                    actOnDoc.activate();
                    try {
                        actOnDoc.setClosed(true);
                    } catch (java.lang.Throwable t) {
                    }
                    // this.closeActiveDocument();
                    /* if(actOnDoc.getEditor().isChanged()) {
                    switch (JOptionPane.showConfirmDialog(_PARENT, "Document Changed!\n\"" + actOnDoc.getTitle() + "\"\nDo you want to save the changes?", "Save Changes?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE)) {
                    case JOptionPane.YES_OPTION :

                    if(actOnDoc.getEditor().isNew()) {
                    this.editorSaveAs(actOnDoc.getEditor());
                    } else {
                    this.editorSave(actOnDoc.getEditor());
                    }
                    //return !actOnDoc.getEditor().isChanged();
                    case JOptionPane.NO_OPTION :
                    //return true;
                    case JOptionPane.CANCEL_OPTION :
                    case JOptionPane.CLOSED_OPTION :
                    default:
                    //return false;
                    }
                    } else {
                    this.editorSave(actOnDoc.getEditor());
                    //return !actOnDoc.getEditor().isChanged();
                    }
                     */
                }
            }
            // MISTAKE... DONT SET TO NULL, UNLESS WE KNOW ALL FRAMES ARE DEAD, OR WE SELECT ANOTHER FRAME.
            _CUR_DOC = null;
        }

        /**
         * Show/Hide format toolbar action.
         *
         * @version 1.0 Revision 0
         * @author Brill Pappin 21-APR-1999
         */
        protected final class MinimizeAction extends javax.swing.AbstractAction {
            public MinimizeAction() {
                super("Minimize Windows");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                minimizeAll();
            }
        }

        protected final javax.swing.Action getMinimizeAction() {
            return new com.jmonkey.office.lexi.Main.DocumentManager.MinimizeAction();
        }

        /**
         * Minimize all.
         */
        protected final void minimizeAll() {
            java.awt.Component[] comps = _PARENT._DESKTOP.getComponents();
            for (int i = 0; i < comps.length; i++) {
                if ((((comps[i] instanceof javax.swing.JInternalFrame) && comps[i].isVisible()) && (!((javax.swing.JInternalFrame) (comps[i])).isIcon())) && ((javax.swing.JInternalFrame) (comps[i])).isIconifiable()) {
                    try {
                        ((javax.swing.JInternalFrame) (comps[i])).setIcon(true);
                    } catch (java.beans.PropertyVetoException pve0) {
                    }
                }
            }
        }

        /**
         * Show/Hide format toolbar action.
         *
         * @version 1.0 Revision 0
         * @author Brill Pappin 21-APR-1999
         */
        protected final class TileAction extends javax.swing.AbstractAction {
            public TileAction() {
                super("Tile Windows");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                tileAll();
            }
        }

        protected final javax.swing.Action getTileAction() {
            return new com.jmonkey.office.lexi.Main.DocumentManager.TileAction();
        }

        /**
         * Tile Windows
         */
        protected final void tileAll() {
            if (_PARENT._DESKTOP.getDesktopManager() == null) {
                // No desktop manager - do nothing
                return;
            }
            java.awt.Component[] comps = _PARENT._DESKTOP.getComponents();
            java.awt.Component comp;
            int count = 0;
            // Count and handle only the internal frames
            for (int i = 0; i < comps.length; i++) {
                comp = comps[i];
                if (((comp instanceof javax.swing.JInternalFrame) && comp.isVisible()) && (!((javax.swing.JInternalFrame) (comp)).isIcon())) {
                    count++;
                }
            }
            if (count != 0) {
                double root = java.lang.Math.sqrt(((double) (count)));
                int rows = ((int) (root));
                int columns = count / rows;
                int spares = count - (columns * rows);
                java.awt.Dimension paneSize = _PARENT._DESKTOP.getSize();
                int columnWidth = paneSize.width / columns;
                // We leave some space at the bottom that doesn't get covered
                int availableHeight = paneSize.height - 48;
                int mainHeight = availableHeight / rows;
                int smallerHeight = availableHeight / (rows + 1);
                int rowHeight = mainHeight;
                int x = 0;
                int y = 0;
                int thisRow = rows;
                int normalColumns = columns - spares;
                for (int i = comps.length - 1; i >= 0; i--) {
                    comp = comps[i];
                    if (((comp instanceof javax.swing.JInternalFrame) && comp.isVisible()) && (!((javax.swing.JInternalFrame) (comp)).isIcon())) {
                        _PARENT._DESKTOP.getDesktopManager().setBoundsForFrame(((javax.swing.JComponent) (comp)), x, y, columnWidth, rowHeight);
                        y += rowHeight;
                        if ((--thisRow) == 0) {
                            // Filled the row
                            y = 0;
                            x += columnWidth;
                            // Switch to smaller rows if necessary
                            if ((--normalColumns) <= 0) {
                                thisRow = rows + 1;
                                rowHeight = smallerHeight;
                            } else {
                                thisRow = rows;
                            }
                        }
                    }
                }
            }
        }
    }

    protected final class MainDesktop extends javax.swing.JDesktopPane implements javax.swing.Scrollable , java.awt.event.AdjustmentListener {
        private volatile java.lang.Thread doublebufferthread;

        java.awt.Image i = getToolkit().getImage("images/gui.gif");

        public MainDesktop() {
            // setBackground(Color.black);
            super();
            // updateUI();
        }

        public java.awt.Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        public int getScrollableUnitIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
            switch (orientation) {
                case javax.swing.SwingConstants.VERTICAL :
                    return visibleRect.height / 10;
                case javax.swing.SwingConstants.HORIZONTAL :
                    return visibleRect.width / 10;
                default :
                    throw new java.lang.IllegalArgumentException("Invalid orientation: " + orientation);
            }
        }

        public int getScrollableBlockIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
            switch (orientation) {
                case javax.swing.SwingConstants.VERTICAL :
                    return visibleRect.height;
                case javax.swing.SwingConstants.HORIZONTAL :
                    return visibleRect.width;
                default :
                    throw new java.lang.IllegalArgumentException("Invalid orientation: " + orientation);
            }
        }

        public boolean getScrollableTracksViewportWidth() {
            return false;
        }

        public boolean getScrollableTracksViewportHeight() {
            return false;
        }

        // End Scroll Methods
        // Adjustment Changes
        public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
        }
    }

    /**
     * Look & Feel Event Listener. Changes the
     * plaf of the application when
     * requested.
     *
     * @version 1.0 Revision 0
     * @author Brill Pappin 21-APR-1999
     */
    private final class LAL implements java.awt.event.ActionListener {
        private com.jmonkey.office.lexi.Main _APP = null;

        private LAL(com.jmonkey.office.lexi.Main app) {
            _APP = app;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // Set the look & feel for the app.
            try {
                javax.swing.UIManager.setLookAndFeel(e.getActionCommand());
                getRegistry().setProperty("MAIN", "main.look&feel", e.getActionCommand());
                // Tell the system to update the UI.
                javax.swing.SwingUtilities.updateComponentTreeUI(_APP);
            } catch (java.lang.Exception lafe) {
            }
        }
    }

    /**
     * A Dynamic FileFilter. The filter can take its
     * extensions as a space delimited list of extensions.
     * <LI> new DynamicFileFilter("txt text java cpp", "Text File (*.txt)");
     *
     * @version 1.0 Revision 1
     * @author Brill Pappin
     */
    protected final class DynamicFileFilter extends javax.swing.filechooser.FileFilter {
        private java.lang.String extension = "*";

        private java.lang.String description = "All Files (*.*)";

        public DynamicFileFilter(java.lang.String ext, java.lang.String desc) {
            this.extension = ext;
            this.description = desc;
        }

        public DynamicFileFilter(java.lang.String ext) {
            this.extension = ext;
            this.description = ((com.jmonkey.office.lexi.support.Mime.findContentType(ext) + " (*.") + ext) + ")";
        }

        public DynamicFileFilter() {
        }

        public boolean accept(java.io.File f) {
            if (f.isFile()) {
                if (extension.equals("*")) {
                    return true;
                } else {
                    // //Code.debug("Acctept Extension: " + f.getName().substring((f.getName().lastIndexOf(".") + 1), f.getName().length()));
                    return extension.indexOf(f.getName().substring(f.getName().lastIndexOf(".") + 1, f.getName().length())) > (-1);
                }
            } else {
                return true;
            }
        }

        public java.lang.String getDescription() {
            return this.description;
        }
    }

    /**
     * Execute an action to start the help program
     */
    protected final class HELPAction extends javax.swing.AbstractAction {
        java.lang.String help = null;

        public HELPAction(java.lang.String helpFile) {
            super("Help...");
            help = helpFile;
        }

        public HELPAction() {
            super("Help...");
            help = "lexi";
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            com.jmonkey.office.help.OfficeHelp helper = new com.jmonkey.office.help.OfficeHelp(help);
            helper.setSize(500, 500);
            helper.setVisible(true);
        }
    }

    /**
     * Checks to see if there is an update to Main available
     */
    protected final class UpdateAction extends javax.swing.AbstractAction {
        public UpdateAction() {
            super("Check for Update..");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // int whatEver = 1;
            // UpdateTester updater = new UpdateTester(getMain(), whatEver, "test.jar");
            java.lang.System.out.println("Not implemented...");
        }
    }

    /**
     * Show/Hide format toolbar action.
     *
     * @version 1.0 Revision 0
     * @author Brill Pappin 21-APR-1999
     */
    protected final class SFTAction extends javax.swing.AbstractAction {
        private com.jmonkey.office.lexi.Main _LISTENER = null;

        public SFTAction(com.jmonkey.office.lexi.Main app) {
            super("Show Format Toolbar");
            _LISTENER = app;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() instanceof javax.swing.JCheckBoxMenuItem) {
                _LISTENER.getFormatToolBar().setVisible(!_LISTENER.getFormatToolBar().isVisible());
                ((javax.swing.JCheckBoxMenuItem) (e.getSource())).setState(_LISTENER.getFormatToolBar().isVisible());
            }
        }
    }

    /**
     * Show/Hide file toolbar action.
     *
     * @version 1.0 Revision 0
     * @author Brill Pappin 21-APR-1999
     */
    protected final class SLTAction extends javax.swing.AbstractAction {
        private com.jmonkey.office.lexi.Main _LISTENER = null;

        public SLTAction(com.jmonkey.office.lexi.Main app) {
            super("Show File Toolbar");
            _LISTENER = app;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() instanceof javax.swing.JCheckBoxMenuItem) {
                _LISTENER.getFileToolBar().setVisible(!_LISTENER.getFileToolBar().isVisible());
                ((javax.swing.JCheckBoxMenuItem) (e.getSource())).setState(_LISTENER.getFileToolBar().isVisible());
            }
        }
    }

    /**
     * Coloured list item Renderer.
     *
     * @version 1.0 Revision 0
     * @author Brill Pappin 21-APR-1999
     */
    protected class ColourActionCellRenderer extends javax.swing.JLabel implements javax.swing.ListCellRenderer {
        public ColourActionCellRenderer() {
            setOpaque(true);
        }

        public java.awt.Component getListCellRendererComponent(javax.swing.JList list, java.lang.Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                this.setMinimumSize(new java.awt.Dimension(0, 16));
                java.awt.Color colour = java.awt.Color.decode(getRegistry().getString("COLOURS", ((java.lang.String) (value)), null));
                this.setText(((java.lang.String) (value)));
                this.setIcon(new com.jmonkey.office.lexi.Main.ColourActionCellRenderer.ColourIcon(colour));
                if (isSelected) {
                    this.setBackground(list.getSelectionBackground());
                    this.setForeground(list.getSelectionForeground());
                } else {
                    this.setBackground(list.getBackground());
                    this.setForeground(list.getForeground());
                }
                return this;
            } else {
                return new javax.swing.JLabel("VALUE IS NULL");
            }
        }

        /**
         * Icon Renderer
         *
         * @version 1.0 Revision 0
         * @author Brill Pappin 21-APR-1999
         */
        protected final class ColourIcon implements javax.swing.Icon , java.io.Serializable {
            private transient java.awt.Color _COLOUR = null;

            private transient java.awt.Image _IMAGE = null;

            protected ColourIcon(java.awt.Color colour) {
                super();
                _COLOUR = colour;
            }

            public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
                // Color background = c.getBackground();
                if (_IMAGE == null) {
                    _IMAGE = c.createImage(this.getIconWidth(), this.getIconHeight());
                    java.awt.Graphics imageG = _IMAGE.getGraphics();
                    this.paintImage(c, imageG, _COLOUR);
                }
                g.drawImage(_IMAGE, x, y, null);
            }

            private void paintImage(java.awt.Component c, java.awt.Graphics g, java.awt.Color colour) {
                g.setColor(colour);
                g.fillRect(0, 0, this.getIconWidth(), this.getIconHeight());
                g.setColor(java.awt.Color.black);
                g.drawRect(0, 0, this.getIconWidth() - 1, this.getIconHeight() - 1);
            }

            public int getIconWidth() {
                return 16;
            }

            public int getIconHeight() {
                return 16;
            }
        }
    }

    /**
     * Font list item Renderer.
     *
     * @version 1.0 Revision 0
     * @author Brill Pappin 21-APR-1999
     */
    protected class FontActionCellRenderer extends javax.swing.JLabel implements javax.swing.ListCellRenderer {
        public FontActionCellRenderer() {
            setOpaque(true);
        }

        public java.awt.Component getListCellRendererComponent(javax.swing.JList list, java.lang.Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                this.setMinimumSize(new java.awt.Dimension(0, 16));
                java.awt.Font thisFont = new java.awt.Font(((java.lang.String) (value)), java.awt.Font.PLAIN, 12);
                // //Code.message("Font PostScript Name: " + thisFont.getPSName());
                this.setFont(thisFont);
                this.setText(((java.lang.String) (value)));
                // setIcon((ImageIcon)a.getValue(Action.SMALL_ICON));
                if (isSelected) {
                    this.setBackground(list.getSelectionBackground());
                    this.setForeground(list.getSelectionForeground());
                } else {
                    this.setBackground(list.getBackground());
                    this.setForeground(list.getForeground());
                }
                return this;
            } else {
                return new javax.swing.JLabel("VALUE IS NULL");
            }
        }
    }

    /**
     * Font size list item Renderer.
     *
     * @version 1.0 Revision 0
     * @author Brill Pappin 21-APR-1999
     */
    protected class FSActionCellRenderer extends javax.swing.JLabel implements javax.swing.ListCellRenderer {
        public FSActionCellRenderer() {
            setOpaque(true);
        }

        public java.awt.Component getListCellRendererComponent(javax.swing.JList list, java.lang.Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                this.setText(((java.lang.String) (value)));
                this.setMinimumSize(new java.awt.Dimension(0, 16));
                // setIcon((ImageIcon)a.getValue(Action.SMALL_ICON));
                if (isSelected) {
                    this.setBackground(list.getSelectionBackground());
                    this.setForeground(list.getSelectionForeground());
                } else {
                    this.setBackground(list.getBackground());
                    this.setForeground(list.getForeground());
                }
                return this;
            } else {
                return new javax.swing.JLabel("VALUE IS NULL");
            }
        }
    }

    // PropertySheetDialog
    protected final class UserPropertyAction extends javax.swing.AbstractAction {
        public UserPropertyAction() {
            super("User Options...");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // PropertySheetDialog.display(getMain(), (Properties)getUserRegistry());
            com.jmonkey.office.lexi.Main.Options opts = new com.jmonkey.office.lexi.Main.Options(getMain(), "Main Options", true);
            opts.setVisible(true);
            getMain().repaint();
        }
    }

    protected final class PopupPropertyAction extends javax.swing.AbstractAction {
        public PopupPropertyAction() {
            super("Popup Options....");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            com.jmonkey.office.lexi.support.PropertySheetDialog.display(getMain(), ((java.util.Properties) (getRegistry().referenceGroup("POPUP"))));
            getMain().repaint();
        }
    }

    protected final class MainPropertyAction extends javax.swing.AbstractAction {
        public MainPropertyAction() {
            super("Main Options...");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            com.jmonkey.office.lexi.support.PropertySheetDialog.display(getMain(), ((java.util.Properties) (getRegistry().referenceGroup("MAIN"))));
            getMain().repaint();
        }
    }

    protected final class ColourPropertyAction extends javax.swing.AbstractAction {
        public ColourPropertyAction() {
            super("Default Colours...");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // PropertySheetDialog.display(getMain(), (Properties)getColourRegistry(), true);
            // ColourPropertySheet psd =
            // new ColourPropertySheet(
            // getMain(),
            // getRegistry().referenceGroup("COLOURS"),
            // true);
            getMain().repaint();
        }
    }

    protected final class FontPropertyAction extends javax.swing.AbstractAction {
        public FontPropertyAction() {
            super("Default Fonts...");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // FontPropertySheet.display(getMain(), getFontRegistry());
            // FontPropertySheet psd =
            // new FontPropertySheet(
            // getMain(),
            // getRegistry().referenceGroup("FONTS"),
            // false);
            getMain().repaint();
        }
    }

    protected final class QuitAction extends javax.swing.AbstractAction {
        public QuitAction() {
            super("Quit");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            doExit();
        }
    }

    /**
     * Start a print job.
     *
     * @version 0.1 Revision 0
     * @author Brill Pappin 21-APR-1999
     */
    protected final class PrintAction extends javax.swing.AbstractAction {
        public PrintAction() {
            super("Print...");
            this.setEnabled(getRegistry().getBoolean("MAIN", "print.document.enabled", false));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // Code.event(e);
            javax.swing.JOptionPane.showMessageDialog(getMain(), "Print Not Implemented!", "Not Implemented!", javax.swing.JOptionPane.WARNING_MESSAGE);
            // Use java.awt.print.PrinterJob instead.
            // What are the properties it wants?
            // PrintJob job = Toolkit.getDefaultToolkit().getPrintJob(getMain(), getMain().getTitle(), null /*Properties props*/);
            // //Code.message("Got Print Job: " + job);
        }
    }

    protected final class FontPropertySheet extends javax.swing.JDialog {
        private java.util.Properties _P = null;

        private java.awt.Frame _PARENT = null;

        private boolean _ALLOW_ADD = false;

        private com.jmonkey.office.lexi.Main.FontPropertySheet.PairTableModel _MODEL = null;

        private FontPropertySheet(java.awt.Frame parent, java.util.Properties p, boolean allowAdd) {
            super(parent);
            this._PARENT = parent;
            this._P = p;
            this._ALLOW_ADD = allowAdd;
            this.init();
            this.pack();
            this.setLocationRelativeTo(parent);
            this.setVisible(true);
        }

        private void doExit() {
            this.dispose();
        }

        private void init() {
            javax.swing.JPanel content = new javax.swing.JPanel();
            content.setLayout(new java.awt.BorderLayout());
            javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
            buttonPanel.setLayout(new java.awt.BorderLayout());
            javax.swing.JPanel spacerPanel = new javax.swing.JPanel();
            spacerPanel.setLayout(new java.awt.GridLayout());
            if (_ALLOW_ADD) {
                javax.swing.JButton addButton = new javax.swing.JButton("Add Key");
                addButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        java.lang.String inputValue = javax.swing.JOptionPane.showInputDialog("What is the key you want to add?");
                        if (inputValue != null) {
                            if (inputValue.trim().length() > 0) {
                                _P.setProperty(inputValue, "");
                                // redraw the table
                                if (_MODEL != null) {
                                    _MODEL.fireTableDataChanged();
                                }
                            }
                        }
                    }
                });
                spacerPanel.add(addButton);
            }
            javax.swing.JButton closeButton = new javax.swing.JButton("Close");
            closeButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    doExit();
                }
            });
            spacerPanel.add(closeButton);
            buttonPanel.add(spacerPanel, java.awt.BorderLayout.EAST);
            content.add(buttonPanel, java.awt.BorderLayout.SOUTH);
            _MODEL = new com.jmonkey.office.lexi.Main.FontPropertySheet.PairTableModel();
            javax.swing.JTable tbl = new javax.swing.JTable(_MODEL);
            content.add(new javax.swing.JScrollPane(tbl), java.awt.BorderLayout.CENTER);
            tbl.getColumnModel().getColumn(1).setPreferredWidth(5);
            this.setContentPane(content);
            // Added this to dispose of
            // the main app window when
            // it gets closed.
            this.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    doExit();
                }
            });
        }

        protected final java.util.Properties getProperties() {
            return _P;
        }

        private final class PairTableModel extends javax.swing.table.AbstractTableModel {
            public PairTableModel() {
                super();
            }

            public int getRowCount() {
                return getProperties().size();
            }

            public int getColumnCount() {
                return 2;
            }

            public java.lang.String getColumnName(int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        return "Font Face";
                    case 1 :
                        return "Show/Hide";
                    default :
                        return null;
                }
            }

            public java.lang.Class getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        return java.lang.String.class;
                    case 1 :
                        return java.lang.Boolean.class;
                    default :
                        return java.lang.String.class;
                }
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        return false;
                    case 1 :
                        return true;
                    default :
                        return false;
                }
            }

            public java.lang.Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        return getProperties().keySet().toArray()[rowIndex].toString();
                    case 1 :
                        return new java.lang.Boolean(getProperties().getProperty(getProperties().keySet().toArray()[rowIndex].toString()));
                    default :
                        return "";
                }
            }

            public void setValueAt(java.lang.Object aValue, int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        // getProperties().keySet().toArray()[rowIndex] = aValue.toString();
                        break;
                    case 1 :
                        getProperties().setProperty(getProperties().keySet().toArray()[rowIndex].toString(), aValue.toString());
                        break;
                }
            }
        }
    }

    protected final class ColourPropertySheet extends javax.swing.JDialog {
        private java.util.Properties _P = null;

        private java.awt.Frame _PARENT = null;

        private boolean _ALLOW_ADD = false;

        private com.jmonkey.office.lexi.Main.ColourPropertySheet.PairTableModel _MODEL = null;

        private ColourPropertySheet(java.awt.Frame parent, java.util.Properties p, boolean allowAdd) {
            super(parent);
            this._PARENT = parent;
            this._P = p;
            this._ALLOW_ADD = allowAdd;
            this.init();
            this.pack();
            this.setLocationRelativeTo(parent);
            this.setVisible(true);
        }

        private void doExit() {
            this.dispose();
        }

        private void init() {
            javax.swing.JPanel content = new javax.swing.JPanel();
            content.setLayout(new java.awt.BorderLayout());
            javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
            buttonPanel.setLayout(new java.awt.BorderLayout());
            javax.swing.JPanel spacerPanel = new javax.swing.JPanel();
            spacerPanel.setLayout(new java.awt.GridLayout());
            if (_ALLOW_ADD) {
                javax.swing.JButton addButton = new javax.swing.JButton("Add Colour");
                addButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        java.lang.String inputValue = javax.swing.JOptionPane.showInputDialog("What is the name of the\ncolour you want to add?");
                        if (inputValue != null) {
                            if (inputValue.trim().length() > 0) {
                                try {
                                    _P.setProperty(inputValue, com.jmonkey.export.Format.colorToHex(javax.swing.JColorChooser.showDialog(getMain(), "Choose a colour...", null)));
                                } catch (java.lang.Throwable t) {
                                    // the colour chooser was most likely
                                    // canceled, so ignore the exception.
                                }
                                // redraw the table
                                if (_MODEL != null) {
                                    _MODEL.fireTableDataChanged();
                                }
                            }
                        }
                    }
                });
                spacerPanel.add(addButton);
            }
            javax.swing.JButton closeButton = new javax.swing.JButton("Close");
            closeButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    doExit();
                }
            });
            spacerPanel.add(closeButton);
            buttonPanel.add(spacerPanel, java.awt.BorderLayout.EAST);
            content.add(buttonPanel, java.awt.BorderLayout.SOUTH);
            _MODEL = new com.jmonkey.office.lexi.Main.ColourPropertySheet.PairTableModel();
            javax.swing.JTable tbl = new javax.swing.JTable(_MODEL);
            content.add(new javax.swing.JScrollPane(tbl), java.awt.BorderLayout.CENTER);
            tbl.getColumnModel().getColumn(1).setPreferredWidth(5);
            tbl.getColumnModel().getColumn(1).setCellRenderer(new com.jmonkey.office.lexi.Main.ColourPropertySheet.ColourCellRenderer());
            this.setContentPane(content);
            // Added this to dispose of
            // the main app window when
            // it gets closed.
            this.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    doExit();
                }
            });
        }

        protected final java.util.Properties getProperties() {
            return _P;
        }

        // private final class ColourCellEditor extends DefaultCellEditor{
        // }
        private final class ColourCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
            private final java.awt.Color defaultBackground = this.getBackground();

            private final java.awt.Color defaultForeground = this.getForeground();

            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // System.out.println(this.toString());
                this.setValue(value);
                if ((!isSelected) && (column == 1)) {
                    try {
                        java.awt.Color c = com.jmonkey.export.Format.hexToColor(((java.lang.String) (value)));
                        this.setBackground(c);
                        this.setForeground(com.jmonkey.export.Runtime.getContrastingTextColor(c));
                    } catch (java.lang.Throwable t) {
                        // Ignore this, its just a bad colour.
                        java.awt.Color c = java.awt.Color.black;
                        this.setBackground(c);
                        this.setForeground(com.jmonkey.export.Runtime.getContrastingTextColor(c));
                        this.setValue("#000000");
                    }
                } else {
                    this.setBackground(defaultBackground);
                    this.setForeground(defaultForeground);
                }
                return this;
            }
        }

        private final class PairTableModel extends javax.swing.table.AbstractTableModel {
            public PairTableModel() {
                super();
            }

            public int getRowCount() {
                return getProperties().size();
            }

            public int getColumnCount() {
                return 2;
            }

            public java.lang.String getColumnName(int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        return "Colour Name";
                    case 1 :
                        return "RGB Hex";
                    default :
                        return null;
                }
            }

            public java.lang.Class getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        return java.lang.String.class;
                    case 1 :
                        return java.lang.String.class;
                    default :
                        return java.lang.String.class;
                }
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        return false;
                    case 1 :
                        return true;
                    default :
                        return false;
                }
            }

            public java.lang.Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        return getProperties().keySet().toArray()[rowIndex].toString();
                    case 1 :
                        return getProperties().getProperty(getProperties().keySet().toArray()[rowIndex].toString());
                    default :
                        return "";
                }
            }

            public void setValueAt(java.lang.Object aValue, int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0 :
                        // getProperties().keySet().toArray()[rowIndex] = aValue.toString();
                        break;
                    case 1 :
                        getProperties().setProperty(getProperties().keySet().toArray()[rowIndex].toString(), aValue.toString());
                        break;
                }
            }
        }
    }

    protected final class Options extends javax.swing.JDialog {
        protected Options(java.awt.Frame owner, java.lang.String title, boolean modal) {
            super(owner, title, modal);
            this.init();
            this.setLocationRelativeTo(owner);
        }

        private void init() {
            javax.swing.JPanel content = new javax.swing.JPanel();
            content.setLayout(new java.awt.BorderLayout());
            javax.swing.JTabbedPane jtp = new javax.swing.JTabbedPane();
            content.add(jtp, java.awt.BorderLayout.CENTER);
            jtp.addTab("Paths", null, new com.jmonkey.office.lexi.Main.PathsSheet(getMain()), "Changed the paths that Main uses.");
            this.setContentPane(content);
            this.setSize(200, 200);
            // Added this to dispose of
            // the main app window when
            // it gets closed.
            this.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    doExit();
                }
            });
        }

        private void doExit() {
            this.dispose();
        }
    }

    protected final class PathsSheet extends javax.swing.JPanel {
        private java.awt.Frame _PARENT = null;

        private javax.swing.JTextField user_home_text = new javax.swing.JTextField();

        private javax.swing.JTextField user_temp_text = new javax.swing.JTextField();

        private javax.swing.JTextField user_document_text = new javax.swing.JTextField();

        protected PathsSheet(java.awt.Frame parent) {
            super();
            _PARENT = parent;
            this.init();
        }

        private void init() {
            this.setLayout(new java.awt.GridLayout(3, 1));
            this.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
            javax.swing.JButton user_home_button = new javax.swing.JButton("...");
            javax.swing.JButton user_temp_button = new javax.swing.JButton("...");
            javax.swing.JButton user_document_button = new javax.swing.JButton("...");
            javax.swing.JPanel user_home_spacer = new javax.swing.JPanel();
            javax.swing.JPanel user_temp_spacer = new javax.swing.JPanel();
            javax.swing.JPanel user_document_spacer = new javax.swing.JPanel();
            user_home_text.setText(getMain().getRegistry().getString("USER", "user.home", null));
            user_temp_text.setText(getMain().getRegistry().getString("USER", "temp.directory", null));
            user_document_text.setText(getMain().getRegistry().getString("USER", "default.documents.directory", null));
            javax.swing.event.DocumentListener listener = new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                    this.updateRegistry();
                }

                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                    this.updateRegistry();
                }

                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                    this.updateRegistry();
                }

                public void updateRegistry() {
                    getMain().getRegistry().setProperty("USER", "user.home", user_home_text.getText().trim());
                    getMain().getRegistry().setProperty("USER", "temp.directory", user_temp_text.getText().trim());
                    getMain().getRegistry().setProperty("USER", "default.documents.directory", user_document_text.getText().trim());
                }
            };
            user_home_text.getDocument().addDocumentListener(listener);
            user_temp_text.getDocument().addDocumentListener(listener);
            user_document_text.getDocument().addDocumentListener(listener);
            user_home_button.addActionListener(new com.jmonkey.office.lexi.Main.PathsSheet.HA());
            user_temp_button.addActionListener(new com.jmonkey.office.lexi.Main.PathsSheet.TA());
            user_document_button.addActionListener(new com.jmonkey.office.lexi.Main.PathsSheet.DA());
            user_home_spacer.add(user_home_button, java.awt.BorderLayout.EAST);
            user_temp_spacer.add(user_temp_button, java.awt.BorderLayout.EAST);
            user_document_spacer.add(user_document_button, java.awt.BorderLayout.EAST);
            user_home_spacer.add(user_home_text, java.awt.BorderLayout.CENTER);
            user_temp_spacer.add(user_temp_text, java.awt.BorderLayout.CENTER);
            user_document_spacer.add(user_document_text, java.awt.BorderLayout.CENTER);
            this.add(user_home_spacer);
            this.add(user_temp_spacer);
            this.add(user_document_spacer);
        }

        private final class HA extends javax.swing.AbstractAction {
            private HA() {
                super("...");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
                chooser.setDialogTitle("Choose Home Diretory");
                chooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
                chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
                // chooser.setCurrentDirectory(new File(FSTool.ensureDirectory(_PARENT.getUserRegistry().getString("default.documents.directory", System.getProperty("user.home") + File.separator + "documents"))));
                chooser.showOpenDialog(null);// showDialog(this, null);

                java.io.File dirChoice = chooser.getSelectedFile();
                if (dirChoice != null) {
                    user_home_text.setText(dirChoice.getAbsolutePath());
                    getMain().getRegistry().setProperty("USER", "user.home", dirChoice.getAbsolutePath());
                }
            }
        }

        private final class TA extends javax.swing.AbstractAction {
            private TA() {
                super("...");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
                chooser.setDialogTitle("Choose Temp Diretory");
                chooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
                chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
                // chooser.setCurrentDirectory(new File(FSTool.ensureDirectory(_PARENT.getUserRegistry().getString("default.documents.directory", System.getProperty("user.home") + File.separator + "documents"))));
                chooser.showOpenDialog(null);// showDialog(this, null);

                java.io.File dirChoice = chooser.getSelectedFile();
                if (dirChoice != null) {
                    user_temp_text.setText(dirChoice.getAbsolutePath());
                    getMain().getRegistry().setProperty("USER", "temp.directory", dirChoice.getAbsolutePath());
                }
            }
        }

        private final class DA extends javax.swing.AbstractAction {
            private DA() {
                super("...");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
                chooser.setDialogTitle("Choose Documents Diretory");
                chooser.setDialogType(javax.swing.JFileChooser.OPEN_DIALOG);
                chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
                // chooser.setCurrentDirectory(new File(FSTool.ensureDirectory(_PARENT.getUserRegistry().getString("default.documents.directory", System.getProperty("user.home") + File.separator + "documents"))));
                chooser.showOpenDialog(null);// showDialog(this, null);

                java.io.File dirChoice = chooser.getSelectedFile();
                if (dirChoice != null) {
                    user_document_text.setText(dirChoice.getAbsolutePath());
                    getMain().getRegistry().setProperty("USER", "default.documents.directory", dirChoice.getAbsolutePath());
                }
            }
        }
    }

    // private Registry _KEY_DESC_REG = null;
    /**
     * Constructor...
     */
    public Main(java.lang.String[] args) {
        super("Main Editor");
        this.init();
        this.setVisible(true);
        if (args != null) {
            if (args.length > 0) {
                try {
                    // Open any files passed on the command line.
                    for (int i = 0; i < args.length; i++) {
                        this.getDesktopManager().createDocumentFrame(new java.io.File(args[i]));
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                    if (this.getRegistry().getBoolean("USER", "open.blank.default", true)) {
                        this.getDesktopManager().createDocumentFrame();
                    }
                }
            } else if (this.getRegistry().getBoolean("USER", "open.blank.default", true)) {
                this.getDesktopManager().createDocumentFrame();
            }
        } else if (this.getRegistry().getBoolean("USER", "open.blank.default", true)) {
            this.getDesktopManager().createDocumentFrame();
        }
        // } catch(Throwable t) {
        // System.out.println("Exit with Fatal Exception: " + t.toString());
        // t.printStackTrace(System.out);
        // }
    }

    protected final void a_documentFrameChanged(com.jmonkey.office.lexi.Main.DocumentFrame frame, boolean textSelected) {
        this.setTitle(("Main - [" + frame.getTitle()) + (frame.getEditor().isChanged() ? "] *" : "]"));
    }

    /**
     * Handles the Menu Actions
     *
     * @depricated Use an action to handle the event.
     */
    public void actionPerformed(java.awt.event.ActionEvent event) {
        // Code.failed(this, "DELETE THIS EVENT CALL (USE ACTIONS INSTEAD) - " + event.toString());
    }

    protected final void addToFileHistory(java.io.File file) {
        // Add the opened file to the histroy menu. -- brill 03/04/1999
        if (_FILE_HISTORY.getItemCount() >= this.getRegistry().getInteger("USER", "max.file.history", 5)) {
            _FILE_HISTORY.getItem(0).setText(file.getName());
            _FILE_HISTORY.getItem(0).setActionCommand(file.getAbsolutePath());
            // Code.debug("File Hist. replace: " + file.getName() + "=" + file.getAbsolutePath());
        } else {
            javax.swing.JMenuItem item = new javax.swing.JMenuItem(file.getName());
            item.setActionCommand(file.getAbsolutePath());
            item.addActionListener(_FILE_HISTROY_ACTION);
            _FILE_HISTORY.insert(item, 0);
            // Code.debug("File Hist. create: " + file.getName() + "=" + file.getAbsolutePath());
        }
    }

    /**
     * Makes a JMenuBar so that we save lines
     *
     * @return javax.swing.JMenuBar
     */
    private javax.swing.JMenuBar createMenuBar() {
        _MENU_BAR = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu("File");
        fileMenu.setMnemonic('f');
        javax.swing.JMenu editMenu = new javax.swing.JMenu("Edit");
        editMenu.setMnemonic('e');
        javax.swing.JMenu viewMenu = new javax.swing.JMenu("View");
        viewMenu.setMnemonic('i');
        javax.swing.JMenu formatMenu = new javax.swing.JMenu("Format");
        formatMenu.setMnemonic('m');
        javax.swing.JMenu windowMenu = new javax.swing.JMenu("Window");
        windowMenu.setMnemonic('w');
        javax.swing.JMenu helpMenu = new javax.swing.JMenu("Help");
        windowMenu.setMnemonic('h');
        // FILE MENU =========================
        // Special Menu item for File history.
        _FILE_HISTORY = new javax.swing.JMenu("File History");
        fileMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getNewAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
        fileMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getOpenAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
        fileMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getOpenAsAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
        fileMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getSaveAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
        fileMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getSaveAsAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
        fileMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getSaveCopyAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
        fileMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getRevertAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
        fileMenu.addSeparator();
        fileMenu.add(((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager())).getCloseAction());
        fileMenu.add(((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager())).getCloseAllAction());
        fileMenu.addSeparator();
        fileMenu.add(this.getPrintAction());
        fileMenu.addSeparator();
        fileMenu.add(_FILE_HISTORY);
        fileMenu.addSeparator();
        fileMenu.add(new com.jmonkey.office.lexi.Main.QuitAction());
        // EDIT MENU ===========================
        editMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getUndoAction());
        editMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getRedoAction());
        editMenu.addSeparator();
        editMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getCutAction());
        editMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getCopyAction());
        editMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getPasteAction());
        editMenu.addSeparator();
        editMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getSelectAllAction());
        editMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getSelectNoneAction());
        editMenu.addSeparator();
        editMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getSearchAction(this));
        editMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getReplaceAction(this));
        // VIEW MENU ===========================
        // Action listener Look & Feel only.
        java.awt.event.ActionListener lafal = new com.jmonkey.office.lexi.Main.LAL(this);
        javax.swing.JMenu LANDF = new javax.swing.JMenu("Look & Feel");
        javax.swing.ButtonGroup lafgroup = new javax.swing.ButtonGroup();
        // Testing availible LAFs.
        for (int i = 0; i < javax.swing.UIManager.getInstalledLookAndFeels().length; i++) {
            javax.swing.JCheckBoxMenuItem lafitem = new javax.swing.JCheckBoxMenuItem(javax.swing.UIManager.getInstalledLookAndFeels()[i].getName());
            lafitem.setActionCommand(javax.swing.UIManager.getInstalledLookAndFeels()[i].getClassName());
            lafitem.addActionListener(lafal);
            lafgroup.add(lafitem);
            LANDF.add(lafitem);
            if (getRegistry().getString("MAIN", "main.look&feel", null).equals(javax.swing.UIManager.getInstalledLookAndFeels()[i].getClassName())) {
                lafitem.setSelected(true);
            }
            // Code.debug(UIManager.getInstalledLookAndFeels()[i].getName() + "=" + UIManager.getInstalledLookAndFeels()[i].getClassName());
        }
        LANDF.addSeparator();
        // ===============
        // ButtonGroup toolbargroup = new ButtonGroup();
        javax.swing.JCheckBoxMenuItem showhideFileToolBar = new javax.swing.JCheckBoxMenuItem("Show File Toolbar");
        showhideFileToolBar.setActionCommand("mnu-showfiletoolbar");
        showhideFileToolBar.addActionListener(new com.jmonkey.office.lexi.Main.SLTAction(this));
        showhideFileToolBar.setState(this.getFileToolBar().isVisible());
        // toolbargroup.add(showhideFileToolBar);
        javax.swing.JCheckBoxMenuItem showhideFormatToolBar = new javax.swing.JCheckBoxMenuItem("Show Format Toolbar");
        showhideFormatToolBar.setActionCommand("mnu-showformattoolbar");
        showhideFormatToolBar.addActionListener(new com.jmonkey.office.lexi.Main.SFTAction(this));
        showhideFormatToolBar.setState(this.getFormatToolBar().isVisible());
        // toolbargroup.add(showhideFormatToolBar);
        viewMenu.add(LANDF);
        viewMenu.addSeparator();
        viewMenu.add(showhideFileToolBar);
        viewMenu.add(showhideFormatToolBar);
        viewMenu.addSeparator();
        viewMenu.add(new com.jmonkey.office.lexi.Main.MainPropertyAction());
        viewMenu.add(new com.jmonkey.office.lexi.Main.UserPropertyAction());
        viewMenu.add(new com.jmonkey.office.lexi.Main.PopupPropertyAction());
        viewMenu.addSeparator();
        viewMenu.add(new com.jmonkey.office.lexi.Main.ColourPropertyAction());
        viewMenu.add(new com.jmonkey.office.lexi.Main.FontPropertyAction());
        // viewMenu.addSeparator();
        // viewMenu.add(credits);
        // FORMAT MENU =========================
        javax.swing.JMenuItem colours = new javax.swing.JMenuItem("Colours...");
        colours.setActionCommand("mnu-colours");
        colours.addActionListener(this);
        javax.swing.JMenuItem fonts = new javax.swing.JMenuItem("Fonts...");
        fonts.setActionCommand("mnu-fonts");
        fonts.addActionListener(this);
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getAlignLeftAction());
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getAlignCenterAction());
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getAlignRightAction());
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getAlignJustifyAction());
        formatMenu.addSeparator();
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getBoldAction());
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getItalicAction());
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getUnderlineAction());
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getStrikeThroughAction());
        formatMenu.addSeparator();
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getColourChooserAction(this));
        formatMenu.add(com.jmonkey.office.lexi.support.EditorActionManager.instance().getFontChooserAction(this));
        // WINDOW MENU =========================
        javax.swing.JMenuItem cascade = new javax.swing.JMenuItem("Cascade Windows");
        cascade.setActionCommand("mnu-cascade");
        cascade.addActionListener(this);
        javax.swing.JMenuItem tile = new javax.swing.JMenuItem("Tile Windows");
        tile.setActionCommand("mnu-tile");
        tile.addActionListener(this);
        javax.swing.JMenuItem minimize = new javax.swing.JMenuItem("Minimize Windows");
        minimize.setActionCommand("mnu-minimize");
        minimize.addActionListener(this);
        _OPEN_WINDOWS = new javax.swing.JMenu("Open Windows");
        // HELP MENU ===========================
        /* JMenuItem help = new JMenuItem("Help...");
        help.setActionCommand("mnu-help");
        help.addActionListener(this);
        help.setEnabled(false);
         */
        javax.swing.JMenuItem about = new javax.swing.JMenuItem("About...");
        about.setActionCommand("mnu-about");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                javax.swing.JOptionPane.showMessageDialog(getMain(), "Main was written by:\nMatthew Schmidt & Brill Pappin\n\nCopyright 1999", "About Main...", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        });
        javax.swing.JCheckBoxMenuItem interact = new javax.swing.JCheckBoxMenuItem("Show Helper...");
        interact.setActionCommand("mnu-interact");
        // interact.addActionListener(this);
        // interact.setState(this.getHelper().isVisible());
        helpMenu.add(this.getHELPAction());
        helpMenu.add(this.getUpdateAction());
        helpMenu.addSeparator();
        helpMenu.add(about);
        windowMenu.add(((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager())).getTileAction());
        windowMenu.add(((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager())).getCascadeAction());
        windowMenu.add(((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager())).getMinimizeAction());
        windowMenu.addSeparator();
        windowMenu.add(_OPEN_WINDOWS);
        // Add all our menues..===================================.
        _MENU_BAR.add(fileMenu);
        _MENU_BAR.add(editMenu);
        _MENU_BAR.add(viewMenu);
        _MENU_BAR.add(formatMenu);
        _MENU_BAR.add(windowMenu);
        _MENU_BAR.add(helpMenu);
        return _MENU_BAR;
    }

    /**
     * Called to exit the application.
     */
    public void doExit() {
        // Store the state of the toolbars and interactive helper.
        this.getRegistry().setProperty("USER", "show.file.toolbar", "" + this.getFileToolBar().isVisible());
        this.getRegistry().setProperty("USER", "show.format.toolbar", "" + this.getFormatToolBar().isVisible());
        // this.getRegistry().putProperty("show.interact.helper", "" + this.getHelper().isVisible());
        // Store the apps last location and size.
        this.getRegistry().setProperty("MAIN", "main.window.w", "" + this.getSize().width);
        this.getRegistry().setProperty("MAIN", "main.window.h", "" + this.getSize().height);
        this.getRegistry().setProperty("MAIN", "main.window.x", "" + this.getLocation().x);
        this.getRegistry().setProperty("MAIN", "main.window.y", "" + this.getLocation().y);
        // Save the file history.
        this.getRegistry().deleteGroup("FILE_HISTORY");
        for (int p = 0; p < _FILE_HISTORY.getItemCount(); p++) {
            this.getRegistry().setProperty("FILE_HISTORY", _FILE_HISTORY.getItem(p).getText(), _FILE_HISTORY.getItem(p).getActionCommand());
        }
        // store registry.
        try {
            this.getRegistry().commit();
        } catch (java.io.IOException ioe0) {
            java.lang.System.out.println("Unable to save registry...");
            java.lang.System.out.println(ioe0.toString());
        }
        this.dispose();
        java.lang.System.exit(0);
    }

    /* Get the applications DesktopManager
    @return javax.swing.DesktopManager
     */
    protected final com.jmonkey.office.lexi.Main.DocumentManager getDesktopManager() {
        // if we have initalized properly,
        // we should get our implementation
        // of DocumentManager.
        return ((com.jmonkey.office.lexi.Main.DocumentManager) (com.jmonkey.office.lexi.Main._DESKTOP.getDesktopManager()));
    }

    /**
     * returns a number for giving new documents
     * a unique name. If the number reaches a value
     * greater than the maximum for an integer,
     * it's reset to 0.
     *
     * @return int
     */
    public int getDocumentNumber() {
        if (com.jmonkey.office.lexi.Main._DOCUMENT_COUNT >= java.lang.Integer.MAX_VALUE) {
            com.jmonkey.office.lexi.Main._DOCUMENT_COUNT = 0;
        }
        return com.jmonkey.office.lexi.Main._DOCUMENT_COUNT++;
    }

    protected final javax.swing.JToolBar getFileToolBar() {
        if (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR == null) {
            com.jmonkey.office.lexi.Main._FILE_TOOL_BAR = new com.jmonkey.office.lexi.support.ActionToolBar();
            // This causes a fatal exception when not in windows L&F
            // _FILE_TOOL_BAR.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getNewAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getOpenAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getSaveAction(this, ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager()))));
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).addSeparator();
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getUndoAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getRedoAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).addSeparator();
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getCutAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getCopyAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getPasteAction());
            // Orientation Not working properly.
            java.lang.String position = this.getRegistry().getString("USER", "position.file.toolbar", java.awt.BorderLayout.NORTH);
            if (position.equalsIgnoreCase(java.awt.BorderLayout.WEST) || position.equalsIgnoreCase(java.awt.BorderLayout.EAST)) {
                ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).setOrientation(javax.swing.JToolBar.VERTICAL);
            } else if (position.equalsIgnoreCase(java.awt.BorderLayout.NORTH) || position.equalsIgnoreCase(java.awt.BorderLayout.SOUTH)) {
                ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).setOrientation(javax.swing.JToolBar.HORIZONTAL);
            }
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).setVisible(this.getRegistry().getBoolean("USER", "show.file.toolbar", true));
        }
        return com.jmonkey.office.lexi.Main._FILE_TOOL_BAR;
    }

    protected final javax.swing.JToolBar getFormatToolBar() {
        if (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR == null) {
            com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR = new com.jmonkey.office.lexi.support.ActionToolBar();
            com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
            // This causes a fatal exception when not in windows L&F
            // _FORMAT_TOOL_BAR.putClientProperty("JToolBar.isRollover", "" + true);
            // Code.debug("Create font colour dropdown.");
            com.jmonkey.office.lexi.support.ActionComboBox colours = new com.jmonkey.office.lexi.support.ActionComboBox();
            colours.setMinimumSize(new java.awt.Dimension(0, 16));
            colours.setEditable(false);
            colours.setRenderer(new com.jmonkey.office.lexi.Main.ColourActionCellRenderer());
            /**
             * Object[] keys = this.getColourRegistry().getKeys();
             * for(int c = 0; c < keys.length; c++) {
             * try {
             * colours.addItem(EditorActionManager.instance().getColourAction( ((String)keys[c]), Color.decode( this.getColourRegistry().getString(((String)keys[c]), null) ) ));
             * } catch(NumberFormatException nfe0) {
             * // ignore this.
             * }
             * }
             */
            java.util.Enumeration colourEnum = this.getRegistry().getKeys("COLOURS");
            while (colourEnum.hasMoreElements()) {
                java.lang.String colourKey = ((java.lang.String) (colourEnum.nextElement()));
                try {
                    colours.addItem(com.jmonkey.office.lexi.support.EditorActionManager.instance().getColourAction(colourKey, java.awt.Color.decode(this.getRegistry().getString("COLOURS", colourKey, null))));
                } catch (java.lang.NumberFormatException nfe0) {
                    // ignore this.
                }
            } 
            // getFontRegistry()
            // Code.debug("Create font faces dropdown.");
            com.jmonkey.office.lexi.support.ActionComboBox fonts = new com.jmonkey.office.lexi.support.ActionComboBox();
            fonts.setMinimumSize(new java.awt.Dimension(0, 16));
            fonts.setEditable(false);
            fonts.setRenderer(new com.jmonkey.office.lexi.Main.FontActionCellRenderer());
            /* Action[] fontActions = EditorActionManager.instance().createDefaultFontFaceActions();
            for(int c = 0; c < fontActions.length;c++) {
            fonts.addItem(fontActions[c]);
            }
             */
            /* Object[] fontKeys = this.getFontRegistry().getKeys();
            for(int f = 0; f < fontKeys.length; f++) {
            if(this.getFontRegistry().getBoolean((String)fontKeys[f], false)) {
            fonts.addItem(EditorActionManager.instance().getFontFaceAction((String)fontKeys[f]));
            }
            }
             */
            java.util.Enumeration fontEnum = this.getRegistry().getKeys("FONTS");
            while (fontEnum.hasMoreElements()) {
                java.lang.String fontKey = ((java.lang.String) (fontEnum.nextElement()));
                if (this.getRegistry().getBoolean("FONTS", fontKey, false)) {
                    fonts.addItem(com.jmonkey.office.lexi.support.EditorActionManager.instance().getFontFaceAction(fontKey));
                }
            } 
            // Code.debug("Create font sizes dropdown.");
            com.jmonkey.office.lexi.support.ActionComboBox fsizes = new com.jmonkey.office.lexi.support.ActionComboBox();
            fsizes.setMinimumSize(new java.awt.Dimension(0, 16));
            fsizes.setEditable(false);
            fsizes.setRenderer(new com.jmonkey.office.lexi.Main.FSActionCellRenderer());
            javax.swing.Action[] fontSizes = com.jmonkey.office.lexi.support.EditorActionManager.instance().createFontSizeActionRange(this.getRegistry().getInteger("MAIN", "font.sizes.minimum", 6), this.getRegistry().getInteger("MAIN", "font.sizes.maximum", 150), this.getRegistry().getInteger("MAIN", "font.sizes.granularity", 2));
            for (int c = 0; c < fontSizes.length; c++) {
                fsizes.addItem(fontSizes[c]);
            }
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(colours);
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(fonts);
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(fsizes);
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).addSeparator();
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getAlignLeftAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getAlignCenterAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getAlignRightAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getAlignJustifyAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).addSeparator();
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getBoldAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getItalicAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getUnderlineAction());
            ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR)).add(false, com.jmonkey.office.lexi.support.EditorActionManager.instance().getStrikeThroughAction());
            // Orientation Not working properly.
            java.lang.String position = this.getRegistry().getString("USER", "position.format.toolbar", java.awt.BorderLayout.NORTH);
            if (position.equalsIgnoreCase(java.awt.BorderLayout.WEST) || position.equalsIgnoreCase(java.awt.BorderLayout.EAST)) {
                ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).setOrientation(javax.swing.JToolBar.VERTICAL);
            } else if (position.equalsIgnoreCase(java.awt.BorderLayout.NORTH) || position.equalsIgnoreCase(java.awt.BorderLayout.SOUTH)) {
                ((com.jmonkey.office.lexi.support.ActionToolBar) (com.jmonkey.office.lexi.Main._FILE_TOOL_BAR)).setOrientation(javax.swing.JToolBar.HORIZONTAL);
            }
            com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR.setVisible(this.getRegistry().getBoolean("USER", "show.format.toolbar", true));
        }
        return com.jmonkey.office.lexi.Main._FORMAT_TOOL_BAR;
    }

    protected final javax.swing.Action getHELPAction() {
        return new com.jmonkey.office.lexi.Main.HELPAction();
    }

    protected final com.jmonkey.office.lexi.Main getMain() {
        return this;
    }

    protected final javax.swing.Action getPrintAction() {
        return new com.jmonkey.office.lexi.Main.PrintAction();
    }

    /**
     * Gets the current Registry for Main
     */
    protected final com.jmonkey.export.Registry getRegistry() {
        if (_REGISTRY == null) {
            try {
                _REGISTRY = com.jmonkey.export.Registry.loadForClass(com.jmonkey.office.lexi.Main.class);
                // Default Users group...
                if (!(_REGISTRY.sizeOf("USER") > 0)) {
                    _REGISTRY.setProperty("USER", "user.name", java.lang.System.getProperty("user.name"));
                    _REGISTRY.setProperty("USER", "user.timezone", java.lang.System.getProperty("user.timezone"));
                    _REGISTRY.setProperty("USER", "user.home", java.lang.System.getProperty("user.home"));
                    _REGISTRY.setProperty("USER", "user.region", java.lang.System.getProperty("user.region"));
                    _REGISTRY.setProperty("USER", "temp.directory", java.lang.System.getProperty("java.io.tmpdir"));
                }
                // Default Options group
                if (!(_REGISTRY.sizeOf("OPTION") > 0)) {
                    _REGISTRY.setProperty("OPTION", "Cut", "true");
                    _REGISTRY.setProperty("OPTION", "Copy", "true");
                    _REGISTRY.setProperty("OPTION", "Paste", "true");
                    _REGISTRY.setProperty("OPTION", "-", "true");
                    _REGISTRY.setProperty("OPTION", "Undo", "true");
                    _REGISTRY.setProperty("OPTION", "Redo", "true");
                    _REGISTRY.setProperty("OPTION", "-", "true");
                    _REGISTRY.setProperty("OPTION", "SelectAll", "true");
                    _REGISTRY.setProperty("OPTION", "SelectNone", "true");
                    _REGISTRY.setProperty("OPTION", "-", "true");
                    // _REGISTRY.putProperty("Define", "true");
                }
                // Default Fonts group
                if (!(_REGISTRY.sizeOf("FONTS") > 0)) {
                    // Get all the fonts and set them to not load.
                    java.lang.String[] families = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
                    for (int f = 0; f < families.length; f++) {
                        if (families[f].indexOf(".") < 0) {
                            _REGISTRY.setProperty("FONTS", families[f], "false");
                        }
                    }
                    _REGISTRY.setProperty("FONTS", "Default", "true");
                    _REGISTRY.setProperty("FONTS", "Dialog", "true");
                    _REGISTRY.setProperty("FONTS", "DialogInput", "true");
                    _REGISTRY.setProperty("FONTS", "Monospaced", "true");
                    _REGISTRY.setProperty("FONTS", "SansSerif", "true");
                    _REGISTRY.setProperty("FONTS", "Serif", "true");
                }
                // Default Colours
                if (!(_REGISTRY.sizeOf("COLOURS") > 0)) {
                    _REGISTRY.setProperty("COLOURS", "White", com.jmonkey.export.Format.colorToHex(java.awt.Color.white));
                    _REGISTRY.setProperty("COLOURS", "Black", com.jmonkey.export.Format.colorToHex(java.awt.Color.black));
                    _REGISTRY.setProperty("COLOURS", "Red", com.jmonkey.export.Format.colorToHex(java.awt.Color.red));
                    _REGISTRY.setProperty("COLOURS", "Green", com.jmonkey.export.Format.colorToHex(java.awt.Color.green));
                    _REGISTRY.setProperty("COLOURS", "Blue", com.jmonkey.export.Format.colorToHex(java.awt.Color.blue));
                    _REGISTRY.setProperty("COLOURS", "Orange", com.jmonkey.export.Format.colorToHex(java.awt.Color.orange));
                    _REGISTRY.setProperty("COLOURS", "Dark Gray", com.jmonkey.export.Format.colorToHex(java.awt.Color.darkGray));
                    _REGISTRY.setProperty("COLOURS", "Gray", com.jmonkey.export.Format.colorToHex(java.awt.Color.gray));
                    _REGISTRY.setProperty("COLOURS", "Light Gray", com.jmonkey.export.Format.colorToHex(java.awt.Color.lightGray));
                    _REGISTRY.setProperty("COLOURS", "Cyan", com.jmonkey.export.Format.colorToHex(java.awt.Color.cyan));
                    _REGISTRY.setProperty("COLOURS", "Magenta", com.jmonkey.export.Format.colorToHex(java.awt.Color.magenta));
                    _REGISTRY.setProperty("COLOURS", "Pink", com.jmonkey.export.Format.colorToHex(java.awt.Color.pink));
                    _REGISTRY.setProperty("COLOURS", "Yellow", com.jmonkey.export.Format.colorToHex(java.awt.Color.yellow));
                }
            } catch (java.io.IOException ioe0) {
                // Code.failed(ioe0);
            }
        }
        return _REGISTRY;
    }

    // "USER",
    protected javax.swing.JLabel getStatusLabel() {
        if (com.jmonkey.office.lexi.Main._STATUS_LABEL == null) {
            com.jmonkey.office.lexi.Main._STATUS_LABEL = new javax.swing.JLabel("Editing");
            com.jmonkey.office.lexi.Main._STATUS_LABEL.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        }
        return com.jmonkey.office.lexi.Main._STATUS_LABEL;
    }

    protected final javax.swing.Action getUpdateAction() {
        return new com.jmonkey.office.lexi.Main.UpdateAction();
    }

    private void init() {
        // Set the main application Icon.
        this.setIconImage(com.jmonkey.office.lexi.support.images.Loader.load("jmonkey16.gif"));
        // Install out custom look & feel
        // This should be in another Runtime
        // class that will gets its data from
        // a property file. we can then add
        // many L&F's as we need.
        // try {
        // MainSplash splasher = new MainSplash(new ImageIcon("com/jmonkey/office/common/images/jmsplash.gif"));
        // splasher.showStatus("Installing Look and Feel..");
        // Install custom PLAF. We should have a
        // global LAF manager instead, that is
        // not specific to Main.
        // UIManager.installLookAndFeel("Active", com.jmonkey.office.common.swing.plaf.active.ActiveLookAndFeel.class.getName());
        // Set the look & feel for the app.
        try {
            javax.swing.UIManager.setLookAndFeel(this.getRegistry().getString("MAIN", "main.look&feel", javax.swing.UIManager.getSystemLookAndFeelClassName()));
        } catch (java.lang.Exception e) {
            java.lang.System.out.println("Unknown Look & Feel. Using Defaults.");
        }
        // Thread.sleep(1500);
        // splasher.showStatus("Setting up Desktop..");
        com.jmonkey.office.lexi.Main._DESKTOP = new com.jmonkey.office.lexi.Main.MainDesktop();
        com.jmonkey.office.lexi.Main._DESKTOP.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
        // Makes it just show the outline when we drag.  Speeds up
        // program significantly -- Matt
        // I've added support so this can be changed later.
        // This says "default to true" if the property is
        // not already in the registry. (i.e. its the first
        // run of the program) -- Brill
        if (this.getRegistry().getBoolean("MAIN", "mdi.outline.drag", true)) {
            com.jmonkey.office.lexi.Main._DESKTOP.putClientProperty("JDesktopPane.dragMode", "outline");
        }
        javax.swing.JPanel contentPane = new javax.swing.JPanel();
        contentPane.setLayout(new java.awt.BorderLayout());
        // We'll add everything to our special content pane first.
        this.setContentPane(contentPane);
        // Don't need to store DocumentManager
        // The desktop pane should always know
        // where it is and how to get it.
        // this helps us to write our code
        // so it doesn't depend on our own
        // copy being valid
        com.jmonkey.office.lexi.Main._DESKTOP.setDesktopManager(new com.jmonkey.office.lexi.Main.DocumentManager(this));
        // we need to add the toolbars
        // before we add the desktop pane.
        // -- Brill 03/18/1999
        // Thread.sleep(1500);
        // splasher.showStatus("Setting up toolbars...");
        javax.swing.JPanel fileToolPanel = new javax.swing.JPanel();
        fileToolPanel.setLayout(new java.awt.BorderLayout());
        fileToolPanel.add(this.getFileToolBar(), this.getRegistry().getString("USER", "position.file.toolbar", java.awt.BorderLayout.WEST));
        // the edit toolbar should also go in here.
        javax.swing.JPanel formatToolPanel = new javax.swing.JPanel();
        formatToolPanel.setLayout(new java.awt.BorderLayout());
        formatToolPanel.add(this.getFormatToolBar(), this.getRegistry().getString("USER", "position.format.toolbar", java.awt.BorderLayout.NORTH));
        javax.swing.JPanel desktopContainer = new javax.swing.JPanel();
        desktopContainer.setLayout(new java.awt.BorderLayout());
        desktopContainer.add(com.jmonkey.office.lexi.Main._DESKTOP, java.awt.BorderLayout.CENTER);
        fileToolPanel.add(formatToolPanel, java.awt.BorderLayout.CENTER);
        formatToolPanel.add(desktopContainer, java.awt.BorderLayout.CENTER);
        contentPane.add(this.getStatusLabel(), java.awt.BorderLayout.SOUTH);
        contentPane.add(fileToolPanel, java.awt.BorderLayout.CENTER);
        // contentPane.add(_DESKTOP, BorderLayout.CENTER);
        // Create and add the menu bar.
        this.setJMenuBar(createMenuBar());
        // Thread.sleep(1500);
        // splasher.showStatus("Reading in Registry...");
        java.util.Enumeration fhEnum = this.getRegistry().getKeys("FILE_HISTORY");
        while (fhEnum.hasMoreElements()) {
            java.lang.String fhKey = ((java.lang.String) (fhEnum.nextElement()));
            javax.swing.JMenuItem item = new javax.swing.JMenuItem(fhKey);
            item.setActionCommand(this.getRegistry().getString("FILE_HISTORY", fhKey, fhKey));
            item.addActionListener(_FILE_HISTROY_ACTION);
            _FILE_HISTORY.add(item);
        } 
        // Thread.sleep(1500);
        // splasher.close();
        // Added this to dispose of
        // the main app window when
        // it gets closed.
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                doExit();
            }
        });
        // Restore the apps last location and size.
        // Changed this to use the screen size as defaults.
        this.setSize(this.getRegistry().getInteger("MAIN", "main.window.w", (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 5) * 4), this.getRegistry().getInteger("MAIN", "main.window.h", (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 5) * 4));
        this.setLocation(this.getRegistry().getInteger("MAIN", "main.window.x", 0), this.getRegistry().getInteger("MAIN", "main.window.y", 0));
    }

    /**
     * Main method...
     */
    public static void main(java.lang.String[] args) {
        com.jmonkey.office.lexi.support.Splash s = new com.jmonkey.office.lexi.support.Splash(400, 200);
        s.setImage(com.jmonkey.office.lexi.support.images.Loader.load("logo.gif"));
        s.getVersionDate().setText("June 15 2000");
        s.getVersion().setText("0.1.1 Alpha");
        s.getAuthor().setText("Founded by Brill Pappin & Matthew Schmidt.");
        s.getCopyright().setText("This software is licensed under the GNU GPL v.2");
        s.getTital().setText("Lexi");
        s.getDescription().setText("A 100% pure Java 2 word processor");
        s.showSplash();
        // Main app = new Main(args);
        s.hideSplash();
    }

    private void updateOpenWindowsMenu() {
        java.lang.String[] openDocs = ((com.jmonkey.office.lexi.Main.DocumentManager) (this.getDesktopManager())).openDocumentList();
        _OPEN_WINDOWS.removeAll();
        for (int o = 0; o < openDocs.length; o++) {
            javax.swing.JMenuItem item = new javax.swing.JMenuItem(openDocs[o]);
            item.setActionCommand(openDocs[o]);
            item.addActionListener(_OPEN_WINDOW_ACTION);
            _OPEN_WINDOWS.insert(item, 0);
        }
    }
}