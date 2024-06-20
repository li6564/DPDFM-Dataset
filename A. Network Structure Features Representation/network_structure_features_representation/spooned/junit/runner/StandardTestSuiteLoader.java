package junit.runner;
/**
 * The standard test suite loader. It can only load the same class once.
 */
public class StandardTestSuiteLoader implements junit.runner.TestSuiteLoader {
    /**
     * Uses the system class loader to load the test class
     */
    public java.lang.Class load(java.lang.String suiteClassName) throws java.lang.ClassNotFoundException {
        return java.lang.Class.forName(suiteClassName);
    }

    /**
     * Uses the system class loader to load the test class
     */
    public java.lang.Class reload(java.lang.Class aClass) throws java.lang.ClassNotFoundException {
        return aClass;
    }
}