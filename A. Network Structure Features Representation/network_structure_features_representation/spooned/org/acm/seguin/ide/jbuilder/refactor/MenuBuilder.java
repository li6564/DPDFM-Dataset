/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder.refactor;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class MenuBuilder {
    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.ide.jbuilder.refactor.ActionGroup build() {
        org.acm.seguin.ide.jbuilder.refactor.ActionGroup group = new org.acm.seguin.ide.jbuilder.refactor.ActionGroup("Refactorings");
        group.setPopup(true);
        group.add(org.acm.seguin.ide.jbuilder.refactor.MenuBuilder.buildType());
        group.add(org.acm.seguin.ide.jbuilder.refactor.MenuBuilder.buildMethod());
        group.add(org.acm.seguin.ide.jbuilder.refactor.MenuBuilder.buildField());
        return group;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.ide.jbuilder.refactor.ActionGroup buildMethod() {
        org.acm.seguin.ide.jbuilder.refactor.ActionGroup group = new org.acm.seguin.ide.jbuilder.refactor.ActionGroup("Method Refactorings");
        group.setPopup(true);
        group.add(new org.acm.seguin.ide.common.action.PushUpMethodAction());
        group.add(new org.acm.seguin.ide.common.action.PushUpAbstractMethodAction());
        group.add(new org.acm.seguin.ide.common.action.PushDownMethodAction());
        group.add(new org.acm.seguin.ide.common.action.RenameParameterAction());
        group.add(new org.acm.seguin.ide.common.action.ExtractMethodAction());
        return group;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.ide.jbuilder.refactor.ActionGroup buildField() {
        org.acm.seguin.ide.jbuilder.refactor.ActionGroup group = new org.acm.seguin.ide.jbuilder.refactor.ActionGroup("Field Refactorings");
        group.setPopup(true);
        group.add(new org.acm.seguin.ide.common.action.RenameFieldAction());
        group.add(new org.acm.seguin.ide.common.action.PushUpFieldAction());
        group.add(new org.acm.seguin.ide.common.action.PushDownFieldAction());
        return group;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    private static org.acm.seguin.ide.jbuilder.refactor.ActionGroup buildType() {
        org.acm.seguin.ide.jbuilder.refactor.ActionGroup group = new org.acm.seguin.ide.jbuilder.refactor.ActionGroup("Type Refactorings");
        group.setPopup(true);
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderRenameClassAction(null));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderMoveClassAction(null));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderAddParentClassAction(null));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderAddChildClassAction(null));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderRemoveClassAction(null));
        group.add(new org.acm.seguin.ide.jbuilder.refactor.JBuilderExtractInterfaceAction(null));
        return group;
    }
}