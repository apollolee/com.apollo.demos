/*
 * 此代码创建于 2015年11月19日 下午5:05:10。
 */
package com.apollo.demos.test.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoBaseDemo {

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
     * 验证调用行为。
     */
    @Test
    public void test01() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        mock.add(1);
        mock.clear();

        verify(mock).add(1);
        verify(mock).clear();
    }

    /**
     * 一个真实的对象时不能进行验证的。
     */
    @Test
    @Ignore
    public void test01_01() {
        List<Integer> mock = new ArrayList<Integer>();

        mock.add(1);
        mock.clear();

        verify(mock).add(1); //一个真实的对象时不能进行验证的。
        verify(mock).clear();
    }

    /**
     * 间接的调用也能验证，不过先要能够间接调用。
     */
    @Test
    public void test02() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(ArrayList.class);
        when(mock.contains(1)).thenCallRealMethod();

        mock.contains(1);

        verify(mock).contains(1);
        verify(mock).indexOf(1);
    }

    /**
     * 间接调用并没有实际调用到。
     */
    @Test
    @Ignore
    public void test02_01() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(ArrayList.class);

        mock.contains(1);

        verify(mock).contains(1);
        verify(mock).indexOf(1); //contains没有进行when的设置，不会调用真实的方法，就不会调用到indexOf了。
    }

    /**
     * 接口没有真实的调用实现。
     */
    @Test
    @Ignore
    public void test02_02() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);
        when(mock.contains(1)).thenCallRealMethod(); //List是接口，没有真实方法，这里会报错。

        mock.contains(1);

        verify(mock).contains(1);
        verify(mock).indexOf(1);
    }

    /**
     * 使用spy对象可以避免使用when设置预期行为。
     */
    @Test
    public void test03() {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> spy = spy(list);

        spy.contains(1); //spy对象所有方法缺省就是调用真实方法的。

        verify(spy).contains(1);
        verify(spy).indexOf(1);
    }

    /**
     * 要明白真实对象、mock对象以及spy对象的区别。
     */
    @Test
    public void test04() {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> spy = spy(list);

        assertEquals(0, spy.size());

        spy.add(1);

        assertEquals(1, spy.size());
        assertEquals(Integer.valueOf(1), spy.get(0));
    }

    /**
     * mock对象所有方法缺省什么都不做或返回null，缺省值等。
     */
    @Test
    @Ignore
    public void test04_01() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(ArrayList.class);

        assertEquals(0, mock.size());

        mock.add(1); //mock对象所有方法缺省什么都不做或返回null，缺省值等。

        assertEquals(1, mock.size());
        assertEquals(Integer.valueOf(1), mock.get(0));
    }

    /**
     * 模拟我们所期望的结果。
     */
    @Test
    public void test05() {
        @SuppressWarnings("unchecked")
        Iterator<Integer> mock = mock(Iterator.class);

        when(mock.next()).thenReturn(1, 2, 3); //预设当iterator调用next()时第一次返回1，第二次返回2，后面无论多少次都返回3
        //when(mock.next()).thenReturn(1).thenReturn(2).thenReturn(3); //这么写也可以，和上面的一样。

        assertEquals(Integer.valueOf(1), mock.next());
        assertEquals(Integer.valueOf(2), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
    }

    /**
     * 了解when的基本规则。
     */
    @Test
    @Ignore
    public void test05_01() {
        @SuppressWarnings("unchecked")
        Iterator<Integer> mock = mock(Iterator.class);

        when(mock.next()).thenReturn(1); //无效。
        when(mock.next()).thenReturn(2); //无效。
        when(mock.next()).thenReturn(3); //这样分开写就不行了，最后一句会覆盖前面的设置，使前面的无效。

        assertEquals(Integer.valueOf(1), mock.next());
        assertEquals(Integer.valueOf(2), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
    }

    /**
     * 模拟异常抛出。
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void test06() throws IOException {
        OutputStream mock = mock(OutputStream.class);

        doThrow(new IOException()).when(mock).close(); //预设当流关闭时抛出异常。

        mock.close();
    }

    /**
     * 多条when可以通过参数精确匹配模拟不同的输入输出。
     */
    @Test
    public void test07() {
        @SuppressWarnings("unchecked")
        Comparable<String> mock = mock(Comparable.class);

        when(mock.compareTo("a")).thenReturn(1);
        when(mock.compareTo("b")).thenReturn(2);

        assertEquals(1, mock.compareTo("a"));
        assertEquals(2, mock.compareTo("b"));
        assertEquals(0, mock.compareTo("c")); //对于没有预设的情况会返回默认值。
    }

    /**
     * 除了精确匹配外，还可以匹配自己想要的任意参数。
     */
    @Test
    public void test08() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        Matcher<Integer> isValid = new ArgumentMatcher<Integer>() {

            @Override
            public boolean matches(Object argument) {
                Integer i = (Integer) argument;
                return i == 1 || i == 2;
            }

        };

        when(mock.get(anyInt())).thenReturn(1); //匹配任意参数。 
        when(mock.contains(argThat(isValid))).thenReturn(true);

        assertEquals(Integer.valueOf(1), mock.get(0));
        assertEquals(Integer.valueOf(1), mock.get(1));
        assertEquals(Integer.valueOf(1), mock.get(999));
        assertTrue(mock.contains(1));
        assertFalse(mock.contains(3));
    }

    /**
     * 需要注意的是如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配。
     */
    @Test
    public void test09() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        mock.compare("a", "b");

        verify(mock).compare(anyString(), eq("b")); //如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配。
    }

    /**
     * 下面这样就不行。
     */
    @Test
    @Ignore
    public void test09_01() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        mock.compare("a", "b");

        verify(mock).compare(anyString(), "b"); //无效的参数匹配使用。
    }

    /**
     * when的参数匹配规则也是一样，不过可以同时设置匹配和精确，并且精确优先。
     */
    @Test
    public void test10() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        when(mock.compare(anyString(), eq("b"))).thenReturn(1);
        when(mock.compare("c", "b")).thenReturn(2); //when的参数匹配规则也是一样，不过可以同时设置匹配和精确，并且精确优先。

        assertEquals(1, mock.compare("a", "b"));
        assertEquals(2, mock.compare("c", "b"));
    }

    /**
     * 下面这样设置同样不行。
     */
    @Test
    @Ignore
    public void test10_01() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        when(mock.compare(anyString(), "b")).thenReturn(1); //无效的参数匹配使用。
        when(mock.compare("c", "b")).thenReturn(2);

        assertEquals(1, mock.compare("a", "b"));
        assertEquals(2, mock.compare("c", "b"));
    }

    /**
     * 关于匹配器使用需要注意在verify中不能分行书写，when中无此限制。
     */
    @Test
    @Ignore
    public void test10_02() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        int anyInt = anyInt();
        when(mock.get(anyInt)).thenReturn(999); //这里anyInt分两行写没有问题。

        assertEquals(Integer.valueOf(999), mock.get(10));

        anyInt = anyInt();
        verify(mock).get(anyInt); //这里anyInt分两行会出错，Mockito会检测调用顺序，要求写到一行中。
        //verify(mock).get(anyInt()); //这样不会有问题，Matchers在verify中都严格要求这种写法。
    }

    /**
     * 验证确切的调用次数。
     */
    @Test
    public void test11() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        mock.add(1);
        mock.add(2);
        mock.add(2);
        mock.add(3);
        mock.add(3);
        mock.add(3);

        verify(mock).add(1); //验证是否被调用一次，等效于下面的times(1)。
        verify(mock, times(1)).add(1); //这种验证可以反复执行多次。

        verify(mock, times(2)).add(2); //验证是否被调用2次 。

        verify(mock, times(3)).add(3); //验证是否被调用3次。

        verify(mock, never()).add(4); //验证是否从未被调用过。

        verify(mock, atLeastOnce()).add(1); //验证至少调用一次。

        verify(mock, atLeast(2)).add(2); //验证至少调用2次。

        verify(mock, atMost(3)).add(3); //验证至多调用3次。
    }

    /**
     * 验证执行顺序。
     */
    @Test
    public void test12() {
        @SuppressWarnings("unchecked")
        List<Integer> mock1 = mock(List.class);
        @SuppressWarnings("unchecked")
        List<String> mock2 = mock(List.class);

        mock1.add(1);
        mock2.add("a");
        mock1.add(2);
        mock2.add("b");

        //将需要排序的mock对象放入InOrder。  
        InOrder inOrder = inOrder(mock1, mock2);

        //下面的代码不能颠倒顺序，验证执行顺序。  
        inOrder.verify(mock1).add(1);
        inOrder.verify(mock2).add("a");
        inOrder.verify(mock1).add(2);
        inOrder.verify(mock2).add("b");
    }

    /**
     * 确保模拟对象上无互动发生。
     */
    @Test
    public void test13() {
        @SuppressWarnings("unchecked")
        List<Integer> mock1 = mock(List.class);
        @SuppressWarnings("unchecked")
        List<Integer> mock2 = mock(List.class);
        @SuppressWarnings("unchecked")
        List<Integer> mock3 = mock(List.class);

        mock1.add(1);

        verify(mock1).add(1);
        verify(mock1, never()).add(2);

        verifyZeroInteractions(mock2, mock3); //验证零互动行为。注意：这里不是指mock2和mock3之间有互相调用，而是指test13里面mock2和mock3没有任何方法被调用。
    }

    /**
     * 找出冗余的互动（即未被验证到的）。
     */
    @Test(expected = NoInteractionsWanted.class)
    public void test14() {
        @SuppressWarnings("unchecked")
        List<Integer> mock1 = mock(List.class);
        mock1.add(1);
        mock1.add(2);
        verify(mock1, times(2)).add(anyInt());
        verifyNoMoreInteractions(mock1); //检查是否有未被验证的互动行为，因为add(1)和add(2)都会被上面的anyInt()验证到，所以这里的代码会通过。

        @SuppressWarnings("unchecked")
        List<Integer> mock2 = mock(List.class);
        mock2.add(1);
        mock2.add(2);
        verify(mock2).add(1);
        verifyNoMoreInteractions(mock2); //检查是否有未被验证的互动行为，因为add(2)没有被验证，所以下面的代码会失败抛出异常。
    }

    /**
     * 使用回调生成期望值。
     */
    @Test
    public void test15() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        Answer<Integer> answer = new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                int index = (int) args[0];
                return index;
            }

        };
        when(mock.get(anyInt())).thenAnswer(answer); //使用Answer来生成我们我们期望的返回。

        assertEquals(Integer.valueOf(0), mock.get(0));
        assertEquals(Integer.valueOf(1), mock.get(1));
        assertEquals(Integer.valueOf(999), mock.get(999));
    }

    /**
     * 监控真实对象，使用spy来监控真实的对象，需要注意的是此时我们需要谨慎的使用when-then语句，而改用do-when语句。
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void test16() {
        List<Integer> list = new LinkedList<Integer>();
        List<Integer> spy = spy(list);

        //when(spy.get(999)).thenReturn(999); //这样预设的spy.get(0)会报错，因为会调用真实对象的get(0)，会抛出越界异常。  
        doReturn(999).when(spy).get(999); //使用doReturn-when可以避免when-thenReturn调用真实对象。

        //when(spy.size()).thenReturn(100); //预设size()期望值，这样调用同样会调用真实对象的size()，只不过不会产生什么严重影响。
        doReturn(100).when(spy).size(); //建议spy都按这种方式来写。

        //下面都会调用真实对象的实际方法。
        spy.add(1);
        spy.add(2);

        assertEquals(100, spy.size());
        assertEquals(Integer.valueOf(1), spy.get(0));
        assertEquals(Integer.valueOf(2), spy.get(1));
        assertEquals(Integer.valueOf(999), spy.get(999));

        verify(spy).add(1);
        verify(spy).add(2);

        spy.get(2); //这里还是会抛IndexOutOfBoundsException出来。
    }

    /**
     * 修改对未预设的调用返回默认期望值。
     */
    @Test
    public void test17() {
        Answer<Object> defaultAnswer = new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return 999; //这里演示处理很简单粗暴，其实这个Answer需要处理mock对象的所有方法，所以参数是Object，适应所有参数，不同方法返回的参数类型可能都不一样。
            }

        };

        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class, defaultAnswer); //mock对象使用Answer来对未预设的调用返回默认期望值。

        assertEquals(Integer.valueOf(999), mock.get(1)); //get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值。
        assertEquals(999, mock.size()); //size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值。
    }

    /**
     * 捕获参数来进一步断言。
     */
    @Test
    public void test18() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        String[] strings = new String[] { "a", "b" };
        Arrays.sort(strings, mock);

        ArgumentCaptor<String> argument1 = ArgumentCaptor.forClass(String.class); //Captor本身代表any，非any的Captor目前没有，官方说明后续会考虑增加，其实目前自己写Matcher可以搞定。
        ArgumentCaptor<String> argument2 = ArgumentCaptor.forClass(String.class);
        verify(mock).compare(argument1.capture(), argument2.capture()); //注意：这本身就是一个验证，只是在验证过程中顺便收集参数。

        assertEquals("b", argument1.getValue());
        assertEquals("a", argument2.getValue());
    }

    /**
     * 捕获参数集来进一步断言。
     */
    @Test
    public void test19() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        String[] strings = new String[] { "a", "b", "c", "d" };
        Arrays.sort(strings, mock);

        ArgumentCaptor<String> argument1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argument2 = ArgumentCaptor.forClass(String.class);
        verify(mock, times(3)).compare(argument1.capture(), argument2.capture());

        List<String> argument1Values = argument1.getAllValues(); //这里需要特别注意：Captor重复使用会累计收集，并且没有像Mock那样的reset动作，预计后续应该会增加reset功能。
        assertEquals("b", argument1Values.get(0));
        assertEquals("c", argument1Values.get(1));
        assertEquals("d", argument1Values.get(2));

        List<String> argument2Values = argument2.getAllValues();
        assertEquals("a", argument2Values.get(0));
        assertEquals("b", argument2Values.get(1));
        assertEquals("c", argument2Values.get(2));
    }

    /**
     * 重置mock。
     */
    @Test
    @SuppressWarnings("unchecked")
    public void test20() {
        List<Integer> mock = mock(List.class);

        when(mock.size()).thenReturn(10);
        mock.add(1);
        assertEquals(10, mock.size());
        verify(mock).add(1);

        reset(mock); //重置mock，清除所有的互动和预设。
        assertEquals(0, mock.size());
        verify(mock, never()).add(1);
    }

    /**
     * spy对象的doNothing的作用就和mock对象的thenCallRealMethod类似，但功能正好相反。
     * doNothing。
     */
    @Test
    public void test21() {
        A spy = spy(new A());

        doNothing().when(spy).doNotInvoke(); //mock不用thenCallRealMethod会加大mock难度，但对于spy来说，不用doNothing有时就无法完成测试了。
        spy.doNotInvoke();
        verify(spy).doNotInvoke();
    }

    /**
     * returnsFirstArg。
     */
    @Test
    public void test22() {
        @SuppressWarnings("unchecked")
        Map<String, String> mock = mock(Map.class);

        when(mock.get(anyString())).then(returnsFirstArg());

        assertEquals("a", mock.get("a"));
        assertEquals("b", mock.get("b"));
        assertEquals("c", mock.get("c"));

        verify(mock, times(3)).get(anyString());
    }

    /**
     * setInternalState，感觉用的地方不是很多，改变内部状态一般不如直接mock调用接口来的直接。
     */
    @Test
    public void test23() {
        B spy = spy(new B());
        setInternalState(spy, "m_value", 999); //估计调用了java的反射接口，从接口注释上看，这里只能设置字段，是不是私有无所谓。
        assertEquals(999, spy.getValue());
        verify(spy).getValue();
    }

    /**
     * 明确泛型参数，注意：这里不是适配，适配还是需要自己写Matcher。
     */
    @Test
    @SuppressWarnings("unchecked")
    public void test24() {
        C mock = mock(C.class);

        mock.process(Arrays.asList("a", "b"));
        mock.process(Arrays.asList("c", "d", "e"));
        mock.process(Arrays.asList("f"));
        mock.process(Collections.unmodifiableCollection(Arrays.asList("g", "h")));
        mock.process(Collections.unmodifiableCollection(Arrays.asList("i", "j", "k")));

        verify(mock, times(3)).process(any(List.class)); //不推荐，这样写无法指定参数类型，会有个警告，但不会产生问题，因为java的方法签名不对泛型参数进行识别。
        verify(mock, times(3)).process(Matchers.<List<String>> any()); //any返回值本身是有参数化的，但有冲突的情况下就需要明确指定。
        verify(mock, times(3)).process(anyListOf(String.class)); //这个和上面效果一样，但在类型上有局限性。

        verify(mock, times(2)).process(any(Collection.class));
        verify(mock, times(2)).process(Matchers.<Collection<String>> any());
        verify(mock, times(2)).process(anyCollectionOf(String.class));
    }

}
