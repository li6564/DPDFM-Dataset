/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * This object stores all the data necessary to print the the Java file
 *
 * @author Chris Seguin
 * @created March 6, 1999
 */
public class PrintData {
    /**
     * Description of the Field
     */
    public int finalLine = -1;

    // Instance Variables
    private int indent;

    private org.acm.seguin.pretty.LineQueue lineQueue;

    private java.lang.StringBuffer outputBuffer;

    private int newlineCount;

    private int last = org.acm.seguin.pretty.PrintData.EMPTY;

    private int INDENT = 4;

    private char indentChar = ' ';

    /**
     * Use the C style blocks
     */
    public static final int BLOCK_STYLE_C = 0;

    /**
     * Use the PASCAL style blocks
     */
    public static final int BLOCK_STYLE_PASCAL = 1;

    /**
     * Use the EMACS style of blocks (like pascal but 2 spaces)
     */
    public static final int BLOCK_STYLE_EMACS = 2;

    private int codeBlockStyle = org.acm.seguin.pretty.PrintData.BLOCK_STYLE_C;

    private int methodBlockStyle = org.acm.seguin.pretty.PrintData.BLOCK_STYLE_C;

    private int classBlockStyle = org.acm.seguin.pretty.PrintData.BLOCK_STYLE_C;

    // Set to true after method parameters are formatted, before the
    // opening brace
    private boolean isMethodBrace = false;

    // Method parameter indentation
    private boolean lineUpParams = true;

    private boolean inParams = false;

    private int lastParamIndent = 0;

    private boolean exprSpace = false;

    private int linesBetween = 2;

    private int javadocMinimum = 40;

    private int javadocMaximum = 80;

    private boolean spaceAfterCast = true;

    private boolean spaceAfterKeyword = true;

    private int javadocStars = 2;

    private org.acm.seguin.util.FileSettings bundle;

    private org.acm.seguin.pretty.sort.MultipleOrdering morder;

    private java.util.Vector classNameStack;

    private int surpriseType;

    private int fieldNameIndent;

    private boolean keepAllJavadoc;

    private boolean reformatComments;

    private int originalLine = -1;

    private java.util.Vector fieldStack;

    private int fieldSpaceCode;

    private int dynamicFieldSpace;

    private boolean storeJavadocPrinted = false;

    private boolean skipNameSpacing = false;

    private int tempEqualsLength;

    private int cStyleFormatCode = org.acm.seguin.pretty.PrintData.CSC_ALIGN_STAR;

    private int cStyleIndent = 2;

    private boolean forceBlock;

    private boolean isClassBrace = false;

    private boolean emptyBlockOnSingleLine = false;

    private boolean castSpace = true;

    private boolean documentNestedClasses = true;

    // Class Variables
    /**
     * Description of the Field
     */
    public static int EMPTY = 0;

    /**
     * Description of the Field
     */
    public static int METHOD = 1;

    /**
     * Description of the Field
     */
    public static int FIELD = 2;

    /**
     * Description of the Field
     */
    public static int INTERFACE = 3;

    /**
     * Description of the Field
     */
    public static int CLASS = 3;

    /**
     * The indent type for an unexpected end of line - single indent
     */
    public static int SINGLE_INDENT = 1;

    /**
     * The indent type for an unexpected end of line - double indent
     */
    public static int DOUBLE_INDENT = 2;

    /**
     * The indent type for an unexpected end of line - line up parameters indent
     */
    public static int PARAM_INDENT = 3;

    /**
     * A comment with javadoc
     */
    public static final int JAVADOC_COMMENT = 1;

    /**
     * A c style comment
     */
    public static final int C_STYLE_COMMENT = 2;

    /**
     * A category comment
     */
    public static final int CATEGORY_COMMENT = 3;

    /**
     * A single line comment
     */
    public static final int SINGLE_LINE_COMMENT = 4;

    /**
     * Never use dynamic field spacing
     */
    public static final int DFS_NEVER = 0;

    /**
     * ALWAYS use dynamic field spacing
     */
    public static final int DFS_ALWAYS = 1;

    /**
     * Use dynamic field spacing except with javadoc
     */
    public static final int DFS_NOT_WITH_JAVADOC = 2;

    /**
     * Only align on the equals
     */
    public static final int DFS_ALIGN_EQUALS = 3;

    /**
     * Leaves C Style comments untouched
     */
    public static final int CSC_LEAVE_UNTOUCHED = 1;

    /**
     * Aligns the C style comments with a * to the right
     */
    public static final int CSC_ALIGN_STAR = 0;

    /**
     * Aligns the C style comments with a * to the right
     */
    public static final int CSC_ALIGN_BLANK = 2;

    /**
     * Maintains spacing in the C style comments, but insists upon a star at the
     *  right
     */
    public static final int CSC_MAINTAIN_STAR = 3;

    /**
     * Create a print data object
     */
    public PrintData() {
        this(java.lang.System.out);
    }

    /**
     * Create a print data object
     *
     * @param out
     * 		the output stream
     */
    public PrintData(java.io.OutputStream out) {
        indent = 0;
        lineQueue = lineQueueFactory(new java.io.PrintWriter(new java.io.OutputStreamWriter(out)));
        outputBuffer = new java.lang.StringBuffer();
        newlineCount = 0;
        // Load the properties
        bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "pretty");
        try {
            INDENT = bundle.getInteger("indent");
            java.lang.String indentCharacter = bundle.getString("indent.char");
            if (indentCharacter.equalsIgnoreCase("space")) {
                indentChar = ' ';
            } else if (indentCharacter.equalsIgnoreCase("tab")) {
                indentChar = '\t';
            } else {
                indentChar = indentCharacter.charAt(0);
            }
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            // Default is sufficient
        }
        codeBlockStyle = translateBlockStyle("block.style");
        methodBlockStyle = translateBlockStyle("method.block.style");
        classBlockStyle = translateBlockStyle("class.block.style");
        try {
            exprSpace = new java.lang.Boolean(bundle.getString("expr.space")).booleanValue();
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            // Default is sufficient
        }
        try {
            lineUpParams = new java.lang.Boolean(bundle.getString("params.lineup")).booleanValue();
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            // Default is sufficient
        }
        try {
            linesBetween = java.lang.Integer.parseInt(bundle.getString("lines.between"));
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            // Default is sufficient
        } catch (java.lang.NumberFormatException nfe) {
            // Default is sufficient
        }
        try {
            javadocMinimum = bundle.getInteger("javadoc.wordwrap.min");
            javadocMaximum = bundle.getInteger("javadoc.wordwrap.max");
        } catch (org.acm.seguin.util.MissingSettingsException snfe) {
            // Default is sufficient
        }
        try {
            spaceAfterCast = bundle.getBoolean("cast.space");
        } catch (org.acm.seguin.util.MissingSettingsException snfe) {
            // Default is sufficient
        }
        try {
            javadocStars = bundle.getInteger("javadoc.star");
        } catch (org.acm.seguin.util.MissingSettingsException snfe) {
            // Default is sufficient
        }
        try {
            spaceAfterKeyword = bundle.getBoolean("keyword.space");
        } catch (org.acm.seguin.util.MissingSettingsException snfe) {
            // Default is sufficient
        }
        try {
            java.lang.String value = bundle.getString("variable.spacing");
            if (value.equalsIgnoreCase("dynamic")) {
                fieldSpaceCode = org.acm.seguin.pretty.PrintData.DFS_ALWAYS;
            } else if (value.equalsIgnoreCase("javadoc.dynamic")) {
                fieldSpaceCode = org.acm.seguin.pretty.PrintData.DFS_NOT_WITH_JAVADOC;
            } else if (value.equalsIgnoreCase("align.equals")) {
                fieldSpaceCode = org.acm.seguin.pretty.PrintData.DFS_ALIGN_EQUALS;
            } else {
                fieldSpaceCode = org.acm.seguin.pretty.PrintData.DFS_NEVER;
            }
        } catch (org.acm.seguin.util.MissingSettingsException snfe) {
            fieldSpaceCode = org.acm.seguin.pretty.PrintData.DFS_NEVER;
        }
        morder = new org.acm.seguin.pretty.sort.MultipleOrdering(bundle);
        classNameStack = new java.util.Vector();
        fieldStack = new java.util.Vector();
        java.lang.String surpriseReturnString;
        try {
            surpriseReturnString = bundle.getString("surprise.return");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            java.lang.System.out.println("Cannot find surprise.return");
            surpriseReturnString = "double";
        }
        if (surpriseReturnString.equalsIgnoreCase("single")) {
            surpriseType = org.acm.seguin.pretty.PrintData.SINGLE_INDENT;
        } else if (surpriseReturnString.equalsIgnoreCase("param")) {
            surpriseType = org.acm.seguin.pretty.PrintData.PARAM_INDENT;
        } else {
            surpriseType = org.acm.seguin.pretty.PrintData.DOUBLE_INDENT;
        }
        try {
            reformatComments = bundle.getBoolean("reformat.comments");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            reformatComments = true;
        }
        try {
            fieldNameIndent = bundle.getInteger("field.name.indent");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            fieldNameIndent = -1;
        }
        try {
            keepAllJavadoc = bundle.getBoolean("keep.all.javadoc");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            keepAllJavadoc = false;
        }
        try {
            forceBlock = bundle.getBoolean("force.block");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            forceBlock = true;
        }
        try {
            dynamicFieldSpace = bundle.getInteger("dynamic.variable.spacing");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            dynamicFieldSpace = 1;
        }
        try {
            java.lang.String temp = bundle.getString("c.style.format");
            if (temp.equalsIgnoreCase("leave")) {
                cStyleFormatCode = org.acm.seguin.pretty.PrintData.CSC_LEAVE_UNTOUCHED;
            } else if (temp.equalsIgnoreCase("maintain.space.star")) {
                cStyleFormatCode = org.acm.seguin.pretty.PrintData.CSC_MAINTAIN_STAR;
            } else if (temp.equalsIgnoreCase("align.blank")) {
                cStyleFormatCode = org.acm.seguin.pretty.PrintData.CSC_ALIGN_BLANK;
            } else {
                cStyleFormatCode = org.acm.seguin.pretty.PrintData.CSC_ALIGN_STAR;
            }
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            cStyleFormatCode = org.acm.seguin.pretty.PrintData.CSC_ALIGN_STAR;
        }
        try {
            cStyleIndent = bundle.getInteger("c.style.indent");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            cStyleIndent = 2;
        }
        try {
            emptyBlockOnSingleLine = bundle.getBoolean("empty.block.single.line");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            emptyBlockOnSingleLine = false;
        }
        try {
            castSpace = !bundle.getBoolean("cast.force.nospace");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            castSpace = true;
        }
        try {
            documentNestedClasses = bundle.getBoolean("document.nested.classes");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            documentNestedClasses = true;
        }
        try {
            allowSingleLineJavadoc = bundle.getBoolean("allow.singleline.javadoc");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            allowSingleLineJavadoc = false;
        }
        try {
            variablesAlignWithBlock = bundle.getBoolean("variable.align.with.block");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            variablesAlignWithBlock = false;
        }
        try {
            elseOnNewLine = bundle.getBoolean("else.start.line");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            elseOnNewLine = true;
        }
        fieldStack = new java.util.Vector();
    }

    /**
     * Set the output writer
     *
     * @param newOutput
     * 		the new output writer
     */
    public void setOutput(java.io.PrintWriter newOutput) {
        if (newOutput != null) {
            lineQueue = lineQueueFactory(newOutput);
        }
    }

    /**
     * Set the state
     *
     * @param newState
     * 		Description of Parameter
     */
    public void setState(int newState) {
        last = newState;
    }

    /**
     * Records the position of the method's opening parenthesis for use in
     *  indenting parameters on subsequent lines.
     */
    public void setParamIndent() {
        if (inParams && (lastParamIndent == 0)) {
            lastParamIndent = getLineLength();
        }
    }

    /**
     * Sets the ReformatComments attribute of the PrintData object
     *
     * @param value
     * 		The new ReformatComments value
     */
    public void setReformatComments(boolean value) {
        reformatComments = value;
    }

    /**
     * Sets the AbsoluteCommentSpacing attribute of the PrintData object
     *
     * @param value
     * 		The new AbsoluteCommentSpacing value
     */
    public void setAbsoluteCommentSpacing(int value) {
        lineQueue.setAbsoluteCommentSpacing(value);
    }

    /**
     * Sets the IncrementalCommentSpacing attribute of the PrintData object
     *
     * @param value
     * 		The new IncrementalCommentSpacing value
     */
    public void setIncrementalCommentSpacing(int value) {
        lineQueue.setIncrementalCommentSpacing(value);
    }

    /**
     * Sets the OriginalLine attribute of the PrintData object
     *
     * @param value
     * 		The new OriginalLine value
     */
    public void setOriginalLine(int value) {
        originalLine = value;
    }

    /**
     * Sets the FinalLine attribute of the PrintData object
     *
     * @param value
     * 		The new FinalLine value
     */
    public void setFinalLine(int value) {
        finalLine = value;
    }

    /**
     * Sets the Ownline attribute of the LineQueue object
     *
     * @param value
     * 		The new Ownline value
     */
    public void setOwnline(boolean value) {
        lineQueue.setOwnline(value);
    }

    /**
     * Sets the SharedIncr attribute of the PrintData object
     *
     * @param value
     * 		The new SharedIncr value
     */
    public void setSharedIncr(boolean value) {
        lineQueue.setSharedIncremental(value);
    }

    /**
     * Sets the OwnlineCode attribute of the PrintData object
     *
     * @param value
     * 		The new OwnlineCode value
     */
    public void setOwnlineCode(boolean value) {
        lineQueue.setOwnlineCode(value);
    }

    /**
     * Sets the DynamicFieldSpacing attribute of the PrintData object
     *
     * @param value
     * 		The new DynamicFieldSpacing value
     */
    public void setDynamicFieldSpacing(int value) {
        fieldSpaceCode = value;
    }

    /**
     * Sets the DynamicFieldSpacing attribute of the PrintData object
     *
     * @param value
     * 		The new DynamicFieldSpacing value
     */
    public void setDynamicFieldSpaces(int value) {
        dynamicFieldSpace = value;
    }

    /**
     * Sets the CStyleFormatCode attribute of the PrintData object
     *
     * @param value
     * 		The new CStyleFormatCode value
     */
    public void setCStyleFormatCode(int value) {
        cStyleFormatCode = value;
    }

    /**
     * Sets the CStyleIndent attribute of the PrintData object
     *
     * @param value
     * 		The new CStyleIndent value
     */
    public void setCStyleIndent(int value) {
        cStyleIndent = value;
    }

    /**
     * Sets the ForceBlock attribute of the PrintData object
     *
     * @param value
     * 		The new ForceBlock value
     */
    public void setForceBlock(boolean value) {
        forceBlock = value;
    }

    /**
     * Sets the ClassCStyleBlock attribute of the PrintData object
     *
     * @param value
     * 		The new ClassCStyleBlock value
     */
    public void setClassBlockStyle(int value) {
        classBlockStyle = value;
    }

    public void setMethodBlockStyle(int value) {
        methodBlockStyle = value;
    }

    public void setCodeBlockStyle(int value) {
        codeBlockStyle = value;
    }

    /**
     * Sets the EmptyBlockOnSingleLine attribute of the PrintData object
     *
     * @param value
     * 		The new EmptyBlockOnSingleLine value
     */
    public void setEmptyBlockOnSingleLine(boolean value) {
        emptyBlockOnSingleLine = value;
    }

    /**
     * Sets the CastSpace attribute of the PrintData object
     *
     * @param value
     * 		The new CastSpace value
     */
    public void setCastSpace(boolean value) {
        castSpace = value;
    }

    /**
     * Sets the ExpressionSpace attribute of the PrintData object
     *
     * @param value
     * 		The new ExpressionSpace value
     */
    public void setExpressionSpace(boolean value) {
        exprSpace = value;
    }

    /**
     * Sets the DocumentNestedClasses attribute of the PrintData object
     *
     * @param value
     * 		The new DocumentNestedClasses value
     */
    public void setDocumentNestedClasses(boolean value) {
        documentNestedClasses = value;
    }

    /**
     * Sets the MultipleOrdering attribute of the PrintData object
     *
     * @param value
     * 		The new MultipleOrdering value
     */
    public void setMultipleOrdering(org.acm.seguin.pretty.sort.MultipleOrdering value) {
        if (value != null) {
            morder = value;
        }
    }

    /**
     * Gets the AllJavadocKept attribute of the PrintData object
     *
     * @return The AllJavadocKept value
     */
    public boolean isAllJavadocKept() {
        return keepAllJavadoc;
    }

    /**
     * Return the indent string
     *
     * @return an appropriate length string
     */
    public java.lang.String getIndentString() {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        for (int ndx = 0; ndx < indent; ndx++) {
            buffer.append(indentChar);
        }
        return buffer.toString();
    }

    /**
     * Is the output buffer empty?
     *
     * @return true if the output buffer is empty
     */
    public boolean isBufferEmpty() {
        return outputBuffer.toString().trim().length() == 0;
    }

    /**
     * Is the output buffer empty?
     *
     * @return true if the output buffer is empty
     */
    public boolean isLineIndented() {
        return isBufferEmpty() && ((outputBuffer.toString().length() > 0) || (indent == 0));
    }

    /**
     * Return the state of the pretty printer
     *
     * @return an integer representing the state
     */
    public int getState() {
        return last;
    }

    /**
     * Get the length of the line
     *
     * @return the length of the buffer
     */
    public int getLineLength() {
        return outputBuffer.length();
    }

    /**
     * Gets the Order attribute of the PrintData object
     *
     * @return The Order value
     */
    public org.acm.seguin.pretty.sort.MultipleOrdering getOrder() {
        return morder;
    }

    /**
     * Gets the CurrentClassName attribute of the PrintData object
     *
     * @return The CurrentClassName value
     */
    public java.lang.String getCurrentClassName() {
        return ((java.lang.String) (classNameStack.elementAt(classNameStack.size() - 1)));
    }

    /**
     * Gets the Settings attribute of the PrintData object
     *
     * @return The Settings value
     */
    public org.acm.seguin.util.Settings getSettings() {
        return bundle;
    }

    /**
     * Gets the JavadocWordWrapMinimum attribute of the PrintData object
     *
     * @return The JavadocWordWrapMinimum value
     */
    public int getJavadocWordWrapMinimum() {
        return javadocMinimum;
    }

    /**
     * Gets the JavadocWordWrapMaximum attribute of the PrintData object
     *
     * @return The JavadocWordWrapMaximum value
     */
    public int getJavadocWordWrapMaximum() {
        return javadocMaximum;
    }

    /**
     * Gets the SpaceAfterCast attribute of the PrintData object
     *
     * @return The SpaceAfterCast value
     */
    public boolean isSpaceAfterCast() {
        return spaceAfterCast;
    }

    /**
     * Determines whether there should be a space between a keyword such as 'if'
     *  or 'while' and the opening brace that follows it.
     *
     * @return The SpaceAfterKeyword value
     */
    public boolean isSpaceAfterKeyword() {
        return spaceAfterKeyword;
    }

    /**
     * Gets the JavadocStarCount attribute of the PrintData object
     *
     * @return The JavadocStarCount value
     */
    public int getJavadocStarCount() {
        return javadocStars;
    }

    /**
     * Returns true if JavaDoc IDs (param, returns, etc.) should be lined up.
     *
     * @return True if lining up comments, false otherwise
     */
    public boolean isJavadocLinedUp() {
        try {
            return bundle.getBoolean("javadoc.id.lineup");
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            return true;
        }
    }

    /**
     * Returns the number of spaces between the JavaDoc asterisks and the
     *  comment text.
     *
     * @return the number of spaces between the JavaDoc asterisks and the
    comment
     */
    public int getJavadocIndent() {
        try {
            return bundle.getInteger("javadoc.indent");
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            return 2;
        }
    }

    /**
     * Determines if there should be a space between the '*' and the '@' in a
     *  javadoc comment.
     *
     * @return true if there should be a space
     */
    public boolean isSpaceBeforeAt() {
        try {
            return bundle.getBoolean("space.before.javadoc");
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            return false;
        }
    }

    /**
     * Gets the TopOrder attribute of the PrintData object
     *
     * @return The TopOrder value
     */
    public org.acm.seguin.util.Comparator getTopOrder() {
        try {
            if (bundle.getBoolean("sort.top")) {
                return new org.acm.seguin.pretty.sort.TopLevelOrdering();
            }
        } catch (org.acm.seguin.util.MissingSettingsException mre) {
        }
        return new org.acm.seguin.pretty.sort.SameOrdering();
    }

    /**
     * Returns true if the catch statement is on a new line
     *
     * @return true if catch should start a line
     */
    public boolean isCatchOnNewLine() {
        try {
            return bundle.getBoolean("catch.start.line");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            return true;
        }
    }

    /**
     * Returns true if the else statement is on a new line
     *
     * @return true if else should start a line
     */
    public boolean isElseOnNewLine() {
        return elseOnNewLine;
    }

    private boolean elseOnNewLine = true;

    public void setElseOnNewLine(boolean value) {
        elseOnNewLine = value;
    }

    /**
     * Returns the indentation to use for parameters on new lines. If the <code>
     *  params.lineup</code> property is true, parameters on new lines are lined
     *  up with the method's open parenthesis.
     *
     * @return The ParamIndent value
     */
    public int getParamIndent() {
        if (inParams) {
            return lastParamIndent;
        }
        return 0;
    }

    /**
     * Gets the SurpriseReturn attribute of the PrintData object
     *
     * @return The SurpriseReturn value
     */
    public int getSurpriseReturn() {
        return surpriseType;
    }

    /**
     * Gets the ThrowsOnNewline attribute of the PrintData object
     *
     * @return The ThrowsOnNewline value
     */
    public boolean isThrowsOnNewline() {
        try {
            return bundle.getBoolean("throws.newline");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            return false;
        }
    }

    /**
     * Gets the ReformatComments attribute of the PrintData object
     *
     * @return The ReformatComments value
     */
    public boolean isReformatComments() {
        return reformatComments;
    }

    /**
     * Gets the FieldNameIndent attribute of the PrintData object
     *
     * @return The FieldNameIndent value
     */
    public int getFieldNameIndent() {
        return fieldNameIndent;
    }

    /**
     * Gets the FieldNameIndented attribute of the PrintData object
     *
     * @return The FieldNameIndented value
     */
    public boolean isFieldNameIndented() {
        return fieldNameIndent > 0;
    }

    /**
     * Gets the OriginalLine attribute of the PrintData object
     *
     * @return The OriginalLine value
     */
    public int getOriginalLine() {
        return originalLine;
    }

    /**
     * Gets the FinalLine attribute of the PrintData object
     *
     * @return The FinalLine value
     */
    public int getFinalLine() {
        return finalLine;
    }

    /**
     * Gets the DynamicFieldSpacing attribute of the PrintData object
     *
     * @param javadocPrinted
     * 		Description of Parameter
     * @return The DynamicFieldSpacing value
     */
    public boolean isDynamicFieldSpacing(boolean javadocPrinted) {
        if (skipNameSpacing) {
            return false;
        }
        return ((!javadocPrinted) && (fieldSpaceCode == org.acm.seguin.pretty.PrintData.DFS_NOT_WITH_JAVADOC)) || (fieldSpaceCode == org.acm.seguin.pretty.PrintData.DFS_ALWAYS);
    }

    /**
     * Return the code for field and local variable spacing
     *
     * @return the code
     */
    public int getFieldSpaceCode() {
        return fieldSpaceCode;
    }

    /**
     * Gets the DynamicFieldSpacing attribute of the PrintData object
     *
     * @return The DynamicFieldSpacing value
     */
    public int getDynamicFieldSpaces() {
        return dynamicFieldSpace;
    }

    /**
     * Gets the CStyleFormatCode attribute of the PrintData object
     *
     * @return The CStyleFormatCode value
     */
    public int getCStyleFormatCode() {
        return cStyleFormatCode;
    }

    /**
     * Gets the CStyleIndent attribute of the PrintData object
     *
     * @return The CStyleIndent value
     */
    public int getCStyleIndent() {
        return cStyleIndent;
    }

    /**
     * Gets the ForcingBlock attribute of the PrintData object
     *
     * @return The ForcingBlock value
     */
    public boolean isForcingBlock() {
        return forceBlock;
    }

    /**
     * Gets the EmptyBlockOnSingleLine attribute of the PrintData object
     *
     * @return The EmptyBlockOnSingleLine value
     */
    public boolean getEmptyBlockOnSingleLine() {
        return emptyBlockOnSingleLine;
    }

    /**
     * Gets the CastSpace attribute of the PrintData object
     *
     * @return The CastSpace value
     */
    public boolean isCastSpace() {
        return castSpace;
    }

    /**
     * Gets the NestedClassDocumented attribute of the PrintData object
     *
     * @return The NestedClassDocumented value
     */
    public boolean isNestedClassDocumented() {
        return documentNestedClasses;
    }

    /**
     * Sets the state for being inside a method declaration. Used for lining up
     *  parameters with the method's open parenthesis.
     */
    public void enterMethodDecl() {
        inParams = true;
        lastParamIndent = 0;
    }

    /**
     * Indicates that we've exited a method declaration.
     */
    public void exitMethodDecl() {
        inParams = false;
        lastParamIndent = 0;
    }

    /**
     * Indents a method parameter. If the <code>params.lineup</code> property is
     *  true, parameters on new lines are lined up with the method's open
     *  parenthesis.
     */
    public void indentParam() {
        if ((surpriseType == org.acm.seguin.pretty.PrintData.PARAM_INDENT) && (lastParamIndent > 0)) {
            indent();
            for (int ndx = 0; ndx < (lastParamIndent - indent); ndx++) {
                append(" ");
            }
        } else {
            incrIndent();
            indent();
            decrIndent();
        }
    }

    /**
     * Indicates that a method's open brace is about to be formatted.
     */
    public void methodBrace() {
        // This is reset to false in beginBlock after formatting
        isMethodBrace = true;
    }

    /**
     * Indicates that a class's open brace is about to be formatted.
     */
    public void classBrace() {
        // This is reset to false in beginBlock after formatting
        isClassBrace = true;
    }

    /**
     * Increment the indent by the default amount
     */
    public void incrIndent() {
        incrIndent(INDENT);
    }

    /**
     * Decrement the indent by the default amount
     */
    public void decrIndent() {
        incrIndent(-INDENT);
    }

    /**
     * Append a keyword to the output
     *
     * @param string
     * 		the input string
     */
    public void appendKeyword(java.lang.String string) {
        append(string);
    }

    /**
     * Append text to the output
     *
     * @param string
     * 		the input string
     */
    public void appendText(java.lang.String string) {
        append(string);
    }

    /**
     * Append constant to the output
     *
     * @param string
     * 		the input string
     */
    public void appendConstant(java.lang.String string) {
        outputBuffer.append(string);
    }

    /**
     * Append a comment to the output
     *
     * @param string
     * 		the input string
     * @param type
     * 		Description of Parameter
     */
    public void appendComment(java.lang.String string, int type) {
        if (type == org.acm.seguin.pretty.PrintData.CATEGORY_COMMENT) {
            lineQueue.appendCategoryComment(string, outputBuffer.toString());
            newlineCount++;
        } else if (type == org.acm.seguin.pretty.PrintData.SINGLE_LINE_COMMENT) {
            lineQueue.appendSingleLineComment(string, outputBuffer.toString());
            newlineCount--;
        } else {
            append(string);
        }
    }

    /**
     * Add a space
     */
    public void space() {
        append(" ");
    }

    /**
     * Backspace
     */
    public void backspace() {
        outputBuffer.setLength(outputBuffer.length() - 1);
    }

    /**
     * Add a newline
     */
    public void newline() {
        java.lang.String save = "";
        if (isBufferEmpty()) {
            outputBuffer.setLength(0);
            lineQueue.println("");
            newlineCount++;
        } else {
            save = outputBuffer.toString();
            lineQueue.println(outputBuffer.toString());
            outputBuffer.setLength(0);
            newlineCount = 1;
        }
    }

    /**
     * Consume a newline
     *
     * @return true when we were expecting this newline
     */
    public boolean consumeNewline() {
        lineQueue.flushFirstLine();
        if (!isBufferEmpty()) {
            newlineCount = 0;
        }
        if (newlineCount > 0) {
            newlineCount--;
            return true;
        } else {
            newline();
            lineQueue.flush();
            return false;
        }
    }

    /**
     * Start a block
     */
    public void beginBlock() {
        beginBlock(true);
    }

    /**
     * Start a block
     *
     * @param space
     * 		Description of Parameter
     */
    public void beginBlock(boolean space) {
        int currentStyle = getCurrentBlockStyle();
        if (currentStyle == org.acm.seguin.pretty.PrintData.BLOCK_STYLE_C) {
            if (space) {
                space();
            }
            append("{");
        } else {
            if (currentStyle == org.acm.seguin.pretty.PrintData.BLOCK_STYLE_EMACS) {
                incrIndent();
            }
            indent();
            append("{");
        }
        isMethodBrace = false;
        isClassBrace = false;
        newline();
        incrIndent();
    }

    /**
     * End a block
     *
     * @param newline
     * 		Description of Parameter
     */
    public void endBlock(boolean newline) {
        decrIndent();
        indent();
        if (getCurrentBlockStyle() == org.acm.seguin.pretty.PrintData.BLOCK_STYLE_EMACS) {
            decrIndent();
        }
        append("}");
        if (newline) {
            newline();
        }
        isMethodBrace = false;
        isClassBrace = false;
    }

    /**
     * End a block
     */
    public void endBlock() {
        endBlock(true);
    }

    /**
     * Start an expression
     *
     * @param notEmpty
     * 		Description of Parameter
     */
    public void beginExpression(boolean notEmpty) {
        if ((notEmpty == false) || (exprSpace == false)) {
            append("(");
        } else {
            append("( ");
        }
    }

    /**
     * End an expression
     *
     * @param notEmpty
     * 		Description of Parameter
     */
    public void endExpression(boolean notEmpty) {
        if ((notEmpty == false) || (exprSpace == false)) {
            append(")");
        } else {
            append(" )");
        }
    }

    /**
     * Start a Method
     */
    public void beginMethod() {
        if (last != org.acm.seguin.pretty.PrintData.EMPTY) {
            for (int ndx = 0; ndx < linesBetween; ndx++) {
                newline();
            }
        }
    }

    /**
     * End a Method
     */
    public void endMethod() {
        last = org.acm.seguin.pretty.PrintData.METHOD;
    }

    /**
     * Start a Field
     */
    public void beginField() {
        if ((last == org.acm.seguin.pretty.PrintData.EMPTY) || (last == org.acm.seguin.pretty.PrintData.FIELD)) {
        } else {
            for (int ndx = 0; ndx < linesBetween; ndx++) {
                newline();
            }
        }
    }

    /**
     * End a Field
     */
    public void endField() {
        last = org.acm.seguin.pretty.PrintData.FIELD;
    }

    /**
     * Start a Interface
     */
    public void beginInterface() {
        if (last != org.acm.seguin.pretty.PrintData.EMPTY) {
            for (int ndx = 0; ndx < linesBetween; ndx++) {
                newline();
            }
        }
        last = org.acm.seguin.pretty.PrintData.EMPTY;
    }

    /**
     * End a Interface
     */
    public void endInterface() {
        last = org.acm.seguin.pretty.PrintData.INTERFACE;
    }

    /**
     * Start a Class
     */
    public void beginClass() {
        if (last != org.acm.seguin.pretty.PrintData.EMPTY) {
            for (int ndx = 0; ndx < linesBetween; ndx++) {
                newline();
            }
        }
        last = org.acm.seguin.pretty.PrintData.EMPTY;
    }

    /**
     * End a Class
     */
    public void endClass() {
        last = org.acm.seguin.pretty.PrintData.CLASS;
    }

    /**
     * Indent the output
     */
    public void indent() {
        if (!isBufferEmpty()) {
            newline();
        }
        outputBuffer.setLength(0);
        append(getIndentString());
    }

    /**
     * Description of the Method
     */
    public void reset() {
        outputBuffer.setLength(0);
    }

    /**
     * Flushes the buffer
     */
    public void flush() {
        lineQueue.flush();
    }

    /**
     * Closes the output stream
     */
    public void close() {
        flush();
        lineQueue.getOutput().close();
    }

    /**
     * Description of the Method
     *
     * @param name
     * 		Description of Parameter
     */
    public void pushCurrentClassName(java.lang.String name) {
        classNameStack.addElement(name);
    }

    /**
     * Description of the Method
     */
    public void popCurrentClassName() {
        classNameStack.removeElementAt(classNameStack.size() - 1);
    }

    /**
     * Description of the Method
     */
    public void saveCurrentLine() {
        setFinalLine(lineQueue.getCurrentLine());
    }

    /**
     * Description of the Method
     *
     * @param size
     * 		Description of Parameter
     */
    public void pushFieldSize(org.acm.seguin.pretty.FieldSize size) {
        fieldStack.addElement(size);
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.pretty.FieldSize topFieldSize() {
        return ((org.acm.seguin.pretty.FieldSize) (fieldStack.elementAt(fieldStack.size() - 1)));
    }

    /**
     * Description of the Method
     */
    public void popFieldSize() {
        fieldStack.removeElementAt(fieldStack.size() - 1);
    }

    /**
     * Increment the indent
     *
     * @param incr
     * 		the amount to increment the indent
     */
    protected void incrIndent(int incr) {
        indent += incr;
        if (indent < 0) {
            indent = 0;
        }
    }

    /**
     * Creates a line queue object
     *
     * @param output
     * 		the output stream
     * @return the queue
     */
    protected org.acm.seguin.pretty.LineQueue lineQueueFactory(java.io.PrintWriter output) {
        return new org.acm.seguin.pretty.LineQueue(output);
    }

    /**
     * Sets the AllJavadocKept attribute of the PrintData object
     *
     * @param value
     * 		The new AllJavadocKept value
     */
    void setAllJavadocKept(boolean value) {
        keepAllJavadoc = value;
    }

    /**
     * Sets the StoreJavadocPrinted attribute of the PrintData object
     *
     * @param value
     * 		The new StoreJavadocPrinted value
     */
    void setStoreJavadocPrinted(boolean value) {
        storeJavadocPrinted = value;
    }

    /**
     * Sets the SkipNameSpacing attribute of the PrintData object
     *
     * @param value
     * 		The new SkipNameSpacing value
     */
    void setSkipNameSpacing(boolean value) {
        skipNameSpacing = value;
    }

    /**
     * Sets the TempEqualsLength attribute of the PrintData object
     *
     * @param value
     * 		The new TempEqualsLength value
     */
    void setTempEqualsLength(int value) {
        tempEqualsLength = value;
    }

    /**
     * Gets the StoreJavadocPrinted attribute of the PrintData object
     *
     * @return The StoreJavadocPrinted value
     */
    boolean isStoreJavadocPrinted() {
        return storeJavadocPrinted;
    }

    /**
     * Gets the SkipNameSpacing attribute of the PrintData object
     *
     * @return The SkipNameSpacing value
     */
    boolean getSkipNameSpacing() {
        return skipNameSpacing;
    }

    /**
     * Gets the TempEqualsLength attribute of the PrintData object
     *
     * @return The TempEqualsLength value
     */
    int getTempEqualsLength() {
        return tempEqualsLength;
    }

    /**
     * Determine what the current block style is
     *
     * @return true if we are using the C style now
     */
    private int getCurrentBlockStyle() {
        if (isClassBrace) {
            return classBlockStyle;
        }
        if (isMethodBrace) {
            return methodBlockStyle;
        }
        return codeBlockStyle;
    }

    /**
     * Append a string to the output
     *
     * @param string
     * 		the input string
     */
    private void append(java.lang.String string) {
        outputBuffer.append(string);
    }

    /**
     * Translates the key in the Settings into the block style
     */
    private int translateBlockStyle(java.lang.String key) {
        try {
            java.lang.String code = bundle.getString(key);
            if (code.equalsIgnoreCase("PASCAL"))
                return org.acm.seguin.pretty.PrintData.BLOCK_STYLE_PASCAL;

            if (code.equalsIgnoreCase("EMACS"))
                return org.acm.seguin.pretty.PrintData.BLOCK_STYLE_EMACS;

        } catch (org.acm.seguin.util.MissingSettingsException mre) {
            // Default is sufficient
        }
        return org.acm.seguin.pretty.PrintData.BLOCK_STYLE_C;
    }

    private boolean allowSingleLineJavadoc = false;

    public void setSingleLineJavadoc(boolean value) {
        allowSingleLineJavadoc = value;
    }

    public boolean isAllowSingleLineJavadoc() {
        return allowSingleLineJavadoc;
    }

    private boolean currentIsSingle = false;

    public void setCurrentIsSingle(boolean value) {
        currentIsSingle = value;
    }

    public boolean isCurrentSingle() {
        return currentIsSingle;
    }

    private boolean variablesAlignWithBlock = false;

    public void setVariablesAlignWithBlock(boolean value) {
        variablesAlignWithBlock = value;
    }

    public boolean isVariablesAlignWithBlock() {
        return variablesAlignWithBlock;
    }
}