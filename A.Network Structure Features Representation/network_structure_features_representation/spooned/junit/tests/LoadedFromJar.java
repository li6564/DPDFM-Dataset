package junit.tests;
public class LoadedFromJar extends junit.framework.Assert {
    public void verify() {
        verifyApplicationClassLoadedByTestLoader();
    }

    private boolean isTestCaseClassLoader(java.lang.ClassLoader cl) {
        return (cl != null) && cl.getClass().getName().equals(junit.runner.TestCaseClassLoader.class.getName());
    }

    private void verifyApplicationClassLoadedByTestLoader() {
        junit.framework.Assert.assertTrue(isTestCaseClassLoader(getClass().getClassLoader()));
    }
}