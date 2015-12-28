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
import com.apollo.demos.osgi.scr.service.api.bad.IBadSiri;
import com.apollo.demos.osgi.scr.service.api.bad.IPhone;

@Component
public class AppleWatch {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleWatch.class);

    @Reference(bind = "bindSiri", unbind = "unbindSiri")
    private ISiri m_siri;

    @Reference(bind = "bindBadSiri", unbind = "unbindBadSiri")
    private IBadSiri m_badSiri;

    public AppleWatch() {
        s_logger.info("New.");
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

    protected void bindSiri(ISiri siri) {
        s_logger.info("Bind Siri.");
        m_siri = siri;
        s_logger.info("Say: {}", m_siri.sayHello());
    }

    protected void unbindSiri(ISiri siri) {
        s_logger.info("Unbind Siri.");
        m_siri = null;
    }

    protected void bindBadSiri(IBadSiri badSiri) {
        s_logger.info("Bind BadSiri.");

        m_badSiri = badSiri;
        IPhone ip = badSiri.giveMeAnIPhone();

        s_logger.info("IBadSiri: [ID = {}] Say: {}", IBadSiri.ID, badSiri.sayHello());
        s_logger.info("IPhone: [ID = {}] Say: {}", IPhone.ID, ip.sayHello());
    }

    protected void unbindBadSiri(IBadSiri badSiri) {
        s_logger.info("Unbind BadSiri.");
        m_badSiri = null;
    }

}
