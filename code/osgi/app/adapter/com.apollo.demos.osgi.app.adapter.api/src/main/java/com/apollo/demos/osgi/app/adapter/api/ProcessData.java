package com.apollo.demos.osgi.app.adapter.api;

import static com.apollo.demos.osgi.app.adapter.api.AdapterUtilities.showClass;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProcessData {

    private final String m_functionId;

    private final String m_deviceType;

    private final String m_version;

    private final String m_deviceId;

    private final Serializable m_request;

    private Serializable m_response;

    private final Map<String, Object> m_data = new HashMap<String, Object>();

    public ProcessData(String functionId, String deviceType, String version, String deviceId, Serializable request) {
        m_functionId = functionId;
        m_deviceType = deviceType;
        m_version = version;
        m_deviceId = deviceId;
        m_request = request;
    }

    public String getFunctionId() {
        return m_functionId;
    }

    public String getDeviceType() {
        return m_deviceType;
    }

    public String getVersion() {
        return m_version;
    }

    public String getDeviceId() {
        return m_deviceId;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> R getRequest() {
        return (R) m_request;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> R getResponse() {
        return (R) m_response;
    }

    @SuppressWarnings("unchecked")
    public <R extends Serializable> R setResponse(R response) {
        R old = (R) m_response;
        m_response = response;
        return old;
    }

    @SuppressWarnings("unchecked")
    public <V> V put(String key, V value) {
        return (V) m_data.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <V> V get(String key) {
        return (V) m_data.get(key);
    }

    @Override
    public String toString() {
        return m_functionId + "#" + m_deviceType + "#" + m_version + "#" + m_deviceId + "#" + showClass(m_request) + "#" + showClass(m_response) + "#" + m_data.size();
    }

}
