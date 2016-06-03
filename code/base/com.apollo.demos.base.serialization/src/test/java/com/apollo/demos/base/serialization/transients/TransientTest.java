/*
 * 此代码创建于 2016年4月13日 下午2:12:29。
 */
package com.apollo.demos.base.serialization.transients;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.apollo.demos.base.serialization.BaseTest;

public class TransientTest extends BaseTest {

    @Test
    public void testC1() {
        C1 c1 = new C1();
        c1.m_int = 66666;
        c1.m_transientInt = 66666;
        c1.m_string = "66666";
        c1.m_transientString = "66666";

        C1 c1s = getSerialized(c1);

        assertEquals(c1.m_int, c1s.m_int);
        assertEquals(0, c1s.m_transientInt); //反序列化对象并不会调用构造方法，所以对于transient将直接恢复成缺省值。
        assertEquals(c1.m_string, c1s.m_string);
        assertNull(c1s.m_transientString); //这里也一样，对象的缺省值就是null。
    }

}
