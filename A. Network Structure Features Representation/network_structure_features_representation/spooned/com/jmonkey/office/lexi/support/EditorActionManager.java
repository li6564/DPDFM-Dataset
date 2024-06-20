package com.jmonkey.office.lexi.support;
public class EditorActionManager extends com.jmonkey.office.lexi.support.ActionManager {
    // Modifier Constants
    public static final int COLOUR_BLACK = java.awt.Color.black.getRGB();

    public static final int COLOUR_BLUE = java.awt.Color.blue.getRGB();

    public static final int COLOUR_CYAN = java.awt.Color.cyan.getRGB();

    public static final int COLOUR_DARKGRAY = java.awt.Color.darkGray.getRGB();

    public static final int COLOUR_GRAY = java.awt.Color.gray.getRGB();

    public static final int COLOUR_GREEN = java.awt.Color.green.getRGB();

    public static final int COLOUR_LIGHTGRAY = java.awt.Color.lightGray.getRGB();

    public static final int COLOUR_MAGENTA = java.awt.Color.magenta.getRGB();

    public static final int COLOUR_ORANGE = java.awt.Color.orange.getRGB();

    public static final int COLOUR_PINK = java.awt.Color.pink.getRGB();

    public static final int COLOUR_RED = java.awt.Color.red.getRGB();

    public static final int COLOUR_WHITE = java.awt.Color.white.getRGB();

    public static final int COLOUR_YELLOW = java.awt.Color.yellow.getRGB();

    // Action Types.
    public static final java.lang.String BEEP_ACTION_PREFIX = "Beep";

    public static final java.lang.String ALIGN_LEFT_ACTION_PREFIX = "Align Left";

    public static final java.lang.String ALIGN_RIGHT_ACTION_PREFIX = "Align Right";

    public static final java.lang.String ALIGN_CENTER_ACTION_PREFIX = "Align Center";

    public static final java.lang.String ALIGN_JUSTIFIED_ACTION_PREFIX = "Align Justified";

    public static final java.lang.String BOLD_ACTION_PREFIX = "Bold";

    public static final java.lang.String ITALIC_ACTION_PREFIX = "Italic";

    public static final java.lang.String UNDERLINE_ACTION_PREFIX = "Underline";

    public static final java.lang.String STRIKETHROUGH_ACTION_PREFIX = "Strikethrough";

    public static final java.lang.String CUT_ACTION_PREFIX = "Cut";

    public static final java.lang.String COPY_ACTION_PREFIX = "Copy";

    public static final java.lang.String PASTE_ACTION_PREFIX = "Paste";

    public static final java.lang.String SELECTALL_ACTION_PREFIX = "Select All";

    public static final java.lang.String SELECTNONE_ACTION_PREFIX = "Select None";

    public static final java.lang.String UNDO_ACTION_PREFIX = "Undo";

    public static final java.lang.String REDO_ACTION_PREFIX = "Redo";

    public static final java.lang.String COLOUR_CHOOSER_ACTION_PREFIX = "Colour Chooser...";

    public static final java.lang.String FONT_CHOOSER_ACTION_PREFIX = "Font Chooser...";

    public static final java.lang.String SEARCH_ACTION_PREFIX = "Find...";

    public static final java.lang.String REPLACE_ACTION_PREFIX = "Find & Replace...";

    public static final java.lang.String FILE_NEW_ACTION_PREFIX = "New";

    public static final java.lang.String FILE_OPEN_ACTION_PREFIX = "Open...";

    public static final java.lang.String FILE_OPENAS_ACTION_PREFIX = "Open As...";

    public static final java.lang.String FILE_REVERT_ACTION_PREFIX = "Revert To Saved";

    public static final java.lang.String FILE_SAVE_ACTION_PREFIX = "Save";

    public static final java.lang.String FILE_SAVEAS_ACTION_PREFIX = "Save As...";

    public static final java.lang.String FILE_SAVECOPY_ACTION_PREFIX = "Save Copy...";

    // public static final String FONT_FAMILY_ACTION_PREFIX = "format-font-family@";
    // public static final String FONT_SIZE_ACTION_PREFIX = "format-font-size@";
    // public static final String FONT_COLOUR_ACTION_PREFIX = "format-font-colour@";
    // StyleConstants.ALIGN_RIGHT;
    // ========= PRIVATE MEMBERS ===================
    private static com.jmonkey.office.lexi.support.EditorActionManager _INSTANCE = null;

    private final javax.swing.event.CaretListener _ATTRIBUTE_TRACKER = new com.jmonkey.office.lexi.support.EditorActionManager.AttributeTracker();

    private static com.jmonkey.office.lexi.support.Editor _EDITOR = null;

    private static java.util.Map _ACTIONS = java.util.Collections.synchronizedMap(new java.util.HashMap());

    // private static ThreadPool _THREADPOOL = null;
    /**
     * Tracks caret movement and keeps the input attributes set
     * to reflect the current set of attribute definitions at the
     * caret position.
     */
    protected final class AttributeTracker implements javax.swing.event.CaretListener , java.io.Serializable {
        protected AttributeTracker() {
            super();
        }

        // # Still referencing the local versions
        public void caretUpdate(javax.swing.event.CaretEvent e) {
            if (com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor() != null) {
                int dot = e.getDot();
                int mark = e.getMark();
                if (dot == mark) {
                    // record current character attributes.
                    // We should check for a JEditorPane here.
                    javax.swing.text.JTextComponent c = ((javax.swing.text.JTextComponent) (e.getSource()));
                    javax.swing.text.StyledDocument doc = ((javax.swing.text.StyledDocument) (c.getDocument()));
                    javax.swing.text.Element run = doc.getCharacterElement(java.lang.Math.max(dot - 1, 0));
                    com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().setCurrentParagraph(doc.getParagraphElement(dot));
                    if (run != com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getCurrentRun()) {
                        // _CURRENT_RUN = run;
                        com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().setCurrentRun(run);
                        com.jmonkey.office.lexi.support.EditorActionManager.instance().createInputAttributes(com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getCurrentRun(), com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getInputAttributes());
                    }
                }
            }
        }
    }

    protected final class FontChooserAction extends javax.swing.AbstractAction {
        // BufferedImage
        private javax.swing.JFrame _PARENT;

        public FontChooserAction(java.lang.String nm, javax.swing.JFrame component) {
            super(nm);
            this._PARENT = component;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            java.awt.Font font = com.jmonkey.office.lexi.support.FontChooser.display(_PARENT);
            if (font != null) {
                // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
                com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
                java.lang.String family = font.getFamily();
                int size = font.getSize();
                boolean is_bold = font.isBold();
                boolean is_italic = font.isItalic();
                // Code.debug("Font Chooser: " + font.toString());
                if (family != null) {
                    javax.swing.text.MutableAttributeSet attr = editor.getSimpleAttributeSet();
                    javax.swing.text.StyleConstants.setFontFamily(attr, family);
                    javax.swing.text.StyleConstants.setFontSize(attr, size);
                    javax.swing.text.StyleConstants.setItalic(attr, is_italic);
                    javax.swing.text.StyleConstants.setBold(attr, is_bold);
                    /* boolean underline = (StyleConstants.isItalic(attr)) ? false : true;
                    StyleConstants.setItalic(attr, underline);
                    boolean strike = (StyleConstants.isItalic(attr)) ? false : true;
                    StyleConstants.setItalic(attr, strike);
                     */
                    com.jmonkey.office.lexi.support.EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
                } else {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }

    protected final class FontFamilyAction extends javax.swing.AbstractAction {
        private java.lang.String family;

        public FontFamilyAction(java.lang.String nm, java.lang.String family) {
            super(nm);
            this.family = family;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (editor != null) {
                java.lang.String family = this.family;
                if ((e != null) && (e.getSource() == editor)) {
                    java.lang.String s = e.getActionCommand();
                    if (s != null) {
                        family = s;
                        // Code.debug("family: " + s);
                    }
                }
                if (family != null) {
                    javax.swing.text.MutableAttributeSet attr = editor.getSimpleAttributeSet();
                    javax.swing.text.StyleConstants.setFontFamily(attr, family);
                    com.jmonkey.office.lexi.support.EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
                } else {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }

    protected final class FontSizeAction extends javax.swing.AbstractAction {
        private int size;

        public FontSizeAction(java.lang.String nm, int size) {
            super(nm);
            this.size = size;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (editor != null) {
                int size = this.size;
                if ((e != null) && (e.getSource() == editor)) {
                    java.lang.String s = e.getActionCommand();
                    try {
                        size = java.lang.Integer.parseInt(s, 10);
                    } catch (java.lang.NumberFormatException nfe) {
                    }
                }
                if (size != 0) {
                    javax.swing.text.MutableAttributeSet attr = editor.getSimpleAttributeSet();
                    javax.swing.text.StyleConstants.setFontSize(attr, size);
                    com.jmonkey.office.lexi.support.EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
                } else {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }

    protected final class ColourChooserAction extends javax.swing.AbstractAction {
        // BufferedImage
        private javax.swing.JFrame _PARENT;

        public ColourChooserAction(java.lang.String nm, javax.swing.JFrame component) {
            super(nm);
            this._PARENT = component;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (editor != null) {
                java.awt.Color fg = javax.swing.JColorChooser.showDialog(_PARENT, "Choose a colour...", null);
                if (((e != null) && (e.getSource() == editor)) && (fg != null)) {
                    java.lang.String s = e.getActionCommand();
                    try {
                        fg = java.awt.Color.decode(s);
                    } catch (java.lang.NumberFormatException nfe) {
                    }
                }
                if (fg != null) {
                    javax.swing.text.MutableAttributeSet attr = editor.getSimpleAttributeSet();
                    javax.swing.text.StyleConstants.setForeground(attr, fg);
                    com.jmonkey.office.lexi.support.EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
                } else {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }

    protected final class ForegroundAction extends javax.swing.AbstractAction {
        protected java.awt.Color fg = null;

        protected java.lang.String name = null;

        public ForegroundAction(java.lang.String nm, java.awt.Color fg) {
            // super(nm, new ImageIcon(EditorActionManager.instance().create16x16ColourRec(c, fg)));
            super(nm);
            this.name = nm;
            this.fg = fg;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (editor != null) {
                java.awt.Color fg = this.fg;
                if ((e != null) && (e.getSource() == editor)) {
                    java.lang.String s = e.getActionCommand();
                    try {
                        fg = java.awt.Color.decode(s);
                    } catch (java.lang.NumberFormatException nfe) {
                    }
                }
                if (fg != null) {
                    javax.swing.text.MutableAttributeSet attr = editor.getSimpleAttributeSet();
                    javax.swing.text.StyleConstants.setForeground(attr, fg);
                    com.jmonkey.office.lexi.support.EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
                } else {
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }

    protected final class AlignmentAction extends javax.swing.AbstractAction {
        private int a;

        public AlignmentAction(java.lang.String nm, int a) {
            super(nm);
            this.a = a;
            switch (a) {
                case javax.swing.text.StyleConstants.ALIGN_RIGHT :
                    this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("align_right16.gif")));
                    break;
                case javax.swing.text.StyleConstants.ALIGN_LEFT :
                    this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("align_left16.gif")));
                    break;
                case javax.swing.text.StyleConstants.ALIGN_CENTER :
                    this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("align_center16.gif")));
                    break;
                case javax.swing.text.StyleConstants.ALIGN_JUSTIFIED :
                    this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("align_justify16.gif")));
                    break;
            }
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (editor != null) {
                int a = this.a;
                if ((e != null) && (e.getSource() == editor)) {
                    java.lang.String s = e.getActionCommand();
                    try {
                        a = java.lang.Integer.parseInt(s, 10);
                    } catch (java.lang.NumberFormatException nfe) {
                    }
                }
                javax.swing.text.MutableAttributeSet attr = editor.getSimpleAttributeSet();
                javax.swing.text.StyleConstants.setAlignment(attr, a);
                com.jmonkey.office.lexi.support.EditorActionManager.instance().setParagraphAttributes(editor.getTextComponent(), attr, false);
            }
        }
    }

    protected final class BoldAction extends javax.swing.AbstractAction {
        public BoldAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.BOLD_ACTION_PREFIX);
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("bold_action16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (editor != null) {
                // StyledEditorKit kit = getStyledEditorKit(editor);
                javax.swing.text.MutableAttributeSet attr = editor.getInputAttributes();// kit.getInputAttributes();

                boolean bold = (javax.swing.text.StyleConstants.isBold(attr)) ? false : true;
                javax.swing.text.StyleConstants.setBold(attr, bold);
                com.jmonkey.office.lexi.support.EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
            }
        }
    }

    protected final class ItalicAction extends javax.swing.AbstractAction {
        public ItalicAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.ITALIC_ACTION_PREFIX);
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("italic_action16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (editor != null) {
                // StyledEditorKit kit = getStyledEditorKit(editor);
                javax.swing.text.MutableAttributeSet attr = editor.getInputAttributes();// kit.getInputAttributes();

                boolean italic = (javax.swing.text.StyleConstants.isItalic(attr)) ? false : true;
                javax.swing.text.StyleConstants.setItalic(attr, italic);
                com.jmonkey.office.lexi.support.EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
            }
        }
    }

    protected final class UnderlineAction extends javax.swing.AbstractAction {
        public UnderlineAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.UNDERLINE_ACTION_PREFIX);
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("underline_action16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (editor != null) {
                // StyledEditorKit kit = getStyledEditorKit(editor);
                javax.swing.text.MutableAttributeSet attr = editor.getInputAttributes();// kit.getInputAttributes();

                boolean underline = (javax.swing.text.StyleConstants.isUnderline(attr)) ? false : true;
                javax.swing.text.StyleConstants.setUnderline(attr, underline);
                com.jmonkey.office.lexi.support.EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
            }
        }
    }

    protected final class StrikeThroughAction extends javax.swing.AbstractAction {
        public StrikeThroughAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.STRIKETHROUGH_ACTION_PREFIX);
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("strikethrough_action16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (editor != null) {
                // StyledEditorKit kit = getStyledEditorKit(editor);
                javax.swing.text.MutableAttributeSet attr = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getInputAttributes();// kit.getInputAttributes();

                boolean bold = (javax.swing.text.StyleConstants.isStrikeThrough(attr)) ? false : true;
                javax.swing.text.StyleConstants.setStrikeThrough(attr, bold);
                com.jmonkey.office.lexi.support.EditorActionManager.instance().setCharacterAttributes(editor.getTextComponent(), attr, false);
            }
        }
    }

    protected final class CutAction extends javax.swing.AbstractAction {
        public CutAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.CUT_ACTION_PREFIX);
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("cut_action16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor target = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (target != null) {
                target.getTextComponent().cut();
            }
        }
    }

    protected final class CopyAction extends javax.swing.AbstractAction {
        public CopyAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.COPY_ACTION_PREFIX);
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("copy_action16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor target = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (target != null) {
                target.getTextComponent().copy();
            }
        }
    }

    protected final class PasteAction extends javax.swing.AbstractAction {
        public PasteAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.PASTE_ACTION_PREFIX);
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("paste_action16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor target = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (target != null) {
                target.getTextComponent().paste();
            }
        }
    }

    protected final class BeepAction extends javax.swing.AbstractAction {
        public BeepAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.BEEP_ACTION_PREFIX);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    protected final class SelectAllAction extends javax.swing.AbstractAction {
        protected SelectAllAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.SELECTALL_ACTION_PREFIX);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor target = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (target != null) {
                javax.swing.text.Document doc = target.getTextComponent().getDocument();
                target.getTextComponent().setCaretPosition(0);
                target.getTextComponent().moveCaretPosition(doc.getLength());
            }
        }
    }

    protected final class SelectNoneAction extends javax.swing.AbstractAction {
        protected SelectNoneAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.SELECTNONE_ACTION_PREFIX);
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor target = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (target != null) {
                if (target.getTextComponent().getSelectionStart() != target.getTextComponent().getSelectionEnd()) {
                    int dot = target.getTextComponent().getSelectionStart();
                    target.getTextComponent().setSelectionStart(dot);
                    target.getTextComponent().setSelectionEnd(dot);
                    target.getTextComponent().setCaretPosition(dot);
                }
            }
        }
    }

    protected final class UndoAction extends javax.swing.AbstractAction {
        protected UndoAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.UNDO_ACTION_PREFIX);
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("undo_action16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor target = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (target != null) {
                if (target.getUndoManager().canUndo()) {
                    target.getUndoManager().undo();
                }
            }
        }
    }

    protected final class RedoAction extends javax.swing.AbstractAction {
        protected RedoAction() {
            super(com.jmonkey.office.lexi.support.EditorActionManager.REDO_ACTION_PREFIX);
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("redo_action16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // JEditorPane editor = EditorActionManager.getActiveEditor().getTextComponent();
            com.jmonkey.office.lexi.support.Editor target = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
            if (target != null) {
                if (target.getUndoManager().canRedo()) {
                    target.getUndoManager().redo();
                }
            }
        }
    }

    protected final class SearchAction extends javax.swing.AbstractAction {
        // BufferedImage
        private javax.swing.JFrame _PARENT;

        public SearchAction(java.lang.String nm, javax.swing.JFrame component) {
            super(nm);
            this._PARENT = component;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // Code.message("Search Activated...");
        }
    }

    protected final class ReplaceAction extends javax.swing.AbstractAction {
        // BufferedImage
        private javax.swing.JFrame _PARENT;

        public ReplaceAction(java.lang.String nm, javax.swing.JFrame component) {
            super(nm);
            this._PARENT = component;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // Code.message("Replace Activated...");
        }
    }

    protected final class NewAction extends javax.swing.AbstractAction {
        private com.jmonkey.office.lexi.support.FileActionListener _LISTENER = null;

        public NewAction(java.lang.String name, javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
            super(name);
            _LISTENER = agent;
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("new_document16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            _LISTENER.editorNew();
        }
    }

    protected final class OpenAction extends javax.swing.AbstractAction {
        private com.jmonkey.office.lexi.support.FileActionListener _LISTENER = null;

        public OpenAction(java.lang.String name, javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
            super(name);
            _LISTENER = agent;
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("open_document16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            _LISTENER.editorOpen();
        }
    }

    protected final class OpenAsAction extends javax.swing.AbstractAction {
        private com.jmonkey.office.lexi.support.FileActionListener _LISTENER = null;

        public OpenAsAction(java.lang.String name, javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
            super(name);
            _LISTENER = agent;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            _LISTENER.editorOpenAs();
        }
    }

    protected final class RevertAction extends javax.swing.AbstractAction {
        private com.jmonkey.office.lexi.support.FileActionListener _LISTENER = null;

        public RevertAction(java.lang.String name, javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
            super(name);
            _LISTENER = agent;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor() != null) {
                _LISTENER.editorRevert(com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor());
            }
        }
    }

    protected final class SaveAction extends javax.swing.AbstractAction {
        private com.jmonkey.office.lexi.support.FileActionListener _LISTENER = null;

        public SaveAction(java.lang.String name, javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
            super(name);
            _LISTENER = agent;
            this.putValue(javax.swing.Action.SMALL_ICON, new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("save_document16.gif")));
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor() != null) {
                _LISTENER.editorSave(com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor());
            }
        }
    }

    protected final class SaveAsAction extends javax.swing.AbstractAction {
        private com.jmonkey.office.lexi.support.FileActionListener _LISTENER = null;

        public SaveAsAction(java.lang.String name, javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
            super(name);
            _LISTENER = agent;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor() != null) {
                _LISTENER.editorSaveAs(com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor());
            }
        }
    }

    protected final class SaveCopyAction extends javax.swing.AbstractAction {
        private com.jmonkey.office.lexi.support.FileActionListener _LISTENER = null;

        public SaveCopyAction(java.lang.String name, javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
            super(name);
            _LISTENER = agent;
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor() != null) {
                _LISTENER.editorSaveCopy(com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor());
            }
        }
    }

    private EditorActionManager() {
        super();
        /* if(_INSTANCE != null) {
        _INSTANCE = EditorActionManager.instance();
        }
         */
    }

    /**
     * Add an editor to the action manager.
     *
     * @param editor
     * 		com.jmonkey.office.common.Editor
     */
    public static void activate(com.jmonkey.office.lexi.support.Editor editor) {
        // First deactivate the current
        // editor if there is one.
        if (com.jmonkey.office.lexi.support.EditorActionManager.instance()._EDITOR != null) {
            com.jmonkey.office.lexi.support.EditorActionManager.deactivate(com.jmonkey.office.lexi.support.EditorActionManager.instance()._EDITOR);
        }
        com.jmonkey.office.lexi.support.EditorActionManager.instance()._EDITOR = editor;
        // After the editor is saved,
        // so that any calles to
        // ActionManager,getActiveEditor()
        // will actually return something.
        com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().hasBeenActivated(com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor());
        // allow the component time to do setup before allowing Caret events.
        com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getTextComponent().addCaretListener(com.jmonkey.office.lexi.support.EditorActionManager.instance()._ATTRIBUTE_TRACKER);
    }

    private java.awt.Image create16x16ColourRec(java.awt.Component c, java.awt.Color colour) {
        /* byte[] imageData = {
        (byte)71, 		(byte)73, 		(byte)70, 		(byte)56, 		(byte)57, 
        (byte)97, 		(byte)16, 		(byte)0, 			(byte)16, 		(byte)0, 
        (byte)128, 		(byte)255, 		(byte)0, 			(byte)255, 		(byte)255, 
        (byte)255, 		(byte)0, 			(byte)0, 			(byte)0, 			(byte)44, 
        (byte)0, 			(byte)0, 			(byte)0, 			(byte)0, 			(byte)16, 
        (byte)0, 			(byte)16, 		(byte)0, 			(byte)0, 			(byte)2, 
        (byte)14, 		(byte)132, 		(byte)143, 		(byte)169, 		(byte)203, 
        (byte)237, 		(byte)15, 		(byte)163, 		(byte)156, 		(byte)180, 
        (byte)218, 		(byte)139, 		(byte)179, 		(byte)62, 		(byte)5, 
        (byte)0, 			(byte)59
        };
        Image img = java.awt.Toolkit.getDefaultToolkit().createImage(imageData);
         */
        // Code.debug("Component=" + c);
        // Code.debug("Color=" + colour);
        java.awt.Image img = c.createImage(16, 16);
        // Code.debug("Image=" + img);
        java.awt.Graphics g = img.getGraphics();
        // Code.debug("Graphics=" + g);
        g.setColor(colour);
        g.fillRect(0, 0, 16, 16);
        // Code.debug("Coloured Image=" + img);
        return img;
    }

    /**
     * Create the default actions of the type: FONT_COLOUR_ACTION
     * <P>
     *
     * @param colour
     * 		java.awt.Color the colour to assign the action.
     * @return javax.swing.Action[]
     */
    public final javax.swing.Action[] createDefaultColourActions() {
        javax.swing.Action[] a = new javax.swing.Action[14];
        a[0] = this.getColourAction("White", java.awt.Color.white);
        a[1] = this.getColourAction("Black", java.awt.Color.black);
        a[2] = this.getColourAction("Red", java.awt.Color.red);
        a[3] = this.getColourAction("Green", java.awt.Color.green);
        a[4] = this.getColourAction("Blue", java.awt.Color.blue);
        a[5] = this.getColourAction("Orange", java.awt.Color.orange);
        a[6] = this.getColourAction("Dark Gray", java.awt.Color.darkGray);
        a[7] = this.getColourAction("Gray", java.awt.Color.gray);
        a[8] = this.getColourAction("Light Gray", java.awt.Color.lightGray);
        a[9] = this.getColourAction("Cyan", java.awt.Color.cyan);
        a[10] = this.getColourAction("Magenta", java.awt.Color.magenta);
        a[11] = this.getColourAction("Pink", java.awt.Color.pink);
        a[12] = this.getColourAction("Yellow", java.awt.Color.yellow);
        return a;
    }

    /**
     * Create the default font family actions.
     *
     * @return javax.swing.Action[]
     */
    public final javax.swing.Action[] createDefaultFontFaceActions() {
        // This is returning all the
        // system fonts at the moment
        // but we should change it to
        // only include the fonts that
        // all  VM have. -- Brill 04/07/1999
        java.lang.String[] families = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        java.util.Map fontFamilyRange = java.util.Collections.synchronizedMap(new java.util.HashMap());
        javax.swing.Action a = null;
        for (int i = 0; i < families.length; i++) {
            if (families[i].indexOf(".") == (-1)) {
                // we have to test to a "." so
                // we don't get duplicates, there
                // appears to be a bug in the VM.
                a = this.getFontFaceAction(families[i]);
                fontFamilyRange.put(families[i], a);
            }
        }
        // This is a bad way to
        // do it, but I'm to tired
        // to fix it now.
        javax.swing.Action[] output = new javax.swing.Action[fontFamilyRange.size()];
        for (int i = 0; i < output.length; i++) {
            if (families[i].indexOf(".") == (-1)) {
                output[i] = ((javax.swing.Action) (fontFamilyRange.get(families[i])));
            }
        }
        return output;
    }

    /**
     * Creates a range of FontSize actions from the
     * begin number to the end number, with the
     * specified granularity.
     * <P>
     * i.e. A granularity of two, will increase the
     * range by 2. So, the number of actions produced
     * will be ((end - begin) / 2) or begin, begin + 2,
     * begin + 4, begin + 6 [up to] end.
     *
     * @param int
     * 		begin start of the range.
     * @param int
     * 		end end of the range.
     * @param int
     * 		granularity range granularity (divide by).
     */
    public final javax.swing.Action[] createFontSizeActionRange(int begin, int end, int granularity) {
        if (begin > end) {
            throw new java.lang.IllegalArgumentException("Beginning of Font Size range must be less than the end of the range.");
        }
        if ((end - begin) <= granularity) {
            throw new java.lang.IllegalArgumentException("The granularity is equal to or grater than the number of elements between the begin and end. No Size elements can be generated.");
        }
        if (((end - begin) % granularity) > 0) {
            throw new java.lang.IllegalArgumentException("The number of elements between the begin and end must be divisible by the granularity.");
        }
        javax.swing.Action[] a = new javax.swing.Action[(end - begin) / granularity];
        for (int i = begin; i < a.length; i += granularity) {
            a[i] = this.getFontSizeAction(i);
        }
        return a;
    }

    /**
     * Copies the key/values in <code>element</code>s AttributeSet into
     * <code>set</code>. This does not copy component, icon, or element
     * names attributes. Subclasses may wish to refine what is and what
     * isn't copied here. But be sure to first remove all the attributes that
     * are in <code>set</code>.<p>
     * This is called anytime the caret moves over a different location.
     */
    protected void createInputAttributes(javax.swing.text.Element element, javax.swing.text.MutableAttributeSet set) {
        set.removeAttributes(set);
        set.addAttributes(element.getAttributes());
        set.removeAttribute(javax.swing.text.StyleConstants.ComponentAttribute);
        set.removeAttribute(javax.swing.text.StyleConstants.IconAttribute);
        set.removeAttribute(javax.swing.text.AbstractDocument.ElementNameAttribute);
        set.removeAttribute(javax.swing.text.StyleConstants.ComposedTextAttribute);
    }

    /**
     * Remove an editor to the action manager.
     *
     * @param editor
     * 		com.jmonkey.office.common.Editor
     */
    public static void deactivate(com.jmonkey.office.lexi.support.Editor editor) {
        if (com.jmonkey.office.lexi.support.EditorActionManager.instance()._EDITOR != null) {
            // disable Caret events.
            com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getTextComponent().removeCaretListener(com.jmonkey.office.lexi.support.EditorActionManager.instance()._ATTRIBUTE_TRACKER);
            // Before the editor is removed,
            // so that any calles to
            // EditorActionManager,getActiveEditor()
            // will actually return something.
            com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().hasBeenDeactivated(com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor());
            com.jmonkey.office.lexi.support.EditorActionManager.instance()._EDITOR = null;
        }
    }

    /**
     * Sets the enabled attribute of all actions
     * matching or containing the specified pattern.
     * To disable a specific action, use a specific
     * name. if the name is found in the list, it
     * is the only one disabled. Otherwise, all
     * actions that contain the pattern will be
     * enabled/disabled.
     *
     * @param pattern
     * 		java.lang.String
     * @param enabled
     * 		boolean
     */
    public static final void enableAction(java.lang.String pattern, boolean enabled) {
        // Code.debug("enableAction: " + pattern + ", " + enabled);
        if (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(pattern)) {
            ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(pattern))).setEnabled(enabled);
        }
    }

    /**
     * Sets the enabled attribute of all format actions .
     *
     * @param enabled
     * 		boolean
     */
    public static final void enableColourActions(boolean enabled) {
        // Code.debug("enableColourActions: " + enabled);
        java.util.Iterator it = com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.entrySet().iterator();
        while (it.hasNext()) {
            java.lang.Object o = it.next();
            if ((o instanceof com.jmonkey.office.lexi.support.EditorActionManager.ForegroundAction) || (o instanceof com.jmonkey.office.lexi.support.EditorActionManager.ColourChooserAction)) {
                ((javax.swing.Action) (o)).setEnabled(enabled);
            }
        } 
    }

    /**
     * Sets the enabled attribute of all Document actions .
     *
     * @param enabled
     * 		boolean
     */
    public static final void enableDocumentActions(boolean enabled) {
        // Code.debug("enableDocumentActions: " + enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.CUT_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.COPY_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.PASTE_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.SELECTALL_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.SELECTNONE_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.UNDO_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.REDO_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.SEARCH_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.REPLACE_ACTION_PREFIX, enabled);
    }

    /**
     * Sets the enabled attribute of all format actions .
     *
     * @param enabled
     * 		boolean
     */
    public static final void enableFontActions(boolean enabled) {
        // Code.debug("enableFontActions: " + enabled);
        java.util.Iterator it = com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.entrySet().iterator();
        while (it.hasNext()) {
            java.lang.Object o = it.next();
            if (((o instanceof com.jmonkey.office.lexi.support.EditorActionManager.FontSizeAction) || (o instanceof com.jmonkey.office.lexi.support.EditorActionManager.FontFamilyAction)) || (o instanceof com.jmonkey.office.lexi.support.EditorActionManager.FontChooserAction)) {
                ((javax.swing.Action) (o)).setEnabled(enabled);
            }
        } 
    }

    /**
     * Sets the enabled attribute of all format actions .
     *
     * @param enabled
     * 		boolean
     */
    public static final void enableFormatActions(boolean enabled) {
        // Code.debug("enableFormatActions: " + enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_LEFT_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_RIGHT_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_CENTER_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_JUSTIFIED_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.BOLD_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.ITALIC_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.UNDERLINE_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.STRIKETHROUGH_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.COLOUR_CHOOSER_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.FONT_CHOOSER_ACTION_PREFIX, enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableColourActions(enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableFontActions(enabled);
    }

    /**
     * Sets the enabled attribute of all Generic actions .
     *
     * @param enabled
     * 		boolean
     */
    public static final void enableGenericActions(boolean enabled) {
        // Code.debug("enableGenericActions: " + enabled);
        com.jmonkey.office.lexi.support.EditorActionManager.enableAction(com.jmonkey.office.lexi.support.EditorActionManager.BEEP_ACTION_PREFIX, enabled);
    }

    public static final com.jmonkey.office.lexi.support.Editor getActiveEditor() {
        return com.jmonkey.office.lexi.support.EditorActionManager.instance()._EDITOR;
    }

    public final javax.swing.Action getAlignCenterAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_CENTER_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_CENTER_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.AlignmentAction(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_CENTER_ACTION_PREFIX, javax.swing.text.StyleConstants.ALIGN_CENTER));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_CENTER_ACTION_PREFIX)));
    }

    public final javax.swing.Action getAlignJustifyAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_JUSTIFIED_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_JUSTIFIED_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.AlignmentAction(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_JUSTIFIED_ACTION_PREFIX, javax.swing.text.StyleConstants.ALIGN_JUSTIFIED));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_JUSTIFIED_ACTION_PREFIX)));
    }

    public final javax.swing.Action getAlignLeftAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_LEFT_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_LEFT_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.AlignmentAction(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_LEFT_ACTION_PREFIX, javax.swing.text.StyleConstants.ALIGN_LEFT));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_LEFT_ACTION_PREFIX)));
    }

    public final javax.swing.Action getAlignRightAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_RIGHT_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_RIGHT_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.AlignmentAction(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_RIGHT_ACTION_PREFIX, javax.swing.text.StyleConstants.ALIGN_RIGHT));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.ALIGN_RIGHT_ACTION_PREFIX)));
    }

    public final javax.swing.Action getBeepAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.BEEP_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.BEEP_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.BeepAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.BEEP_ACTION_PREFIX)));
    }

    public final javax.swing.Action getBoldAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.BOLD_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.BOLD_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.BoldAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.BOLD_ACTION_PREFIX)));
    }

    public final javax.swing.Action getColourAction(java.lang.String name, java.awt.Color colour) {
        // String key = "#" + Integer.toHexString(colour.getRGB()).toUpperCase();
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(name)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(name, new com.jmonkey.office.lexi.support.EditorActionManager.ForegroundAction(name, colour));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(name)));
    }

    public final java.awt.Color getColourAtCaret() {
        com.jmonkey.office.lexi.support.Editor editor = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor();
        if (editor != null) {
            return javax.swing.text.StyleConstants.getForeground(editor.getInputAttributes());
        } else {
            return null;
        }
    }

    public final javax.swing.Action getColourChooserAction(javax.swing.JFrame component) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.COLOUR_CHOOSER_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.COLOUR_CHOOSER_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.ColourChooserAction(com.jmonkey.office.lexi.support.EditorActionManager.COLOUR_CHOOSER_ACTION_PREFIX, component));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.COLOUR_CHOOSER_ACTION_PREFIX)));
    }

    public final javax.swing.Action getCopyAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.COPY_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.COPY_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.CopyAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.COPY_ACTION_PREFIX)));
    }

    public final javax.swing.Action getCutAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.CUT_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.CUT_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.CutAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.CUT_ACTION_PREFIX)));
    }

    // =============== BEGIN ADD ACTION METHODS =========================================
    public final javax.swing.Action getFontChooserAction(javax.swing.JFrame component) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.FONT_CHOOSER_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.FONT_CHOOSER_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.FontChooserAction(com.jmonkey.office.lexi.support.EditorActionManager.FONT_CHOOSER_ACTION_PREFIX, component));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.FONT_CHOOSER_ACTION_PREFIX)));
    }

    /**
     *
     * @param font
     * 		java.awt.Font the font to use as a template.
     */
    public final javax.swing.Action getFontFaceAction(java.awt.Font font) {
        return this.getFontFaceAction(font.getFontName());
    }

    public final javax.swing.Action getFontFaceAction(java.lang.String name) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(name)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(name, new com.jmonkey.office.lexi.support.EditorActionManager.FontFamilyAction(name, name));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(name)));
    }

    public final javax.swing.Action getFontSizeAction(int size) {
        java.lang.String key = "" + size;
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(key)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(key, new com.jmonkey.office.lexi.support.EditorActionManager.FontSizeAction(key, size));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(key)));
    }

    public final javax.swing.Action getItalicAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.ITALIC_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.ITALIC_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.ItalicAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.ITALIC_ACTION_PREFIX)));
    }

    // ===== Editor File Actions ============================
    public final javax.swing.Action getNewAction(javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.FILE_NEW_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.FILE_NEW_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.NewAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_NEW_ACTION_PREFIX, component, agent));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.FILE_NEW_ACTION_PREFIX)));
    }

    public final javax.swing.Action getOpenAction(javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.FILE_OPEN_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.FILE_OPEN_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.OpenAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_OPEN_ACTION_PREFIX, component, agent));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.FILE_OPEN_ACTION_PREFIX)));
    }

    public final javax.swing.Action getOpenAsAction(javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.FILE_OPENAS_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.FILE_OPENAS_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.OpenAsAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_OPENAS_ACTION_PREFIX, component, agent));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.FILE_OPENAS_ACTION_PREFIX)));
    }

    public final javax.swing.Action getPasteAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.PASTE_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.PASTE_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.PasteAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.PASTE_ACTION_PREFIX)));
    }

    public final javax.swing.Action getRedoAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.REDO_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.REDO_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.RedoAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.REDO_ACTION_PREFIX)));
    }

    public final javax.swing.Action getReplaceAction(javax.swing.JFrame component) {
        // Code.debug("getReplaceAction");
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.REPLACE_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.REPLACE_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.FontChooserAction(com.jmonkey.office.lexi.support.EditorActionManager.REPLACE_ACTION_PREFIX, component));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.REPLACE_ACTION_PREFIX)));
    }

    public final javax.swing.Action getRevertAction(javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.FILE_REVERT_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.FILE_REVERT_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.RevertAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_REVERT_ACTION_PREFIX, component, agent));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.FILE_REVERT_ACTION_PREFIX)));
    }

    public final javax.swing.Action getSaveAction(javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVE_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVE_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.SaveAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVE_ACTION_PREFIX, component, agent));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVE_ACTION_PREFIX)));
    }

    public final javax.swing.Action getSaveAsAction(javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVEAS_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVEAS_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.SaveAsAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVEAS_ACTION_PREFIX, component, agent));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVEAS_ACTION_PREFIX)));
    }

    public final javax.swing.Action getSaveCopyAction(javax.swing.JFrame component, com.jmonkey.office.lexi.support.FileActionListener agent) {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVECOPY_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVECOPY_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.SaveCopyAction(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVECOPY_ACTION_PREFIX, component, agent));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.FILE_SAVECOPY_ACTION_PREFIX)));
    }

    // ===== Edit File Actions ============================
    public final javax.swing.Action getSearchAction(javax.swing.JFrame component) {
        // Code.debug("getSearchAction");
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.SEARCH_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.SEARCH_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.FontChooserAction(com.jmonkey.office.lexi.support.EditorActionManager.SEARCH_ACTION_PREFIX, component));
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.SEARCH_ACTION_PREFIX)));
    }

    public final javax.swing.Action getSelectAllAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.SELECTALL_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.SELECTALL_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.SelectAllAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.SELECTALL_ACTION_PREFIX)));
    }

    public final javax.swing.Action getSelectNoneAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.SELECTNONE_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.SELECTNONE_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.SelectNoneAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.SELECTNONE_ACTION_PREFIX)));
    }

    public final javax.swing.Action getStrikeThroughAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.STRIKETHROUGH_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.STRIKETHROUGH_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.StrikeThroughAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.STRIKETHROUGH_ACTION_PREFIX)));
    }

    public final javax.swing.Action getUnderlineAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.UNDERLINE_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.UNDERLINE_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.UnderlineAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.UNDERLINE_ACTION_PREFIX)));
    }

    public final javax.swing.Action getUndoAction() {
        if (!com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.containsKey(com.jmonkey.office.lexi.support.EditorActionManager.UNDO_ACTION_PREFIX)) {
            com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.put(com.jmonkey.office.lexi.support.EditorActionManager.UNDO_ACTION_PREFIX, new com.jmonkey.office.lexi.support.EditorActionManager.UndoAction());
        }
        return ((javax.swing.Action) (com.jmonkey.office.lexi.support.EditorActionManager._ACTIONS.get(com.jmonkey.office.lexi.support.EditorActionManager.UNDO_ACTION_PREFIX)));
    }

    public static final com.jmonkey.office.lexi.support.EditorActionManager instance() {
        if (com.jmonkey.office.lexi.support.EditorActionManager._INSTANCE == null) {
            com.jmonkey.office.lexi.support.EditorActionManager._INSTANCE = new com.jmonkey.office.lexi.support.EditorActionManager();
        }
        return com.jmonkey.office.lexi.support.EditorActionManager._INSTANCE;
    }

    /**
     * Returns true if there is an active
     * editor in the action manager. false
     * otherwise.
     *
     * @return boolean
     */
    public static final boolean isActiveEditor() {
        return com.jmonkey.office.lexi.support.EditorActionManager.instance()._EDITOR != null;
    }

    /**
     * Applies the given attributes to character
     * content.  If there is a selection, the attributes
     * are applied to the selection range.  If there
     * is no selection, the attributes are applied to
     * the input attribute set which defines the attributes
     * for any new text that gets inserted.
     *
     * @param editor
     * 		the editor
     * @param attr
     * 		the attributes
     * @param replace
     * 		if true, then replace the existing attributes first
     */
    protected final void setCharacterAttributes(javax.swing.JEditorPane editor, javax.swing.text.AttributeSet attr, boolean replace) {
        int p0 = editor.getSelectionStart();
        int p1 = editor.getSelectionEnd();
        if (p0 != p1) {
            // StyledDocument doc = getStyledDocument(editor);
            if (com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getTextComponent().getDocument() instanceof javax.swing.text.StyledDocument) {
                ((javax.swing.text.StyledDocument) (com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getTextComponent().getDocument())).setCharacterAttributes(p0, p1 - p0, attr, replace);
            }
        } else {
            // StyledEditorKit k = getStyledEditorKit(editor);
            javax.swing.text.MutableAttributeSet inputAttributes = com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getInputAttributes();
            if (replace) {
                inputAttributes.removeAttributes(inputAttributes);
            }
            inputAttributes.addAttributes(attr);
        }
    }

    /**
     * Applies the given attributes to paragraphs.  If
     * there is a selection, the attributes are applied
     * to the paragraphs that intersect the selection.
     * if there is no selection, the attributes are applied
     * to the paragraph at the current caret position.
     *
     * @param editor
     * 		the editor
     * @param attr
     * 		the attributes
     * @param replace
     * 		if true, replace the existing attributes first
     */
    protected final void setParagraphAttributes(javax.swing.JEditorPane editor, javax.swing.text.AttributeSet attr, boolean replace) {
        int p0 = editor.getSelectionStart();
        int p1 = editor.getSelectionEnd();
        // StyledDocument doc = getStyledDocument(editor);
        // doc.setParagraphAttributes(p0, p1 - p0, attr, replace);
        if (com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getTextComponent().getDocument() instanceof javax.swing.text.StyledDocument) {
            ((javax.swing.text.StyledDocument) (com.jmonkey.office.lexi.support.EditorActionManager.getActiveEditor().getTextComponent().getDocument())).setParagraphAttributes(p0, p1 - p0, attr, replace);
        }
    }

    /**
     * Returns a running thread wrapping the runnable object.
     *
     * @param r
     * 		java.lang.Runnable
     * @return java.lang.Thread
     */
    public static final java.lang.Runnable threads(java.lang.Runnable r) {
        java.lang.Thread t = new java.lang.Thread(r);
        t.start();
        return t;
        /* // The ThreadPool is broken, so we're taking a
        // simple aproach instead... this should be finxed
        // for speed, but will do for the moment.
        if(EditorActionManager.instance()._THREADPOOL == null) {
        EditorActionManager.instance()._THREADPOOL = new ThreadPool();
        EditorActionManager.instance()._THREADPOOL.loadBuffer();
        }
        return EditorActionManager.instance()._THREADPOOL;
         */
    }
}