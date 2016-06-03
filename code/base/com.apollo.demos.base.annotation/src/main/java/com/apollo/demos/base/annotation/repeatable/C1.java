/*
 * 此代码创建于 2016年2月17日 下午3:33:36。
 */
package com.apollo.demos.base.annotation.repeatable;

/*这种写法只是一种语法糖，和C2的效果一样。*/
@Role(name = "C1")
@Role(name = "C11")
@Role(name = "C111")
public class C1 {

    public static void main(String[] args) {
        Role[] roles = C1.class.getAnnotationsByType(Role.class);
        String names = "";
        for (Role role : roles) {
            names = names + " " + role.name();
        }
        System.out.println("C1's names is" + names);
    }

}
