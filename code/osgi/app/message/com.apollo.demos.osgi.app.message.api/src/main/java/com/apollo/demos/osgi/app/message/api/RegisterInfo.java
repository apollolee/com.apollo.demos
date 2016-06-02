/*
 * 此代码创建于 2016年1月8日 下午5:14:19。
 */
package com.apollo.demos.osgi.app.message.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterInfo {

    private static final Logger s_logger = LoggerFactory.getLogger(RegisterInfo.class);

    public String m_type;

    public String m_scope;

    public String m_function;

    public RegisterInfo(String type, String scope, String function) {
        s_logger.trace("New. [{}#{}#{}]", type, scope, function);

        m_type = type;
        m_scope = scope;
        m_function = function;
    }

    public String getType() {
        return m_type;
    }

    public String getScope() {
        return m_scope;
    }

    public String getFunction() {
        return m_function;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;

        hashCode = hashCode * 37 + m_type.hashCode();
        hashCode = hashCode * 37 + m_scope.hashCode();
        hashCode = hashCode * 37 + m_function.hashCode();

        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof RegisterInfo) {
            RegisterInfo o = (RegisterInfo) obj;
            return m_type.endsWith(o.m_type) && m_scope.endsWith(o.m_scope) && m_function.equals(o.m_function);

        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return m_type + "#" + m_scope + "#" + m_function;
    }

}
