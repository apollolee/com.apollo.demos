/*
 * 此代码创建于 2015年12月24日 上午11:02:51。
 */
package com.apollo.demos.osgi.blueprint.service.invoker.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.service.api.ISiri;

public class AppleWatch {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleWatch.class);

    private ISiri m_siri;

    public AppleWatch() {
        s_logger.info("New.");
    }

    public void bindSiri(ISiri siri) {
        s_logger.info("Bind siri. [Siri = {}]", siri);
        m_siri = siri;
        s_logger.info("Say: {}", m_siri.sayHello());
    }

    public void unbindSiri(ISiri siri) {
        s_logger.info("Unbind siri. [Siri = {}]", siri);
        m_siri = null;
    }

}
