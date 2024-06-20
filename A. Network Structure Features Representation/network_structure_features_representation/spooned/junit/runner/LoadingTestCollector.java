package junit.runner;
/**
 * An implementation of a TestCollector that loads
 * all classes on the class path and tests whether
 * it is assignable from Test or provides a static suite method.
 *
 * @see TestCollector
 */
public class LoadingTestCollector extends junit.runner.ClassPathTestCollector {
    junit.runner.TestCaseClassLoader fLoader;

    public LoadingTestCollector() {
        fLoader = new junit.runner.TestCaseClassLoader();
    }

    protected boolean isTestClass(java.lang.String classFileName) {
        try {
            if (classFileName.endsWith(".class")) {
                java.lang.Class testClass = classFromFile(classFileName);
                return (testClass != null) && isTestClass(testClass);
            }
        } catch (java.lang.ClassNotFoundException expected) {
        } catch (java.lang.NoClassDefFoundError notFatal) {
        }
        return false;
    }

    java.lang.Class classFromFile(java.lang.String classFileName) throws java.lang.ClassNotFoundException {
        java.lang.String className = classNameFromFile(classFileName);
        if (!fLoader.isExcluded(className))
            return fLoader.loadClass(className, false);

        return null;
    }

    boolean isTestClass(java.lang.Class testClass) {
        if (hasSuiteMethod(testClass))
            return true;

        if ((junit.framework.Test.class.isAssignableFrom(testClass) && java.lang.reflect.Modifier.isPublic(testClass.getModifiers())) && hasPublicConstructor(testClass))
            return true;

        return false;
    }

    boolean hasSuiteMethod(java.lang.Class testClass) {
        try {
            testClass.getMethod(junit.runner.BaseTestRunner.SUITE_METHODNAME, new java.lang.Class[0]);
        } catch (java.lang.Exception e) {
            return false;
        }
        return true;
    }

    boolean hasPublicConstructor(java.lang.Class testClass) {
        java.lang.Class[] args = new java.lang.Class[]{ java.lang.String.class };
        try {
            testClass.getConstructor(args);
        } catch (java.lang.Exception e) {
            return false;
        }
        return true;
    }
}