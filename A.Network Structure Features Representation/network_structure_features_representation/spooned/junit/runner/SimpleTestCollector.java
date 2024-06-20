package junit.runner;
/**
 * An implementation of a TestCollector that considers
 * a class to be a test class when it contains the
 * pattern "Test" in its name
 *
 * @see TestCollector
 */
public class SimpleTestCollector extends junit.runner.ClassPathTestCollector {
    public SimpleTestCollector() {
    }

    protected boolean isTestClass(java.lang.String classFileName) {
        return (classFileName.endsWith(".class") && (classFileName.indexOf('$') < 0)) && (classFileName.indexOf("Test") > 0);
    }
}