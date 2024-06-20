package org.acm.seguin.refactor.type;
/**
 * Object responsible for renaming a type
 *
 * @author Chris Seguin
 * @created September 18, 1999
 */
public class RenameTypeData {
    // Instance Variables
    private org.acm.seguin.parser.ast.ASTName oldName;

    private org.acm.seguin.parser.ast.ASTName newName;

    private org.acm.seguin.summary.TypeSummary summary;

    /**
     * Constructor for the RenameTypeData object
     *
     * @param oldValue
     * 		The old name
     * @param newValue
     * 		The new name
     * @param init
     * 		Description of Parameter
     */
    public RenameTypeData(org.acm.seguin.parser.ast.ASTName oldValue, org.acm.seguin.parser.ast.ASTName newValue, org.acm.seguin.summary.TypeSummary init) {
        oldName = oldValue;
        newName = newValue;
        summary = init;
    }

    /**
     * Constructor for the RenameTypeData object
     *
     * @param oldValue
     * 		The old name
     * @param newValue
     * 		The new name
     * @param init
     * 		Description of Parameter
     */
    public RenameTypeData(java.lang.String oldValue, java.lang.String newValue, org.acm.seguin.summary.TypeSummary init) {
        oldName = new org.acm.seguin.parser.ast.ASTName(0);
        oldName.addNamePart(oldValue);
        newName = new org.acm.seguin.parser.ast.ASTName(0);
        newName.addNamePart(newValue);
        summary = init;
    }

    /**
     * Return the oldname
     *
     * @return the old name
     */
    public org.acm.seguin.parser.ast.ASTName getOld() {
        return oldName;
    }

    /**
     * Return the new name
     *
     * @return the new name
     */
    public org.acm.seguin.parser.ast.ASTName getNew() {
        return newName;
    }

    /**
     * Get the type summary associated with the type
     *
     * @return the type summary
     */
    public org.acm.seguin.summary.TypeSummary getTypeSummary() {
        return summary;
    }
}