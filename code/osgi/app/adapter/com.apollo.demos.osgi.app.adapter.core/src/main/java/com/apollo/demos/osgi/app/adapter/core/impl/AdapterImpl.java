/*
 * 此代码创建于 2016年5月20日 下午4:31:35。
 */
package com.apollo.demos.osgi.app.adapter.core.impl;

import static com.apollo.demos.osgi.app.adapter.api.AdapterUtilities.showClass;
import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Property.DeviceType;
import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Property.FunctionId;
import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Property.Version;
import static org.apache.felix.scr.annotations.ReferenceCardinality.OPTIONAL_MULTIPLE;
import static org.apache.felix.scr.annotations.ReferencePolicy.DYNAMIC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.app.adapter.api.IAdapter;
import com.apollo.demos.osgi.app.adapter.api.IFunction;

@Component(immediate = true)
@Service
@Reference(referenceInterface = IFunction.class, cardinality = OPTIONAL_MULTIPLE, policy = DYNAMIC, bind = "register", unbind = "unregister")
public class AdapterImpl implements IAdapter {

    private static final Logger s_logger = LoggerFactory.getLogger(AdapterImpl.class);

    private ConcurrentMap<String, IFunction> m_functions = new ConcurrentHashMap<String, IFunction>();

    public AdapterImpl() {
        s_logger.trace("New.");
    }

    @Override
    public IFunction getFunction(String functionId, String deviceType, String version) {
        if (functionId == null || functionId.isEmpty()) {
            s_logger.warn("Get function is failed. Function ID is illegal. [{}]", functionId);
            return null;
        }

        if (deviceType == null || deviceType.isEmpty()) {
            s_logger.warn("Get function is failed. Device type is illegal. [{}]", deviceType);
            return null;
        }

        if (version == null || version.isEmpty()) {
            s_logger.warn("Get function is failed. Version is illegal. [{}]", version);
            return null;
        }

        String key = functionId + "#" + deviceType + "#" + version;
        s_logger.trace("Get function. [{}]", key);
        IFunction function = m_functions.get(key);

        if (function == null) {
            s_logger.warn("Get function is failed. function is not found. [{}]", key);
        }

        return function;
    }

    @Activate
    protected void activate(ComponentContext context) {
        s_logger.trace("Activate.");
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        s_logger.trace("Deactivate.");
    }

    @Modified
    protected void modified(ComponentContext context) {
        s_logger.trace("Modified.");
    }

    protected void register(IFunction function, Map<String, Object> properties) {
        String functionId = (String) properties.get(FunctionId);
        if (functionId == null || functionId.isEmpty()) {
            s_logger.warn("Register function is failed. Function ID is illegal. [{}#{}]", showClass(function), functionId);
            return;
        }

        String deviceType = (String) properties.get(DeviceType);
        if (deviceType == null || deviceType.isEmpty()) {
            s_logger.warn("Register function is failed. Device type is illegal. [{}#{}]", showClass(function), deviceType);
            return;
        }

        String version = (String) properties.get(Version);
        if (version == null || version.isEmpty()) {
            s_logger.warn("Register function is failed. Version is illegal. [{}#{}]", showClass(function), version);
            return;
        }

        String key = functionId + "#" + deviceType + "#" + version;
        s_logger.trace("Put function. [{}#{}]", key, showClass(function));
        IFunction oldFunction = m_functions.putIfAbsent(key, function);
        if (oldFunction == null) {
            s_logger.debug("Register function is successful. [{}#{}]", key, showClass(function));

        } else {
            s_logger.warn("Register function is failed. [{}#{}#{}]", key, showClass(function), showClass(oldFunction));
        }
    }

    protected void unregister(IFunction function, Map<String, Object> properties) {
        String functionId = (String) properties.get(FunctionId);
        if (functionId == null || functionId.isEmpty()) {
            s_logger.error("Unregister function is failed. Function ID is illegal. [{}#{}]", showClass(function), functionId);
            return;
        }

        String deviceType = (String) properties.get(DeviceType);
        if (deviceType == null || deviceType.isEmpty()) {
            s_logger.error("Unregister function is failed. Device type is illegal. [{}#{}]", showClass(function), deviceType);
            return;
        }

        String version = (String) properties.get(Version);
        if (version == null || version.isEmpty()) {
            s_logger.error("Unregister function is failed. Version is illegal. [{}#{}]", showClass(function), version);
            return;
        }

        String key = functionId + "#" + deviceType + "#" + version;
        s_logger.trace("Remove function. [{}#{}]", key, showClass(function));
        if (m_functions.remove(key, function)) {
            s_logger.debug("Unregister function is successful. [{}#{}]", key, showClass(function));

        } else {
            s_logger.error("Unregister function is failed. [{}#{}#{}]", key, showClass(function), m_functions.containsKey(key));
        }
    }

}
