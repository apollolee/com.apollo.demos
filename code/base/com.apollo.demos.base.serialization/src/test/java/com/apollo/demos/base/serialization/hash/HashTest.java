/*
 * 此代码创建于 2016年4月11日 下午3:39:12。
 */
package com.apollo.demos.base.serialization.hash;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.apollo.demos.base.serialization.BaseTest;

public class HashTest extends BaseTest {

    @Test
    public void testSet() {
        m_expectedException.expect(RuntimeException.class);
        m_expectedException.expectMessage("java.lang.NullPointerException");

        S s1 = new S("s1");
        S s2 = new S("s2");
        S s3 = new S("s3");

        s1.m_set.add(s2);
        s2.m_set.add(s3);
        s3.m_set.add(s1); //关键在这一句，Hash容器在序列化时会调用key的hashCode，而此时key还未完成序列化，相当于在初始化一半的对象上调用hashCode，会抛空指针异常。

        getSerialized(s1);
    }

    @Test
    public void testSetEquals() {
        m_expectedException.expect(StackOverflowError.class); //即使不序列化，Hash容器的循环关联也非常危险，equals会引起调用栈死循环。

        S s1 = new S("s1");
        S s2 = new S("s2");

        s1.m_set.add(s2);
        s2.m_set.add(s1);

        S s1a = new S("s1");
        S s2a = new S("s2");

        s1a.m_set.add(s2a);
        s2a.m_set.add(s1a);

        s1.equals(s1a);
    }

    @Test
    public void testMap() {
        m_expectedException.expect(StackOverflowError.class); //不做key，序列化没有问题，但equals依然会引起调用栈死循环。

        M m1 = new M();
        M m2 = new M();
        M m3 = new M();

        m1.m_map.put("m2", m2);
        m2.m_map.put("m3", m3);
        m3.m_map.put("m1", m1);

        M m1s = getSerialized(m1);
        assertEquals(m1, m1s);

        //Hash容器的循环关联在一般设计中应该尽量规避，而在序列化设计应严格禁止。
    }

}
