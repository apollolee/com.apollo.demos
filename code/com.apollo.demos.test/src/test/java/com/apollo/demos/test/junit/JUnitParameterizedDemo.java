/*
 * 此代码创建于 2015年11月19日 上午11:51:23。
 */
package com.apollo.demos.test.junit;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JUnitParameterizedDemo {

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

    @Parameters(name = "{index}: Fibonacci({0}) = {1}")
    public static Iterable<Integer[]> createDatas() {
        return Arrays.asList(new Integer[][] { { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 } });
    }

    private int m_input;

    private int m_expected;

    public JUnitParameterizedDemo(int input, int expected) {
        m_input = input;
        m_expected = expected;
    }

    @Test
    public void testFibonacci() {
        assertEquals(m_expected, Fibonacci.get(m_input));
    }

}
