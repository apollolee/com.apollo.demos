/*
 * 此代码创建于 2016年1月4日 下午1:28:12。
 */
package com.apollo.demos.karaf.shell.impl.completer;

import java.util.List;

import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.StringsCompleter;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.karaf.shell.Activator;

public class ApolloBundleName implements Completer {

    public static final String s_prefix = "com.apollo.demos.";

    private static final Logger s_logger = LoggerFactory.getLogger(ApolloBundleName.class);

    /**
     * @see org.apache.karaf.shell.console.Completer#complete(java.lang.String, int, java.util.List)
     */
    public int complete(String buffer, int cursor, List<String> candidates) {
        s_logger.info("[Buffer = {}] , [Cursor = {}], [Candidates = {}]", buffer, cursor, candidates);

        StringsCompleter delegate = new StringsCompleter();
        for (Bundle bundle : Activator.getContext().getBundles()) {
            String sn = bundle.getSymbolicName();
            if (sn.startsWith(s_prefix)) {
                delegate.getStrings().add(sn.substring(s_prefix.length()));
            }
        }

        return delegate.complete(buffer, cursor, candidates);
    }

}
