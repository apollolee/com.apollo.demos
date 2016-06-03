/*
 * 此代码创建于 2016年5月20日 下午4:23:59。
 */
package com.apollo.demos.osgi.app.adapter.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Processor {

    public int order();

}
