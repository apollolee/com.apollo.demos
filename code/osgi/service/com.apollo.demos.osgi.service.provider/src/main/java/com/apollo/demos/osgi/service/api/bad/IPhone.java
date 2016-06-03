/*
 * 此代码创建于 2015年12月26日 下午1:44:39。
 */
package com.apollo.demos.osgi.service.api.bad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPhone {

    private static final Logger s_logger = LoggerFactory.getLogger(IPhone.class);

    public static final String ID = "1.0.0";

    public IPhone() {
        s_logger.info("New.");
    }

    public String sayHello() {
        return "Hello, I am an IPhone 6s Plus 16GB. [ID = " + ID + "]"; //这里的ID始终都是老的，这里ID是否进行了编译优化都不影响结果，因为sayHello方法就是老的。
    }

}
