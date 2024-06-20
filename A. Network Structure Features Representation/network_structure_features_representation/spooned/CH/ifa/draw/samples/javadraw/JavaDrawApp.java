/* @(#)JavaDrawApp.java 5.1 */
package CH.ifa.draw.samples.javadraw;
public class JavaDrawApp extends CH.ifa.draw.application.DrawApplication {
    private CH.ifa.draw.samples.javadraw.Animator fAnimator;

    private static java.lang.String fgSampleImagesPath = "CH/ifa/draw/samples/javadraw/sampleimages/";

    private static java.lang.String fgSampleImagesResourcePath = "/" + CH.ifa.draw.samples.javadraw.JavaDrawApp.fgSampleImagesPath;

    public JavaDrawApp() {
        this("JHotDraw");
    }

    public JavaDrawApp(java.lang.String name) {
        super(name);
    }

    public void open() {
        super.open();
    }

    // -- application life cycle --------------------------------------------
    public void destroy() {
        super.destroy();
        endAnimation();
    }

    // -- DrawApplication overrides -----------------------------------------
    protected void createTools(java.awt.Panel palette) {
        super.createTools(palette);
        CH.ifa.draw.framework.Tool tool = new CH.ifa.draw.figures.TextTool(view(), new CH.ifa.draw.figures.TextFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "TEXT", "Text Tool", tool));
        tool = new CH.ifa.draw.figures.ConnectedTextTool(view(), new CH.ifa.draw.figures.TextFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "ATEXT", "Connected Text Tool", tool));
        tool = new CH.ifa.draw.samples.javadraw.URLTool(view());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "URL", "URL Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.RectangleFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "RECT", "Rectangle Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.RoundRectangleFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "RRECT", "Round Rectangle Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.EllipseFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "ELLIPSE", "Ellipse Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.LineFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "LINE", "Line Tool", tool));
        tool = new CH.ifa.draw.standard.ConnectionTool(view(), new CH.ifa.draw.figures.LineConnection());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "CONN", "Connection Tool", tool));
        tool = new CH.ifa.draw.standard.ConnectionTool(view(), new CH.ifa.draw.figures.ElbowConnection());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "OCONN", "Elbow Connection Tool", tool));
        tool = new CH.ifa.draw.figures.ScribbleTool(view());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "SCRIBBL", "Scribble Tool", tool));
        tool = new CH.ifa.draw.contrib.PolygonTool(view());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "POLYGON", "Polygon Tool", tool));
        tool = new CH.ifa.draw.figures.BorderTool(view());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "BORDDEC", "Border Tool", tool));
    }

    protected CH.ifa.draw.framework.Tool createSelectionTool() {
        return new CH.ifa.draw.samples.javadraw.MySelectionTool(view());
    }

    protected void createMenus(java.awt.MenuBar mb) {
        super.createMenus(mb);
        mb.add(createAnimationMenu());
        mb.add(createImagesMenu());
        mb.add(createWindowMenu());
    }

    protected java.awt.Menu createAnimationMenu() {
        java.awt.Menu menu = new java.awt.Menu("Animation");
        java.awt.MenuItem mi = new java.awt.MenuItem("Start Animation");
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                startAnimation();
            }
        });
        menu.add(mi);
        mi = new java.awt.MenuItem("Stop Animation");
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                endAnimation();
            }
        });
        menu.add(mi);
        return menu;
    }

    protected java.awt.Menu createWindowMenu() {
        java.awt.Menu menu = new java.awt.Menu("Window");
        java.awt.MenuItem mi = new java.awt.MenuItem("New Window");
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                openView();
            }
        });
        menu.add(mi);
        return menu;
    }

    protected java.awt.Menu createImagesMenu() {
        CH.ifa.draw.util.CommandMenu menu = new CH.ifa.draw.util.CommandMenu("Images");
        java.io.File imagesDirectory = new java.io.File(CH.ifa.draw.samples.javadraw.JavaDrawApp.fgSampleImagesPath);
        try {
            java.lang.String[] list = imagesDirectory.list();
            for (int i = 0; i < list.length; i++) {
                java.lang.String name = list[i];
                java.lang.String path = CH.ifa.draw.samples.javadraw.JavaDrawApp.fgSampleImagesResourcePath + name;
                menu.add(new CH.ifa.draw.figures.InsertImageCommand(name, path, view()));
            }
        } catch (java.lang.Exception e) {
        }
        return menu;
    }

    protected CH.ifa.draw.framework.Drawing createDrawing() {
        return new CH.ifa.draw.samples.javadraw.BouncingDrawing();
        // return new StandardDrawing();
    }

    protected java.awt.Dimension defaultSize() {
        return new java.awt.Dimension(430, 436);
    }

    // ---- animation support --------------------------------------------
    public void startAnimation() {
        if ((drawing() instanceof CH.ifa.draw.util.Animatable) && (fAnimator == null)) {
            fAnimator = new CH.ifa.draw.samples.javadraw.Animator(((CH.ifa.draw.util.Animatable) (drawing())), view());
            fAnimator.start();
        }
    }

    public void endAnimation() {
        if (fAnimator != null) {
            fAnimator.end();
            fAnimator = null;
        }
    }

    public void openView() {
        CH.ifa.draw.samples.javadraw.JavaDrawApp window = new CH.ifa.draw.samples.javadraw.JavaDrawApp();
        window.open();
        window.setDrawing(drawing());
        window.setTitle("JHotDraw (View)");
    }

    // -- main -----------------------------------------------------------
    public static void main(java.lang.String[] args) {
        CH.ifa.draw.samples.javadraw.JavaDrawApp window = new CH.ifa.draw.samples.javadraw.JavaDrawApp();
        window.open();
    }
}