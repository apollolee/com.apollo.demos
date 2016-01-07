/*
 * 此代码创建于 2016年1月4日 上午10:07:23。
 */
package com.apollo.demos.karaf.shell.impl.command;

import org.apache.karaf.shell.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "apollo", name = "hello", description = "Apollo say hello.")
public class ApolloHello extends ApolloBase {

    private static final Logger s_logger = LoggerFactory.getLogger(ApolloHello.class);

    public ApolloHello() {
        s_logger.info("New.");
    }

    @Override
    protected Object doExecute() throws Exception {
        System.out.println("Hello everyone.\n");
        return null;
    }

}
