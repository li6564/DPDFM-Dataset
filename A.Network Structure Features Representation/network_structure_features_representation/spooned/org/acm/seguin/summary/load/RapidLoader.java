package org.acm.seguin.summary.load;
/**
 * This code is responsible for speeding the loading and saving of the meta
 *  data about the different classes.
 *
 * @author Chris Seguin
 */
public class RapidLoader {
    /**
     * This will save the classes
     */
    public void save() {
        new org.acm.seguin.summary.load.RapidLoader.SaveThread().start();
    }

    /**
     * This will load the classes
     */
    public void load() {
        try {
            java.lang.System.out.println("RapidLoader.load()");
            java.lang.String filename = (((org.acm.seguin.util.FileSettings.getSettingsRoot() + java.io.File.separator) + ".refactory") + java.io.File.separator) + "data.sof";
            java.io.FileInputStream fileInput = new java.io.FileInputStream(filename);
            java.io.BufferedInputStream bufferInput = new java.io.BufferedInputStream(fileInput);
            java.io.ObjectInputStream in = new java.io.ObjectInputStream(bufferInput);
            org.acm.seguin.summary.PackageSummary.loadAll(in);
            in.close();
            org.acm.seguin.summary.SummaryTraversal.setFrameworkLoader(new org.acm.seguin.summary.load.FlashLoader());
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace(java.lang.System.out);
        }
    }

    /**
     * Separate thread to save the data to the serialized object file
     *
     * @author Chris Seguin
     */
    public class SaveThread extends java.lang.Thread {
        /**
         * Main processing method for the SaveThread object
         */
        public void run() {
            try {
                java.lang.String filename = (((org.acm.seguin.util.FileSettings.getSettingsRoot() + java.io.File.separator) + ".refactory") + java.io.File.separator) + "data.sof";
                java.io.FileOutputStream fileOutput = new java.io.FileOutputStream(filename);
                java.io.BufferedOutputStream bufferOutput = new java.io.BufferedOutputStream(fileOutput);
                java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(bufferOutput);
                org.acm.seguin.summary.PackageSummary.saveAll(out);
                out.close();
            } catch (java.io.IOException ioe) {
                ioe.printStackTrace(java.lang.System.out);
            }
        }
    }
}