package junit.tests;
import java.io.File;
import java.io.InputStream;
public class TextRunnerTest extends junit.framework.TestCase {
    public TextRunnerTest(java.lang.String name) {
        super(name);
    }

    public void testFailure() throws java.lang.Exception {
        execTest("junit.tests.Failure", false);
    }

    public void testSuccess() throws java.lang.Exception {
        execTest("junit.tests.Success", true);
    }

    public void testError() throws java.lang.Exception {
        execTest("junit.tests.BogusDude", false);
    }

    void execTest(java.lang.String testClass, boolean success) throws java.lang.Exception {
        String java = (((System.getProperty("java.home") + File.separator) + "bin") + File.separator) + "java";
        String cp = System.getProperty("java.class.path");
        // use -classpath for JDK 1.1.7 compatibility
        String[] cmd = new String[]{ java, "-classpath", cp, "junit.textui.TestRunner", testClass };
        Process p = Runtime.getRuntime().exec(cmd);
        InputStream i = p.getInputStream();
        int b;
        while ((b = i.read()) != (-1))
            System.out.write(b);

        junit.framework.Assert.assertTrue((p.waitFor() == 0) == success);
    }
}