/*
 * 此代码创建于 2016年1月8日 下午2:24:30。
 */
package com.apollo.demos.osgi.scr.message.api;

public interface IComputeParticle {

    public abstract void processMessage(IMessageContext context, ParticleMessage message);

}
