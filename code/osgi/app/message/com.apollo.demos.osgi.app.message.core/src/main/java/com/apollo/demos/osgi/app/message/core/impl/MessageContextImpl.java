/*
 * 此代码创建于 2016年1月11日 上午9:54:45。
 */
package com.apollo.demos.osgi.app.message.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.app.message.api.IMessageContext;

public class MessageContextImpl implements IMessageContext {

    private static final Logger s_logger = LoggerFactory.getLogger(MessageContextImpl.class);

    public MessageContextImpl() {
        s_logger.trace("New.");
    }

    @Override
    public String toString() {
        return "This is a message context.";
    }

}
