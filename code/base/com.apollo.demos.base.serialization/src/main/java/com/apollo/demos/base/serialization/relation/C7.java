/*
 * 此代码创建于 2016年4月11日 下午2:46:43。
 */
package com.apollo.demos.base.serialization.relation;

import java.io.Serializable;

public class C7 implements Serializable {

    private static final long serialVersionUID = 1L;

    C5 m_c5;

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof C7) {
            if (this == obj) {
                return true;

            } else {
                C7 o = (C7) obj;
                return m_c5.equals(o.m_c5);
            }

        } else {
            return false;
        }
    }

}
