/* @(#)NetApp.java 5.1 */
package CH.ifa.draw.samples.net;
public class NetApp extends CH.ifa.draw.application.DrawApplication {
    NetApp() {
        super("Net");
    }

    protected void createTools(java.awt.Panel palette) {
        super.createTools(palette);
        CH.ifa.draw.framework.Tool tool = new CH.ifa.draw.figures.TextTool(view(), new CH.ifa.draw.samples.net.NodeFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "TEXT", "Text Tool", tool));
        tool = new CH.ifa.draw.standard.CreationTool(view(), new CH.ifa.draw.samples.net.NodeFigure());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "RECT", "Create Org Unit", tool));
        tool = new CH.ifa.draw.standard.ConnectionTool(view(), new CH.ifa.draw.figures.LineConnection());
        palette.add(createToolButton(CH.ifa.draw.application.DrawApplication.IMAGES + "CONN", "Connection Tool", tool));
    }

    // -- main -----------------------------------------------------------
    public static void main(java.lang.String[] args) {
        CH.ifa.draw.application.DrawApplication window = new CH.ifa.draw.samples.net.NetApp();
        window.open();
    }
}