/*
 * 此代码创建于 2017年2月23日 下午5:13:57。
 */
package com.apollo.demos.osgi.base.api.impl;

import static org.slf4j.LoggerFactory.getLogger;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;

public class Activator implements BundleActivator {

    private static final Logger s_logger = getLogger(Activator.class);

    private static volatile BundleContext s_context;

    public static BundleContext getContext() {
        return s_context;
    }

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext bundleContext) throws Exception {
        s_logger.debug("Start.");
        s_context = bundleContext;
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        s_logger.debug("Stop.");
        s_context = null;
    }

}
