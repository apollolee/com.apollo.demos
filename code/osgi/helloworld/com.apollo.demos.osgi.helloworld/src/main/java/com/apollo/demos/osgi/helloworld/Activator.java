package com.apollo.demos.osgi.helloworld;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 这是最简单的一个OSGi程序。
 */
public class Activator implements BundleActivator {

    private static final Logger s_logger = LoggerFactory.getLogger(Activator.class);

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext bundleContext) throws Exception {
        s_logger.info("Start com.apollo.demos.osgi.helloworld.");

        Activator.context = bundleContext;

        Bundle bundle = context.getBundle();
        s_logger.info("[Location = {}]", bundle.getLocation());
        s_logger.info("[Bundle ID = {}]", bundle.getBundleId());
        s_logger.info("[Symbolic Name = {}]", bundle.getSymbolicName());
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        s_logger.info("Stop com.apollo.demos.osgi.helloworld.");

        Activator.context = null;
    }

}
