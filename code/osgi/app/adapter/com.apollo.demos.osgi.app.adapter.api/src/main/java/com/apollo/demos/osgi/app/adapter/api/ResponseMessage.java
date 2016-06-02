/*
 * 此代码创建于 2016年5月25日 上午10:00:01。
 */
package com.apollo.demos.osgi.app.adapter.api;

import static com.apollo.demos.osgi.app.adapter.api.AdapterUtilities.showClass;

import java.io.Serializable;

public class ResponseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Serializable m_response;

    public ResponseMessage(Serializable response) {
        m_response = response;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> R getResponse() {
        return (R) m_response;
    }

    @Override
    public String toString() {
        return showClass(m_response);
    }

}
