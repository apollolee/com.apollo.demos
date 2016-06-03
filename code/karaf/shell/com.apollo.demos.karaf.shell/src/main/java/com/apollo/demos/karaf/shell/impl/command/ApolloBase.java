/*
 * 此代码创建于 2016年1月4日 上午10:07:23。
 */
package com.apollo.demos.karaf.shell.impl.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.karaf.shell.Activator;

public abstract class ApolloBase extends OsgiCommandSupport {

    private static final Logger s_logger = LoggerFactory.getLogger(ApolloBase.class);

    private static final String s_prefix = "com.apollo.demos.";

    public static BundleContext getContext() {
        return Activator.getContext();
    }

    public static String[] getGroups() {
        List<String> groups = new ArrayList<String>();

        for (Bundle bundle : getContext().getBundles()) {
            String sn = bundle.getSymbolicName();
            if (sn.startsWith(s_prefix)) {
                groups.add(sn.substring(s_prefix.length(), sn.lastIndexOf('.')));
            }
        }

        return groups.toArray(new String[groups.size()]);
    }

    public static String[] getNames(String group) {
        if (group == null) {
            return new String[0];
        }

        String prefix = s_prefix + group + ".";

        List<String> names = new ArrayList<String>();

        for (Bundle bundle : getContext().getBundles()) {
            String sn = bundle.getSymbolicName();
            if (sn.startsWith(prefix)) {
                names.add(sn.substring(prefix.length()));
            }
        }

        return names.toArray(new String[names.size()]);
    }

    public static Bundle getBundle(String group, String name) {
        String sn = s_prefix + group + "." + name;

        for (Bundle bundle : getContext().getBundles()) {
            if (bundle.getSymbolicName().equals(sn)) {
                return bundle;
            }
        }

        return null;
    }

    public ApolloBase() {
        s_logger.info("New.");
    }

}
