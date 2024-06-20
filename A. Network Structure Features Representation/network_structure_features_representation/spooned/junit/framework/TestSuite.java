package junit.framework;
public class TestSuite implements junit.framework.Test {
    private java.util.Vector fTests = new java.util.Vector(10);

    private java.lang.String fName;

    public TestSuite() {
    }

    public TestSuite(final java.lang.Class theClass) {
        fName = theClass.getName();
        java.lang.reflect.Constructor constructor = null;
        try {
            constructor = getConstructor(theClass);
        } catch (java.lang.NoSuchMethodException e) {
            addTest(warning(("Class " + theClass.getName()) + " has no public constructor TestCase(String name)"));
            return;
        }
        if (!java.lang.reflect.Modifier.isPublic(theClass.getModifiers())) {
            addTest(warning(("Class " + theClass.getName()) + " is not public"));
            return;
        }
        java.lang.Class superClass = theClass;
        java.util.Vector names = new java.util.Vector();
        while (junit.framework.Test.class.isAssignableFrom(superClass)) {
            java.lang.reflect.Method[] methods = superClass.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                addTestMethod(methods[i], names, constructor);
            }
            superClass = superClass.getSuperclass();
        } 
        if (fTests.size() == 0)
            addTest(warning("No tests found in " + theClass.getName()));

    }

    public TestSuite(java.lang.String name) {
        fName = name;
    }

    public void addTest(junit.framework.Test test) {
        fTests.addElement(test);
    }

    public void addTestSuite(java.lang.Class testClass) {
        addTest(new junit.framework.TestSuite(testClass));
    }

    private void addTestMethod(java.lang.reflect.Method m, java.util.Vector names, java.lang.reflect.Constructor constructor) {
        java.lang.String name = m.getName();
        if (names.contains(name))
            return;

        if (isPublicTestMethod(m)) {
            names.addElement(name);
            java.lang.Object[] args = new java.lang.Object[]{ name };
            try {
                addTest(((junit.framework.Test) (constructor.newInstance(args))));
            } catch (java.lang.InstantiationException e) {
                addTest(warning(((("Cannot instantiate test case: " + name) + " (") + exceptionToString(e)) + ")"));
            } catch (java.lang.reflect.InvocationTargetException e) {
                addTest(warning(((("Exception in constructor: " + name) + " (") + exceptionToString(e.getTargetException())) + ")"));
            } catch (java.lang.IllegalAccessException e) {
                addTest(warning(((("Cannot access test case: " + name) + " (") + exceptionToString(e)) + ")"));
            }
        } else if (isTestMethod(m))
            addTest(warning("Test method isn't public: " + m.getName()));

    }

    private java.lang.String exceptionToString(java.lang.Throwable t) {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        java.io.PrintWriter writer = new java.io.PrintWriter(stringWriter);
        t.printStackTrace(writer);
        return stringWriter.toString();
    }

    public int countTestCases() {
        int count = 0;
        for (java.util.Enumeration e = tests(); e.hasMoreElements();) {
            junit.framework.Test test = ((junit.framework.Test) (e.nextElement()));
            count = count + test.countTestCases();
        }
        return count;
    }

    private java.lang.reflect.Constructor getConstructor(java.lang.Class theClass) throws java.lang.NoSuchMethodException {
        java.lang.Class[] args = new java.lang.Class[]{ java.lang.String.class };
        return theClass.getConstructor(args);
    }

    private boolean isPublicTestMethod(java.lang.reflect.Method m) {
        return isTestMethod(m) && java.lang.reflect.Modifier.isPublic(m.getModifiers());
    }

    private boolean isTestMethod(java.lang.reflect.Method m) {
        java.lang.String name = m.getName();
        java.lang.Class[] parameters = m.getParameterTypes();
        java.lang.Class returnType = m.getReturnType();
        return ((parameters.length == 0) && name.startsWith("test")) && returnType.equals(java.lang.Void.TYPE);
    }

    public void run(junit.framework.TestResult result) {
        for (java.util.Enumeration e = tests(); e.hasMoreElements();) {
            if (result.shouldStop())
                break;

            junit.framework.Test test = ((junit.framework.Test) (e.nextElement()));
            runTest(test, result);
        }
    }

    public void runTest(junit.framework.Test test, junit.framework.TestResult result) {
        test.run(result);
    }

    public junit.framework.Test testAt(int index) {
        return ((junit.framework.Test) (fTests.elementAt(index)));
    }

    public int testCount() {
        return fTests.size();
    }

    public java.util.Enumeration tests() {
        return fTests.elements();
    }

    public java.lang.String toString() {
        if (getName() != null)
            return getName();

        return super.toString();
    }

    public void setName(java.lang.String name) {
        fName = name;
    }

    public java.lang.String getName() {
        return fName;
    }

    private junit.framework.Test warning(final java.lang.String message) {
        return new junit.framework.TestCase("warning") {
            protected void runTest() {
                junit.framework.Assert.fail(message);
            }
        };
    }
}