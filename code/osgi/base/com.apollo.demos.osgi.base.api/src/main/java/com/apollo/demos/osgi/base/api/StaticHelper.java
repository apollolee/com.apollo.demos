/*
 * 此代码创建于 2017年2月23日 下午4:32:31。
 */
package com.apollo.demos.osgi.base.api;

import static com.apollo.demos.osgi.base.api.impl.Activator.getContext;
import static org.slf4j.LoggerFactory.getLogger;

import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;

@Deprecated
public final class StaticHelper {

    /**
     * 日志记录器。
     */
    private static final Logger s_logger = getLogger(StaticHelper.class);

    @Deprecated
    @SuppressWarnings("unchecked")
    public static final <T> T getOsgiSerivce(Class<T> type) {
        s_logger.trace("Get OSGi serivce.[{}]", type);
        ServiceTracker st = new ServiceTracker(getContext(), type.getName(), null);
        try {
            st.open();
            return (T) st.waitForService(Long.MAX_VALUE);

        } catch (Throwable ex) {
            s_logger.error("Get OSGi serivce is failed.", ex);
            return null;

        } finally {
            st.close();
        }
    }

    @Deprecated
    public static final IExecutors getExecutors() {
        return getOsgiSerivce(IExecutors.class);
    }

    @Deprecated
    public static final ILogHelper getLogHelper() {
        return getOsgiSerivce(ILogHelper.class);
    }

    @Deprecated
    public static final IUtilities getUtilities() {
        return getOsgiSerivce(IUtilities.class);
    }

    /**
     * 构造方法。
     */
    private StaticHelper() {
        /* 禁止从外部实例化此类 */
    }

}
