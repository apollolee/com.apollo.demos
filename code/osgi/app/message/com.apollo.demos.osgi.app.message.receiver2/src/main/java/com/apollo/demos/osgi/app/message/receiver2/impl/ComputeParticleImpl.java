/*
 * 此代码创建于 2016年1月8日 下午3:01:05。
 */
package com.apollo.demos.osgi.app.message.receiver2.impl;

import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Factory.ComputeParticle;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Property.Function;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Property.Scope;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Property.Type;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Scope.CNode;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Type.NE;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.app.message.api.IComputeParticle;
import com.apollo.demos.osgi.app.message.api.IMessageContext;
import com.apollo.demos.osgi.app.message.api.ParticleMessage;

@Component(factory = ComputeParticle + ":" + Type + "=" + NE + ";" + Scope + "=" + CNode + ";" + Function + "=CreatePw")
@Service
public class ComputeParticleImpl implements IComputeParticle<String> {

    private static final Logger s_logger = LoggerFactory.getLogger(ComputeParticleImpl.class);

    public ComputeParticleImpl() {
        s_logger.info("New. [ID = 0x{}]", Integer.toHexString(System.identityHashCode(this)));
    }

    @Override
    public void processMessage(IMessageContext context, ParticleMessage<String> message) {
        s_logger.info("Process message. [ID = 0x{}] , [Message Context = {}] , [Particle Message ={}]",
                      Integer.toHexString(System.identityHashCode(this)),
                      context,
                      message);
    }

    @Activate
    protected void activate(ComponentContext context) {
        s_logger.info("Activate. [ID = 0x{}]", Integer.toHexString(System.identityHashCode(this)));
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        s_logger.info("Deactivate. [ID = 0x{}]", Integer.toHexString(System.identityHashCode(this)));
    }

    @Modified
    protected void modified(ComponentContext context) {
        s_logger.info("Modified. [ID = 0x{}]", Integer.toHexString(System.identityHashCode(this)));
    }

}
