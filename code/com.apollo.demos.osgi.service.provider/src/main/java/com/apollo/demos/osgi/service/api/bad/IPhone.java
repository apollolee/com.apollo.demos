/*
 * 此代码创建于 2015年12月26日 下午1:44:39。
 */
package com.apollo.demos.osgi.service.api.bad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPhone {

    private static final Logger s_logger = LoggerFactory.getLogger(IPhone.class);

    public IPhone() {
        s_logger.info("New.");
    }

    public String getDescription() {
        return "I am an IPhone 6s Plus 16GB.";
    }

}
