/* @(#)PertApplet.java 5.1 */
package CH.ifa.draw.samples.pert;
public class PertApplet extends CH.ifa.draw.applet.DrawApplet {
    private static final java.lang.String PERTIMAGES = "/CH/ifa/draw/samples/pert/images/";

    protected void createTools(java.awt.Panel palette) {
        super.createTools(palette);
        CH.ifa.draw.framework.Tool tool;
        tool = new CH.ifa.draw.figures.TextTool(view(), new CH.ifa.draw.figures.TextFigure());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "TEXT", "Text Tool", tool));
        tool = new CH.ifa.draw.samples.pert.PertFigureCreationTool(view());
        palette.add(createToolButton(CH.ifa.draw.samples.pert.PertApplet.PERTIMAGES + "PERT", "Task Tool", tool));
        tool = new CH.ifa.draw.standard.ConnectionTool(view(), new CH.ifa.draw.samples.pert.PertDependency());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "CONN", "Dependency Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.LineFigure());
        palette.add(createToolButton(CH.ifa.draw.applet.DrawApplet.IMAGES + "Line", "Line Tool", tool));
    }
}