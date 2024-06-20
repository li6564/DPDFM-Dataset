package junit.tests;
/**
 * A TestCase for testing the TestCaseClassLoader
 */
public class TestCaseClassLoaderTest extends junit.framework.TestCase {
    public TestCaseClassLoaderTest(java.lang.String name) {
        super(name);
    }

    public void testClassLoading() throws java.lang.Exception {
        junit.runner.TestCaseClassLoader loader = new junit.runner.TestCaseClassLoader();
        java.lang.Class loadedClass = loader.loadClass("junit.tests.ClassLoaderTest", true);
        java.lang.Object o = loadedClass.newInstance();
        // 
        // Invoke the assertClassLoaders method via reflection.
        // We use reflection since the class is loaded by
        // another class loader and we can't do a successfull downcast to
        // ClassLoaderTestCase.
        // 
        java.lang.reflect.Method method = loadedClass.getDeclaredMethod("verify", new java.lang.Class[0]);
        method.invoke(o, new java.lang.Class[0]);
    }

    public void testJarClassLoading() throws java.lang.Exception {
        java.net.URL url = getClass().getResource("test.jar");
        java.lang.String path = url.getFile();
        junit.runner.TestCaseClassLoader loader = new junit.runner.TestCaseClassLoader(path);
        java.lang.Class loadedClass = loader.loadClass("junit.tests.LoadedFromJar", true);
        java.lang.Object o = loadedClass.newInstance();
        // 
        // Invoke the assertClassLoaders method via reflection.
        // We use reflection since the class is loaded by
        // another class loader and we can't do a successfull downcast to
        // ClassLoaderTestCase.
        // 
        java.lang.reflect.Method method = loadedClass.getDeclaredMethod("verify", new java.lang.Class[0]);
        method.invoke(o, new java.lang.Class[0]);
    }
}