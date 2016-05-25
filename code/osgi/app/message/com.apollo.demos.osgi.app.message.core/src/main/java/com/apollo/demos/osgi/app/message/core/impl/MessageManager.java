/*
 * 此代码创建于 2016年1月8日 下午2:47:41。
 */
package com.apollo.demos.osgi.app.message.core.impl;

import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Factory.ComputeParticle;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Property.Function;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Property.Scope;
import static com.apollo.demos.osgi.app.message.api.IMessageConstants.Property.Type;
import static org.apache.felix.scr.annotations.ReferenceCardinality.MANDATORY_MULTIPLE;
import static org.apache.felix.scr.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.ComponentConstants.COMPONENT_FACTORY;
import static org.osgi.service.component.ComponentConstants.COMPONENT_NAME;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

import com.apollo.demos.osgi.app.message.api.IComputeParticle;
import com.apollo.demos.osgi.app.message.api.IMessageContext;
import com.apollo.demos.osgi.app.message.api.IMessageManager;
import com.apollo.demos.osgi.app.message.api.ParticleMessage;
import com.apollo.demos.osgi.app.message.api.RegisterInfo;

@Component
@Service
@Reference(referenceInterface = ComponentFactory.class, target = "(" + COMPONENT_FACTORY + "=" + ComputeParticle + "*)", cardinality = MANDATORY_MULTIPLE, policy = DYNAMIC, bind = "register", unbind = "unregister")
public class MessageManager implements IMessageManager {

    static class Pair {

        ComponentFactory m_cf;

        ConcurrentMap<String, ComponentInstance> m_cis = new ConcurrentHashMap<String, ComponentInstance>();

        Pair(ComponentFactory cf) {
            m_cf = cf;
        }

    }

    private static final Logger s_logger = LoggerFactory.getLogger(MessageManager.class);

    private IMessageContext m_context = new MessageContextImpl();

    private ConcurrentMap<RegisterInfo, CopyOnWriteArrayList<Pair>> m_cpMap = new ConcurrentHashMap<RegisterInfo, CopyOnWriteArrayList<Pair>>();

    public MessageManager() {
        s_logger.info("New.");
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void postMessage(ParticleMessage message) {
        s_logger.info("Post message. [Message = {}]", message);

        RegisterInfo ri = message.getRegisterInfo();
        CopyOnWriteArrayList<Pair> cpfs = m_cpMap.get(ri);
        if (cpfs == null || cpfs.isEmpty()) {
            s_logger.warn("Compute particle factory list is not found. [Message = {}]", message);

        } else {
            String value = message.getValue();
            for (Pair cpf : cpfs) {
                ComponentInstance ci = cpf.m_cis.get(value);
                if (ci == null) {
                    ci = cpf.m_cf.newInstance(null);
                    ComponentInstance oldCi = cpf.m_cis.putIfAbsent(value, ci);
                    if (oldCi != null && oldCi != ci) {
                        ci = oldCi;
                    }
                }
                IComputeParticle cp = (IComputeParticle) ci.getInstance();
                cp.processMessage(m_context, message);
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
        String cp = (String) properties.get(COMPONENT_NAME);
        RegisterInfo ri = getRegisterInfo(cpf, properties);
        s_logger.info("Register compute particle factory. [ComputeParticle = {}] , [RegisterInfo = {}]", cp, ri);

        CopyOnWriteArrayList<Pair> cpfs = new CopyOnWriteArrayList<Pair>();
        CopyOnWriteArrayList<Pair> oldCpfs = m_cpMap.putIfAbsent(ri, cpfs);
        cpfs = oldCpfs == null ? cpfs : oldCpfs;
        cpfs.add(new Pair(cpf));
    }

    protected void unregister(ComponentFactory cpf, Map<String, Object> properties) {
        String cp = (String) properties.get(COMPONENT_NAME);
        RegisterInfo ri = getRegisterInfo(cpf, properties);
        s_logger.info("Unregister compute particle factory. [ComputeParticle = {}] , [RegisterInfo = {}]", cp, ri);

        CopyOnWriteArrayList<Pair> cpfs = m_cpMap.get(ri);
        if (cpfs == null) {
            s_logger.error("Compute particle factory list is not found. [ComputeParticle = {}] , [RegisterInfo = {}]", cp, ri);

        } else {
            for (Pair p : cpfs) {
                if (p.m_cf == cpf) {
                    s_logger.info("Remove compute particle factory. [ComputeParticle = {}] , [RegisterInfo = {}]", cp, ri);

                    for (ComponentInstance ci : p.m_cis.values()) {
                        ci.dispose();
                    }
                    cpfs.remove(p);

                    return;
                }
            }

            s_logger.error("Compute particle factory is not found. [ComputeParticle = {}] , [RegisterInfo = {}]", cp, ri);
        }
    }

    protected RegisterInfo getRegisterInfo(ComponentFactory cpf, Map<String, Object> properties) {
        String factory = (String) properties.get(COMPONENT_FACTORY);

        String type = getProperty(factory, Type);
        String scope = getProperty(factory, Scope);
        String function = getProperty(factory, Function);

        return new RegisterInfo(type, scope, function);
    }

    protected String getProperty(String factory, String property) {
        int beginIndex = factory.indexOf(property);
        beginIndex = factory.indexOf('=', beginIndex) + 1;
        int endIndex = factory.indexOf(';', beginIndex);
        endIndex = endIndex == -1 ? factory.length() : endIndex;
        return factory.substring(beginIndex, endIndex);
    }

}
