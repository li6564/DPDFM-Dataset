package org.acm.seguin.ant;
/**
 *
 * @author Ara Abrahamian (ara_e@email.com)
 * @created May 18, 2001
 * @version $Revision: 1.1 $
 */
public final class Pretty extends org.acm.seguin.ant.Task {
    private java.util.Vector filesets = new java.util.Vector();

    private boolean cvs = false;

    private org.acm.seguin.ant.CVSUtil cvsUtil = new org.acm.seguin.ant.CVSUtil();

    /**
     * Adds a set of files (nested fileset attribute).
     */
    public void addFileset(org.acm.seguin.refactor.undo.FileSet set) {
        filesets.addElement(set);
    }

    public void setSettingsdir(java.io.File new_settings_dir) {
        org.acm.seguin.util.FileSettings.setSettingsRoot(new_settings_dir);
    }

    public void setCvs(boolean cvs) {
        this.cvs = cvs;
    }

    public void execute() throws org.acm.seguin.ant.BuildException {
        // make sure we don't have an illegal set of options
        validateAttributes();
        try {
            org.acm.seguin.pretty.PrettyPrintFile ppf = new org.acm.seguin.pretty.PrettyPrintFile();
            ppf.setAsk(false);
            for (int i = 0; i < filesets.size(); i++) {
                org.acm.seguin.refactor.undo.FileSet fs = ((org.acm.seguin.refactor.undo.FileSet) (filesets.elementAt(i)));
                org.acm.seguin.ant.DirectoryScanner ds = fs.getDirectoryScanner(project);
                java.io.File from_dir = fs.getDir(project);
                java.lang.String[] src_files = ds.getIncludedFiles();
                for (int j = 0; j < src_files.length; j++) {
                    java.io.File source_file = new java.io.File((from_dir + java.io.File.separator) + src_files[j]);
                    if ((cvs == false) || ((cvs == true) && cvsUtil.isFileModified(source_file))) {
                        java.lang.System.out.println("formatting:" + source_file);
                        ppf.setParserFactory(new org.acm.seguin.parser.factory.FileParserFactory(source_file));
                        // reformat
                        ppf.apply(source_file);
                    }
                }
            }
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new org.acm.seguin.ant.BuildException("Cannot javastyle files", location);
        }
    }

    /**
     * Ensure we have a consistent and legal set of attributes, and set
     * any internal flags necessary based on different combinations
     * of attributes.
     */
    protected void validateAttributes() throws org.acm.seguin.ant.BuildException {
        if (filesets.size() == 0) {
            throw new org.acm.seguin.ant.BuildException("Specify at least one fileset.");
        }
        // possibly some other attributes: overwrite/destDir/etc
    }
}