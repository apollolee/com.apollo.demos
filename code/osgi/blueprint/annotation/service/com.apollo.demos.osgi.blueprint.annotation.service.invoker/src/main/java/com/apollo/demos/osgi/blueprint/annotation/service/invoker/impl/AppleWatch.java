/*
 * 此代码创建于 2015年12月24日 上午11:02:51。
 */
package com.apollo.demos.osgi.blueprint.annotation.service.invoker.impl;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.annotation.service.api.ISiri;

@Bean(id = "AppleWatch")
@ReferenceListener
public class AppleWatch {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleWatch.class);

    @Inject
    @Reference(id = "SiriService", serviceInterface = ISiri.class, timeout = 0, referenceListeners = @ReferenceListener(ref = "AppleWatch"))
    private ISiri m_siri;

    public AppleWatch() {
        s_logger.info("New.");
    }

    @Bind
    public void bindSiri(ISiri siri) {
        s_logger.info("Bind siri. [Siri = {}]", siri);
        m_siri = siri;
        s_logger.info("Say: {}", m_siri.sayHello());
    }

    @Unbind
    public void unbindSiri(ISiri siri) {
        s_logger.info("Unbind siri. [Siri = {}]", siri);
        m_siri = null;
    }

}
