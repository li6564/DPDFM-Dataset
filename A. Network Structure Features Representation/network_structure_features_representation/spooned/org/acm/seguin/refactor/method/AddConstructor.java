/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * A series of transformations taht adds a new constructor to a class.
 *  The constructor invokes the super classes's constructor.  This
 *  object shares a lot in common with the AddNewMethod class, and
 *  combining these two objects can be considered for future refactorings.
 *
 * @author Chris Seguin
 */
public class AddConstructor extends org.acm.seguin.refactor.TransformAST {
    /**
     * The method we are updating
     */
    protected org.acm.seguin.summary.MethodSummary methodSummary;

    private java.lang.String typeName;

    /**
     * Constructor for the AddConstructor object
     *
     * @param init
     * 		the method summary to add
     */
    public AddConstructor(org.acm.seguin.summary.MethodSummary init, java.lang.String name) {
        methodSummary = init;
        typeName = name;
    }

    /**
     * Update the syntax tree
     *
     * @param root
     * 		the root of the syntax tree
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        // Find the type declaration
        int last = root.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (root.jjtGetChild(ndx) instanceof org.acm.seguin.parser.ast.ASTTypeDeclaration) {
                drillIntoType(((org.acm.seguin.parser.ast.SimpleNode) (root.jjtGetChild(ndx))));
                return;
            }
        }
    }

    /**
     * Sets up the modifiers
     *
     * @param source
     * 		the source holder
     * @param dest
     * 		the destination holder
     */
    protected void setupModifiers(org.acm.seguin.pretty.ModifierHolder source, org.acm.seguin.pretty.ModifierHolder dest) {
        dest.copy(source);
    }

    /**
     * Determines if the method is abstract
     *
     * @return true if the method is abstract
     */
    protected boolean isAbstract() {
        return methodSummary.getModifiers().isAbstract();
    }

    /**
     * Adds the return to the method declaration
     *
     * @param methodDecl
     * 		The feature to be added to the Return attribute
     * @param index
     * 		The feature to be added to the Return attribute
     */
    protected void addReturn(org.acm.seguin.parser.ast.SimpleNode methodDecl, int index) {
        org.acm.seguin.parser.ast.ASTResultType result = new org.acm.seguin.parser.ast.ASTResultType(0);
        org.acm.seguin.summary.TypeDeclSummary returnType = methodSummary.getReturnType();
        if ((returnType != null) && (!returnType.getType().equals("void"))) {
            org.acm.seguin.parser.ast.ASTType type = buildType(returnType);
            result.jjtAddChild(type, 0);
        }
        methodDecl.jjtAddChild(result, index);
    }

    /**
     * Creates the parameters
     *
     * @return Description of the Returned Value
     */
    protected org.acm.seguin.parser.ast.ASTFormalParameters createParameters() {
        org.acm.seguin.parser.ast.ASTFormalParameters params = new org.acm.seguin.parser.ast.ASTFormalParameters(0);
        java.util.Iterator iter = methodSummary.getParameters();
        if (iter != null) {
            int paramCount = 0;
            while (iter.hasNext()) {
                org.acm.seguin.summary.ParameterSummary paramSummary = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
                org.acm.seguin.parser.ast.ASTFormalParameter nextParam = new org.acm.seguin.parser.ast.ASTFormalParameter(0);
                org.acm.seguin.parser.ast.ASTType type = buildType(paramSummary.getTypeDecl());
                nextParam.jjtAddChild(type, 0);
                org.acm.seguin.parser.ast.ASTVariableDeclaratorId paramDeclID = new org.acm.seguin.parser.ast.ASTVariableDeclaratorId(0);
                paramDeclID.setName(paramSummary.getName());
                nextParam.jjtAddChild(paramDeclID, 1);
                params.jjtAddChild(nextParam, paramCount);
                paramCount++;
            } 
        }
        return params;
    }

    /**
     * Creates the exceptions
     *
     * @param iter
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected org.acm.seguin.parser.ast.ASTNameList createExceptions(java.util.Iterator iter) {
        org.acm.seguin.parser.ast.ASTNameList list = new org.acm.seguin.parser.ast.ASTNameList(0);
        int ndx = 0;
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeDeclSummary next = ((org.acm.seguin.summary.TypeDeclSummary) (iter.next()));
            list.jjtAddChild(buildName(next), ndx);
            ndx++;
        } 
        return list;
    }

    /**
     * Adds the exceptions to the node
     *
     * @param methodDecl
     * 		The feature to be added to the Exceptions attribute
     * @param index
     * 		The feature to be added to the Exceptions attribute
     * @return Description of the Returned Value
     */
    protected int addExceptions(org.acm.seguin.parser.ast.SimpleNode methodDecl, int index) {
        java.util.Iterator iter = methodSummary.getExceptions();
        if (iter != null) {
            org.acm.seguin.parser.ast.ASTNameList list = createExceptions(iter);
            methodDecl.jjtAddChild(list, index);
            index++;
        }
        return index;
    }

    /**
     * Adds the body of the method
     *
     * @param methodDecl
     * 		The feature to be added to the Body attribute
     * @param index
     * 		The feature to be added to the Body attribute
     */
    protected void addBody(org.acm.seguin.parser.ast.SimpleNode methodDecl, int index) {
        org.acm.seguin.parser.ast.ASTExplicitConstructorInvocation eci = new org.acm.seguin.parser.ast.ASTExplicitConstructorInvocation(0);
        eci.setName("super");
        org.acm.seguin.parser.ast.ASTArguments args = new org.acm.seguin.parser.ast.ASTArguments(0);
        eci.jjtAddChild(args, 0);
        org.acm.seguin.parser.ast.ASTArgumentList argList = new org.acm.seguin.parser.ast.ASTArgumentList(0);
        args.jjtAddChild(argList, 0);
        java.util.Iterator iter = methodSummary.getParameters();
        int item = 0;
        if (iter != null) {
            org.acm.seguin.parser.build.BuildExpression builder = new org.acm.seguin.parser.build.BuildExpression();
            while (iter.hasNext()) {
                org.acm.seguin.summary.ParameterSummary param = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
                argList.jjtAddChild(builder.buildName(param.getName()), item);
                item++;
            } 
        }
        methodDecl.jjtAddChild(eci, index);
    }

    /**
     * Drills down into the class definition to add the method
     *
     * @param node
     * 		the type syntax tree node that is being modified
     */
    private void drillIntoType(org.acm.seguin.parser.ast.SimpleNode node) {
        org.acm.seguin.parser.ast.ASTClassDeclaration classDecl = ((org.acm.seguin.parser.ast.ASTClassDeclaration) (node.jjtGetChild(0)));
        if (isAbstract()) {
            classDecl.addModifier("abstract");
        }
        org.acm.seguin.parser.ast.SimpleNode unmodified = ((org.acm.seguin.parser.ast.SimpleNode) (classDecl.jjtGetChild(0)));
        org.acm.seguin.parser.ast.SimpleNode classBody = ((org.acm.seguin.parser.ast.SimpleNode) (unmodified.jjtGetChild(unmodified.jjtGetNumChildren() - 1)));
        org.acm.seguin.parser.ast.ASTClassBodyDeclaration decl = new org.acm.seguin.parser.ast.ASTClassBodyDeclaration(0);
        decl.jjtAddChild(build(), 0);
        classBody.jjtAddChild(decl, classBody.jjtGetNumChildren());
    }

    /**
     * Builds the method to be adding
     *
     * @return a syntax tree branch containing the new method
     */
    private org.acm.seguin.parser.ast.ASTConstructorDeclaration build() {
        org.acm.seguin.parser.ast.ASTConstructorDeclaration methodDecl = new org.acm.seguin.parser.ast.ASTConstructorDeclaration(0);
        // Set the modifiers
        setupModifiers(methodSummary.getModifiers(), methodDecl.getModifiers());
        // Set the declaration
        methodDecl.setName(typeName);
        org.acm.seguin.parser.ast.ASTFormalParameters params = createParameters();
        methodDecl.jjtAddChild(params, 0);
        int index = 1;
        index = addExceptions(methodDecl, index);
        addBody(methodDecl, index);
        return methodDecl;
    }

    /**
     * Builds the type from the type declaration summary
     *
     * @param summary
     * 		the summary
     * @return the AST type node
     */
    private org.acm.seguin.parser.ast.ASTType buildType(org.acm.seguin.summary.TypeDeclSummary summary) {
        org.acm.seguin.parser.ast.ASTType type = new org.acm.seguin.parser.ast.ASTType(0);
        if (summary.isPrimitive()) {
            org.acm.seguin.parser.ast.ASTPrimitiveType addition = new org.acm.seguin.parser.ast.ASTPrimitiveType(0);
            addition.setName(summary.getLongName());
            type.jjtAddChild(addition, 0);
        } else {
            org.acm.seguin.parser.ast.ASTName name = buildName(summary);
            type.jjtAddChild(name, 0);
        }
        type.setArrayCount(summary.getArrayCount());
        return type;
    }

    /**
     * Builds the name of the type from the type decl summary
     *
     * @param summary
     * 		the summary
     * @return the name node
     */
    private org.acm.seguin.parser.ast.ASTName buildName(org.acm.seguin.summary.TypeDeclSummary summary) {
        org.acm.seguin.parser.ast.ASTName name = new org.acm.seguin.parser.ast.ASTName(0);
        name.fromString(summary.getLongName());
        return name;
    }
}