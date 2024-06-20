/* @(#)JavaDrawApplet.java 5.1 */
package CH.ifa.draw.samples.javadraw;
public class JavaDrawApplet extends CH.ifa.draw.applet.DrawApplet {
    private transient java.awt.Button fAnimationButton;

    private transient CH.ifa.draw.samples.javadraw.Animator fAnimator;

    // -- applet life cycle --------------------------------------------
    public void destroy() {
        super.destroy();
        endAnimation();
    }

    // -- DrawApplet overrides -----------------------------------------
    protected void createTools(java.awt.Panel palette) {
        super.createTools(palette);
        CH.ifa.draw.framework.Tool tool = new CH.ifa.draw.figures.TextTool(view(), new CH.ifa.draw.figures.TextFigure());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "TEXT", "Text Tool", tool));
        tool = new CH.ifa.draw.figures.ConnectedTextTool(view(), new CH.ifa.draw.figures.TextFigure());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "ATEXT", "Connected Text Tool", tool));
        tool = new CH.ifa.draw.samples.javadraw.URLTool(view());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "URL", "URL Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.RectangleFigure());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "RECT", "Rectangle Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.RoundRectangleFigure());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "RRECT", "Round Rectangle Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.EllipseFigure());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "ELLIPSE", "Ellipse Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.LineFigure());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "LINE", "Line Tool", tool));
        tool = new CH.ifa.draw.standard.ConnectionTool(view(), new CH.ifa.draw.figures.LineConnection());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "CONN", "Connection Tool", tool));
        tool = new CH.ifa.draw.standard.ConnectionTool(view(), new CH.ifa.draw.figures.ElbowConnection());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "OCONN", "Elbow Connection Tool", tool));
        tool = new CH.ifa.draw.figures.ScribbleTool(view());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "SCRIBBL", "Scribble Tool", tool));
        tool = new CH.ifa.draw.contrib.PolygonTool(view());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "POLYGON", "Polygon Tool", tool));
        tool = new CH.ifa.draw.figures.BorderTool(view());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "BORDDEC", "Border Tool", tool));
    }

    protected void createButtons(java.awt.Panel panel) {
        super.createButtons(panel);
        fAnimationButton = new java.awt.Button("Start Animation");
        fAnimationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                toggleAnimation();
            }
        });
        panel.add(fAnimationButton);
    }

    protected CH.ifa.draw.framework.Drawing createDrawing() {
        return new CH.ifa.draw.samples.javadraw.BouncingDrawing();
    }

    // -- animation support ----------------------------------------------
    public void startAnimation() {
        if ((drawing() instanceof CH.ifa.draw.util.Animatable) && (fAnimator == null)) {
            fAnimator = new CH.ifa.draw.samples.javadraw.Animator(((CH.ifa.draw.util.Animatable) (drawing())), view());
            fAnimator.start();
            fAnimationButton.setLabel("End Animation");
        }
    }

    public void endAnimation() {
        if (fAnimator != null) {
            fAnimator.end();
            fAnimator = null;
            fAnimationButton.setLabel("Start Animation");
        }
    }

    public void toggleAnimation() {
        if (fAnimator != null)
            endAnimation();
        else
            startAnimation();

    }
}