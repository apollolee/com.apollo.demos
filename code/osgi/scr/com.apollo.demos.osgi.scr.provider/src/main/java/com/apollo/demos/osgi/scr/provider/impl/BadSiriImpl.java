/*
 * 此代码创建于 2015年12月26日 上午8:53:31。
 */
package com.apollo.demos.osgi.scr.provider.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.api.bad.IBadSiri;
import com.apollo.demos.osgi.scr.api.bad.IPhone;

@Component
@Service
public class BadSiriImpl implements IBadSiri {

    private static final Logger s_logger = LoggerFactory.getLogger(BadSiriImpl.class);

    public BadSiriImpl() {
        s_logger.info("New.");
    }

    @Override
    public String sayHello() {
        return "Hello, I am a bad Siri. [ID = " + ID + "]"; //这里的ID始终都是最新的，其实是编译优化引起的，并不是OSGi中存在2个IBadSiri。
    }

    @Override
    public IPhone giveMeAnIPhone() {
        return new IPhone();
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
