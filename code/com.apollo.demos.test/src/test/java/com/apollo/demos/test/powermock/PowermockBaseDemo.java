/*
 * 此代码创建于 2015年11月20日 下午4:20:02。
 */
package com.apollo.demos.test.powermock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.apollo.demos.test.mockito.MyDictionary;

@RunWith(PowerMockRunner.class)
public class PowermockBaseDemo {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 模拟private的方法。
     * @throws Exception
     */
    @Test
    @PrepareForTest(A.class)
    public void test01() throws Exception {
        A spy = spy(new A()); //私有方法一般使用spy，因为触发私有方法的部分都是有代码实现的。

        doReturn("This is mock A.").when(spy, "getA", 1);
        assertEquals("This is mock A.", spy.getMessage(1)); //私有方法一般使用公有接口进行间接测试。
        assertEquals("This is A. [n = 2]", spy.getMessage(2));

        doReturn("This is mock A.").when(spy, "getA", anyInt());
        assertEquals("This is mock A.", spy.getMessage(2));
        assertEquals("This is mock A.", spy.getMessage(3));

        verifyPrivate(spy, times(4)).invoke("getA", anyInt());
    }

    /**
     * 模拟static方法。
     * @throws Exception
     */
    @Test
    @PrepareForTest(B.class)
    public void test02() {
        mockStatic(B.class); //static方法只能mock，不能spy。
        when(B.getMessage(999)).thenReturn("This is mock B.");
        when(B.getMessage(888)).thenCallRealMethod();

        assertEquals("This is mock B.", B.getMessage(999));
        assertEquals("This is B. [n = 888]", B.getMessage(888));
        assertNull(B.getMessage(777));

        verifyStatic(times(3)); //这里验证的目标静态方法是紧跟在这一句之后的静态方法调用，这2句必须成对出现，是一种特定规则。
        B.getMessage(anyInt()); //这一句不会真正调用静态方法，仅是个静态方法的描述。
    }

    /**
     * 模拟private static方法。
     * @throws Exception
     */
    @Test
    @PrepareForTest(C.class)
    public void test03() throws Exception {
        mockStatic(C.class);
        doCallRealMethod().when(C.class, "throwException");
        doNothing().when(C.class, "doNotInvoke"); //这句不加也没有关系，因为mock对象默认就是doNothing的。

        try {
            C.doNotInvoke();

        } catch (RuntimeException ex) {
            fail("This is not invoked");
        }

        doCallRealMethod().when(C.class, "doNotInvoke");

        try {
            C.doNotInvoke();
            fail("This is not invoked");

        } catch (RuntimeException ex) {
        }

        verifyPrivate(C.class).invoke("throwException"); //验证throwException只调用了一次。
        verifyStatic(times(2));
        C.doNotInvoke();
    }

    /**
     * 模拟final类、final方法和final静态方法。
     */
    @Test
    @PrepareForTest({ D1.class, D2.class, D3.class })
    public void test04() {
        D1 mockD1 = mock(D1.class);
        when(mockD1.getMessage(999)).thenReturn("This is mock D1.");
        assertEquals("This is mock D1.", mockD1.getMessage(999));
        verify(mockD1).getMessage(999);

        D2 mockD2 = mock(D2.class);
        when(mockD2.getMessage(888)).thenReturn("This is mock D2.");
        assertEquals("This is mock D2.", mockD2.getMessage(888));
        verify(mockD2).getMessage(888);

        mockStatic(D3.class);
        when(D3.getMessage(777)).thenReturn("This is mock D3.");
        assertEquals("This is mock D3.", D3.getMessage(777));
        verifyStatic();
        D3.getMessage(777);
    }

    /**
     * final类在Mockito中是无法模拟的。
     */
    @Test
    @Ignore
    public void test04_01() {
        D1 mockD1 = Mockito.mock(D1.class);
        Mockito.when(mockD1.getMessage(999)).thenReturn("This is mock D1.");
        assertEquals("This is mock D1.", mockD1.getMessage(999));
        Mockito.verify(mockD1).getMessage(999);
    }

    /**
     * final方法在Mockito中是无法模拟的。
     */
    @Test
    @Ignore
    public void test04_02() {
        D2 mockD2 = Mockito.mock(D2.class);
        Mockito.when(mockD2.getMessage(888)).thenReturn("This is mock D2.");
        assertEquals("This is mock D2.", mockD2.getMessage(888));
        Mockito.verify(mockD2).getMessage(888);
    }

    /**
     * 对new动作进行mock，注意@PrepareForTest中填写的是被影响的类，而不是填被Mock的类。
     * @throws Exception
     */
    @Test
    @PrepareForTest(MyDictionary.class)
    public void test05() throws Exception {
        @SuppressWarnings("unchecked")
        HashMap<String, String> mock = mock(HashMap.class);

        whenNew(HashMap.class).withNoArguments().thenReturn(mock);
        when(mock.get("a")).thenReturn("b");

        MyDictionary myDictionary = new MyDictionary();
        assertEquals("b", myDictionary.getMeaning("a"));

        verifyNew(HashMap.class).withNoArguments();
    }

    /**
     * 构造方法内部异常是我们使用whenNew的主要原因。
     * @throws Exception
     */
    @Test
    @PrepareForTest(E.class)
    public void test06() throws Exception {
        E mock = mock(E.class);

        whenNew(E.class).withNoArguments().thenReturn(mock);

        assertSame(mock, new E());
        assertSame(mock, new E());
        assertSame(mock, new E());

        verifyNew(E.class, times(3)).withNoArguments();
    }

    /**
     * 静态初始化块异常会导致类加载失败，直接抑制掉是目前唯一的处理方法。
     * @throws Exception
     */
    @Test
    @PrepareForTest(F.class)
    @SuppressStaticInitializationFor("com.apollo.demos.test.powermock.F")
    public void test07() throws Exception {
        F mock = mock(F.class);

        whenNew(F.class).withNoArguments().thenReturn(mock);

        assertSame(mock, new F());
        assertSame(mock, new F());
        assertSame(mock, new F());

        verifyNew(F.class, times(3)).withNoArguments();
    }

    /**
     * verifyNew不能在没有mock的情况下进行，这个和mockito是类似的。
     * @throws Exception
     */
    @Test
    @Ignore
    @PrepareForTest(F.class)
    @SuppressStaticInitializationFor("com.apollo.demos.test.powermock.F")
    public void test07_01() throws Exception {
        new F();
        new F();
        new F();

        verifyNew(F.class, times(3)).withNoArguments();
    }

    /**
     * verifyNew的前提是whenNew的时候设置了mock，这样框架才能收集相关信息进行verify。
     * @throws Exception
     */
    @Test
    @Ignore
    @PrepareForTest(F.class)
    @SuppressStaticInitializationFor("com.apollo.demos.test.powermock.F")
    public void test07_02() throws Exception {
        F mock = mock(F.class);

        //whenNew(F.class).withNoArguments().thenReturn(mock);

        assertNotSame(mock, new F());
        assertNotSame(mock, new F());
        assertNotSame(mock, new F());

        verifyNew(F.class, times(3)).withNoArguments();
    }

    /**
     * setInternalState，powermock的Whitebox强大了很多，但依然感觉用的地方不是很多。
     * @throws Exception
     */
    @Test
    @PrepareForTest(G.class)
    public void test08() throws Exception {
        G mock = mock(G.class);
        when(mock.getMessage()).thenReturn("This is mock G.");
        setInternalState(G.class, "s_instance", mock);

        mockStatic(G.class);
        doCallRealMethod().when(G.class, "getInstance");

        assertSame(mock, G.getInstance());
        assertEquals("This is mock G.", G.getInstance().getMessage());

        verify(mock).getMessage();
        verifyStatic(times(2));
        G.getInstance();
    }

    /**
     * 演示@PowerMockIgnore的作用。
     * @throws Exception
     */
    @Test
    @PowerMockIgnore({ "java.awt.*", "javax.swing.*" })
    public void test09() {
        //TODO @PowerMockIgnore是解决一些powermock类加载器引入的ClassCastException问题的唯一方法，很重要，暂时还没找到合适的例子。
    }

}
