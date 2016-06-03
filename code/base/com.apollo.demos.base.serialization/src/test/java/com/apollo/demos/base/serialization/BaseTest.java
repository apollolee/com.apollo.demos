/*
 * 此代码创建于 2016年4月11日 下午2:52:24。
 */
package com.apollo.demos.base.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @SuppressWarnings("unchecked")
    protected static final <T extends Serializable> T getSerialized(T serializableObject) {
        byte[] serializedBytes = null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(serializableObject);
            oos.flush();
            serializedBytes = baos.toByteArray();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedBytes); ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Rule
    public ExpectedException m_expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

}
