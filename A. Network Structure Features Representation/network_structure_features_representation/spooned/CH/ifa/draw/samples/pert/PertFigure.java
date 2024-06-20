/* @(#)PertFigure.java 5.1 */
package CH.ifa.draw.samples.pert;
public class PertFigure extends CH.ifa.draw.standard.CompositeFigure {
    private static final int BORDER = 3;

    private java.awt.Rectangle fDisplayBox;

    private java.util.Vector fPreTasks;

    private java.util.Vector fPostTasks;

    /* Serialization support. */
    private static final long serialVersionUID = -7877776240236946511L;

    private int pertFigureSerializedDataVersion = 1;

    public PertFigure() {
        initialize();
    }

    public int start() {
        int start = 0;
        java.util.Enumeration i = fPreTasks.elements();
        while (i.hasMoreElements()) {
            CH.ifa.draw.samples.pert.PertFigure f = ((CH.ifa.draw.samples.pert.PertFigure) (i.nextElement()));
            start = java.lang.Math.max(start, f.end());
        } 
        return start;
    }

    public int end() {
        return asInt(2);
    }

    public int duration() {
        return asInt(1);
    }

    public void setEnd(int value) {
        setInt(2, value);
    }

    public void addPreTask(CH.ifa.draw.samples.pert.PertFigure figure) {
        if (!fPreTasks.contains(figure)) {
            fPreTasks.addElement(figure);
        }
    }

    public void addPostTask(CH.ifa.draw.samples.pert.PertFigure figure) {
        if (!fPostTasks.contains(figure)) {
            fPostTasks.addElement(figure);
        }
    }

    public void removePreTask(CH.ifa.draw.samples.pert.PertFigure figure) {
        fPreTasks.removeElement(figure);
    }

    public void removePostTask(CH.ifa.draw.samples.pert.PertFigure figure) {
        fPostTasks.removeElement(figure);
    }

    private int asInt(int figureIndex) {
        CH.ifa.draw.figures.NumberTextFigure t = ((CH.ifa.draw.figures.NumberTextFigure) (figureAt(figureIndex)));
        return t.getValue();
    }

    private java.lang.String taskName() {
        CH.ifa.draw.figures.TextFigure t = ((CH.ifa.draw.figures.TextFigure) (figureAt(0)));
        return t.getText();
    }

    private void setInt(int figureIndex, int value) {
        CH.ifa.draw.figures.NumberTextFigure t = ((CH.ifa.draw.figures.NumberTextFigure) (figureAt(figureIndex)));
        t.setValue(value);
    }

    protected void basicMoveBy(int x, int y) {
        fDisplayBox.translate(x, y);
        super.basicMoveBy(x, y);
    }

    public java.awt.Rectangle displayBox() {
        return new java.awt.Rectangle(fDisplayBox.x, fDisplayBox.y, fDisplayBox.width, fDisplayBox.height);
    }

    public void basicDisplayBox(java.awt.Point origin, java.awt.Point corner) {
        fDisplayBox = new java.awt.Rectangle(origin);
        fDisplayBox.add(corner);
        layout();
    }

    private void drawBorder(java.awt.Graphics g) {
        super.draw(g);
        java.awt.Rectangle r = displayBox();
        CH.ifa.draw.framework.Figure f = figureAt(0);
        java.awt.Rectangle rf = f.displayBox();
        g.setColor(java.awt.Color.gray);
        g.drawLine(r.x, (r.y + rf.height) + 2, r.x + r.width, (r.y + rf.height) + 2);
        g.setColor(java.awt.Color.white);
        g.drawLine(r.x, (r.y + rf.height) + 3, r.x + r.width, (r.y + rf.height) + 3);
        g.setColor(java.awt.Color.white);
        g.drawLine(r.x, r.y, r.x, r.y + r.height);
        g.drawLine(r.x, r.y, r.x + r.width, r.y);
        g.setColor(java.awt.Color.gray);
        g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
    }

    public void draw(java.awt.Graphics g) {
        drawBorder(g);
        super.draw(g);
    }

    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector();
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.northWest()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.northEast()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.southWest()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.southEast()));
        handles.addElement(new CH.ifa.draw.standard.ConnectionHandle(this, CH.ifa.draw.standard.RelativeLocator.east(), new CH.ifa.draw.samples.pert.PertDependency()));
        return handles;
    }

    private void initialize() {
        fPostTasks = new java.util.Vector();
        fPreTasks = new java.util.Vector();
        fDisplayBox = new java.awt.Rectangle(0, 0, 0, 0);
        java.awt.Font f = new java.awt.Font("Helvetica", java.awt.Font.PLAIN, 12);
        java.awt.Font fb = new java.awt.Font("Helvetica", java.awt.Font.BOLD, 12);
        CH.ifa.draw.figures.TextFigure name = new CH.ifa.draw.figures.TextFigure();
        name.setFont(fb);
        name.setText("Task");
        // name.setAttribute("TextColor",Color.white);
        add(name);
        CH.ifa.draw.figures.NumberTextFigure duration = new CH.ifa.draw.figures.NumberTextFigure();
        duration.setValue(0);
        duration.setFont(fb);
        add(duration);
        CH.ifa.draw.figures.NumberTextFigure end = new CH.ifa.draw.figures.NumberTextFigure();
        end.setValue(0);
        end.setFont(f);
        end.setReadOnly(true);
        add(end);
    }

    private void layout() {
        java.awt.Point partOrigin = new java.awt.Point(fDisplayBox.x, fDisplayBox.y);
        partOrigin.translate(CH.ifa.draw.samples.pert.PertFigure.BORDER, CH.ifa.draw.samples.pert.PertFigure.BORDER);
        java.awt.Dimension extent = new java.awt.Dimension(0, 0);
        CH.ifa.draw.framework.FigureEnumeration k = figures();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure f = k.nextFigure();
            java.awt.Dimension partExtent = f.size();
            java.awt.Point corner = new java.awt.Point(partOrigin.x + partExtent.width, partOrigin.y + partExtent.height);
            f.basicDisplayBox(partOrigin, corner);
            extent.width = java.lang.Math.max(extent.width, partExtent.width);
            extent.height += partExtent.height;
            partOrigin.y += partExtent.height;
        } 
        fDisplayBox.width = extent.width + (2 * CH.ifa.draw.samples.pert.PertFigure.BORDER);
        fDisplayBox.height = extent.height + (2 * CH.ifa.draw.samples.pert.PertFigure.BORDER);
    }

    private boolean needsLayout() {
        java.awt.Dimension extent = new java.awt.Dimension(0, 0);
        CH.ifa.draw.framework.FigureEnumeration k = figures();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure f = k.nextFigure();
            extent.width = java.lang.Math.max(extent.width, f.size().width);
        } 
        int newExtent = extent.width + (2 * CH.ifa.draw.samples.pert.PertFigure.BORDER);
        return newExtent != fDisplayBox.width;
    }

    public void update(CH.ifa.draw.framework.FigureChangeEvent e) {
        // duration has changed
        if (e.getFigure() == figureAt(1))
            updateDurations();

        if (needsLayout()) {
            layout();
            changed();
        }
    }

    public void figureChanged(CH.ifa.draw.framework.FigureChangeEvent e) {
        update(e);
    }

    public void figureRemoved(CH.ifa.draw.framework.FigureChangeEvent e) {
        update(e);
    }

    public void notifyPostTasks() {
        java.util.Enumeration i = fPostTasks.elements();
        while (i.hasMoreElements())
            ((CH.ifa.draw.samples.pert.PertFigure) (i.nextElement())).updateDurations();

    }

    public void updateDurations() {
        int newEnd = start() + duration();
        if (newEnd != end()) {
            setEnd(newEnd);
            notifyPostTasks();
        }
    }

    public boolean hasCycle(CH.ifa.draw.framework.Figure start) {
        if (start == this)
            return true;

        java.util.Enumeration i = fPreTasks.elements();
        while (i.hasMoreElements()) {
            if (((CH.ifa.draw.samples.pert.PertFigure) (i.nextElement())).hasCycle(start))
                return true;

        } 
        return false;
    }

    // -- store / load ----------------------------------------------
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fDisplayBox.x);
        dw.writeInt(fDisplayBox.y);
        dw.writeInt(fDisplayBox.width);
        dw.writeInt(fDisplayBox.height);
        writeTasks(dw, fPreTasks);
        writeTasks(dw, fPostTasks);
    }

    public void writeTasks(CH.ifa.draw.util.StorableOutput dw, java.util.Vector v) {
        dw.writeInt(v.size());
        java.util.Enumeration i = v.elements();
        while (i.hasMoreElements())
            dw.writeStorable(((CH.ifa.draw.util.Storable) (i.nextElement())));

    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        fDisplayBox = new java.awt.Rectangle(dr.readInt(), dr.readInt(), dr.readInt(), dr.readInt());
        layout();
        fPreTasks = readTasks(dr);
        fPostTasks = readTasks(dr);
    }

    public java.awt.Insets connectionInsets() {
        java.awt.Rectangle r = fDisplayBox;
        int cx = r.width / 2;
        int cy = r.height / 2;
        return new java.awt.Insets(cy, cx, cy, cx);
    }

    public java.util.Vector readTasks(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        int size = dr.readInt();
        java.util.Vector v = new java.util.Vector(size);
        for (int i = 0; i < size; i++)
            v.addElement(((CH.ifa.draw.framework.Figure) (dr.readStorable())));

        return v;
    }
}