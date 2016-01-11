/*
 * 此代码创建于 2016年1月8日 下午4:55:26。
 */
package com.apollo.demos.osgi.scr.message.sender2.impl;

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

import com.apollo.demos.osgi.scr.message.api.EScope;
import com.apollo.demos.osgi.scr.message.api.EType;
import com.apollo.demos.osgi.scr.message.api.IMessageManager;
import com.apollo.demos.osgi.scr.message.api.ParticleMessage;
import com.apollo.demos.osgi.scr.message.api.RegisterInfo;

@Component
public class SenderImpl {

    private static final Logger s_logger = LoggerFactory.getLogger(SenderImpl.class);

    @Reference(bind = "bindMessageManager", unbind = "unbindMessageManager")
    private IMessageManager m_messageManager;

    private ExecutorService m_es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    public SenderImpl() {
        s_logger.info("New.");
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

    protected void bindMessageManager(IMessageManager messageManager) {
        s_logger.info("Bind MessageManager.");
        m_messageManager = messageManager;
        m_es.execute(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    RegisterInfo ri = new RegisterInfo(EType.NE, "CreatePw", EScope.CNode);
                    ParticleMessage message = new ParticleMessage(ri, "1", "Please create a PW.");
                    s_logger.info("Post message. [Message = {}]", message);
                    m_messageManager.postMessage(message);

                    Thread.yield();

                    try {
                        Thread.sleep(5000);

                    } catch (InterruptedException ex) {
                        s_logger.info("Post message is interrupted.");
                        break;
                    }
                }
            }

        });
    }

    protected void unbindMessageManager(IMessageManager messageManager) {
        s_logger.info("Unbind MessageManager.");

        m_es.shutdownNow();

        try {
            m_es.awaitTermination(10, TimeUnit.SECONDS);

        } catch (InterruptedException ex) {
            s_logger.error("Await termination is failed.", ex);
        }

        m_messageManager = null;
    }

}
