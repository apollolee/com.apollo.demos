/*
 * 此代码创建于 2015年12月24日 上午11:02:51。
 */
package com.apollo.demos.osgi.service.invoker.impl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.service.api.ISiri;

public class AppleWatch extends ServiceTracker {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleWatch.class);

    public AppleWatch(BundleContext context) {
        super(context, ISiri.class.getName(), null);
        s_logger.info("New.");
    }

    @Override
    public Object addingService(ServiceReference reference) {
        s_logger.info("Adding service.");

        ISiri siri = (ISiri) super.addingService(reference);
        s_logger.info("Say: {}", siri.sayHello());

        return siri;
    }

    @Override
    public void removedService(ServiceReference reference, Object service) {
        s_logger.info("Removed service.");

        super.removedService(reference, service);
    }

}
