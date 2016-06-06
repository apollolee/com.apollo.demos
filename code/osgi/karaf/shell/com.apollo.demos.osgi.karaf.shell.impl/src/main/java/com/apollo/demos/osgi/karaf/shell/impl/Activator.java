/*
 * 此代码创建于 2016年6月6日 下午2:48:11。
 */
package com.apollo.demos.osgi.karaf.shell.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

    private static final Logger s_logger = LoggerFactory.getLogger(Activator.class);

    private static BundleContext context;

    public static BundleContext getContext() {
        return context;
    }

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext bundleContext) throws Exception {
        s_logger.info("Start com.apollo.demos.osgi.karaf.shell.impl.");

        Activator.context = bundleContext;
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        s_logger.info("Stop com.apollo.demos.osgi.karaf.shell.impl.");

        Activator.context = null;
    }

}
