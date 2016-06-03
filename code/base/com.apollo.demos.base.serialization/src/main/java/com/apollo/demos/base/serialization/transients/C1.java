/*
 * 此代码创建于 2016年4月11日 下午2:46:43。
 */
package com.apollo.demos.base.serialization.transients;

import java.io.Serializable;

public class C1 implements Serializable {

    private static final long serialVersionUID = 1L;

    int m_int = 12345;

    transient int m_transientInt = 12345;

    String m_string = "12345";

    transient String m_transientString = "12345";

}
