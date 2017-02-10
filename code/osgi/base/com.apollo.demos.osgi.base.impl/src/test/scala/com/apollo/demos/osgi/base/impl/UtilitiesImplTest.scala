package com.apollo.demos.osgi.base.impl

import org.junit.Assert._
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Spy
import org.mockito.runners.MockitoJUnitRunner

class UtilitiesImplTest extends TestBase {
  @Spy val utilities = new UtilitiesImpl

  @Test def testCurrentState() {
    val state = utilities.currentState("com.apollo.demos.osgi.base.impl.thread.ThreadPoolExecutor@3dc1e9b5[Running, pool size = 100, active threads = 25, queued tasks = 0, completed tasks = 123]")
    assertEquals("Running", state._1)
    assertEquals(100, state._2)
    assertEquals(25, state._3)
    assertEquals(0, state._4)
    assertEquals(123, state._5)
  }
}
