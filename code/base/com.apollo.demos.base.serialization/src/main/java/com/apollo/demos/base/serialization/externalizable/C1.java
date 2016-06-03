/*
 * 此代码创建于 2016年4月11日 下午2:46:43。
 */
package com.apollo.demos.base.serialization.externalizable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class C1 implements Externalizable {

    private static final long serialVersionUID = 1L;

    transient int m_transientInt = 12345;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(m_transientInt);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        m_transientInt = in.readInt();
    }

}
