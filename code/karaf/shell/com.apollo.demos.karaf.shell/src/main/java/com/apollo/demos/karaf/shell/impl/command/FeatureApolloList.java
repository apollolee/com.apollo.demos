/*
 * 此代码创建于 2016年1月4日 上午11:24:07。
 */
package com.apollo.demos.karaf.shell.impl.command;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.CommandSessionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "feature", name = "apollo-list", description = "List apollo features.")
public class FeatureApolloList extends ApolloBase {

    private static final Logger s_logger = LoggerFactory.getLogger(FeatureApolloList.class);

    @Option(name = "-i", aliases = "--installed", description = "Display a list of all installed apollo features only.", required = false, multiValued = false)
    private boolean m_isInstalled = false;

    public FeatureApolloList() {
        s_logger.info("New.");
    }

    @Override
    protected Object doExecute() throws Exception {
        String installed = m_isInstalled ? "-i" : "";
        return CommandSessionHolder.getSession().execute("feature:list " + installed + " | grep apollo-demos");
    }

}
