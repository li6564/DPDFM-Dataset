package com.jmonkey.office.help;
public class OfficeHelp extends javax.swing.JFrame {
    // static URL backURL = null;
    // static URL forURL = null;
    // htmlFrame hFrame;
    private java.lang.String start_loc = null;

    // protected Browser iceBrowser;
    private java.lang.Class iceClass;

    private javax.swing.JComponent iceBrowser;

    public OfficeHelp(java.lang.String helpFile) {
        super("Program Help");
        this.setIconImage(com.jmonkey.office.lexi.support.images.Loader.load("help_book16.gif"));
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        start_loc = ("http://www.jmonkey.com/~mschmidt/help/" + helpFile) + "/index.html";
        try {
            iceClass = java.lang.Class.forName("ice.iblite.Browser");
            iceBrowser = ((javax.swing.JComponent) (iceClass.newInstance()));
        } catch (java.lang.Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Can\'t find class ice.iblite.Browser\n" + " put icebrowserlist.jar in your CLASSPATH", "Can't find class ice.iblite.Browser", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
        this.getContentPane().add("Center", iceBrowser);
        java.lang.Object[] args = new java.lang.Object[]{ start_loc };
        iceMethodCall("gotoLocation", args);
        // iceBrowser.gotoLocation(start_loc);
    }

    /**
     * Use this so that you don't need the iceBrowser classes at
     * compile.  This code is taken from the Jazilla project, but
     * might also be in the IceBrowser Demos
     */
    private java.lang.Object iceMethodCall(java.lang.String methodName, java.lang.Object[] args) {
        // Figure the Class of each element of args[]:
        java.lang.Class[] parameterTypes = new java.lang.Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        try {
            java.lang.reflect.Method method = iceClass.getMethod(methodName, parameterTypes);
            return method.invoke(iceBrowser, args);
        } catch (java.lang.NoSuchMethodException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "No such method in ice.iblite.Browser: " + methodName, "Warning", javax.swing.JOptionPane.WARNING_MESSAGE);
        } catch (java.lang.reflect.InvocationTargetException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "InvocationTargetException in call to: " + methodName, "Warning", javax.swing.JOptionPane.WARNING_MESSAGE);
        } catch (java.lang.IllegalAccessException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "IllegalAccessException in call to: " + methodName, "Warning", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    private void redirectOutput() {
        try {
            java.io.FileOutputStream fileOut = new java.io.FileOutputStream("Output.txt", true);
            java.io.PrintStream thePrintStream = new java.io.PrintStream(fileOut);
            java.lang.System.setOut(thePrintStream);
            java.lang.System.setErr(thePrintStream);
        } catch (java.io.IOException ex) {
            java.lang.System.out.println("Error writing: " + ex);
            // Code.failed(ex);
        }
    }
}