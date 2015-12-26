/*
 * 此代码创建于 2015年12月24日 上午10:20:12。
 */
package com.apollo.demos.osgi.scr.service.provider.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.service.api.ISiri;

@Component
@Service
public class SiriImpl implements ISiri {

    private static final Logger s_logger = LoggerFactory.getLogger(SiriImpl.class);

    public SiriImpl() {
        s_logger.info("New.");
    }

    @Override
    public String sayHello() {
        return "Hello, Service Component Runtime (SCR).";
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
