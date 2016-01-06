/*
 * 此代码创建于 2016年1月4日 下午5:24:21。
 */
package com.apollo.demos.osgi.blueprint.service.provider.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.service.api.ISiri;

public class SiriImpl implements ISiri {

    private static final Logger s_logger = LoggerFactory.getLogger(SiriImpl.class);

    public SiriImpl() {
        s_logger.info("New.");
    }

    @Override
    public String sayHello() {
        return "Hello, Blueprint.";
    }

}
