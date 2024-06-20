package org.acm.seguin.refactor.type;
/**
 * Holds a state for a type change visitor to find the type;
 *
 * @author Chris Seguin
 */
class State {
    private boolean packageRequired;

    private java.lang.String className;

    /**
     * Constructor for the State object
     *
     * @param name
     * 		Description of Parameter
     * @param required
     * 		Description of Parameter
     */
    public State(java.lang.String name, boolean required) {
        packageRequired = required;
        className = name;
    }

    /**
     * Gets the ClassName attribute of the State object
     *
     * @return The ClassName value
     */
    public java.lang.String getClassName() {
        return className;
    }

    /**
     * Gets the PackageRequired attribute of the State object
     *
     * @return The PackageRequired value
     */
    public boolean isPackageRequired() {
        return packageRequired;
    }
}