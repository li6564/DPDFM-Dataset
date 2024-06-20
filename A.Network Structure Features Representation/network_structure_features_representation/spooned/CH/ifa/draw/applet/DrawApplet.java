/* @(#)DrawApplet.java 5.1 */
package CH.ifa.draw.applet;
/**
 * DrawApplication defines a standard presentation for
 * a drawing editor that is run as an applet. The presentation is
 * customized in subclasses.<p>
 * Supported applet parameters: <br>
 * <i>DRAWINGS</i>: a blank separated list of drawing names that is
 *           shown in the drawings choice.
 */
public class DrawApplet extends java.applet.Applet implements CH.ifa.draw.framework.DrawingEditor , CH.ifa.draw.util.PaletteListener {
    private transient CH.ifa.draw.framework.Drawing fDrawing;

    private transient CH.ifa.draw.framework.Tool fTool;

    private transient CH.ifa.draw.standard.StandardDrawingView fView;

    private transient CH.ifa.draw.standard.ToolButton fDefaultToolButton;

    private transient CH.ifa.draw.standard.ToolButton fSelectedToolButton;

    private transient boolean fSimpleUpdate;

    private transient java.awt.Button fUpdateButton;

    private transient java.awt.Choice fFrameColor;

    private transient java.awt.Choice fFillColor;

    // transient private Choice          fTextColor;
    private transient java.awt.Choice fArrowChoice;

    private transient java.awt.Choice fFontChoice;

    private transient java.lang.Thread fSleeper;

    private CH.ifa.draw.util.Iconkit fIconkit;

    static java.lang.String fgUntitled = "untitled";

    private static final java.lang.String fgDrawPath = "/CH/ifa/draw/";

    public static final java.lang.String IMAGES = CH.ifa.draw.applet.DrawApplet.fgDrawPath + "images/";

    /**
     * Initializes the applet and creates its contents.
     */
    public void init() {
        fIconkit = new CH.ifa.draw.util.Iconkit(this);
        setLayout(new java.awt.BorderLayout());
        fView = createDrawingView();
        java.awt.Panel attributes = createAttributesPanel();
        createAttributeChoices(attributes);
        add("North", attributes);
        java.awt.Panel toolPanel = createToolPalette();
        createTools(toolPanel);
        add("West", toolPanel);
        add("Center", fView);
        java.awt.Panel buttonPalette = createButtonPanel();
        createButtons(buttonPalette);
        add("South", buttonPalette);
        initDrawing();
        setBufferedDisplayUpdate();
        setupAttributes();
    }

    /* Gets the iconkit to be used in the applet. */
    /**
     * ** not sure whether this will still be needed on 1.1 enabled browsers
     * protected Iconkit iconkit() {
     * if (fIconkit == null) {
     *
     * startSleeper();
     * loadAllImages(this); // blocks until images loaded
     * stopSleeper();
     * }
     * return fIconkit;
     * }
     */
    /**
     * Creates the attributes panel.
     */
    protected java.awt.Panel createAttributesPanel() {
        java.awt.Panel panel = new java.awt.Panel();
        panel.setLayout(new CH.ifa.draw.util.PaletteLayout(2, new java.awt.Point(2, 2), false));
        return panel;
    }

    /**
     * Creates the attribute choices. Override to add additional
     * choices.
     */
    protected void createAttributeChoices(java.awt.Panel panel) {
        panel.add(new java.awt.Label("Fill"));
        fFillColor = createColorChoice("FillColor");
        panel.add(fFillColor);
        // panel.add(new Label("Text"));
        // fTextColor = createColorChoice("TextColor");
        // panel.add(fTextColor);
        panel.add(new java.awt.Label("Pen"));
        fFrameColor = createColorChoice("FrameColor");
        panel.add(fFrameColor);
        panel.add(new java.awt.Label("Arrow"));
        CH.ifa.draw.util.CommandChoice choice = new CH.ifa.draw.util.CommandChoice();
        fArrowChoice = choice;
        choice.addItem(new CH.ifa.draw.standard.ChangeAttributeCommand("none", "ArrowMode", new java.lang.Integer(CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_NONE), fView));
        choice.addItem(new CH.ifa.draw.standard.ChangeAttributeCommand("at Start", "ArrowMode", new java.lang.Integer(CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_START), fView));
        choice.addItem(new CH.ifa.draw.standard.ChangeAttributeCommand("at End", "ArrowMode", new java.lang.Integer(CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_END), fView));
        choice.addItem(new CH.ifa.draw.standard.ChangeAttributeCommand("at Both", "ArrowMode", new java.lang.Integer(CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_BOTH), fView));
        panel.add(fArrowChoice);
        panel.add(new java.awt.Label("Font"));
        fFontChoice = createFontChoice();
        panel.add(fFontChoice);
    }

    /**
     * Creates the color choice for the given attribute.
     */
    protected java.awt.Choice createColorChoice(java.lang.String attribute) {
        CH.ifa.draw.util.CommandChoice choice = new CH.ifa.draw.util.CommandChoice();
        for (int i = 0; i < CH.ifa.draw.util.ColorMap.size(); i++)
            choice.addItem(new CH.ifa.draw.standard.ChangeAttributeCommand(CH.ifa.draw.util.ColorMap.name(i), attribute, CH.ifa.draw.util.ColorMap.color(i), fView));

        return choice;
    }

    /**
     * Creates the font choice. The choice is filled with
     * all the fonts supported by the toolkit.
     */
    protected java.awt.Choice createFontChoice() {
        CH.ifa.draw.util.CommandChoice choice = new CH.ifa.draw.util.CommandChoice();
        java.lang.String fonts[] = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (int i = 0; i < fonts.length; i++)
            choice.addItem(new CH.ifa.draw.standard.ChangeAttributeCommand(fonts[i], "FontName", fonts[i], fView));

        return choice;
    }

    /**
     * Creates the buttons panel.
     */
    protected java.awt.Panel createButtonPanel() {
        java.awt.Panel panel = new java.awt.Panel();
        panel.setLayout(new CH.ifa.draw.util.PaletteLayout(2, new java.awt.Point(2, 2), false));
        return panel;
    }

    /**
     * Creates the buttons shown in the buttons panel. Override to
     * add additional buttons.
     *
     * @param panel
     * 		the buttons panel.
     */
    protected void createButtons(java.awt.Panel panel) {
        panel.add(new CH.ifa.draw.util.Filler(24, 20));
        java.awt.Choice drawingChoice = new java.awt.Choice();
        drawingChoice.addItem(CH.ifa.draw.applet.DrawApplet.fgUntitled);
        java.lang.String param = getParameter("DRAWINGS");
        if (param == null)
            param = "";

        java.util.StringTokenizer st = new java.util.StringTokenizer(param);
        while (st.hasMoreTokens())
            drawingChoice.addItem(st.nextToken());

        // offer choice only if more than one
        if (drawingChoice.getItemCount() > 1)
            panel.add(drawingChoice);
        else
            panel.add(new java.awt.Label(CH.ifa.draw.applet.DrawApplet.fgUntitled));

        drawingChoice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    loadDrawing(((java.lang.String) (e.getItem())));
                }
            }
        });
        panel.add(new CH.ifa.draw.util.Filler(6, 20));
        java.awt.Button button;
        button = new CH.ifa.draw.util.CommandButton(new CH.ifa.draw.standard.DeleteCommand("Delete", fView));
        panel.add(button);
        button = new CH.ifa.draw.util.CommandButton(new CH.ifa.draw.standard.DuplicateCommand("Duplicate", fView));
        panel.add(button);
        button = new CH.ifa.draw.util.CommandButton(new CH.ifa.draw.figures.GroupCommand("Group", fView));
        panel.add(button);
        button = new CH.ifa.draw.util.CommandButton(new CH.ifa.draw.figures.UngroupCommand("Ungroup", fView));
        panel.add(button);
        button = new java.awt.Button("Help");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                showHelp();
            }
        });
        panel.add(button);
        fUpdateButton = new java.awt.Button("Simple Update");
        fUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                if (fSimpleUpdate)
                    setBufferedDisplayUpdate();
                else
                    setSimpleDisplayUpdate();

            }
        });
        // panel.add(fUpdateButton); // not shown currently
    }

    /**
     * Creates the tools palette.
     */
    protected java.awt.Panel createToolPalette() {
        java.awt.Panel palette = new java.awt.Panel();
        palette.setLayout(new CH.ifa.draw.util.PaletteLayout(2, new java.awt.Point(2, 2)));
        return palette;
    }

    /**
     * Creates the tools. By default only the selection tool is added.
     * Override this method to add additional tools.
     * Call the inherited method to include the selection tool.
     *
     * @param palette
     * 		the palette where the tools are added.
     */
    protected void createTools(java.awt.Panel palette) {
        CH.ifa.draw.framework.Tool tool = createSelectionTool();
        fDefaultToolButton = createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "SEL", "Selection Tool", tool);
        palette.add(fDefaultToolButton);
    }

    /**
     * Creates the selection tool used in this editor. Override to use
     * a custom selection tool.
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
     * Creates the drawing used in this application.
     * You need to override this method to use a Drawing
     * subclass in your application. By default a standard
     * Drawing is returned.
     */
    protected CH.ifa.draw.framework.Drawing createDrawing() {
        return new CH.ifa.draw.standard.StandardDrawing();
    }

    /**
     * Creates the drawing view used in this application.
     * You need to override this method to use a DrawingView
     * subclass in your application. By default a standard
     * DrawingView is returned.
     */
    protected CH.ifa.draw.standard.StandardDrawingView createDrawingView() {
        return new CH.ifa.draw.standard.StandardDrawingView(this, 410, 370);
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
        if (inside)
            showStatus(((CH.ifa.draw.standard.ToolButton) (button)).name());
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
        setTool(fDefaultToolButton.tool(), fDefaultToolButton.name());
        setSelected(fDefaultToolButton);
    }

    /**
     * Handles a change of the current selection. Updates all
     * menu items that are selection sensitive.
     *
     * @see DrawingEditor
     */
    public void selectionChanged(CH.ifa.draw.framework.DrawingView view) {
        setupAttributes();
    }

    private void initDrawing() {
        fDrawing = createDrawing();
        fView.setDrawing(fDrawing);
        toolDone();
    }

    private void setTool(CH.ifa.draw.framework.Tool t, java.lang.String name) {
        if (fTool != null)
            fTool.deactivate();

        fTool = t;
        if (fTool != null) {
            showStatus(name);
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

    protected void loadDrawing(java.lang.String param) {
        if (param == CH.ifa.draw.applet.DrawApplet.fgUntitled) {
            fDrawing.release();
            initDrawing();
            return;
        }
        java.lang.String filename = getParameter(param);
        if (filename != null)
            readDrawing(filename);

    }

    private void readDrawing(java.lang.String filename) {
        toolDone();
        java.lang.String type = guessType(filename);
        if (type.equals("storable"))
            readFromStorableInput(filename);
        else if (type.equals("serialized"))
            readFromObjectInput(filename);
        else
            showStatus("Unknown file type");

    }

    private void readFromStorableInput(java.lang.String filename) {
        try {
            java.net.URL url = new java.net.URL(getCodeBase(), filename);
            java.io.InputStream stream = url.openStream();
            CH.ifa.draw.util.StorableInput input = new CH.ifa.draw.util.StorableInput(stream);
            fDrawing.release();
            fDrawing = ((CH.ifa.draw.framework.Drawing) (input.readStorable()));
            fView.setDrawing(fDrawing);
        } catch (java.io.IOException e) {
            initDrawing();
            showStatus("Error:" + e);
        }
    }

    private void readFromObjectInput(java.lang.String filename) {
        try {
            java.net.URL url = new java.net.URL(getCodeBase(), filename);
            java.io.InputStream stream = url.openStream();
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

    private void setupAttributes() {
        java.awt.Color frameColor = ((java.awt.Color) (CH.ifa.draw.figures.AttributeFigure.getDefaultAttribute("FrameColor")));
        java.awt.Color fillColor = ((java.awt.Color) (CH.ifa.draw.figures.AttributeFigure.getDefaultAttribute("FillColor")));
        // Color textColor =
        // (Color) AttributeFigure.getDefaultAttribute("TextColor");
        java.lang.Integer arrowMode = ((java.lang.Integer) (CH.ifa.draw.figures.AttributeFigure.getDefaultAttribute("ArrowMode")));
        java.lang.String fontName = ((java.lang.String) (CH.ifa.draw.figures.AttributeFigure.getDefaultAttribute("FontName")));
        CH.ifa.draw.framework.FigureEnumeration k = fView.selectionElements();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure f = k.nextFigure();
            frameColor = ((java.awt.Color) (f.getAttribute("FrameColor")));
            fillColor = ((java.awt.Color) (f.getAttribute("FillColor")));
            // textColor = (Color) f.getAttribute("TextColor");
            arrowMode = ((java.lang.Integer) (f.getAttribute("ArrowMode")));
            fontName = ((java.lang.String) (f.getAttribute("FontName")));
        } 
        fFrameColor.select(CH.ifa.draw.util.ColorMap.colorIndex(frameColor));
        fFillColor.select(CH.ifa.draw.util.ColorMap.colorIndex(fillColor));
        // fTextColor.select(ColorMap.colorIndex(textColor));
        if (arrowMode != null)
            fArrowChoice.select(arrowMode.intValue());

        if (fontName != null)
            fFontChoice.select(fontName);

    }

    protected void setSimpleDisplayUpdate() {
        fView.setDisplayUpdate(new CH.ifa.draw.standard.SimpleUpdateStrategy());
        fUpdateButton.setLabel("Simple Update");
        fSimpleUpdate = true;
    }

    protected void setBufferedDisplayUpdate() {
        fView.setDisplayUpdate(new CH.ifa.draw.standard.BufferedUpdateStrategy());
        fUpdateButton.setLabel("Buffered Update");
        fSimpleUpdate = false;
    }

    /**
     * Shows a help page for the applet. The URL of the help
     * page is derived as follows: codeBase+appletClassname+Help.html"
     */
    protected void showHelp() {
        try {
            java.lang.String appletPath = getClass().getName().replace('.', '/');
            java.net.URL url = new java.net.URL(getCodeBase(), appletPath + "Help.html");
            getAppletContext().showDocument(url, "Help");
        } catch (java.io.IOException e) {
            showStatus("Help file not found");
        }
    }

    /**
     * *** netscape browser work around ***
     */
    private void startSleeper() {
        if (fSleeper == null)
            fSleeper = new CH.ifa.draw.applet.SleeperThread(this);

        fSleeper.start();
    }

    private void stopSleeper() {
        if (fSleeper != null)
            fSleeper.stop();

    }
}