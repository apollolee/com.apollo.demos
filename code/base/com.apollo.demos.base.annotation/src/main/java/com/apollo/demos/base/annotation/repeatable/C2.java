/*
 * 此代码创建于 2016年2月17日 下午3:33:36。
 */
package com.apollo.demos.base.annotation.repeatable;

@Roles({ @Role(name = "C2"), @Role(name = "C22"), @Role(name = "C222") })
public class C2 {

    public static void main(String[] args) {
        Role[] roles = C2.class.getAnnotationsByType(Role.class);
        String names = "";
        for (Role role : roles) {
            names = names + " " + role.name();
        }
        System.out.println("C2's names is" + names);
    }

}
