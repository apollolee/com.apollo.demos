/*
 * 此代码创建于 2016年1月4日 下午5:24:21。
 */
package com.apollo.demos.osgi.blueprint.provider2.impl;

import javax.inject.Singleton;

import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.api.ISiri2;

@Singleton
@OsgiServiceProvider(classes = ISiri2.class)
public class Siri2Impl implements ISiri2 {

    private static final Logger s_logger = LoggerFactory.getLogger(Siri2Impl.class);

    public Siri2Impl() {
        s_logger.info("New.");
    }

    @Override
    public String sayHello() {
        return "Hello, Blueprint generate.";
    }

}
