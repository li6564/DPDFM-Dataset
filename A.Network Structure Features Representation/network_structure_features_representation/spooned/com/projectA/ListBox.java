package com.projectA;
/**
 *
 * @Author：linan  * @Date：2023/4/24 17:17
 */
public class ListBox implements com.projectA.Component {
    java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder();

    com.projectA.TextBox t = new com.projectA.TextBox();

    @java.lang.Override
    public void display() {
        java.lang.System.out.println("显示列表框！");
    }
}