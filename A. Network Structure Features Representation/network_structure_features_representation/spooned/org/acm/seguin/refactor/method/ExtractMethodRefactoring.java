/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Refactoring class that extracts a portion of the method and creates a new
 *  method with what the user has selected.
 *
 * @author Chris Seguin
 */
public class ExtractMethodRefactoring extends org.acm.seguin.refactor.Refactoring {
    private java.lang.StringBuffer fullFile = null;

    private java.lang.String selection = null;

    private java.lang.String methodName = null;

    private org.acm.seguin.parser.ast.SimpleNode root;

    private org.acm.seguin.summary.FileSummary mainFileSummary;

    private org.acm.seguin.summary.FileSummary extractedMethodFileSummary;

    private org.acm.seguin.parser.Node key;

    private org.acm.seguin.refactor.method.EMParameterFinder empf = null;

    private java.lang.StringBuffer signature;

    /**
     * Stores the return type for the extracted method
     */
    private java.lang.Object returnType = null;

    private int prot = org.acm.seguin.refactor.method.ExtractMethodRefactoring.PRIVATE;

    private java.lang.Object[] arguments = new java.lang.Object[0];

    /**
     * The extracted method should be private
     */
    public static final int PRIVATE = 0;

    /**
     * The extracted method should have package scope
     */
    public static final int PACKAGE = 1;

    /**
     * The extracted method should have protected scope
     */
    public static final int PROTECTED = 2;

    /**
     * The extracted method should have public scope
     */
    public static final int PUBLIC = 3;

    /**
     * Constructor for the ExtractMethodRefactoring object
     */
    protected ExtractMethodRefactoring() {
        super();
        signature = new java.lang.StringBuffer();
    }

    /**
     * Sets the FullFile attribute of the ExtractMethodRefactoring object
     *
     * @param value
     * 		The new FullFile value
     */
    public void setFullFile(java.lang.String value) {
        fullFile = new java.lang.StringBuffer(value);
    }

    /**
     * Sets the FullFile attribute of the ExtractMethodRefactoring object
     *
     * @param value
     * 		The new FullFile value
     */
    public void setFullFile(java.lang.StringBuffer value) {
        fullFile = value;
    }

    /**
     * Sets the Selection attribute of the ExtractMethodRefactoring object
     *
     * @param value
     * 		The new Selection value
     */
    public void setSelection(java.lang.String value) throws org.acm.seguin.refactor.RefactoringException {
        if (value == null) {
            throw new org.acm.seguin.refactor.RefactoringException("Nothing has been selected, so nothing can be extracted");
        }
        selection = value.trim();
        if (isStatement()) {
            setReturnType(null);
        } else {
            setReturnType("boolean");
        }
    }

    /**
     * Sets the MethodName attribute of the ExtractMethodRefactoring object
     *
     * @param value
     * 		The new MethodName value
     */
    public void setMethodName(java.lang.String value) {
        methodName = value;
        if ((methodName == null) || (methodName.length() == 0)) {
            methodName = "extractedMethod";
        }
    }

    /**
     * Sets the order of the parameters
     *
     * @param data
     * 		The new ParameterOrder value
     */
    public void setParameterOrder(java.lang.Object[] data) {
        empf.setParameterOrder(data);
        arguments = data;
    }

    /**
     * Sets the Protection attribute of the ExtractMethodRefactoring object
     *
     * @param value
     * 		The new Protection value
     */
    public void setProtection(int value) {
        prot = value;
    }

    /**
     * Sets the return type for the extracted method
     *
     * @param obj
     * 		The new ReturnType value
     */
    public void setReturnType(java.lang.Object obj) {
        returnType = obj;
    }

    /**
     * Gets the Description attribute of the ExtractMethodRefactoring object
     *
     * @return The Description value
     */
    public java.lang.String getDescription() {
        return "Extract a method named " + methodName;
    }

    /**
     * Gets the FullFile attribute of the ExtractMethodRefactoring object
     *
     * @return The FullFile value
     */
    public java.lang.String getFullFile() {
        return fullFile.toString();
    }

    /**
     * Gets the Parameters attribute of the ExtractMethodRefactoring object
     *
     * @return The Parameters value
     * @exception RefactoringException
     * 		Description of Exception
     */
    public org.acm.seguin.summary.VariableSummary[] getParameters() throws org.acm.seguin.refactor.RefactoringException {
        preconditions();
        org.acm.seguin.parser.query.Search srch = new org.acm.seguin.parser.query.Search();
        empf = prescan(srch);
        java.util.LinkedList list = empf.getList();
        org.acm.seguin.summary.VariableSummary[] result = new org.acm.seguin.summary.VariableSummary[list.size()];
        java.util.Iterator iter = list.iterator();
        int count = 0;
        while (iter.hasNext()) {
            result[count] = ((org.acm.seguin.summary.VariableSummary) (iter.next()));
            count++;
        } 
        arguments = result;
        return result;
    }

    /**
     * Gets the possible return types
     *
     * @return The return types
     * @exception RefactoringException
     * 		problem in loading these
     */
    public java.lang.Object[] getReturnTypes() throws org.acm.seguin.refactor.RefactoringException {
        if (empf == null) {
            return null;
        }
        return empf.getReturnTypes();
    }

    /**
     * Gets the Statement attribute of the ExtractMethodRefactoring object
     *
     * @return The Statement value
     */
    public boolean isStatement() {
        return (selection.indexOf(";") > 0) || (selection.indexOf("}") > 0);
    }

    /**
     * Gets the Signature attribute of the ExtractMethodRefactoring object
     *
     * @return The Signature value
     */
    public java.lang.String getSignature() {
        signature.setLength(0);
        signature.append(getProtection());
        signature.append(" ");
        signature.append(getReturnTypeString());
        signature.append(" ");
        signature.append(methodName);
        signature.append("(");
        for (int ndx = 0; ndx < arguments.length; ndx++) {
            signature.append(((org.acm.seguin.summary.VariableSummary) (arguments[ndx])).getDeclaration());
            if (ndx != (arguments.length - 1)) {
                signature.append(", ");
            }
        }
        signature.append(")");
        return signature.toString();
    }

    /**
     * Gets the return type for the extracted method
     *
     * @return The return type
     */
    public java.lang.Object getReturnType() {
        return returnType;
    }

    /**
     * Gets the ID attribute of the ExtractMethodRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.EXTRACT_METHOD;
    }

    /**
     * These items must be true before the refactoring will work
     *
     * @exception RefactoringException
     * 		the problem that arose
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        if (fullFile == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No file specified");
        }
        if (selection == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No selection specified");
        }
        if (methodName == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No method specified");
        }
        root = getFileRoot();
        if (root == null) {
            throw new org.acm.seguin.refactor.RefactoringException(("Unable to parse the current file.\n" + "Please make sure you can compile this file before\n") + "trying to extract a method from it.");
        }
        mainFileSummary = findVariablesUsed(root);
        org.acm.seguin.parser.ast.SimpleNode newMethod = getMethodTree();
        if (newMethod == null) {
            throw new org.acm.seguin.refactor.RefactoringException(("Unable to parse the current selection.\n" + "Please make sure you have highlighted the entire expression\n") + "or set of statements.");
        }
    }

    /**
     * Actually make the transformation
     */
    protected void transform() {
        replaceAllInstances(root);
        printFile(root);
    }

    /**
     * Gets the MethodTree attribute of the ExtractMethodRefactoring object
     *
     * @return The MethodTree value
     */
    private org.acm.seguin.parser.ast.SimpleNode getMethodTree() {
        java.lang.String tempClass = ("public class TempClass { " + makeMethod()) + "}";
        org.acm.seguin.parser.factory.BufferParserFactory bpf = new org.acm.seguin.parser.factory.BufferParserFactory(tempClass);
        org.acm.seguin.parser.ast.SimpleNode root = bpf.getAbstractSyntaxTree(false);
        extractedMethodFileSummary = findVariablesUsed(root);
        org.acm.seguin.parser.ast.ASTTypeDeclaration top = ((org.acm.seguin.parser.ast.ASTTypeDeclaration) (root.jjtGetChild(0)));
        org.acm.seguin.parser.ast.ASTClassDeclaration classDecl = ((org.acm.seguin.parser.ast.ASTClassDeclaration) (top.jjtGetChild(0)));
        org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration unmodifiedClassDecl = ((org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration) (classDecl.jjtGetChild(0)));
        org.acm.seguin.parser.ast.ASTClassBody classBody = ((org.acm.seguin.parser.ast.ASTClassBody) (unmodifiedClassDecl.jjtGetChild(0)));
        org.acm.seguin.parser.ast.ASTClassBodyDeclaration bodyDecl = ((org.acm.seguin.parser.ast.ASTClassBodyDeclaration) (classBody.jjtGetChild(0)));
        return ((org.acm.seguin.parser.ast.SimpleNode) (bodyDecl.jjtGetChild(0)));
    }

    /**
     * Gets the FileRoot attribute of the ExtractMethodRefactoring object
     *
     * @return The FileRoot value
     */
    private org.acm.seguin.parser.ast.SimpleNode getFileRoot() {
        org.acm.seguin.parser.factory.BufferParserFactory bpf = new org.acm.seguin.parser.factory.BufferParserFactory(fullFile.toString());
        org.acm.seguin.parser.ast.SimpleNode root = bpf.getAbstractSyntaxTree(true);
        return root;
    }

    /**
     * Returns the protection
     *
     * @return The Protection value
     */
    private java.lang.String getProtection() {
        switch (prot) {
            case org.acm.seguin.refactor.method.ExtractMethodRefactoring.PRIVATE :
                return "private";
            case org.acm.seguin.refactor.method.ExtractMethodRefactoring.PACKAGE :
                return "";
            case org.acm.seguin.refactor.method.ExtractMethodRefactoring.PROTECTED :
                return "protected";
            case org.acm.seguin.refactor.method.ExtractMethodRefactoring.PUBLIC :
                return "public";
        }
        return "private";
    }

    /**
     * Returns the return type
     *
     * @return The ReturnType value
     */
    private java.lang.String getReturnTypeString() {
        if (returnType == null) {
            return "void";
        } else if (returnType instanceof java.lang.String) {
            return ((java.lang.String) (returnType));
        } else if (returnType instanceof org.acm.seguin.summary.VariableSummary) {
            return ((org.acm.seguin.summary.VariableSummary) (returnType)).getTypeDecl().getName();
        } else {
            return returnType.toString();
        }
    }

    /**
     * Replace all instances of code with a selected value
     *
     * @param root
     * 		Description of Parameter
     */
    private void replaceAllInstances(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.refactor.method.EMBuilder builder = new org.acm.seguin.refactor.method.EMBuilder();
        builder.setMethodName(methodName);
        builder.setStatement(isStatement());
        org.acm.seguin.parser.query.Search srch = new org.acm.seguin.parser.query.Search();
        if (empf == null) {
            empf = prescan(srch);
        }
        builder.setParameters(empf.getList());
        org.acm.seguin.parser.ast.SimpleNode methodTree = addReturn(getMethodTree());
        org.acm.seguin.parser.query.Found result = srch.search(root, key);
        updateModifiers(((org.acm.seguin.parser.ast.SimpleNode) (result.getRoot())), methodTree);
        if (returnType instanceof org.acm.seguin.summary.VariableSummary) {
            builder.setReturnSummary(((org.acm.seguin.summary.VariableSummary) (returnType)));
            org.acm.seguin.refactor.method.FindLocalVariableDeclVisitor flvdv = new org.acm.seguin.refactor.method.FindLocalVariableDeclVisitor();
            methodTree.jjtAccept(flvdv, returnType);
            builder.setLocalVariableNeeded(flvdv.isFound());
        }
        org.acm.seguin.parser.ast.SimpleNode firstResult = ((org.acm.seguin.parser.ast.SimpleNode) (result.getRoot()));
        while (result != null) {
            replaceExtractedMethod(result, builder);
            result = srch.search(root, key);
        } 
        insertAtNextClass(firstResult, methodTree);
    }

    /**
     * Prints the file using the pretty printer option
     *
     * @param root
     * 		Description of Parameter
     */
    private void printFile(org.acm.seguin.parser.ast.SimpleNode root) {
        java.io.ByteArrayOutputStream baos;
        baos = new java.io.ByteArrayOutputStream();
        org.acm.seguin.pretty.PrintData pd = new org.acm.seguin.pretty.PrintData(baos);
        org.acm.seguin.pretty.PrettyPrintVisitor ppv = new org.acm.seguin.pretty.PrettyPrintVisitor();
        ppv.visit(((org.acm.seguin.parser.ast.ASTCompilationUnit) (root)), pd);
        pd.close();
        byte[] buffer = baos.toByteArray();
        java.lang.String file = new java.lang.String(buffer);
        if (file.length() > 0) {
            fullFile = new java.lang.StringBuffer(file);
        }
    }

    /**
     * Creates a string with the new method in it
     *
     * @return the new method
     */
    private java.lang.String makeMethod() {
        if (isStatement()) {
            return ((getSignature() + "{") + selection) + "}";
        } else {
            return ((getSignature() + "{ return ") + selection) + "; }";
        }
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private org.acm.seguin.summary.FileSummary findVariablesUsed(org.acm.seguin.parser.Node node) {
        if (node == null) {
            return null;
        }
        org.acm.seguin.summary.SummaryLoaderState state = new org.acm.seguin.summary.SummaryLoaderState();
        node.jjtAccept(new org.acm.seguin.summary.SummaryLoadVisitor(), state);
        return ((org.acm.seguin.summary.FileSummary) (state.getCurrentSummary()));
    }

    /**
     * Finds the parameters
     *
     * @param result
     * 		the location where the section was found
     * @return Description of the Returned Value
     */
    private org.acm.seguin.refactor.method.EMParameterFinder findParameters(org.acm.seguin.parser.query.Found result) {
        org.acm.seguin.refactor.method.EMParameterFinder empf = new org.acm.seguin.refactor.method.EMParameterFinder();
        empf.setMainFileSummary(mainFileSummary);
        empf.setExtractFileSummary(extractedMethodFileSummary);
        empf.setLocation(result.getRoot());
        empf.run();
        return empf;
    }

    /**
     * This allows us to scan for the parameters first
     *
     * @param srch
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private org.acm.seguin.refactor.method.EMParameterFinder prescan(org.acm.seguin.parser.query.Search srch) {
        org.acm.seguin.refactor.method.EMDigger digger = new org.acm.seguin.refactor.method.EMDigger();
        if (isStatement()) {
            key = digger.last(((org.acm.seguin.parser.ast.ASTMethodDeclaration) (getMethodTree())));
        } else {
            key = digger.dig(((org.acm.seguin.parser.ast.ASTMethodDeclaration) (getMethodTree())));
        }
        org.acm.seguin.parser.query.Found result = srch.search(root, key);
        org.acm.seguin.refactor.method.EMParameterFinder parameterFinder = findParameters(result);
        java.util.LinkedList list = parameterFinder.getList();
        arguments = new java.lang.Object[list.size()];
        java.util.Iterator iter = list.iterator();
        int count = 0;
        while (iter.hasNext()) {
            arguments[count] = iter.next();
            count++;
        } 
        return parameterFinder;
    }

    /**
     * Replaces the extracted method
     *
     * @param result
     * 		where we found the portion to replace
     * @param builder
     * 		build a method invocation
     */
    private void replaceExtractedMethod(org.acm.seguin.parser.query.Found result, org.acm.seguin.refactor.method.EMBuilder builder) {
        int index = result.getIndex();
        int length = key.jjtGetNumChildren();
        org.acm.seguin.parser.Node location = result.getRoot();
        for (int ndx = 0; ndx < length; ndx++) {
            location.jjtDeleteChild(index);
        }
        location.jjtInsertChild(builder.build(), index);
    }

    /**
     * Adds the return at the end of the method if one is necessary
     *
     * @param methodDecl
     * 		The feature to be added to the Return attribute
     * @return Description of the Returned Value
     */
    private org.acm.seguin.parser.ast.SimpleNode addReturn(org.acm.seguin.parser.ast.SimpleNode methodDecl) {
        if (returnType instanceof org.acm.seguin.summary.VariableSummary) {
            org.acm.seguin.parser.Node block = methodDecl.jjtGetChild(methodDecl.jjtGetNumChildren() - 1);
            org.acm.seguin.parser.ast.ASTBlockStatement blockStatement = new org.acm.seguin.parser.ast.ASTBlockStatement(0);
            org.acm.seguin.parser.ast.ASTStatement statement = new org.acm.seguin.parser.ast.ASTStatement(0);
            blockStatement.jjtAddChild(statement, 0);
            org.acm.seguin.parser.ast.ASTReturnStatement returnStatement = new org.acm.seguin.parser.ast.ASTReturnStatement(0);
            statement.jjtAddChild(returnStatement, 0);
            org.acm.seguin.parser.build.BuildExpression be = new org.acm.seguin.parser.build.BuildExpression();
            java.lang.String name = ((org.acm.seguin.summary.VariableSummary) (returnType)).getName();
            returnStatement.jjtAddChild(be.buildName(name), 0);
            block.jjtAddChild(blockStatement, block.jjtGetNumChildren());
        }
        return methodDecl;
    }

    /**
     * Adds the static and synchronized attributes to the extracted method
     *
     * @param currentNode
     * 		where the body of the extracted method was found
     * @param methodTree
     * 		the method we are extracting
     */
    private void updateModifiers(org.acm.seguin.parser.ast.SimpleNode currentNode, org.acm.seguin.parser.ast.SimpleNode methodTree) {
        while (!(currentNode instanceof org.acm.seguin.parser.ast.ASTMethodDeclaration)) {
            currentNode = ((org.acm.seguin.parser.ast.SimpleNode) (currentNode.jjtGetParent()));
            if (currentNode instanceof org.acm.seguin.parser.ast.ASTClassBody) {
                return;
            }
        } 
        org.acm.seguin.parser.ast.ASTMethodDeclaration extractedFrom = ((org.acm.seguin.parser.ast.ASTMethodDeclaration) (currentNode));
        org.acm.seguin.parser.ast.ASTMethodDeclaration newMethod = ((org.acm.seguin.parser.ast.ASTMethodDeclaration) (methodTree));
        org.acm.seguin.pretty.ModifierHolder efmh = extractedFrom.getModifiers();
        org.acm.seguin.pretty.ModifierHolder nmmh = newMethod.getModifiers();
        nmmh.setStatic(efmh.isStatic());
        nmmh.setSynchronized(nmmh.isSynchronized());
    }

    /**
     * Inserts the method at the next class found when heading up from the first
     *  place where we replaced this value
     *
     * @param currentNode
     * 		where this was found
     * @param methodTree
     * 		the method to be inserted
     */
    private void insertAtNextClass(org.acm.seguin.parser.ast.SimpleNode currentNode, org.acm.seguin.parser.ast.SimpleNode methodTree) {
        while (!(currentNode instanceof org.acm.seguin.parser.ast.ASTClassBody)) {
            currentNode = ((org.acm.seguin.parser.ast.SimpleNode) (currentNode.jjtGetParent()));
            if (currentNode == null) {
                return;
            }
        } 
        currentNode.jjtInsertChild(methodTree, currentNode.jjtGetNumChildren());
    }
}