/*
 * 此代码创建于 2015年12月29日 下午2:04:23。
 */
package com.apollo.demos.osgi.scr.event.publisher.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.event.api.SiriEvent;

@Component
public class AppleEventPublisher {

    private static final Logger s_logger = LoggerFactory.getLogger(AppleEventPublisher.class);

    @Reference(bind = "bindEventAdmin", unbind = "unbindEventAdmin")
    private EventAdmin m_eventAdmin;

    private ExecutorService m_es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    public AppleEventPublisher() {
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

    protected void bindEventAdmin(EventAdmin eventAdmin) {
        s_logger.info("Bind EventAdmin.");
        m_eventAdmin = eventAdmin;
        m_es.execute(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    Event evt = new SiriEvent();
                    s_logger.info("Post Siri event. [Siri Event = {}]", evt);
                    m_eventAdmin.postEvent(evt);

                    Map<String, Object> properties = new HashMap<String, Object>();
                    properties.put("iphone.version", "IPhone 6s Plus 16GB");
                    evt = new Event("com/apollo/demos/osgi/scr/event/api/IPhoneEvent", properties);
                    s_logger.info("Post IPhone event. [IPhone Event = {}]", evt);
                    m_eventAdmin.postEvent(evt);

                    Thread.yield();

                    try {
                        Thread.sleep(5000);

                    } catch (InterruptedException ex) {
                        s_logger.info("Post siri event is interrupted.");
                        break;
                    }
                }
            }

        });
    }

    protected void unbindEventAdmin(EventAdmin eventAdmin) {
        s_logger.info("Unbind EventAdmin.");

        m_es.shutdownNow();

        try {
            m_es.awaitTermination(10, TimeUnit.SECONDS);

        } catch (InterruptedException ex) {
            s_logger.error("Await termination is failed.", ex);
        }

        m_eventAdmin = null;
    }

}
