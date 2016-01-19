/*
 * 此代码创建于 2016年1月8日 下午3:00:41。
 */
package com.apollo.demos.osgi.scr.message.receiver2.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.message.api.EScope;
import com.apollo.demos.osgi.scr.message.api.EType;
import com.apollo.demos.osgi.scr.message.api.IComputeParticle;
import com.apollo.demos.osgi.scr.message.api.IComputeParticleCreator;
import com.apollo.demos.osgi.scr.message.api.annotation.ComputeParticleCreator;

@Component
@Service
@ComputeParticleCreator(type = EType.NE, function = "CreatePw", scope = EScope.CNode)
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
