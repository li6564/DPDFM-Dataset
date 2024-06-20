package org.acm.seguin.refactor.method;
/**
 * This class contains a static method to determine if the method in question
 *  makes any references to the local object
 *
 * @author Chris Seguin
 */
class ObjectReference {
    /**
     * Determines if this object is referenced
     *
     * @param methodSummary
     * 		the method summary
     * @return true if the object is referenced
     */
    public static boolean isReferenced(org.acm.seguin.summary.MethodSummary methodSummary) {
        java.util.LinkedList locals = new java.util.LinkedList();
        java.util.Iterator iter = methodSummary.getDependencies();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                if (next instanceof org.acm.seguin.summary.LocalVariableSummary) {
                    locals.add(next.getName());
                } else if (next instanceof org.acm.seguin.summary.FieldAccessSummary) {
                    org.acm.seguin.summary.FieldAccessSummary fas = ((org.acm.seguin.summary.FieldAccessSummary) (next));
                    if (((fas.getPackageName() == null) && (fas.getObjectName() == null)) && (!locals.contains(fas.getFieldName()))) {
                        return true;
                    }
                } else if (next instanceof org.acm.seguin.summary.MessageSendSummary) {
                    org.acm.seguin.summary.MessageSendSummary mss = ((org.acm.seguin.summary.MessageSendSummary) (next));
                    if ((mss.getPackageName() == null) && ((mss.getObjectName() == null) || mss.getObjectName().equals("this"))) {
                        return true;
                    }
                }
            } 
        }
        return false;
    }
}