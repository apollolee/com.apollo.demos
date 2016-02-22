/*
 * 此代码创建于 2016年1月8日 下午5:14:19。
 */
package com.apollo.demos.osgi.app.message.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterInfo {

    private static final Logger s_logger = LoggerFactory.getLogger(RegisterInfo.class);

    public EType m_type;

    public String m_function;

    public EScope m_scope;

    public RegisterInfo(EType type, String function, EScope scope) {
        s_logger.info("New. [Type = {}] , [Function = {}] , [Scope = {}]", type, function, scope);

        m_type = type;
        m_function = function;
        m_scope = scope;
    }

    public EType getType() {
        return m_type;
    }

    public String getFunction() {
        return m_function;
    }

    public EScope getScope() {
        return m_scope;
    }

    @Override
    public int hashCode() {
        int hashCode = 17;

        hashCode = hashCode * 37 + m_type.hashCode();
        hashCode = hashCode * 37 + m_function.hashCode();
        hashCode = hashCode * 37 + m_scope.hashCode();

        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof RegisterInfo) {
            RegisterInfo o = (RegisterInfo) obj;
            return m_type == o.m_type && m_function.equals(o.m_function) && m_scope == o.m_scope;

        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[Type = " + m_type + "] , [Function = " + m_function + "] , [Scope = " + m_scope + "]";
    }

}
