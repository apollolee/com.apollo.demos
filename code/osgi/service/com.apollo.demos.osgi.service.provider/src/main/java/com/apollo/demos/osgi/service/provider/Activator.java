package com.apollo.demos.osgi.service.provider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.service.api.ISiri;
import com.apollo.demos.osgi.service.api.bad.IBadSiri;
import com.apollo.demos.osgi.service.provider.impl.BadSiriImpl;
import com.apollo.demos.osgi.service.provider.impl.SiriImpl;

/*
 * Service的提供者，启动Bundle时注册。
 */
public class Activator implements BundleActivator {

    private static final Logger s_logger = LoggerFactory.getLogger(Activator.class);

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    private ServiceRegistration m_siriSr;

    private ServiceRegistration m_badSiriSr;

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext bundleContext) throws Exception {
        s_logger.info("Start com.apollo.demos.osgi.service.provider.");

        Activator.context = bundleContext;

        m_siriSr = context.registerService(ISiri.class.getName(), new SiriImpl(), null);
        m_badSiriSr = context.registerService(IBadSiri.class.getName(), new BadSiriImpl(), null);
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        s_logger.info("Stop com.apollo.demos.osgi.service.provider.");

        m_badSiriSr.unregister();
        m_siriSr.unregister();

        Activator.context = null;
    }

}
