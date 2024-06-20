package junit.runner;
/**
 * An interface to define how a test suite should be loaded.
 */
public interface TestSuiteLoader {
    public abstract java.lang.Class load(java.lang.String suiteClassName) throws java.lang.ClassNotFoundException;

    public abstract java.lang.Class reload(java.lang.Class aClass) throws java.lang.ClassNotFoundException;
}