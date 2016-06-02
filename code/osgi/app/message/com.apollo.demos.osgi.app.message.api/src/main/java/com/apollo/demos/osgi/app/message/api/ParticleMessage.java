/*
 * 此代码创建于 2016年1月8日 下午2:27:16。
 */
package com.apollo.demos.osgi.app.message.api;

import static com.apollo.demos.osgi.app.message.api.MessageUtilities.showClass;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParticleMessage<M extends Serializable> {

    private static final Logger s_logger = LoggerFactory.getLogger(ParticleMessage.class);

    public RegisterInfo m_registerInfo;

    public String m_value;

    public M m_message;

    public ParticleMessage(RegisterInfo registerInfo, String value, M message) {
        s_logger.trace("New. [{}#{}#{}]", registerInfo, value, showClass(message));

        m_registerInfo = registerInfo;
        m_value = value;
        m_message = message;
    }

    public RegisterInfo getRegisterInfo() {
        return m_registerInfo;
    }

    public String getValue() {
        return m_value;
    }

    public M getMessage() {
        return m_message;
    }

    @Override
    public String toString() {
        return m_registerInfo + "#" + m_value + "#" + showClass(m_message);
    }

}
