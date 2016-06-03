/*
 * 此代码创建于 2016年4月11日 下午2:46:43。
 */
package com.apollo.demos.base.serialization.hash;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class S implements Serializable {

    private static final long serialVersionUID = 1L;

    String m_id;

    Set<S> m_set = new HashSet<S>();

    public S(String id) {
        m_id = id;
    }

    @Override
    public int hashCode() {
        return m_id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof S) {
            if (this == obj) {
                return true;

            } else {
                S o = (S) obj;
                return m_id.equals(o.m_id) && m_set.equals(o.m_set);
            }

        } else {
            return false;
        }
    }

}
