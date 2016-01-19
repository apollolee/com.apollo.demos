package com.apollo.demos.osgi.fragment.host;

import java.util.ResourceBundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        s_logger.info("Start com.apollo.demos.osgi.fragment.host.");

        Activator.context = bundleContext;

        ResourceBundle rb = ResourceBundle.getBundle("/OSGI-INF/l10n/bundle");
        s_logger.info("[HostBundleDescription = {}]", rb.getString("HostBundleDescription"));
        s_logger.info("[LocalizationBundleDescription = {}]", rb.getString("LocalizationBundleDescription"));
        s_logger.info("[HelloFragment = {}]", rb.getString("HelloFragment"));
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        s_logger.info("Stop com.apollo.demos.osgi.fragment.host.");

        Activator.context = null;
    }

}
