/*
 * 此代码创建于 2016年1月8日 下午3:01:05。
 */
package com.apollo.demos.osgi.app.message.receiver1.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.app.message.api.ComputeParticle;
import com.apollo.demos.osgi.app.message.api.EScope;
import com.apollo.demos.osgi.app.message.api.EType;
import com.apollo.demos.osgi.app.message.api.IComputeParticle;
import com.apollo.demos.osgi.app.message.api.IMessageContext;
import com.apollo.demos.osgi.app.message.api.ParticleMessage;

@Component(factory = "ComputeParticle", configurationFactory = true)
@Service
@ComputeParticle(type = EType.NE, function = "CreateLsp", scope = EScope.CNode)
public class ComputeParticleImpl implements IComputeParticle {

    private static final Logger s_logger = LoggerFactory.getLogger(ComputeParticleImpl.class);

    public ComputeParticleImpl() {
        s_logger.info("New.");
    }

    @Override
    public void processMessage(IMessageContext context, ParticleMessage message) {
        s_logger.info("Process message. [Message Context = {}] , [Particle Message ={}]", context, message);
    }

    @Activate
    protected void activate(ComponentContext context) {
        s_logger.info("Activate.");
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        s_logger.info("Deactivate.");
    }

    @Modified
    protected void modified(ComponentContext context) {
        s_logger.info("Modified.");
    }

}
