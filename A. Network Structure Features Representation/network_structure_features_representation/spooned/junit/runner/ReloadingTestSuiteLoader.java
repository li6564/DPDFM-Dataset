package junit.runner;
/**
 * A TestSuite loader that can reload classes.
 */
public class ReloadingTestSuiteLoader implements junit.runner.TestSuiteLoader {
    public java.lang.Class load(java.lang.String suiteClassName) throws java.lang.ClassNotFoundException {
        junit.runner.TestCaseClassLoader loader = new junit.runner.TestCaseClassLoader();
        return loader.loadClass(suiteClassName, true);
    }

    public java.lang.Class reload(java.lang.Class aClass) throws java.lang.ClassNotFoundException {
        junit.runner.TestCaseClassLoader loader = new junit.runner.TestCaseClassLoader();
        return loader.loadClass(aClass.getName(), true);
    }
}