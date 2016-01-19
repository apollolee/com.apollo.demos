/*
 * 此代码创建于 2016年1月8日 下午3:00:41。
 */
package com.apollo.demos.osgi.scr.message.receiver1.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.message.api.IComputeParticle;
import com.apollo.demos.osgi.scr.message.api.IComputeParticleCreator;

@Component
@Service
@Properties({ @Property(name = "type", value = "NE"),
             @Property(name = "function", value = "CreateLsp"),
             @Property(name = "scope", value = "CNode") })
public class ComputeParticleCreatorImpl implements IComputeParticleCreator {

    private static final Logger s_logger = LoggerFactory.getLogger(ComputeParticleCreatorImpl.class);

    public ComputeParticleCreatorImpl() {
        s_logger.info("New.");
    }

    @Override
    public IComputeParticle createParticle() {
        s_logger.info("Create particle.");

        return new ComputeParticleImpl();
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
