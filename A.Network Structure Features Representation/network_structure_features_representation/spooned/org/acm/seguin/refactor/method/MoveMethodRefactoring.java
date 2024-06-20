/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Moves a method from one class to another. Generally used to move a method
 *  into a local variable or a parameter.
 *
 * @author Chris Seguin
 */
public class MoveMethodRefactoring extends org.acm.seguin.refactor.method.MethodRefactoring {
    private org.acm.seguin.summary.MethodSummary methodSummary;

    private org.acm.seguin.summary.TypeSummary typeSummary;

    private org.acm.seguin.summary.Summary destination;

    /**
     * Constructor for the MoveMethodRefactoring object
     */
    protected MoveMethodRefactoring() {
    }

    /**
     * Sets the Method attribute of the MoveMethodRefactoring object
     *
     * @param value
     * 		The new Method value
     */
    public void setMethod(org.acm.seguin.summary.MethodSummary value) {
        methodSummary = value;
        org.acm.seguin.summary.Summary current = methodSummary;
        while (!(current instanceof org.acm.seguin.summary.TypeSummary)) {
            current = current.getParent();
        } 
        typeSummary = ((org.acm.seguin.summary.TypeSummary) (current));
    }

    /**
     * Sets the Destination attribute of the MoveMethodRefactoring object
     *
     * @param value
     * 		The new Destination value
     */
    public void setDestination(org.acm.seguin.summary.Summary value) {
        destination = value;
    }

    /**
     * Gets the description of the refactoring
     *
     * @return the description
     */
    public java.lang.String getDescription() {
        return (((("Moving " + methodSummary.toString()) + " from ") + typeSummary.toString()) + " to ") + destination.toString();
    }

    /**
     * Gets the ID attribute of the MoveMethodRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.MOVE_METHOD;
    }

    /**
     * Describes the preconditions that must be true for this refactoring to be
     *  applied
     *
     * @exception RefactoringException
     * 		thrown if one or more of the
     * 		preconditions is not satisfied. The text of the exception provides a
     * 		hint of what went wrong.
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        java.util.Iterator iter = methodSummary.getDependencies();
        while ((iter != null) && iter.hasNext()) {
            org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
            // Check to see if we have any private fields without the appropriate getters/setters
            if (next instanceof org.acm.seguin.summary.FieldAccessSummary) {
                org.acm.seguin.summary.FieldAccessSummary fas = ((org.acm.seguin.summary.FieldAccessSummary) (next));
                checkFieldAccess(fas);
            } else if (next instanceof org.acm.seguin.summary.MessageSendSummary) {
                org.acm.seguin.summary.MessageSendSummary mss = ((org.acm.seguin.summary.MessageSendSummary) (next));
                checkMessageSend(mss);
            }
        } 
        if (destination instanceof org.acm.seguin.summary.VariableSummary) {
            org.acm.seguin.summary.VariableSummary varSummary = ((org.acm.seguin.summary.VariableSummary) (destination));
            org.acm.seguin.summary.TypeDeclSummary typeDecl = varSummary.getTypeDecl();
            org.acm.seguin.summary.TypeSummary destType = org.acm.seguin.summary.query.GetTypeSummary.query(typeDecl);
            if (destType == null) {
                throw new org.acm.seguin.refactor.RefactoringException(("The parameter " + varSummary.getName()) + " is a primitive");
            }
            org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (destType.getParent()));
            if (fileSummary.getFile() == null) {
                throw new org.acm.seguin.refactor.RefactoringException(("The source code for " + destType.getName()) + " is not modifiable");
            }
        }
    }

    /**
     * Performs the transform on the rest of the classes
     */
    protected void transform() {
        org.acm.seguin.refactor.ComplexTransform transform = getComplexTransform();
        // Update the method declaration to have the proper permissions
        org.acm.seguin.parser.ast.SimpleNode methodDecl = removeMethod(typeSummary, transform);
        if (methodDecl == null) {
            return;
        }
        update(methodDecl);
        org.acm.seguin.summary.TypeSummary destType;
        if (destination instanceof org.acm.seguin.summary.VariableSummary) {
            org.acm.seguin.summary.VariableSummary varSummary = ((org.acm.seguin.summary.VariableSummary) (destination));
            org.acm.seguin.summary.TypeDeclSummary typeDecl = varSummary.getTypeDecl();
            destType = org.acm.seguin.summary.query.GetTypeSummary.query(typeDecl);
        } else if (destination instanceof org.acm.seguin.summary.TypeSummary) {
            destType = ((org.acm.seguin.summary.TypeSummary) (destination));
        } else {
            return;
        }
        addMethodToDest(transform, methodDecl, destType);
    }

    /**
     * Removes the method from the source
     *
     * @param source
     * 		the source type
     * @param transform
     * 		the transform
     * @return Description of the Returned Value
     */
    protected org.acm.seguin.parser.ast.SimpleNode removeMethod(org.acm.seguin.summary.TypeSummary source, org.acm.seguin.refactor.ComplexTransform transform) {
        org.acm.seguin.refactor.method.RemoveMethodTransform rft = new org.acm.seguin.refactor.method.RemoveMethodTransform(methodSummary);
        transform.add(rft);
        org.acm.seguin.refactor.method.InvokeMovedMethodTransform immt = new org.acm.seguin.refactor.method.InvokeMovedMethodTransform(methodSummary, destination);
        transform.add(immt);
        org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (source.getParent()));
        transform.apply(fileSummary.getFile(), fileSummary.getFile());
        return rft.getMethodDeclaration();
    }

    /**
     * Adds the method to the destination class
     *
     * @param transform
     * 		The feature to be added to the MethodToDest attribute
     * @param methodDecl
     * 		The feature to be added to the MethodToDest attribute
     * @param dest
     * 		The feature to be added to the MethodToDest attribute
     */
    protected void addMethodToDest(org.acm.seguin.refactor.ComplexTransform transform, org.acm.seguin.parser.ast.SimpleNode methodDecl, org.acm.seguin.summary.TypeSummary dest) {
        transform.clear();
        org.acm.seguin.refactor.method.AddMethodTransform aft = new org.acm.seguin.refactor.method.AddMethodTransform(methodDecl);
        transform.add(aft);
        org.acm.seguin.refactor.method.AddMethodTypeVisitor visitor = new org.acm.seguin.refactor.method.AddMethodTypeVisitor();
        methodSummary.accept(visitor, transform);
        // Add appropriate import statements - to be determined later
        org.acm.seguin.summary.FileSummary parentFileSummary = ((org.acm.seguin.summary.FileSummary) (dest.getParent()));
        transform.apply(parentFileSummary.getFile(), parentFileSummary.getFile());
    }

    /**
     * Gets the name of the getter for the field
     *
     * @param summary
     * 		the field summary
     * @return the getter
     */
    private java.lang.String getFieldGetter(org.acm.seguin.summary.FieldSummary summary) {
        java.lang.String typeName = summary.getType();
        java.lang.String prefix = "get";
        if (typeName.equalsIgnoreCase("boolean")) {
            prefix = "is";
        }
        java.lang.String name = summary.getName();
        return (prefix + name.substring(0, 1).toUpperCase()) + name.substring(1);
    }

    /**
     * Gets the name of the setter for the field
     *
     * @param summary
     * 		the field summary
     * @return the setter
     */
    private java.lang.String getFieldSetter(org.acm.seguin.summary.FieldSummary summary) {
        java.lang.String prefix = "set";
        java.lang.String name = summary.getName();
        return (prefix + name.substring(0, 1).toUpperCase()) + name.substring(1);
    }

    /**
     * Checks if we can properly transform the field access
     *
     * @param fas
     * 		Description of Parameter
     * @exception RefactoringException
     * 		Description of Exception
     */
    private void checkFieldAccess(org.acm.seguin.summary.FieldAccessSummary fas) throws org.acm.seguin.refactor.RefactoringException {
        if ((fas.getPackageName() == null) && ((fas.getObjectName() == null) || fas.getObjectName().equals("this"))) {
            // Now we have to find the field
            org.acm.seguin.summary.FieldSummary field = org.acm.seguin.summary.query.FieldQuery.find(typeSummary, fas.getFieldName());
            if (field != null) {
                if (field.getModifiers().isPrivate()) {
                    checkForMethod(fas, field);
                }
            }
        }
    }

    /**
     * For a private field, check that we have the correct setters or getters
     *  (as appropriate)
     *
     * @param fas
     * 		Description of Parameter
     * @param field
     * 		Description of Parameter
     * @exception RefactoringException
     * 		Description of Exception
     */
    private void checkForMethod(org.acm.seguin.summary.FieldAccessSummary fas, org.acm.seguin.summary.FieldSummary field) throws org.acm.seguin.refactor.RefactoringException {
        java.lang.String methodName;
        if (fas.isAssignment()) {
            methodName = getFieldSetter(field);
        } else {
            methodName = getFieldGetter(field);
        }
        org.acm.seguin.summary.MethodSummary method = org.acm.seguin.summary.query.MethodQuery.find(typeSummary, methodName);
        if (method == null) {
            throw new org.acm.seguin.refactor.RefactoringException((("Unable to find the appropriate method (" + methodName) + ") for private field access in ") + typeSummary.getName());
        }
    }

    /**
     * Updates the node fore move method
     *
     * @param node
     * 		Description of Parameter
     */
    private void update(org.acm.seguin.parser.ast.SimpleNode node) {
        org.acm.seguin.refactor.method.MoveMethodVisitor mmv = new org.acm.seguin.refactor.method.MoveMethodVisitor(typeSummary, methodSummary, destination);
        node.jjtAccept(mmv, null);
    }

    /**
     * Description of the Method
     *
     * @param mss
     * 		Description of Parameter
     * @exception RefactoringException
     * 		Description of Exception
     */
    private void checkMessageSend(org.acm.seguin.summary.MessageSendSummary mss) throws org.acm.seguin.refactor.RefactoringException {
        if ((mss.getPackageName() == null) && ((mss.getObjectName() == null) || mss.getObjectName().equals("this"))) {
            org.acm.seguin.summary.MethodSummary method = org.acm.seguin.summary.query.MethodQuery.find(typeSummary, mss.getMessageName());
            if (method == null) {
                throw new org.acm.seguin.refactor.RefactoringException((("Unable to find the method (" + mss.getMessageName()) + ") in ") + typeSummary.getName());
            }
            if (method.getModifiers().isPrivate()) {
                throw new org.acm.seguin.refactor.RefactoringException(((("Moving a method (" + mss.getMessageName()) + ") from ") + typeSummary.getName()) + " that requires private access is illegal");
            }
            if (method.getModifiers().isPackage()) {
                org.acm.seguin.summary.TypeSummary destType;
                if (destination instanceof org.acm.seguin.summary.VariableSummary) {
                    org.acm.seguin.summary.VariableSummary varSummary = ((org.acm.seguin.summary.VariableSummary) (destination));
                    org.acm.seguin.summary.TypeDeclSummary typeDecl = varSummary.getTypeDecl();
                    destType = org.acm.seguin.summary.query.GetTypeSummary.query(typeDecl);
                } else if (destination instanceof org.acm.seguin.summary.TypeSummary) {
                    destType = ((org.acm.seguin.summary.TypeSummary) (destination));
                } else {
                    throw new org.acm.seguin.refactor.RefactoringException("Cannot find the type associated with " + destination.getName());
                }
                if (!org.acm.seguin.summary.query.SamePackage.query(typeSummary, destType)) {
                    throw new org.acm.seguin.refactor.RefactoringException(((("Moving a method (" + mss.getMessageName()) + ") from ") + typeSummary.getName()) + " to a different package that requires package access is illegal.");
                }
            }
        }
    }
}