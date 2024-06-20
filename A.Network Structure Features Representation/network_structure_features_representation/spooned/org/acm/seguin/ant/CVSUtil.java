package org.acm.seguin.ant;
/**
 *
 * @author Ara Abrahamian (ara_e@email.com)
 * @created June 21, 2001
 * @version $Revision: 1.1 $
 */
public final class CVSUtil {
    private java.util.HashMap entries;

    private static java.util.TimeZone tz;

    static {
        tz = java.util.TimeZone.getTimeZone("GMT");
    }

    public CVSUtil() {
        entries = new java.util.HashMap();
    }

    public boolean isFileModified(java.io.File file) {
        org.acm.seguin.ant.CVSUtil.CVSEntry entry = ((org.acm.seguin.ant.CVSUtil.CVSEntry) (entries.get(file.toString().replace(java.io.File.separatorChar, '/'))));
        // either a new file or the Entries file not loaded yet
        if (entry == null) {
            // try loading the Entries file
            entry = loadEntriesFileFor(file);
            // new file, not yet placed at cvs
            if (entry == null) {
                return true;
            }
        }
        return !entry.equalsTime(file.lastModified());
    }

    private org.acm.seguin.ant.CVSUtil.CVSEntry loadEntriesFileFor(java.io.File file) {
        java.io.File workingDirectory = file.getParentFile();
        int linenum = 0;
        java.lang.String line = null;
        java.io.BufferedReader in = null;
        java.io.File entriesFile = new java.io.File(workingDirectory + "/CVS/Entries");
        if (!entriesFile.exists())
            return null;

        try {
            in = new java.io.BufferedReader(new java.io.FileReader(entriesFile));
            for (linenum = 1; ; ++linenum) {
                try {
                    line = in.readLine();
                } catch (java.io.IOException ex) {
                    line = null;
                    break;
                }
                if (line == null)
                    break;

                if (line.startsWith("/")) {
                    try {
                        org.acm.seguin.ant.CVSUtil.CVSEntry entry = org.acm.seguin.ant.CVSUtil.CVSEntry.parseEntryLine(workingDirectory, line);
                        entries.put(entry.getFileName(), entry);
                    } catch (java.text.ParseException ex) {
                        java.lang.System.err.println((((("Bad 'Entries' line " + linenum) + ", '") + line) + "' - ") + ex.getMessage());
                    }
                }
            }
        } catch (java.io.IOException ex) {
            in = null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (java.io.IOException ex) {
                }
            }
        }
        return ((org.acm.seguin.ant.CVSUtil.CVSEntry) (entries.get(file.toString().replace(java.io.File.separatorChar, '/'))));
    }

    public static class CVSEntry {
        private java.lang.String fileName;

        private java.util.Date date;

        public java.lang.String getFileName() {
            return fileName;
        }

        private java.util.Date getDate() {
            return date;
        }

        public java.lang.String toString() {
            return (("fileName=" + fileName) + " date=") + date;
        }

        public int hashCode() {
            return fileName.hashCode();
        }

        public boolean equals(java.lang.Object src) {
            if (src instanceof org.acm.seguin.ant.CVSUtil.CVSEntry)
                return fileName.equals(((org.acm.seguin.ant.CVSUtil.CVSEntry) (src)).getFileName());
            else
                return false;

        }

        /**
         * Determines if this timestamp is considered equivalent to
         * the time represented by the parameter we are passed. Note
         * that we allow up to, but not including, one second of time
         * difference, since Java allows millisecond time resolution
         * while CVS stores second resolution timestamps. Further, we
         * allow the resolution difference on either side of the second
         * because we can not be sure of the rounding.
         */
        public boolean equalsTime(long time) {
            // System.out.println("time="+time);
            // System.out.println("date.getTime()="+date.getTime());
            return date.getTime() > time ? (date.getTime() - time) < 1000 : (time - date.getTime()) < 1000;
        }

        private static java.lang.String parseAToken(java.util.StringTokenizer toker) {
            java.lang.String token = null;
            try {
                token = toker.nextToken();
            } catch (java.util.NoSuchElementException ex) {
                token = null;
            }
            return token;
        }

        public static org.acm.seguin.ant.CVSUtil.CVSEntry parseEntryLine(java.io.File parent_dir, java.lang.String parseLine) throws java.text.ParseException {
            java.lang.String token = null;
            java.lang.String nameToke = null;
            java.lang.String versionToke = null;
            java.lang.String conflictToke = null;
            java.lang.String optionsToke = null;
            java.lang.String tagToke = null;
            java.util.StringTokenizer toker = new java.util.StringTokenizer(parseLine, "/", true);
            int tokeCount = toker.countTokens();
            if (tokeCount < 6) {
                throw new java.text.ParseException((("not enough tokens in entries line " + "(min 6, parsed ") + tokeCount) + ")", 0);
            }
            token = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);// the starting slash

            nameToke = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);
            if (nameToke == null) {
                throw new java.text.ParseException("could not parse entry name", 0);
            } else if (nameToke.equals("/")) {
                throw new java.text.ParseException("entry has an empty name", 0);
            } else {
                token = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);
                if ((token == null) || (!token.equals("/")))
                    throw new java.text.ParseException("could not parse version's starting slash", 0);

            }
            versionToke = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);
            if (versionToke == null) {
                throw new java.text.ParseException("out of tokens getting version field", 0);
            } else if (versionToke.equals("/")) {
                versionToke = "";
            } else {
                token = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);
                if ((token == null) || (!token.equals("/")))
                    throw new java.text.ParseException("could not parse conflict's starting slash", 0);

            }
            conflictToke = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);
            if (conflictToke == null) {
                throw new java.text.ParseException("out of tokens getting conflict field", 0);
            } else if (conflictToke.equals("/")) {
                conflictToke = "";
            } else {
                token = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);
                if ((token == null) || (!token.equals("/")))
                    throw new java.text.ParseException("could not parse options' starting slash", 0);

            }
            optionsToke = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);
            if (optionsToke == null) {
                throw new java.text.ParseException("out of tokens getting options field", 0);
            } else if (optionsToke.equals("/")) {
                optionsToke = "";
            } else {
                token = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);
                if ((token == null) || (!token.equals("/")))
                    throw new java.text.ParseException("could not parse tag's starting slash", 0);

            }
            tagToke = org.acm.seguin.ant.CVSUtil.CVSEntry.parseAToken(toker);
            if ((tagToke == null) || tagToke.equals("/")) {
                tagToke = "";
            }
            org.acm.seguin.ant.CVSUtil.CVSEntry entry = new org.acm.seguin.ant.CVSUtil.CVSEntry();
            nameToke = (parent_dir.toString() + "/") + nameToke;
            entry.fileName = nameToke.replace(java.io.File.separatorChar, '/');
            entry.setTimestamp(conflictToke);
            return entry;
        }

        public java.util.Date parseTimestamp(java.lang.String source) throws java.text.ParseException {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", java.util.Locale.US);
            dateFormat.setTimeZone(org.acm.seguin.ant.CVSUtil.tz);
            java.util.Date result = dateFormat.parse(source, new java.text.ParsePosition(0));
            if (result == null)
                throw new java.text.ParseException(("invalid timestamp '" + source) + "'", 0);

            return result;
        }

        public void setTimestamp(java.lang.String timeStamp) {
            java.lang.String tstamp = new java.lang.String(timeStamp);
            java.lang.String conflict = null;
            if (tstamp.length() < 1) {
                this.date = null;
            } else if (tstamp.startsWith("+")) {
                // We have received a "+conflict" format, which
                // typically only comes from the server.
                conflict = tstamp.substring(1);
                if (conflict.equals("=")) {
                    // In this case, the server is indicating that the
                    // file is "going to be equal" once the 'Merged' handling
                    // is completed. To retain the "inConflict" nature of
                    // the entry, we will simply set the conflict to an
                    // empty string (not null), as the conflict will be
                    // set very shortly as a result of the 'Merged' handling.
                    // 
                    conflict = "";
                }
            } else {
                int index = tstamp.indexOf('+');
                if (index < 0) {
                    this.date = null;
                } else {
                    // The "timestamp+conflict" case.
                    // This should <em>only</em> comes from an Entries
                    // file, and should never come from the server.
                    conflict = tstamp.substring(index + 1);
                    tstamp = tstamp.substring(0, index);
                    this.date = null;// signal need to parse!

                    if (tstamp.equals("Result of merge")) {
                        // REVIEW should we always set to conflict?
                        // If timestamp is empty, use the conflict...
                        if (((timeStamp == null) || (timeStamp.length() == 0)) && (conflict.length() > 0)) {
                            timeStamp = conflict;
                        }
                    } else {
                        timeStamp = tstamp;
                    }
                }
            }
            // If tsCache is set to null, we need to update it...
            if ((this.date == null) && (timeStamp.length() > 0)) {
                try {
                    this.date = parseTimestamp(timeStamp);
                } catch (java.text.ParseException ex) {
                    this.date = null;
                }
            }
        }
    }
}