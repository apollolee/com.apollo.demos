/*
 * 此代码创建于 2016年1月4日 下午5:24:21。
 */
package com.apollo.demos.osgi.blueprint.provider1.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.api.ISiri1;

public class Siri1Impl implements ISiri1 {

    private static final Logger s_logger = LoggerFactory.getLogger(Siri1Impl.class);

    public Siri1Impl() {
        s_logger.info("New.");
    }

    @Override
    public String sayHello() {
        return "Hello, Blueprint.";
    }

}
