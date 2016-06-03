/*
 * 此代码创建于 2016年4月11日 下午2:46:43。
 */
package com.apollo.demos.base.serialization.hash;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class M implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, M> m_map = new HashMap<String, M>();

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof M) {
            if (this == obj) {
                return true;

            } else {
                M o = (M) obj;
                return m_map.equals(o.m_map);
            }

        } else {
            return false;
        }
    }

}
