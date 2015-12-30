/*
 * 此代码创建于 2015年8月28日 下午3:14:18。
 */
package com.apollo.demos.osgi.scr.event.api;

import java.util.Map;
import java.util.Random;

import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiriEvent extends Event {

    public static final String TOPIC = "com/apollo/demos/osgi/scr/event/api/SiriEvent";

    private static final Logger s_logger = LoggerFactory.getLogger(SiriEvent.class);

    private int m_random = new Random().nextInt();

    public SiriEvent() {
        super(TOPIC, (Map<String, Object>) null);

        s_logger.info("New.");
    }

    @Override
    public String toString() {
        return "[Random = " + m_random + "]";
    }

    public String sayHelloRandom() {
        return "Hello, Random " + m_random + ".";
    }

}
