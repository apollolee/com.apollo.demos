/*
 * 此代码创建于 2016年1月4日 下午1:28:12。
 */
package com.apollo.demos.osgi.karaf.shell.impl.completer;

import java.util.List;

import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.StringsCompleter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApolloOption implements Completer {

    private static final Logger s_logger = LoggerFactory.getLogger(ApolloOption.class);

    /**
     * @see org.apache.karaf.shell.console.Completer#complete(java.lang.String, int, java.util.List)
     */
    public int complete(String buffer, int cursor, List<String> candidates) {
        s_logger.info("[Buffer = {}] , [Cursor = {}], [Candidates = {}]", buffer, cursor, candidates);

        StringsCompleter delegate = new StringsCompleter();
        delegate.getStrings().add("import");
        delegate.getStrings().add("export");
        delegate.getStrings().add("provide");

        return delegate.complete(buffer, cursor, candidates);
    }

}
