package junit.tests;
public class ClassLoaderTest extends junit.framework.Assert {
    public ClassLoaderTest() {
    }

    public void verify() {
        verifyApplicationClassLoadedByTestLoader();
        verifySystemClassNotLoadedByTestLoader();
    }

    private boolean isTestCaseClassLoader(java.lang.ClassLoader cl) {
        return (cl != null) && cl.getClass().getName().equals(junit.runner.TestCaseClassLoader.class.getName());
    }

    private void verifyApplicationClassLoadedByTestLoader() {
        junit.framework.Assert.assertTrue(isTestCaseClassLoader(getClass().getClassLoader()));
    }

    private void verifySystemClassNotLoadedByTestLoader() {
        junit.framework.Assert.assertTrue(!isTestCaseClassLoader(java.lang.Object.class.getClassLoader()));
        junit.framework.Assert.assertTrue(!isTestCaseClassLoader(junit.framework.TestCase.class.getClassLoader()));
    }
}