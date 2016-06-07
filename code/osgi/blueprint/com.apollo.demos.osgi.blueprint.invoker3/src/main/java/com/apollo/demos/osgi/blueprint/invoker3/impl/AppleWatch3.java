/*
 * 此代码创建于 2015年12月24日 上午11:02:51。
 */
package com.apollo.demos.osgi.blueprint.invoker3.impl;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.api.ISiri3;

@Bean(id = "AppleWatch3")
@ReferenceListener
public class AppleWatch3 {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleWatch3.class);

    @Inject
    @Reference(id = "Siri3", serviceInterface = ISiri3.class, timeout = 0, referenceListeners = @ReferenceListener(ref = "AppleWatch3"))
    private ISiri3 m_siri3;

    public AppleWatch3() {
        s_logger.info("New.");
    }

    @Bind
    public void bindSiri3(ISiri3 siri3) {
        s_logger.info("Bind siri3. [Siri3 = {}]", siri3);
        m_siri3 = siri3;
        s_logger.info("Say: {}", m_siri3.sayHello());
    }

    @Unbind
    public void unbindSiri3(ISiri3 siri3) {
        s_logger.info("Unbind siri3. [Siri3 = {}]", siri3);
        m_siri3 = null;
    }

}
