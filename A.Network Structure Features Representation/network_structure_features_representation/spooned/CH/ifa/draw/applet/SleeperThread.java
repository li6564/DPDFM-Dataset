/* @(#)DrawApplet.java 5.1 */
package CH.ifa.draw.applet;
class SleeperThread extends java.lang.Thread {
    java.applet.Applet fApplet;

    SleeperThread(java.applet.Applet applet) {
        fApplet = applet;
    }

    public void run() {
        try {
            for (; ;) {
                fApplet.showStatus("loading icons...");
                java.lang.Thread.sleep(50);
            }
        } catch (java.lang.InterruptedException e) {
            return;
        }
    }
}