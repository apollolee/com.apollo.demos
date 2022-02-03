/*
 * 此代码创建于 2022年2月3日 下午7:28:05。
 */
package com.apollo.demos.base.jsr269;

import static com.apollo.demos.base.jsr269.LogLevel.INFO;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target(METHOD)
public @interface Log {

    LogLevel level() default INFO;

}
