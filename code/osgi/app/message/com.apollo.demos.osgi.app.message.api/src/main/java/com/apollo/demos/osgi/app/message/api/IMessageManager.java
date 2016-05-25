/*
 * 此代码创建于 2016年1月8日 下午4:45:24。
 */
package com.apollo.demos.osgi.app.message.api;

public interface IMessageManager {

    @SuppressWarnings("rawtypes")
    public abstract void postMessage(ParticleMessage message);

}
