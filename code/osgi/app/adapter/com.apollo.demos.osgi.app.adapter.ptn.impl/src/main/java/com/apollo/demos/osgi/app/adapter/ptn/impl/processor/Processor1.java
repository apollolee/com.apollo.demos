/*
 * 此代码创建于 2016年2月19日 下午2:08:19。
 */
package com.apollo.demos.osgi.app.adapter.ptn.impl.processor;

import static com.apollo.demos.osgi.app.adapter.api.AdapterUtilities.showId;
import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Factory.Processor;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.app.adapter.api.IProcessor;
import com.apollo.demos.osgi.app.adapter.api.ProcessData;

@Component(factory = Processor)
@Service
public class Processor1 implements IProcessor {

    private static final Logger s_logger = LoggerFactory.getLogger(Processor1.class);

    @Override
    public void execute(ProcessData processData) {
        s_logger.info("Execute. [{}#{}]", showId(this), processData);

        String request = processData.getRequest();
        String temp = request + " ; Processor1";
        processData.put("temp", temp);
    }

}
