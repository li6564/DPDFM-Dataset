package junit.extensions;
/**
 * A Decorator for Tests. Use TestDecorator as the base class
 * for defining new test decorators. Test decorator subclasses
 * can be introduced to add behaviour before or after a test
 * is run.
 */
public class TestDecorator extends junit.framework.Assert implements junit.framework.Test {
    protected junit.framework.Test fTest;

    public TestDecorator(junit.framework.Test test) {
        fTest = test;
    }

    /**
     * The basic run behaviour.
     */
    public void basicRun(junit.framework.TestResult result) {
        fTest.run(result);
    }

    public int countTestCases() {
        return fTest.countTestCases();
    }

    public void run(junit.framework.TestResult result) {
        basicRun(result);
    }

    public java.lang.String toString() {
        return fTest.toString();
    }

    public junit.framework.Test getTest() {
        return fTest;
    }
}