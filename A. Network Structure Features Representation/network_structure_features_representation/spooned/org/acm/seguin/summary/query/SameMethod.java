package org.acm.seguin.summary.query;
/**
 * Checks that two methods are the same. Also provides a search feature to
 *  find a method with a specific signature in a type
 *
 * @author Chris Seguin
 */
public class SameMethod {
    private static final int SAME = 0;

    private static final int ONE_ANCESTOR = 1;

    private static final int TWO_ANCESTOR = 2;

    private static final int ERROR = 3;

    private static final int ANCESTOR = 4;

    /**
     * Checks if two methods are the same
     *
     * @param one
     * 		Description of Parameter
     * @param two
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static boolean query(org.acm.seguin.summary.MethodSummary one, org.acm.seguin.summary.MethodSummary two) {
        return org.acm.seguin.summary.query.SameMethod.check(one, two, org.acm.seguin.summary.query.SameMethod.SAME);
    }

    /**
     * Description of the Method
     *
     * @param one
     * 		Description of Parameter
     * @param two
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static boolean conflict(org.acm.seguin.summary.MethodSummary one, org.acm.seguin.summary.MethodSummary two) {
        return org.acm.seguin.summary.query.SameMethod.check(one, two, org.acm.seguin.summary.query.SameMethod.ANCESTOR);
    }

    /**
     * Finds the method with the same signature in the other type
     *
     * @param type
     * 		Description of Parameter
     * @param method
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.summary.MethodSummary find(org.acm.seguin.summary.TypeSummary type, org.acm.seguin.summary.MethodSummary method) {
        java.util.Iterator iter = type.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                if (org.acm.seguin.summary.query.SameMethod.query(next, method)) {
                    return next;
                }
            } 
        }
        return null;
    }

    /**
     * Finds the method with a conflicting in the other type
     *
     * @param type
     * 		Description of Parameter
     * @param method
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.summary.MethodSummary findConflict(org.acm.seguin.summary.TypeSummary type, org.acm.seguin.summary.MethodSummary method) {
        java.util.Iterator iter = type.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                if (org.acm.seguin.summary.query.SameMethod.conflict(next, method)) {
                    return next;
                }
            } 
        }
        return null;
    }

    /**
     * Checks the types
     *
     * @param one
     * 		Description of Parameter
     * @param two
     * 		Description of Parameter
     * @param way
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private static int compareTypes(org.acm.seguin.summary.TypeSummary one, org.acm.seguin.summary.TypeSummary two, int way) {
        if (one == two) {
            return way;
        }
        if (way == org.acm.seguin.summary.query.SameMethod.ANCESTOR) {
            if (org.acm.seguin.summary.query.Ancestor.query(one, two)) {
                return org.acm.seguin.summary.query.SameMethod.ONE_ANCESTOR;
            } else if (org.acm.seguin.summary.query.Ancestor.query(two, one)) {
                return org.acm.seguin.summary.query.SameMethod.TWO_ANCESTOR;
            }
        }
        if ((org.acm.seguin.summary.query.SameMethod.ONE_ANCESTOR == way) && org.acm.seguin.summary.query.Ancestor.query(two, one)) {
            return way;
        }
        if ((org.acm.seguin.summary.query.SameMethod.TWO_ANCESTOR == way) && org.acm.seguin.summary.query.Ancestor.query(one, two)) {
            return way;
        }
        return org.acm.seguin.summary.query.SameMethod.ERROR;
    }

    /**
     * Work horse that actually checks the methods
     *
     * @param one
     * 		Description of Parameter
     * @param two
     * 		Description of Parameter
     * @param test
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private static boolean check(org.acm.seguin.summary.MethodSummary one, org.acm.seguin.summary.MethodSummary two, int test) {
        if (!one.getName().equals(two.getName())) {
            return false;
        }
        java.util.Iterator oneIter = one.getParameters();
        java.util.Iterator twoIter = two.getParameters();
        if (oneIter == null) {
            return twoIter == null;
        }
        if (twoIter == null) {
            return false;
        }
        while (oneIter.hasNext() && twoIter.hasNext()) {
            org.acm.seguin.summary.ParameterSummary oneParam = ((org.acm.seguin.summary.ParameterSummary) (oneIter.next()));
            org.acm.seguin.summary.ParameterSummary twoParam = ((org.acm.seguin.summary.ParameterSummary) (twoIter.next()));
            org.acm.seguin.summary.TypeDeclSummary oneDecl = oneParam.getTypeDecl();
            org.acm.seguin.summary.TypeDeclSummary twoDecl = twoParam.getTypeDecl();
            org.acm.seguin.summary.TypeSummary typeOne = org.acm.seguin.summary.query.GetTypeSummary.query(oneDecl);
            org.acm.seguin.summary.TypeSummary typeTwo = org.acm.seguin.summary.query.GetTypeSummary.query(twoDecl);
            if (org.acm.seguin.summary.query.SameMethod.compareTypes(typeOne, typeTwo, test) == org.acm.seguin.summary.query.SameMethod.ERROR) {
                return false;
            }
        } 
        return !(oneIter.hasNext() || twoIter.hasNext());
    }
}