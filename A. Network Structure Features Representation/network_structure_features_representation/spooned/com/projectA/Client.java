package com.projectA;
/**
 *
 * @Author：linan  * @Date：2023/4/24 17:19
 */
public class Client implements com.projectA.A {
    public Client() {
    }

    public static void main(java.lang.String[] args) {
        com.projectA.Component component;// 使用抽象构件定义

        com.projectA.Component componentSB;
        component = new com.projectA.Window();// 定义具体构件

        componentSB = new com.projectA.ScrollBarDecorator(component);// 定义装饰后的构件

        componentSB.display();
    }
}