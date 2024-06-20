/* @(#)PertApplication.java 5.1 */
package CH.ifa.draw.samples.pert;
public class PertApplication extends CH.ifa.draw.application.DrawApplication {
    private static final java.lang.String PERTIMAGES = "/CH/ifa/draw/samples/pert/images/";

    PertApplication() {
        super("PERT Editor");
    }

    protected void createTools(java.awt.Panel palette) {
        super.createTools(palette);
        CH.ifa.draw.framework.Tool tool;
        tool = new CH.ifa.draw.figures.TextTool(view(), new CH.ifa.draw.figures.TextFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "TEXT", "Text Tool", tool));
        // the generic but slower version
        // tool = new CreationTool(new PertFigure());
        // palette.add(createToolButton(PERTIMAGES+"PERT", "Task Tool", tool));
        tool = new CH.ifa.draw.samples.pert.PertFigureCreationTool(view());
        palette.add(createToolButton(CH.ifa.draw.samples.pert.PertApplication.PERTIMAGES + "PERT", "Task Tool", tool));
        tool = new CH.ifa.draw.standard.ConnectionTool(view(), new CH.ifa.draw.samples.pert.PertDependency());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "CONN", "Dependency Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.figures.LineFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "Line", "Line Tool", tool));
    }

    // -- main -----------------------------------------------------------
    public static void main(java.lang.String[] args) {
        CH.ifa.draw.samples.pert.PertApplication pert = new CH.ifa.draw.samples.pert.PertApplication();
        pert.open();
    }
}