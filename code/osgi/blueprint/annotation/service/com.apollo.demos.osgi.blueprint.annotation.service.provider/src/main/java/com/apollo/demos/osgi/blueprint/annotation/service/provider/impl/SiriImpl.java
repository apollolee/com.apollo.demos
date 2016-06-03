/*
 * 此代码创建于 2016年1月4日 下午5:24:21。
 */
package com.apollo.demos.osgi.blueprint.annotation.service.provider.impl;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.annotation.service.api.ISiri;

@Bean(id = "SiriImpl")
@Service(autoExport = "all-classes")
public class SiriImpl implements ISiri {

    private static final Logger s_logger = LoggerFactory.getLogger(SiriImpl.class);

    public SiriImpl() {
        s_logger.info("New.");
    }

    @Override
    public String sayHello() {
        return "Hello, Blueprint annotation.";
    }

}
