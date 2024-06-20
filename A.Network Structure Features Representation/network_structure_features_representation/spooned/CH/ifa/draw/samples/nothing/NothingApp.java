/* @(#)NothingApp.java 5.1 */
package CH.ifa.draw.samples.nothing;
public class NothingApp extends CH.ifa.draw.application.DrawApplication {
    NothingApp() {
        super("Nothing");
    }

    protected void createTools(java.awt.Panel palette) {
        super.createTools(palette);
        CH.ifa.draw.framework.Tool tool = new CH.ifa.draw.figures.TextTool(view(), new CH.ifa.draw.figures.TextFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "TEXT", "Text Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.RectangleFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "RECT", "Rectangle Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.RoundRectangleFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "RRECT", "Round Rectangle Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.EllipseFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "ELLIPSE", "Ellipse Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.LineFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "LINE", "Line Tool", tool));
        tool = new CH.ifa.draw.contrib.PolygonTool(view());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "POLYGON", "Polygon Tool", tool));
        tool = new CH.ifa.draw.standard.ConnectionTool(view(), new CH.ifa.draw.figures.LineConnection());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "CONN", "Connection Tool", tool));
        tool = new CH.ifa.draw.standard.ConnectionTool(view(), new CH.ifa.draw.figures.ElbowConnection());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "OCONN", "Elbow Connection Tool", tool));
    }

    // -- main -----------------------------------------------------------
    public static void main(java.lang.String[] args) {
        CH.ifa.draw.application.DrawApplication window = new CH.ifa.draw.samples.nothing.NothingApp();
        window.open();
    }
}