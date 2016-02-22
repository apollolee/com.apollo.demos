/*
 * 此代码创建于 2016年1月8日 下午2:47:41。
 */
package com.apollo.demos.osgi.app.message.core.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.app.message.api.ComputeParticle;
import com.apollo.demos.osgi.app.message.api.EScope;
import com.apollo.demos.osgi.app.message.api.EType;
import com.apollo.demos.osgi.app.message.api.IComputeParticle;
import com.apollo.demos.osgi.app.message.api.IMessageContext;
import com.apollo.demos.osgi.app.message.api.IMessageManager;
import com.apollo.demos.osgi.app.message.api.ParticleMessage;
import com.apollo.demos.osgi.app.message.api.RegisterInfo;

@Component
@Service
@Reference(referenceInterface = ComponentFactory.class, target = "(component.factory=ComputeParticle)", cardinality = ReferenceCardinality.MANDATORY_MULTIPLE, policy = ReferencePolicy.DYNAMIC, bind = "register", unbind = "unregister")
public class MessageManager implements IMessageManager {

    private static final Logger s_logger = LoggerFactory.getLogger(MessageManager.class);

    private IMessageContext m_context = new MessageContextImpl();

    private ConcurrentMap<RegisterInfo, CopyOnWriteArrayList<ComponentFactory>> m_computeParticleFactoryMap = new ConcurrentHashMap<RegisterInfo, CopyOnWriteArrayList<ComponentFactory>>();

    public MessageManager() {
        s_logger.info("New.");
    }

    @Override
    public void postMessage(ParticleMessage message) {
        s_logger.info("Post message. [Message = {}]", message);

        RegisterInfo ri = message.getRegisterInfo();
        CopyOnWriteArrayList<ComponentFactory> cpfs = m_computeParticleFactoryMap.get(ri);
        if (cpfs != null) {
            for (ComponentFactory cpf : cpfs) {
                ComponentInstance ci = cpf.newInstance(null);
                IComputeParticle cp = (IComputeParticle) ci.getInstance();
                cp.processMessage(m_context, message);
                ci.dispose();
            }
        }
    }

    @Activate
    protected void activate(ComponentContext context) {
        s_logger.info("Activate.");
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        s_logger.info("Deactivate.");
    }

    @Modified
    protected void modified(ComponentContext context) {
        s_logger.info("Modified.");
    }

    protected void register(ComponentFactory cpf, Map<String, Object> properties) {
        String cp = (String) properties.get("component.name");
        RegisterInfo ri = getRegisterInfo(cpf, properties);
        s_logger.info("Register compute particle factory. [ComputeParticle = {}] , [RegisterInfo = {}]", cp, ri);

        CopyOnWriteArrayList<ComponentFactory> cpfs = new CopyOnWriteArrayList<ComponentFactory>();
        CopyOnWriteArrayList<ComponentFactory> oldCpfs = m_computeParticleFactoryMap.putIfAbsent(ri, cpfs);
        cpfs = oldCpfs == null ? cpfs : oldCpfs;
        cpfs.add(cpf);
    }

    protected void unregister(ComponentFactory cpf, Map<String, Object> properties) {
        String cp = (String) properties.get("component.name");
        RegisterInfo ri = getRegisterInfo(cpf, properties);
        s_logger.info("Unregister compute particle factory. [ComputeParticle = {}] , [RegisterInfo = {}]", cp, ri);

        CopyOnWriteArrayList<ComponentFactory> cpfs = m_computeParticleFactoryMap.get(ri);
        if (cpfs == null || !cpfs.remove(cpf)) {
            s_logger.error("Compute particle factory is not exist. [ComputeParticle = {}] , [RegisterInfo = {}]", cp, ri);
        }
    }

    protected RegisterInfo getRegisterInfo(ComponentFactory cpf, Map<String, Object> properties) {
        EType type = null;
        String function = null;
        EScope scope = null;

        Class<?> cpc = cpf.newInstance(null).getInstance().getClass();

        if (cpc.isAnnotationPresent(ComputeParticle.class)) {
            ComputeParticle cp = cpc.getAnnotation(ComputeParticle.class);
            type = cp.type();
            function = cp.function();
            scope = cp.scope();
        }

        type = type == null ? EType.valueOf((String) properties.get("type")) : type;
        function = function == null ? (String) properties.get("function") : function;
        scope = scope == null ? EScope.valueOf((String) properties.get("scope")) : scope;

        return new RegisterInfo(type, function, scope);
    }

}
