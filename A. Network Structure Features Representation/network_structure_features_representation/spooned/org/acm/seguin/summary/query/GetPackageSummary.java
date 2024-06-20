package org.acm.seguin.summary.query;
/**
 * Finds the package summary associated with this type
 *
 * @author Chris Seguin
 */
public class GetPackageSummary {
    /**
     * Description of the Method
     *
     * @param type
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.summary.PackageSummary query(org.acm.seguin.summary.TypeSummary type) {
        org.acm.seguin.summary.Summary current = type;
        while (!(current instanceof org.acm.seguin.summary.PackageSummary)) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.PackageSummary) (current));
    }
}