package com.projectA;
/**
 *
 * @Author：linan  * @Date：2023/4/24 17:17
 */
public class ComponentDecorator implements com.projectA.Component {
    private com.projectA.Component component;

    public ComponentDecorator(com.projectA.Component component) {
        this.component = component;
    }

    @java.lang.Override
    public void display() {
        component.display();
    }
}