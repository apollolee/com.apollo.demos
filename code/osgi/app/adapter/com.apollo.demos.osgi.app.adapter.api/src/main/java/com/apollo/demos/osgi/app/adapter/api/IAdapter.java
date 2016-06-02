/*
 * 此代码创建于 2016年5月25日 下午1:21:49。
 */
package com.apollo.demos.osgi.app.adapter.api;

public interface IAdapter {

    public abstract IFunction getFunction(String functionId, String deviceType, String version);

}
