package junit.awtui;
/**
 * An AWT based user interface to run tests.
 * Enter the name of a class which either provides a static
 * suite method or is a subclass of TestCase.
 * <pre>
 * Synopsis: java junit.awtui.TestRunner [-noloading] [TestCase]
 * </pre>
 * TestRunner takes as an optional argument the name of the testcase class to be run.
 */
public class TestRunner extends junit.runner.BaseTestRunner {
    protected java.awt.Frame fFrame;

    protected java.util.Vector fExceptions;

    protected java.util.Vector fFailedTests;

    protected java.lang.Thread fRunner;

    protected junit.framework.TestResult fTestResult;

    protected java.awt.TextArea fTraceArea;

    protected java.awt.TextField fSuiteField;

    protected java.awt.Button fRun;

    protected junit.awtui.ProgressBar fProgressIndicator;

    protected java.awt.List fFailureList;

    protected junit.awtui.Logo fLogo;

    protected java.awt.Label fNumberOfErrors;

    protected java.awt.Label fNumberOfFailures;

    protected java.awt.Label fNumberOfRuns;

    protected java.awt.Button fQuitButton;

    protected java.awt.Button fRerunButton;

    protected java.awt.TextField fStatusLine;

    protected java.awt.Checkbox fUseLoadingRunner;

    protected static java.awt.Font PLAIN_FONT = new java.awt.Font("dialog", java.awt.Font.PLAIN, 12);

    private static final int GAP = 4;

    public TestRunner() {
    }

    private void about() {
        junit.awtui.AboutDialog about = new junit.awtui.AboutDialog(fFrame);
        about.setModal(true);
        about.setLocation(300, 300);
        about.setVisible(true);
    }

    public void addError(junit.framework.Test test, java.lang.Throwable t) {
        fNumberOfErrors.setText(java.lang.Integer.toString(fTestResult.errorCount()));
        appendFailure("Error", test, t);
    }

    public void addFailure(junit.framework.Test test, junit.framework.AssertionFailedError t) {
        fNumberOfFailures.setText(java.lang.Integer.toString(fTestResult.failureCount()));
        appendFailure("Failure", test, t);
    }

    protected void addGrid(java.awt.Panel p, java.awt.Component co, int x, int y, int w, int fill, double wx, int anchor) {
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = w;
        c.anchor = anchor;
        c.weightx = wx;
        c.fill = fill;
        if ((fill == java.awt.GridBagConstraints.BOTH) || (fill == java.awt.GridBagConstraints.VERTICAL))
            c.weighty = 1.0;

        c.insets = new java.awt.Insets(y == 0 ? junit.awtui.TestRunner.GAP : 0, x == 0 ? junit.awtui.TestRunner.GAP : 0, junit.awtui.TestRunner.GAP, junit.awtui.TestRunner.GAP);
        p.add(co, c);
    }

    private void appendFailure(java.lang.String kind, junit.framework.Test test, java.lang.Throwable t) {
        kind += ": " + test;
        java.lang.String msg = t.getMessage();
        if (msg != null) {
            kind += ":" + junit.runner.BaseTestRunner.truncate(msg);
        }
        fFailureList.add(kind);
        fExceptions.addElement(t);
        fFailedTests.addElement(test);
        if (fFailureList.getItemCount() == 1) {
            fFailureList.select(0);
            failureSelected();
        }
    }

    /**
     * Creates the JUnit menu. Clients override this
     * method to add additional menu items.
     */
    protected java.awt.Menu createJUnitMenu() {
        java.awt.Menu menu = new java.awt.Menu("JUnit");
        java.awt.MenuItem mi = new java.awt.MenuItem("About...");
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                about();
            }
        });
        menu.add(mi);
        menu.addSeparator();
        mi = new java.awt.MenuItem("Exit");
        mi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                java.lang.System.exit(0);
            }
        });
        menu.add(mi);
        return menu;
    }

    protected void createMenus(java.awt.MenuBar mb) {
        mb.add(createJUnitMenu());
    }

    protected junit.framework.TestResult createTestResult() {
        return new junit.framework.TestResult();
    }

    protected java.awt.Frame createUI(java.lang.String suiteName) {
        java.awt.Frame frame = new java.awt.Frame("JUnit");
        java.awt.Image icon = loadFrameIcon();
        if (icon != null)
            frame.setIconImage(icon);

        frame.setLayout(new java.awt.BorderLayout(0, 0));
        frame.setBackground(java.awt.SystemColor.control);
        final java.awt.Frame finalFrame = frame;
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                finalFrame.dispose();
                java.lang.System.exit(0);
            }
        });
        java.awt.MenuBar mb = new java.awt.MenuBar();
        createMenus(mb);
        frame.setMenuBar(mb);
        // ---- first section
        java.awt.Label suiteLabel = new java.awt.Label("Test class name:");
        fSuiteField = new java.awt.TextField(suiteName != null ? suiteName : "");
        fSuiteField.selectAll();
        fSuiteField.requestFocus();
        fSuiteField.setFont(junit.awtui.TestRunner.PLAIN_FONT);
        fSuiteField.setColumns(40);
        fSuiteField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                runSuite();
            }
        });
        fSuiteField.addTextListener(new java.awt.event.TextListener() {
            public void textValueChanged(java.awt.event.TextEvent e) {
                fRun.setEnabled(fSuiteField.getText().length() > 0);
                fStatusLine.setText("");
            }
        });
        fRun = new java.awt.Button("Run");
        fRun.setEnabled(false);
        fRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                runSuite();
            }
        });
        boolean useLoader = useReloadingTestSuiteLoader();
        fUseLoadingRunner = new java.awt.Checkbox("Reload classes every run", useLoader);
        if (junit.runner.BaseTestRunner.inVAJava())
            fUseLoadingRunner.setVisible(false);

        // ---- second section
        fProgressIndicator = new junit.awtui.ProgressBar();
        // ---- third section
        fNumberOfErrors = new java.awt.Label("0000", java.awt.Label.RIGHT);
        fNumberOfErrors.setText("0");
        fNumberOfErrors.setFont(junit.awtui.TestRunner.PLAIN_FONT);
        fNumberOfFailures = new java.awt.Label("0000", java.awt.Label.RIGHT);
        fNumberOfFailures.setText("0");
        fNumberOfFailures.setFont(junit.awtui.TestRunner.PLAIN_FONT);
        fNumberOfRuns = new java.awt.Label("0000", java.awt.Label.RIGHT);
        fNumberOfRuns.setText("0");
        fNumberOfRuns.setFont(junit.awtui.TestRunner.PLAIN_FONT);
        java.awt.Panel numbersPanel = new java.awt.Panel(new java.awt.FlowLayout());
        numbersPanel.add(new java.awt.Label("Runs:"));
        numbersPanel.add(fNumberOfRuns);
        numbersPanel.add(new java.awt.Label("   Errors:"));
        numbersPanel.add(fNumberOfErrors);
        numbersPanel.add(new java.awt.Label("   Failures:"));
        numbersPanel.add(fNumberOfFailures);
        // ---- fourth section
        java.awt.Label failureLabel = new java.awt.Label("Errors and Failures:");
        fFailureList = new java.awt.List(5);
        fFailureList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                failureSelected();
            }
        });
        fRerunButton = new java.awt.Button("Run");
        fRerunButton.setEnabled(false);
        fRerunButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                rerun();
            }
        });
        java.awt.Panel failedPanel = new java.awt.Panel(new java.awt.GridLayout(0, 1, 0, 2));
        failedPanel.add(fRerunButton);
        fTraceArea = new java.awt.TextArea();
        fTraceArea.setRows(5);
        fTraceArea.setColumns(60);
        // ---- fifth section
        fStatusLine = new java.awt.TextField();
        fStatusLine.setFont(junit.awtui.TestRunner.PLAIN_FONT);
        fStatusLine.setEditable(false);
        fStatusLine.setForeground(java.awt.Color.red);
        fQuitButton = new java.awt.Button("Exit");
        fQuitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                java.lang.System.exit(0);
            }
        });
        // ---------
        fLogo = new junit.awtui.Logo();
        // ---- overall layout
        java.awt.Panel panel = new java.awt.Panel(new java.awt.GridBagLayout());
        addGrid(panel, suiteLabel, 0, 0, 2, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fSuiteField, 0, 1, 2, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fRun, 2, 1, 1, java.awt.GridBagConstraints.HORIZONTAL, 0.0, java.awt.GridBagConstraints.CENTER);
        addGrid(panel, fUseLoadingRunner, 0, 2, 2, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fProgressIndicator, 0, 3, 2, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fLogo, 2, 3, 1, java.awt.GridBagConstraints.NONE, 0.0, java.awt.GridBagConstraints.NORTH);
        addGrid(panel, numbersPanel, 0, 4, 2, java.awt.GridBagConstraints.NONE, 0.0, java.awt.GridBagConstraints.CENTER);
        addGrid(panel, failureLabel, 0, 5, 2, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fFailureList, 0, 6, 2, java.awt.GridBagConstraints.BOTH, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, failedPanel, 2, 6, 1, java.awt.GridBagConstraints.HORIZONTAL, 0.0, java.awt.GridBagConstraints.CENTER);
        addGrid(panel, fTraceArea, 0, 7, 2, java.awt.GridBagConstraints.BOTH, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fStatusLine, 0, 8, 2, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.CENTER);
        addGrid(panel, fQuitButton, 2, 8, 1, java.awt.GridBagConstraints.HORIZONTAL, 0.0, java.awt.GridBagConstraints.CENTER);
        frame.add(panel, java.awt.BorderLayout.CENTER);
        frame.pack();
        return frame;
    }

    public void failureSelected() {
        fRerunButton.setEnabled(isErrorSelected());
        showErrorTrace();
    }

    public void endTest(junit.framework.Test test) {
        setLabelValue(fNumberOfRuns, fTestResult.runCount());
        synchronized(this) {
            fProgressIndicator.step(fTestResult.wasSuccessful());
        }
    }

    private boolean isErrorSelected() {
        return fFailureList.getSelectedIndex() != (-1);
    }

    private java.awt.Image loadFrameIcon() {
        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        try {
            java.net.URL url = junit.runner.BaseTestRunner.class.getResource("smalllogo.gif");
            return toolkit.createImage(((java.awt.image.ImageProducer) (url.getContent())));
        } catch (java.lang.Exception ex) {
        }
        return null;
    }

    public java.lang.Thread getRunner() {
        return fRunner;
    }

    public static void main(java.lang.String[] args) {
        new junit.awtui.TestRunner().start(args);
    }

    public static void run(java.lang.Class test) {
        java.lang.String args[] = new java.lang.String[]{ test.getName() };
        junit.awtui.TestRunner.main(args);
    }

    public void rerun() {
        int index = fFailureList.getSelectedIndex();
        if (index == (-1))
            return;

        junit.framework.Test test = ((junit.framework.Test) (fFailedTests.elementAt(index)));
        if (!(test instanceof junit.framework.TestCase)) {
            showInfo("Could not reload " + test.toString());
            return;
        }
        junit.framework.Test reloadedTest = null;
        try {
            java.lang.Class reloadedTestClass = getLoader().reload(test.getClass());
            java.lang.Class[] classArgs = new java.lang.Class[]{ java.lang.String.class };
            java.lang.reflect.Constructor constructor = reloadedTestClass.getConstructor(classArgs);
            java.lang.Object[] args = new java.lang.Object[]{ ((junit.framework.TestCase) (test)).getName() };
            reloadedTest = ((junit.framework.Test) (constructor.newInstance(args)));
        } catch (java.lang.Exception e) {
            showInfo("Could not reload " + test.toString());
            return;
        }
        junit.framework.TestResult result = new junit.framework.TestResult();
        reloadedTest.run(result);
        java.lang.String message = reloadedTest.toString();
        if (result.wasSuccessful())
            showInfo(message + " was successful");
        else if (result.errorCount() == 1)
            showStatus(message + " had an error");
        else
            showStatus(message + " had a failure");

    }

    protected void reset() {
        setLabelValue(fNumberOfErrors, 0);
        setLabelValue(fNumberOfFailures, 0);
        setLabelValue(fNumberOfRuns, 0);
        fProgressIndicator.reset();
        fRerunButton.setEnabled(false);
        fFailureList.removeAll();
        fExceptions = new java.util.Vector(10);
        fFailedTests = new java.util.Vector(10);
        fTraceArea.setText("");
    }

    /**
     * runs a suite.
     *
     * @deprecated use runSuite() instead
     */
    public void run() {
        runSuite();
    }

    protected void runFailed(java.lang.String message) {
        showStatus(message);
        fRun.setLabel("Run");
        fRunner = null;
    }

    public synchronized void runSuite() {
        if (fRunner != null) {
            fTestResult.stop();
        } else {
            setLoading(shouldReload());
            fRun.setLabel("Stop");
            showInfo("Initializing...");
            reset();
            showInfo("Load Test Case...");
            final junit.framework.Test testSuite = getTest(fSuiteField.getText());
            if (testSuite != null) {
                fRunner = new java.lang.Thread() {
                    public void run() {
                        fTestResult = createTestResult();
                        fTestResult.addListener(TestRunner.this);
                        fProgressIndicator.start(testSuite.countTestCases());
                        showInfo("Running...");
                        long startTime = java.lang.System.currentTimeMillis();
                        testSuite.run(fTestResult);
                        if (fTestResult.shouldStop()) {
                            showStatus("Stopped");
                        } else {
                            long endTime = java.lang.System.currentTimeMillis();
                            long runTime = endTime - startTime;
                            showInfo(("Finished: " + elapsedTimeAsString(runTime)) + " seconds");
                        }
                        fTestResult = null;
                        fRun.setLabel("Run");
                        fRunner = null;
                        java.lang.System.gc();
                    }
                };
                fRunner.start();
            }
        }
    }

    private boolean shouldReload() {
        return (!junit.runner.BaseTestRunner.inVAJava()) && fUseLoadingRunner.getState();
    }

    private void setLabelValue(java.awt.Label label, int value) {
        label.setText(java.lang.Integer.toString(value));
        label.invalidate();
        label.getParent().validate();
    }

    public void setSuiteName(java.lang.String suite) {
        fSuiteField.setText(suite);
    }

    private void showErrorTrace() {
        int index = fFailureList.getSelectedIndex();
        if (index == (-1))
            return;

        java.lang.Throwable t = ((java.lang.Throwable) (fExceptions.elementAt(index)));
        fTraceArea.setText(junit.runner.BaseTestRunner.getFilteredTrace(t));
    }

    private void showInfo(java.lang.String message) {
        fStatusLine.setFont(junit.awtui.TestRunner.PLAIN_FONT);
        fStatusLine.setForeground(java.awt.Color.black);
        fStatusLine.setText(message);
    }

    protected void clearStatus() {
        showStatus("");
    }

    private void showStatus(java.lang.String status) {
        fStatusLine.setFont(junit.awtui.TestRunner.PLAIN_FONT);
        fStatusLine.setForeground(java.awt.Color.red);
        fStatusLine.setText(status);
    }

    /**
     * Starts the TestRunner
     */
    public void start(java.lang.String[] args) {
        java.lang.String suiteName = processArguments(args);
        fFrame = createUI(suiteName);
        fFrame.setLocation(200, 200);
        fFrame.setVisible(true);
        if (suiteName != null) {
            setSuiteName(suiteName);
            runSuite();
        }
    }

    public void startTest(junit.framework.Test test) {
        showInfo("Running: " + test);
    }
}