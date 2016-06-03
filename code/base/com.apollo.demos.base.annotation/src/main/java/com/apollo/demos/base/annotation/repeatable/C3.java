/*
 * 此代码创建于 2016年2月17日 下午3:33:36。
 */
package com.apollo.demos.base.annotation.repeatable;

/*同样不能达到父子类自动合并所有Role的效果。*/
@Role(name = "C3")
@Role(name = "C33")
@Role(name = "C333")
public class C3 extends C1 {

    public static void main(String[] args) {
        Role[] roles = C3.class.getAnnotationsByType(Role.class);
        String names = "";
        for (Role role : roles) {
            names = names + " " + role.name();
        }
        System.out.println("C3's names is" + names);
    }

}
