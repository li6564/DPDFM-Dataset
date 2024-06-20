package junit.tests;
/**
 * A helper test case for testing whether the testing method
 * is run.
 */
class WasRun extends junit.framework.TestCase {
    boolean fWasRun = false;

    WasRun(java.lang.String name) {
        super(name);
    }

    protected void runTest() {
        fWasRun = true;
    }
}