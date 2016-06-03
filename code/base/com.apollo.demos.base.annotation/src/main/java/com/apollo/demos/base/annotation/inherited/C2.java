/*
 * 此代码创建于 2016年2月17日 下午3:33:36。
 */
package com.apollo.demos.base.annotation.inherited;

@Role(id = 2, names = { "C2", "C22", "C222" })
public class C2 extends C1 {

    public static void main(String[] args) {
        Role role = C2.class.getAnnotation(Role.class);
        String names = "";
        for (String name : role.names()) {
            names = names + " " + name;
        }
        System.out.println("C2's id is " + role.id() + " , names is" + names);
    }

}
