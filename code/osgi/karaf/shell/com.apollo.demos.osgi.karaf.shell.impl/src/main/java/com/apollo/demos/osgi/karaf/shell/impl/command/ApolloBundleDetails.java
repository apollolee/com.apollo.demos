/*
 * 此代码创建于 2016年1月4日 上午10:07:23。
 */
package com.apollo.demos.osgi.karaf.shell.impl.command;

import java.util.Set;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "apollo", name = "bundle-details", description = "Apollo bundle details.")
public class ApolloBundleDetails extends ApolloBase {

    private static final Logger s_logger = LoggerFactory.getLogger(ApolloBundleDetails.class);

    @Argument(index = 0, name = "group", description = "The group of apollo bundle.", required = true, multiValued = false)
    private String m_group = null;

    @Argument(index = 1, name = "name", description = "The name of apollo bundle.", required = true, multiValued = false)
    private String m_name = null;

    @Option(name = "-o", aliases = "--option", description = "The option of apollo bundle details.", required = false, multiValued = true, valueToShowInHelp = "import, export, provide.")
    private Set<String> m_options = null;

    public ApolloBundleDetails() {
        s_logger.info("New.");
    }

    @Override
    protected Object doExecute() throws Exception {
        s_logger.info("[Group = {}] , [Name = {}], [Options = {}]", m_group, m_name, m_options);

        Bundle bundle = getBundle(m_group, m_name);

        if (bundle == null) {
            System.err.println("Apollo bundle is not found. [Group = " + m_group + "] , [Name = " + m_name + "]");

        } else {
            System.out.println("---------------------Base---------------------");
            System.out.println("[Location = " + bundle.getLocation() + "]");
            System.out.println("[Bundle ID = " + bundle.getBundleId() + "]");
            System.out.println("[Symbolic Name = " + bundle.getSymbolicName() + "]");

            if (m_options != null) {
                if (m_options.contains("import")) {
                    System.out.println("\n---------------------Import-Package---------------------");
                    String ips = (String) bundle.getHeaders().get("Import-Package");
                    if (ips != null) {
                        for (String ip : ips.split("\",")) {
                            System.out.println(ip);
                        }
                    }
                }

                if (m_options.contains("export")) {
                    System.out.println("\n---------------------Export-Package---------------------");
                    String eps = (String) bundle.getHeaders().get("Export-Package");
                    if (eps != null) {
                        for (String ep : eps.split("\",")) {
                            System.out.println(ep);
                        }
                    }
                }

                if (m_options.contains("provide")) {
                    System.out.println("\n---------------------Provide-Capability---------------------");
                    String rcs = (String) bundle.getHeaders().get("Provide-Capability");
                    for (String rc : rcs.split(",")) {
                        System.out.println(rc);
                    }
                }
            }
        }

        System.out.println("");

        return null;
    }

}
