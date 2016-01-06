package com.apollo.demos.osgi.service.invoker;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.service.invoker.impl.AppleWatch;
import com.apollo.demos.osgi.service.invoker.impl.BadAppleWatch;

/*
 * Service的调用者，为了不依赖启动顺序，使用了ServiceTracker进行处理。
 */
public class Activator implements BundleActivator {

    private static final Logger s_logger = LoggerFactory.getLogger(Activator.class);

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    private AppleWatch m_appleWatch;

    private BadAppleWatch m_badAppleWatch;

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext bundleContext) throws Exception {
        s_logger.info("Start com.apollo.demos.osgi.service.invoker.");

        Activator.context = bundleContext;

        m_appleWatch = new AppleWatch(context);
        m_appleWatch.open();

        m_badAppleWatch = new BadAppleWatch(context);
        m_badAppleWatch.open();
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        s_logger.info("Stop com.apollo.demos.osgi.service.invoker.");

        m_badAppleWatch.close();
        m_badAppleWatch = null;

        m_appleWatch.close();
        m_appleWatch = null;

        Activator.context = null;
    }

}
