/*
 * 此代码创建于 2016年1月13日 上午11:22:03。
 */
package com.apollo.demos.osgi.app.message.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComputeParticle {

    public EType type();

    public String function();

    public EScope scope() default EScope.CNode;

}
