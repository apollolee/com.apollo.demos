/*
 * 此代码创建于 2015年11月18日 下午5:04:00。
 */
package com.apollo.demos.base.test.junit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CustomMatcher;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class JUnitBaseDemo {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        //System.out.println("on setUpBeforeClass()");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        //System.out.println("on tearDownAfterClass()");
    }

    @Before
    public void setUp() throws Exception {
        //System.out.println("on setUp()");
    }

    @After
    public void tearDown() throws Exception {
        //System.out.println("on tearDown()");
    }

    @Test
    public void test01() {
        List<Integer> list = new ArrayList<Integer>();

        list.add(5);
        int actual = list.get(0);

        int expected = 5;

        assertEquals(expected, actual);
    }

    @Test
    public void test02() {
        List<Integer> list = new ArrayList<Integer>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        Integer[] actuals = list.toArray(new Integer[list.size()]);
        Integer[] expecteds = new Integer[] { 1, 2, 3, 4, 5 };

        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void test03() {
        int value = 3;
        int newVaue = value + 1;

        assertNotEquals(value, newVaue);
    }

    @Test
    public void test04() {
        int value = 3;
        int newVaue = value + 1;

        assertTrue(value < newVaue);
    }

    @Test
    public void test05() {
        int value = 3;
        int newVaue = value + 1;

        assertFalse(value > newVaue);
    }

    @Test
    public void test06() {
        Integer expected = new Integer(5);

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("expected", expected);
        Integer actual = map.get("expected");

        assertSame(expected, actual);
    }

    @Test
    @Ignore
    public void test06_01() {
        Integer expected = new Integer(5);
        Integer actual = new Integer(5);

        assertSame(expected, actual); //注意：相同和相等是不一样的概念。
    }

    @Test
    public void test07() {
        Integer unexpected = new Integer(5);

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("unexpected", unexpected);
        map.put("unexpected", new Integer(5));
        Integer actual = map.get("unexpected");

        assertNotSame(unexpected, actual);
    }

    @Test
    public void test08() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Integer object = map.get("object");

        assertNull(object);
    }

    @Test
    public void test09() {
        String[] actual = new String[] { "10.8.24.123", "255.255.255.0" };

        Matcher<String[]> matcher = new CustomMatcher<String[]>("Subnet Mask is 255.255.255.0") {

            @Override
            public boolean matches(Object obj) {
                String[] address = (String[]) obj;
                return "255.255.255.0".equals(address[1]);
            }

        };

        assertThat(actual, matcher);
    }

    @Test
    public void test10() {
        try {
            List<Integer> list = new ArrayList<Integer>();
            list.get(0);
            fail("No IndexOutOfBoundsException.");

        } catch (IndexOutOfBoundsException ex) {
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test11() {
        List<Integer> list = new ArrayList<Integer>();
        list.get(0);
    }

    @Test(timeout = 1000)
    public void test12() {
        try {
            Thread.sleep(500);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        assertTrue(true);
    }

    @Test
    public void test13() {
        int i = 0;

        assumeTrue(i != 0);
        assertEquals(1, 2 / i);
    }

    @Test
    public void test14() {
        int i = 2;

        assumeFalse(i == 0);
        assertEquals(1, 2 / i);
    }

    @Test
    public void test15() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("b", 2);
        Integer a = map.get("a");
        Integer b = map.get("b");

        assumeNotNull(a, b);
        assertEquals(1, a / b);
    }

    @Test
    public void test16() {
        List<Integer> list = new ArrayList<Integer>();

        try {
            list.get(0);

        } catch (IndexOutOfBoundsException ex) {
            assumeNoException(ex);
        }

        Integer i = list.get(0);

        assertTrue(i > 0);
    }

    @Test
    public void test17() {
        String[] actual = new String[] { "10.8.24.123", "255.255.255.0" };

        Matcher<String[]> matcher = new CustomMatcher<String[]>("Subnet Mask is 255.255.255.0") {

            @Override
            public boolean matches(Object obj) {
                String[] address = (String[]) obj;
                return "255.255.255.0".equals(address[1]);
            }

        };

        assumeThat(actual, matcher);

        matcher = new CustomMatcher<String[]>("IP Address is 10.8.24.123") {

            @Override
            public boolean matches(Object obj) {
                String[] address = (String[]) obj;
                return "10.8.24.123".equals(address[0]);
            }

        };

        assertThat(actual, matcher);
    }

}
