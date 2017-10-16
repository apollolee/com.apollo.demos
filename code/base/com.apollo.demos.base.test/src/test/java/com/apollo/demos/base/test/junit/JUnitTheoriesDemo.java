/*
 * 此代码创建于 2015年11月19日 下午2:08:36。
 */
package com.apollo.demos.base.test.junit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class JUnitTheoriesDemo {

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

    @DataPoints
    public static int[] s_positiveInteger = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    @DataPoints
    public static int[] s_zero = new int[] { 0 };

    @DataPoints
    public static int[] s_negtiveInteger = new int[] { -1, -2, -3, -4, -5, -6, -7, -8, -9 };

    @Theory
    public void test1(int a, int b) {
        //System.out.println("Begin test1: a = " + a + " , b = " + b);
        assertTrue(a + b == b + a);//加法交换律
        //System.out.println("  End test1: a = " + a + " , b = " + b);
    }

    @Theory
    public void test2(int a, int b, int c) {
        //System.out.println("Begin test2: a = " + a + " , b = " + b + " , c = " + c);
        assertTrue((a + b) + c == a + (b + c));//加法结合律
        //System.out.println("  End test2: a = " + a + " , b = " + b + " , c = " + c);
    }

    @Theory
    public void test3(int a, int b) {
        //System.out.println("Begin test3: a = " + a + " , b = " + b);
        assertTrue(a * b == b * a);//乘法交换律
        //System.out.println("  End test3: a = " + a + " , b = " + b);
    }

    @Theory
    public void test4(int a, int b, int c) {
        //System.out.println("Begin test4: a = " + a + " , b = " + b + " , c = " + c);
        assertTrue((a * b) * c == a * (b * c));//乘法结合律
        //System.out.println("  End test4: a = " + a + " , b = " + b + " , c = " + c);
    }

    @Theory
    public void test5(int a, int b, int c) {
        //System.out.println("Begin test5: a = " + a + " , b = " + b + " , c = " + c);
        assertTrue((a + b) * c == a * c + b * c);//乘法分配律
        //System.out.println("  End test5: a = " + a + " , b = " + b + " , c = " + c);
    }

    @Theory
    public void test6(int a, int b) {
        //System.out.println("Begin test6: a = " + a + " , b = " + b);

        assumeTrue(a > 0 && b > 0);

        assertTrue(a + b > a);
        assertTrue(a + b > b);

        //System.out.println("  End test6: a = " + a + " , b = " + b);
    }

    @Theory
    public void test7(int a) {
        //System.out.println("Begin test7: a = " + a);

        assertTrue(a == -(-a)); //负负得正

        //System.out.println("  End test7: a = " + a);
    }

}
