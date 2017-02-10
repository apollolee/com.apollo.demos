/*
 * 此代码创建于 2017年2月4日 下午1:52:13。
 */
package com.apollo.demos.osgi.base.api;

public interface IUtilities {

    String id(Object obj);

    String threadId(Thread thread);

    String className(Object object);

    String stack(Thread thread);

}
