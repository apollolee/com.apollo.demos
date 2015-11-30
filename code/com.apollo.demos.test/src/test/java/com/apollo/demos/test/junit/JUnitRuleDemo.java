/*
 * 此代码创建于 2015年11月19日 上午11:13:02。
 */
package com.apollo.demos.test.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

public class JUnitRuleDemo {

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

    @Rule
    public ExpectedException m_expectedException = ExpectedException.none();

    @Test
    public void test01() {
        m_expectedException.expect(IndexOutOfBoundsException.class);
        m_expectedException.expectMessage("Index: 0, Size: 0"); //ExpectedException.expectXxx很强大，如果需要，可以匹配特定调用栈信息。

        List<Integer> list = new ArrayList<Integer>();
        list.get(0); //注意，当抛出异常后，后面的语句是不会执行的。

        System.out.println("This is not invoked.");
    }

    @Rule
    public TemporaryFolder m_temporaryFolder = new TemporaryFolder();

    @Test
    public void test02() throws IOException {
        File parent = m_temporaryFolder.newFolder("test");

        assertTrue(parent.isDirectory());

        File testFile = new File(parent, "test.txt");
        assertTrue(testFile.createNewFile());
        assertEquals(parent, testFile.getParentFile());

        try (FileWriter fw = new FileWriter(testFile)) {
            fw.write("This is test.");
        }

        try (FileReader fr = new FileReader(testFile); BufferedReader br = new BufferedReader(fr)) {
            assertEquals("This is test.", br.readLine());
        }
    }

    @ClassRule
    public static Timeout s_timeout = new Timeout(1000);

    @Test
    public void test03() {
        try {
            Thread.sleep(500);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        assertTrue(true);
    }

}
