/*
 * 此代码创建于 2016年1月8日 下午2:47:41。
 */
package com.apollo.demos.osgi.scr.message.core.impl;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.message.api.EScope;
import com.apollo.demos.osgi.scr.message.api.EType;
import com.apollo.demos.osgi.scr.message.api.IComputeParticleCreator;
import com.apollo.demos.osgi.scr.message.api.IMessageContext;
import com.apollo.demos.osgi.scr.message.api.IMessageManager;
import com.apollo.demos.osgi.scr.message.api.ParticleMessage;
import com.apollo.demos.osgi.scr.message.api.RegisterInfo;

@Component
@Service
@Reference(referenceInterface = IComputeParticleCreator.class, cardinality = ReferenceCardinality.MANDATORY_MULTIPLE, policy = ReferencePolicy.DYNAMIC, bind = "registerComputeParticleCreator", unbind = "unregisterComputeParticleCreator")
public class MessageManager implements IMessageManager {

    private static final Logger s_logger = LoggerFactory.getLogger(MessageManager.class);

    private IMessageContext m_context = new MessageContextImpl();

    private ConcurrentMap<RegisterInfo, CopyOnWriteArrayList<IComputeParticleCreator>> m_creatorMap = new ConcurrentHashMap<RegisterInfo, CopyOnWriteArrayList<IComputeParticleCreator>>();

    public MessageManager() {
        s_logger.info("New.");
    }

    @Override
    public void postMessage(ParticleMessage message) {
        s_logger.info("Post message. [Message = {}]", message);

        RegisterInfo ri = message.getRegisterInfo();
        CopyOnWriteArrayList<IComputeParticleCreator> creators = m_creatorMap.get(ri);
        if (creators != null) {
            for (IComputeParticleCreator creator : creators) {
                creator.createParticle().processMessage(m_context, message);
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

    protected void registerComputeParticleCreator(IComputeParticleCreator creator, Map<String, Object> properties) {
        String type = (String) properties.get("type");
        String function = (String) properties.get("function");
        String scope = (String) properties.get("scope");
        s_logger.info("Register compute particle creator. [Compute Particle Creator = {}] , [Type = {}] , [Function = {}] , [Scope = {}]",
                      creator.getClass().getName(),
                      type,
                      function,
                      scope);

        RegisterInfo ri = new RegisterInfo(EType.valueOf(type), function, EScope.valueOf(scope));

        CopyOnWriteArrayList<IComputeParticleCreator> creators = new CopyOnWriteArrayList<IComputeParticleCreator>();
        CopyOnWriteArrayList<IComputeParticleCreator> oldCreators = m_creatorMap.putIfAbsent(ri, creators);
        creators = oldCreators == null ? creators : oldCreators;
        creators.add(creator);
    }

    protected void unregisterComputeParticleCreator(IComputeParticleCreator creator, Map<String, Object> properties) {
        String type = (String) properties.get("type");
        String function = (String) properties.get("function");
        String scope = (String) properties.get("scope");
        s_logger.info("Unregister compute particle creator. [Compute Particle Creator = {}] , [Type = {}] , [Function = {}] , [Scope = {}]",
                      creator.getClass().getName(),
                      type,
                      function,
                      scope);

        RegisterInfo ri = new RegisterInfo(EType.valueOf(type), function, EScope.valueOf(scope));

        CopyOnWriteArrayList<IComputeParticleCreator> creators = m_creatorMap.get(ri);
        if (creators == null || !creators.remove(creator)) {
            s_logger.error("Compute particle creator is not exist. [Compute Particle Creator = {}] , [Type = {}] , [Function = {}] , [Scope = {}]",
                           creator.getClass().getName(),
                           type,
                           function,
                           scope);
        }
    }

}
