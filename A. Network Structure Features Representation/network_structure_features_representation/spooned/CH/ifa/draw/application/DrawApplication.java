/* @(#)DrawApplication.java 5.1 */
package CH.ifa.draw.application;
/**
 * DrawApplication defines a standard presentation for standalone drawing
 * editors. The presentation is customized in subclasses. The application is
 * started as follows:
 *
 * <pre>
 * public static void main(String[] args) {
 * 	MayDrawApp window = new MyDrawApp();
 * 	window.open();
 * }
 * </pre>
 */
public class DrawApplication extends java.awt.Frame implements CH.ifa.draw.framework.DrawingEditor , CH.ifa.draw.util.PaletteListener {
    private CH.ifa.draw.framework.Drawing fDrawing;

    private CH.ifa.draw.framework.Tool fTool;

    private CH.ifa.draw.util.Iconkit fIconkit;

    private java.awt.TextField fStatusLine;

    private CH.ifa.draw.standard.StandardDrawingView fView;

    private CH.ifa.draw.standard.ToolButton fDefaultToolButton;

    private CH.ifa.draw.standard.ToolButton fSelectedToolButton;

    private java.lang.String fDrawingFilename;

    static java.lang.String fgUntitled = "untitled";

    // the image resource path
    private static final java.lang.String fgDrawPath = "/CH/ifa/draw/";

    public static final java.lang.String IMAGES = CH.ifa.draw.application.DrawApplication.fgDrawPath + "images/";

    /**
     * The index of the file menu in the menu bar.
     */
    public static final int FILE_MENU = 0;

    /**
     * The index of the edit menu in the menu bar.
     */
    public static final int EDIT_MENU = 1;

    /**
     * The index of the alignment menu in the menu bar.
     */
    public static final int ALIGNMENT_MENU = 2;

    /**
     * The index of the attributes menu in the menu bar.
     */
    public static final int ATTRIBUTES_MENU = 3;

    /**
     * Constructs a drawing window with a default title.
     */
    public DrawApplication() {
        super("JHotDraw");
    }

    /**
     * Constructs a drawing window with the given title.
     */
    public DrawApplication(java.lang.String title) {
        super(title);
    }

    /**
     * Opens the window and initializes its contents. Clients usually only call
     * but don't override it.
     */
    public void open() {
        fIconkit = new CH.ifa.draw.util.Iconkit(this);
        setLayout(new java.awt.BorderLayout());
        fView = createDrawingView();
        java.awt.Component contents = createContents(fView);
        add("Center", contents);
        // add("Center", fView);
        java.awt.Panel tools = createToolPalette();
        createTools(tools);
        add("West", tools);
        fStatusLine = createStatusLine();
        add("South", fStatusLine);
        java.awt.MenuBar mb = new java.awt.MenuBar();
        createMenus(mb);
        setMenuBar(mb);
        initDrawing();
        java.awt.Dimension d = defaultSize();
        setSize(d.width, d.height);
        addListeners();
        show();
    }

    /**
     * Registers the listeners for this window
     */
    protected void addListeners() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent event) {
                exit();
            }
        });
    }

    private void initDrawing() {
        fDrawing = createDrawing();
        fDrawingFilename = CH.ifa.draw.application.DrawApplication.fgUntitled;
        fView.setDrawing(fDrawing);
        toolDone();
    }

    /**
     * Creates the standard menus. Clients override this method to add
     * additional menus.
     */
    protected void createMenus(java.awt.MenuBar mb) {
        mb.add(createFileMenu());
        mb.add(createEditMenu());
        mb.add(createAlignmentMenu());
        mb.add(createAttributesMenu());
        mb.add(createDebugMenu());
    }

    /**
     * Creates the file menu. Clients override this method to add additional
     * menu items.
     */
    protected java.awt.Menu createFileMenu() {
        java.awt.Menu menu = new java.awt.Menu("File");
        java.awt.MenuItem mi = new java.awt.MenuItem("New", new java.awt.MenuShortcut('n'));
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                promptNew();
            }
        });
        menu.add(mi);
        mi = new java.awt.MenuItem("Open...", new java.awt.MenuShortcut('o'));
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                promptOpen();
            }
        });
        menu.add(mi);
        mi = new java.awt.MenuItem("Save As...", new java.awt.MenuShortcut('s'));
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                promptSaveAs();
            }
        });
        menu.add(mi);
        mi = new java.awt.MenuItem("Save As Serialized...");
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                promptSaveAsSerialized();
            }
        });
        menu.add(mi);
        menu.addSeparator();
        mi = new java.awt.MenuItem("Print...", new java.awt.MenuShortcut('p'));
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                print();
            }
        });
        menu.add(mi);
        menu.addSeparator();
        mi = new java.awt.MenuItem("Exit");
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                exit();
            }
        });
        menu.add(mi);
        return menu;
    }

    /**
     * Creates the edit menu. Clients override this method to add additional
     * menu items.
     */
    protected java.awt.Menu createEditMenu() {
        CH.ifa.draw.util.CommandMenu menu = new CH.ifa.draw.util.CommandMenu("Edit");
        menu.add(new CH.ifa.draw.standard.CutCommand("Cut", fView), new java.awt.MenuShortcut('x'));
        menu.add(new CH.ifa.draw.standard.CopyCommand("Copy", fView), new java.awt.MenuShortcut('c'));
        menu.add(new CH.ifa.draw.standard.PasteCommand("Paste", fView), new java.awt.MenuShortcut('v'));
        menu.addSeparator();
        menu.add(new CH.ifa.draw.standard.DuplicateCommand("Duplicate", fView), new java.awt.MenuShortcut('d'));
        menu.add(new CH.ifa.draw.standard.DeleteCommand("Delete", fView));
        menu.addSeparator();
        menu.add(new CH.ifa.draw.figures.GroupCommand("Group", fView));
        menu.add(new CH.ifa.draw.figures.UngroupCommand("Ungroup", fView));
        menu.addSeparator();
        menu.add(new CH.ifa.draw.standard.SendToBackCommand("Send to Back", fView));
        menu.add(new CH.ifa.draw.standard.BringToFrontCommand("Bring to Front", fView));
        return menu;
    }

    /**
     * Creates the alignment menu. Clients override this method to add
     * additional menu items.
     */
    protected java.awt.Menu createAlignmentMenu() {
        CH.ifa.draw.util.CommandMenu menu = new CH.ifa.draw.util.CommandMenu("Align");
        menu.add(new CH.ifa.draw.standard.ToggleGridCommand("Toggle Snap to Grid", fView, new java.awt.Point(4, 4)));
        menu.addSeparator();
        menu.add(new CH.ifa.draw.standard.AlignCommand("Lefts", fView, CH.ifa.draw.standard.AlignCommand.LEFTS));
        menu.add(new CH.ifa.draw.standard.AlignCommand("Centers", fView, CH.ifa.draw.standard.AlignCommand.CENTERS));
        menu.add(new CH.ifa.draw.standard.AlignCommand("Rights", fView, CH.ifa.draw.standard.AlignCommand.RIGHTS));
        menu.addSeparator();
        menu.add(new CH.ifa.draw.standard.AlignCommand("Tops", fView, CH.ifa.draw.standard.AlignCommand.TOPS));
        menu.add(new CH.ifa.draw.standard.AlignCommand("Middles", fView, CH.ifa.draw.standard.AlignCommand.MIDDLES));
        menu.add(new CH.ifa.draw.standard.AlignCommand("Bottoms", fView, CH.ifa.draw.standard.AlignCommand.BOTTOMS));
        return menu;
    }

    /**
     * Creates the debug menu. Clients override this method to add additional
     * menu items.
     */
    protected java.awt.Menu createDebugMenu() {
        java.awt.Menu menu = new java.awt.Menu("Debug");
        java.awt.MenuItem mi = new java.awt.MenuItem("Simple Update");
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                fView.setDisplayUpdate(new CH.ifa.draw.standard.SimpleUpdateStrategy());
            }
        });
        menu.add(mi);
        mi = new java.awt.MenuItem("Buffered Update");
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                fView.setDisplayUpdate(new CH.ifa.draw.standard.BufferedUpdateStrategy());
            }
        });
        menu.add(mi);
        return menu;
    }

    /**
     * Creates the attributes menu and its submenus. Clients override this
     * method to add additional menu items.
     */
    protected java.awt.Menu createAttributesMenu() {
        java.awt.Menu menu = new java.awt.Menu("Attributes");
        menu.add(createColorMenu("Fill Color", "FillColor"));
        menu.add(createColorMenu("Pen Color", "FrameColor"));
        menu.add(createArrowMenu());
        menu.addSeparator();
        menu.add(createFontMenu());
        menu.add(createFontSizeMenu());
        menu.add(createFontStyleMenu());
        menu.add(createColorMenu("Text Color", "TextColor"));
        return menu;
    }

    /**
     * Creates the color menu.
     */
    protected java.awt.Menu createColorMenu(java.lang.String title, java.lang.String attribute) {
        CH.ifa.draw.util.CommandMenu menu = new CH.ifa.draw.util.CommandMenu(title);
        for (int i = 0; i < CH.ifa.draw.util.ColorMap.size(); i++)
            menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand(CH.ifa.draw.util.ColorMap.name(i), attribute, CH.ifa.draw.util.ColorMap.color(i), fView));

        return menu;
    }

    /**
     * Creates the arrows menu.
     */
    protected java.awt.Menu createArrowMenu() {
        CH.ifa.draw.util.CommandMenu menu = new CH.ifa.draw.util.CommandMenu("Arrow");
        menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand("none", "ArrowMode", new java.lang.Integer(CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_NONE), fView));
        menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand("at Start", "ArrowMode", new java.lang.Integer(CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_START), fView));
        menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand("at End", "ArrowMode", new java.lang.Integer(CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_END), fView));
        menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand("at Both", "ArrowMode", new java.lang.Integer(CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_BOTH), fView));
        return menu;
    }

    /**
     * Creates the fonts menus. It installs all available fonts supported by the
     * toolkit implementation.
     */
    protected java.awt.Menu createFontMenu() {
        CH.ifa.draw.util.CommandMenu menu = new CH.ifa.draw.util.CommandMenu("Font");
        java.lang.String fonts[] = java.awt.Toolkit.getDefaultToolkit().getFontList();
        for (int i = 0; i < fonts.length; i++)
            menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand(fonts[i], "FontName", fonts[i], fView));

        return menu;
    }

    /**
     * Creates the font style menu with entries (Plain, Italic, Bold).
     */
    protected java.awt.Menu createFontStyleMenu() {
        CH.ifa.draw.util.CommandMenu menu = new CH.ifa.draw.util.CommandMenu("Font Style");
        menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand("Plain", "FontStyle", new java.lang.Integer(java.awt.Font.PLAIN), fView));
        menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand("Italic", "FontStyle", new java.lang.Integer(java.awt.Font.ITALIC), fView));
        menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand("Bold", "FontStyle", new java.lang.Integer(java.awt.Font.BOLD), fView));
        return menu;
    }

    /**
     * Creates the font size menu.
     */
    protected java.awt.Menu createFontSizeMenu() {
        CH.ifa.draw.util.CommandMenu menu = new CH.ifa.draw.util.CommandMenu("Font Size");
        int sizes[] = new int[]{ 9, 10, 12, 14, 18, 24, 36, 48, 72 };
        for (int i = 0; i < sizes.length; i++) {
            menu.add(new CH.ifa.draw.standard.ChangeAttributeCommand(java.lang.Integer.toString(sizes[i]), "FontSize", new java.lang.Integer(sizes[i]), fView));
        }
        return menu;
    }

    /**
     * Creates the tool palette.
     */
    protected java.awt.Panel createToolPalette() {
        java.awt.Panel palette = new java.awt.Panel();
        palette.setBackground(java.awt.Color.lightGray);
        palette.setLayout(new CH.ifa.draw.util.PaletteLayout(2, new java.awt.Point(2, 2)));
        return palette;
    }

    /**
     * Creates the tools. By default only the selection tool is added. Override
     * this method to add additional tools. Call the inherited method to include
     * the selection tool.
     *
     * @param palette
     * 		the palette where the tools are added.
     */
    protected void createTools(java.awt.Panel palette) {
        CH.ifa.draw.framework.Tool tool = createSelectionTool();
        fDefaultToolButton = createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "SEL", "Selection Tool", tool);
        palette.add(fDefaultToolButton);
    }

    /**
     * Creates the selection tool used in this editor. Override to use a custom
     * selection tool.
     */
    protected CH.ifa.draw.framework.Tool createSelectionTool() {
        return new CH.ifa.draw.standard.SelectionTool(view());
    }

    /**
     * Creates a tool button with the given image, tool, and text
     */
    protected CH.ifa.draw.standard.ToolButton createToolButton(java.lang.String iconName, java.lang.String toolName, CH.ifa.draw.framework.Tool tool) {
        return new CH.ifa.draw.standard.ToolButton(this, iconName, toolName, tool);
    }

    /**
     * Creates the drawing view used in this application. You need to override
     * this method to use a DrawingView subclass in your application. By default
     * a standard DrawingView is returned.
     */
    protected CH.ifa.draw.standard.StandardDrawingView createDrawingView() {
        java.awt.Dimension d = getDrawingViewSize();
        return new CH.ifa.draw.standard.StandardDrawingView(this, d.width, d.height);
    }

    /**
     * Override to define the dimensions of the drawing view.
     */
    protected java.awt.Dimension getDrawingViewSize() {
        return new java.awt.Dimension(400, 600);
    }

    /**
     * Creates the drawing used in this application. You need to override this
     * method to use a Drawing subclass in your application. By default a
     * standard Drawing is returned.
     */
    protected CH.ifa.draw.framework.Drawing createDrawing() {
        return new CH.ifa.draw.standard.StandardDrawing();
    }

    /**
     * Creates the contents component of the application frame. By default the
     * DrawingView is returned in a ScrollPane.
     */
    protected java.awt.Component createContents(CH.ifa.draw.standard.StandardDrawingView view) {
        java.awt.ScrollPane sp = new java.awt.ScrollPane();
        java.awt.Adjustable vadjust = sp.getVAdjustable();
        java.awt.Adjustable hadjust = sp.getHAdjustable();
        hadjust.setUnitIncrement(16);
        vadjust.setUnitIncrement(16);
        sp.add(view);
        return sp;
    }

    /**
     * Sets the drawing to be edited.
     */
    public void setDrawing(CH.ifa.draw.framework.Drawing drawing) {
        fView.setDrawing(drawing);
        fDrawing = drawing;
    }

    /**
     * Gets the default size of the window.
     */
    protected java.awt.Dimension defaultSize() {
        return new java.awt.Dimension(430, 406);
    }

    /**
     * Creates the status line.
     */
    protected java.awt.TextField createStatusLine() {
        java.awt.TextField field = new java.awt.TextField("No Tool", 40);
        field.setEditable(false);
        return field;
    }

    /**
     * Handles a user selection in the palette.
     *
     * @see PaletteListener
     */
    public void paletteUserSelected(CH.ifa.draw.util.PaletteButton button) {
        CH.ifa.draw.standard.ToolButton toolButton = ((CH.ifa.draw.standard.ToolButton) (button));
        setTool(toolButton.tool(), toolButton.name());
        setSelected(toolButton);
    }

    /**
     * Handles when the mouse enters or leaves a palette button.
     *
     * @see PaletteListener
     */
    public void paletteUserOver(CH.ifa.draw.util.PaletteButton button, boolean inside) {
        CH.ifa.draw.standard.ToolButton toolButton = ((CH.ifa.draw.standard.ToolButton) (button));
        if (inside)
            showStatus(toolButton.name());
        else
            showStatus(fSelectedToolButton.name());

    }

    /**
     * Gets the current drawing.
     *
     * @see DrawingEditor
     */
    public CH.ifa.draw.framework.Drawing drawing() {
        return fDrawing;
    }

    /**
     * Gets the current tool.
     *
     * @see DrawingEditor
     */
    public CH.ifa.draw.framework.Tool tool() {
        return fTool;
    }

    /**
     * Gets the current drawing view.
     *
     * @see DrawingEditor
     */
    public CH.ifa.draw.framework.DrawingView view() {
        return fView;
    }

    /**
     * Sets the default tool of the editor.
     *
     * @see DrawingEditor
     */
    public void toolDone() {
        if (fDefaultToolButton != null) {
            setTool(fDefaultToolButton.tool(), fDefaultToolButton.name());
            setSelected(fDefaultToolButton);
        }
    }

    /**
     * Handles a change of the current selection. Updates all menu items that
     * are selection sensitive.
     *
     * @see DrawingEditor
     */
    public void selectionChanged(CH.ifa.draw.framework.DrawingView view) {
        java.awt.MenuBar mb = getMenuBar();
        CH.ifa.draw.util.CommandMenu editMenu = ((CH.ifa.draw.util.CommandMenu) (mb.getMenu(CH.ifa.draw.application.DrawApplication.EDIT_MENU)));
        editMenu.checkEnabled();
        CH.ifa.draw.util.CommandMenu alignmentMenu = ((CH.ifa.draw.util.CommandMenu) (mb.getMenu(CH.ifa.draw.application.DrawApplication.ALIGNMENT_MENU)));
        alignmentMenu.checkEnabled();
    }

    /**
     * Shows a status message.
     *
     * @see DrawingEditor
     */
    public void showStatus(java.lang.String string) {
        fStatusLine.setText(string);
    }

    private void setTool(CH.ifa.draw.framework.Tool t, java.lang.String name) {
        if (fTool != null)
            fTool.deactivate();

        fTool = t;
        if (fTool != null) {
            fStatusLine.setText(name);
            fTool.activate();
        }
    }

    private void setSelected(CH.ifa.draw.standard.ToolButton button) {
        if (fSelectedToolButton != null)
            fSelectedToolButton.reset();

        fSelectedToolButton = button;
        if (fSelectedToolButton != null)
            fSelectedToolButton.select();

    }

    /**
     * Exits the application. You should never override this method
     */
    public void exit() {
        destroy();
        setVisible(false);// hide the Frame

        dispose();// tell windowing system to free resources

        java.lang.System.exit(0);
    }

    /**
     * Handles additional clean up operations. Override to destroy or release
     * drawing editor resources.
     */
    protected void destroy() {
    }

    /**
     * Resets the drawing to a new empty drawing.
     */
    public void promptNew() {
        initDrawing();
    }

    /**
     * Shows a file dialog and opens a drawing.
     */
    public void promptOpen() {
        java.awt.FileDialog dialog = new java.awt.FileDialog(this, "Open File...", java.awt.FileDialog.LOAD);
        dialog.show();
        java.lang.String filename = dialog.getFile();
        if (filename != null) {
            filename = stripTrailingAsterisks(filename);
            java.lang.String dirname = dialog.getDirectory();
            loadDrawing(dirname + filename);
        }
        dialog.dispose();
    }

    /**
     * Shows a file dialog and saves drawing.
     */
    public void promptSaveAs() {
        toolDone();
        java.lang.String path = getSavePath("Save File...");
        if (path != null) {
            if (!path.endsWith(".draw"))
                path += ".draw";

            saveAsStorableOutput(path);
        }
    }

    /**
     * Shows a file dialog and saves drawing.
     */
    public void promptSaveAsSerialized() {
        toolDone();
        java.lang.String path = getSavePath("Save File...");
        if (path != null) {
            if (!path.endsWith(".ser"))
                path += ".ser";

            saveAsObjectOutput(path);
        }
    }

    /**
     * Prints the drawing.
     */
    public void print() {
        fTool.deactivate();
        java.awt.PrintJob printJob = getToolkit().getPrintJob(this, "Print Drawing", null);
        if (printJob != null) {
            java.awt.Graphics pg = printJob.getGraphics();
            if (pg != null) {
                fView.printAll(pg);
                pg.dispose();// flush page

            }
            printJob.end();
        }
        fTool.activate();
    }

    private java.lang.String getSavePath(java.lang.String title) {
        java.lang.String path = null;
        java.awt.FileDialog dialog = new java.awt.FileDialog(this, title, java.awt.FileDialog.SAVE);
        dialog.show();
        java.lang.String filename = dialog.getFile();
        if (filename != null) {
            filename = stripTrailingAsterisks(filename);
            java.lang.String dirname = dialog.getDirectory();
            path = dirname + filename;
        }
        dialog.dispose();
        return path;
    }

    private java.lang.String stripTrailingAsterisks(java.lang.String filename) {
        // workaround for bug on NT
        if (filename.endsWith("*.*"))
            return filename.substring(0, filename.length() - 4);
        else
            return filename;

    }

    private void saveAsStorableOutput(java.lang.String file) {
        // TBD: should write a MIME header
        try {
            java.io.FileOutputStream stream = new java.io.FileOutputStream(file);
            CH.ifa.draw.util.StorableOutput output = new CH.ifa.draw.util.StorableOutput(stream);
            output.writeStorable(fDrawing);
            output.close();
        } catch (java.io.IOException e) {
            showStatus(e.toString());
        }
    }

    private void saveAsObjectOutput(java.lang.String file) {
        // TBD: should write a MIME header
        try {
            java.io.FileOutputStream stream = new java.io.FileOutputStream(file);
            java.io.ObjectOutput output = new java.io.ObjectOutputStream(stream);
            output.writeObject(fDrawing);
            output.close();
        } catch (java.io.IOException e) {
            showStatus(e.toString());
        }
    }

    private void loadDrawing(java.lang.String file) {
        toolDone();
        java.lang.String type = guessType(file);
        if (type.equals("storable"))
            readFromStorableInput(file);
        else if (type.equals("serialized"))
            readFromObjectInput(file);
        else
            showStatus("Unknown file type");

    }

    private void readFromStorableInput(java.lang.String file) {
        try {
            java.io.FileInputStream stream = new java.io.FileInputStream(file);
            CH.ifa.draw.util.StorableInput input = new CH.ifa.draw.util.StorableInput(stream);
            fDrawing.release();
            fDrawing = ((CH.ifa.draw.framework.Drawing) (input.readStorable()));
            fView.setDrawing(fDrawing);
        } catch (java.io.IOException e) {
            initDrawing();
            showStatus("Error: " + e);
        }
    }

    private void readFromObjectInput(java.lang.String file) {
        try {
            java.io.FileInputStream stream = new java.io.FileInputStream(file);
            java.io.ObjectInput input = new java.io.ObjectInputStream(stream);
            fDrawing.release();
            fDrawing = ((CH.ifa.draw.framework.Drawing) (input.readObject()));
            fView.setDrawing(fDrawing);
        } catch (java.io.IOException e) {
            initDrawing();
            showStatus("Error: " + e);
        } catch (java.lang.ClassNotFoundException e) {
            initDrawing();
            showStatus("Class not found: " + e);
        }
    }

    private java.lang.String guessType(java.lang.String file) {
        if (file.endsWith(".draw"))
            return "storable";

        if (file.endsWith(".ser"))
            return "serialized";

        return "unknown";
    }
}