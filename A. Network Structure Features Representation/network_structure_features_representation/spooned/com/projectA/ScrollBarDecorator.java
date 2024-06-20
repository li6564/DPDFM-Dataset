package com.projectA;
/**
 *
 * @Author：linan  * @Date：2023/4/24 17:18
 */
public class ScrollBarDecorator extends com.projectA.ComponentDecorator {
    public ScrollBarDecorator(com.projectA.Component component) {
        super(component);
    }

    public void display() {
        this.setScrollBar();
        super.display();
    }

    public void setScrollBar() {
        java.lang.System.out.println("为构件增加滚动条！");
    }
}