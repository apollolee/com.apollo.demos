/*
 * 此代码创建于 2015年12月24日 上午11:02:51。
 */
package com.apollo.demos.osgi.scr.service.invoker;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.service.api.ISiri;

@Component
public class AppleWatch {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleWatch.class);

    @Reference
    private ISiri m_siri;

    public AppleWatch() {
        s_logger.info("New.");
    }

    @Activate
    protected void activate(ComponentContext context) {
        s_logger.info("Activate.");
        s_logger.info("Say: {}", m_siri.sayHello());
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
