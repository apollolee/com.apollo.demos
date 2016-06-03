/*
 * 此代码创建于 2016年4月11日 下午2:46:43。
 */
package com.apollo.demos.base.serialization.relation;

import java.io.Serializable;

public class C6 implements Serializable {

    private static final long serialVersionUID = 1L;

    C7 m_c7;

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof C6) {
            if (this == obj) {
                return true;

            } else {
                C6 o = (C6) obj;
                return m_c7.equals(o.m_c7);
            }

        } else {
            return false;
        }
    }

}
