/*
 * 此代码创建于 2015年12月24日 上午11:02:51。
 */
package com.apollo.demos.osgi.blueprint.invoker1.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.api.ISiri1;

public class AppleWatch1 {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleWatch1.class);

    private ISiri1 m_siri1;

    public AppleWatch1() {
        s_logger.info("New.");
    }

    public void bindSiri1(ISiri1 siri1) {
        s_logger.info("Bind siri1. [Siri1 = {}]", siri1);
        m_siri1 = siri1;
        s_logger.info("Say: {}", m_siri1.sayHello());
    }

    public void unbindSiri1(ISiri1 siri1) {
        s_logger.info("Unbind siri1. [Siri1 = {}]", siri1);
        m_siri1 = null;
    }

}
