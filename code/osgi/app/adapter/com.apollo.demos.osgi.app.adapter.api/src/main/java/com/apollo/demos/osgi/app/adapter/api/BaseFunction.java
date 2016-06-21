/*
 * 此代码创建于 2016年5月20日 下午4:31:35。
 */
package com.apollo.demos.osgi.app.adapter.api;

import org.osgi.service.component.ComponentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseFunction implements IFunction {

    private static final Logger s_logger = LoggerFactory.getLogger(BaseFunction.class);

    @Override
    public ComponentFactory[] getProcessors() {
        String msg = "This method must be overridden or use adapter-maven-plugin.generateFunction goal. [Class=" + getClass().getName() + "].";
        s_logger.error(msg);
        throw new IllegalStateException(msg);
    }

}
