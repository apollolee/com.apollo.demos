/*
 * 此代码创建于 2015年12月24日 上午11:02:51。
 */
package com.apollo.demos.osgi.blueprint.generate.service.invoker.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.ops4j.pax.cdi.api.OsgiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.generate.service.api.ISiri;

@Singleton
public class AppleWatch {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleWatch.class);

    @Inject
    @OsgiService(timeout = 0)
    private ISiri siri;

    public AppleWatch() {
        s_logger.info("New.");
    }

    public void setSiri(ISiri siri) {
        s_logger.info("Set siri. [Siri = {}]", siri);
        this.siri = siri;
        s_logger.info("Say: {}", siri.sayHello());
    }

}
