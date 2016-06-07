/*
 * 此代码创建于 2015年12月24日 上午11:02:51。
 */
package com.apollo.demos.osgi.blueprint.invoker2.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.ops4j.pax.cdi.api.OsgiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.blueprint.api.ISiri2;

@Singleton
public class AppleWatch2 {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleWatch2.class);

    @Inject
    @OsgiService(timeout = 0)
    private ISiri2 siri2;

    public AppleWatch2() {
        s_logger.info("New.");
    }

    public void setSiri2(ISiri2 siri2) {
        s_logger.info("Set siri2. [Siri2 = {}]", siri2);
        this.siri2 = siri2;
        s_logger.info("Say: {}", siri2.sayHello());
    }

}
