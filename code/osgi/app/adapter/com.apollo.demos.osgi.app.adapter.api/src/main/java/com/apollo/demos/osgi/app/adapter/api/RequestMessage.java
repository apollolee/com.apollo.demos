/*
 * 此代码创建于 2016年5月25日 上午9:58:16。
 */
package com.apollo.demos.osgi.app.adapter.api;

import static com.apollo.demos.osgi.app.adapter.api.AdapterUtilities.showClass;

import java.io.Serializable;

public class RequestMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String m_functionId;

    private final Serializable m_request;

    public RequestMessage(String functionId, Serializable request) {
        m_functionId = functionId;
        m_request = request;
    }

    public String getFunctionId() {
        return m_functionId;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> R getRequest() {
        return (R) m_request;
    }

    @Override
    public String toString() {
        return m_functionId + "#" + showClass(m_request);
    }

}
