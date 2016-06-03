/*
 * 此代码创建于 2016年2月17日 下午3:31:40。
 */
package com.apollo.demos.base.annotation.repeatable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Roles {

    public Role[] value();

}
