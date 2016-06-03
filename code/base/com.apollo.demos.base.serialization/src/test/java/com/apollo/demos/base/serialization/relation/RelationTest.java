/*
 * 此代码创建于 2016年4月11日 下午3:16:11。
 */
package com.apollo.demos.base.serialization.relation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.HashMap;

import org.junit.Test;

import com.apollo.demos.base.serialization.BaseTest;

public class RelationTest extends BaseTest {

    @Test
    public void testC2C3() {
        C1 c1 = new C1();

        C2 c2 = new C2();
        c2.m_c1 = c1;

        C3 c3 = new C3();
        c3.m_c1 = c1;

        assertSame(c2.m_c1, c3.m_c1); //c1对象赋值时是同一个。

        c2 = getSerialized(c2);
        c3 = getSerialized(c3);

        assertNotSame(c2.m_c1, c3.m_c1); //通过分别序列化，c1对象生成了2个。这通常不是我们想要的。
    }

    @Test
    public void testC4() {
        C1 c1 = new C1();

        C2 c2 = new C2();
        c2.m_c1 = c1;

        C3 c3 = new C3();
        c3.m_c1 = c1;

        C4 c4 = new C4();
        c4.m_c2 = c2;
        c4.m_c3 = c3;

        C4 c4s = getSerialized(c4);

        assertNotSame(c4, c4s); //序列化后的对象和原对象不是同一个。
        assertSame(c4s.m_c2.m_c1, c4s.m_c3.m_c1); //通过单一序列化，c1对象只生成了1个。这个才是我们想要的，这也说明序列化单入口的重要性。
    }

    @Test
    public void testMap() {
        C1 c1 = new C1();

        C2 c2 = new C2();
        c2.m_c1 = c1;

        C3 c3 = new C3();
        c3.m_c1 = c1;

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("c2", c2);
        map.put("c3", c3);

        map = getSerialized(map);
        C2 c2s = (C2) map.get("c2");
        C3 c3s = (C3) map.get("c3");

        assertNotSame(c2, c2s); //序列化后的对象和原对象不是同一个。
        assertNotSame(c3, c3s); //序列化后的对象和原对象不是同一个。
        assertSame(c2s.m_c1, c3s.m_c1); //通过Map容器同样可以达到序列化单入口的效果。
    }

    @Test
    public void testC5C6C7() {
        m_expectedException.expect(StackOverflowError.class); //这里循环关联对序列化没有影响，但equals逻辑会产生循环，看equals代码很容易理解，这里的循环逻辑是必然的。

        C5 c5 = new C5();
        C6 c6 = new C6();
        C7 c7 = new C7();

        c5.m_c6 = c6;
        c6.m_c7 = c7;
        c7.m_c5 = c5;

        C5 c5s = getSerialized(c5);
        assertEquals(c5, c5s);
    }

}
