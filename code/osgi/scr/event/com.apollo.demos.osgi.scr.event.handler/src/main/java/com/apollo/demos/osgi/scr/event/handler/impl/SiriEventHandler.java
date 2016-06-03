/*
 * 此代码创建于 2015年12月29日 下午1:57:05。
 */
package com.apollo.demos.osgi.scr.event.handler.impl;

import static org.osgi.service.event.EventConstants.EVENT_TOPIC;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.event.api.SiriEvent;

@Component
@Service
@Property(name = EVENT_TOPIC, value = SiriEvent.TOPIC)
public class SiriEventHandler implements EventHandler {

    private static final Logger s_logger = LoggerFactory.getLogger(SiriEventHandler.class);

    public SiriEventHandler() {
        s_logger.info("New.");
    }

    @Override
    public void handleEvent(Event event) {
        s_logger.info("Handle Siri event. [Siri Event = {}]", event);
        SiriEvent se = (SiriEvent) event;
        s_logger.info("Siri Event Say: {}", se.sayHelloRandom());
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

}
