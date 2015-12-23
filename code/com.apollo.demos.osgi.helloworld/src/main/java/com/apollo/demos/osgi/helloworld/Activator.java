package com.apollo.demos.osgi.helloworld;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/*
 * 这是最简单的一个OSGi程序。
 */
public class Activator implements BundleActivator {

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("Start com.apollo.demos.osgi.helloworld !");

        Activator.context = bundleContext;

        Bundle bundle = context.getBundle();
        System.out.println("Location:" + bundle.getLocation());
        System.out.println("ID:" + bundle.getBundleId());
        System.out.println("SymbolicName:" + bundle.getSymbolicName());
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("Stop com.apollo.demos.osgi.helloworld !");

        Activator.context = null;
    }

}
