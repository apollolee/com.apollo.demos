/*
 * 此代码创建于 2016年6月2日 上午10:55:06。
 */
package com.apollo.demos.osgi.app.adapter.api;

public final class AdapterUtilities implements IAdapterConstants {

    public static final String showClass(Object object) {
        return object == null ? "null" : object.getClass().getName();
    }

    public static final String showId(Object object) {
        return object == null ? "null" : ("0x" + Integer.toHexString(System.identityHashCode(object)));
    }

    /**
     * 构造方法。
     */
    private AdapterUtilities() {
        /* 禁止从外部实例化此类 */
    }

}
