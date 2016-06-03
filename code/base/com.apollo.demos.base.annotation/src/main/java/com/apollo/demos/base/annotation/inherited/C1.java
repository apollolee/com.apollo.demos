/*
 * 此代码创建于 2016年2月17日 下午3:33:36。
 */
package com.apollo.demos.base.annotation.inherited;

@Role(id = 1, names = { "C1", "C11", "C111" })
public class C1 {

    public static void main(String[] args) {
        Role role = C1.class.getAnnotation(Role.class);
        String names = "";
        for (String name : role.names()) {
            names = names + " " + name;
        }
        System.out.println("C1's id is " + role.id() + " , names is" + names);
    }

}
