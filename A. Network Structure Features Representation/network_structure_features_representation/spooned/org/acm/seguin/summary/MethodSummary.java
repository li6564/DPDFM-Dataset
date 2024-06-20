/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * Stores information about a method
 *
 * @author Chris Seguin
 * @created June 8, 1999
 */
public class MethodSummary extends org.acm.seguin.summary.Summary {
    // Instance Variables
    private org.acm.seguin.summary.TypeDeclSummary returnType;

    private java.lang.String name;

    private java.util.LinkedList parameterList = null;

    private java.util.LinkedList exceptionList = null;

    private java.util.LinkedList dependencyList = null;

    private org.acm.seguin.pretty.ModifierHolder modifiers;

    private int statementCount = 0;

    private int blockDepth = 0;

    private int maxBlockDepth = 0;

    private int declarationLine = 0;

    /**
     * Construct a method from a method declaration node
     *
     * @param parent
     * 		Description of Parameter
     */
    public MethodSummary(org.acm.seguin.summary.Summary parent) {
        // Initialize the parent class
        super(parent);
        returnType = null;
    }

    /**
     * Set the name
     *
     * @param newName
     * 		the name of the method
     */
    public void setName(java.lang.String newName) {
        name = newName.intern();
    }

    /**
     * Set the return
     *
     * @param type
     * 		the return type. For a constructor, this value is null.
     */
    public void setReturnType(org.acm.seguin.summary.TypeDeclSummary type) {
        returnType = type;
    }

    /**
     * Sets the DeclarationLine attribute of the MethodSummary object
     *
     * @param value
     * 		The new DeclarationLine value
     */
    public void setDeclarationLine(int value) {
        declarationLine = value;
    }

    /**
     * Return the name
     *
     * @return the name of the method
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * Get the return
     *
     * @return the return type. For a constructor, this value is null.
     */
    public org.acm.seguin.summary.TypeDeclSummary getReturnType() {
        return returnType;
    }

    /**
     * Get a list of execeptions
     *
     * @return an iterator full of exceptions
     */
    public java.util.Iterator getExceptions() {
        if (exceptionList == null) {
            return null;
        }
        return exceptionList.iterator();
    }

    /**
     * Get a list of parameters
     *
     * @return an iterator over the parameters
     */
    public java.util.Iterator getParameters() {
        if (parameterList == null) {
            return null;
        }
        return parameterList.iterator();
    }

    /**
     * Return the number of parameters
     *
     * @return the count of parameters to this method
     */
    public int getParameterCount() {
        if (parameterList == null) {
            return 0;
        }
        return parameterList.size();
    }

    /**
     * Return a list of dependencies. This list will contain variable summaries
     *  and type decl summaries.
     *
     * @return a list of dependencies.
     */
    public java.util.Iterator getDependencies() {
        if (dependencyList == null) {
            return null;
        }
        return dependencyList.iterator();
    }

    /**
     * Returns the modifier holder
     *
     * @return the holder
     */
    public org.acm.seguin.pretty.ModifierHolder getModifiers() {
        return modifiers;
    }

    /**
     * Get the statement count
     *
     * @return the number of statements in the method
     */
    public int getStatementCount() {
        return statementCount;
    }

    /**
     * Gets the NearMiss attribute of the MethodSummary object
     *
     * @param other
     * 		Description of Parameter
     * @return The NearMiss value
     */
    public boolean isNearMiss(org.acm.seguin.summary.MethodSummary other) {
        return checkSignature(other) && (!(checkReturn(other) && checkProtection(other)));
    }

    /**
     * Determines if this is really a method or an initializer
     *
     * @return true if it is an initializer
     */
    public boolean isInitializer() {
        return name.equals("***Initializer***");
    }

    /**
     * Returns the maximum levels of blocks in a certain code
     *
     * @return The MaxBlockDepth value
     */
    public int getMaxBlockDepth() {
        return maxBlockDepth;
    }

    /**
     * Gets the DeclarationLine attribute of the MethodSummary object
     *
     * @return The DeclarationLine value
     */
    public int getDeclarationLine() {
        return declarationLine;
    }

    /**
     * Determine if this is a constructor
     *
     * @return The Constructor value
     */
    public boolean isConstructor() {
        return (returnType == null) && (!isInitializer());
    }

    /**
     * Convert this to a string
     *
     * @return a string representation of this object
     */
    public java.lang.String toString() {
        // Start with method name
        java.lang.StringBuffer buffer = new java.lang.StringBuffer(getName());
        buffer.append("(");
        // Add parameters
        java.util.Iterator iter = getParameters();
        if (iter != null) {
            while (iter.hasNext()) {
                java.lang.Object next = iter.next();
                buffer.append(next.toString());
                if (iter.hasNext()) {
                    buffer.append(", ");
                }
            } 
        }
        // Add return type
        if (returnType == null) {
            buffer.append(")");
        } else {
            buffer.append(") : ");
            buffer.append(returnType.toString());
        }
        // Return the result
        return buffer.toString();
    }

    /**
     * Provide method to visit a node
     *
     * @param visitor
     * 		the visitor
     * @param data
     * 		the data for the visit
     * @return some new data
     */
    public java.lang.Object accept(org.acm.seguin.summary.SummaryVisitor visitor, java.lang.Object data) {
        return visitor.visit(this, data);
    }

    /**
     * Increments the statement count
     */
    public void incrStatementCount() {
        statementCount++;
    }

    /**
     * Description of the Method
     *
     * @param other
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public boolean equals(java.lang.Object other) {
        if (other instanceof org.acm.seguin.summary.MethodSummary) {
            org.acm.seguin.summary.MethodSummary temp = ((org.acm.seguin.summary.MethodSummary) (other));
            return (checkSignature(temp) && checkReturn(temp)) && checkProtection(temp);
        }
        return false;
    }

    /**
     * Notes to the method summary that there is a block in the code
     */
    public void beginBlock() {
        blockDepth++;
        if (blockDepth > maxBlockDepth) {
            maxBlockDepth = blockDepth;
        }
    }

    /**
     * Notes to the method summary that the block in the code is complete
     */
    public void endBlock() {
        blockDepth--;
    }

    /**
     * Determines if their signatures are the same
     *
     * @param other
     * 		the oether method
     * @return true if they have the same signatures
     */
    public boolean checkSignature(org.acm.seguin.summary.MethodSummary other) {
        if (!name.equals(other.getName())) {
            return false;
        }
        java.util.Iterator params1 = getParameters();
        java.util.Iterator params2 = other.getParameters();
        if (params1 == null) {
            return params2 == null;
        }
        if (params2 == null) {
            return false;
        }
        while (params1.hasNext() && params2.hasNext()) {
            org.acm.seguin.summary.ParameterSummary next1 = ((org.acm.seguin.summary.ParameterSummary) (params1.next()));
            org.acm.seguin.summary.ParameterSummary next2 = ((org.acm.seguin.summary.ParameterSummary) (params2.next()));
            if (!next1.getTypeDecl().isSame(next2.getTypeDecl())) {
                return false;
            }
        } 
        return params1.hasNext() == params2.hasNext();
    }

    /**
     * Sets the modifier holder
     *
     * @param mod
     * 		the holder
     */
    protected void setModifiers(org.acm.seguin.pretty.ModifierHolder mod) {
        modifiers = mod;
    }

    /**
     * Add the formal parameters
     *
     * @param except
     * 		Description of Parameter
     */
    protected void add(org.acm.seguin.summary.TypeDeclSummary except) {
        if (except != null) {
            if (exceptionList == null) {
                initExceptionList();
            }
            exceptionList.add(except);
        }
    }

    /**
     * Add the formal parameters
     *
     * @param param
     * 		the next formal parameter
     */
    protected void add(org.acm.seguin.summary.ParameterSummary param) {
        if (param != null) {
            if (parameterList == null) {
                initParameterList();
            }
            parameterList.add(param);
        }
    }

    /**
     * Adds a variable dependency
     *
     * @param dependsOn
     * 		a variable that this method is depending on - local
     * 		variable or something in an anonymous class.
     */
    protected void addDependency(org.acm.seguin.summary.Summary dependsOn) {
        if (dependsOn != null) {
            if (dependencyList == null) {
                initDependencyList();
            }
            if (!dependencyList.contains(dependsOn)) {
                dependencyList.add(dependsOn);
            }
        }
    }

    /**
     * Initialize the parameter list
     */
    private void initParameterList() {
        parameterList = new java.util.LinkedList();
    }

    /**
     * Initialize the exception list
     */
    private void initExceptionList() {
        exceptionList = new java.util.LinkedList();
    }

    /**
     * Initialize the dependency list
     */
    private void initDependencyList() {
        dependencyList = new java.util.LinkedList();
    }

    /**
     * Determines if they have the same return value
     *
     * @param other
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean checkReturn(org.acm.seguin.summary.MethodSummary other) {
        return getReturnType().isSame(other.getReturnType());
    }

    /**
     * Determines if they have the same protections
     *
     * @param other
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean checkProtection(org.acm.seguin.summary.MethodSummary other) {
        org.acm.seguin.pretty.ModifierHolder mh1 = getModifiers();
        org.acm.seguin.pretty.ModifierHolder mh2 = other.getModifiers();
        return (((mh1.isPublic() == mh2.isPublic()) && (mh1.isProtected() == mh2.isProtected())) && (mh1.isPackage() == mh2.isPackage())) && (mh1.isPrivate() == mh2.isPrivate());
    }
}