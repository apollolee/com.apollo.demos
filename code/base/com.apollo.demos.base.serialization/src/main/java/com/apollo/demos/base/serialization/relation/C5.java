/*
 * 此代码创建于 2016年4月11日 下午2:46:43。
 */
package com.apollo.demos.base.serialization.relation;

import java.io.Serializable;

public class C5 implements Serializable {

    private static final long serialVersionUID = 1L;

    C6 m_c6;

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof C5) {
            if (this == obj) {
                return true;

            } else {
                C5 o = (C5) obj;
                return m_c6.equals(o.m_c6);
            }

        } else {
            return false;
        }
    }

}
