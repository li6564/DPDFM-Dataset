/* @(#)StandardDrawingView.java 5.1 */
package CH.ifa.draw.standard;
/**
 * The standard implementation of DrawingView.
 *
 * @see DrawingView
 * @see Painter
 * @see Tool
 */
public class StandardDrawingView extends java.awt.Panel implements CH.ifa.draw.framework.DrawingView , java.awt.event.MouseListener , java.awt.event.MouseMotionListener , java.awt.event.KeyListener {
    /**
     * The DrawingEditor of the view.
     *
     * @see #tool
     * @see #setStatus
     */
    private transient CH.ifa.draw.framework.DrawingEditor fEditor;

    /**
     * The shown drawing.
     */
    private CH.ifa.draw.framework.Drawing fDrawing;

    /**
     * the accumulated damaged area
     */
    private transient java.awt.Rectangle fDamage = null;

    /**
     * The list of currently selected figures.
     */
    private transient java.util.Vector fSelection;

    /**
     * The shown selection handles.
     */
    private transient java.util.Vector fSelectionHandles;

    /**
     * The preferred size of the view
     */
    private java.awt.Dimension fViewSize;

    /**
     * The position of the last mouse click
     * inside the view.
     */
    private java.awt.Point fLastClick;

    /**
     * A vector of optional backgrounds. The vector maintains
     * a list a view painters that are drawn before the contents,
     * that is in the background.
     */
    private java.util.Vector fBackgrounds = null;

    /**
     * A vector of optional foregrounds. The vector maintains
     * a list a view painters that are drawn after the contents,
     * that is in the foreground.
     */
    private java.util.Vector fForegrounds = null;

    /**
     * The update strategy used to repair the view.
     */
    private CH.ifa.draw.framework.Painter fUpdateStrategy;

    /**
     * The grid used to constrain points for snap to
     * grid functionality.
     */
    private CH.ifa.draw.framework.PointConstrainer fConstrainer;

    /* Serialization support. In JavaDraw only the Drawing is serialized.
    However, for beans support StandardDrawingView supports
    serialization
     */
    private static final long serialVersionUID = -3878153366174603336L;

    private int drawingViewSerializedDataVersion = 1;

    /**
     * Constructs the view.
     */
    public StandardDrawingView(CH.ifa.draw.framework.DrawingEditor editor, int width, int height) {
        fEditor = editor;
        fViewSize = new java.awt.Dimension(width, height);
        fLastClick = new java.awt.Point(0, 0);
        fConstrainer = null;
        fSelection = new java.util.Vector();
        setDisplayUpdate(new CH.ifa.draw.standard.BufferedUpdateStrategy());
        setBackground(java.awt.Color.lightGray);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    /**
     * Sets the view's editor.
     */
    public void setEditor(CH.ifa.draw.framework.DrawingEditor editor) {
        fEditor = editor;
    }

    /**
     * Gets the current tool.
     */
    public CH.ifa.draw.framework.Tool tool() {
        return fEditor.tool();
    }

    /**
     * Gets the drawing.
     */
    public CH.ifa.draw.framework.Drawing drawing() {
        return fDrawing;
    }

    /**
     * Sets and installs another drawing in the view.
     */
    public void setDrawing(CH.ifa.draw.framework.Drawing d) {
        clearSelection();
        if (fDrawing != null)
            fDrawing.removeDrawingChangeListener(this);

        fDrawing = d;
        if (fDrawing != null)
            fDrawing.addDrawingChangeListener(this);

        checkMinimumSize();
        repaint();
    }

    /**
     * Gets the editor.
     */
    public CH.ifa.draw.framework.DrawingEditor editor() {
        return fEditor;
    }

    /**
     * Adds a figure to the drawing.
     *
     * @return the added figure.
     */
    public CH.ifa.draw.framework.Figure add(CH.ifa.draw.framework.Figure figure) {
        return drawing().add(figure);
    }

    /**
     * Removes a figure from the drawing.
     *
     * @return the removed figure
     */
    public CH.ifa.draw.framework.Figure remove(CH.ifa.draw.framework.Figure figure) {
        return drawing().remove(figure);
    }

    /**
     * Adds a vector of figures to the drawing.
     */
    public void addAll(java.util.Vector figures) {
        CH.ifa.draw.framework.FigureEnumeration k = new CH.ifa.draw.standard.FigureEnumerator(figures);
        while (k.hasMoreElements())
            add(k.nextFigure());

    }

    /**
     * Gets the minimum dimension of the drawing.
     */
    public java.awt.Dimension getMinimumSize() {
        return fViewSize;
    }

    /**
     * Gets the preferred dimension of the drawing..
     */
    public java.awt.Dimension getPreferredSize() {
        return getMinimumSize();
    }

    /**
     * Sets the current display update strategy.
     *
     * @see UpdateStrategy
     */
    public void setDisplayUpdate(CH.ifa.draw.framework.Painter updateStrategy) {
        fUpdateStrategy = updateStrategy;
    }

    /**
     * Gets the currently selected figures.
     *
     * @return a vector with the selected figures. The vector
    is a copy of the current selection.
     */
    public java.util.Vector selection() {
        // protect the vector with the current selection
        return ((java.util.Vector) (fSelection.clone()));
    }

    /**
     * Gets an enumeration over the currently selected figures.
     */
    public CH.ifa.draw.framework.FigureEnumeration selectionElements() {
        return new CH.ifa.draw.standard.FigureEnumerator(fSelection);
    }

    /**
     * Gets the currently selected figures in Z order.
     *
     * @see #selection
     * @return a vector with the selected figures. The vector
    is a copy of the current selection.
     */
    public java.util.Vector selectionZOrdered() {
        java.util.Vector result = new java.util.Vector(fSelection.size());
        CH.ifa.draw.framework.FigureEnumeration figures = drawing().figures();
        while (figures.hasMoreElements()) {
            CH.ifa.draw.framework.Figure f = figures.nextFigure();
            if (fSelection.contains(f)) {
                result.addElement(f);
            }
        } 
        return result;
    }

    /**
     * Gets the number of selected figures.
     */
    public int selectionCount() {
        return fSelection.size();
    }

    /**
     * Adds a figure to the current selection.
     */
    public void addToSelection(CH.ifa.draw.framework.Figure figure) {
        if (!fSelection.contains(figure)) {
            fSelection.addElement(figure);
            fSelectionHandles = null;
            figure.invalidate();
            selectionChanged();
        }
    }

    /**
     * Adds a vector of figures to the current selection.
     */
    public void addToSelectionAll(java.util.Vector figures) {
        CH.ifa.draw.framework.FigureEnumeration k = new CH.ifa.draw.standard.FigureEnumerator(figures);
        while (k.hasMoreElements())
            addToSelection(k.nextFigure());

    }

    /**
     * Removes a figure from the selection.
     */
    public void removeFromSelection(CH.ifa.draw.framework.Figure figure) {
        if (fSelection.contains(figure)) {
            fSelection.removeElement(figure);
            fSelectionHandles = null;
            figure.invalidate();
            selectionChanged();
        }
    }

    /**
     * If a figure isn't selected it is added to the selection.
     * Otherwise it is removed from the selection.
     */
    public void toggleSelection(CH.ifa.draw.framework.Figure figure) {
        if (fSelection.contains(figure))
            removeFromSelection(figure);
        else
            addToSelection(figure);

        selectionChanged();
    }

    /**
     * Clears the current selection.
     */
    public void clearSelection() {
        CH.ifa.draw.framework.Figure figure;
        CH.ifa.draw.framework.FigureEnumeration k = selectionElements();
        while (k.hasMoreElements())
            k.nextFigure().invalidate();

        fSelection = new java.util.Vector();
        fSelectionHandles = null;
        selectionChanged();
    }

    /**
     * Gets an enumeration of the currently active handles.
     */
    private java.util.Enumeration selectionHandles() {
        if (fSelectionHandles == null) {
            fSelectionHandles = new java.util.Vector();
            CH.ifa.draw.framework.FigureEnumeration k = selectionElements();
            while (k.hasMoreElements()) {
                CH.ifa.draw.framework.Figure figure = k.nextFigure();
                java.util.Enumeration kk = figure.handles().elements();
                while (kk.hasMoreElements())
                    fSelectionHandles.addElement(kk.nextElement());

            } 
        }
        return fSelectionHandles.elements();
    }

    /**
     * Gets the current selection as a FigureSelection. A FigureSelection
     * can be cut, copied, pasted.
     */
    public CH.ifa.draw.framework.FigureSelection getFigureSelection() {
        return new CH.ifa.draw.framework.FigureSelection(selectionZOrdered());
    }

    /**
     * Finds a handle at the given coordinates.
     *
     * @return the hit handle, null if no handle is found.
     */
    public CH.ifa.draw.framework.Handle findHandle(int x, int y) {
        CH.ifa.draw.framework.Handle handle;
        java.util.Enumeration k = selectionHandles();
        while (k.hasMoreElements()) {
            handle = ((CH.ifa.draw.framework.Handle) (k.nextElement()));
            if (handle.containsPoint(x, y))
                return handle;

        } 
        return null;
    }

    /**
     * Informs that the current selection changed.
     * By default this event is forwarded to the
     * drawing editor.
     */
    protected void selectionChanged() {
        fEditor.selectionChanged(this);
    }

    /**
     * Gets the position of the last click inside the view.
     */
    public java.awt.Point lastClick() {
        return fLastClick;
    }

    /**
     * Sets the grid spacing that is used to constrain points.
     */
    public void setConstrainer(CH.ifa.draw.framework.PointConstrainer c) {
        fConstrainer = c;
    }

    /**
     * Gets the current constrainer.
     */
    public CH.ifa.draw.framework.PointConstrainer getConstrainer() {
        return fConstrainer;
    }

    /**
     * Constrains a point to the current grid.
     */
    protected java.awt.Point constrainPoint(java.awt.Point p) {
        // constrin to view size
        java.awt.Dimension size = getSize();
        // p.x = Math.min(size.width, Math.max(1, p.x));
        // p.y = Math.min(size.height, Math.max(1, p.y));
        p.x = CH.ifa.draw.util.Geom.range(1, size.width, p.x);
        p.y = CH.ifa.draw.util.Geom.range(1, size.height, p.y);
        if (fConstrainer != null)
            return fConstrainer.constrainPoint(p);

        return p;
    }

    /**
     * Handles mouse down events. The event is delegated to the
     * currently active tool.
     *
     * @return whether the event was handled.
     */
    public void mousePressed(java.awt.event.MouseEvent e) {
        requestFocus();// JDK1.1

        java.awt.Point p = constrainPoint(new java.awt.Point(e.getX(), e.getY()));
        fLastClick = new java.awt.Point(e.getX(), e.getY());
        tool().mouseDown(e, p.x, p.y);
        checkDamage();
    }

    /**
     * Handles mouse drag events. The event is delegated to the
     * currently active tool.
     *
     * @return whether the event was handled.
     */
    public void mouseDragged(java.awt.event.MouseEvent e) {
        java.awt.Point p = constrainPoint(new java.awt.Point(e.getX(), e.getY()));
        tool().mouseDrag(e, p.x, p.y);
        checkDamage();
    }

    /**
     * Handles mouse move events. The event is delegated to the
     * currently active tool.
     *
     * @return whether the event was handled.
     */
    public void mouseMoved(java.awt.event.MouseEvent e) {
        tool().mouseMove(e, e.getX(), e.getY());
    }

    /**
     * Handles mouse up events. The event is delegated to the
     * currently active tool.
     *
     * @return whether the event was handled.
     */
    public void mouseReleased(java.awt.event.MouseEvent e) {
        java.awt.Point p = constrainPoint(new java.awt.Point(e.getX(), e.getY()));
        tool().mouseUp(e, p.x, p.y);
        checkDamage();
    }

    /**
     * Handles key down events. Cursor keys are handled
     * by the view the other key events are delegated to the
     * currently active tool.
     *
     * @return whether the event was handled.
     */
    public void keyPressed(java.awt.event.KeyEvent e) {
        int code = e.getKeyCode();
        if ((code == java.awt.event.KeyEvent.VK_BACK_SPACE) || (code == java.awt.event.KeyEvent.VK_DELETE)) {
            CH.ifa.draw.util.Command cmd = new CH.ifa.draw.standard.DeleteCommand("Delete", this);
            cmd.execute();
        } else if ((((code == java.awt.event.KeyEvent.VK_DOWN) || (code == java.awt.event.KeyEvent.VK_UP)) || (code == java.awt.event.KeyEvent.VK_RIGHT)) || (code == java.awt.event.KeyEvent.VK_LEFT)) {
            handleCursorKey(code);
        } else {
            tool().keyDown(e, code);
        }
        checkDamage();
    }

    /**
     * Handles cursor keys by moving all the selected figures
     * one grid point in the cursor direction.
     */
    protected void handleCursorKey(int key) {
        int dx = 0;
        int dy = 0;
        int stepX = 1;
        int stepY = 1;
        // should consider Null Object.
        if (fConstrainer != null) {
            stepX = fConstrainer.getStepX();
            stepY = fConstrainer.getStepY();
        }
        switch (key) {
            case java.awt.event.KeyEvent.VK_DOWN :
                dy = stepY;
                break;
            case java.awt.event.KeyEvent.VK_UP :
                dy = -stepY;
                break;
            case java.awt.event.KeyEvent.VK_RIGHT :
                dx = stepX;
                break;
            case java.awt.event.KeyEvent.VK_LEFT :
                dx = -stepX;
                break;
        }
        moveSelection(dx, dy);
    }

    private void moveSelection(int dx, int dy) {
        CH.ifa.draw.framework.FigureEnumeration figures = selectionElements();
        while (figures.hasMoreElements())
            figures.nextFigure().moveBy(dx, dy);

        checkDamage();
    }

    /**
     * Refreshes the drawing if there is some accumulated damage
     */
    public synchronized void checkDamage() {
        java.util.Enumeration each = drawing().drawingChangeListeners();
        while (each.hasMoreElements()) {
            java.lang.Object l = each.nextElement();
            if (l instanceof CH.ifa.draw.framework.DrawingView) {
                ((CH.ifa.draw.framework.DrawingView) (l)).repairDamage();
            }
        } 
    }

    public void repairDamage() {
        if (fDamage != null) {
            repaint(fDamage.x, fDamage.y, fDamage.width, fDamage.height);
            fDamage = null;
        }
    }

    public void drawingInvalidated(CH.ifa.draw.framework.DrawingChangeEvent e) {
        java.awt.Rectangle r = e.getInvalidatedRectangle();
        if (fDamage == null)
            fDamage = r;
        else
            fDamage.add(r);

    }

    public void drawingRequestUpdate(CH.ifa.draw.framework.DrawingChangeEvent e) {
        repairDamage();
    }

    /**
     * Updates the drawing view.
     */
    public void update(java.awt.Graphics g) {
        paint(g);
    }

    /**
     * Paints the drawing view. The actual drawing is delegated to
     * the current update strategy.
     *
     * @see Painter
     */
    public void paint(java.awt.Graphics g) {
        fUpdateStrategy.draw(g, this);
    }

    /**
     * Draws the contents of the drawing view.
     * The view has three layers: background, drawing, handles.
     * The layers are drawn in back to front order.
     */
    public void drawAll(java.awt.Graphics g) {
        boolean isPrinting = g instanceof java.awt.PrintGraphics;
        drawBackground(g);
        if ((fBackgrounds != null) && (!isPrinting))
            drawPainters(g, fBackgrounds);

        drawDrawing(g);
        if ((fForegrounds != null) && (!isPrinting))
            drawPainters(g, fForegrounds);

        if (!isPrinting)
            drawHandles(g);

    }

    /**
     * Draws the currently active handles.
     */
    public void drawHandles(java.awt.Graphics g) {
        java.util.Enumeration k = selectionHandles();
        while (k.hasMoreElements())
            ((CH.ifa.draw.framework.Handle) (k.nextElement())).draw(g);

    }

    /**
     * Draws the drawing.
     */
    public void drawDrawing(java.awt.Graphics g) {
        fDrawing.draw(g);
    }

    /**
     * Draws the background. If a background pattern is set it
     * is used to fill the background. Otherwise the background
     * is filled in the background color.
     */
    public void drawBackground(java.awt.Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getBounds().width, getBounds().height);
    }

    private void drawPainters(java.awt.Graphics g, java.util.Vector v) {
        for (int i = 0; i < v.size(); i++)
            ((CH.ifa.draw.framework.Painter) (v.elementAt(i))).draw(g, this);

    }

    /**
     * Adds a background.
     */
    public void addBackground(CH.ifa.draw.framework.Painter painter) {
        if (fBackgrounds == null)
            fBackgrounds = new java.util.Vector(3);

        fBackgrounds.addElement(painter);
        repaint();
    }

    /**
     * Removes a background.
     */
    public void removeBackground(CH.ifa.draw.framework.Painter painter) {
        if (fBackgrounds != null)
            fBackgrounds.removeElement(painter);

        repaint();
    }

    /**
     * Removes a foreground.
     */
    public void removeForeground(CH.ifa.draw.framework.Painter painter) {
        if (fForegrounds != null)
            fForegrounds.removeElement(painter);

        repaint();
    }

    /**
     * Adds a foreground.
     */
    public void addForeground(CH.ifa.draw.framework.Painter painter) {
        if (fForegrounds == null)
            fForegrounds = new java.util.Vector(3);

        fForegrounds.addElement(painter);
        repaint();
    }

    /**
     * Freezes the view by acquiring the drawing lock.
     *
     * @see Drawing#lock
     */
    public void freezeView() {
        drawing().lock();
    }

    /**
     * Unfreezes the view by releasing the drawing lock.
     *
     * @see Drawing#unlock
     */
    public void unfreezeView() {
        drawing().unlock();
    }

    private void readObject(java.io.ObjectInputStream s) throws java.lang.ClassNotFoundException, java.io.IOException {
        s.defaultReadObject();
        fSelection = new java.util.Vector();// could use lazy initialization instead

        if (fDrawing != null)
            fDrawing.addDrawingChangeListener(this);

    }

    private void checkMinimumSize() {
        CH.ifa.draw.framework.FigureEnumeration k = drawing().figures();
        java.awt.Dimension d = new java.awt.Dimension(0, 0);
        while (k.hasMoreElements()) {
            java.awt.Rectangle r = k.nextFigure().displayBox();
            d.width = java.lang.Math.max(d.width, r.x + r.width);
            d.height = java.lang.Math.max(d.height, r.y + r.height);
        } 
        if ((fViewSize.height < d.height) || (fViewSize.width < d.width)) {
            fViewSize.height = d.height + 10;
            fViewSize.width = d.width + 10;
            setSize(fViewSize);
        }
    }

    public boolean isFocusTraversable() {
        return true;
    }

    // listener methods we are not interested in
    public void mouseEntered(java.awt.event.MouseEvent e) {
    }

    public void mouseExited(java.awt.event.MouseEvent e) {
    }

    public void mouseClicked(java.awt.event.MouseEvent e) {
    }

    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    public void keyReleased(java.awt.event.KeyEvent e) {
    }
}