/*
 * 此代码创建于 2016年1月8日 下午2:24:30。
 */
package com.apollo.demos.osgi.app.message.api;

import java.io.Serializable;

public interface IComputeParticle<M extends Serializable> {

    public abstract void processMessage(IMessageContext context, ParticleMessage<M> message);

}
