/*
 * 此代码创建于 2016年5月25日 下午1:25:40。
 */
package com.apollo.demos.osgi.app.adapter.core.impl;

import static com.apollo.demos.osgi.app.adapter.api.AdapterUtilities.showId;
import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Function.Adapter;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Factory.ComputeParticle;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Property.Function;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Property.Scope;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Property.Type;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Scope.CNode;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Type.NE;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.app.adapter.api.IAdapter;
import com.apollo.demos.osgi.app.adapter.api.IFunction;
import com.apollo.demos.osgi.app.adapter.api.IProcessor;
import com.apollo.demos.osgi.app.adapter.api.ProcessData;
import com.apollo.demos.osgi.app.adapter.api.RequestMessage;
import com.apollo.demos.osgi.app.message.api.IComputeParticle;
import com.apollo.demos.osgi.app.message.api.IMessageContext;
import com.apollo.demos.osgi.app.message.api.ParticleMessage;

@Component(factory = ComputeParticle + ":" + Type + "=" + NE + ";" + Scope + "=" + CNode + ";" + Function + "=" + Adapter)
@Service
public class ComputeParticleImpl implements IComputeParticle<RequestMessage> {

    private static final Logger s_logger = LoggerFactory.getLogger(ComputeParticleImpl.class);

    @Reference
    private volatile IAdapter m_adapter;

    public ComputeParticleImpl() {
        s_logger.trace("New. [{}]", showId(this));
    }

    @Override
    public void processMessage(IMessageContext context, ParticleMessage<RequestMessage> message) {
        s_logger.debug("Start process message. [{}#{}#{}]", showId(this), context, message);

        IAdapter adapter = m_adapter;
        if (adapter == null) {
            s_logger.warn("Adapter is not found. [{}#{}#{}]", showId(this), context, message);

        } else {
            String deviceId = message.getValue();
            String deviceType = getDeviceType(deviceId);
            String version = getVersion(deviceId);

            RequestMessage request = message.getMessage();
            String functionId = request.getFunctionId();

            IFunction function = adapter.getFunction(functionId, deviceType, version);
            if (function == null) {
                s_logger.error("Function is not found. [{}#{}#{}#{}]", showId(this), functionId, deviceType, version);

            } else {
                ProcessData processData = new ProcessData(functionId, deviceType, version, deviceId, request.getRequest());

                for (ComponentFactory processor : function.getProcessors()) {
                    ComponentInstance ci = processor.newInstance(null);
                    try {
                        ((IProcessor) ci.getInstance()).execute(processData);

                    } catch (Exception ex) {
                        s_logger.error("Execute processor is failed.", ex);
                        return;

                    } finally {
                        ci.dispose();
                    }
                }

                s_logger.info("Process message is successful. [{}#{}#{}#{}]", showId(this), context, message, processData.getResponse());
            }
        }

        s_logger.debug("End process message. [{}#{}#{}]", showId(this), context, message);
    }

    @Activate
    protected void activate(ComponentContext context) {
        s_logger.trace("Activate. [{}]", showId(this));
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        s_logger.trace("Deactivate. [{}]", showId(this));
    }

    @Modified
    protected void modified(ComponentContext context) {
        s_logger.trace("Modified. [{}]", showId(this));
    }

    private String getDeviceType(String deviceId) {
        switch (deviceId) {
        case "1":
        case "2":
        case "3":
            return "ZXCTN_6120";

        case "4":
        default:
            return "ZXCTN_6150";
        }
    }

    private String getVersion(String deviceId) {
        switch (deviceId) {
        case "1":
        case "4":
            return "V1.00/V2.20";

        case "2":
            return "V2.00/V2.20";

        case "3":
        default:
            return "V2.01/V2.20";
        }
    }

}
