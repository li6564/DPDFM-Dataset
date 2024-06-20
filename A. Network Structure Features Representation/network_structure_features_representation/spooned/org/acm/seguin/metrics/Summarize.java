package org.acm.seguin.metrics;
public class Summarize {
    private java.io.File dir;

    public Summarize(java.lang.String dirName) {
        dir = new java.io.File(dirName);
    }

    public void run() {
        java.io.File[] list = dir.listFiles();
        for (int ndx = 0; ndx < list.length; ndx++) {
            extractTotals(list[ndx]);
        }
    }

    private void extractTotals(java.io.File input) {
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(input));
            java.lang.String name = input.getName();
            name = name.substring(0, name.indexOf(".csv"));
            java.lang.String line = bufferedReader.readLine();
            while (line != null) {
                if (line.indexOf("---,---,---") >= 0) {
                    java.lang.System.out.println((name + ",") + line);
                }
                line = bufferedReader.readLine();
            } 
            bufferedReader.close();
        } catch (java.io.IOException ioe) {
        }
    }

    public static void main(java.lang.String[] args) {
        if (args.length == 0) {
            java.lang.System.out.println("Syntax:  org.acm.seguin.metrics.Summarize <dir>");
        } else {
            new org.acm.seguin.metrics.Summarize(args[0]).run();
        }
    }
}