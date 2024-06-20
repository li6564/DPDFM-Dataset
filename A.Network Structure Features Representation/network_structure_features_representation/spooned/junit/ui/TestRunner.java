package junit.ui;
/**
 *
 * @deprecated use junit.awtui.TestRunner.
 */
public class TestRunner extends junit.awtui.TestRunner {
    public static void main(java.lang.String[] args) {
        new junit.ui.TestRunner().start(args);
    }
}