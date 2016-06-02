/*
 * 此代码创建于 2016年6月2日 上午10:12:38。
 */
package com.apollo.demos.osgi.app.message.api;

public final class MessageUtilities implements IMessageConstants {

    public static final String showClass(Object object) {
        return object == null ? "null" : object.getClass().getName();
    }

    public static final String showId(Object object) {
        return "0x" + Integer.toHexString(System.identityHashCode(object));
    }

    /**
     * 构造方法。
     */
    private MessageUtilities() {
        /* 禁止从外部实例化此类 */
    }

}
