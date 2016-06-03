/*
 * 此代码创建于 2015年12月26日 上午8:52:13。
 */
package com.apollo.demos.osgi.service.api.bad;

public interface IBadSiri {

    public static final String ID = "1.0.0";

    public abstract String sayHello();

    public abstract IPhone giveMeAnIPhone();

}
