/*
 * 此代码创建于 2016年6月14日 上午9:55:03。
 */
package com.apollo.demos.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "pre-base")
public class PreBaseMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Do pre-base now.");
    }

}
