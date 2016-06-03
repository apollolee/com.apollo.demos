/*
 * 此代码创建于 2016年1月4日 下午1:28:12。
 */
package com.apollo.demos.karaf.shell.impl.completer;

import java.util.List;

import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.StringsCompleter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.karaf.shell.impl.command.ApolloBase;

public class ApolloGroup implements Completer {

    private static final Logger s_logger = LoggerFactory.getLogger(ApolloGroup.class);

    /**
     * @see org.apache.karaf.shell.console.Completer#complete(java.lang.String, int, java.util.List)
     */
    public int complete(String buffer, int cursor, List<String> candidates) {
        s_logger.info("[Buffer = {}] , [Cursor = {}], [Candidates = {}]", buffer, cursor, candidates);

        return new StringsCompleter(ApolloBase.getGroups()).complete(buffer, cursor, candidates);
    }

}
