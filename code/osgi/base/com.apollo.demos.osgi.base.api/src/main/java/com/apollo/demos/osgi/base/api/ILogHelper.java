/*
 * 此代码创建于 2017年2月4日 上午9:05:42。
 */
package com.apollo.demos.osgi.base.api;

import org.slf4j.Logger;

public interface ILogHelper {

    void trace(Logger logger, String msg, Object... arguments);

    void debug(Logger logger, String msg, Object... arguments);

    void info(Logger logger, String msg, Object... arguments);

    void warn(Logger logger, String msg, Object... arguments);

    void error(Logger logger, String msg, Object... arguments);

}
