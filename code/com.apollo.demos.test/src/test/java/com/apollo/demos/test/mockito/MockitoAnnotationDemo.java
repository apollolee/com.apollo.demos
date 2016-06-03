/*
 * 此代码创建于 2015年11月20日 下午2:27:33。
 */
package com.apollo.demos.test.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationDemo {

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

    public MockitoAnnotationDemo() {
        MockitoAnnotations.initMocks(this); //这个调用和@RunWith(MockitoJUnitRunner.class)使用其中一个即可。
    }

    @Mock
    private List<Integer> m_mock; //Mock属性是不是static影响不大，可能是有reset的关系。

    @Test
    public void test01() {
        when(m_mock.get(10)).thenReturn(999);
        assertEquals(Integer.valueOf(999), m_mock.get(10));
        verify(m_mock).get(10);
    }

    @Test
    public void test02() {
        when(m_mock.get(10)).thenReturn(888);
        assertEquals(Integer.valueOf(888), m_mock.get(10));
        verify(m_mock).get(10);
    }

    @Spy
    private List<Integer> m_spy = new ArrayList<Integer>(); //Spy属性是不是static影响很大，如果是static时设置的数据是会影响后面运行的Test。

    @Test
    public void test03() {
        m_spy.add(999);
        assertEquals(Integer.valueOf(999), m_spy.get(0));
        verify(m_spy).add(999);
    }

    @Test
    public void test04() {
        m_spy.add(888);
        assertEquals(Integer.valueOf(888), m_spy.get(0));
        verify(m_spy).add(888);
    }

    /**
     * 注意：虽然captor对象在每个test中是不同的对象，不会相互影响，但是在同一个test中，captor重复使用是会累计搜集的，并且captor目前没有像mock那样的reset动作，使用时需要注意。
     */
    @Captor
    private ArgumentCaptor<Comparator<Integer>> m_captor;

    @Test
    public void test05() {
        Comparator<Integer> comparator = new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }

        };

        Collections.sort(m_mock, comparator);

        verify(m_mock).sort(m_captor.capture());
        assertSame(comparator, m_captor.getValue());
    }

    @Mock
    private Map<String, String> m_wordMap; //这个mock会被注入到m_myDictionary中，这里名称最好和MyDictionary.m_wordMap保持一致，因为Inject规则是先类型匹配，再名称匹配。

    @InjectMocks
    private MyDictionary m_myDictionary = new MyDictionary();

    @Test
    public void test06() {
        when(m_wordMap.get("a")).thenReturn("b");
        assertEquals("b", m_myDictionary.getMeaning("a"));
    }

}
