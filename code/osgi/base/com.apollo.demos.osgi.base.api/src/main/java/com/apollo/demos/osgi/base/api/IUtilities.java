/*
 * 此代码创建于 2017年2月4日 下午1:52:13。
 */
package com.apollo.demos.osgi.base.api;

import java.io.PrintWriter;
import java.io.StringWriter;

public interface IUtilities {

    String id(Object obj);

    String threadId(Thread thread);

    String className(Object object);

    String stack(Thread thread);

    default String stack(Throwable throwable) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();

        } catch (Throwable ex) {
            throw new RuntimeException("Get stack is failed.", ex);
        }
    }

    String percent(long numerator, long denominator);

}
