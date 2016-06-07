/*
 * 此代码创建于 2016年1月4日 下午5:24:21。
 */
package com.apollo.demos.osgi.blueprint.provider3.impl;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.api.ISiri3;

@Bean(id = "Siri3Impl")
@Service(autoExport = "all-classes")
public class Siri3Impl implements ISiri3 {

    private static final Logger s_logger = LoggerFactory.getLogger(Siri3Impl.class);

    public Siri3Impl() {
        s_logger.info("New.");
    }

    @Override
    public String sayHello() {
        return "Hello, Blueprint annotation.";
    }

}
