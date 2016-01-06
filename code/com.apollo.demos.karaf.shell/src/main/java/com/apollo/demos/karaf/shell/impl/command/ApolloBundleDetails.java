/*
 * 此代码创建于 2016年1月4日 上午10:07:23。
 */
package com.apollo.demos.karaf.shell.impl.command;

import java.util.Set;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.karaf.shell.impl.completer.ApolloBundleName;

@Command(scope = "apollo", name = "bundle-details", description = "Apollo bundle details.")
public class ApolloBundleDetails extends OsgiCommandSupport {

    private static final Logger s_logger = LoggerFactory.getLogger(ApolloBundleDetails.class);

    @Argument(index = 0, name = "name", description = "The name of apollo bundle.", required = true, multiValued = false)
    private String m_name = null;

    @Option(name = "-o", aliases = "--option", description = "The option of apollo bundle details.", required = false, multiValued = true, valueToShowInHelp = "import, export, provide.")
    private Set<String> m_options = null;

    public ApolloBundleDetails() {
        s_logger.info("New.");
    }

    @Override
    protected Object doExecute() throws Exception {
        s_logger.info("[Name = {}], [Options = {}]", m_name, m_options);

        Bundle target = null;

        for (Bundle bundle : getBundleContext().getBundles()) {
            if (bundle.getSymbolicName().startsWith(ApolloBundleName.s_prefix + m_name)) {
                target = bundle;
                break;
            }
        }

        if (target == null) {
            System.err.println("Apollo bundle is not found. [Name = " + m_name + "]");

        } else {
            System.out.println("---------------------Base---------------------");
            System.out.println("[Location = " + target.getLocation() + "]");
            System.out.println("[Bundle ID = " + target.getBundleId() + "]");
            System.out.println("[Symbolic Name = " + target.getSymbolicName() + "]");

            if (m_options != null) {
                if (m_options.contains("import")) {
                    System.out.println("\n---------------------Import-Package---------------------");
                    String ips = (String) target.getHeaders().get("Import-Package");
                    if (ips != null) {
                        for (String ip : ips.split("\",")) {
                            System.out.println(ip);
                        }
                    }
                }

                if (m_options.contains("export")) {
                    System.out.println("\n---------------------Export-Package---------------------");
                    String eps = (String) target.getHeaders().get("Export-Package");
                    if (eps != null) {
                        for (String ep : eps.split("\",")) {
                            System.out.println(ep);
                        }
                    }
                }

                if (m_options.contains("provide")) {
                    System.out.println("\n---------------------Provide-Capability---------------------");
                    String rcs = (String) target.getHeaders().get("Provide-Capability");
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
