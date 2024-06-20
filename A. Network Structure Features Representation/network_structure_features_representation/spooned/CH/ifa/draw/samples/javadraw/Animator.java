/* @(#)Animator.java 5.1 */
package CH.ifa.draw.samples.javadraw;
public class Animator extends java.lang.Thread {
    private CH.ifa.draw.framework.DrawingView fView;

    private CH.ifa.draw.util.Animatable fAnimatable;

    private boolean fIsRunning;

    private static final int DELAY = 1000 / 16;

    public Animator(CH.ifa.draw.util.Animatable animatable, CH.ifa.draw.framework.DrawingView view) {
        super("Animator");
        fView = view;
        fAnimatable = animatable;
    }

    public void start() {
        super.start();
        fIsRunning = true;
    }

    public void end() {
        fIsRunning = false;
    }

    public void run() {
        while (fIsRunning) {
            long tm = java.lang.System.currentTimeMillis();
            fView.freezeView();
            fAnimatable.animationStep();
            fView.checkDamage();
            fView.unfreezeView();
            // Delay for a while
            try {
                tm += CH.ifa.draw.samples.javadraw.Animator.DELAY;
                java.lang.Thread.sleep(java.lang.Math.max(0, tm - java.lang.System.currentTimeMillis()));
            } catch (java.lang.InterruptedException e) {
                break;
            }
        } 
    }
}