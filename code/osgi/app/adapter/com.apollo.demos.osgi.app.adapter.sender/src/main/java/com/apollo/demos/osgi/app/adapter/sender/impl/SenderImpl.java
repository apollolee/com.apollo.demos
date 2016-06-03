/*
 * 此代码创建于 2016年1月8日 下午4:55:26。
 */
package com.apollo.demos.osgi.app.adapter.sender.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.app.adapter.api.IAdapterConstants;
import com.apollo.demos.osgi.app.adapter.api.RequestMessage;
import com.apollo.demos.osgi.app.message.api.IMessageConstants.Scope;
import com.apollo.demos.osgi.app.message.api.IMessageConstants.Type;
import com.apollo.demos.osgi.app.message.api.IMessageManager;
import com.apollo.demos.osgi.app.message.api.ParticleMessage;
import com.apollo.demos.osgi.app.message.api.RegisterInfo;

@Component
public class SenderImpl {

    private static final Logger s_logger = LoggerFactory.getLogger(SenderImpl.class);

    @Reference(bind = "bindMessageManager", unbind = "unbindMessageManager")
    private IMessageManager m_messageManager;

    private ExecutorService m_es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    public SenderImpl() {
        s_logger.trace("New.");
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

    protected void bindMessageManager(IMessageManager messageManager) {
        s_logger.trace("Bind MessageManager.");
        m_messageManager = messageManager;
        m_es.execute(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    RegisterInfo ri = new RegisterInfo(Type.NE, Scope.CNode, IAdapterConstants.Function.Adapter);
                    RequestMessage rm = new RequestMessage(getFunctionId(), "Hello, adapter!");
                    ParticleMessage<RequestMessage> pm = new ParticleMessage<RequestMessage>(ri, getDeviceId(), rm);
                    s_logger.info("Post message. [{}]", pm);
                    m_messageManager.postMessage(pm);

                    Thread.yield();

                    try {
                        Thread.sleep(5000);

                    } catch (InterruptedException ex) {
                        s_logger.debug("Post message is interrupted.");
                        break;
                    }
                }
            }

        });
    }

    protected void unbindMessageManager(IMessageManager messageManager) {
        s_logger.trace("Unbind MessageManager.");

        m_es.shutdownNow();

        try {
            m_es.awaitTermination(10, TimeUnit.SECONDS);

        } catch (InterruptedException ex) {
            s_logger.error("Await termination is failed.", ex);
        }

        m_messageManager = null;
    }

    private String getDeviceId() {
        return String.valueOf((int) (Math.random() * 100) % 4 + 1);
    }

    private String getFunctionId() {
        switch ((int) (Math.random() * 100) % 2) {
        case 0:
            return "80001";

        case 1:
        default:
            return "80002";
        }
    }

}
