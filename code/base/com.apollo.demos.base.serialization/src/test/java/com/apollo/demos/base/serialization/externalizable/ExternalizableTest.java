/*
 * 此代码创建于 2016年4月13日 下午2:12:29。
 */
package com.apollo.demos.base.serialization.externalizable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.apollo.demos.base.serialization.BaseTest;

public class ExternalizableTest extends BaseTest {

    @Test
    public void testC1() {
        C1 c1 = new C1();
        c1.m_transientInt = 66666;

        C1 c1s = getSerialized(c1);

        assertEquals(c1.m_transientInt, c1s.m_transientInt); //即使是transient也可以在Externalizable下进行序列化。
    }

}
