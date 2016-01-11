/*
 * 此代码创建于 2016年1月8日 下午3:01:05。
 */
package com.apollo.demos.osgi.scr.message.receiver2.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.message.api.IComputeParticle;
import com.apollo.demos.osgi.scr.message.api.IMessageContext;
import com.apollo.demos.osgi.scr.message.api.ParticleMessage;

public class ComputeParticleImpl implements IComputeParticle {

    private static final Logger s_logger = LoggerFactory.getLogger(ComputeParticleImpl.class);

    @Override
    public void processMessage(IMessageContext context, ParticleMessage message) {
        s_logger.info("Process message. [Message Context = {}] , [Particle Message ={}]", context, message);
    }

}
